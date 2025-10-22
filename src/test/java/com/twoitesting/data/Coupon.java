package com.twoitesting.data;

public class Coupon {
    private final String code;
    private final int discount;

    public Coupon(String code, int discount) {
        this.code = code;
        this.discount = discount;
    }

    public String getCode() {
        return code;
    }

    public int getDiscount() {
        return discount;
    }
}
