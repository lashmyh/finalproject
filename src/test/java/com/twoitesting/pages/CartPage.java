package com.twoitesting.pages;

import com.twoitesting.utils.Helpers;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public class CartPage {

    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Locators

    @FindBy(xpath = "//h1[text()='Cart']")
    public WebElement cartHeader;

    @FindBy(css = "tr.cart-subtotal .woocommerce-Price-amount bdi")
    private WebElement subtotalElement;

    @FindBy(css = "tr.cart-discount .woocommerce-Price-amount")
    private WebElement discountedAmount;

    @FindBy(linkText = "My account")
    private WebElement myAccountLink;

    @FindBy(linkText = "Proceed to checkout")
    private WebElement checkoutButton;

    @FindBy(css = "a.woocommerce-remove-coupon")
    private List<WebElement> removeCouponButtons;

    @FindBy(css = "a.remove")
    private List<WebElement> removeItemButtons;

    @FindBy(css = ".cart-empty")
    private WebElement emptyCartMessage;

    // Check whether user is on Cart Page
    public boolean isAt() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOf(cartHeader));
            return cartHeader.isDisplayed();
        } catch (TimeoutException e) {
            return false; // div wasn't found, so not logged in
        }
    }

    // Apply coupon code
    public void applyCoupon(String couponCode) {

        By couponInputLocator = By.id("coupon_code");
        By applyCouponBtnLocator = By.name("apply_coupon");

        WebElement input = Helpers.waitForElementToBeClickable(driver, couponInputLocator, 10);
//        Helpers.scrollIntoView(driver, input);
        input.clear();
        input.sendKeys(couponCode);

        WebElement applyBtn = Helpers.waitForElementToBeClickable(driver, applyCouponBtnLocator, 10);
        try {
            applyBtn.click();
        } catch (ElementClickInterceptedException e) {
            // fallback to JS click
            Helpers.javascriptClick(driver, applyBtn);
        }
    }

    // Get order subtotal in pennies
    public int getSubtotal() {
        WebElement subtotal = Helpers.waitForElementToBeVisible(driver, subtotalElement, 10);
//        Helpers.scrollIntoView(driver, subtotal);
        String text = subtotal.getText(); // e.g. £22.50
        String numeric = text.replaceAll("[^0-9.]", ""); // "22.50"

        // Split into pounds and pennies
        BigDecimal value = new BigDecimal(numeric);
        BigDecimal pennies = value.movePointRight(2); // shift decimal 2 places = pennies
        return pennies.intValueExact(); // throws if too big for int
    }


    // Get amount reduced by discount
    public int getDiscountValue() {
        WebElement discount = Helpers.waitForElementToBeVisible(driver, discountedAmount, 10);
//        Helpers.scrollIntoView(driver, discount);
        String text = discount.getText(); // "e.g. £22.50"
        String numeric = text.replaceAll("[^0-9.]", ""); // "22.50"

        // Convert to pennies
        BigDecimal value = new BigDecimal(numeric);
        BigDecimal pennies = value.movePointRight(2); // shift decimal 2 places
        return pennies.intValueExact();
    }

    // Navigate to account page
    public MyAccountPage goToMyAccountPage() {
        WebElement account = Helpers.waitForElementToBeClickable(driver, myAccountLink, 10);

        // Scroll into view
//        Helpers.scrollIntoView(driver, account);
        try {
            account.click();
        } catch (ElementClickInterceptedException e) {
            // fallback to JS click
            Helpers.javascriptClick(driver, account);
        }

        return new MyAccountPage(driver);
    }

    // Handle checkout button
    public CheckoutPage goToCheckoutPage() {

        WebElement checkout = Helpers.waitForElementToBeClickable(driver, checkoutButton, 10);
//        Helpers.scrollIntoView(driver, checkout);

        try {
            checkout.click();
        } catch (ElementClickInterceptedException e) {
            Helpers.javascriptClick(driver, checkout);
        }
        return new CheckoutPage(driver);
    }


    // Remove all existing coupons in the cart
    public void removeAllCoupons() {
        while (!removeCouponButtons.isEmpty()) { // while there are existing coupons
            WebElement coupon = removeCouponButtons.get(0); // first coupon
            WebElement clickableCoupon = Helpers.waitForElementToBeClickable(driver, coupon, 10);
//            Helpers.scrollIntoView(driver, clickableCoupon);

            try {
                clickableCoupon.click();
            } catch (ElementClickInterceptedException e) {
                Helpers.javascriptClick(driver, clickableCoupon);
            }
            // Wait until the coupon button is removed from DOM
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.stalenessOf(coupon));

        }
    }



    // Remove all existing items from the cart
    public void removeAllItems() {
        while (!removeItemButtons.isEmpty()) {
            WebElement item = removeItemButtons.get(0); // first item

            // Wait until item is clickable
            WebElement clickableItem = Helpers.waitForElementToBeClickable(driver, item, 10);

//            Helpers.scrollIntoView(driver, clickableItem);

            try {
                clickableItem.click();
            } catch (ElementClickInterceptedException e) {
                Helpers.javascriptClick(driver, clickableItem);
            }

            // wait until item removed from DOM
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.stalenessOf(item));

        }
    }

    // Clear cart completely
    public void clearCart() {
        removeAllCoupons();
        removeAllItems();
    }




}
