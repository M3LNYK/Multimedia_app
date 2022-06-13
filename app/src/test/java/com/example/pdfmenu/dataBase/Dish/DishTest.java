package com.example.pdfmenu.dataBase.Dish;

import static org.junit.Assert.*;

import org.junit.Test;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DishTest {

    @Test
    public void getDishForID() {
        fail();
    }

    @Test
    public void getGroup() {
        Dish a = new Dish(0, "Appetizers", "Meat", "", "19.99");
        assertEquals("Appetizers", a.getGroup());
    }

    @Test
    public void setGroup() {
        Dish a = new Dish();
        a.setGroup("Appetizers");
        assertEquals("Appetizers", a.getGroup());
    }

    @Test
    public void getId() {
        Dish a = new Dish(0, "Appetizers", "Meat", "", "19.99");
        assertEquals(0, a.getId());
    }

    @Test
    public void getName() {
        Dish a = new Dish(0, "Appetizers", "Meat", "", "19.99");
        assertEquals("Meat", a.getName());
    }

    @Test
    public void getNote() {
        Dish a = new Dish(0, "Appetizers", "Meat", "Notee", "19.99");
        assertEquals("Notee", a.getNote());
    }

    @Test
    public void getPrice() {
        Dish a = new Dish(0, "Appetizers", "Meat", "Notee", "19.99");
        assertEquals("19.99", a.getPrice());
    }

    @Test
    public void setId() {
        Dish a = new Dish();
        a.setId(0);
        assertEquals(0, a.getId());
    }

    @Test
    public void setName() {
        Dish a = new Dish();
        a.setName("Apple");
        assertEquals("Apple", a.getName());
    }

    @Test
    public void setNote() {
        Dish a = new Dish();
        a.setNote("New note");
        assertEquals("New note", a.getNote());
    }

    @Test
    public void setPrice() {
        Dish a = new Dish();
        a.setPrice("29.95");
        assertEquals("29.95", a.getPrice());
    }

    @Test
    public void getDeleted() {
        Dish a = new Dish(0, "Appetizers", "Meat", "Notee", "19.99", new Date(2022, 04, 19, 00, 00, 00));
        assertEquals("Fri May 19 00:00:00 CEST 3922", a.getDeleted().toString());
    }

    @Test
    public void setDeleted() {
        Dish a = new Dish();
        a.setDeleted(new Date(2022, 04, 19, 00, 00, 00));
        assertEquals("Fri May 19 00:00:00 CEST 3922", a.getDeleted().toString());

    }

    @Test
    public void nonDeletedDishes() {
        fail();
    }
}