package io.github.wonjoonseol.patches.kakaotalk.ads

import app.morphe.patcher.patch.resourcePatch
import io.github.wonjoonseol.patches.shared.Constants.COMPATIBILITY_KAKAO

// The Friends (main) tab reserves a banner slot at the very top of the list, above the profile update
// row. Its root is the custom view com.kakao.talk.friendv2.widget.FriendTabNestedBizboardScrollableHost
// from res/layout/friend_list_chip_biz_board_ad_view_holder.xml (id biz_board_container); on a device
// that id is the node at the banner position. Other KakaoTalk ad patch sources stop the ad from
// filling (the slot is empty in a build without this patch) but the slot keeps its height, which
// leaves a blank box under the chips. The root declares no margins and no other layout includes this
// file, so collapsing the root removes the reserved space.
@Suppress("unused")
val hideFriendTabBannerAdPatch = resourcePatch(
    name = "Hide Friends tab banner ad",
    description = "Collapses the empty BizBoard banner slot at the top of the Friends tab so it " +
        "leaves no blank space, which other KakaoTalk ad patch sources leave reserved.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_KAKAO)

    execute {
        document("res/layout/friend_list_chip_biz_board_ad_view_holder.xml").use { document ->
            val host = document.documentElement
            // Zero height keeps the slot collapsed even if code later calls setVisibility(VISIBLE);
            // gone also takes it out of layout and touch handling.
            host.setAttribute("android:layout_height", "0.0dip")
            host.setAttribute("android:visibility", "gone")
        }
    }
}
