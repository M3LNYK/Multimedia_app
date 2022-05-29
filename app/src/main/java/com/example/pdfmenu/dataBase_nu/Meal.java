package com.example.pdfmenu.dataBase_nu;

import java.util.ArrayList;

public class Meal {

    public static ArrayList<Meal> mealArrayList = new ArrayList<>();

    private int id;
    private String group;
    private String name;
    private String note;
    private double price;

    public Meal(int id, String group, String name, String note, double price) {
        this.id = id;
        this.group = group;
        this.name = name;
        this.note = note;
        this.price = price;
    }

    public Meal(int id, String group, String name, double price) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.price = price;
        this.note = null;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
