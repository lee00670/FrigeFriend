package com.example.jlee.frigefriend;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;

import butterknife.BindView;
import butterknife.OnClick;




public class addActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
//    public RecyclerView.Adapter mAdapter;
    private addAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<addItem> maddList;

    List<FridgeItem> listFridgeItem = new ArrayList<>();
    private List<FridgeItem> currentSelectedItems = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);



        createAddList();
        buildRecyclerView();

    // (+) btn when clicked directs to product Info activity
        View btnAdd = findViewById(R.id.Button_fab);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addActivity.this, ProductInfo.class));
            }
        });

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

        // sort List Alphabetically
        TextView sortAlpha = findViewById(R.id.sortAlpha);
        sortAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortList(MainActivity.SORT_BY_NAME);
            }
        });

        // sort List categorically
        TextView sortCat = findViewById(R.id.sortCat1);
        sortCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortList(MainActivity.SORT_BY_CAT);
            }
        });


    } // END of  protected void onCreate(Bundle savedInstanceState)

    // search bar
    private void filter (String text){
        ArrayList<addItem> filteredList = new ArrayList<>();

        for (addItem item: maddList){
            if (item.getText1().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }


   public void createAddList(){
//       ArrayList<addItem> addList = new ArrayList<>();
       maddList =new ArrayList<>();

       maddList.add(new addItem(R.drawable.milk, "Milk", "dairy"));
       maddList.add(new addItem(R.drawable.yogurt, "Yogurt", "dairy"));
       maddList.add(new addItem(R.drawable.cheese, "Cheese", "dairy"));
       maddList.add(new addItem(R.drawable.butter, "Butter", "dairy"));
       maddList.add(new addItem(R.drawable.eggs, "Eggs", "dairy"));

       maddList.add(new addItem(R.drawable.pork, "Beef", "meat"));
       maddList.add(new addItem(R.drawable.chicken, "Chicken", "meat"));
       maddList.add(new addItem(R.drawable.pork, "Lamb", "meat"));
       maddList.add(new addItem(R.drawable.chicken, "Turkey", "meat"));
       maddList.add(new addItem(R.drawable.pork, "Pork", "meat"));
       maddList.add(new addItem(R.drawable.sausague, "Sausages", "meat"));
       maddList.add(new addItem(R.drawable.bacon, "Bacon", "meat"));
       maddList.add(new addItem(R.drawable.salami, "Salami", "meat"));
       maddList.add(new addItem(R.drawable.fish, "Fish", "meat"));
       maddList.add(new addItem(R.drawable.othermeat, "Other Meat", "meat"));

       maddList.add(new addItem(R.drawable.apple, "Apple" , "fruit"));
       maddList.add(new addItem(R.drawable.pineapple, "Pineapple", "fruit"));
       maddList.add(new addItem(R.drawable.pear, "Pear", "fruit"));
       maddList.add(new addItem(R.drawable.orange, "Oranges", "fruit"));
       maddList.add(new addItem(R.drawable.lemon, "Lemon", "fruit"));
       maddList.add(new addItem(R.drawable.melon,"Melon", "fruit"));
       maddList.add(new addItem(R.drawable.kiwi, "Kiwi", "fruit"));
       maddList.add(new addItem(R.drawable.grapes, "Grapes", "fruit"));
       maddList.add(new addItem(R.drawable.strawberry, "Strawberries", "fruit"));
       maddList.add(new addItem(R.drawable.berries, "Berries", "fruit"));
       maddList.add(new addItem(R.drawable.avocado, "Avocado", "fruit"));
       maddList.add(new addItem(R.drawable.otherfruits, "Other Fruit", "fruit"));

       maddList.add(new addItem(R.drawable.cucummber, "Cucumber", "veggies"));
       maddList.add(new addItem(R.drawable.broccoli, "Broccoli", "veggies"));
       maddList.add(new addItem(R.drawable.carrots, "Carrots", "veggies"));
       maddList.add(new addItem(R.drawable.pepper, "Pepper", "veggies"));
       maddList.add(new addItem(R.drawable.lettuce, "Lettuce", "veggies"));
       maddList.add(new addItem(R.drawable.tomato, "Tomatoes", "veggies"));
       maddList.add(new addItem(R.drawable.potato, "Potatoes", "veggies"));
       maddList.add(new addItem(R.drawable.mushroom, "Mushrooms", "veggies"));
       maddList.add(new addItem(R.drawable.garlic, "Garlic", "veggies"));
       maddList.add(new addItem(R.drawable.ginger, "Ginger", "veggies"));
       maddList.add(new addItem(R.drawable.onion, "Onions", "veggies"));
       maddList.add(new addItem(R.drawable.chili, "Chili", "veggies"));
       maddList.add(new addItem(R.drawable.corn, "Corn", "veggies"));
       maddList.add(new addItem(R.drawable.peas, "Peas", "veggies"));
       maddList.add(new addItem(R.drawable.eggplant, "Eggplant", "veggies"));
       maddList.add(new addItem(R.drawable.herbs, "Herbs", "veggies"));
       maddList.add(new addItem(R.drawable.otherveggies, "Other Vegetables", "veggies"));

       maddList.add(new addItem(R.drawable.bread, "Bread", "bakery"));
       maddList.add(new addItem(R.drawable.bagel, "Bagel", "bakery"));
       maddList.add(new addItem(R.drawable.donuts, "Doughnuts", "bakery"));
       maddList.add(new addItem(R.drawable.cake, "Cake", "bakery"));
       maddList.add(new addItem(R.drawable.cookies, "Cookies", "bakery"));
       maddList.add(new addItem(R.drawable.chocolate, "Chocolate", "bakery"));
       maddList.add(new addItem(R.drawable.pie, "Pie", "bakery"));
       maddList.add(new addItem(R.drawable.icecream, "Ice Cream", "bakery"));
       maddList.add(new addItem(R.drawable.otherbakery, "Other Bakery&Sweets", "bakery"));

       maddList.add(new addItem(R.drawable.softdrink, "Soft Drinks", "beverages"));
       maddList.add(new addItem(R.drawable.soda, "Soda", "beverages"));
       maddList.add(new addItem(R.drawable.jucie, "Juice", "beverages"));
       maddList.add(new addItem(R.drawable.icedtea, "Iced Tea", "beverages"));
       maddList.add(new addItem(R.drawable.coffee, "Coffee", "beverages"));
       maddList.add(new addItem(R.drawable.water, "Water", "beverages"));
       maddList.add(new addItem(R.drawable.smoothie, "Smoothies", "beverages"));
       maddList.add(new addItem(R.drawable.otherbeverages, "Other Beverages", "beverages"));

   }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new addAdapter(maddList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // click item send info to product Info page
        mAdapter.setOnClickListner(new addAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(addActivity.this, ProductInfo.class);
                intent.putExtra("addItem", maddList.get(position));

                startActivity(intent);

            }
        });
    }


    // sort List by Category
    private void sortList(int sortOrder) {
        Log.e("test","sortOreder: "+sortOrder);
        Log.e("test","sortOreder: "+sortOrder);
        switch(sortOrder)
        {
            case MainActivity.SORT_BY_CAT:
                Log.e("test","sortcat1: "+sortOrder);
                Collections.sort(listFridgeItem, new Comparator<FridgeItem>() {
                    @Override
                    public int compare(FridgeItem o1, FridgeItem o2) {

                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return o1.getCatID() < o2.getCatID() ? -1 : (o1.getCatID() > o2.getCatID()) ? 1 : 0;
                    }
                });
                break;
            case MainActivity.SORT_BY_NAME:
                Collections.sort(maddList, new Comparator<addItem>() {
                    @Override
                    public int compare(addItem o1, addItem o2) {
                        return (o1.getText1()).compareTo(o2.getText1());
                    }
                });
                break;
        }

        mAdapter.notifyDataSetChanged();
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
