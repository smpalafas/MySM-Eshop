package com.example.myeshop;

public class Product {
    private int id;
    private String title;
    private String description;
    private double price;
    private String quantity;
    private int subcategoryId;

    public Product(int id, String title, String description, double price, String quantity, int subcategoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.subcategoryId = subcategoryId;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getQuantity() { return quantity; }
    public int getSubcategoryId() { return subcategoryId; }
}
