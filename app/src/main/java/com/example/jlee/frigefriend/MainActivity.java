package com.example.jlee.frigefriend;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.sortDate)
    TextView sortDate;
    @BindView(R.id.sortName)
    TextView sortName;
    @BindView(R.id.sortCat)
    TextView sortCat;
    @BindView (R.id.recyclerView)
    RecyclerView rv;
    String jsonStringUserData = null;
    String jsonStringCat = null;
    String jsonStringLC = null;
    UserData userData;
    List<CategoryData> CategoryData;
    List<LCData> LCData;

    List<FridgeItem> listFridgeItem = new ArrayList<>();
    MainActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        jsonStringUserData = intent.getStringExtra(LoginActivity.USER_DATA);
        jsonStringCat = intent.getStringExtra(LoginActivity.CAT_DATA);
        jsonStringLC = intent.getStringExtra(LoginActivity.LC_DATA);
        Log.e("test", "UserData:" + jsonStringUserData);
        Log.e("test", "Category:" + jsonStringCat);
        Log.e("test", "LC:" + jsonStringLC);

        Gson gson = new Gson();
        userData = gson.fromJson(jsonStringUserData, UserData.class);
        CategoryData = gson.fromJson(jsonStringCat,new TypeToken<List<CategoryData>>() {}.getType());
        LCData = gson.fromJson(jsonStringLC,new TypeToken<List<LCData>>() {}.getType());
        Log.e("test", "MaingActivity: userData:" + userData.toString());
        Log.e("test", "MaingActivity: CategoryData:" + CategoryData.toString());
        Log.e("test", "MaingActivity: LCData:" + LCData.toString());
        listFridgeItem = userData.getFrideItems();
        initializeViews();

    }
    private void initializeViews()
    {
        rv.setHasFixedSize(true);

        //set layout manager
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearlayoutmanager);

        //set adapter
        adapter = new MainActivityAdapter(listFridgeItem, CategoryData, MainActivity.this );
        rv.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cartMenu:
                Intent viewCartIntent = new Intent(this, ViewCart.class);
                viewCartIntent.putExtra(LoginActivity.USER_DATA, jsonStringUserData);
                startActivity(viewCartIntent);
                break;

            case R.id.logoutMenu:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                break;

            default:
                //unknown error
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                DrawableCompat.setTint(drawable,ContextCompat.getColor(getApplicationContext(), color) );
                //drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_IN));
            }
        }
    }
    @OnClick(R.id.sortDate)
    void sortByDate() {
//        Drawable mDrawable = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
//        mDrawable.setColorFilter(new
//                PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY));
//        sortDate.setCompoundDrawablesRelativeWithIntrinsicBounds(mDrawable, 0, 0, 0b0);

        setTextViewDrawableColor(sortDate, R.color.colorAccent );
        setTextViewDrawableColor(sortName, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortCat, R.color.colorPrimaryDark );
        sortDate.setTextColor(getResources().getColor(R.color.colorAccent));
        sortName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortCat.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @OnClick(R.id.sortName)
    void sortByName() {
        setTextViewDrawableColor(sortDate, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortName, R.color.colorAccent );
        setTextViewDrawableColor(sortCat, R.color.colorPrimaryDark );
        sortDate.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortName.setTextColor(getResources().getColor(R.color.colorAccent));
        sortCat.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }
    @OnClick(R.id.sortCat)
    void sortByCat() {
        setTextViewDrawableColor(sortDate, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortName, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortCat, R.color.colorAccent );
        sortDate.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortCat.setTextColor(getResources().getColor(R.color.colorAccent));
    }
}
