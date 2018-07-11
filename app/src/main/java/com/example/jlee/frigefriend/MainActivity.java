package com.example.jlee.frigefriend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.app.SearchManager;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
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
    @BindView( (R.id.floatingActionButton))
    FloatingActionButton mFab;

    String jsonStringUserData = null;
    String jsonStringCat = null;
    String jsonStringLC = null;
    UserData userData;
    List<CategoryData> CategoryData;
    List<LCData> LCData;

    List<FridgeItem> listFridgeItem = new ArrayList<>();
    MainActivityAdapter adapter;
    SearchView searchView;


    private static final int SORT_BY_DATE = 0;
    private static final int SORT_BY_NAME = 1;
    private static final int SORT_BY_CAT = 2;
    private int sort_by = SORT_BY_DATE;

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

        listFridgeItem.add(new FridgeItem(2, "Coffee Milk", 17, R.drawable.milk, 1, "cup", "20170801"));
        listFridgeItem.add(new FridgeItem(3, "Strawberry Milk", 17, R.drawable.milk, 1, "cup", "20170801"));
        listFridgeItem.add(new FridgeItem(4, "Coconut Milk", 17, R.drawable.milk, 1, "cup", "20180801"));
        listFridgeItem.add(new FridgeItem(5, "Brown Eggs", 2, R.drawable.ic_add_circle_outline_black_24dp, 1, "cup", "20180803"));
        listFridgeItem.add(new FridgeItem(5, "Brown Eggs1", 2, R.drawable.ic_add_circle_outline_black_24dp, 1, "cup", "20180715"));
        listFridgeItem.add(new FridgeItem(5, "Brown Eggs2", 2, R.drawable.ic_add_circle_outline_black_24dp, 1, "cup", "20180714"));
        listFridgeItem.add(new FridgeItem(5, "White Egg", 2, R.drawable.ic_add_circle_outline_black_24dp, 1, "cup", "20180716"));


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

        sortData(SORT_BY_DATE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.searchMenu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<FridgeItem> newList = new ArrayList<>();

        for(FridgeItem item : listFridgeItem )
        {
            String name = item.getItemName().toLowerCase();
            if(name.contains(newText))
            {
                newList.add(item);
            }

        }
        adapter.setFilter(newList);
        return false;
    }

//    private void changeSearchViewTextColor(View view)
//    {
//        if(view != null)
//        {
//            if(view instanceof TextView)
//            {
//                ((TextView) view).setTextColor(Color.BLUE);
//                return;
//            }else if(view instanceof ViewGroup)
//            {
//                ViewGroup viewGroup = (ViewGroup) view;
//                for(int i=0; i<viewGroup.getChildCount(); i++)
//                {
//                    changeSearchViewTextColor(viewGroup.getChildAt(i));
//                }
//            }
//        }
//    }

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
    private void sortData(int sortOrder)
    {
        switch(sortOrder)
        {
            case SORT_BY_DATE:
                Collections.sort(listFridgeItem, new Comparator<FridgeItem>() {
                    @Override
                    public int compare(FridgeItem o1, FridgeItem o2) {

                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return (o1.getExpDate()).compareTo(o2.getExpDate());
                    }
                });
                break;
            case SORT_BY_NAME:
                Collections.sort(listFridgeItem, new Comparator<FridgeItem>() {
                    @Override
                    public int compare(FridgeItem o1, FridgeItem o2) {

                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return (o1.getItemName()).compareTo(o2.getItemName());
                    }
                });

                break;
            case SORT_BY_CAT:
                Collections.sort(listFridgeItem, new Comparator<FridgeItem>() {
                    @Override
                    public int compare(FridgeItem o1, FridgeItem o2) {

                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return o1.getCatID() < o2.getCatID() ? -1 : (o1.getCatID() > o2.getCatID()) ? 1 : 0;
                    }
                });
                break;
            default:
        }
        //adapter
        adapter = new MainActivityAdapter(listFridgeItem, CategoryData, MainActivity.this);
        rv.setAdapter(adapter);
    }


    @OnClick(R.id.sortDate)
    void sortByDate() {
//        Drawable mDrawable = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
//        mDrawable.setColorFilter(new
//                PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY));
//        sortDate.setCompoundDrawablesRelativeWithIntrinsicBounds(mDrawable, 0, 0, 0b0);
        sortData(SORT_BY_DATE);
        setTextViewDrawableColor(sortDate, R.color.colorAccent );
        setTextViewDrawableColor(sortName, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortCat, R.color.colorPrimaryDark );
        sortDate.setTextColor(getResources().getColor(R.color.colorAccent));
        sortName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortCat.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @OnClick(R.id.sortName)
    void sortByName() {
        sortData(SORT_BY_NAME);
        setTextViewDrawableColor(sortDate, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortName, R.color.colorAccent );
        setTextViewDrawableColor(sortCat, R.color.colorPrimaryDark );
        sortDate.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortName.setTextColor(getResources().getColor(R.color.colorAccent));
        sortCat.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }
    @OnClick(R.id.sortCat)
    void sortByCat() {
        sortData(SORT_BY_CAT);
        setTextViewDrawableColor(sortDate, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortName, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortCat, R.color.colorAccent );
        sortDate.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortCat.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    @OnClick(R.id.floatingActionButton)
    public void addItem()
    {
        Intent addItemIntent = new Intent(this, ProductInfo.class);
       // addItemIntent.putExtra(LoginActivity.USER_DATA, jsonStringUserData);
        //addItemIntent.putExtra(LoginActivity.CAT_DATA, jsonStringCat);
        //addItemIntent.putExtra(LoginActivity.LC_DATA, jsonStringLC);
        startActivity(addItemIntent);
    }
}
