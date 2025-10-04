package com.twoitesting.basetest;

import com.twoitesting.pages.CartPage;
import com.twoitesting.pages.LoginPage;
import com.twoitesting.pages.MyAccountPage;
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
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            config.load(fis);
        }
        String browser = System.getProperty("browser", "chrome"); // defaults to chrome if no browser requested
        if (browser == null) {
            browser = "chrome";
            System.out.println("No browser specified: using Chrome as default");
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
        System.out.println("Going to base URL...");
        driver.get(config.getProperty("base.url"));

        // Perform login
        System.out.println("On login page");
        LoginPage loginPage = new LoginPage(driver);
        myAccountPage = loginPage.loginAs(
                config.getProperty("username"),
                config.getProperty("password")
        );


        // Check correctly logged in
        if (!myAccountPage.isAt()) {
            throw new IllegalStateException("Login Failed. Testing has stopped.");
        }
        System.out.println("Logged in");

        System.out.println("Going to cart page");
        // Clear cart of existing items and coupons
        driver.get(config.getProperty("cart.url"));

        CartPage cartPage = new CartPage(driver);
        System.out.println("On cart page");

        cartPage.clearCart();
        System.out.println("Cart cleared");
        System.out.println("--------------------Base test passed -------------------");

    }

    @AfterEach
    void tearDown() {

        try {
            // go to my account
            driver.get(config.getProperty("base.url"));

            // log out
            myAccountPage.logout();
        } catch (Exception e) {
            System.out.println("Could not log out: " + e.getMessage());
        } finally {
            driver.quit();
        }

    }

}
