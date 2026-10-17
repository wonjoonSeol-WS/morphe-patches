# 🚫 Block KakaoPay

A single [Morphe](https://morphe.software) patch that disables KakaoPay on a patched (re-signed) KakaoTalk, so KakaoPay's integrity scan never runs. It is a companion patch, meant to run alongside other KakaoTalk patch sources, not a full patch set.

**Add to Morphe:** https://morphe.software/add-source?github=wonjoonSeol-WS/morphe-patches

---

## English

This repository provides the **Block KakaoPay** patch only. Add it alongside other KakaoTalk patch sources (for example, shortform/feed and ad removal) in Morphe.

### Block KakaoPay

Closes every KakaoPay screen the moment it opens, and stops the KakaoPay integrity scan
("Moat") from running on a patched (re-signed) build.

**Why:** a patched KakaoTalk is signed with a different key than the official app, so running
KakaoPay on it can trip integrity checks and is not safe for real payments. This patch makes
KakaoPay unreachable on the patched app, including screens opened by an incoming money-transfer
or gift link, so you keep the original unmodified app installed for payments and use the patched
app only for messaging. As a side effect, the Moat scan never runs on the modified client.

- **Target:** KakaoTalk 26.8.3 (APKM)
- **Enabled by default**

### Usage

Install [Morphe](https://morphe.software), add this source (link above), select KakaoTalk, and
patch. It can be combined with other KakaoTalk patch sources in Morphe.

---

## 한국어

패치된(재서명된) 카카오톡에서 카카오페이를 비활성화해 카카오페이 무결성 검사가 실행되지 않게 하는 [Morphe](https://morphe.software) 패치 하나예요. 전체 패치 모음이 아니라, 다른 카카오톡 패치 소스와 함께 쓰는 보조(companion) 패치예요.

### Block KakaoPay (카카오페이 차단)

카카오페이 화면이 열리는 즉시 닫고, 패치된(재서명된) 빌드에서 카카오페이 무결성 검사("Moat")가
실행되지 않게 막아요.

**이유:** 패치된 카카오톡은 공식 앱과 다른 키로 재서명돼 있어서, 그 위에서 카카오페이를 쓰면
무결성 검사에 걸릴 수 있고 실제 결제에 안전하지 않아요. 이 패치는 패치된 앱에서 카카오페이를 아예
열 수 없게 만들어요(송금/선물 링크로 열리는 화면 포함). 그래서 결제는 원본 앱으로 하고, 패치된
앱은 메신저 용도로만 쓰면 돼요. 그 결과 변조된 클라이언트에서 Moat 스캔도 실행되지 않아요.

- **대상:** 카카오톡 26.8.3 (APKM)
- **기본값: 켜짐**

### 사용법

[Morphe](https://morphe.software)를 설치하고, 이 소스를 추가(위 링크)한 뒤, 카카오톡을 선택해
패치하세요. Morphe의 다른 카카오톡 패치 소스와 함께 쓸 수 있어요.

---

## 🩹 Patches list

<!-- PATCHES_START EXPANDED -->
> **[v1.0.0](https://github.com/wonjoonSeol-WS/morphe-patches/releases/tag/v1.0.0)**&nbsp;&nbsp;•&nbsp;&nbsp;`main`&nbsp;&nbsp;•&nbsp;&nbsp;1 patches total
<details open>
<summary>📦 Kakao Talk&nbsp;&nbsp;•&nbsp;&nbsp;1 patch</summary>
<br>

**🎯 Supported versions:**

| 26.8.3 |
| :---: |

| 💊&nbsp;Patch | 📜&nbsp;Description | ⚙️&nbsp;Options |
|----------|----------------|-----------|
| [Block KakaoPay](#block-kakaopay) | Closes every KakaoPay screen the moment it opens and stops the KakaoPay Moat scan from running on this re-signed client. Pay on the original, unpatched app instead. |  |

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
