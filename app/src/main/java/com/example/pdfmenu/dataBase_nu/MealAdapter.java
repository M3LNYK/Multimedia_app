package com.example.pdfmenu.dataBase_nu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pdfmenu.R;

import java.util.List;

public class MealAdapter extends ArrayAdapter<Meal> {

    public MealAdapter (Context context, List<Meal> meals){
        super(context, 0, meals);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Meal meal = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_view_layout, parent, false);

//        AutoCompleteTextView group = convertView.findViewById(R.id.groupText);
        TextView name = convertView.findViewById(R.id.nameText);
        TextView note = convertView.findViewById(R.id.noteText);
        TextView price = convertView.findViewById(R.id.priceText);


        name.setText(meal.getName());
        note.setText(meal.getNote());
        price.setText((int) meal.getPrice());

        return super.getView(position, convertView, parent);
    }
}
