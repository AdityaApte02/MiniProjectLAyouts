package com.example.mini_project_phone_auth;

public class Stock {

    private User user;
    private String stockName;
    private double price;
    private int quantity;

    public Stock(){

    }

    public Stock(User user, String stockName, double price, int quantity) {
        this.user = user;
        this.stockName = stockName;
        this.price = price;
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getItemName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
