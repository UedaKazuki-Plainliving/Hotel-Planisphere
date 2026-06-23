package com.hotel.planisphere.page;

import static com.hotel.planisphere.context.PlaywrightContext.baseUrl;
import static com.hotel.planisphere.context.PlaywrightContext.page;

public class LoginPage {

    public static void navigate() {
        page().navigate(baseUrl() + "login.html");
    }

    public static void fillEmail(String email) {
        page().fill("#email", email);
    }

    public static void fillPassword(String password) {
        page().fill("#password", password);
    }

    public static void submit() {
        page().click("button[type='submit']");
    }

    public static boolean hasErrorMessage() {
        return page().locator(".invalid-feedback").count() > 0;
    }

    public static String getErrorMessage() {
        if (hasErrorMessage()) {
            return page().locator(".invalid-feedback").first().textContent();
        }
        return "";
    }
}
