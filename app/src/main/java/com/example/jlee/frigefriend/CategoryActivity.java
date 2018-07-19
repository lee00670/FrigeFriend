package com.example.jlee.frigefriend;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.Scanner;

import butterknife.OnClick;

public class CategoryActivity extends Activity {

    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    private categoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    String jsonStringUserData;
    String jsonStringCat;
    String jsonStringLC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent intent = getIntent();
        jsonStringUserData = intent.getStringExtra(LoginActivity.USER_DATA);
        jsonStringCat = intent.getStringExtra(LoginActivity.CAT_DATA);
        jsonStringLC = intent.getStringExtra(LoginActivity.LC_DATA);

//        findViewById(R.id.headerDairy);

        final ArrayList<categoryItem> categoryList = new ArrayList<>();
        categoryList.add(new categoryItem(R.drawable.milk, "Milk"));
        categoryList.add(new categoryItem(R.drawable.yogurt, "Yogurt"));
        categoryList.add(new categoryItem(R.drawable.cheese, "Cheese"));
        categoryList.add(new categoryItem(R.drawable.butter, "Butter"));
        categoryList.add(new categoryItem(R.drawable.eggs, "Eggs"));

       categoryList.add(new categoryItem(R.drawable.pork, "Beef"));
       categoryList.add(new categoryItem(R.drawable.chicken, "Chicken"));
       categoryList.add(new categoryItem(R.drawable.pork, "Lamb"));
       categoryList.add(new categoryItem(R.drawable.chicken, "Turkey"));
       categoryList.add(new categoryItem(R.drawable.pork, "Pork"));
       categoryList.add(new categoryItem(R.drawable.sausague, "Sausages"));
       categoryList.add(new categoryItem(R.drawable.bacon, "Bacon"));
       categoryList.add(new categoryItem(R.drawable.salami, "Salami"));
       categoryList.add(new categoryItem(R.drawable.fish, "Fish"));
       categoryList.add(new categoryItem(R.drawable.othermeat, "Other Meat"));

       categoryList.add(new categoryItem(R.drawable.apple, "Apple"));
       categoryList.add(new categoryItem(R.drawable.pineapple, "Pineapple"));
       categoryList.add(new categoryItem(R.drawable.pear, "Pear"));
       categoryList.add(new categoryItem(R.drawable.orange, "Oranges"));
       categoryList.add(new categoryItem(R.drawable.lemon, "Lemon"));
       categoryList.add(new categoryItem(R.drawable.melon,"Melon"));
       categoryList.add(new categoryItem(R.drawable.kiwi, "Kiwi"));
       categoryList.add(new categoryItem(R.drawable.grapes, "Grapes"));
       categoryList.add(new categoryItem(R.drawable.strawberry, "Strawberries"));
       categoryList.add(new categoryItem(R.drawable.berries, "Berries"));
       categoryList.add(new categoryItem(R.drawable.avocado, "Avocado"));
       categoryList.add(new categoryItem(R.drawable.otherfruits, "Other Fruit"));

       categoryList.add(new categoryItem(R.drawable.cucummber, "Cucumber"));
       categoryList.add(new categoryItem(R.drawable.broccoli, "Broccoli"));
       categoryList.add(new categoryItem(R.drawable.carrots, "Carrots"));
       categoryList.add(new categoryItem(R.drawable.pepper, "Pepper"));
       categoryList.add(new categoryItem(R.drawable.lettuce, "Lettuce"));
       categoryList.add(new categoryItem(R.drawable.tomato, "Tomatoes"));
       categoryList.add(new categoryItem(R.drawable.potato, "Potatoes"));
       categoryList.add(new categoryItem(R.drawable.mushroom, "Mushrooms"));
       categoryList.add(new categoryItem(R.drawable.garlic, "Garlic"));
       categoryList.add(new categoryItem(R.drawable.ginger, "Ginger"));
       categoryList.add(new categoryItem(R.drawable.onion, "Onions"));
       categoryList.add(new categoryItem(R.drawable.chili, "Chili"));
       categoryList.add(new categoryItem(R.drawable.corn, "Corn"));
       categoryList.add(new categoryItem(R.drawable.peas, "Peas"));
       categoryList.add(new categoryItem(R.drawable.eggplant, "Eggplant"));
       categoryList.add(new categoryItem(R.drawable.herbs, "Herbs"));
       categoryList.add(new categoryItem(R.drawable.otherveggies, "Other Vegetables"));

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new categoryAdapter(categoryList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



        mAdapter.setOnItemClickListener(new categoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                categoryList.get(position).changetext1("Clicked");
                categoryList.get(position).displayCheck(R.drawable.ic_check_black_24dp);
                mAdapter.notifyItemChanged(position);
            }
        });



        TextView btn = findViewById(R.id.textViewCloseIcon);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this, ProductInfo.class));
            }
        });


        View btnAdd = findViewById(R.id.Button_fab);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this, ProductInfo.class));
            }
        });

    }



//    @OnClick(R.id.Button_fab)
//    public void addItem()
//    {
//        Intent addItemIntent = new Intent(this, ProductInfo.class);
//        addItemIntent.putExtra(LoginActivity.USER_DATA, jsonStringUserData);
//        addItemIntent.putExtra(LoginActivity.CAT_DATA, jsonStringCat);
//        addItemIntent.putExtra(LoginActivity.LC_DATA, jsonStringLC);
//        startActivity(addItemIntent);
//    }



   }





