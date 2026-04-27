package com.hotel.planisphere.steps;

import com.hotel.planisphere.context.PlaywrightContext;
import com.hotel.planisphere.page.LoginPage;
import com.hotel.planisphere.page.MyPage;
import com.hotel.planisphere.page.SignupPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonSteps {

    @Given("会員登録ページを開いている")
    public void openSignupPage() {
        SignupPage.navigate();
    }

    @Given("ログインページを開いている")
    public void openLoginPage() {
        LoginPage.navigate();
    }

    @When("メールアドレスに {string} を入力する")
    public void fillEmail(String email) {
        PlaywrightContext.page().fill("#email", email);
    }

    @When("パスワードに {string} を入力する")
    public void fillPassword(String password) {
        PlaywrightContext.page().fill("#password", password);
    }

    @When("氏名に {string} を入力する")
    public void fillName(String name) {
        // Both signup (main page) and reservation (popup) use #username
        if (PlaywrightContext.popupPage() != null) {
            PlaywrightContext.popupPage().fill("#username", name);
        } else {
            PlaywrightContext.page().fill("#username", name);
        }
    }

    @Then("マイページに遷移する")
    public void assertOnMyPage() {
        PlaywrightContext.page().waitForURL("**/mypage.html");
    }

    @Then("トップページに遷移する")
    public void assertOnTopPage() {
        PlaywrightContext.page().waitForURL("**/index.html");
    }

    @Then("マイページに {string} と表示される")
    public void assertMyPageContains(String text) {
        assertTrue(MyPage.containsText(text),
                "マイページに「" + text + "」が見つかりません");
    }
}
