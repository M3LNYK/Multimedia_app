package com.example.pdfmenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pdfmenu.dataBase.Dish.Dish;
import com.example.pdfmenu.dataBase.Dish.DishListAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ADGC_Activity extends AppCompatActivity {

    ListView mListView;
    private EditText newDishPopup_name, newDishPopup_price, newDishPopup_note;
    private AutoCompleteTextView newDishPopup_group;


    private AlertDialog mDialog;
    private AlertDialog mDialogEdit;
    private Dish selectedDish;

    private boolean clicked = false;

    FloatingActionButton fabOpen;
    ExtendedFloatingActionButton fabEdit;
    FloatingActionButton fabContinue;

    private ChipGroup chipFilterGroup;
    private String filterStatus = "All";
    private SearchView searchView;

    private MaterialCardView editTextCardView, continueTextCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adgc);

//        getApplicationContext().deleteDatabase("DishDB");

        mListView = findViewById(R.id.menu_items);


        loadFromDbToMemory();
        setDishAdapter();

        fabOpen = findViewById(R.id.floating_open);

        fabEdit = findViewById(R.id.floating_edit);

        fabContinue = findViewById(R.id.floating_continue);

        editTextCardView = findViewById(R.id.text_card_view_edit);

        continueTextCardView = findViewById(R.id.text_card_view_continue);


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
                materialDialogOpen();

            }
        });

//        For item in List
        setOnClickListener();
        initSearchWidgets();
        initChips();
        getCheckedFilterChips();
    }

    //Alert dialog to proceed
    private void materialDialogOpen() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Continue to menus?")
                .setMessage(R.string.message_text)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        proceed to next activity
                        Intent intent = new Intent(ADGC_Activity.this, TemplateMenuActivity.class);
