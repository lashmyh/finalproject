package com.twoitesting;

import com.twoitesting.basetest.BaseTest;
import com.twoitesting.pages.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests extends BaseTest {

    //Verify log in works
    @Test
    void testUserCanSeeAccountPage() {
        assertTrue(myAccountPage.isAt());
    }


    //test case 1 : user can add item to cart, go to cart and apply correct discount with coupon code
    @ParameterizedTest(name = "Coupon {0} should apply a {1}% discount")
    @CsvFileSource(resources = "/coupons.csv", numLinesToSkip = 1)
    void couponTest(String couponCode, int discount) {
        ShopPage shopPage = myAccountPage.goToShop();
        assertTrue(shopPage.isAt());

        // Click add to cart btn of first product
        shopPage.clickAddToCart();

        // Go to cart
        CartPage cartPage = shopPage.goToCart();
        assertTrue(cartPage.isAt());

        //Apply coupon code from csv file
        cartPage.applyCoupon(couponCode);

        //Validate discount
        int subtotal = cartPage.getSubtotal();          // in pennies
        int discountedAmount = cartPage.getDiscountValue(); // in pennies

        int expectedDiscount = (int) Math.round((subtotal * discount) / 100.0);

        System.out.println("Amount expected to be reduced by: " + discount + "%" + "(£" + expectedDiscount + ")");
        System.out.println("Amount reduced by: £" + discountedAmount);

        assertEquals(expectedDiscount, discountedAmount, "Incorrect amount discounted");

        // Go to account page to log out
        MyAccountPage myAccountPage1 = cartPage.goToMyAccountPage();
        myAccountPage1.logout();


    }

    //test case 2: user can purchase an item of clothing and go through checkout.
    //The order number will be present in 'My Orders'
    @ParameterizedTest(name = "Current order number should match latest order number")
    @CsvFileSource(resources = "/billingData.csv", numLinesToSkip = 1)
    void orderTest(String firstName, String lastName, String country, String street,
                   String city, String postcode, String phone, String email) {
        ShopPage shopPage = myAccountPage.goToShop();
        assertTrue(shopPage.isAt());

        // Click add to cart btn of first product
        shopPage.clickAddToCart();

        // Go to cart page
        CartPage cartPage = shopPage.goToCart();
        assertTrue(cartPage.isAt());

        // Go to checkout page
        CheckoutPage checkoutPage = cartPage.goToCheckoutPage();

        checkoutPage.fillBillingForm(firstName, lastName, country, street, city, postcode, phone, email);
//        checkoutPage.setSelectCheckPayments(); TODO: FIX

        //Go to order received page
        OrderReceivedPage orderReceivedPage = checkoutPage.placeOrder();

        String orderNumberFromCheckout = orderReceivedPage.getOrderNumber();
        System.out.println("Order number from current checkout: " + orderNumberFromCheckout);

        myAccountPage = orderReceivedPage.goToMyAccountPage();
        myAccountPage.viewOrders();
        String latestOrderNumber = myAccountPage.getLatestOrderNumber();
        System.out.println("Latest order number in account: " + latestOrderNumber);

        assertEquals(orderNumberFromCheckout, latestOrderNumber, "Order numbers do not match!");

        // Finally, log out
        MyAccountPage myAccountPage1 = cartPage.goToMyAccountPage();
        myAccountPage1.logout();

    }
}
