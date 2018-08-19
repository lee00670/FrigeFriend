/*
* Fridge food list view
*
* */
package com.example.jlee.frigefriend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.app.SearchManager;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.versionedparcelable.VersionedParcel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
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
    @BindView( (R.id.fabAddToCart))
    FloatingActionButton mFabAddToCart;
    @BindView( (R.id.fabDelete))
    FloatingActionButton mFabDelete;

    public static final String PREFERENCE = "com.buildup.frigefriend";
    String jsonStringUserData = null;
    String jsonStringCat = null;
    String jsonStringLC = null;
    UserData userData;
    List<CategoryData> CategoryData;
    List<LCData> LCData;

    List<FridgeItem> listFridgeItem = new ArrayList<>();
    private List<FridgeItem> currentSelectedItems = new ArrayList<>();
    private List<CartItem> myCartList = new ArrayList<>();

    MainActivityAdapter adapter;

    public ItemTouchHelperExtension.Callback mCallback;
    public ItemTouchHelperExtension mItemTouchHelper;

    //server IP address
    final static public String ServerURL="http://192.168.0.132/";
    //final static public String ServerURL="http://10.70.146.167/";

    public static final int SORT_BY_DATE = 0;
    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_CAT = 2;
    public static final int REQUEST_CODE_CART = 1;
    public static final int REQUEST_CODE_EDIT = 2;
    public static final int REQUEST_CODE_EDIT_CAT = 3;
    public static final int REQUEST_CODE_ADD = 4;
    private int sort_by = SORT_BY_DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //get intent
        Intent intent = getIntent();
        jsonStringUserData = intent.getStringExtra(LoginActivity.USER_DATA);
        jsonStringCat = intent.getStringExtra(LoginActivity.CAT_DATA);
        jsonStringLC = intent.getStringExtra(LoginActivity.LC_DATA);

        //choose the recent data between server or shared preferences
        checkSharedPreferences();

        //set the class data with json string files
        Gson gson = new Gson();
        userData = gson.fromJson(jsonStringUserData, UserData.class);
        CategoryData = gson.fromJson(jsonStringCat,new TypeToken<List<CategoryData>>() {}.getType());
        LCData = gson.fromJson(jsonStringLC,new TypeToken<List<LCData>>() {}.getType());
//        Log.e("test", "MaingActivity: userData:" + userData.toString());
//        Log.e("test", "MaingActivity: CategoryData:" + CategoryData.toString());
//        Log.e("test", "MaingActivity: LCData:" + LCData.toString());
        listFridgeItem = userData.getFrideItems();
        myCartList = userData.getCartItems();
        //initPreference(); // to delete shared preferences for test

        //update the shared preferences with the current user data and category data
        updatePreferences();

        setCategoryData();

        //for test input
