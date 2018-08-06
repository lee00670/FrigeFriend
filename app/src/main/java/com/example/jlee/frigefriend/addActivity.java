package com.example.jlee.frigefriend;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;


public class addActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
//    public RecyclerView.Adapter mAdapter;
    private addAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<addItem> maddList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        createAddList();
        buildRecyclerView();


        View btnAdd = findViewById(R.id.Button_fab);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addActivity.this, ProductInfo.class));
            }
        });


//
//        // sort List Alphabetically
//        TextView sortAlpha = findViewById(R.id.sortAlpha);
//        sortAlpha.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sortArrayList();
//            }
//        });


    } // END of  protected void onCreate(Bundle savedInstanceState)


   public void createAddList(){
//       ArrayList<addItem> addList = new ArrayList<>();
       maddList =new ArrayList<>();

       maddList.add(new addItem(R.drawable.milk, "Milk"));
       maddList.add(new addItem(R.drawable.yogurt, "Yogurt"));
       maddList.add(new addItem(R.drawable.cheese, "Cheese"));
       maddList.add(new addItem(R.drawable.butter, "Butter"));
       maddList.add(new addItem(R.drawable.eggs, "Eggs"));

       maddList.add(new addItem(R.drawable.pork, "Beef"));
       maddList.add(new addItem(R.drawable.chicken, "Chicken"));
       maddList.add(new addItem(R.drawable.pork, "Lamb"));
       maddList.add(new addItem(R.drawable.chicken, "Turkey"));
       maddList.add(new addItem(R.drawable.pork, "Pork"));
       maddList.add(new addItem(R.drawable.sausague, "Sausages"));
       maddList.add(new addItem(R.drawable.bacon, "Bacon"));
       maddList.add(new addItem(R.drawable.salami, "Salami"));
       maddList.add(new addItem(R.drawable.fish, "Fish"));
       maddList.add(new addItem(R.drawable.othermeat, "Other Meat"));

       maddList.add(new addItem(R.drawable.apple, "Apple"));
       maddList.add(new addItem(R.drawable.pineapple, "Pineapple"));
       maddList.add(new addItem(R.drawable.pear, "Pear"));
       maddList.add(new addItem(R.drawable.orange, "Oranges"));
       maddList.add(new addItem(R.drawable.lemon, "Lemon"));
       maddList.add(new addItem(R.drawable.melon,"Melon"));
       maddList.add(new addItem(R.drawable.kiwi, "Kiwi"));
       maddList.add(new addItem(R.drawable.grapes, "Grapes"));
       maddList.add(new addItem(R.drawable.strawberry, "Strawberries"));
       maddList.add(new addItem(R.drawable.berries, "Berries"));
       maddList.add(new addItem(R.drawable.avocado, "Avocado"));
       maddList.add(new addItem(R.drawable.otherfruits, "Other Fruit"));

       maddList.add(new addItem(R.drawable.cucummber, "Cucumber"));
       maddList.add(new addItem(R.drawable.broccoli, "Broccoli"));
       maddList.add(new addItem(R.drawable.carrots, "Carrots"));
       maddList.add(new addItem(R.drawable.pepper, "Pepper"));
       maddList.add(new addItem(R.drawable.lettuce, "Lettuce"));
       maddList.add(new addItem(R.drawable.tomato, "Tomatoes"));
       maddList.add(new addItem(R.drawable.potato, "Potatoes"));
       maddList.add(new addItem(R.drawable.mushroom, "Mushrooms"));
       maddList.add(new addItem(R.drawable.garlic, "Garlic"));
       maddList.add(new addItem(R.drawable.ginger, "Ginger"));
       maddList.add(new addItem(R.drawable.onion, "Onions"));
       maddList.add(new addItem(R.drawable.chili, "Chili"));
       maddList.add(new addItem(R.drawable.corn, "Corn"));
       maddList.add(new addItem(R.drawable.peas, "Peas"));
       maddList.add(new addItem(R.drawable.eggplant, "Eggplant"));
       maddList.add(new addItem(R.drawable.herbs, "Herbs"));
       maddList.add(new addItem(R.drawable.otherveggies, "Other Vegetables"));

       maddList.add(new addItem(R.drawable.bread, "Bread"));
       maddList.add(new addItem(R.drawable.bagel, "Bagel"));
       maddList.add(new addItem(R.drawable.donuts, "Doughnuts"));
       maddList.add(new addItem(R.drawable.cake, "Cake"));
       maddList.add(new addItem(R.drawable.cookies, "Cookies"));
       maddList.add(new addItem(R.drawable.chocolate, "Chocolate"));
       maddList.add(new addItem(R.drawable.pie, "Pie"));
       maddList.add(new addItem(R.drawable.icecream, "Ice Cream"));
       maddList.add(new addItem(R.drawable.otherbakery, "Other Bakery&Sweets"));

       maddList.add(new addItem(R.drawable.softdrink, "Soft Drinks"));
       maddList.add(new addItem(R.drawable.soda, "Soda"));
       maddList.add(new addItem(R.drawable.jucie, "Juice"));
       maddList.add(new addItem(R.drawable.icedtea, "Iced Tea"));
       maddList.add(new addItem(R.drawable.coffee, "Coffee"));
       maddList.add(new addItem(R.drawable.water, "Water"));
       maddList.add(new addItem(R.drawable.smoothie, "Smoothies"));
       maddList.add(new addItem(R.drawable.otherbeverages, "Other Beverages"));

   }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new addAdapter(maddList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListner(new addAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(addActivity.this, ProductInfo.class);
                intent.putExtra("addItem", maddList.get(position));

                startActivity(intent);

            }
        });
    }


//    // Sort List Alphabetically
//    private void sortArrayList(){
//        Collections.sort(maddList, new Comparator<addItem>() {
//            @Override
//            public int compare(addItem o1, addItem o2) {
//                return (o1.getText1()).compareTo(o2.getText1());
//            }
//        });
//        mAdapter.notifyDataSetChanged();
//    }

    //    @Override
//    public void onItemClick(View view, int position) {
//        Context context=view.getContext();
//        Intent intent=new Intent();
//        switch (position){
//            case 0:
//                intent =  new Intent(context, ProductInfo.class);
//                context.startActivity(intent);
//                break;
//        }
//    }



} // END of public class addActivity extends AppCompatActivity
