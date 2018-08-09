package com.example.jlee.frigefriend;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.OnClick;


public class ProductInfo extends Activity {

    private static final String TAG = "ProductInfo";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    String jsonStringUserData;
    String jsonStringCat;
    String jsonStringLC;

    private  int imageRes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        // Will display the image and text of the item clicked from add page to proInfo page.
        Intent intent = getIntent();
        addItem addItem1  = intent.getParcelableExtra("addItem");

        imageRes = addItem1.getimageResource();
        String line1 = addItem1.getText1();

        ImageView imageView = findViewById(R.id.textViewImgHolder);
        imageView.setImageResource(imageRes);

        TextView textView1 = findViewById(R.id.textView4);
        textView1.setText(line1);

//        Displays Quantity in dropdown list
        Spinner quantitySpinner = (Spinner) findViewById(R.id.spinnerQuantity);

        ArrayAdapter <String> quantityAdapter = new ArrayAdapter<String>(ProductInfo.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.quantityNum));

        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        quantitySpinner.setAdapter(quantityAdapter);

        Intent intent1 = getIntent();
        jsonStringUserData = intent1.getStringExtra(LoginActivity.USER_DATA);
        jsonStringCat = intent1.getStringExtra(LoginActivity.CAT_DATA);
        jsonStringLC = intent1.getStringExtra(LoginActivity.LC_DATA);


//      When Category is clicked directs to CategoryActivity
        TextView CategoryTextView = (TextView) findViewById(R.id.textViewCategory);
        CategoryTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent CategoryIntent = new Intent(ProductInfo.this, CategoryActivity.class);
                CategoryIntent.putExtra(LoginActivity.USER_DATA, jsonStringUserData);
                CategoryIntent.putExtra(LoginActivity.CAT_DATA, jsonStringCat);
                CategoryIntent.putExtra(LoginActivity.LC_DATA, jsonStringLC);
                ProductInfo.this.startActivity(CategoryIntent);

            }
        });


        // Date picker

        mDisplayDate = findViewById(R.id.textViewDisplayDate);


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick (View view) {

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(ProductInfo.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year, month, day);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }

        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month =month +1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };


//        // Add button directs to Main activity
//        View btn = findViewById(R.id.Button_fab);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ProductInfo.this, MainActivity.class));
//            }
//        });

        // Close button directs to Add activity
        View btnClose = findViewById(R.id.textViewCloseIcon);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ProductInfo.this, addActivity.class));
                finish();
            }
        });


    }





}
