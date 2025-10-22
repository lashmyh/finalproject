package com.twoitesting.tests;

import com.twoitesting.basetest.BaseTest;
import com.twoitesting.data.Coupon;
import com.twoitesting.data.TestData;
import com.twoitesting.scenarios.CouponScenario;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class CouponTest extends BaseTest {

    static Stream<Coupon> coupons() {
        return TestData.getCoupons().stream();
    }

    @Story("Coupon Functionality")
    @DisplayName("Apply Coupon and Verify Discount")
    @ParameterizedTest(name = "Coupon {0} should apply a {1}% discount")
    @MethodSource("coupons")
    void couponTest(Coupon coupon) {
        CouponScenario couponScenario = new CouponScenario(driver, myAccountPage);
        couponScenario.addItemToCart("Beanie");
        couponScenario.applyCouponAndVerifyDiscount(coupon);
    }
}
