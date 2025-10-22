package com.twoitesting.tests;

import com.twoitesting.basetest.BaseTest;
import com.twoitesting.data.BillingInfo;
import com.twoitesting.data.TestData;
import com.twoitesting.scenarios.CheckoutScenario;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Story("Checkout Flow")
class CheckoutTest extends BaseTest {

    @Test
    @DisplayName("Complete checkout and verify order")
    void checkoutTest() {
        CheckoutScenario scenario = new CheckoutScenario(driver, myAccountPage);
        BillingInfo billing = TestData.getBillingInfo();

        scenario.completeCheckout(billing, "Beanie");
    }
}
