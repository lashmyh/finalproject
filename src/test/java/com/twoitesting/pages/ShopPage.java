package com.twoitesting.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    @FindBy(css = "a.add_to_cart_button") //first add to cart element
    private WebElement addToCartBtn;

    @FindBy(css = "a.cart-contents") //first add to cart element
    private WebElement viewCartBtn;

    //Check user is at Shop page
    public boolean isAt() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOf(shopProducts));
            return shopProducts.isDisplayed();
        } catch (TimeoutException e) {
            return false; // shop products div wasn't found, so not in shop
        }
    }

    // Handle add to cart click
    public void clickAddToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartBtn);
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();

    }

    // Handle go to cart click
    public CartPage goToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(viewCartBtn)).click();
        return new CartPage(driver);
    }


}
