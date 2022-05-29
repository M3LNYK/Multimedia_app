package com.example.pdfmenu.dataBase.Dish;

import java.util.ArrayList;
import java.util.Date;

public class Dish {
    public static ArrayList<Dish> dishArrayList = new ArrayList<>();
    public static String DISH_EDIT_EXTRA = "dishEdit";

    private int id;
    private String group;
    private String name;
    private String note;
    private String price;
    private Date deleted;

    public Dish(int id, String group, String name, String note, String price) {
        this.id = id;
        this.group = group;
        this.name = name;
        this.note = note;
        this.price = price;
        deleted = null;
    }

    public Dish(int id, String name, String note, String price) {
        this.id = id;
        this.name = name;
        this.group = null;
        this.price = price;
        this.note = note;
        deleted = null;
    }

    public Dish(int id, String group, String name, String note, String price, Date deleted) {
        this.id = id;
        this.group = group;
        this.name = name;
        this.note = note;
        this.price = price;
        this.deleted = deleted;
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

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    public static ArrayList<Dish>nonDeletedDishes(){
        ArrayList<Dish> nonDeleted = new ArrayList<>();
        for(Dish dish : dishArrayList){
            if (dish.getDeleted() == null)
                nonDeleted.add(dish);
        }

        return nonDeleted;
    }
}