package com.playwright.tests;

import com.microsoft.playwright.*;
import com.playwright.base.BaseTest;
import com.playwright.utils.ExcelUtils;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SauceDemoTest extends BaseTest {
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/TestData.xlsx";
    private static final String SHEET_NAME = "TestData";

    @BeforeClass
    public void loadTestData() {
        System.out.println("Loading test data from: " + TEST_DATA_PATH);
        try {
            ExcelUtils.loadExcel(TEST_DATA_PATH, SHEET_NAME);
        } catch (IOException e) {
            System.err.println("Failed to load test data: " + e.getMessage());
            throw new RuntimeException("Failed to load test data", e);
        }
    }

    @Test(dataProvider = "testData")
    public void testLoginAndAddToCart(Map<String, String> data) {
        test = extent.createTest("Test: " + data.get("Test Case"));
        
        try {
            // Navigate to the website
            page.navigate(BASE_URL);
            test.info("Navigated to " + BASE_URL);
            
            // Login
            page.fill("#user-name", data.get("Username"));
            page.fill("#password", data.get("Password"));
            page.click("#login-button");
            test.info("Attempted login with username: " + data.get("Username"));
            
            // Wait for either success or error
            if ("Success".equals(data.get("Expected Result"))) {
                // Wait for inventory list to be visible (indicates successful login)
                page.waitForSelector(".inventory_list", new Page.WaitForSelectorOptions().setTimeout(5000));
                test.pass("Login successful");
                
                // Add to cart functionality
                if ("Add to Cart".equals(data.get("Test Case"))) {
                    test.info("Adding item to cart");
                    page.click(".btn_inventory");
                    page.click(".shopping_cart_link");
                    
                    // Verify item in cart
                    page.waitForSelector(".cart_item", new Page.WaitForSelectorOptions().setTimeout(5000));
                    test.pass("Item added to cart successfully");
                }
            } else {
                // Wait for error message
                page.waitForSelector("[data-test='error']", new Page.WaitForSelectorOptions().setTimeout(5000));
                String errorMessage = page.textContent("[data-test='error']");
                test.pass("Error message displayed as expected: " + errorMessage);
            }
        } catch (Exception e) {
            test.fail("Test failed with error: " + e.getMessage());
            throw e;
        }
    }

    @DataProvider(name = "testData")
    public Object[] getTestData() {
        try {
            List<Map<String, String>> testData = ExcelUtils.getTestData();
            return testData.toArray();
        } catch (Exception e) {
            System.err.println("Failed to get test data: " + e.getMessage());
            throw new RuntimeException("Failed to get test data", e);
        }
    }

    @AfterClass
    public void closeTestData() {
        try {
            ExcelUtils.closeExcel();
        } catch (IOException e) {
            System.err.println("Failed to close Excel file: " + e.getMessage());
        }
    }
} 