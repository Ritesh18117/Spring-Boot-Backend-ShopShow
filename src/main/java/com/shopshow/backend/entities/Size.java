package com.shopshow.backend.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "size_id")
    private Long id;
    private String size;

    public Size() {
    }

    public Size(Long id, String size) {
        this.id = id;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
