package com.example.jlee.frigefriend;

import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class CategoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        ArrayList items = new ArrayList();

        items.add(new ListCell("Apple", "Fruit"));
        items.add(new ListCell("Banana", "Fruit"));
        items.add(new ListCell("Peach", "Fruit"));
        items.add(new ListCell("Plum", "Fruit"));
        items.add(new ListCell("Orange", "Fruit"));
        items.add(new ListCell("Pineapple", "Fruit"));
        items.add(new ListCell("Pear", "Fruit"));
        items.add(new ListCell("Cucummber", "Vegetables"));
        items.add(new ListCell("Carrot", "Vegetables"));
        items.add(new ListCell("Eggplant", "Vegetables"));
        items.add(new ListCell("Tomato", "Vegetables"));
        items.add(new ListCell("Potato", "Vegetables"));
        items.add(new ListCell("Spinach", "Vegetables"));
        items.add(new ListCell("Beets", "Vegetables"));
        items.add(new ListCell("Milk", "Dairy Product"));
        items.add(new ListCell("Yogurt", "Dairy Product"));
        items.add(new ListCell("Cheese", "Dairy Product"));
        items.add(new ListCell("Chicken", "Meat"));
        items.add(new ListCell("Beef", "Meat"));
        items.add(new ListCell("pork", "Meat"));
        items.add(new ListCell("Turkey", "Meat"));
        items.add(new ListCell("Sausage", "Meat"));
        items.add(new ListCell("Juice", "Beverages"));
        items.add(new ListCell("Soda", "Beverages"));
        items.add(new ListCell("Coffee", "Beverages"));
        items.add(new ListCell("Tea", "Beverages"));




        ListView list = (ListView) findViewById(R.id.awesome_list);

        items = sortAndAddSections(items);

        ListAdapter adapter = new ListAdapter(this, items);
        list.setAdapter(adapter);

    }



    private ArrayList sortAndAddSections(ArrayList itemList)
    {

        ArrayList tempList = new ArrayList();
        //First we sort the array
        Collections.sort(itemList);

        //Loops thorugh the list and add a section before each sectioncell start
        String header = "";
        for(int i = 0; i < itemList.size(); i++)
        {
            //If it is the start of a new section we create a new listcell and add it to our array
            if(header != itemList.get(i).getCategory()){
                ListCell sectionCell = new ListCell(itemList.get(i).getCategory(), null);
                sectionCell.setToSectionHeader();
                tempList.add(sectionCell);
                header = itemList.get(i).getCategory();
            }
            tempList.add(itemList.get(i));
        }

        return tempList;

    }

//    public void loadItems(View view) {
//
//        Resources res = getResources();
//
//        InputStream is = res.openRawResource(R.raw.items);
//
//        Scanner scanner = new Scanner(is);
//
//        StringBuilder builder = new StringBuilder();
//
//        while (scanner.hasNextLine()) {
//            builder.append(scanner.nextLine());
//
//        }
//
//        parseJson(builder.toString());
//    }
//
//    private void parseJson(String s) {
//        TextView txtDisplay = findViewById(R.id.text_display);
//
//        StringBuilder builder = new StringBuilder();
//        try {
//            JSONObject root = new JSONObject(s);
//
//          JSONObject items = root.getJSONObject("Items");
//
//
//            JSONArray products = items.getJSONArray("foodItems");
//
//            for (int i =0; i < products.length(); i++) {
//                JSONObject product = products.getJSONObject(i);
//                builder.append(product.getString("name"))
//                        .append(": ")
//                        .append(product.getString("category"))
//                        .append("\n");
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        txtDisplay.setText(s);
   }



