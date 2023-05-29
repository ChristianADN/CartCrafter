package com.example.cartcrafter.models;

public class ShoppingListProductModel {
    private String shoppingListId;
    private String productId;
    private int quantity;
    private boolean addedToCart;
    private ProductModel product;
    private float price;

    public String getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(String shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAddedToCart() {
        return addedToCart;
    }

    public void setAddedToCart(boolean addedToCart) {
        this.addedToCart = addedToCart;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public float getShop() {
        return price;
    }

    public void setShop(float price) {
        this.price = price;
    }
}
