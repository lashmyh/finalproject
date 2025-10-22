package com.twoitesting.data;

public class BillingInfo {
    private final String firstName;
    private final String lastName;
    private final String country;
    private final String street;
    private final String city;
    private final String postcode;
    private final String phone;
    private final String email;

    public BillingInfo(String firstName, String lastName, String country, String street,
                       String city, String postcode, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.street = street;
        this.city = city;
        this.postcode = postcode;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCountry() { return country; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getPostcode() { return postcode; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
}
