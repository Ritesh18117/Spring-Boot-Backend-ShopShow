package com.shopshow.backend.dto;

public class CartItemRequest {
    private Long product_id;
    private String color;
    private String size;
    private int quantity;
    public CartItemRequest() {
    }

    public CartItemRequest(Long product_id, String color, String size, int quantity) {
        this.product_id = product_id;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
