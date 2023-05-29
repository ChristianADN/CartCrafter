package com.example.cartcrafter.models;

import java.time.LocalDate;

public class ProductShopModel {
    private String id;
    private ProductModel product;
    private ShopModel shop;
    private float price;
    private String dateAdded;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductModel getProductId() {
        return product;
    }

    public void setProductId(ProductModel product) {
        this.product = product;
    }

    public ShopModel getShop() {
        return shop;
    }

    public void setShop(ShopModel shopId) {
        this.shop = shop;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}