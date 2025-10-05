package com.twoitesting.basetest;

import com.twoitesting.pages.CartPage;
import com.twoitesting.pages.LoginPage;
import com.twoitesting.pages.MyAccountPage;
import com.twoitesting.utils.Helpers;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver;
    protected MyAccountPage myAccountPage;
    protected Properties config = new Properties(); // for user data and base url

    @BeforeEach
    void setUp() throws Exception {
        // Load properties
        Allure.step("Loading configuration properties...");
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            config.load(fis);
        }
        String browser = System.getProperty("browser", "chrome"); // defaults to chrome if no browser requested
        if (browser == null) {
            browser = "chrome";
            Allure.step("No browser specified, defaulting to Chrome");
        }
        browser = browser.trim().toLowerCase();

        switch(browser.toLowerCase()) {
            case "chrome" -> {driver = new ChromeDriver();}
            case "firefox" -> {driver = new FirefoxDriver();}
            default -> {
                System.out.println("Unknown browser: " + browser + ". Falling back to Chrome.");
                driver = new ChromeDriver();
            }
        }

        driver.manage().window().maximize(); //maximise window

        // Go to login page
        Allure.step("Navigating to base URL...");
        driver.get(config.getProperty("base.url"));

        // Perform login
        Allure.step("Logging in as test user...");
        LoginPage loginPage = new LoginPage(driver);
        myAccountPage = loginPage.loginAs(
                config.getProperty("username"),
                config.getProperty("password")
        );

        Helpers.captureScreenshot(driver, "After logging in");


        // Check correctly logged in
        if (!myAccountPage.isAt()) {
            throw new IllegalStateException("Login Failed. Testing has stopped.");
        }
        Allure.step("Login successful, navigating to Cart page...");

        // Clear cart of existing items and coupons
        driver.get(config.getProperty("cart.url"));

        CartPage cartPage = new CartPage(driver);

        cartPage.clearCart();
        Allure.step("Cart cleared successfully.");
    }

    @AfterEach
    void tearDown() {
        Allure.step("Starting teardown process...");
        try {
            // go to my account
            Allure.step("Navigating back to My Account page...");
            driver.get(config.getProperty("base.url"));

            // log out
            Allure.step("Logging out...");
            myAccountPage.logout();
            Allure.step("Logout successful.");
        } catch (Exception e) {
            Allure.step("Could not log out: " + e.getMessage());
        } finally {
            Allure.step("Closing browser...");
            driver.quit();
        }

    }

}
