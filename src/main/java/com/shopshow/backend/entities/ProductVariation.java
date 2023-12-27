// Product Variation is A Table which contains the Amount, Color and Size of the Product which is Connected to each other,
// It contains that " Which product of which size and which color is available in which quantity."
//  color, Size and Quantity should be sync together so that people should not order anything that is already sold
//  or anything that is of different color
package com.shopshow.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "product_variation")
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_variation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;


    private int quantity;

    public ProductVariation() {
    }

    public ProductVariation(Long id, Product product, Color color, Size size, int quantity) {
        this.id = id;
        this.product = product;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductVariation{" +
                "id=" + id +
                ", product=" + product +
                ", color=" + color +
                ", size=" + size +
                ", quantity=" + quantity +
                '}';
    }
}
