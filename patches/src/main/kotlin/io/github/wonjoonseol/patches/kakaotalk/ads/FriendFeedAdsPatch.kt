package io.github.wonjoonseol.patches.kakaotalk.ads

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.methodCall
import app.morphe.patcher.patch.bytecodePatch
import io.github.wonjoonseol.patches.shared.Constants.COMPATIBILITY_KAKAO
import com.android.tools.smali.dexlib2.AccessFlags

// The Friends tab "news" feed (friendv2.screen.maintab.chip.feed) shows two sponsored cards that
// other KakaoTalk ad patch sources do not remove: a FeedAd and a BizBoard native ad. KakaoTalk
// obfuscates its own class names, so these are matched by their preserved Kotlin source file name
// plus the AdFit load call they make (the AdFit SDK classes are not obfuscated).

// FriendFeedChipFeedAdViewModel builds a FeedAdRequest and calls FeedAdLoader.loadAd(); that load
// method is the only one in the class that reaches loadAd, so it is the removal point.
internal object FriendFeedChipFeedAdLoadFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "V",
    filters = listOf(
        methodCall(definingClass = "Lcom/kakao/adfit/ads/feed/FeedAdLoader;", name = "loadAd"),
    ),
    custom = { _, classDef -> classDef.sourceFile == "FriendFeedChipFeedAdViewModel.kt" },
)

// FriendFeedChipBizBoardAdViewModel calls TalkNativeAdProvider.load() from a single method; it is
// the only method in the class that starts the BizBoard native ad load.
internal object FriendFeedChipBizBoardAdLoadFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "V",
    filters = listOf(
        methodCall(definingClass = "Lcom/kakao/adfit/ads/talk/TalkNativeAdProvider;", name = "load"),
    ),
    custom = { _, classDef -> classDef.sourceFile == "FriendFeedChipBizBoardAdViewModel.kt" },
)

// Blocking the load above is not enough on its own: the BizBoard ad is a 3-state sealed class and its
// "pending" state still produces an empty placeholder card (the "feed_bizboard" slot) while it waits
// for an ad, which is the blank box that is left behind. FriendFeedChipItemFactory builds that card in
// the only method that carries the "feed_bizboard" slot id. That builder already returns null for the
// no-ad state, its single caller (FriendFeedChipItemFactory.a) adds the result through a null-safe
// add-if-not-null helper, so forcing it to return null drops the placeholder without a layout change.
// The object-return check keeps `return-object` valid if a later version changes the return type.
internal object FriendFeedChipBizBoardItemFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    strings = listOf("feed_bizboard"),
    custom = { method, classDef ->
        classDef.sourceFile == "FriendFeedChipItemFactory.kt" && method.returnType.startsWith("L")
    },
)

@Suppress("unused")
val disableFriendFeedAdsPatch = bytecodePatch(
    name = "Disable Friend Feed ads",
    description = "Stops the Friends tab news feed from loading its sponsored feed ad and its " +
        "BizBoard native ad, and removes the empty BizBoard placeholder card so no blank slot is " +
        "left behind, which other KakaoTalk ad patch sources leave in place.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_KAKAO)

    execute {
        // Both are void load methods; returning immediately means no ad is ever requested, so the
        // feed never receives an ad to render.
        FriendFeedChipFeedAdLoadFingerprint.method.addInstructions(0, "return-void")
        FriendFeedChipBizBoardAdLoadFingerprint.method.addInstructions(0, "return-void")

        // Return null from the BizBoard item builder so its pending-state placeholder card is never
        // added to the feed. v0 is always a valid register (the method is an instance method).
        FriendFeedChipBizBoardItemFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return-object v0
            """,
        )
    }
}
