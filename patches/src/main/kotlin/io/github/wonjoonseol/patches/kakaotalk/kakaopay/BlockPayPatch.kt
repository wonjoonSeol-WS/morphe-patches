package io.github.wonjoonseol.patches.kakaotalk.kakaopay

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import io.github.wonjoonseol.patches.shared.Constants.COMPATIBILITY_KAKAO
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

// initMoat() runs KakaoPay's native tamper/root scan ("Moat") and is the last step of
// PayBaseActivity.onCreate(); neither the class nor the method name is obfuscated.
internal object PayBaseActivityOnCreateFingerprint : Fingerprint(
    definingClass = "Lcom/kakao/talk/kakaopay/base/ui/PayBaseActivity;",
    name = "onCreate",
    parameters = listOf("Landroid/os/Bundle;"),
    returnType = "V",
)

@Suppress("unused")
val blockPayPatch = bytecodePatch(
    name = "Block KakaoPay",
    description = "Closes every KakaoPay screen the moment it opens and stops the KakaoPay Moat " +
        "scan from running on this re-signed client. Pay on the original, unpatched app instead.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_KAKAO)

    execute {
        val method = PayBaseActivityOnCreateFingerprint.method

        val initMoatIndex = method.instructions.indexOfFirst {
            it.opcode == Opcode.INVOKE_DIRECT &&
                ((it as? ReferenceInstruction)?.reference as? MethodReference)?.name == "initMoat"
        }
        if (initMoatIndex < 0) {
            throw PatchException("Could not find initMoat() call in PayBaseActivity.onCreate")
        }

        // finish() is null-safe during onCreate: setCloseTransition() guards closeTransition.
        method.addInstructions(
            initMoatIndex,
            """
                invoke-virtual {p0}, Lcom/kakao/talk/kakaopay/base/ui/PayBaseActivity;->finish()V
                return-void
            """,
        )
    }
}
