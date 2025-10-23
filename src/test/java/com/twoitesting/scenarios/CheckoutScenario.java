package com.twoitesting.scenarios;

import com.twoitesting.data.BillingInfo;
import com.twoitesting.pages.MyAccountPage;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;

public class CheckoutScenario extends BaseScenario {

    public CheckoutScenario(WebDriver driver, MyAccountPage myAccountPage) {
        super(driver, myAccountPage);
    }

    public void completeCheckout(BillingInfo billing, String productName) {
        navigateToShop();
        goToCartFromShop(productName);

        // Proceed to checkout
        Allure.step("Proceeding to Checkout page...");
        checkoutPage = cartPage.goToCheckoutPage();

        // Fill billing form
        Allure.step("Filling billing form for " + billing.getFirstName() + " " + billing.getLastName());
        checkoutPage.fillBillingForm(
                billing.getFirstName(),
                billing.getLastName(),
                billing.getCountry(),
                billing.getStreet(),
                billing.getCity(),
                billing.getPostcode(),
                billing.getPhone(),
                billing.getEmail()
        );

        // Place order
        Allure.step("Placing the order...");
        orderReceivedPage = checkoutPage.placeOrder();

        // Verify order in My Account
        String orderNumberFromCheckout = orderReceivedPage.getOrderNumber();
        myAccountPage = orderReceivedPage.goToMyAccountPage();
        orderHistoryPage = myAccountPage.viewOrders();
        String latestOrderNumber = orderHistoryPage.getLatestOrderNumber();

        assert orderNumberFromCheckout.equals(latestOrderNumber) :
                "Order numbers do not match!";
    }
}
