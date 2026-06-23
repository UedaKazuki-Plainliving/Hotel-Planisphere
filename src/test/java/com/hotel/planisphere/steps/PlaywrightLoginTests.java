package com.hotel.planisphere.steps;

import com.hotel.planisphere.context.PlaywrightContext;
import com.hotel.planisphere.page.LoginPage;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ログイン - Playwright バリデーションテスト")
public class PlaywrightLoginTests {

    private Page page;

    @BeforeEach
    public void setUp() {
        page = PlaywrightContext.page();
        LoginPage.navigate();
    }

    @Test
    @DisplayName("UT-010: 正しい認証情報でログイン成功")
    public void testValidLoginCredentials() {
        LoginPage.fillEmail("ichiro@example.com");
        LoginPage.fillPassword("password");
        LoginPage.submit();

        // マイページへ遷移を確認
        assertTrue(page.url().contains("mypage"), "マイページへ遷移していません");
        // エラーメッセージが表示されないことを確認
        assertEquals(0, page.locator(".invalid-feedback").count(), "エラーメッセージが表示されています");
    }

    @Test
    @DisplayName("UT-011: 誤ったパスワードでエラー")
    public void testInvalidPassword() {
        LoginPage.fillEmail("ichiro@example.com");
        LoginPage.fillPassword("wrongpassword");
        LoginPage.submit();

        assertTrue(page.locator(".invalid-feedback").count() > 0,
                "エラーメッセージが表示されていません");
        assertTrue(page.url().contains("login"), "ログインページのままになっていません");
    }

    @Test
    @DisplayName("UT-012: 未登録メールでエラー")
    public void testNonexistentEmail() {
        LoginPage.fillEmail("notexist@example.com");
        LoginPage.fillPassword("password");
        LoginPage.submit();

        assertTrue(page.locator(".invalid-feedback").count() > 0,
                "エラーメッセージが表示されていません");
        assertTrue(page.url().contains("login"), "ログインページのままになっていません");
    }

    @Test
    @DisplayName("UT-013: エラーメッセージが統一されている")
    public void testErrorMessageConsistency() {
        // パスワード誤りの場合
        LoginPage.fillEmail("ichiro@example.com");
        LoginPage.fillPassword("wrongpassword");
        LoginPage.submit();

        String errorMessage1 = page.locator(".invalid-feedback").first().textContent();

        // ページをリロード
        page.reload();

        // 未登録メールの場合
        LoginPage.fillEmail("notexist@example.com");
        LoginPage.fillPassword("password");
        LoginPage.submit();

        String errorMessage2 = page.locator(".invalid-feedback").first().textContent();

        // 両方のエラーメッセージが同じであることを確認
        assertEquals(errorMessage1, errorMessage2,
                "エラーメッセージが統一されていません");
    }
}
