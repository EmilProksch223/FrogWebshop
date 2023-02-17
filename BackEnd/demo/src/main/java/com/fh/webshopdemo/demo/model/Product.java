package com.fh.webshopdemo.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity(name="product")
public class Product {
    
    @Id
    @GeneratedValue
    @Column(name="id")
    private final Long id;

    @NotBlank(message ="Titel darf nicht leer sein!")
    @Column(name="name")
    private String name;

    @NotBlank(message ="Beschreibung darf nicht leer sein!")
    @Column(name = "description", length = 350)
    private String description;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="price")
    private double price;

    @PositiveOrZero(message = "Darf nicht negativ sein!")
    @Column(name="quantity")
    private int quantity;

    @NotBlank(message = "Muss mindestens eine Farbe haben!")
    @Column(name="manaType")
    private String manaType;
    


    public Product() {
        this.id = null;
        this.name = null;
        this.description = null;
        this.imageUrl = null;
        this.price = 0.0;
        this.quantity = 0;
        this.manaType = null;
    }
    


    public Product(Long id, String name, String description, String imageUrl, double price, int quantity, String manaType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
        this.manaType = manaType;
    }



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getManaType() {
        return manaType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setManaType(String manaType) {
        this.manaType = manaType;
    }
    
}