//        listFridgeItem.add(new FridgeItem(14, "Coffee Milk", 0, R.drawable.milk, 1, "Kilogram(s)", "20170801",0));
//        listFridgeItem.add(new FridgeItem(15, "Strawberry Milk", 0, R.drawable.milk, 1, "Package(s)", "20170801",0));
//        listFridgeItem.add(new FridgeItem(16, "Coconut Milk", 0, R.drawable.milk, 1, "Package(s)", "20180801",0));
//        listFridgeItem.add(new FridgeItem(17, "Brown Eggs", 2, R.drawable.eggs, 1, "can(s)", "20180803",0));
//        listFridgeItem.add(new FridgeItem(18, "Brown Eggs1", 2, R.drawable.eggs, 1, "Jar(s)", "20180715",0));
//        listFridgeItem.add(new FridgeItem(19, "Brown Eggs2", 2, R.drawable.eggs, 1, "Jar(s)", "20180714",0));
//        listFridgeItem.add(new FridgeItem(20, "White Egg", 2, R.drawable.eggs, 1, "Jar(s)", "20180716",0));

        initializeViews();
        MainActivityAdapter myAdapter = new MainActivityAdapter(listFridgeItem, onItemCheckListener);
    }

    /*
    * update json string variables to the recent data between server or shared preferences
    * */
    public void checkSharedPreferences()
    {
        String sSPDate = getPreferenceString("dt"); // date when the data is saved

        //sSPDate = "2018-08-14T23:18:04Z";
        if(sSPDate != "")
        {
            Gson gson = new Gson();
            UserData serverData = gson.fromJson(jsonStringUserData, UserData.class);
            String sServerDate = serverData.getUpdateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try{
                Date dSPDate = sdf.parse(sSPDate);
                Date dServerDate = sdf.parse(sServerDate);
//                Log.e("test", "dSPDate: "+dSPDate.toString());
//                Log.e("test", "dServerDate: "+dServerDate.toString());
                if (dServerDate.before(dSPDate)) {

                    //get the shared preferences for the recent data
                    Log.e("test",       "updated by SP");
                    jsonStringUserData = getPreferenceString("ud");
                    jsonStringCat = getPreferenceString("cd");
                }
            }catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    }

    /*
    * update the server json files with current user data and time
    * */
    public void updateServerData()
    {
        //Log.e("test","updateServerData");
        Response.Listener<String> responseUpdateListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    JSONObject jsonResponse  = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success)
                    {
                        Log.e("test", "updated");
                    }
                    else
                    {
                        Log.e("test", "update failed");
                    }

                }catch(Exception e)
                {
                    Log.e("err", "err");
                    e.printStackTrace();
                }
            }
        };
        //update time of saving user data
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String dateTime = dateFormat.format(currentDate);
        Gson gson = new Gson();
        String jsonFridgeItems = gson.toJson(userData.getFrideItems());
        String jsonCartItems = gson.toJson(userData.getCartItems());

        //update the server data with user data (ID, PW, Email, UpdateTime, list of fridge and cart)
        UpdateRequest updateRequest = new UpdateRequest(userData.getUserID(),
                userData.getUserPW(), userData.getUserEmail(), userData.getUpdateTime(),
                jsonFridgeItems, jsonCartItems, responseUpdateListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(updateRequest);
        queue.start();
    }

    /*
    * Save the data to the shared preferences with the current class values.
    * */
    public void updatePreferences()
    {
        Log.e("test","updatePreferences+");
        //update time of saving user data
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String dateTime = dateFormat.format(currentDate);
        userData.setUpdateTime(dateTime);

        Gson gson = new Gson();
        jsonStringUserData = gson.toJson(userData);
//        Log.e("test", "Current Date Time : "+dateTime);
//        Log.e("test", "Current jsonStringUserData: "+jsonStringUserData);
        setPreference("dt", dateTime);
        setPreference("ud", jsonStringUserData);
        setPreference("cd", jsonStringCat);
    }

