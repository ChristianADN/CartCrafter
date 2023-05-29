package com.example.cartcrafter.models;

import java.util.List;

public class ProductFullDataModel {
    private ProductModel product;
    private List<ProductReviewModel> reviews;
    private List<ProductShopModel> prices;
    private int averageRating;
    private boolean currentUserHasReview;

    public boolean isCurrentUserHasReview() {
        return currentUserHasReview;
    }

    public void setCurrentUserHasReview(boolean currentUserHasReview) {
        this.currentUserHasReview = currentUserHasReview;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public List<ProductReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ProductReviewModel> reviews) {
        this.reviews = reviews;
    }

    public List<ProductShopModel> getPrices() {
        return prices;
    }

    public void setPrices(List<ProductShopModel> prices) {
        this.prices = prices;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }
}