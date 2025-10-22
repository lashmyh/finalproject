package com.twoitesting.data;

import java.util.List;

public class TestData {

    public static List<Coupon> getCoupons() {
        return List.of(
                new Coupon("Edgewords", 15),
                new Coupon("2idiscount", 25)
        );
    }

    public static BillingInfo getBillingInfo() {
        return new BillingInfo(
                "Joe",
                "Sardine",
                "United Kingdom (UK)",
                "121 Real Street",
                "Manchester",
                "M15 4TQ",
                "07123456789",
                "joesardine@gmail.com"
        );
    }
}

