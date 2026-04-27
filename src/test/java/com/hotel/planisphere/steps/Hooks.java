package com.hotel.planisphere.steps;

import com.hotel.planisphere.context.PlaywrightContext;
import com.hotel.planisphere.data.TestData;
import io.cucumber.java.After;
import io.cucumber.java.Before;

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
    public void tearDown() {
        PlaywrightContext.endScenario();
    }
}
