package com.example.project_part_2;

import java.io.Serializable;

public class Food implements Serializable {

    private int  restaurantId;
    private String category;
    private String name;
    private double price;

    Food(String name)
    {
        this.name=name;
    }
    Food(int resid, String cat, String n, double pr)
    {
        this.restaurantId=resid;
        this.category=cat;
        this.name=n;
        this.price=pr;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
    void showDetails()
    {
        System.out.println("Food name: "+this.name);
        System.out.println("Food category: "+this.category);
        System.out.println("Food price: "+this.price);
        System.out.println("Food RestaurantId: "+this.restaurantId);
        System.out.println();
    }

}
