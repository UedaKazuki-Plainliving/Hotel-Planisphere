package com.hotel.planisphere.steps;

import com.hotel.planisphere.context.PlaywrightContext;
import com.hotel.planisphere.context.ScenarioContext;
import com.hotel.planisphere.data.TestData;
import com.hotel.planisphere.page.PlansPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlansSteps {

    @Given("ログインしていない状態でプラン一覧ページを開く")
    public void openPlansPageWithoutLogin() {
        PlansPage.navigate();
    }

    @Then("プランが1件以上表示される")
    public void assertAtLeastOnePlan() {
        assertTrue(PlansPage.planCount() > 0, "プランが1件も表示されていません");
    }

    @When("未ログイン時のプラン件数を記録する")
    public void recordPlanCountBeforeLogin() {
        ScenarioContext.set(TestData.Keys.PLAN_COUNT_BEFORE, PlansPage.planCount());
    }

    @Then("ログイン前よりプラン件数が増えている")
    public void assertPlanCountIncreased() {
        int before = ScenarioContext.get(TestData.Keys.PLAN_COUNT_BEFORE);
        int after = PlansPage.planCount();
        assertTrue(after > before,
                "ログイン後のプラン件数(" + after + ")がログイン前(" + before + ")より増えていません");
    }

    @When("最初のプランの予約ボタンをクリックする")
    public void clickFirstReserveButton() {
        PlansPage.clickFirstReserveButton();
    }

    @When("最初のプランの予約ボタンをクリックして予約ページを開く")
    public void clickFirstReserveButtonAndOpenPage() {
        PlansPage.clickFirstReserveButton();
    }

    @Then("予約ページが開く")
    public void assertReservationPageOpened() {
        assertNotNull(PlaywrightContext.popupPage(), "予約ページが開いていません");
        assertTrue(PlaywrightContext.popupPage().url().contains("reserve.html"),
                "予約ページのURLが正しくありません");
    }

    @Then("最初のプランの予約ボタンが表示されている")
    public void assertFirstReserveButtonVisible() {
        assertTrue(PlansPage.isFirstReserveButtonVisible(),
                "最初のプランの予約ボタンが表示されていません");
    }
}
