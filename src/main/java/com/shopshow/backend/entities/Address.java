package com.shopshow.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Long id;
    private String address1;
    private String address2;
    @NotNull(message = "City cannot be null")
    @NotBlank(message = "City must not be Blank!")
    private String city;
    @NotNull(message = "State cannot be null")
    @NotBlank(message = "State must not be Blank!")
    private String state;
    @NotNull(message = "Country cannot be null")
    @NotBlank(message = "Country must not be Blank!")
    private String country;
    @NotNull(message = "Zipcode cannot be null")
    @NotBlank(message = "Zipcode must not be Blank!")
    private String zipcode;

    public Address() {
    }

    public Address(Long id, String address1, String address2, String city, String state, String country, String pincode) {
        this.id = id;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = pincode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
