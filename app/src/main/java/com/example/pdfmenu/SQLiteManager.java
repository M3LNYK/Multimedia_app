package com.example.pdfmenu;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pdfmenu.dataBase.Dish.Dish;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "DishDB";

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Dish";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "title";
    private static final String NOTE_FIELD = "note";
    private static final String PRICE_FIELD = "price";
    private static final String GROUP_FIELD = "groupp";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");


    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context) {
        if (sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql;
        SQLiteDatabase db = sqLiteDatabase;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(GROUP_FIELD)
                .append(" TEXT, ")
                .append(PRICE_FIELD)
                .append(" TEXT, ")
                .append(NOTE_FIELD)
                .append(" TEXT)");

        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void addDishToDatabase(Dish dish) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, dish.getId());
        contentValues.put(NAME_FIELD, dish.getName());
        contentValues.put(GROUP_FIELD, dish.getGroup());
        contentValues.put(PRICE_FIELD, dish.getPrice());
        contentValues.put(NOTE_FIELD, dish.getNote());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

    }

    public void populateDishListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    String group = result.getString(3);
                    String price = result.getString(4);
                    String note = result.getString(5);

                    Dish dish = new Dish(id, group, name, note, price);
                    Dish.dishArrayList.add(dish);
                }
            }
        }
    }

    public void updateDishInDB(Dish dish) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_FIELD, dish.getId());
        contentValues.put(NAME_FIELD, dish.getName());
        contentValues.put(GROUP_FIELD, dish.getGroup());
        contentValues.put(PRICE_FIELD, dish.getPrice());
        contentValues.put(NOTE_FIELD, dish.getNote());


        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(dish.getId())});
    }


    private String getStringFromDate(Date date) {
        if (date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
