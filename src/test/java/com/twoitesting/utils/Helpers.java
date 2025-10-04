package com.twoitesting.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Helpers {

    // manually scroll into view
    public static void scrollIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
    }

    // click using javascript, in cases  where click() does not work
    public static void javascriptClick(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // For static elements
    public static WebElement waitForElementToBeClickable(WebDriver driver, WebElement element, int timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // For dynamic elements
    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementToBeVisible(WebDriver driver, WebElement element, int timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

}
