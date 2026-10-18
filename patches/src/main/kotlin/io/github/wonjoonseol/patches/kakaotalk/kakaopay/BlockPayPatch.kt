package io.github.wonjoonseol.patches.kakaotalk.kakaopay

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.methodCall
import app.morphe.patcher.patch.bytecodePatch
import io.github.wonjoonseol.patches.shared.Constants.COMPATIBILITY_KAKAO

// initMoat() runs KakaoPay's native tamper/root scan ("Moat") and is the last step of
// PayBaseActivity.onCreate(); neither the class nor the method name is obfuscated.
internal object PayBaseActivityOnCreateFingerprint : Fingerprint(
    definingClass = "Lcom/kakao/talk/kakaopay/base/ui/PayBaseActivity;",
    name = "onCreate",
    parameters = listOf("Landroid/os/Bundle;"),
    returnType = "V",
    filters = listOf(
        methodCall(definingClass = "this", name = "initMoat"),
    ),
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
        val initMoatIndex = PayBaseActivityOnCreateFingerprint.instructionMatches.first().index

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
