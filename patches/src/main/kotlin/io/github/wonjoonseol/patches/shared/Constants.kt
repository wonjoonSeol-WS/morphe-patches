package io.github.wonjoonseol.patches.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_KAKAO = Compatibility(
        name = "Kakao Talk", // App name as it appears in the Android launcher.
        packageName = "com.kakao.talk",
        // Matches the .apkm file type on APKMirror so Manager can send you to the right download.
        apkFileType = ApkFileType.APKM,
        // KakaoTalk brand yellow.
        appIconColor = 0xFEE500,
        targets = listOf(
            // Developed and verified against this exact version.
            AppTarget(
                version = "26.8.3"
            )
        )
    )
}
