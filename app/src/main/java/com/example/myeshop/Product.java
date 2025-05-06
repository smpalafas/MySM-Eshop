package com.example.myeshop;

public class Product {
    private int id;
    private String title;
    private String description;
    private double price;
    private int quantity;  // Ενημερώσαμε την ποσότητα σε int
    private int subcategoryId;

    // Κατασκευαστής με όλες τις απαιτούμενες παραμέτρους
    public Product(int id, String title, String description, double price, int quantity, int subcategoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;  // Ενημέρωση της ποσότητας σε int
        this.subcategoryId = subcategoryId;
    }

    // Getter methods για τις παραμέτρους
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }  // Επιστρέφει την ποσότητα ως int
    public int getSubcategoryId() { return subcategoryId; }

    // Setter για την ποσότητα
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Μέθοδος για την μείωση της ποσότητας
    public void decreaseQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }

    // Μέθοδος για την αύξηση της ποσότητας
    public void increaseQuantity() {
        this.quantity++;
    }
}
