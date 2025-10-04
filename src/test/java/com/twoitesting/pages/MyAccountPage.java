package com.twoitesting.pages;

import com.twoitesting.utils.Helpers;
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

    @FindBy(linkText = "Logout")
    private WebElement logoutLink;

    @FindBy(linkText = "Orders")
    private WebElement ordersLink;


    // Logged in status helper method
    public boolean isAt() {
        try {
            WebElement contentElement = Helpers.waitForElementToBeVisible(driver, entryContent, 5);
            return contentElement.isDisplayed();
        } catch (TimeoutException e) {
            return false; // div wasn't found, so not logged in
        }
    }

    // Navigate to shop page
    // dynamic due to re-render of DOM when cart is empty
    public ShopPage goToShop() {

        By shopBtnLocator = By.linkText("Shop");
        WebElement shop = Helpers.waitForElementToBeClickable(driver, shopBtnLocator, 10);
        // scroll into view
        Helpers.scrollIntoView(driver, shop);
        try {
            shop.click();
        } catch (ElementClickInterceptedException e) {
            // Fallback: use helper to JS click
            Helpers.javascriptClick(driver, shop);
        }
        return new ShopPage(driver);
    }

    // Logout
    public void logout() {
        WebElement logout = Helpers.waitForElementToBeClickable(driver, logoutLink, 5);
        // Scroll into view
        Helpers.scrollIntoView(driver, logout);
        try {
            logout.click();
        } catch (ElementClickInterceptedException e) {
            // Fallback: click via JavaScript
            Helpers.javascriptClick(driver, logout);
        }
    }

    // Navigate to Orders option in account sidebar
    public OrderHistoryPage viewOrders() {
        WebElement orders = Helpers.waitForElementToBeClickable(driver, ordersLink, 10);
        // Scroll it into view
        Helpers.scrollIntoView(driver, orders);
        try {
            orders.click();
        } catch (ElementClickInterceptedException e) {
            Helpers.javascriptClick(driver, orders);
        }
        return new OrderHistoryPage(driver);
    }






}
