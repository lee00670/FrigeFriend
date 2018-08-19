package com.example.jlee.frigefriend;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;




public class addActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
//    public RecyclerView.Adapter mAdapter;
    private addAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<addItem> maddList;
    List<CategoryData> listCategoryData;
    FridgeItem mNewItem;

    List<FridgeItem> listFridgeItem = new ArrayList<>();
    private List<FridgeItem> currentSelectedItems = new ArrayList<>();
    private String jsonStringCatData;
    private String jsonStringLCatData;
    private String jsonStringUserData;
    private int sort_by = MainActivity.SORT_BY_CAT;

    @BindView(R.id.app_bar_add)
    Toolbar toolbar;

    @BindView(R.id.sortAlpha)
    TextView sortAlpha;
    @BindView(R.id.sortCat1)
    TextView sortCat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        //set the action bar with title
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.addMenu);

        //get the category list from the intent
        Intent intent = getIntent();
        jsonStringCatData = intent.getStringExtra(LoginActivity.CAT_DATA);
        jsonStringLCatData = intent.getStringExtra(LoginActivity.LC_DATA);
        jsonStringUserData = intent.getStringExtra(LoginActivity.USER_DATA);

        Log.e("test","addActivity cat_data :"+jsonStringCatData);
        Gson gson = new Gson();
        listCategoryData = gson.fromJson(jsonStringCatData,new TypeToken<List<CategoryData>>() {}.getType());

        createAddList();
        buildRecyclerView();
        sortByCat();

    // Search Bar
        EditText editTextSearch = findViewById(R.id.editTextSearchBar);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

    } // END of  protected void onCreate(Bundle savedInstanceState)

    /*
     * Click Listener for sorting by category
     * */
    @OnClick(R.id.sortCat1)
    void sortByCat() {
        sortList(MainActivity.SORT_BY_CAT);
        sort_by = MainActivity.SORT_BY_DATE;
        setTextViewDrawableColor(sortCat, R.color.colorAccent );
        setTextViewDrawableColor(sortAlpha, R.color.colorPrimaryDark );
        sortCat.setTextColor(getResources().getColor(R.color.colorAccent));
        sortAlpha.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    /*
     * Click Listener for sorting by category
     * */
    @OnClick(R.id.sortAlpha)
    void sortByAlpha() {
        sortList(MainActivity.SORT_BY_NAME);
        sort_by = MainActivity.SORT_BY_NAME;
        setTextViewDrawableColor(sortCat, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortAlpha, R.color.colorAccent );
        sortCat.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortAlpha.setTextColor(getResources().getColor(R.color.colorAccent));
    }
    // To change color when on click Alpha or Cat.
    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                DrawableCompat.setTint(drawable, ContextCompat.getColor(getApplicationContext(), color) );
                //drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_IN));
            }
        }
    }


    // search bar
    private void filter (String text){
        List<CategoryData>  filteredList = new ArrayList<>();

        for (CategoryData item: listCategoryData){
            if (item.getCatName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

   public void createAddList(){
//       ArrayList<addItem> addList = new ArrayList<>();
//       maddList =new ArrayList<>();
//
//       maddList.add(new addItem(R.drawable.milk, "Milk", "dairy"));
//       maddList.add(new addItem(R.drawable.yogurt, "Yogurt", "dairy"));
//       maddList.add(new addItem(R.drawable.cheese, "Cheese", "dairy"));
//       maddList.add(new addItem(R.drawable.butter, "Butter", "dairy"));
//       maddList.add(new addItem(R.drawable.eggs, "Eggs", "dairy"));
//
//       maddList.add(new addItem(R.drawable.pork, "Beef", "meat"));
//       maddList.add(new addItem(R.drawable.chicken, "Chicken", "meat"));
//       maddList.add(new addItem(R.drawable.pork, "Lamb", "meat"));
//       maddList.add(new addItem(R.drawable.chicken, "Turkey", "meat"));
//       maddList.add(new addItem(R.drawable.pork, "Pork", "meat"));
//       maddList.add(new addItem(R.drawable.sausague, "Sausages", "meat"));
//       maddList.add(new addItem(R.drawable.bacon, "Bacon", "meat"));
//       maddList.add(new addItem(R.drawable.salami, "Salami", "meat"));
//       maddList.add(new addItem(R.drawable.fish, "Fish", "meat"));
//       maddList.add(new addItem(R.drawable.othermeat, "Other Meat", "meat"));
//
//       maddList.add(new addItem(R.drawable.apple, "Apple" , "fruit"));
//       maddList.add(new addItem(R.drawable.pineapple, "Pineapple", "fruit"));
//       maddList.add(new addItem(R.drawable.pear, "Pear", "fruit"));
//       maddList.add(new addItem(R.drawable.orange, "Oranges", "fruit"));
//       maddList.add(new addItem(R.drawable.lemon, "Lemon", "fruit"));
//       maddList.add(new addItem(R.drawable.melon,"Melon", "fruit"));
//       maddList.add(new addItem(R.drawable.kiwi, "Kiwi", "fruit"));
//       maddList.add(new addItem(R.drawable.grapes, "Grapes", "fruit"));
//       maddList.add(new addItem(R.drawable.strawberry, "Strawberries", "fruit"));
//       maddList.add(new addItem(R.drawable.berries, "Berries", "fruit"));
//       maddList.add(new addItem(R.drawable.avocado, "Avocado", "fruit"));
//       maddList.add(new addItem(R.drawable.otherfruits, "Other Fruit", "fruit"));
//
//       maddList.add(new addItem(R.drawable.cucummber, "Cucumber", "veggies"));
//       maddList.add(new addItem(R.drawable.broccoli, "Broccoli", "veggies"));
//       maddList.add(new addItem(R.drawable.carrots, "Carrots", "veggies"));
//       maddList.add(new addItem(R.drawable.pepper, "Pepper", "veggies"));
//       maddList.add(new addItem(R.drawable.lettuce, "Lettuce", "veggies"));
//       maddList.add(new addItem(R.drawable.tomato, "Tomatoes", "veggies"));
//       maddList.add(new addItem(R.drawable.potato, "Potatoes", "veggies"));
//       maddList.add(new addItem(R.drawable.mushroom, "Mushrooms", "veggies"));
//       maddList.add(new addItem(R.drawable.garlic, "Garlic", "veggies"));
//       maddList.add(new addItem(R.drawable.ginger, "Ginger", "veggies"));
//       maddList.add(new addItem(R.drawable.onion, "Onions", "veggies"));
//       maddList.add(new addItem(R.drawable.chili, "Chili", "veggies"));
//       maddList.add(new addItem(R.drawable.corn, "Corn", "veggies"));
//       maddList.add(new addItem(R.drawable.peas, "Peas", "veggies"));
//       maddList.add(new addItem(R.drawable.eggplant, "Eggplant", "veggies"));
//       maddList.add(new addItem(R.drawable.herbs, "Herbs", "veggies"));
//       maddList.add(new addItem(R.drawable.otherveggies, "Other Vegetables", "veggies"));
//
//       maddList.add(new addItem(R.drawable.bread, "Bread", "bakery"));
//       maddList.add(new addItem(R.drawable.bagel, "Bagel", "bakery"));
//       maddList.add(new addItem(R.drawable.donuts, "Doughnuts", "bakery"));
//       maddList.add(new addItem(R.drawable.cake, "Cake", "bakery"));
//       maddList.add(new addItem(R.drawable.cookies, "Cookies", "bakery"));
//       maddList.add(new addItem(R.drawable.chocolate, "Chocolate", "bakery"));
//       maddList.add(new addItem(R.drawable.pie, "Pie", "bakery"));
//       maddList.add(new addItem(R.drawable.icecream, "Ice Cream", "bakery"));
//       maddList.add(new addItem(R.drawable.otherbakery, "Other Bakery&Sweets", "bakery"));
//
//       maddList.add(new addItem(R.drawable.softdrink, "Soft Drinks", "beverages"));
//       maddList.add(new addItem(R.drawable.soda, "Soda", "beverages"));
//       maddList.add(new addItem(R.drawable.jucie, "Juice", "beverages"));
//       maddList.add(new addItem(R.drawable.icedtea, "Iced Tea", "beverages"));
//       maddList.add(new addItem(R.drawable.coffee, "Coffee", "beverages"));
//       maddList.add(new addItem(R.drawable.water, "Water", "beverages"));
//       maddList.add(new addItem(R.drawable.smoothie, "Smoothies", "beverages"));
//       maddList.add(new addItem(R.drawable.otherbeverages, "Other Beverages", "beverages"));
   }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new addAdapter(listCategoryData);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // click item send info to product Info page
        mAdapter.setOnClickListner(new addAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Log.e("test","position: "+position);
                Intent intent = new Intent(addActivity.this, ProductInfo.class);
                //intent.putExtra("addItem", listCategoryData.get(position));
                intent.putExtra("cat_image", listCategoryData.get(position).getCatImg());
                intent.putExtra("cat_id", listCategoryData.get(position).getCatID());
                intent.putExtra("cat_name", listCategoryData.get(position).getCatName());
                intent.putExtra(LoginActivity.CAT_DATA, jsonStringCatData);
                intent.putExtra(LoginActivity.LC_DATA, jsonStringLCatData);
                intent.putExtra(LoginActivity.USER_DATA, jsonStringUserData);
                startActivityForResult(intent, MainActivity.REQUEST_CODE_ADD);
            }
        });
    }

    /*
     * onActivityResult after viewing cart or editing an item
     * */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST_CODE_ADD) {
            if(resultCode == RESULT_OK) {

                String jsonEditedItem = data.getStringExtra(LoginActivity.EDIT_ITEM);

                Gson gson = new Gson();
                mNewItem = gson.fromJson(jsonEditedItem, FridgeItem.class);
                Log.e("test", "Add item: "+mNewItem);

                //updateServerData();
            }
            else if( resultCode == RESULT_CANCELED) {

            }
        }
    }

    private void addItem(FridgeItem newItem)
    {
        Gson gson = new Gson();
        UserData userData = gson.fromJson(jsonStringUserData, UserData.class);
        List<FridgeItem> listFridgeItem = userData.getFrideItems();
        listFridgeItem.add(newItem);
    }

    // sort List by Category
    private void sortList(int sortOrder) {
        Log.e("test","sortOreder: "+sortOrder);
        Log.e("test","sortOreder: "+sortOrder);
        switch(sortOrder)
        {
            case MainActivity.SORT_BY_CAT:
                Log.e("test","sortcat1: "+sortOrder);
                Collections.sort(listCategoryData, new Comparator<CategoryData>() {
                    @Override
                    public int compare(CategoryData o1, CategoryData o2) {

                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return o1.getCatID() < o2.getCatID() ? -1 : (o1.getCatID() > o2.getCatID()) ? 1 : 0;
                    }
                });
                break;
            case MainActivity.SORT_BY_NAME:
                Collections.sort(listCategoryData, new Comparator<CategoryData>() {
                    @Override
                    public int compare(CategoryData o1, CategoryData o2) {
                        return (o1.getCatName()).compareTo(o2.getCatName());
                    }
                });
                break;
        }
        mAdapter.filterList(listCategoryData);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
                } else {
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    Gson gson = new Gson();
                    String jsonAddedItem = gson.toJson(mNewItem);
                    upIntent.putExtra(LoginActivity.ADD_ITEM,jsonAddedItem);
                    setResult(RESULT_OK, upIntent); //go back to main activity
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        Log.e("test","back");

        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
        } else {
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Gson gson = new Gson();
            String jsonEditedItem = gson.toJson(mNewItem);
            upIntent.putExtra(LoginActivity.ADD_ITEM,jsonEditedItem);
            setResult(RESULT_OK, upIntent); //go back to main activity
            super.onBackPressed();
        }
    }




//        @Override
//    public void onItemClick(View view, int position) {
//        Context context=view.getContext();
//        Intent intent = new Intent();
//        switch (position){
//            case 0:
//                intent =  new Intent(context, ProductInfo.class);
//                context.startActivity(intent);
//                break;
//        }
//    }



} // END of public class addActivity extends AppCompatActivity
