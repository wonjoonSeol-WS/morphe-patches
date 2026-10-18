package io.github.wonjoonseol.patches.kakaotalk.openchat

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.methodCall
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.intOption
import io.github.wonjoonseol.patches.shared.Constants.COMPATIBILITY_KAKAO
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.NarrowLiteralInstruction
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

// KakaoTalk 26.8.3 collapses the OpenChat list to this many rooms.
private const val ORIGINAL_COLLAPSED_COUNT = 3

// The OpenChat "now" tab trims its room list to a fixed size before showing the collapse ("더보기")
// button: inside a (List, Z, Z)V method of OpenChatTabFragment it calls CollectionsKt.take(list, 3).
// KakaoTalk is obfuscated, so the method is matched by its preserved source file plus that take call.
internal object OpenChatCollapseTrimFingerprint : Fingerprint(
    returnType = "V",
    parameters = listOf("Ljava/util/List;", "Z", "Z"),
    filters = listOf(
        methodCall(
            definingClass = "Lkotlin/collections/CollectionsKt;",
            name = "take",
            opcode = Opcode.INVOKE_STATIC,
        ),
    ),
    custom = { _, classDef -> classDef.sourceFile == "OpenChatTabFragment.kt" },
)

@Suppress("unused")
val setOpenChatCollapsedCountPatch = bytecodePatch(
    name = "Set collapsed OpenChat room count",
    description = "Sets how many OpenChat rooms show in the collapsed list before the more button " +
        "(KakaoTalk default is $ORIGINAL_COLLAPSED_COUNT). Change it with the collapsedCount option at patch time.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_KAKAO)

    val collapsedCount by intOption(
        key = "collapsedCount",
        title = "Collapsed OpenChat room count",
        description = "Number of OpenChat rooms shown before the collapse/more button.",
        default = 4,
        validator = { it != null && it in 1..50 },
    )

    execute {
        val method = OpenChatCollapseTrimFingerprint.method
        val takeIndex = OpenChatCollapseTrimFingerprint.instructionMatches.first().index

        // take(Iterable, I): the count is the second argument register of the invoke.
        val countRegister = method.getInstruction<FiveRegisterInstruction>(takeIndex).registerD

        // That register is loaded by a const right before the invoke; rewrite its value.
        val constIndex = (takeIndex - 1 downTo 0).firstOrNull { i ->
            val insn = method.getInstruction(i)
            insn is OneRegisterInstruction &&
                insn.registerA == countRegister &&
                (insn.opcode == Opcode.CONST_4 || insn.opcode == Opcode.CONST_16 || insn.opcode == Opcode.CONST)
        } ?: throw PatchException("Could not find the collapse-count const before CollectionsKt.take")

        // Fail loudly if a KakaoTalk update changed what that const holds, instead of rewriting an
        // unrelated value.
        val original = method.getInstruction<NarrowLiteralInstruction>(constIndex).narrowLiteral
        if (original != ORIGINAL_COLLAPSED_COUNT) {
            throw PatchException(
                "Expected the OpenChat collapse count const to be $ORIGINAL_COLLAPSED_COUNT but found $original",
            )
        }

        method.replaceInstruction(constIndex, "const/16 v$countRegister, ${collapsedCount!!}")
    }
}
