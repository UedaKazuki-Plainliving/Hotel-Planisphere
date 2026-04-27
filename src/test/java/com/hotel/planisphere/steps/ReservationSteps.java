package com.hotel.planisphere.steps;

import com.hotel.planisphere.context.ScenarioContext;
import com.hotel.planisphere.data.TestData;
import com.hotel.planisphere.page.ReservationPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationSteps {

    @When("宿泊日に明日の日付を入力する")
    public void fillDateTomorrow() {
        ReservationPage.fillDateTomorrow();
    }

    @When("宿泊日に過去の日付を入力する")
    public void fillDatePast() {
        ReservationPage.fillDatePast();
    }

    @When("宿泊日に4ヶ月後の日付を入力する")
    public void fillDateFourMonthsLater() {
        ReservationPage.fillDateFourMonthsLater();
    }

    @When("宿泊数に {string} を入力する")
    public void fillTerm(String term) {
        ReservationPage.fillTerm(term);
    }

    @When("宿泊数を {string} に変更する")
    public void changeTerm(String term) {
        ScenarioContext.set(TestData.Keys.TOTAL_BEFORE, ReservationPage.totalBillValue());
        ReservationPage.fillTerm(term);
    }

    @When("人数に {string} を入力する")
    public void fillHeadCount(String count) {
        ReservationPage.fillHeadCount(count);
    }

    @When("人数を {string} に変更する")
    public void changeHeadCount(String count) {
        ScenarioContext.set(TestData.Keys.TOTAL_BEFORE, ReservationPage.totalBillValue());
        ReservationPage.fillHeadCount(count);
    }

    @And("オプションを選択する前の合計金額を記録する")
    public void recordTotalBeforeOption() {
        ScenarioContext.set(TestData.Keys.TOTAL_BEFORE, ReservationPage.totalBillValue());
    }

    @And("宿泊数を変更する前の合計金額を記録する")
    public void recordTotalBeforeTermChange() {
        ScenarioContext.set(TestData.Keys.TOTAL_BEFORE, ReservationPage.totalBillValue());
    }

    @And("朝食バイキングのオプションを選択する")
    public void selectBreakfast() {
        ReservationPage.checkBreakfast();
    }

    @And("昼からチェックインのオプションを選択する")
    public void selectEarlyCheckIn() {
        ReservationPage.checkEarlyCheckIn();
    }

    @And("お得な観光プランのオプションを選択する")
    public void selectSightseeing() {
        ReservationPage.checkSightseeing();
    }

    @And("確認連絡で {string} を選択する")
    public void selectContact(String option) {
        ReservationPage.selectContact(option);
    }

    @And("予約するボタンをクリックする")
    public void clickSubmit() {
        ReservationPage.submit();
    }

    @Then("合計金額が変更前より高くなっている")
    public void assertTotalIncreased() {
        int before = ScenarioContext.get(TestData.Keys.TOTAL_BEFORE);
        int after = ReservationPage.totalBillValue();
        assertTrue(after > before,
                "合計金額が変更前(" + before + "円)より高くなっていません: " + after + "円");
    }

    @Then("合計金額が選択前より高くなっている")
    public void assertTotalIncreasedAfterOption() {
        int before = ScenarioContext.get(TestData.Keys.TOTAL_BEFORE);
        int after = ReservationPage.totalBillValue();
        assertTrue(after > before,
                "合計金額が選択前(" + before + "円)より高くなっていません: " + after + "円");
    }

    @Then("予約確認ダイアログが表示される")
    public void assertConfirmPageVisible() {
        assertTrue(ReservationPage.isConfirmPageVisible(),
                "予約確認ページが表示されていません");
    }

    @Then("予約確認ダイアログに {string} が表示される")
    public void assertConfirmPageContains(String text) {
        assertTrue(ReservationPage.confirmPageContains(text),
                "予約確認ページに「" + text + "」が表示されていません");
    }

    @Then("宿泊日のエラーメッセージが表示される")
    public void assertDateError() {
        assertTrue(ReservationPage.hasDateError(),
                "宿泊日のエラーメッセージが表示されていません");
    }

    @Then("氏名のエラーメッセージが表示される")
    public void assertUsernameError() {
        assertTrue(ReservationPage.hasUsernameError(),
                "氏名のエラーメッセージが表示されていません");
    }

    @Then("宿泊日の入力欄が表示されている")
    public void assertDateInputVisible() {
        assertTrue(ReservationPage.isDateInputVisible(),
                "宿泊日の入力欄が表示されていません");
    }

    @Then("予約するボタンが表示されている")
    public void assertSubmitButtonVisible() {
        assertTrue(ReservationPage.isSubmitButtonVisible(),
                "予約するボタンが表示されていません");
    }
}
