package com.twoitesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // helper for POM creation assistance

    }

    //Locators
    @FindBy(id = "username")
    public WebElement usernameField;

    @FindBy(id = "password")
    public WebElement passwordField;

    @FindBy(css = ".woocommerce-button.button.woocommerce-form-login__submit")
    public WebElement submitButton;

    @FindBy(css = "form.woocommerce-form-login")
    public WebElement loginForm;

    @FindBy(css = "ul.woocommerce-error")
    private WebElement loginErrorMessage; //only appears if failure to log in


    // Login helper methods

    //set username
    public LoginPage setUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
        return this;
    }

    //set password
    public LoginPage setPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    //submit login form
    public void submitForm() {
        submitButton.click();
    }

    // Attempt login
    public MyAccountPage loginAs(String username, String password) {
        setUsername(username);
        setPassword(password);
        submitForm();
        return new MyAccountPage(driver);
    }

    public Boolean isErrorDisplayed() {
        try {
            return loginErrorMessage.isDisplayed();
        } catch (Exception e) {
            return false; //element not present
        }
    }

    public String getErrorMessage() {
        try {
            return loginErrorMessage.getText();
        }
        catch (Exception e) {
            return "";
        }
    }




}
