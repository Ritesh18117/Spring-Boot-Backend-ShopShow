package com.shopshow.backend.dto;

public class CartItemRequest {
    private Long product_id;
    private Long color_id;
    private Long size_id;
    private int quantity;
    public CartItemRequest() {
    }

    public CartItemRequest(Long product_id, Long color_id, Long size_id, int quantity) {
        this.product_id = product_id;
        this.color_id = color_id;
        this.size_id = size_id;
        this.quantity = quantity;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getColor_id() {
        return color_id;
    }

    public void setColor_id(Long color_id) {
        this.color_id = color_id;
    }

    public Long getSize_id() {
        return size_id;
    }

    public void setSize_id(Long size_id) {
        this.size_id = size_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
