package com.example.jlee.frigefriend;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class addActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ArrayList<addItem> addList = new ArrayList<>();

        addList.add(new addItem(R.drawable.milk, "Milk"));
        addList.add(new addItem(R.drawable.yogurt, "Yogurt"));
        addList.add(new addItem(R.drawable.cheese, "Cheese"));
        addList.add(new addItem(R.drawable.butter, "Butter"));
        addList.add(new addItem(R.drawable.eggs, "Eggs"));

        addList.add(new addItem(R.drawable.pork, "Beef"));
        addList.add(new addItem(R.drawable.chicken, "Chicken"));
        addList.add(new addItem(R.drawable.pork, "Lamb"));
        addList.add(new addItem(R.drawable.chicken, "Turkey"));
        addList.add(new addItem(R.drawable.pork, "Pork"));
        addList.add(new addItem(R.drawable.sausague, "Sausages"));
        addList.add(new addItem(R.drawable.bacon, "Bacon"));
        addList.add(new addItem(R.drawable.salami, "Salami"));
        addList.add(new addItem(R.drawable.fish, "Fish"));
        addList.add(new addItem(R.drawable.othermeat, "Other Meat"));

        addList.add(new addItem(R.drawable.apple, "Apple"));
        addList.add(new addItem(R.drawable.pineapple, "Pineapple"));
        addList.add(new addItem(R.drawable.pear, "Pear"));
        addList.add(new addItem(R.drawable.orange, "Oranges"));
        addList.add(new addItem(R.drawable.lemon, "Lemon"));
        addList.add(new addItem(R.drawable.melon,"Melon"));
        addList.add(new addItem(R.drawable.kiwi, "Kiwi"));
        addList.add(new addItem(R.drawable.grapes, "Grapes"));
        addList.add(new addItem(R.drawable.strawberry, "Strawberries"));
        addList.add(new addItem(R.drawable.berries, "Berries"));
        addList.add(new addItem(R.drawable.avocado, "Avocado"));
        addList.add(new addItem(R.drawable.otherfruits, "Other Fruit"));

        addList.add(new addItem(R.drawable.cucummber, "Cucumber"));
        addList.add(new addItem(R.drawable.broccoli, "Broccoli"));
        addList.add(new addItem(R.drawable.carrots, "Carrots"));
        addList.add(new addItem(R.drawable.pepper, "Pepper"));
        addList.add(new addItem(R.drawable.lettuce, "Lettuce"));
        addList.add(new addItem(R.drawable.tomato, "Tomatoes"));
        addList.add(new addItem(R.drawable.potato, "Potatoes"));
        addList.add(new addItem(R.drawable.mushroom, "Mushrooms"));
        addList.add(new addItem(R.drawable.garlic, "Garlic"));
        addList.add(new addItem(R.drawable.ginger, "Ginger"));
        addList.add(new addItem(R.drawable.onion, "Onions"));
        addList.add(new addItem(R.drawable.chili, "Chili"));
        addList.add(new addItem(R.drawable.corn, "Corn"));
        addList.add(new addItem(R.drawable.peas, "Peas"));
        addList.add(new addItem(R.drawable.eggplant, "Eggplant"));
        addList.add(new addItem(R.drawable.herbs, "Herbs"));
        addList.add(new addItem(R.drawable.otherveggies, "Other Vegetables"));

        addList.add(new addItem(R.drawable.bread, "Bread"));
        addList.add(new addItem(R.drawable.bagel, "Bagel"));
        addList.add(new addItem(R.drawable.donuts, "Doughnuts"));
        addList.add(new addItem(R.drawable.cake, "Cake"));
        addList.add(new addItem(R.drawable.cookies, "Cookies"));
        addList.add(new addItem(R.drawable.chocolate, "Chocolate"));
        addList.add(new addItem(R.drawable.pie, "Pie"));
        addList.add(new addItem(R.drawable.icecream, "Ice Cream"));
        addList.add(new addItem(R.drawable.otherbakery, "Other Bakery&Sweets"));

        addList.add(new addItem(R.drawable.softdrink, "Soft Drinks"));
        addList.add(new addItem(R.drawable.soda, "Soda"));
        addList.add(new addItem(R.drawable.jucie, "Juice"));
        addList.add(new addItem(R.drawable.icedtea, "Iced Tea"));
        addList.add(new addItem(R.drawable.coffee, "Coffee"));
        addList.add(new addItem(R.drawable.water, "Water"));
        addList.add(new addItem(R.drawable.smoothie, "Smoothies"));
        addList.add(new addItem(R.drawable.otherbeverages, "Other Beverages"));

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new addAdapter(addList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);





    } // END of  protected void onCreate(Bundle savedInstanceState)

} // END of public class addActivity extends AppCompatActivity
