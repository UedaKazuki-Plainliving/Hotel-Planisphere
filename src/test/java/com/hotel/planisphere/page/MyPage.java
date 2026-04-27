package com.hotel.planisphere.page;

import static com.hotel.planisphere.context.PlaywrightContext.baseUrl;
import static com.hotel.planisphere.context.PlaywrightContext.page;

public class MyPage {

    public static void navigate() {
        page().navigate(baseUrl() + "mypage.html");
        page().waitForSelector("#email:not(:empty)");
    }

    public static boolean isEmailDisplayed() {
        String text = page().locator("#email").textContent();
        return text != null && !text.isBlank();
    }

    public static boolean isUsernameDisplayed() {
        String text = page().locator("#username").textContent();
        return text != null && !text.isBlank();
    }

    public static boolean isRankDisplayed() {
        String text = page().locator("#rank").textContent();
        return text != null && !text.isBlank();
    }

    public static boolean containsText(String text) {
        // Wait for JS-rendered content to include the target text
        page().waitForSelector("#email:not(:empty)");
        return page().locator("body").textContent().contains(text);
    }

    public static void clickLogout() {
        page().click("#logout-form button[type='submit']");
    }
}
