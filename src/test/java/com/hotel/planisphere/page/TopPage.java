package com.hotel.planisphere.page;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.hotel.planisphere.context.PlaywrightContext.baseUrl;
import static com.hotel.planisphere.context.PlaywrightContext.page;

public class TopPage {

    public static void navigate() {
        page().navigate(baseUrl());
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public static void waitForNavigationMenu(int timeoutMs) {
        page().waitForSelector("nav.navbar",
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(timeoutMs));
    }
}
