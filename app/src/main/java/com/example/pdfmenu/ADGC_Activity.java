package com.example.pdfmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pdfmenu.dataBase.Dish.Dish;
import com.example.pdfmenu.dataBase.Dish.DishListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ADGC_Activity extends AppCompatActivity {

    ListView mListView;
    private EditText newDishPopup_name, newDishPopup_price, newDishPopup_note;
    private Button newDishPopup_save;

    private ListView dishListView;
    private AlertDialog mDialog;

    private boolean clicked = false;

    FloatingActionButton fabOpen;
    FloatingActionButton fabEdit;
    FloatingActionButton fabContinue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adgc);

        mListView = (ListView) findViewById(R.id.menu_items);


        loadFromDbToMemory();
        DishListAdapter adapter = new DishListAdapter(getApplicationContext(), R.layout.adapter_view_layout, Dish.dishArrayList);
        mListView.setAdapter(adapter);

        fabOpen = (FloatingActionButton) findViewById(R.id.floating_open);

        fabEdit = (FloatingActionButton) findViewById(R.id.floating_edit);

        fabContinue = (FloatingActionButton) findViewById(R.id.floating_continue);



        fabOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anAddButtonClicked();
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNewDishDialog();
            }
        });

        fabContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Go to menus", Toast.LENGTH_LONG).show();
            }
        });

//        setOnClickListener();

        initSearchWidgets();
    }

    private void anAddButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        clicked = !clicked;

    }

    private void setAnimation(Boolean click) {
        Animation rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_anim);
        Animation rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_anim);
        Animation fromBottom =  AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_anim);
        Animation toBottom =  AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_anim);
        if (!click) {
            fabEdit.startAnimation(fromBottom);
            fabContinue.startAnimation(fromBottom);
            fabOpen.startAnimation(rotateOpen);
        } else {
            fabEdit.startAnimation(toBottom);
            fabContinue.startAnimation(toBottom);
            fabOpen.startAnimation(rotateClose);
        }
    }

    private void setVisibility(Boolean click) {
        if (!click){
            fabEdit.setVisibility(View.VISIBLE);
            fabContinue.setVisibility(View.VISIBLE);
        } else {
            fabEdit.setVisibility(View.INVISIBLE);
            fabContinue.setVisibility(View.INVISIBLE);
        }
    }

    private void setClickable(Boolean click){
        if (!click){
            fabEdit.setClickable(true);
            fabContinue.setClickable(true);
        } else {
            fabEdit.setClickable(false);
            fabContinue.setClickable(false);
        }
    }

    private void initSearchWidgets(){
        SearchView searchView = (SearchView) findViewById(R.id.menu_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void loadFromDbToMemory() {

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateDishListArray();
    }

//    private void setOnClickListener(){
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Dish selectedDish = (Dish) mListView.getItemAtPosition(position);
//                Intent editDishIntent = new Intent(getApplicationContext(), DishDetailActivity.class);
//                editDishIntent.putExtra(Dish.DISH_EDIT_EXTRA, selectedDish.getId());
//                startActivity(editDishIntent);
//            }
//        });
//    }

//    @TODO create where it will redirect how to create new dish
//    public void newDish( View view){
//        Intent newDishIntent = new Intent(this, )
//        startActivity(newDishIntent);
//    }

//    private void initWidgets() {
//        dishListView = findViewById(R.id.menu_items);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adgc, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

//        if ( id == R.id.item_add_dish) {
//            Toast.makeText(getApplicationContext(), "Add dish", Toast.LENGTH_LONG).show();
//            createNewDishDialog();
//        }
        if (id == R.id.item_delete_dish) {
            Toast.makeText(getApplicationContext(), "Delete dish selected", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.item_organize_dish) {
            Toast.makeText(getApplicationContext(), "Organize dish selected", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void createNewDishDialog() {
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
        final View dishPopupView = getLayoutInflater().inflate(R.layout.dish_popup, null);
        mDialogBuilder.setView(dishPopupView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

//        TextInputLayout textInputLayout = (TextInputLayout) mDialog.findViewById(R.id.outlinedTextField_dish_group);
        AutoCompleteTextView chooseGroup = (AutoCompleteTextView) mDialog.findViewById(R.id.dish_group_autocomplete);
        String[] dishGroups = getResources().getStringArray(R.array.dishTypes);

        ArrayAdapter<String> adapterr = new ArrayAdapter<>(this,
                R.layout.dish_group_item, dishGroups);
        chooseGroup.setAdapter(adapterr);

        newDishPopup_name = mDialog.findViewById(R.id.popup_dishName);
        newDishPopup_note = mDialog.findViewById(R.id.popup_dishNote);
        newDishPopup_price = mDialog.findViewById(R.id.popup_dishPrice);

    }

    public void saveDish(View view){

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String name = String.valueOf(newDishPopup_name.getText());
        String note = String.valueOf(newDishPopup_note.getText());
        String price = String.valueOf(newDishPopup_price.getText());

        
        int id = Dish.dishArrayList.size();

        Dish newDish = new Dish(id, name, note, price);
        Dish.dishArrayList.add(newDish);
        sqLiteManager.addDishToDatabase(newDish);
        mDialog.hide();
    }

}