# 🚫 Block KakaoPay + KakaoTalk cleanup

A small [Morphe](https://morphe.software) patch bundle for KakaoTalk. **Block KakaoPay** disables KakaoPay on a patched (re-signed) KakaoTalk so its integrity scan never runs, and three cleanups remove ad slots and tune a list that other KakaoTalk patch sources leave behind. It is a companion bundle, meant to run alongside other KakaoTalk patch sources, not a full patch set.

**Add to Morphe:** https://morphe.software/add-source?github=wonjoonSeol-WS/morphe-patches

---

## English

This repository provides four patches. Add it alongside other KakaoTalk patch sources (for example, shortform/feed and ad removal) in Morphe. All four target KakaoTalk 26.8.3 (APKM) and are enabled by default; deselect any you do not want when you patch.

### Block KakaoPay

Closes every KakaoPay screen the moment it opens, and stops the KakaoPay integrity scan
("Moat") from running on a patched (re-signed) build.

**Why:** a patched KakaoTalk is signed with a different key than the official app, so running
KakaoPay on it can trip integrity checks and is not safe for real payments. This patch makes
KakaoPay unreachable on the patched app, including screens opened by an incoming money-transfer
or gift link, so you keep the original unmodified app installed for payments and use the patched
app only for messaging. As a side effect, the Moat scan never runs on the modified client.

### Disable Friend Feed ads

Stops the Friends tab news feed (소식) from loading its sponsored feed ad and its BizBoard native
ad, and removes the empty BizBoard placeholder card, so the ad leaves no blank slot behind.

![News feed before and after: the sponsored card is gone](images/before-after-news-feed.png)

### Hide Friends tab banner ad

Collapses the empty BizBoard banner slot at the top of the Friends tab. Without it the slot keeps
its height as a blank box under the chips; with it the rest of the list moves up and more rows fit
on screen. This is a resource patch (it edits one layout), not a bytecode patch.

![Friends tab before and after: the blank banner space is reclaimed](images/before-after-friends-tab.png)

### Set collapsed OpenChat room count

Sets how many OpenChat rooms show in the collapsed list before the more button. KakaoTalk shows 3;
this patch defaults to 4. Change it at patch time with the `collapsedCount` option (1 to 50): in
Morphe Manager use the option field on this patch, or with Morphe Desktop pass
`-O collapsedCount=5` after this bundle's `-p`.

### Usage

Install [Morphe](https://morphe.software), add this source (link above), select KakaoTalk, and
patch. It can be combined with other KakaoTalk patch sources in Morphe.

---

## 한국어

카카오톡용 작은 [Morphe](https://morphe.software) 패치 묶음이에요. **Block KakaoPay**는 패치된(재서명된) 카카오톡에서 카카오페이를 비활성화해 무결성 검사가 실행되지 않게 하고, 나머지 세 개는 다른 카카오톡 패치 소스가 남겨두는 광고 자리와 목록 설정을 정리해요. 전체 패치 모음이 아니라, 다른 카카오톡 패치 소스와 함께 쓰는 보조(companion) 묶음이에요.

### Block KakaoPay (카카오페이 차단)

카카오페이 화면이 열리는 즉시 닫고, 패치된(재서명된) 빌드에서 카카오페이 무결성 검사("Moat")가
실행되지 않게 막아요.

**이유:** 패치된 카카오톡은 공식 앱과 다른 키로 재서명돼 있어서, 그 위에서 카카오페이를 쓰면
무결성 검사에 걸릴 수 있고 실제 결제에 안전하지 않아요. 이 패치는 패치된 앱에서 카카오페이를 아예
열 수 없게 만들어요(송금/선물 링크로 열리는 화면 포함). 그래서 결제는 원본 앱으로 하고, 패치된
앱은 메신저 용도로만 쓰면 돼요. 그 결과 변조된 클라이언트에서 Moat 스캔도 실행되지 않아요.

### Disable Friend Feed ads (친구 소식 광고 제거)

친구 탭의 소식 피드가 스폰서 피드 광고와 BizBoard 네이티브 광고를 불러오지 않게 하고, 비어 있는
BizBoard 자리 카드도 없애서 광고가 빈 칸으로 남지 않게 해요.

![소식 피드 전후 비교: 스폰서 카드가 사라짐](images/before-after-news-feed.png)

### Hide Friends tab banner ad (친구 탭 배너 광고 자리 제거)

친구 탭 맨 위의 비어 있는 BizBoard 배너 자리를 접어요. 이 패치가 없으면 그 자리가 칩 아래에
빈 상자로 남고, 있으면 나머지 목록이 위로 올라와 더 많은 줄이 한 화면에 보여요. 바이트코드가
아니라 레이아웃 하나를 고치는 리소스 패치예요.

![친구 탭 전후 비교: 빈 배너 공간이 사라짐](images/before-after-friends-tab.png)

### Set collapsed OpenChat room count (오픈채팅 접힘 개수)

더보기 버튼 전에 접힌 오픈채팅 목록에 보이는 방 개수를 정해요. 카카오톡 기본값은 3개이고, 이
패치의 기본값은 4개예요. 패치할 때 `collapsedCount` 옵션(1~50)으로 바꿀 수 있어요. Morphe
Manager에서는 이 패치의 옵션 칸을 쓰고, Morphe Desktop에서는 이 묶음의 `-p` 뒤에
`-O collapsedCount=5`처럼 넘기세요.

대상은 모두 카카오톡 26.8.3 (APKM)이고 기본값은 켜짐이에요. 필요 없는 패치는 패치할 때 선택 해제하세요.

### 사용법

[Morphe](https://morphe.software)를 설치하고, 이 소스를 추가(위 링크)한 뒤, 카카오톡을 선택해
패치하세요. Morphe의 다른 카카오톡 패치 소스와 함께 쓸 수 있어요.

---

## 🩹 Patches list

<!-- PATCHES_START EXPANDED -->
> **[v1.1.0](https://github.com/wonjoonSeol-WS/morphe-patches/releases/tag/v1.1.0)**&nbsp;&nbsp;•&nbsp;&nbsp;`main`&nbsp;&nbsp;•&nbsp;&nbsp;4 patches total
<details open>
<summary>📦 Kakao Talk&nbsp;&nbsp;•&nbsp;&nbsp;4 patches</summary>
<br>

**🎯 Supported versions:**

| 26.8.3 |
| :---: |

| 💊&nbsp;Patch | 📜&nbsp;Description | ⚙️&nbsp;Options |
|----------|----------------|-----------|
| [Block KakaoPay](#block-kakaopay) | Closes every KakaoPay screen the moment it opens and stops the KakaoPay Moat scan from running on this re-signed client. Pay on the original, unpatched app instead. |  |
| [Disable Friend Feed ads](#disable-friend-feed-ads) | Stops the Friends tab news feed from loading its sponsored feed ad and its BizBoard native ad, and removes the empty BizBoard placeholder card so no blank slot is left behind, which other KakaoTalk ad patch sources leave in place. |  |
| [Hide Friends tab banner ad](#hide-friends-tab-banner-ad) | Collapses the empty BizBoard banner slot at the top of the Friends tab so it leaves no blank space, which other KakaoTalk ad patch sources leave reserved. |  |
| [Set collapsed OpenChat room count](#set-collapsed-openchat-room-count) | Sets how many OpenChat rooms show in the collapsed list before the more button (KakaoTalk default is 3). Change it with the collapsedCount option at patch time. | • Collapsed OpenChat room count |

</details>

<!-- PATCHES_END -->

## 🛠️ Building

Building resolves the Morphe patcher from the Morphe GitHub Packages registry, which requires
authentication even for public packages. Create a GitHub token with the `read:packages` scope and
add it to `~/.gradle/gradle.properties` (your user home, never committed):

```properties
gpr.user=your-github-username
gpr.key=your-token-with-read-packages
```

Then:

- Run `./gradlew buildAndroid`
- The built `.mpp` is in `patches/build/libs/patches-*.mpp`; apply it with
  [Morphe Desktop](https://github.com/MorpheApp/morphe-desktop) like any other patch bundle.

## ⚠️ Disclaimer

For personal and research use. A patched build is re-signed and is not the official app; use the
official KakaoTalk for payments and anything security-sensitive. Not affiliated with or endorsed
by Kakao. Using a modified client may violate KakaoTalk's terms of service.

## 📜 License

Licensed under the [GNU General Public License v3.0](LICENSE).
