package com.hotel.planisphere.steps;

import com.hotel.planisphere.context.PlaywrightContext;
import com.hotel.planisphere.data.TestData;
import com.hotel.planisphere.page.PlansPage;
import com.hotel.planisphere.page.ReservationPage;
import com.hotel.planisphere.page.TopPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NonFunctionalSteps {

    @Given("トップページを開いている")
    public void openTopPage() {
        TopPage.navigate();
    }

    @Given("モバイル画面幅でブラウザを開いている")
    public void mobileViewportIsActive() {
        // Viewport (375x667) is configured by @Before("@responsive") in Hooks
    }

    @Then("3秒以内にナビゲーションメニューが表示される")
    public void assertNavMenuLoadsWithinThreeSeconds() {
        TopPage.waitForNavigationMenu(TestData.PERFORMANCE_TIMEOUT_MS);
    }

    @Then("3秒以内にプランが1件以上表示される")
    public void assertPlansLoadWithinThreeSeconds() {
        PlansPage.waitForPlans(TestData.PERFORMANCE_TIMEOUT_MS);
        assertTrue(PlansPage.planCount() > 0, "3秒以内にプランが表示されませんでした");
    }

    @Then("3秒以内に宿泊日の入力欄が表示される")
    public void assertDateInputLoadsWithinThreeSeconds() {
        ReservationPage.waitForDateInput(TestData.PERFORMANCE_TIMEOUT_MS);
    }

    @Then("ページのURLが {string} で始まっている")
    public void assertUrlStartsWith(String prefix) {
        String url = PlaywrightContext.page().url();
        assertTrue(url.startsWith(prefix),
                "URLが「" + prefix + "」で始まっていません: " + url);
    }
}
