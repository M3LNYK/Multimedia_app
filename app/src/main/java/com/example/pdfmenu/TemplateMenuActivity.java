package com.example.pdfmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TemplateMenuActivity extends AppCompatActivity {

    RecyclerView menusList;
    List<String> titles;
    List<Integer> images;
    Adapter_RV adapter_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_menu);

        menusList = findViewById(R.id.menu_recycler_view);

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("Example 1");
        titles.add("Simple menu");
        titles.add("Wood & red stripes");
        titles.add("Taxi yellow menu");
        titles.add("Blue menu");
        titles.add("Many options");
        titles.add("Breakfast menu");
        titles.add("Few options menu");

        images.add(R.drawable.menu_example_v1);
        images.add(R.drawable.menu_simple);
        images.add(R.drawable.menu_wood_red);
        images.add(R.drawable.menu_yellow);
        images.add(R.drawable.menu_blue);
        images.add(R.drawable.menu_black_white);
        images.add(R.drawable.menu_breakfast);
        images.add(R.drawable.menu_small_diner);

        adapter_rv = new Adapter_RV(this, titles, images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        menusList.setLayoutManager(gridLayoutManager);
        menusList.setAdapter(adapter_rv);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, CreateMenuActivity.class);
        startActivity(intent);
    }
}