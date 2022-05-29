package com.example.pdfmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateMenuActivity extends AppCompatActivity {

    Button button_create;

    Bitmap bmp, scaledBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);

        button_create = findViewById(R.id.button_createPDF);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.food_v2);
        scaledBitmap = Bitmap.createScaledBitmap(bmp, 1240, 585, false);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();
    }

    private void createPDF() {
        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
    }

}
