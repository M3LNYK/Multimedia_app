package com.example.pdfmenu.dataBase.Dish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pdfmenu.R;

import java.util.ArrayList;


public class DishListAdapter extends ArrayAdapter<Dish> {

    private Context mContext;
    int mResource;

    public DishListAdapter(Context context, int resource, ArrayList<Dish> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    public DishListAdapter(Context context, ArrayList<Dish> objects) {
        super(context, 0, objects);
        this.mContext = context;
        this.mResource = 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String note = getItem(position).getNote();
        String price = getItem(position).getPrice();
        String group = getItem(position).getGroup();
        String groupCharacter;
//        Dish dish = new Dish(name, price, note);
        groupCharacter = selectGroupCharacter(group);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.nameText);
        TextView tvNote = (TextView) convertView.findViewById(R.id.noteText);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.priceText);
        TextView tvGroup = (TextView) convertView.findViewById(R.id.groupText);

        tvName.setText(name);
        tvNote.setText(note);
        tvPrice.setText(price);
        tvGroup.setText(groupCharacter);


        return convertView;
    }

    private String selectGroupCharacter(String group) {
        switch (group) {
            case "Drinks":
                return "D";
            case "Main Dish":
                return "MD";
            case "Appetizers":
                return "A";
            case "Season Specials":
                return "Sp";
            default:
                return "";
        }
    }
}
