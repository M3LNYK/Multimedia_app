package com.example.pdfmenu;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TemplateMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_menu);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, CreateMenuActivity.class);
        startActivity(intent);
    }
}