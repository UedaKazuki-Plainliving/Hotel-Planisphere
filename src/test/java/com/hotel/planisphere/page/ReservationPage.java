package com.hotel.planisphere.page;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.hotel.planisphere.context.PlaywrightContext.popupPage;

public class ReservationPage {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static void fillDateTomorrow() {
        fillDate(LocalDate.now().plusDays(1).format(DATE_FORMAT));
    }

    public static void fillDatePast() {
        fillDate(LocalDate.now().minusDays(1).format(DATE_FORMAT));
    }

    public static void fillDateFourMonthsLater() {
        fillDate(LocalDate.now().plusMonths(4).format(DATE_FORMAT));
    }

    private static void fillDate(String date) {
        popupPage().fill("#date", date);
        popupPage().locator("#date").press("Tab");
    }

    public static void fillTerm(String term) {
        popupPage().fill("#term", term);
        popupPage().locator("#term").press("Tab");
    }

    public static void fillHeadCount(String count) {
        popupPage().fill("#head-count", count);
        popupPage().locator("#head-count").press("Tab");
    }

    public static void fillUsername(String name) {
        popupPage().fill("#username", name);
    }

    public static void checkBreakfast() {
        if (!popupPage().isChecked("#breakfast")) {
            popupPage().click("#breakfast");
        }
    }

    public static void checkEarlyCheckIn() {
        if (!popupPage().isChecked("#early-check-in")) {
            popupPage().click("#early-check-in");
        }
    }

    public static void checkSightseeing() {
        if (!popupPage().isChecked("#sightseeing")) {
            popupPage().click("#sightseeing");
        }
    }

    public static void selectContact(String label) {
        popupPage().selectOption("#contact", new SelectOption().setLabel(label));
    }

    public static int totalBillValue() {
        popupPage().waitForFunction(
                "document.getElementById('total-bill').textContent.trim() !== '-'");
        String text = popupPage().locator("#total-bill").textContent().trim();
        String digits = text.replaceAll("[^0-9]", "");
        return digits.isEmpty() ? 0 : Integer.parseInt(digits);
    }

    public static void submit() {
        popupPage().waitForFunction("!document.getElementById('submit-button').disabled");
        popupPage().waitForNavigation(() -> popupPage().click("#submit-button"));
        // Wait for confirm.html's JS to populate the guest name field
        popupPage().waitForSelector("#username:not(:empty)");
    }

    public static boolean isConfirmPageVisible() {
        return popupPage().url().contains("confirm.html");
    }

    public static boolean confirmPageContains(String text) {
        return popupPage().locator("body").textContent().contains(text);
    }

    public static boolean hasDateError() {
        String text = popupPage().locator("#date ~ .invalid-feedback").textContent();
        return text != null && !text.isBlank();
    }

    public static boolean hasUsernameError() {
        String text = popupPage().locator("#username ~ .invalid-feedback").textContent();
        return text != null && !text.isBlank();
    }

    public static boolean isDateInputVisible() {
        return popupPage().locator("#date").isVisible();
    }

    public static boolean isSubmitButtonVisible() {
        return popupPage().locator("#submit-button").isVisible();
    }

    public static void waitForDateInput(int timeoutMs) {
        popupPage().waitForSelector("#date",
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(timeoutMs));
    }
}
