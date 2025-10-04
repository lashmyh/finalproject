package com.twoitesting.pages;

import com.twoitesting.utils.Helpers;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShopPage {

    private WebDriver driver;

    public ShopPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Locators

    @FindBy(css = "ul.products")
    private WebElement shopProducts;


    //Check user is at Shop page
    public boolean isAt() {
        try {
            WebElement products = Helpers.waitForElementToBeVisible(driver, shopProducts, 5);
            Helpers.scrollIntoView(driver, products);
            return products.isDisplayed();
        } catch (TimeoutException e) {
            return false; // shop products div wasn't found, so not in shop
        }
    }

    // Handle add to cart click
    public void clickAddToCart() {
        By addToCartLocator = By.cssSelector("a.add_to_cart_button");
        WebElement addBtn = Helpers.waitForElementToBeClickable(driver, addToCartLocator, 10);
        Helpers.scrollIntoView(driver, addBtn);

        try {
            addBtn.click();
        } catch (ElementClickInterceptedException e) {
            Helpers.javascriptClick(driver, addBtn);
        }
    }

    // Handle go to cart click
    public CartPage goToCart() {
        By cartBtnLocator = By.cssSelector("a.cart-contents");
        WebElement cartBtn = Helpers.waitForElementToBeClickable(driver, cartBtnLocator, 10);
        Helpers.scrollIntoView(driver, cartBtn);

        try {
            cartBtn.click();
        } catch (ElementClickInterceptedException e) {
            Helpers.javascriptClick(driver, cartBtn);
        }

        return new CartPage(driver);
    }


}
