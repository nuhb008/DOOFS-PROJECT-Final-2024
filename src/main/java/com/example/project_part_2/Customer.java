package com.example.project_part_2;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {

    private int usrID;
    private String pass;
    private ArrayList<Food> order;
    private String order_line;
    private double total;
    Customer(int usrID, String pass)
    {
        this.usrID=usrID;
        this.pass=pass;
    }
    public int getUsrID() {
        return usrID;
    }

    public String getPass() {
        return pass;
    }

    public void setOrder(ArrayList<Food> order) {
        this.order = order;
    }

    public ArrayList<Food> getOrder() {
        return order;
    }


    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public String getOrder_line() {
        return order_line;
    }

    public void order_stringer()
    {
        String temp="";
        for (Food f:order)
        {
            temp=temp+f.getName()+" ,";
        }
        order_line=temp;
    }
}
