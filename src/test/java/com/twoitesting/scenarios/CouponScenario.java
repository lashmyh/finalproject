package com.twoitesting.scenarios;

import com.twoitesting.data.Coupon;
import com.twoitesting.pages.MyAccountPage;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CouponScenario extends BaseScenario {

    public CouponScenario(WebDriver driver, MyAccountPage myAccountPage) {
        super(driver, myAccountPage);
    }

    public void addItemToCart(String productName) {
        navigateToShop();
        goToCartFromShop(productName);
    }

    public void applyCouponAndVerifyDiscount(Coupon coupon) {
        Allure.step("Applying coupon: " + coupon.getCode());
        cartPage.applyCoupon(coupon.getCode());

        Allure.step("Verifying discount...");
        int subtotal = cartPage.getSubtotal();
        int discountedAmount = cartPage.getDiscountValue();
        int expected = (int) Math.round(subtotal * coupon.getDiscount() / 100.0);

        Allure.addAttachment("Subtotal", String.valueOf(subtotal));
        Allure.addAttachment("Expected Discount", String.valueOf(expected));
        Allure.addAttachment("Actual Discount", String.valueOf(discountedAmount));

        assertEquals(expected, discountedAmount, "Incorrect discount amount.");
    }
}

