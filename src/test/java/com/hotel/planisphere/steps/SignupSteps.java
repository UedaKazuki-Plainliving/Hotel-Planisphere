package com.hotel.planisphere.steps;

import com.hotel.planisphere.page.SignupPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupSteps {

    @When("確認用パスワードに {string} を入力する")
    public void fillPasswordConfirmation(String password) {
        SignupPage.fillPasswordConfirmation(password);
    }

    @When("電話番号に {string} を入力する")
    public void fillTel(String tel) {
        SignupPage.fillTel(tel);
    }

    @When("会員区分で {string} を選択する")
    public void selectRank(String rank) {
        SignupPage.selectRank(rank);
    }

    @When("登録ボタンをクリックする")
    public void clickSubmit() {
        SignupPage.submit();
    }

    @Then("{string} のエラーメッセージが表示される")
    public void assertErrorMessage(String expectedMessage) {
        assertTrue(SignupPage.hasErrorContaining(expectedMessage),
                "エラーメッセージが表示されていません: " + expectedMessage);
    }

    @Then("パスワード不一致のエラーメッセージが表示される")
    public void assertPasswordMismatchError() {
        assertTrue(SignupPage.hasPasswordMismatchError(),
                "パスワード不一致エラーが表示されていません");
    }

    @Then("入力必須のエラーメッセージが表示される")
    public void assertRequiredFieldErrors() {
        assertTrue(SignupPage.hasRequiredFieldErrors(),
                "入力必須エラーが表示されていません");
    }
}
