package com.example.pdfmenu.dataBase.Dish;

import java.util.ArrayList;

public class Dish {
    public static ArrayList<Dish> dishArrayList = new ArrayList<>();
    public static String DISH_EDIT_EXTRA = "dishEdit";

    private int id;
    private String group;
    private String name;
    private String note;
    private String price;

    public Dish(int id, String group, String name, String note, String price) {
        this.id = id;
        this.group = group;
        this.name = name;
        this.note = note;
        this.price = price;
    }

    public Dish(int id, String name, String note, String price) {
        this.id = id;
        this.name = name;
        this.group = null;
        this.price = price;
        this.note = note;
    }

    public static Dish getDishForID(int passedDishID){
        for (Dish dish : dishArrayList){
            if(dish.getId() == passedDishID){
                return dish;
            }
        }
        return null;
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

    public String getPrice() {
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

    public void setPrice(String price) {
        this.price = price;
    }
}