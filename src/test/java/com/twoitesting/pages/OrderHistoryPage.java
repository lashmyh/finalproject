package com.twoitesting.pages;

import com.twoitesting.utils.Helpers;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderHistoryPage {

    private WebDriver driver;

    public OrderHistoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // helper for POM creation assistance

    }

    // Locators

    @FindBy(css = "a[href*='view-order']")
    private WebElement latestOrderLink;

    // Get first entry from My Orders
    public String getLatestOrderNumber() {
        WebElement latestOrder = Helpers.waitForElementToBeVisible(driver, latestOrderLink, 10);
//        Helpers.scrollIntoView(driver, latestOrder);
        // Get text and clean it
        return latestOrder.getText().replace("#", "").trim();
    }

}
