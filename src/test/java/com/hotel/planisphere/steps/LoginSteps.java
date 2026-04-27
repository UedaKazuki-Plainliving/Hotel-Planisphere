package com.hotel.planisphere.steps;

import com.hotel.planisphere.context.PlaywrightContext;
import com.hotel.planisphere.context.ScenarioContext;
import com.hotel.planisphere.data.TestData;
import com.hotel.planisphere.page.LoginPage;
import com.hotel.planisphere.page.MyPage;
import com.hotel.planisphere.page.PlansPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    @When("ログインボタンをクリックする")
    public void clickLoginButton() {
        LoginPage.submit();
    }

    @When("マイページでログアウトボタンをクリックする")
    public void clickLogout() {
        MyPage.clickLogout();
    }

    @Then("ログインエラーメッセージが表示される")
    public void assertLoginError() {
        assertTrue(LoginPage.hasLoginError(),
                "ログインエラーメッセージが表示されていません");
    }

    @Given("{string} でログインしている")
    public void loginAs(String email) {
        LoginPage.navigate();
        LoginPage.fillEmail(email);
        LoginPage.fillPassword(TestData.PremiumUser.PASSWORD);
        LoginPage.submit();
        PlaywrightContext.page().waitForURL("**/mypage.html");
        ScenarioContext.set(TestData.Keys.LOGGED_IN_EMAIL, email);
    }

    @When("{string} でログインしてプラン一覧を開く")
    public void loginAndOpenPlans(String email) {
        LoginPage.navigate();
        LoginPage.fillEmail(email);
        LoginPage.fillPassword(TestData.PremiumUser.PASSWORD);
        LoginPage.submit();
        PlaywrightContext.page().waitForURL("**/mypage.html");
        PlansPage.navigate();
    }
}
