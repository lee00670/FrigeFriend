package com.example.jlee.frigefriend;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateProductInfoActivity extends AppCompatActivity {

    @BindView(R.id.app_bar_update)
    Toolbar toolbar;
    @BindView( (R.id.fab_save))
    FloatingActionButton mFabAddToCart;
    @BindView( (R.id.textViewCategory))
    TextView mTextViewCategory;
    @BindView( (R.id.textViewItemName))
    TextView mTextViewItemName;
    @BindView( (R.id.imageViewCategory))
    ImageView mImageViewCategory;
    @BindView( (R.id.catImage))
    ImageView mImageView3Dots;


    private static final String TAG = "updateProductInfo";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    String jsonFridgeItem;
    String jsonStringCat;
    String jsonStringLC;
    FridgeItem mSelectedItem;
    private DatePicker datePicker;
    FridgeItem mItemCategorySaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_info);
        ButterKnife.bind(this);

        //set the action bar with title
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.editMenu);

        //get the intent data for category and selected fridge item.
        Intent intent = getIntent();
        jsonFridgeItem = intent.getStringExtra(LoginActivity.EDIT_ITEM);
        jsonStringCat = intent.getStringExtra(LoginActivity.CAT_DATA);
        Gson gson = new Gson();
        mSelectedItem = gson.fromJson(jsonFridgeItem, FridgeItem.class);

        initView();

    }

    public void initView(){

        // set item image and name
        mImageViewCategory.setImageResource(mSelectedItem.getCatImg());
        mTextViewItemName.setText(mSelectedItem.getItemName());

        // Displays Quantity in dropdown list
        Spinner quantitySpinner = (Spinner) findViewById(R.id.spinnerQuantity);
        ArrayAdapter <String> quantityAdapter = new ArrayAdapter<String>(UpdateProductInfoActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.quantityNum));
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(quantityAdapter);
        quantitySpinner.setSelection(mSelectedItem.getQuantity());

        // Display unit in spinner
        Spinner unitsSpinner = (Spinner) findViewById(R.id.spinnerUnits);
        ArrayAdapter <String> unitsAdapater = new ArrayAdapter<String>(UpdateProductInfoActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.units));
        unitsAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitsSpinner.setAdapter(unitsAdapater);

        int spinnerPosition = unitsAdapater.getPosition(mSelectedItem.getQuantityUnit());
        unitsSpinner.setSelection(spinnerPosition);
        Log.e("test", "selection: "+spinnerPosition);
        Log.e("test", "getQuantityUnit: "+mSelectedItem.getQuantityUnit());

        // Date picker
        Calendar cal = Calendar.getInstance();
        this.datePicker = (DatePicker) this.findViewById(R.id.date_value);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(mSelectedItem.getExpDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);

//
//
//        TextView btnClose = findViewById(R.id.textViewCloseIcon);
//
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//
//            }
//        });
    }

    @OnClick({R.id.textViewCategory, R.id.catImage})
    public void selectCategory()
    {
        // When Category is clicked directs to CategoryActivity
        Intent CategoryIntent = new Intent(UpdateProductInfoActivity.this, CategoryActivity.class);
        CategoryIntent.putExtra(LoginActivity.CAT_DATA, jsonStringCat);
        CategoryIntent.putExtra(LoginActivity.LC_DATA, jsonStringLC);
        Gson gson = new Gson();
        String jsonEditedItem = gson.toJson(mSelectedItem);
        CategoryIntent.putExtra(LoginActivity.EDIT_ITEM, jsonEditedItem);
        CategoryIntent.putExtra("parentActivityUpdate", true);
        startActivityForResult(CategoryIntent, MainActivity.REQUEST_CODE_EDIT_CAT);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST_CODE_EDIT_CAT) {
            if(resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {
                String jsonEditedItem = data.getStringExtra(LoginActivity.EDIT_ITEM);
                Gson gson = new Gson();
                mItemCategorySaved = gson.fromJson(jsonEditedItem,FridgeItem.class);
                Log.e("test", "updateItem: "+mItemCategorySaved);
                Log.e("test", "update: cat id: "+mItemCategorySaved.getCatID());
                mImageViewCategory.setImageResource(mItemCategorySaved.getCatImg());
            }
        }
    }

    public void saveCurrentItem()
    {
        //save the current date to the item
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.datePicker.getYear(), this.datePicker.getMonth(), this.datePicker.getDayOfMonth());
        mSelectedItem.setExpDate(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));

        //save the quantity spinner
        Spinner quantitySpinner = (Spinner) findViewById(R.id.spinnerQuantity);
        mSelectedItem.setQuantity(quantitySpinner.getSelectedItemPosition());

        //save the unit spinner
        Spinner unitsSpinner = (Spinner) findViewById(R.id.spinnerUnits);
        mSelectedItem.setQuantityUnit(unitsSpinner.getSelectedItem().toString());
        Log.e("test", "quanUnit: "+mSelectedItem.getQuantityUnit());

        //save the category
        if(mItemCategorySaved != null)
        {
            mSelectedItem.setCatID(mItemCategorySaved.getCatID());
            mSelectedItem.setCatImg(mItemCategorySaved.getCatImg());
            Log.e("test", "cat id: "+mSelectedItem.getCatID());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
                } else {
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    Gson gson = new Gson();
                    String jsonEditedItem = gson.toJson(mSelectedItem);
                    upIntent.putExtra(LoginActivity.EDIT_ITEM,jsonEditedItem);
                    setResult(RESULT_OK, upIntent); //go back to main activity
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Log.e("test","back");

        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
        } else {
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Gson gson = new Gson();
            String jsonEditedItem = gson.toJson(mSelectedItem);
            upIntent.putExtra(LoginActivity.EDIT_ITEM,jsonEditedItem);
            setResult(RESULT_OK, upIntent); //go back to main activity
            super.onBackPressed();
        }
    }
    @OnClick(R.id.fab_save)
    public void saveItem()
    {
        saveCurrentItem();
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Gson gson = new Gson();
        String jsonEditedItem = gson.toJson(mSelectedItem);
        upIntent.putExtra(LoginActivity.EDIT_ITEM, jsonEditedItem);
        setResult(RESULT_OK, upIntent); //go back to main activity
        finish();
    }
}
