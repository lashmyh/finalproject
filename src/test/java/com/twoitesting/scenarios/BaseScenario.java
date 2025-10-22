package com.twoitesting.scenarios;

import com.twoitesting.pages.*;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseScenario {

    protected final WebDriver driver;
    protected MyAccountPage myAccountPage;
    protected ShopPage shopPage;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;
    protected OrderReceivedPage orderReceivedPage;
    protected OrderHistoryPage orderHistoryPage;

    public BaseScenario(WebDriver driver, MyAccountPage myAccountPage) {
        this.driver = driver;
        this.myAccountPage = myAccountPage;
    }

    protected void navigateToShop() {
        Allure.step("Navigating to shop page...");
        shopPage = myAccountPage.goToShop();
        assertTrue(shopPage.isAt(), "User not on shop page.");
    }

    protected void goToCartFromShop(String productName) {
        Allure.step("Adding item to cart...");
        shopPage.clickAddToCart(productName);

        Allure.step("Going to cart page...");
        cartPage = shopPage.goToCart();
        assertTrue(cartPage.isAt(), "User not on cart page.");
    }
}
