package com.hotel.planisphere.page;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.hotel.planisphere.context.PlaywrightContext.baseUrl;
import static com.hotel.planisphere.context.PlaywrightContext.page;
import static com.hotel.planisphere.context.PlaywrightContext.setPopupPage;

public class PlansPage {

    public static void navigate() {
        page().navigate(baseUrl() + "plans.html");
        page().waitForLoadState(LoadState.NETWORKIDLE);
    }

    public static void waitForLoaded() {
        page().waitForSelector("a[href*='reserve.html']");
    }

    public static void waitForPlans(int timeoutMs) {
        page().waitForSelector("a[href*='reserve.html']",
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(timeoutMs));
    }

    public static int planCount() {
        page().waitForSelector("a[href*='reserve.html']");
        return page().locator("a[href*='reserve.html']").count();
    }

    public static void clickFirstReserveButton() {
        Page popup = page().waitForPopup(() ->
                page().locator("a[href*='reserve.html']").first().click());
        popup.waitForLoadState(LoadState.DOMCONTENTLOADED);
        setPopupPage(popup);
    }

    public static boolean isFirstReserveButtonVisible() {
        return page().locator("a[href*='reserve.html']").first().isVisible();
    }
}
