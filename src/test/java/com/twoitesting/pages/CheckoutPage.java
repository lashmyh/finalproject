package com.twoitesting.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {

    private WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // helper for POM creation assistance

    }

    //Locators
    @FindBy(id = "billing_first_name")
    private WebElement firstNameInput; //check this

    @FindBy(id = "billing_last_name")
    private WebElement lastNameInput; //check this

    @FindBy(id = "billing_country")
    private WebElement countryDropdown; //check this

    @FindBy(id = "billing_address_1")
    private WebElement streetInput; //check this

    @FindBy(id = "billing_city")
    private WebElement cityInput; //check this

    @FindBy(id = "billing_postcode")
    private WebElement postcodeInput; //check this

    @FindBy(id = "billing_phone")
    private WebElement phoneInput; //check this

    @FindBy(id = "billing_email")
    private WebElement emailInput; //check this

    @FindBy(css = "label[for='payment_method_cheque']")
    private WebElement checkPaymentsOption;

    @FindBy(id = "place_order")
    private WebElement placeOrderBtn; //check this

    public void fillBillingForm(String firstName, String lastName, String countryName, String streetName,
    String cityName,String postcode, String phoneNumber, String emailAddress) {

        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);

        // For country dropdown
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText(countryName);

        streetInput.clear();
        streetInput.sendKeys(streetName);

        cityInput.clear();
        cityInput.sendKeys(cityName);

        postcodeInput.clear();
        postcodeInput.sendKeys(postcode);

        phoneInput.clear();
        phoneInput.sendKeys(phoneNumber);

        emailInput.clear();
        emailInput.sendKeys(emailAddress);
    }

    public void setSelectCheckPayments() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(checkPaymentsOption));
        // Scroll it into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkPaymentsOption);
        checkPaymentsOption.click();
    }

    // Avoided lazy loading for dynamic elements
    public OrderReceivedPage placeOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for loading overlay to be gone
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div.blockUI.blockOverlay")
        ));

        // Wait until place order button is clickable
        WebElement placeOrderBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("place_order"))
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeOrderBtn);
        placeOrderBtn.click();

        return new OrderReceivedPage(driver);
    }


}
