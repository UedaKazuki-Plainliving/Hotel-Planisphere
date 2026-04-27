package com.hotel.planisphere.page;

import static com.hotel.planisphere.context.PlaywrightContext.baseUrl;
import static com.hotel.planisphere.context.PlaywrightContext.page;

public class SignupPage {

    public static void navigate() {
        page().navigate(baseUrl() + "signup.html");
    }

    public static void fillEmail(String email) {
        page().fill("#email", email);
    }

    public static void fillPassword(String password) {
        page().fill("#password", password);
    }

    public static void fillPasswordConfirmation(String password) {
        page().fill("#password-confirmation", password);
    }

    public static void fillUsername(String name) {
        page().fill("#username", name);
    }

    public static void fillTel(String tel) {
        page().fill("#tel", tel);
    }

    public static void selectRank(String rankLabel) {
        if (rankLabel.contains("プレミアム")) {
            page().click("#rank-premium");
        } else {
            page().click("#rank-normal");
        }
    }

    public static void submit() {
        page().click("button[type='submit']");
    }

    public static boolean hasErrorContaining(String text) {
        int count = page().locator(".invalid-feedback").count();
        for (int i = 0; i < count; i++) {
            String feedback = page().locator(".invalid-feedback").nth(i).textContent();
            if (feedback != null && feedback.contains(text)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPasswordMismatchError() {
        String text = page().locator("#password-confirmation ~ .invalid-feedback").textContent();
        return text != null && !text.isBlank();
    }

    public static boolean hasRequiredFieldErrors() {
        int count = page().locator(".invalid-feedback").count();
        for (int i = 0; i < count; i++) {
            String text = page().locator(".invalid-feedback").nth(i).textContent();
            if (text != null && !text.isBlank()) {
                return true;
            }
        }
        return false;
    }
}
