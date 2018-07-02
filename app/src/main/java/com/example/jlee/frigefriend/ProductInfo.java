package com.example.jlee.frigefriend;

import android.os.Bundle;
import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class ProductInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        Spinner quantitySpinner = (Spinner) findViewById(R.id.spinnerQuantity);

        ArrayAdapter <String> quantityAdapter = new ArrayAdapter<String>(ProductInfo.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.quantityNum));

        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        quantitySpinner.setAdapter(quantityAdapter);


    }

}
