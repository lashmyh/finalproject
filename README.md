# Java Selenium E-Commerce Final Project

This project automates two **end-to-end test cases** for an e-commerce website using **Java**, **Selenium WebDriver**, and **JUnit**.  
It demonstrates test automation best practices such as the **Page Object Model (POM)**, modular test design, and structured reporting.

---

## Project Overview

The goal of this project is to test critical user journeys of an online store — logging in, purchasing items, applying discount codes, and verifying order information.

**Website Under Test:**  
🔗 [https://www.edgewordstraining.co.uk/demo-site](https://www.edgewordstraining.co.uk/demo-site)

---

## Test Cases

### **Test Case 1 – Apply Discount and Verify Total**
**Objective:**  
Login as a registered user, purchase an item, apply a discount code, and verify the total price after discount and shipping.

**Valid Coupon Codes:**

| Code | Discount |
|------|-----------|
| `Edgewords` | 15% |
| `2idiscount` | 25% |

**Automated Steps:**
1. Login to your account  
2. Navigate to **Shop**  
3. Add a clothing item to the cart  
4. Apply a valid coupon code  
5. Verify that the discount is applied correctly  
6. Verify that the final total (including shipping) is correct  
7. Log out  

---

### **Test Case 2 – Place Order and Verify in My Orders**
**Objective:**  
Login as a registered user, purchase an item, complete checkout, and verify that the order appears under **My Orders**.

**Automated Steps:**
1. Login to your account  
2. Add a clothing item to the cart  
3. Proceed to checkout  
4. Fill in billing details (using a valid postcode)  
5. Choose **Check Payments** as the payment method  
6. Place the order  
7. Capture and store the order number  
8. Navigate to **My Account → Orders** and verify the same order appears  
9. Log out  

---

## Tools & Technologies

- **Language:** Java  
- **Test Framework:** JUnit  
- **Automation Library:** Selenium WebDriver  
- **Build Tool:** Maven  
- **Design Pattern:** Page Object Model (POM)  
- **IDE:** IntelliJ IDEA   
- **Reporting:** Allure reports

