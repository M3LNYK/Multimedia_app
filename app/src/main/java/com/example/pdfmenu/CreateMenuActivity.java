package com.example.pdfmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pdfmenu.dataBase.Dish.Dish;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CreateMenuActivity extends AppCompatActivity {

    private Button button_create;

    private Bitmap bmp, scaledBitmap;

    private TextView tvAmountOfObj;

    private int pageWidth, pageHeight;

    private int md_counter = 0;
    private int d_counter = 0;
    private int a_counter = 0;
    private int sp_counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);

        button_create = findViewById(R.id.button_createPDF);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.food_v2);
        scaledBitmap = Bitmap.createScaledBitmap(bmp, 1240, 585, false);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        TextView tvGroup = findViewById(R.id.textViewNumberDish);
        tvGroup.setText(amountToStr());

        createPDF();
    }

    private void createPDF() {
        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPdfFileV2();

            }
        });
    }

    private ArrayList<String> groups = new ArrayList<>();

    private void getGroups() {
        if (Dish.nonDeletedDishes().size() > 0) {
            for (Dish dish : Dish.nonDeletedDishes()) {
                if (!groups.contains(dish.getGroup())) {
                    groups.add(dish.getGroup());
                }
            }
        }
    }

    private void createPdfFileV2() {
        if (Dish.nonDeletedDishes().size() > 0) {

            PdfDocument myPdfDocument = new PdfDocument();
            Paint myPaint = new Paint();
            Paint titlePaint = new Paint();
            Paint groupName = new Paint();
            Paint dishData = new Paint();



            pageWidth = 1240;
            pageHeight = 1754;

            PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
            PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);

            Canvas canvas = myPage1.getCanvas();

            int color = ContextCompat.getColor(getApplicationContext(), R.color.button_red);
            myPaint.setColor(color);
//            canvas.drawRect(0, 0, pageWidth, pageHeight, myPaint);
            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            titlePaint.setTextSize(110);
            canvas.drawText("MENU", pageWidth / 2, 200, titlePaint);
            canvas.drawLine(pageWidth/3, 250, pageWidth/3*2, 250, titlePaint);

            getGroups();
            groupName.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            groupName.setTextSize(50);
            groupName.setTextAlign(Paint.Align.LEFT);

            dishData.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            dishData.setTextSize(35);
            dishData.setTextAlign(Paint.Align.LEFT);

            int counter = 370;
            for (int i = 0; i < groups.size(); i++) {
                canvas.drawText(groups.get(i), 140, counter , groupName);
                ArrayList<Dish> tmp = getDishList(groups.get(i));
                for (Dish dish : tmp){
                    counter += 60;
                    String changedName = dish.getName() + "   ";
                    int s = changedName.length();
                    for (int k = s; k <= 50; k++ ){
                        changedName += "_";
                    }
                    canvas.drawText(changedName, 160, counter, dishData );
                    canvas.drawText(dish.getPrice() + " $", 995, counter, dishData );
                }
                counter +=150;
            }


            myPdfDocument.finishPage(myPage1);

            File file = new File(Environment.getExternalStorageDirectory(), "/Download/Menu.pdf");

//          Create a more fancy way to check if folder exists and create a new if it doesn't
            try {
                myPdfDocument.writeTo(new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }

            myPdfDocument.close();
            Intent intent = new Intent(CreateMenuActivity.this, PdfDisplayActivity.class);
            CreateMenuActivity.this.startActivity(intent);
        } else {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Can not create empty menu!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void createPdfFileV1() {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        Paint rectPaint = new Paint();
        rectPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.menu1_strip, null));

        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1240, 1754, 1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);

        Canvas canvas = myPage1.getCanvas();

        canvas.drawBitmap(scaledBitmap, 0, 0, myPaint);
        canvas.drawRect(0, 585, 1240, 620, rectPaint);

        rectPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.menu1_background, null));
        canvas.drawRect(0, 620, 1240, 1754, rectPaint);


        myPdfDocument.finishPage(myPage1);

        File file = new File(Environment.getExternalStorageDirectory(), "/Download/Menu.pdf");

//                Create a more fancy way to check if folder exists and create a new if it doesn't
        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        myPdfDocument.close();
    }

    public ArrayList<Dish> getDishList(String groupName){
        ArrayList<Dish> res = new ArrayList<>();
        for (Dish dish : Dish.nonDeletedDishes()){
            if (dish.getGroup().contains(groupName)){
                res.add(dish);
            }
        }
        return res;
    }

    private String amountToStr() {
        String result = Integer.toString(Dish.nonDeletedDishes().size());
        return result;
    }

}
