package com.twoitesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderReceivedPage {

    private WebDriver driver;

    public OrderReceivedPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Locators
    @FindBy(css = "li.woocommerce-order-overview__order strong")
    private WebElement orderNumber;

    @FindBy(linkText = "My account")
    private WebElement myAccountLink;

    // Get order number of successful order
    public String getOrderNumber() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(orderNumber));
        return orderNumber.getText();
    }

    // Navigate to account page
    public MyAccountPage goToMyAccountPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(myAccountLink)).click();
        return new MyAccountPage(driver);
    }
}
