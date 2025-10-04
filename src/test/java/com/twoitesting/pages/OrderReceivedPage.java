package com.twoitesting.pages;

import com.twoitesting.utils.Helpers;
import org.openqa.selenium.ElementClickInterceptedException;
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
        WebElement order = Helpers.waitForElementToBeVisible(driver, orderNumber, 10);
        Helpers.scrollIntoView(driver, order);
        return order.getText();
    }

    // Navigate to account page
    public MyAccountPage goToMyAccountPage() {
        WebElement accountLink = Helpers.waitForElementToBeClickable(driver, myAccountLink, 5);
        Helpers.scrollIntoView(driver, accountLink);

        try {
            accountLink.click();
        } catch (ElementClickInterceptedException e) {
            Helpers.javascriptClick(driver, accountLink);
        }

        return new MyAccountPage(driver);
    }
}
