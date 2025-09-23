package com.twoitesting.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;


public class MyAccountPage {
    private WebDriver driver;

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Locators
    @FindBy(css = "div.entry-content")
    private WebElement entryContent;

    @FindBy(linkText = "Shop")
    private WebElement shopLink;

    @FindBy(linkText = "Logout")
    private WebElement logoutLink;

    @FindBy(linkText = "Orders")
    private WebElement ordersLink;

    @FindBy(css = "a[href*='view-order']")
    private WebElement latestOrderLink;

    // Logged in status helper method
    public boolean isAt() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOf(entryContent));
            return entryContent.isDisplayed();
        } catch (TimeoutException e) {
            return false; // div wasn't found, so not logged in
        }
    }

    // Navigate to shop page
    public ShopPage goToShop() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(shopLink)).click();
        return new ShopPage(driver);
    }

    // Logout
    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(logoutLink));
        // Scroll it into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", logoutLink);
        logoutLink.click();
    }

    // Navigate to Orders option in account sidebar
    public void viewOrders() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(ordersLink));
        // Scroll it into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ordersLink);
        ordersLink.click();

    }

    // Get first entry from My Orders
    public String getLatestOrderNumber() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(latestOrderLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", latestOrderLink);
        return latestOrderLink.getText().replace("#", "").trim();
    }





}
