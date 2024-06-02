package com.example.project_part_2;
import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable {

    private int id;
    private String name;

    private double score;
    private String price;
    private int zip_code;
    private String[] categories=new String[3];
    private int categories_count=0;

    private ArrayList<Food> menu;

    private ArrayList<Customer> customers;

    Restaurant(String name)
    {
        this.name=name;
    }
    Restaurant(int id, String name, double score, String price, int zip_code, String cat1)
    {
        this.id=id;
        this.name=name;
        this.score=score;
        this.price=price;
        this.zip_code=zip_code;
        this.categories[0]=cat1;
        this.categories_count=1;
        menu= new ArrayList<>();
        customers=new ArrayList<>();

    }
    Restaurant(int id, String name, double score, String price, int zip_code, String cat1, String cat2)
    {
        this.id=id;
        this.name=name;
        this.score=score;
        this.price=price;
        this.zip_code=zip_code;
        this.categories[0]=cat1;
        this.categories[1]=cat2;
        this.categories_count=2;
        menu= new ArrayList<>();
        customers=new ArrayList<>();

    }
    Restaurant(int id, String name, double score, String price, int zip_code, String cat1, String cat2, String cat3)
    {
        this.id=id;
        this.name=name;
        this.score=score;
        this.price=price;
        this.zip_code=zip_code;
        this.categories[0]=cat1;
        this.categories[1]=cat2;
        this.categories[2]=cat3;
        this.categories_count=3;
        menu= new ArrayList<>();
        customers=new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public double getScore() {
        return score;
    }

    public String getPrice() {
        return price;
    }

    public int getZip_code() {
        return zip_code;
    }

    public String[] getCategories() {
        return categories;
    }

    public int getCategories_count() {
        return categories_count;
    }
    public ArrayList<Food> getMenu() {
        return menu;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }


    public void add_customer(Customer customer)
    {
        this.customers.add(customer);
    }
    public void add_to_menu(Food food_item)
    {
        this.menu.add(food_item);
    }
    public void show_details()
    {
        System.out.println("Restaurant ID: "+this.id);
        System.out.println("Restaurant Name: "+this.name);
        System.out.println("Restaurant Score: "+this.score);
        System.out.println("Restaurant Price: "+this.price);
        System.out.println("Restaurant ZIP Code: "+this.zip_code);
        if(this.categories_count==1)
        {
            System.out.println("Restaurant Category(s): "+this.categories[0]);
        }
        if(this.categories_count==2)
        {
            System.out.println("Restaurant Category(s): "+this.categories[0]+", "+this.categories[1]);
        }

        if(this.categories_count==3)
        {
            System.out.println("Restaurant Category(s): "+this.categories[0]+", "+this.categories[1]+", "+this.categories[2]);
        }

        System.out.println();
    }
}
