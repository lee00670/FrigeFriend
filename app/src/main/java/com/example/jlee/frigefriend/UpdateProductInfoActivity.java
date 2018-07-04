package com.example.jlee.frigefriend;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class UpdateProductInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_info);

        //        Displays Quantity in dropdown list
        Spinner quantitySpinner = (Spinner) findViewById(R.id.spinnerQuantity);

        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<String>(UpdateProductInfoActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.quantityNum));

        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        quantitySpinner.setAdapter(quantityAdapter);

//      When Category is clicked directs to CategoryActivity
        TextView CategoryTextView = (TextView) findViewById(R.id.textViewCategory);
        CategoryTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent CategoryIntent = new Intent(UpdateProductInfoActivity.this, CategoryActivity.class);
                UpdateProductInfoActivity.this.startActivity(CategoryIntent);
            }
        });
    }

}
