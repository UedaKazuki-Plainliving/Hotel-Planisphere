package com.hotel.planisphere.steps;

import com.hotel.planisphere.page.MyPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyPageSteps {

    @When("マイページを開く")
    public void openMyPage() {
        MyPage.navigate();
    }

    @Then("メールアドレスが表示されている")
    public void assertEmailDisplayed() {
        assertTrue(MyPage.isEmailDisplayed(), "メールアドレスが表示されていません");
    }

    @Then("氏名が表示されている")
    public void assertNameDisplayed() {
        assertTrue(MyPage.isUsernameDisplayed(), "氏名が表示されていません");
    }

    @Then("会員ランクが表示されている")
    public void assertRankDisplayed() {
        assertTrue(MyPage.isRankDisplayed(), "会員ランクが表示されていません");
    }
}
