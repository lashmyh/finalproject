package com.twoitesting;

import com.twoitesting.basetest.BaseTest;
import com.twoitesting.scenarios.CheckoutScenario;
import com.twoitesting.scenarios.CouponScenario;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("E-commerce Functionality")
public class Tests extends BaseTest {


    @Story("Coupon Functionality")
    @DisplayName("Apply Coupon and Verify Discount")
    @ParameterizedTest(name = "Coupon {0} should apply a {1}% discount")
    @CsvFileSource(resources = "/coupons.csv", numLinesToSkip = 1)
    void couponTest(String couponCode, int discount) {
        CouponScenario couponScenario = new CouponScenario(driver, myAccountPage);
        couponScenario.addItemToCart("Beanie");
        couponScenario.applyCouponAndVerifyDiscount(couponCode, discount);
    }

    @Story("Checkout Flow")
    @DisplayName("Complete Checkout and Verify Order")
    @ParameterizedTest(name = "Order number should match latest order")
    @CsvFileSource(resources = "/billingData.csv", numLinesToSkip = 1)
    void orderTest(String firstName, String lastName, String country, String street,
                   String city, String postcode, String phone, String email) {
        CheckoutScenario checkoutScenario = new CheckoutScenario(driver, myAccountPage);
        checkoutScenario.completeCheckoutAndVerifyOrder(
                firstName, lastName, country, street, city, postcode, phone, email, "Beanie"
        );
    }

}
