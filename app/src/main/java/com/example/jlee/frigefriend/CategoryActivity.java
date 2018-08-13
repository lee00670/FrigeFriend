package com.example.jlee.frigefriend;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.app_bar_cat)
    Toolbar toolbar;
    @BindView(R.id.imageViewClose)
    ImageView mImageClose;
    @BindView(R.id.imageViewCat)
    ImageView mImageViewCat;
    @BindView(R.id.textViewItemName)
    TextView mTextViewItemName;

    private RecyclerView mRecyclerView;
    private categoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    String jsonStringFridgeItem;
    String jsonStringCat;
    String jsonStringLC;
    Boolean bParentActivityUpdate;
    FridgeItem mSelectedItem;
    List<CategoryData> listCategoryData;

    int positionUserClicked = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        //set the action bar with title
        setSupportActionBar(toolbar);
        setTitle("Category");

        Intent intent = getIntent();
        jsonStringFridgeItem = intent.getStringExtra(LoginActivity.EDIT_ITEM);
        jsonStringCat = intent.getStringExtra(LoginActivity.CAT_DATA);
        jsonStringLC = intent.getStringExtra(LoginActivity.LC_DATA);
        bParentActivityUpdate = intent.getBooleanExtra("parentActivityUpdate",false);

        Gson gson = new Gson();
        mSelectedItem = gson.fromJson(jsonStringFridgeItem, FridgeItem.class);
        listCategoryData = gson.fromJson(jsonStringCat,new TypeToken<List<CategoryData>>() {}.getType());

        mRecyclerView = findViewById(R.id.recyclerViewCategory);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new categoryAdapter(listCategoryData);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mImageViewCat.setImageResource(mSelectedItem.getCatImg());
        mTextViewItemName.setText(mSelectedItem.getItemName());

        mAdapter.setOnItemClickListener(new categoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //listCategoryData.get(position).displayCheck(R.drawable.ic_check_black_24dp);
                Log.e("test","position: "+position+" previous: "+ positionUserClicked);
                //listCategoryData.get(positionUserClicked).hideCheck(R.drawable.berries);
                ImageView imageViewCat = findViewById(R.id.imageViewCat);
                imageViewCat.setImageResource(listCategoryData.get(position).getCatImg());
                listCategoryData.get(position).setChecked(true);
                if(positionUserClicked > -1){
                    listCategoryData.get(positionUserClicked).setChecked(false);
                }
                positionUserClicked = position;
                mAdapter.notifyDataSetChanged();
            }
        });


    } // END of protected void onCreate(Bundle savedInstanceState)

    @OnClick({R.id.fab_save_category, R.id.imageViewClose})
    public void saveCategory(View v)
    {
        if(v.getId() == R.id.fab_save_category)
        {
            if(positionUserClicked > -1){
                mSelectedItem.setCatImg(listCategoryData.get(positionUserClicked).getCatImg());
                int catid = listCategoryData.get(positionUserClicked).getCatID();
                mSelectedItem.setCatID(catid);
                Log.e("test", "categoryActivity: cat id: "+listCategoryData.get(positionUserClicked).getCatID());
                Log.e("test", "categoryActivity: cat id: "+catid);
                Log.e("test", "categoryActivity: cat id: "+mSelectedItem.getCatID());
            }
        }

        Intent upIntent = new Intent(this, bParentActivityUpdate?UpdateProductInfoActivity.class:ProductInfo.class);
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Gson gson = new Gson();
        String jsonEditedItem = gson.toJson(mSelectedItem);
        upIntent.putExtra(LoginActivity.EDIT_ITEM,jsonEditedItem);
        Log.e("test", "categoryActivity: jsonEditedItem: "+jsonEditedItem);
        setResult(RESULT_OK, upIntent); //go back to main activity
        finish();
    }



   } // END of public class CategoryActivity extends Activity