//                        Intent intent = new Intent(ADGC_Activity.this, CreateMenuActivity.class);
                        ADGC_Activity.this.startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.show();
    }

    //For chips under searchbar
    private void filterList(String status) {
        ArrayList<Dish> filteredDish = new ArrayList<>();
        if (status.contains("All")) {
            setDishAdapter();
            searchView.setQuery("", false);
            searchView.clearFocus();
        } else {
            for (Dish dish : Dish.nonDeletedDishes()) {
                if (dish.getGroup().contains(status)) {
                    filteredDish.add(dish);
                }
            }
            DishListAdapter adapter = new DishListAdapter(getApplicationContext(), R.layout.adapter_view_layout, filteredDish);
            mListView.setAdapter(adapter);
        }
    }

    //For chips under searchbar
    private void getCheckedFilterChips() {
        for (int i = 0; i < chipFilterGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipFilterGroup.getChildAt(i);

            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        filterStatus = compoundButton.getText().toString();
                    }
                    filterList(filterStatus);
                }
            });
        }
    }

    //For chips under searchbar
    private void initChips() {
        chipFilterGroup = findViewById(R.id.chip_filter_group);
    }

    //For floating button
    private void anAddButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        clicked = !clicked;

    }

    //For floating buttons
    private void setAnimation(Boolean click) {
        Animation rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_anim);
        Animation rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_anim);
        Animation fromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_anim);
        Animation toBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_anim);
        if (!click) {
            fabEdit.startAnimation(fromBottom);
            fabContinue.startAnimation(fromBottom);
            fabOpen.startAnimation(rotateOpen);
            editTextCardView.startAnimation(fromBottom);
            continueTextCardView.startAnimation(fromBottom);
        } else {
            fabEdit.startAnimation(toBottom);
            fabContinue.startAnimation(toBottom);
            fabOpen.startAnimation(rotateClose);
            editTextCardView.startAnimation(toBottom);
            continueTextCardView.startAnimation(toBottom);
        }
    }

    //For floating buttons
    private void setVisibility(Boolean click) {
        if (!click) {
            fabEdit.setVisibility(View.VISIBLE);
            fabContinue.setVisibility(View.VISIBLE);
            editTextCardView.setVisibility(View.VISIBLE);
            continueTextCardView.setVisibility(View.VISIBLE);
        } else {
            fabEdit.setVisibility(View.INVISIBLE);
            fabContinue.setVisibility(View.INVISIBLE);
            editTextCardView.setVisibility(View.INVISIBLE);
            continueTextCardView.setVisibility(View.INVISIBLE);
        }
    }

    //For floating buttons
    private void setClickable(Boolean click) {
        if (!click) {
            fabEdit.setClickable(true);
            fabContinue.setClickable(true);
        } else {
            fabEdit.setClickable(false);
            fabContinue.setClickable(false);
        }
    }


    //For Search bar
    private void initSearchWidgets() {
        searchView = findViewById(R.id.menu_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<Dish> filteredDish = new ArrayList<>();
                for (Dish dish : Dish.nonDeletedDishes()) {
                    if (filterStatus.toLowerCase().equals("All".toLowerCase())) {
                        if (dish.getName().toLowerCase().contains(s.toLowerCase())) {
                            filteredDish.add(dish);
                        }
                    } else {
                        if(dish.getGroup().toLowerCase().contains(filterStatus.toLowerCase()) && dish.getName().toLowerCase().contains(s.toLowerCase())){
                            filteredDish.add(dish);
                        }
                    }
                }

                DishListAdapter adapter = new DishListAdapter(getApplicationContext(), R.layout.adapter_view_layout, filteredDish);
                mListView.setAdapter(adapter);

                return false;
            }
        });
    }


    //For DB
    private void loadFromDbToMemory() {

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateDishListArray();
    }


    //For item in list
    private void setOnClickListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Dish selectedDish = (Dish) mListView.getItemAtPosition(position);
                createEditDishDialog(selectedDish.getId());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setDishAdapter();
    }


    //For db
    private void setDishAdapter() {
        DishListAdapter adapter = new DishListAdapter(getApplicationContext(), R.layout.adapter_view_layout, Dish.nonDeletedDishes());
        mListView.setAdapter(adapter);
    }


    //For options menu on adgc screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adgc, menu);
        return true;
    }

    //For options menu on adgc screen
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_delete_dish) {
//            Toast.makeText(getApplicationContext(), "Delete dish selected", Toast.LENGTH_LONG).show();
            confirmDishDelete();
        }
        if (id == R.id.item_organize_dish) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Organize dishes", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        return super.onOptionsItemSelected(item);
    }


    //For options menu
    private void deleteAllDish() {
        for (Dish dish : Dish.nonDeletedDishes()){
            dish.setDeleted(new Date());
            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
            sqLiteManager.updateDishInDB(dish);
        }
        setDishAdapter();
    }

    //For options menu
    private void confirmDishDelete() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Delete all?")
                .setMessage(R.string.message_delete_text)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAllDish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.show();
    }


    //For add new dish popup
    public void createNewDishDialog() {
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
        final View dishPopupView = getLayoutInflater().inflate(R.layout.dish_popup, null);
        mDialogBuilder.setView(dishPopupView);
        mDialog = mDialogBuilder.create();
        mDialog.show();

        newDishPopup_group = mDialog.findViewById(R.id.dish_group_autocomplete);
        String[] dishGroups = getResources().getStringArray(R.array.dishTypes);

        ArrayAdapter<String> adapterr = new ArrayAdapter<>(this,
                R.layout.dish_group_item, dishGroups);
        newDishPopup_group.setAdapter(adapterr);

        newDishPopup_name = mDialog.findViewById(R.id.popup_dishName);
        newDishPopup_note = mDialog.findViewById(R.id.popup_dishNote);
        newDishPopup_price = mDialog.findViewById(R.id.popup_dishPrice);

    }


    //For edit dish popup
    public void createEditDishDialog(int dishID) {
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
        final View dishPopupView = getLayoutInflater().inflate(R.layout.dish_popup_edit, null);
        mDialogBuilder.setView(dishPopupView);
        mDialogEdit = mDialogBuilder.create();
        mDialogEdit.show();

        newDishPopup_group = mDialogEdit.findViewById(R.id.dish_edit_group_autocomplete);
        String[] dishGroups = getResources().getStringArray(R.array.dishTypes);

        ArrayAdapter<String> adapterr = new ArrayAdapter<>(this,
                R.layout.dish_group_item, dishGroups);
        newDishPopup_group.setAdapter(adapterr);

        newDishPopup_name = mDialogEdit.findViewById(R.id.popup_edit_dishName);
        newDishPopup_note = mDialogEdit.findViewById(R.id.popup_edit_dishNote);
        newDishPopup_price = mDialogEdit.findViewById(R.id.popup_edit_dishPrice);

        selectedDish = Dish.getDishForID(dishID);

        if (selectedDish != null) {
            newDishPopup_name.setText(selectedDish.getName());
            newDishPopup_note.setText(selectedDish.getNote());
            newDishPopup_price.setText(selectedDish.getPrice());
            newDishPopup_group.setAdapter(adapterr);
            newDishPopup_group.setText(selectedDish.getGroup());
        }
    }

    //Button to save dish in popups
    public void saveDish(View view) {

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String name = String.valueOf(newDishPopup_name.getText());
        String note = String.valueOf(newDishPopup_note.getText());
        String price = String.valueOf(newDishPopup_price.getText());
        String group = String.valueOf(newDishPopup_group.getText());

        if (selectedDish == null) {
            int id = Dish.dishArrayList.size();

            Dish newDish = new Dish(id, group, name, note, price);
            Dish.dishArrayList.add(newDish);
            sqLiteManager.addDishToDatabase(newDish);
        } else {
            selectedDish.setGroup(group);
            selectedDish.setName(name);
            selectedDish.setNote(note);
            selectedDish.setPrice(price);
            sqLiteManager.updateDishInDB(selectedDish);
        }
        setDishAdapter();
        close_popup();
    }


    //For popups
    public void close_popup() {
        if (mDialog != null) {
            mDialog.hide();
            searchView.clearFocus();
        }
        if (mDialogEdit != null) {
            mDialogEdit.hide();
            searchView.clearFocus();
        }
    }


    //Button to delete dish in popup
    public void deleteDish(View view) {
        selectedDish.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.updateDishInDB(selectedDish);
        setDishAdapter();
        close_popup();
    }
}