/*
*   remove shared preferences for test
* */
    public void initPreference()
    {
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.remove("ud");
        editor.remove("cd");
        editor.remove("dt");
        editor.commit();

    }
    /*
    * set shared preferenc with key and value
    * */
    public void setPreference(String key, String value){
        Log.e("test","setPreference key: "+key+", value: "+value);
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }
    /*
    * getPreferenceString : return the string value of key in shared preferences.
    * */
    public String getPreferenceString(String key){
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        return pref.getString(key, "");
    }

    /*
    * Define onItemCheckListener for check button in each fridge item
    * */
    MainActivityAdapter.OnItemCheckListener onItemCheckListener = new MainActivityAdapter.OnItemCheckListener() {
        @Override
        public void onItemCheck(FridgeItem item) {
            currentSelectedItems.add(item);

            if(currentSelectedItems.size()==1)
            {
                mFab.setVisibility(View.INVISIBLE);
                mFabAddToCart.setVisibility(View.VISIBLE);
                mFabDelete.setVisibility(View.VISIBLE);
            }
            Log.e("test","currentSelectedItems.add ");
            Log.e("test","currentSelectedItems.size:  "+currentSelectedItems.size());
        }

        @Override
        public void onItemUncheck(FridgeItem item) {
            currentSelectedItems.remove(item);
            Log.e("test","currentSelectedItems.remove");

            if(currentSelectedItems.size()==0)
            {
                mFab.setVisibility(View.VISIBLE);
                mFabAddToCart.setVisibility(View.INVISIBLE);
                mFabDelete.setVisibility(View.INVISIBLE);

            }
        }
        @Override
        public void onItemClick(FridgeItem item) {
            Log.e("test","onItemClick : "+item.getItemName());
            setUpdateIntent(item);
            //addItemIntent.putExtra(LoginActivity.USER_DATA, jsonStringUserData);
        }
    };

    /*
    *  setUpdateIntent : set the intent for update item and call UpdateProduceInfoActivity
    * */
    private void setUpdateIntent(FridgeItem item)
    {
        Intent updateItemIntent = new Intent(this, UpdateProductInfoActivity.class);
        Gson gson = new Gson();
        String jsonFridgeItem = gson.toJson(item);
        String jsonCategory = gson.toJson(CategoryData);
        //Log.e("test","setUpdateIntent : "+jsonFridgeItem);
        updateItemIntent.putExtra(LoginActivity.EDIT_ITEM, jsonFridgeItem);
        updateItemIntent.putExtra(LoginActivity.CAT_DATA, jsonCategory);
        startActivityForResult(updateItemIntent, REQUEST_CODE_EDIT);
    }
    /*
    * Create hash map for category name and image resource
    * */
    private HashMap<String, Integer> createCategoryHashMap()
    {
        HashMap<String, Integer> hashMapCategory = new HashMap<String, Integer>();
        hashMapCategory.put("Milk",              R.drawable.milk);
        hashMapCategory.put("Yogurt",            R.drawable.yogurt);
        hashMapCategory.put("Cheese",            R.drawable.cheese);
        hashMapCategory.put("Butter",            R.drawable.butter);
        hashMapCategory.put("Eggs",              R.drawable.eggs);

        hashMapCategory.put("Beef",               R.drawable.pork);
        hashMapCategory.put("Chicken",            R.drawable.chicken);
        hashMapCategory.put("Lamb",               R.drawable.pork);
        hashMapCategory.put("Turkey",             R.drawable.chicken);
        hashMapCategory.put("Pork",               R.drawable.pork);
        hashMapCategory.put("Sausages",           R.drawable.sausague);
        hashMapCategory.put("Bacon",              R.drawable.bacon);
        hashMapCategory.put("Salami",             R.drawable.salami);
        hashMapCategory.put("Fish",               R.drawable.fish);
        hashMapCategory.put("Other Meat",          R.drawable.othermeat);

        hashMapCategory.put("Apple",              R.drawable.apple);
        hashMapCategory.put("Pineapple",          R.drawable.pineapple);
        hashMapCategory.put("Pear",               R.drawable.pear);
        hashMapCategory.put("Oranges",            R.drawable.orange);
        hashMapCategory.put("Lemon",              R.drawable.lemon);
        hashMapCategory.put("Melon",              R.drawable.melon);
        hashMapCategory.put("Kiwi",               R.drawable.kiwi);
        hashMapCategory.put("Grapes",             R.drawable.grapes);
        hashMapCategory.put("Strawberries",       R.drawable.strawberry);
        hashMapCategory.put("Berries",            R.drawable.berries);
        hashMapCategory.put("Avocado",            R.drawable.avocado);
        hashMapCategory.put("Other Fruit",         R.drawable.otherfruits);

        hashMapCategory.put("Cucumber",           R.drawable.cucummber);
        hashMapCategory.put("Broccoli",           R.drawable.broccoli);
        hashMapCategory.put("Carrots",            R.drawable.carrots);
        hashMapCategory.put("Pepper",             R.drawable.pepper);
        hashMapCategory.put("Lettuce",            R.drawable.lettuce);
        hashMapCategory.put("Tomatoes",           R.drawable.tomato);
        hashMapCategory.put("Potatoes",           R.drawable.potato);
        hashMapCategory.put("Mushrooms",          R.drawable.mushroom);
        hashMapCategory.put("Garlic",             R.drawable.garlic);
        hashMapCategory.put("Ginger",             R.drawable.ginger);
        hashMapCategory.put("Onions",             R.drawable.onion);
        hashMapCategory.put("Chili",              R.drawable.chili);
        hashMapCategory.put("Corn",               R.drawable.corn);
        hashMapCategory.put("Peas",               R.drawable.peas);
        hashMapCategory.put("Eggplant",           R.drawable.eggplant);
        hashMapCategory.put("Herbs",              R.drawable.herbs);
        hashMapCategory.put("Other Vegetables",    R.drawable.otherveggies);

        hashMapCategory.put("Bread",              R.drawable.bread);
        hashMapCategory.put("Bagel",              R.drawable.bagel);
        hashMapCategory.put("Doughnuts",          R.drawable.donuts);
        hashMapCategory.put("Cake",               R.drawable.cake);
        hashMapCategory.put("Cookies",            R.drawable.cookies);
        hashMapCategory.put("Chocolate",          R.drawable.chocolate);
        hashMapCategory.put("Pie",                R.drawable.pie);
        hashMapCategory.put("Ice Cream",           R.drawable.icecream);
        hashMapCategory.put("Other Bakery&Sweets", R.drawable.otherbakery);

        hashMapCategory.put("Soft Drinks",         R.drawable.softdrink);
        hashMapCategory.put("Soda",               R.drawable.soda);
        hashMapCategory.put("Juice",              R.drawable.jucie);
        hashMapCategory.put("Iced Tea",            R.drawable.icedtea);
        hashMapCategory.put("Coffee",             R.drawable.coffee);
        hashMapCategory.put("Water",              R.drawable.water);
        hashMapCategory.put("Smoothies",          R.drawable.smoothie);
        hashMapCategory.put("Other Beverages",     R.drawable.otherbeverages);

        hashMapCategory.put("Pasta",          R.drawable.pasta);
        hashMapCategory.put("Rice",          R.drawable.rice);
        hashMapCategory.put("Leftovers",          R.drawable.leftovers);
        hashMapCategory.put("Pizza",          R.drawable.pizza);
        hashMapCategory.put("Other food",          R.drawable.otherfood);
        return hashMapCategory;
    }
