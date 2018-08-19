package com.example.jlee.frigefriend;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProductInfo extends AppCompatActivity {

    private static final String TAG = "ProductInfo";

    private TextView mDisplayDate;
    private DatePicker datePicker;

    String jsonStringLC;
    String jsonStringCatData;
    String jsonStringUserData;
    FridgeItem mNewItem, mItemCategorySaved;

    private  int imageRes;
    @BindView( (R.id.textViewCategory))
    TextView mTextViewCategory;
    @BindView( (R.id.catImage))
    ImageView mImageView3Dots;
    @BindView(R.id.app_bar_add)
    Toolbar toolbar;
    @BindView(R.id.imageViewClose)
    ImageView closeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        ButterKnife.bind(this);

        //set the action bar with title
        setSupportActionBar(toolbar);
        setTitle("Category");

        // Get intent: Will display the image and text of the item clicked from add page to proInfo page.
        imageRes = getIntent().getIntExtra("cat_image", 1);
        String sCatName = getIntent().getStringExtra("cat_name");
        int iCatID = getIntent().getIntExtra("cat_id",0);
        jsonStringCatData = getIntent().getStringExtra(LoginActivity.CAT_DATA);
        jsonStringLC = getIntent().getStringExtra(LoginActivity.LC_DATA);
        jsonStringUserData = getIntent().getStringExtra(LoginActivity.USER_DATA);

        ImageView imageView = findViewById(R.id.imageViewCat);
        imageView.setImageResource(imageRes);

        EditText editTextName = findViewById(R.id.textViewItemName);
        editTextName.setText("");
        editTextName.append(sCatName);

//        Displays Quantity in dropdown list
        Spinner quantitySpinner = (Spinner) findViewById(R.id.spinnerQuantity);
        ArrayAdapter <String> quantityAdapter = new ArrayAdapter<String>(ProductInfo.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.quantityNum));
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(quantityAdapter);

        // Display unit in spinner
        Spinner unitsSpinner = (Spinner) findViewById(R.id.spinnerUnits);
        ArrayAdapter <String> unitsAdapater = new ArrayAdapter<String>(ProductInfo.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.units));
        unitsAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitsSpinner.setAdapter(unitsAdapater);


        // Date picker
        Calendar cal = Calendar.getInstance();
        this.datePicker = (DatePicker) this.findViewById(R.id.date_value);

        cal.set(this.datePicker.getYear(), this.datePicker.getMonth(), this.datePicker.getDayOfMonth());
        makeItemID();
        mNewItem = new FridgeItem(makeItemID(),
                sCatName,
                iCatID,
                imageRes,
                0,
                "unit",
                new SimpleDateFormat("yyyyMMdd").format(cal.getTime()),
                0);

    }

    private int makeItemID(){

        String dateTime = getCurrentTime();

        int itemID=0;
        try {
            itemID = Integer.parseInt(dateTime);
            Log.e("test", "makeItemID2: "+itemID);
            while (isUsed(itemID))
            {
                dateTime = getCurrentTime();
                try{
                    itemID = Integer.parseInt(dateTime);
                    Log.e("test", "makeItemID3: "+itemID);
                }catch (NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
            }

        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        return itemID;
    }
    private String getCurrentTime()
    {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddHHmmss");
        return dateFormat.format(currentDate);
    }
    private boolean isUsed(int itemID){
        Gson gson = new Gson();
        UserData userData = gson.fromJson(jsonStringUserData, UserData.class);
        List<FridgeItem> listFridgeItem = userData.getFrideItems();
        boolean found = false;
        for(FridgeItem item : listFridgeItem){
            if(item.getItemID() == itemID)
            {
                found = true;
                Log.e("test", "ItemID is used. try again!!");
                break;
            }
        }
        return found;
    }
    @OnClick({R.id.textViewCategory, R.id.catImage})
    public void selectCategory()
    {
        // When Category is clicked directs to CategoryActivity
        Intent CategoryIntent = new Intent(ProductInfo.this, CategoryActivity.class);
        CategoryIntent.putExtra(LoginActivity.CAT_DATA, jsonStringCatData);
        CategoryIntent.putExtra(LoginActivity.LC_DATA, jsonStringLC);
        Gson gson = new Gson();
        //new FridgeItem(14, "Coffee Milk", 0, R.drawable.milk, 1, "Kilogram(s)", "20170801",0));
        String jsonEditedItem = gson.toJson(mNewItem);
        CategoryIntent.putExtra(LoginActivity.EDIT_ITEM, jsonEditedItem);
        startActivityForResult(CategoryIntent, MainActivity.REQUEST_CODE_ADD);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST_CODE_ADD) {
            if(resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {
                String jsonEditedItem = data.getStringExtra(LoginActivity.EDIT_ITEM);
                Gson gson = new Gson();
                mItemCategorySaved = gson.fromJson(jsonEditedItem,FridgeItem.class);
                Log.e("test", "updateItem: "+mItemCategorySaved);
                Log.e("test", "update: cat id: "+mItemCategorySaved.getCatID());
                ImageView imageView = findViewById(R.id.imageViewCat);
                imageView.setImageResource(mItemCategorySaved.getCatImg());
            }
        }
    }

    @OnClick({R.id.fab_add, R.id.imageViewClose})
    public void saveItem(View v)
    {
        if(v.getId() == R.id.fab_add)
        {
            saveCurrentItem();
            Intent upIntent = NavUtils.getParentActivityIntent(this);
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Gson gson = new Gson();
            String jsonEditedItem = gson.toJson(mNewItem);
            Log.e("test", "productInfo: new item: "+ mNewItem);
            upIntent.putExtra(LoginActivity.EDIT_ITEM, jsonEditedItem);
            setResult(RESULT_OK, upIntent); //go back to main activity
        }
        else{
            setResult(RESULT_CANCELED);
        }

        finish();
    }
    public void saveCurrentItem()
    {
        //save the current date to the item
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.datePicker.getYear(), this.datePicker.getMonth(), this.datePicker.getDayOfMonth());
        mNewItem.setExpDate(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));

        //set item name
        EditText editTextName = findViewById(R.id.textViewItemName);
        mNewItem.setItemName(editTextName.getText().toString());


        //save the quantity spinner
        Spinner quantitySpinner = (Spinner) findViewById(R.id.spinnerQuantity);
        mNewItem.setQuantity(quantitySpinner.getSelectedItemPosition());

        //save the unit spinner
        Spinner unitsSpinner = (Spinner) findViewById(R.id.spinnerUnits);
        mNewItem.setQuantityUnit(unitsSpinner.getSelectedItem().toString());
        Log.e("test", "quanUnit: "+mNewItem.getQuantityUnit());

        //save the category
        if(mItemCategorySaved != null)
        {
            mNewItem.setCatID(mItemCategorySaved.getCatID());
            mNewItem.setCatImg(mItemCategorySaved.getCatImg());
            Log.e("test", "cat id: "+mNewItem.getCatID());
        }


    }
}
