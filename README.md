# 🚫 Block KakaoPay + KakaoTalk cleanup

A small [Morphe](https://morphe.software) patch bundle for KakaoTalk. **Block KakaoPay** disables KakaoPay on a patched (re-signed) KakaoTalk so its integrity scan never runs, and three cleanups remove ad slots and tune a list that other KakaoTalk patch sources leave behind. It is a companion bundle, meant to run alongside other KakaoTalk patch sources, not a full patch set.

**Add to Morphe:** https://morphe.software/add-source?github=wonjoonSeol-WS/morphe-patches

> [!IMPORTANT]
> **Supported version: KakaoTalk 26.8.3 (APKM) only.** KakaoTalk 26.9.0 and other versions are not
> supported. AmpleReVanced moved to 26.9.0 in v1.5.0-dev.22, so pair this bundle with AmpleReVanced
> v1.5.0-dev.19 (see [Patches we use with it](#patches-we-use-with-it)).
>
> **지원 버전: 카카오톡 26.8.3 (APKM)만 지원해요.** 26.9.0 등 다른 버전은 지원하지 않아요.
> AmpleReVanced는 v1.5.0-dev.22부터 26.9.0 대상이라, 이 묶음과는 AmpleReVanced v1.5.0-dev.19를 함께
> 쓰세요([함께 쓰는 패치](#함께-쓰는-패치) 참고).

> [!CAUTION]
> **A patched KakaoTalk can get your Kakao account banned.** Kakao can restrict an account
> temporarily or permanently, and in some cases immediately (section 4 of
> [Kakao's operation policy](https://www.kakao.com/policy/oppolicy)). The risk is highest when the
> patches are applied wrong: the wrong AmpleReVanced release, its default patch selection, or an
> integrity patch that failed to apply. Read [Avoiding an account ban](#avoiding-an-account-ban)
> before you patch, and only use an account you can afford to lose.

> [!CAUTION]
> **패치된 카카오톡을 쓰면 카카오 계정이 정지될 수 있어요.** 카카오는 계정 이용을 한시적 또는
> 영구적으로, 경우에 따라 즉시 제한할 수 있어요([카카오 운영정책](https://www.kakao.com/policy/oppolicy) 4항).
> 특히 패치를 잘못 적용했을 때(맞지 않는 AmpleReVanced 버전, 기본 패치 선택 그대로 패치, 적용에 실패한
> 무결성 패치) 위험이 가장 커요. 패치하기 전에 [계정 정지를 피하려면](#계정-정지를-피하려면)을 꼭 읽고,
> 잃어도 괜찮은 계정으로만 쓰세요.

---

## English

This repository provides four patches. It does not remove short-form or most ads by itself: we run it alongside [AmpleReVanced](https://github.com/AmpleReVanced/revanced-patches), and [Patches we use with it](#patches-we-use-with-it) lists exactly which of its patches we apply. All four target KakaoTalk 26.8.3 (APKM) and are enabled by default; deselect any you do not want when you patch.

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

### Patches we use with it

Our build combines this bundle with 40 patches from
[AmpleReVanced/revanced-patches](https://github.com/AmpleReVanced/revanced-patches)
[v1.5.0-dev.19](https://github.com/AmpleReVanced/revanced-patches/releases/tag/v1.5.0-dev.19) on
KakaoTalk 26.8.3, and all 44 apply with no failures. Use that exact AmpleReVanced release, not the
newest one:

- **Morphe Manager:** download `patches-1.5.0-dev.19.mpp` from the release page and add it under
  **Patch sources > Add patch source > Local**. Do not add the AmpleReVanced repository with
  **Pre-release patches** on instead: that follows its newest pre-release, and v1.5.0-dev.22 already
  targets KakaoTalk 26.9.0 (the stable v1.4.0 targets 26.7.2).
- **Morphe Desktop:** pass the downloaded `.mpp` with `-p` (see [Usage](#usage)).

v1.5.0-dev.20 and v1.5.0-dev.21 also target 26.8.3 with the same patch names, but we have not
tested them.

| Group | AmpleReVanced patches |
|-------|-----------------------|
| Settings | `Add settings resources`, `Add settings tab` |
| Short-form and ads | `Remove Short-form Tab`, `Disable ShortForm ad`, `Disable ChatRoomAdController`, `Disable Friend Lists ad`, `Disable OpenChat feed ad`, `Disable Pay banner ad`, `Disable chat room list ad`, `Remove BizBoard ads`, `Remove More tab ad`, `Remove OpenLink chat room list ad`, `Remove feed ad`, `Remove focus ad`, `Remove native ad` |
| Integrity (keep all) | `Disable verifying signature`, `Spoof signature`, `Hook Package Manager`, `Spoof apk checksums`, `Spoof App ID`, `Spoof installer package name`, `Spoof attestation package name`, `Disable abuse detection report`, `Disable AdFit environment detection` |
| KakaoPay | `Bypass Moat check`, `Remove shop tab`, `Hide More tab components` |
| Privacy | `Disable S2Event`, `Disable SDK Tracker`, `Disable Sentry`, `Strip image metadata`, `Default external browser` |
| Chat | `Show deleted, hidden, or edited messages`, `Block reactions on deleted or hidden messages`, `Block replies on deleted or hidden messages`, `Show message read receipts`, `Show messages restricted to mobile` |
| Convenience | `Allow profile media download`, `Open profile from open chat feed`, `Ghost Mode` |

From this bundle we apply all four patches, with `collapsedCount` at 4.

After installing, open the Morphe settings inside KakaoTalk and turn on:

- **Advanced > Bypass Moat integrity detection** (off by default). It is a backup for
  `Block KakaoPay`: if a KakaoPay screen ever gets past it, Moat does not scan and returns a benign
  result. It does not make paying on the patched app safe; AmpleReVanced's own description says
  payments on a modified build are still risky.
- **Hide KakaoPay section** from `Hide More tab components` (off by default), so the More tab has no
  KakaoPay entry to tap.

### Avoiding an account ban

Kakao can restrict an account temporarily or permanently, and in some cases immediately (section 4
of [Kakao's operation policy](https://www.kakao.com/policy/oppolicy)). Most of the risk from a
patched KakaoTalk comes from patching it wrong. Before you log in with a patched build:

1. **Use the exact versions.** KakaoTalk 26.8.3 (APKM), this bundle, and AmpleReVanced
   v1.5.0-dev.19 (see above). Do not force patches onto a version they do not list: a patch that
   fails to match leaves that part of KakaoTalk unpatched.
2. **Turn on Expert mode and start from Disable all.** Without Expert mode, Morphe Manager does not
   let you pick patches and applies each source's defaults, and AmpleReVanced v1.5.0-dev.19 turns on
   71 of its 75 KakaoTalk patches by default, including risky ones below. In Expert mode, tap
   **Disable all** on the patch screen, then enable only the patches listed above.
3. **Never enable these AmpleReVanced patches:**
   - `Force enable emoticon plus feature` (on by default): sends paid emoticons you have not bought.
   - `Override feature flag` (on by default): overrides KakaoTalk's feature flags, which can switch
     on features Kakao has not enabled for you.
   - `Change package name`: installs KakaoTalk as `com.kakao.talk.revanced`, a package Kakao does
     not ship.
   - `Add Packet Handler`: lets other apps handle KakaoTalk's packets, which AmpleReVanced itself
     says may compromise security.
4. **Check the patch result before you install.** Every selected patch must apply. If
   `Disable verifying signature`, `Spoof signature`, `Disable abuse detection report` or
   `Block KakaoPay` fails, do not install that build. Without `Disable abuse detection report`,
   KakaoTalk answers its startup and login integrity check with a Google-signed token that exposes
   the re-signed certificate.
5. **Keep the blockers with Show deleted.** `Show deleted, hidden, or edited messages` needs
   `Block reactions on deleted or hidden messages` and `Block replies on deleted or hidden messages`.
   Without them you can react or reply to a message the server considers deleted, which tells Kakao
   you can still see it.
6. **Never pay with the patched app.** Keep the original app for KakaoPay. After installing, open
   KakaoPay once (or tap a money-transfer or gift link): it must close immediately. If it stays
   open, `Block KakaoPay` did not match, so uninstall that build.

Doing all of this lowers the risk but does not remove it. A re-signed build can never produce a
valid Play Integrity token (`Disable abuse detection report` answers with an empty token and a
failure code instead), so Kakao can still tell it apart from the official app. Only use an account
you can afford to lose.

### Usage

**Morphe Manager**

1. Turn on **Expert mode** in Morphe Manager's settings.
2. Add this source from the link above, and AmpleReVanced v1.5.0-dev.19 as a local `.mpp` (see
   [Patches we use with it](#patches-we-use-with-it)).
3. Select KakaoTalk and provide the 26.8.3 APKM. On the patch screen tap **Disable all**, then
   enable the patches listed above in both sources.
4. Patch, check that nothing failed, install, and turn on the two in-app settings above.

**Morphe Desktop**

```sh
java -jar morphe-desktop-<version>-all.jar patch --exclusive \
  -p patches-1.5.0-dev.19.mpp -e "Add settings resources" -e "Add settings tab" ... \
  -p patches-1.1.0.mpp -O collapsedCount=4 \
    -e "Block KakaoPay" -e "Disable Friend Feed ads" \
    -e "Hide Friends tab banner ad" -e "Set collapsed OpenChat room count" \
  -r result.json -o kakaotalk-patched.apk <KakaoTalk 26.8.3 .apkm>
```

Pass one `-e` per AmpleReVanced patch in the table above. `--exclusive` leaves every patch you did
not name with `-e` off, and `failedPatches` in `result.json` must be empty.

---

## 한국어

카카오톡용 작은 [Morphe](https://morphe.software) 패치 묶음이에요. **Block KakaoPay**는 패치된(재서명된) 카카오톡에서 카카오페이를 비활성화해 무결성 검사가 실행되지 않게 하고, 나머지 세 개는 다른 카카오톡 패치 소스가 남겨두는 광고 자리와 목록 설정을 정리해요. 전체 패치 모음이 아니라, 다른 카카오톡 패치 소스와 함께 쓰는 보조(companion) 묶음이에요. 저희는 [AmpleReVanced](https://github.com/AmpleReVanced/revanced-patches)와 함께 쓰고, 그중 어떤 패치를 쓰는지는 [함께 쓰는 패치](#함께-쓰는-패치)에 정리했어요.

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

### 함께 쓰는 패치

저희 빌드는 이 묶음에
[AmpleReVanced/revanced-patches](https://github.com/AmpleReVanced/revanced-patches)
[v1.5.0-dev.19](https://github.com/AmpleReVanced/revanced-patches/releases/tag/v1.5.0-dev.19)의
패치 40개를 더해 카카오톡 26.8.3에 적용하고, 44개 모두 실패 없이 적용돼요. AmpleReVanced는 최신
버전이 아니라 정확히 이 릴리스를 쓰세요.

- **Morphe Manager:** 릴리스 페이지에서 `patches-1.5.0-dev.19.mpp`를 받아 **패치 소스 > 패치 소스
  추가 > 로컬**로 추가하세요. AmpleReVanced 저장소를 **Pre-release 패치**를 켠 채로 추가하면 안 돼요.
  그러면 가장 최신 pre-release를 따라가는데, v1.5.0-dev.22는 이미 카카오톡 26.9.0 대상이에요(정식
  v1.4.0은 26.7.2 대상).
- **Morphe Desktop:** 받은 `.mpp`를 `-p`로 넘기세요([사용법](#사용법) 참고).

v1.5.0-dev.20과 v1.5.0-dev.21도 26.8.3 대상이고 패치 이름도 같지만, 저희가 테스트하지는 않았어요.

| 분류 | AmpleReVanced 패치 |
|------|--------------------|
| 설정 | `Add settings resources`, `Add settings tab` |
| 숏폼과 광고 | `Remove Short-form Tab`, `Disable ShortForm ad`, `Disable ChatRoomAdController`, `Disable Friend Lists ad`, `Disable OpenChat feed ad`, `Disable Pay banner ad`, `Disable chat room list ad`, `Remove BizBoard ads`, `Remove More tab ad`, `Remove OpenLink chat room list ad`, `Remove feed ad`, `Remove focus ad`, `Remove native ad` |
| 무결성 (모두 유지) | `Disable verifying signature`, `Spoof signature`, `Hook Package Manager`, `Spoof apk checksums`, `Spoof App ID`, `Spoof installer package name`, `Spoof attestation package name`, `Disable abuse detection report`, `Disable AdFit environment detection` |
| 카카오페이 | `Bypass Moat check`, `Remove shop tab`, `Hide More tab components` |
| 개인정보 | `Disable S2Event`, `Disable SDK Tracker`, `Disable Sentry`, `Strip image metadata`, `Default external browser` |
| 채팅 | `Show deleted, hidden, or edited messages`, `Block reactions on deleted or hidden messages`, `Block replies on deleted or hidden messages`, `Show message read receipts`, `Show messages restricted to mobile` |
| 편의 | `Allow profile media download`, `Open profile from open chat feed`, `Ghost Mode` |

이 묶음에서는 패치 네 개를 모두 적용하고, `collapsedCount`는 4로 둬요.

설치한 뒤 카카오톡 안의 Morphe 설정에서 다음을 켜세요.

- **고급 > Moat 무결성 감지 우회** (기본값 꺼짐). `Block KakaoPay`의 예비 장치예요. 카카오페이 화면이
  혹시 차단을 빠져나가도 Moat가 검사하지 않고 정상 결과를 돌려줘요. 그렇다고 패치된 앱에서 결제해도
  안전해지는 건 아니에요. AmpleReVanced 설명에도 변조된 빌드에서의 결제는 여전히 위험하다고 적혀 있어요.
- `Hide More tab components`의 **카카오페이 섹션 숨기기** (기본값 꺼짐). 더보기 탭에 누를 수 있는
  카카오페이 항목이 남지 않게 해요.

### 계정 정지를 피하려면

카카오는 계정 이용을 한시적 또는 영구적으로, 경우에 따라 즉시 제한할 수 있어요
([카카오 운영정책](https://www.kakao.com/policy/oppolicy) 4항). 패치된 카카오톡의 위험은 대부분 패치를
잘못 적용하는 데서 와요. 패치된 빌드로 로그인하기 전에 다음을 지키세요.

1. **버전을 정확히 맞추세요.** 카카오톡 26.8.3 (APKM), 이 묶음, AmpleReVanced v1.5.0-dev.19(위
   참고). 패치가 지원하지 않는 버전에 억지로 적용하지 마세요. 매칭에 실패한 패치는 카카오톡의 그
   부분을 패치하지 않은 채로 남겨요.
2. **전문가 모드를 켜고 모두 비활성화에서 시작하세요.** 전문가 모드가 꺼져 있으면 Morphe Manager는
   패치를 고를 수 없고 각 소스의 기본 선택을 그대로 적용해요. AmpleReVanced v1.5.0-dev.19는 카카오톡
   패치 75개 중 71개가 기본으로 켜져 있고, 아래의 위험한 패치도 포함돼요. 전문가 모드에서 패치 화면의
   **모두 비활성화**를 누른 뒤, 위 목록의 패치만 켜세요.
3. **다음 AmpleReVanced 패치는 절대 켜지 마세요.**
   - `Force enable emoticon plus feature` (기본 켜짐): 구매하지 않은 유료 이모티콘을 보내요.
   - `Override feature flag` (기본 켜짐): 카카오톡의 기능 플래그를 덮어써서, 카카오가 켜주지 않은
     기능을 켤 수 있어요.
   - `Change package name`: 카카오톡을 카카오가 배포하지 않는 패키지 이름인
     `com.kakao.talk.revanced`로 설치해요.
   - `Add Packet Handler`: 다른 앱이 카카오톡의 패킷을 다룰 수 있게 해요. AmpleReVanced 스스로 보안을
     해칠 수 있다고 적어 둔 패치예요.
4. **설치하기 전에 패치 결과를 확인하세요.** 선택한 패치가 모두 적용돼야 해요.
   `Disable verifying signature`, `Spoof signature`, `Disable abuse detection report`,
   `Block KakaoPay` 중 하나라도 실패했다면 그 빌드는 설치하지 마세요.
   `Disable abuse detection report`가 없으면 카카오톡은 시작과 로그인 때의 무결성 검사에 재서명된
   인증서를 드러내는 Google 서명 토큰으로 응답해요.
5. **삭제 메시지 보기는 차단 패치와 함께 쓰세요.** `Show deleted, hidden, or edited messages`를 켜면
   `Block reactions on deleted or hidden messages`와 `Block replies on deleted or hidden messages`도
   켜야 해요. 없으면 서버에서 삭제된 메시지에 반응하거나 답장할 수 있고, 그러면 아직 그 메시지를 보고
   있다는 사실이 카카오에 드러나요.
6. **패치된 앱으로는 절대 결제하지 마세요.** 카카오페이는 원본 앱으로 쓰세요. 설치한 뒤 카카오페이를
   한 번 열어(또는 송금/선물 링크를 눌러) 바로 닫히는지 확인하세요. 열린 채로 남으면
   `Block KakaoPay`가 매칭되지 않은 것이니 그 빌드는 삭제하세요.

이걸 모두 지켜도 위험이 줄어들 뿐 없어지지는 않아요. 재서명된 빌드는 유효한 Play Integrity 토큰을
절대 만들 수 없어서(`Disable abuse detection report`는 빈 토큰과 실패 코드로 대신 응답해요), 카카오는
여전히 공식 앱과 구분할 수 있어요. 잃어도 괜찮은 계정으로만 쓰세요.

### 사용법

**Morphe Manager**

1. Morphe Manager 설정에서 **전문가 모드**를 켜세요.
2. 이 소스는 위 링크로, AmpleReVanced v1.5.0-dev.19는 로컬 `.mpp`로 추가하세요
   ([함께 쓰는 패치](#함께-쓰는-패치) 참고).
3. 카카오톡을 선택하고 26.8.3 APKM을 넣으세요. 패치 화면에서 **모두 비활성화**를 누른 뒤, 두 소스
   모두에서 위 목록의 패치를 켜세요.
4. 패치하고, 실패한 패치가 없는지 확인한 뒤 설치하고, 위의 앱 내 설정 두 개를 켜세요.

**Morphe Desktop**

위 영어 섹션의 [명령 예시](#usage)를 쓰세요. AmpleReVanced 패치는 표에 있는 이름마다 `-e`를 하나씩
넘기고, `--exclusive`는 `-e`로 지정하지 않은 패치를 모두 끈 채로 둬요. `result.json`의
`failedPatches`는 비어 있어야 해요.

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
by Kakao. Using a modified client may violate KakaoTalk's terms of service and can get your account
restricted or banned; see [Avoiding an account ban](#avoiding-an-account-ban).

## 📜 License

Licensed under the [GNU General Public License v3.0](LICENSE).
