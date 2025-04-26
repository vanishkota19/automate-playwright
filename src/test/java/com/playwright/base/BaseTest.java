package com.playwright.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.nio.file.Paths;

public class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;
    protected static ExtentReports extent;
    protected ExtentTest test;

    @BeforeClass
    public void setUp() {
        // Initialize Extent Reports
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        // Initialize Playwright
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(1000));
        context = browser.newContext();
        page = context.newPage();
        
        // Set viewport size
        page.setViewportSize(1920, 1080);
    }

    @AfterClass
    public void tearDown() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
        if (extent != null) {
            extent.flush();
        }
    }
} 