package com.twoitesting;

import com.twoitesting.basetest.BaseTest;
import com.twoitesting.pages.*;
import com.twoitesting.utils.Helpers;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("E-commerce Functionality")
public class Tests extends BaseTest {


    //test case 1 : user can add item to cart, go to cart and apply correct discount with coupon code
    @Story("Coupon Functionality")
    @DisplayName("Apply Coupon and Verify Discount")
    @Description("Verify user can add item to cart, go to cart, and apply a correct discount using a coupon code.")
    @ParameterizedTest(name = "Coupon {0} should apply a {1}% discount")
    @CsvFileSource(resources = "/coupons.csv", numLinesToSkip = 1)
    void couponTest(String couponCode, int discount) {
        String productName = "Beanie";
        Allure.step("Navigating to Shop page...");
        ShopPage shopPage = myAccountPage.goToShop();
        assertTrue(shopPage.isAt(), "Did not reach shop page as expected.");

        // Click add to cart btn of first product
        Allure.step("Adding first available item to cart...");
        shopPage.clickAddToCart(productName);


        // Go to cart
        Allure.step("Navigating to Cart page...");
        CartPage cartPage = shopPage.goToCart();
        assertTrue(cartPage.isAt(), "Did not reach cart page as expected.");


        //Apply coupon code from csv file
        Allure.step("Applying coupon: " + couponCode);
        cartPage.applyCoupon(couponCode);
        Allure.addAttachment("Coupon Code", couponCode);


        //Validate discount
        Allure.step("Validating discount...");
        int subtotal = cartPage.getSubtotal();          // in pennies
        int discountedAmount = cartPage.getDiscountValue(); // in pennies
        int expectedDiscount = (int) Math.round((subtotal * discount) / 100.0);

        Allure.addAttachment("Subtotal", String.valueOf(subtotal));
        Allure.addAttachment("Expected Discount", String.valueOf(expectedDiscount));
        Allure.addAttachment("Actual Discount", String.valueOf(discountedAmount));

        assertEquals(expectedDiscount, discountedAmount, "Incorrect amount discounted");

    }

    //test case 2: user can purchase an item of clothing and go through checkout.
    //The order number will be present in 'My Orders'
    @Story("Checkout Flow")
    @DisplayName("Complete Checkout and Verify Order")
    @Description("Verify user can purchase an item, complete checkout, and find the order in 'My Orders'.")
    @ParameterizedTest(name = "Current order number should match latest order number")
    @CsvFileSource(resources = "/billingData.csv", numLinesToSkip = 1)
    void orderTest(String firstName, String lastName, String country, String street,
                   String city, String postcode, String phone, String email) {
        String productName = "Beanie";
        Allure.step("Navigating to Shop page...");
        ShopPage shopPage = myAccountPage.goToShop();
        assertTrue(shopPage.isAt(), "Did not reach shop page as expected.");

        // Click add to cart btn of first product
        Allure.step("Adding first item to cart...");
        shopPage.clickAddToCart(productName);

        // Go to cart page
        Allure.step("Navigating to Cart page...");
        CartPage cartPage = shopPage.goToCart();
        assertTrue(cartPage.isAt(), "Did not reach cart page as expected");
        Helpers.captureScreenshot(driver, "On cart page");


        // Go to checkout page
        Allure.step("Proceeding to Checkout page...");
        CheckoutPage checkoutPage = cartPage.goToCheckoutPage();

        // fill billing form
        Allure.step("Filling billing form for " + firstName + " " + lastName);
        checkoutPage.fillBillingForm(firstName, lastName, country, street, city, postcode, phone, email);

        //Go to order received page
        Allure.step("Placing the order...");
        OrderReceivedPage orderReceivedPage = checkoutPage.placeOrder();

        String orderNumberFromCheckout = orderReceivedPage.getOrderNumber();
        Allure.addAttachment("Order Number (from checkout)", orderNumberFromCheckout);

        // go to my account
        Allure.step("Navigating to My Account...");
        myAccountPage = orderReceivedPage.goToMyAccountPage();

        // go to orders page
        Allure.step("Viewing order history...");
        OrderHistoryPage orderHistoryPage = myAccountPage.viewOrders();
        Helpers.captureScreenshot(driver, "On orders page");


        // get latest order no.
        String latestOrderNumber = orderHistoryPage.getLatestOrderNumber();
        Allure.addAttachment("Latest Order Number (in account)", latestOrderNumber);

        assertEquals(orderNumberFromCheckout, latestOrderNumber, "Order numbers do not match.");

    }
}
