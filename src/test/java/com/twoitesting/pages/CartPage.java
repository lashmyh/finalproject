package com.twoitesting.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    @FindBy(id = "coupon_code")
    private WebElement couponInput;

    @FindBy(name = "apply_coupon")
    private WebElement applyCouponBtn;

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Scroll input into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", couponInput);
        // Clear input of existing text, then enter coupon code
        wait.until(ExpectedConditions.elementToBeClickable(couponInput)).clear();
        couponInput.sendKeys(couponCode);
        // Click apply button
        wait.until(ExpectedConditions.elementToBeClickable(applyCouponBtn)).click();

    }

    // Get order subtotal
    public int getSubtotal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //Wait until subtotal element is visible
        wait.until(ExpectedConditions.visibilityOf(subtotalElement));

        String text = subtotalElement.getText(); // "e.g. £22.50"
        String numeric = text.replaceAll("[^0-9.]", ""); // "22.50"
        double value = Double.parseDouble(numeric);
        return (int) Math.round(value * 100); // 2250, in pennies
    }

    // Get amount reduced by discount
    public int getDiscountValue() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait until discounted amount is visible
        wait.until(ExpectedConditions.visibilityOf(discountedAmount));

        String text = discountedAmount.getText(); // "e.g. £22.50"
        String numeric = text.replaceAll("[^0-9.]", ""); // "22.50"
        double value = Double.parseDouble(numeric);
        return (int) Math.round(value * 100); // 2250
    }

    // Navigate to account page
    public MyAccountPage goToMyAccountPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(myAccountLink)).click();
        return new MyAccountPage(driver);
    }

    // Handle checkout button
    public CheckoutPage goToCheckoutPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for loading overlay to be gone
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div.blockUI.blockOverlay")
        ));

        // Wait until place order button is clickable
        WebElement placeOrderBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.partialLinkText("Proceed to checkout"))
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutButton);
        checkoutButton.click();

        return new CheckoutPage(driver);
    }

    //Remove all existing coupons in the cart
    public void removeAllCoupons() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        while (!removeCouponButtons.isEmpty()) { // while there are existing coupons
            WebElement coupon = removeCouponButtons.get(0); //first coupon
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", coupon);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(coupon)).click();
            } catch (ElementClickInterceptedException e) {
                // Fallback to JS click if blocked
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", coupon);
            }
            wait.until(ExpectedConditions.stalenessOf(coupon));
            // Refresh list
            PageFactory.initElements(driver, this);
        }
    }


    // Remove all existing items from the cart
    public void removeAllItems() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        while (!removeItemButtons.isEmpty()) {
            WebElement item = removeItemButtons.get(0);
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", item);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(item)).click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", item);
            }
            wait.until(ExpectedConditions.stalenessOf(item));
            // Refresh list
            PageFactory.initElements(driver, this);
        }
    }


    // Clear cart completely
    public void clearCart() {
        removeAllCoupons();
        removeAllItems();
    }




}
