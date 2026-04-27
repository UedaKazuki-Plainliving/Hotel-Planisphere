package com.hotel.planisphere.context;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Video;
import com.microsoft.playwright.options.ViewportSize;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PlaywrightContext {

    public static final String BASE_URL = "https://hotel-example-site.takeyaqa.dev/ja/";
    public static final Path VIDEO_DIR  = Paths.get("target/videos");

    private static final Playwright playwright;
    private static final Browser browser;

    static {
        playwright = Playwright.create();
        boolean headless = "true".equalsIgnoreCase(System.getenv("CI"))
                || "true".equalsIgnoreCase(System.getenv("PLAYWRIGHT_HEADLESS"));
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(headless)
                        .setSlowMo(headless ? 0 : 300));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            browser.close();
            playwright.close();
        }));
    }

    private static BrowserContext context;
    private static Page page;
    private static Page popupPage;

    public static void newScenario() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(VIDEO_DIR));
        page = context.newPage();
        popupPage = null;
    }

    public static void newScenarioWithViewport(int width, int height) {
        if (context != null) context.close();
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(new ViewportSize(width, height))
                .setRecordVideoDir(VIDEO_DIR));
        page = context.newPage();
        popupPage = null;
    }

    public static Video currentVideo() {
        try {
            return page != null ? page.video() : null;
        } catch (Exception e) {
            return null;
        }
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
