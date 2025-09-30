package com.twoitesting.pages;

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(latestOrderLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", latestOrderLink);
        return latestOrderLink.getText().replace("#", "").trim();
    }
}