/*
* setCategoryData() : Set the category image for each category data
* */
    private void setCategoryData()
    {
        HashMap<String, Integer> catHashMap = createCategoryHashMap();

        for( CategoryData catData : CategoryData)
        {
            //Log.e("test", "catName: "+catData.getCatName()+", img : "+catHashMap.get(catData.getCatName()));
            catData.setCatImg(catHashMap.get(catData.getCatName()));
            //Log.e("test", "setCatImg: "+catData.getCatImg());
        }
    }


    /*
    * Initialize the recycler view
    * */
    private void initializeViews()
    {
        rv.setHasFixedSize(true);

        //set layout manager
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearlayoutmanager);

        //set adapter
        adapter = new MainActivityAdapter(listFridgeItem, CategoryData, MainActivity.this , onItemCheckListener);

        rv.setAdapter(adapter);
        //rv.addItemDecoration(new DividerItemDecoration(this));

        sortData(SORT_BY_DATE);

        mCallback = new ItemTouchHelperCallback();
        mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
        mItemTouchHelper.attachToRecyclerView(rv);
        adapter.setItemTouchHelperExtension(mItemTouchHelper);

    }

    /*
    * onCreateOptionsMenu : connect the search view to listener
    * */
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

    /*
    * handle when the text is changed in search view
    * */
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

    /*
    *  GoToMyCart: call viewcart class with cart data and email
    * */
    public void GoToMyCart()
    {
        Intent viewCartIntent = new Intent(this, ViewCart.class);
        Gson gson = new Gson();
        String jsonCartList = gson.toJson(myCartList);
        viewCartIntent.putExtra(LoginActivity.CART_DATA, jsonCartList);

        viewCartIntent.putExtra(LoginActivity.USER_EMAIL, userData.getUserEmail());
        startActivityForResult(viewCartIntent, REQUEST_CODE_CART);
        //startActivity(viewCartIntent);
    }

    /*
    * onActivityResult after viewing cart or editing an item
    * */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CART) {
            if(resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {
                String jsonStringCartData = data.getStringExtra(LoginActivity.CART_DATA);
                Gson gson = new Gson();
                myCartList = gson.fromJson(jsonStringCartData,new TypeToken<List<CartItem>>() {}.getType());
                userData.setCartItems(myCartList);
                updateServerData();
            }
        }else if (requestCode == REQUEST_CODE_EDIT) {
            if(resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {

                String jsonEditedItem = data.getStringExtra(LoginActivity.EDIT_ITEM);
                Log.e("test", "REQUEST_CODE_EDIT: "+jsonEditedItem);
                Gson gson = new Gson();
                FridgeItem item  = gson.fromJson(jsonEditedItem, FridgeItem.class);
                updateItem(item);
                updateServerData();
            }
        }
        else if (requestCode == REQUEST_CODE_ADD) {
            if(resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {

                String jsonAddedItem = data.getStringExtra(LoginActivity.ADD_ITEM);
                Log.e("test", "Add item: "+jsonAddedItem);

                Gson gson = new Gson();
                FridgeItem item  = gson.fromJson(jsonAddedItem, FridgeItem.class);
                if(item != null)
                {
                    addItem(item);
                    updateServerData();
                }
            }
        }
    }
    private void addItem(FridgeItem newItem)
    {
        Gson gson = new Gson();
        UserData userData = gson.fromJson(jsonStringUserData, UserData.class);
        List<FridgeItem> listFridgeItem = userData.getFrideItems();
        listFridgeItem.add(newItem);
        this.userData.setFrideItems(listFridgeItem);
        this.listFridgeItem = listFridgeItem;
        adapter.notifyItemInserted(1);
        adapter.notifyDataSetChanged();
        sortData(sort_by);
    }
    /*
    * update the specific item in main fridge list and refresh the list
    * */
    public void updateItem(FridgeItem updatedItem)
    {
        Log.e("test", "updateItem: "+updatedItem);
        //find the updated item
        for(FridgeItem item: listFridgeItem)
        {
            if(item.getItemID() == updatedItem.getItemID())
            {
                item.setCatID(updatedItem.getCatID());
                item.setCatImg(updatedItem.getCatImg());
                item.setQuantity(updatedItem.getQuantity());
                item.setQuantityUnit(updatedItem.getQuantityUnit());
                item.setExpDate(updatedItem.getExpDate());
            }

        }
        adapter.notifyDataSetChanged();
        sortData(sort_by);
        for(CartItem item: myCartList)
        {
            if(item.getItemID() == updatedItem.getItemID())
            {
                item.setCatID(updatedItem.getCatID());
                item.setCatImg(updatedItem.getCatImg());
            }
        }
        userData.setCartItems(myCartList);
        updatePreferences();
    }

    /*
    * Menu : my cart / logout
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cartMenu:
                GoToMyCart();
                break;

            case R.id.logoutMenu:
                updatePreferences();
                updateServerData();
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

    /*
    * Sort the main fridge list as the order using collections
    * */
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
        adapter = new MainActivityAdapter(listFridgeItem, CategoryData, MainActivity.this, onItemCheckListener);
        rv.setAdapter(adapter);
    }

    /*
     * Click Listener for sorting by date
     * */
    @OnClick(R.id.sortDate)
    void sortByDate() {
//        Drawable mDrawable = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
//        mDrawable.setColorFilter(new
//                PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY));
//        sortDate.setCompoundDrawablesRelativeWithIntrinsicBounds(mDrawable, 0, 0, 0b0);
        sortData(SORT_BY_DATE);
        sort_by = SORT_BY_DATE;
        setTextViewDrawableColor(sortDate, R.color.colorAccent );
        setTextViewDrawableColor(sortName, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortCat, R.color.colorPrimaryDark );
        sortDate.setTextColor(getResources().getColor(R.color.colorAccent));
        sortName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortCat.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    /*
     * Click Listener for sorting by name
     * */
    @OnClick(R.id.sortName)
    void sortByName() {
        sortData(SORT_BY_NAME);
        sort_by = SORT_BY_NAME;
        setTextViewDrawableColor(sortDate, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortName, R.color.colorAccent );
        setTextViewDrawableColor(sortCat, R.color.colorPrimaryDark );
        sortDate.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortName.setTextColor(getResources().getColor(R.color.colorAccent));
        sortCat.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }
    /*
     * Click Listener for sorting by category
     * */
    @OnClick(R.id.sortCat)
    void sortByCat() {
        sortData(SORT_BY_CAT);
        sort_by = SORT_BY_CAT;
        setTextViewDrawableColor(sortDate, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortName, R.color.colorPrimaryDark );
        setTextViewDrawableColor(sortCat, R.color.colorAccent );
        sortDate.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        sortCat.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    /*
     * Click Listener for add fab button
     * */
    @OnClick(R.id.floatingActionButton)
    public void addItem()
    {
        Intent addItemIntent = new Intent(this, addActivity.class);
         //addItemIntent.putExtra(LoginActivity.USER_DATA, jsonStringUserData);
        Gson gson = new Gson();
        String jsonCategoryList = gson.toJson(CategoryData);
        addItemIntent.putExtra(LoginActivity.CAT_DATA, jsonCategoryList);
        addItemIntent.putExtra(LoginActivity.LC_DATA, jsonStringLC);
        addItemIntent.putExtra(LoginActivity.USER_DATA, jsonStringUserData);
        startActivityForResult(addItemIntent, REQUEST_CODE_ADD);
    }

    /*
     * Click Listener for delete fab button
     * */
    @OnClick(R.id.fabDelete)
    public void deleteList()
    {
        mFab.setVisibility(View.VISIBLE);
        mFabAddToCart.setVisibility(View.INVISIBLE);
        mFabDelete.setVisibility(View.INVISIBLE);

        for(FridgeItem item: currentSelectedItems)
        {
            int index = listFridgeItem.indexOf(item);
            adapter.doDelete(index);
        }

        //clear the selected item list
        Iterator<FridgeItem> iter = currentSelectedItems.iterator();
        while (iter.hasNext())
        {
            FridgeItem item = iter.next();
            iter.remove();
        }
        Log.e("test","deleteList:"+  this.currentSelectedItems.size());

        adapter.notifyDataSetChanged();
    }

    /*
    * Click Listener for Add to Cart fab button
    * */
    @OnClick(R.id.fabAddToCart)
    public void addToCart()
    {
        mFab.setVisibility(View.VISIBLE);
        mFabAddToCart.setVisibility(View.INVISIBLE);
        mFabDelete.setVisibility(View.INVISIBLE);
        //Log.e("test","fabAddToCart:"+ this.currentSelectedItems.size());

        // add selected items to carrList
        for(FridgeItem item: currentSelectedItems)
        {
            int index = listFridgeItem.indexOf(item);
            adapter.doUncheck(index);
            Boolean found = false;
            for(CartItem cartItem: myCartList)
            {
                // if the selected item is already in the cartlist, then just increase the number of the quantity
                if((cartItem.getItemID() == item.getItemID())
                        && (cartItem.getItemName().equals(item.getItemName())))
                {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    //cartItem.setQuantityUnit(item.getQuantityUnit());
                    cartItem.setCatImg(item.getCatImg());
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                CartItem cartItem = new CartItem(item.getItemID(), item.getItemName(),item.getCatID(),item.getCatImg(), 1, item.getQuantityUnit());
                myCartList.add(cartItem);
            }

        }

        Iterator<FridgeItem> iter = currentSelectedItems.iterator();
        while (iter.hasNext())
        {
            FridgeItem item = iter.next();
            iter.remove();
        }

        new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this,R.style.AlertDialogCustom))
                .setMessage("Do you want to go to your cart?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //MyActivity.this.finish();
                        GoToMyCart();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //dialog.cancel();
                    }
                })
                .create()
                .show();
    }
 }
