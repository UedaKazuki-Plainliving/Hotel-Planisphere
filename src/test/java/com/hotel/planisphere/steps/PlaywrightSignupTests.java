package com.hotel.planisphere.steps;

import com.hotel.planisphere.context.PlaywrightContext;
import com.hotel.planisphere.page.SignupPage;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("会員登録 - Playwright バリデーションテスト")
public class PlaywrightSignupTests {

    private Page page;

    @BeforeEach
    public void setUp() {
        page = PlaywrightContext.page();
        SignupPage.navigate();
    }

    @Test
    @DisplayName("UT-001: 正しいメールアドレス形式で登録できる")
    public void testValidEmailRegistration() {
        SignupPage.fillEmail("user001@example.com");
        SignupPage.fillPassword("password01");
        SignupPage.fillPasswordConfirmation("password01");
        SignupPage.fillUsername("テスト 太郎");
        SignupPage.submit();

        // マイページへ遷移を確認
        assertTrue(page.url().contains("mypage"), "マイページへ遷移していません");
        // エラーメッセージが表示されないことを確認
        assertEquals(0, page.locator(".invalid-feedback").count(), "エラーメッセージが表示されています");
    }

    @Test
    @DisplayName("UT-002: @なしのメールアドレスでエラー")
    public void testInvalidEmailWithoutAtMark() {
        SignupPage.fillEmail("not-an-email");
        SignupPage.fillPassword("password02");
        SignupPage.fillPasswordConfirmation("password02");
        SignupPage.fillUsername("テスト 太郎");
        SignupPage.submit();

        assertTrue(SignupPage.hasErrorContaining("@") || page.locator(".invalid-feedback").count() > 0,
                "メールアドレスエラーが表示されていません");
        assertTrue(page.url().contains("signup"), "ページが会員登録のままになっていません");
    }

    @Test
    @DisplayName("UT-003: パスワード7文字でエラー")
    public void testPasswordTooShort() {
        SignupPage.fillEmail("user003@example.com");
        SignupPage.fillPassword("1234567"); // 7文字
        SignupPage.fillPasswordConfirmation("1234567");
        SignupPage.fillUsername("テスト 太郎");
        SignupPage.submit();

        assertTrue(SignupPage.hasErrorContaining("8文字"),
                "パスワード最小文字数エラーが表示されていません");
    }

    @Test
    @DisplayName("UT-004: パスワード8文字で登録成功")
    public void testPasswordMinimumLength() {
        SignupPage.fillEmail("user004@example.com");
        SignupPage.fillPassword("12345678"); // 8文字
        SignupPage.fillPasswordConfirmation("12345678");
        SignupPage.fillUsername("テスト 太郎");
        SignupPage.submit();

        assertTrue(page.url().contains("mypage"), "マイページへ遷移していません");
    }

    @Test
    @DisplayName("UT-005: パスワード不一致でエラー")
    public void testPasswordMismatch() {
        SignupPage.fillEmail("user005@example.com");
        SignupPage.fillPassword("password05");
        SignupPage.fillPasswordConfirmation("different99");
        SignupPage.fillUsername("テスト 太郎");
        SignupPage.submit();

        assertTrue(SignupPage.hasPasswordMismatchError(),
                "パスワード不一致エラーが表示されていません");
    }

    @Test
    @DisplayName("UT-006: 電話番号10桁でエラー")
    public void testPhoneNumberTooShort() {
        SignupPage.fillEmail("user006@example.com");
        SignupPage.fillPassword("password06");
        SignupPage.fillPasswordConfirmation("password06");
        SignupPage.fillUsername("テスト 太郎");
        SignupPage.fillTel("0901234567"); // 10桁
        SignupPage.submit();

        assertTrue(SignupPage.hasErrorContaining("形式"),
                "電話番号形式エラーが表示されていません");
    }

    @Test
    @DisplayName("UT-007: 電話番号11桁で登録成功")
    public void testPhoneNumberValidLength() {
        SignupPage.fillEmail("user007@example.com");
        SignupPage.fillPassword("password07");
        SignupPage.fillPasswordConfirmation("password07");
        SignupPage.fillUsername("テスト 太郎");
        SignupPage.fillTel("09012345678"); // 11桁
        SignupPage.submit();

        assertTrue(page.url().contains("mypage"), "マイページへ遷移していません");
    }

    @Test
    @DisplayName("UT-008: 電話番号ハイフン入りでエラー")
    public void testPhoneNumberWithHyphen() {
        SignupPage.fillEmail("user008@example.com");
        SignupPage.fillPassword("password08");
        SignupPage.fillPasswordConfirmation("password08");
        SignupPage.fillUsername("テスト 太郎");
        SignupPage.fillTel("090-1234-5678");
        SignupPage.submit();

        assertTrue(page.locator(".invalid-feedback").count() > 0,
                "電話番号エラーが表示されていません");
    }

    @Test
    @DisplayName("UT-009: プレミアム会員選択で登録できる")
    public void testPremiumMembershipRegistration() {
        SignupPage.fillEmail("user009@example.com");
        SignupPage.fillPassword("password09");
        SignupPage.fillPasswordConfirmation("password09");
        SignupPage.fillUsername("テスト 太郎");
        SignupPage.selectRank("プレミアム会員");
        SignupPage.submit();

        assertTrue(page.url().contains("mypage"), "マイページへ遷移していません");
        // マイページに「プレミアム会員」の文字が表示されることを確認
        assertTrue(page.locator("text=プレミアム会員").isVisible(),
                "マイページにプレミアム会員が表示されていません");
    }

    @Test
    @DisplayName("DT-001: 全必須項目入力で登録成功")
    public void testAllRequiredFieldsRegistration() {
        SignupPage.fillEmail("dt001@example.com");
        SignupPage.fillPassword("password01");
        SignupPage.fillPasswordConfirmation("password01");
        SignupPage.fillUsername("テスト 太郎");
        SignupPage.submit();

        assertTrue(page.url().contains("mypage"), "マイページへ遷移していません");
    }

    @Test
    @DisplayName("DT-002: メールアドレス未入力でエラー")
    public void testMissingEmail() {
        SignupPage.fillPassword("password02");
        SignupPage.fillPasswordConfirmation("password02");
        SignupPage.fillUsername("テスト 太郎");
        SignupPage.submit();

        assertTrue(SignupPage.hasRequiredFieldErrors(),
                "必須項目エラーが表示されていません");
    }

    @Test
    @DisplayName("DT-003: パスワード未入力でエラー")
    public void testMissingPassword() {
        SignupPage.fillEmail("dt003@example.com");
        SignupPage.fillUsername("テスト 太郎");
        SignupPage.submit();

        assertTrue(SignupPage.hasRequiredFieldErrors(),
                "必須項目エラーが表示されていません");
    }

    @Test
    @DisplayName("DT-005: 氏名未入力でエラー")
    public void testMissingUsername() {
        SignupPage.fillEmail("dt005@example.com");
        SignupPage.fillPassword("password05");
        SignupPage.fillPasswordConfirmation("password05");
        SignupPage.submit();

        assertTrue(SignupPage.hasRequiredFieldErrors(),
                "必須項目エラーが表示されていません");
    }
}
