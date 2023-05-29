package com.example.cartcrafter.models;

import java.util.Date;

public class ShoppingListModel {
    private String id;
    private String userId;
    private Date dateCreated;
    private boolean archived;
    private float totalWeight;
    private int productCount;

    public ShoppingListModel(Date dateCreated, int productCount, float totalWeight) {
        this.dateCreated = dateCreated;
        this.productCount = productCount;
        this.totalWeight = totalWeight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}

