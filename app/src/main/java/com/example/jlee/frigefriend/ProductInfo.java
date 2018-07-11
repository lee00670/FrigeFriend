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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.OnClick;


public class ProductInfo extends Activity {

    private static final String TAG = "ProductInfo";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

//        Displays Quantity in dropdown list
        Spinner quantitySpinner = (Spinner) findViewById(R.id.spinnerQuantity);

        ArrayAdapter <String> quantityAdapter = new ArrayAdapter<String>(ProductInfo.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.quantityNum));

        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        quantitySpinner.setAdapter(quantityAdapter);

//      When Category is clicked directs to CategoryActivity
        TextView CategoryTextView = (TextView) findViewById(R.id.textViewCategory);
        CategoryTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent CategoryIntent = new Intent(ProductInfo.this, CategoryActivity.class);
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

    }


}
