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
        page().click("#login-button");
    }

    public static boolean hasLoginError() {
        String msg = page().locator("#email-message").textContent();
        return msg != null && !msg.isBlank();
    }

    public static String currentUrl() {
        return page().url();
    }
}
