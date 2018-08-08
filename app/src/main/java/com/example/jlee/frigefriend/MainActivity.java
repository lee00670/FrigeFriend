/*
* Fridge food list view
*
* */
package com.example.jlee.frigefriend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

    //final static public String ServerURL="http://192.168.0.132/";
    final static public String ServerURL="http://10.70.146.117/";

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
//        Log.e("test", "UserData:" + jsonStringUserData);
//        Log.e("test", "Category:" + jsonStringCat);
//        Log.e("test", "LC:" + jsonStringLC);

        Gson gson = new Gson();
        userData = gson.fromJson(jsonStringUserData, UserData.class);
        CategoryData = gson.fromJson(jsonStringCat,new TypeToken<List<CategoryData>>() {}.getType());
        LCData = gson.fromJson(jsonStringLC,new TypeToken<List<LCData>>() {}.getType());
        Log.e("test", "MaingActivity: userData:" + userData.toString());
        Log.e("test", "MaingActivity: CategoryData:" + CategoryData.toString());
        Log.e("test", "MaingActivity: LCData:" + LCData.toString());
        listFridgeItem = userData.getFrideItems();

        //for test input
        listFridgeItem.add(new FridgeItem(14, "Coffee Milk", 17, R.drawable.milk, 1, "cup", "20170801",0));
        listFridgeItem.add(new FridgeItem(15, "Strawberry Milk", 17, R.drawable.milk, 1, "cup", "20170801",0));
        listFridgeItem.add(new FridgeItem(16, "Coconut Milk", 17, R.drawable.milk, 1, "cup", "20180801",0));
        listFridgeItem.add(new FridgeItem(17, "Brown Eggs", 2, R.drawable.ic_add_circle_outline_black_24dp, 1, "cup", "20180803",0));
        listFridgeItem.add(new FridgeItem(18, "Brown Eggs1", 2, R.drawable.ic_add_circle_outline_black_24dp, 1, "cup", "20180715",0));
        listFridgeItem.add(new FridgeItem(19, "Brown Eggs2", 2, R.drawable.ic_add_circle_outline_black_24dp, 1, "cup", "20180714",0));
        listFridgeItem.add(new FridgeItem(20, "White Egg", 2, R.drawable.ic_add_circle_outline_black_24dp, 1, "cup", "20180716",0));

        initializeViews();
        MainActivityAdapter myAdapter = new MainActivityAdapter(listFridgeItem, onItemCheckListener);
    }

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
    };

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

    public void GoToMyCart()
    {
        Intent viewCartIntent = new Intent(this, ViewCart.class);
        Gson gson = new Gson();
        String jsonCartList = gson.toJson(myCartList);
        viewCartIntent.putExtra(LoginActivity.CART_DATA, jsonCartList);

        viewCartIntent.putExtra(LoginActivity.USER_EMAIL, userData.getUserEmail());
        startActivityForResult(viewCartIntent, 1);
        //startActivity(viewCartIntent);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {
                String jsonStringCartData = data.getStringExtra(LoginActivity.CART_DATA);
                Gson gson = new Gson();
                myCartList = gson.fromJson(jsonStringCartData,new TypeToken<List<CartItem>>() {}.getType());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cartMenu:
                GoToMyCart();
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
        adapter = new MainActivityAdapter(listFridgeItem, CategoryData, MainActivity.this, onItemCheckListener);
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
        Intent addItemIntent = new Intent(this, addActivity.class);
       // addItemIntent.putExtra(LoginActivity.USER_DATA, jsonStringUserData);
        //addItemIntent.putExtra(LoginActivity.CAT_DATA, jsonStringCat);
        //addItemIntent.putExtra(LoginActivity.LC_DATA, jsonStringLC);
        startActivity(addItemIntent);
    }
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

        Iterator<FridgeItem> iter = currentSelectedItems.iterator();
        while (iter.hasNext())
        {
            FridgeItem item = iter.next();
            iter.remove();
        }
        Log.e("test","deleteList:"+  this.currentSelectedItems.size());

        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fabAddToCart)
    public void addToCart()
    {
        mFab.setVisibility(View.VISIBLE);
        mFabAddToCart.setVisibility(View.INVISIBLE);
        mFabDelete.setVisibility(View.INVISIBLE);
        Log.e("test","fabAddToCart:"+ this.currentSelectedItems.size());
        for(FridgeItem item: currentSelectedItems)
        {
            int index = listFridgeItem.indexOf(item);
            adapter.doUncheck(index);
            Boolean found = false;
            for(CartItem cartItem: myCartList)
            {
                if(cartItem.getItemID() == item.getItemID())
                {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
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
