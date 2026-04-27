package com.hotel.planisphere.steps;

import com.hotel.planisphere.context.PlaywrightContext;
import com.hotel.planisphere.data.TestData;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Video;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Hooks {

    @Before("not @responsive")
    public void setUp() {
        PlaywrightContext.newScenario();
    }

    @Before("@responsive")
    public void setUpResponsive() {
        PlaywrightContext.newScenarioWithViewport(
                TestData.MOBILE_VIEWPORT_WIDTH,
                TestData.MOBILE_VIEWPORT_HEIGHT);
    }

    @After
    public void tearDown(Scenario scenario) {
        takeScreenshot(scenario);

        // Get video reference before closing context (path is available after close)
        Video video = PlaywrightContext.currentVideo();

        PlaywrightContext.endScenario();

        renameVideo(video, scenario);
    }

    private void takeScreenshot(Scenario scenario) {
        try {
            Page activePage = PlaywrightContext.popupPage() != null
                    ? PlaywrightContext.popupPage()
                    : PlaywrightContext.page();
            if (activePage == null) return;

            byte[] png = activePage.screenshot(
                    new Page.ScreenshotOptions().setFullPage(true));

            // Attach to Cucumber HTML report
            scenario.attach(png, "image/png", scenario.getName());

            // Save to target/screenshots/<safe-name>.png
            Path dir = Path.of("target", "screenshots");
            Files.createDirectories(dir);
            String safeName = safeName(scenario.getName());
            Files.write(dir.resolve(safeName + ".png"), png);

        } catch (Exception e) {
            // Screenshot failure should not fail the scenario
        }
    }

    private void renameVideo(Video video, Scenario scenario) {
        if (video == null) return;
        try {
            Path src = video.path();  // blocks until video is saved
            if (src == null || !Files.exists(src)) return;
            String safeName = safeName(scenario.getName());
            Path dest = PlaywrightContext.VIDEO_DIR.resolve(safeName + ".webm");
            Files.createDirectories(PlaywrightContext.VIDEO_DIR);
            Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // Rename failure is non-critical
        }
    }

    private String safeName(String name) {
        // Replace only filesystem-unsafe characters; Japanese characters are kept as-is
        String safe = name.replaceAll("[/\\\\:*?\"<>|]", "_");
        return safe.length() > 80 ? safe.substring(0, 80) : safe;
    }
}
