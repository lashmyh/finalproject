package com.twoitesting.pages;

import com.twoitesting.utils.Helpers;
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

    @FindBy(id = "place_order")
    private WebElement placeOrderBtn;

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

    // Avoided lazy loading for dynamic elements
    public OrderReceivedPage placeOrder() {
        WebElement btn = Helpers.waitForElementToBeClickable(driver, placeOrderBtn, 10);
//        Helpers.scrollIntoView(driver, btn);

        try {
            btn.click();
        } catch (ElementClickInterceptedException e) {
            // Fallback to JS click
            Helpers.javascriptClick(driver, btn);
        }

        return new OrderReceivedPage(driver);
    }


}
