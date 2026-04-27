package com.hotel.planisphere.context;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.ViewportSize;

public class PlaywrightContext {

    public static final String BASE_URL = "https://hotel-example-site.takeyaqa.dev/ja/";

    private static final Playwright playwright;
    private static final Browser browser;

    static {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            browser.close();
            playwright.close();
        }));
    }

    private static BrowserContext context;
    private static Page page;
    private static Page popupPage;

    public static void newScenario() {
        context = browser.newContext();
        page = context.newPage();
        popupPage = null;
    }

    public static void newScenarioWithViewport(int width, int height) {
        if (context != null) context.close();
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(new ViewportSize(width, height)));
        page = context.newPage();
        popupPage = null;
    }

    public static void endScenario() {
        if (context != null) {
            context.close();
            context = null;
            page = null;
            popupPage = null;
        }
        ScenarioContext.clear();
    }

    public static Page page()               { return page; }
    public static BrowserContext context()  { return context; }
    public static Page popupPage()          { return popupPage; }
    public static void setPopupPage(Page p) { popupPage = p; }
    public static String baseUrl()          { return BASE_URL; }
}
