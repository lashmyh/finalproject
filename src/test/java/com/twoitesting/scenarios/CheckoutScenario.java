package com.twoitesting.scenarios;

import com.twoitesting.pages.MyAccountPage;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutScenario extends BaseScenario {

    public CheckoutScenario(WebDriver driver, MyAccountPage myAccountPage) {
        super(driver, myAccountPage);
    }

    public void completeCheckoutAndVerifyOrder(
            String firstName, String lastName, String country,
            String street, String city, String postcode,
            String phone, String email, String productName) {

        navigateToShop();
        goToCartFromShop(productName);

        Allure.step("Proceeding to checkout...");
        checkoutPage = cartPage.goToCheckoutPage();

        Allure.step("Filling in billing information...");
        checkoutPage.fillBillingForm(firstName, lastName, country, street, city, postcode, phone, email);

        Allure.step("Placing the order...");
        orderReceivedPage = checkoutPage.placeOrder();
        String orderNumberFromCheckout = orderReceivedPage.getOrderNumber();

        Allure.step("Verifying order appears in account...");
        myAccountPage = orderReceivedPage.goToMyAccountPage();
        orderHistoryPage = myAccountPage.viewOrders();
        String latestOrderNumber = orderHistoryPage.getLatestOrderNumber();

        Allure.addAttachment("Order Number (from checkout)", orderNumberFromCheckout);
        Allure.addAttachment("Order Number (from My Orders)", latestOrderNumber);

        assertEquals(orderNumberFromCheckout, latestOrderNumber, "Order numbers do not match.");
    }
}
