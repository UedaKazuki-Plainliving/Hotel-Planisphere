package com.hotel.planisphere.page;

import com.hotel.planisphere.context.PlaywrightContext;
import com.microsoft.playwright.Page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationPageHelper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static String getTomorrowDate() {
        return LocalDate.now().plusDays(1).format(formatter);
    }

    public static String getYesterdayDate() {
        return LocalDate.now().minusDays(1).format(formatter);
    }

    public static String get3MonthsLaterDate() {
        return LocalDate.now().plusMonths(3).format(formatter);
    }

    public static String get4MonthsLaterDate() {
        return LocalDate.now().plusMonths(4).format(formatter);
    }

    public static int getTotalAmount() {
        Page page = PlaywrightContext.page();
        String text = page.locator(".total-amount").textContent();
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

    public static void fillCheckInDate(String date) {
        PlaywrightContext.page().fill("#checkin-date", date);
        PlaywrightContext.page().press("#checkin-date", "Tab");
    }

    public static void fillNights(String nights) {
        PlaywrightContext.page().fill("#nights", nights);
        PlaywrightContext.page().press("#nights", "Tab");
    }

    public static void fillHeadCount(String count) {
        PlaywrightContext.page().fill("#headcount", count);
        PlaywrightContext.page().press("#headcount", "Tab");
    }

    public static void fillGuestName(String name) {
        PlaywrightContext.page().fill("#guest-name", name);
    }

    public static void selectContact(String option) {
        if ("希望しない".equals(option)) {
            PlaywrightContext.page().click("input[name='contact'][value='no']");
        } else if ("希望する".equals(option)) {
            PlaywrightContext.page().click("input[name='contact'][value='yes']");
        }
    }

    public static void checkBreakfast() {
        PlaywrightContext.page().check("#breakfast-option");
    }

    public static void checkEarlyCheckIn() {
        PlaywrightContext.page().check("#checkin-option");
    }

    public static void checkSightseeing() {
        PlaywrightContext.page().check("#sightseeing-option");
    }

    public static void submit() {
        PlaywrightContext.page().click("button:has-text('予約する')");
    }

    public static boolean isConfirmDialogVisible() {
        return PlaywrightContext.page().locator(".confirm-dialog").isVisible();
    }

    public static boolean confirmDialogContains(String text) {
        return PlaywrightContext.page().locator(".confirm-dialog:has-text(\"" + text + "\")").isVisible();
    }

    public static boolean hasDateError() {
        return PlaywrightContext.page().locator("#checkin-date ~ .invalid-feedback").count() > 0;
    }

    public static boolean hasNameError() {
        return PlaywrightContext.page().locator("#guest-name ~ .invalid-feedback").count() > 0;
    }
}
