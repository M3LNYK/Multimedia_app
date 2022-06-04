package com.example.pdfmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class PdfDisplayActivity extends AppCompatActivity {

    ImageView pdfImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_displayer);

        pdfImage = findViewById(R.id.pdf_image);
        try {
            displayPdf();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayPdf() throws IOException {
        File file = new File(Environment.getExternalStorageDirectory(), "/Download/Menu.pdf");
        ParcelFileDescriptor pfd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        PdfRenderer pdfRenderer = new PdfRenderer(pfd);
        PdfRenderer.Page page =  pdfRenderer.openPage(0);
        Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        pdfImage.setImageBitmap(bitmap);
        page.close();
        pdfRenderer.close();
    }
}