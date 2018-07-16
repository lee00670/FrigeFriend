package com.example.jlee.frigefriend;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

import butterknife.OnClick;

public class CategoryActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
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

        ArrayList<categoryItem> categoryList = new ArrayList<>();
        categoryList.add(new categoryItem(R.drawable.milk, "Milk"));
        categoryList.add(new categoryItem(R.drawable.yogurt, "Yogurt"));
        categoryList.add(new categoryItem(R.drawable.cheese, "Cheese"));
        categoryList.add(new categoryItem(R.drawable.butter, "Butter"));
        categoryList.add(new categoryItem(R.drawable.eggs, "Eggs"));

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new categoryAdapter(categoryList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

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




