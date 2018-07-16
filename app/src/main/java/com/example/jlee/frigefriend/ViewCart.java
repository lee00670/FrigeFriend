package com.example.jlee.frigefriend;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewCart extends AppCompatActivity {
    @BindView(R.id.app_bar) Toolbar toolbar;
    @BindView(R.id.buttonMail)
    Button buttonMail;
    @BindView (R.id.recyclerViewCart)
    RecyclerView rvCart;

    ViewCartAdapter adapter;
    List<CartItem> listCartItem = new ArrayList<>();

    public ItemTouchHelperExtension.Callback mCallback;
    public ItemTouchHelperExtension mItemTouchHelper;
    private String userEmail;
    private String jsonStringCartData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.cartMenu);
        //getSupportActionBar().setIcon(R.drawable.ic_shopping_cart_black_24dp);
        Intent intent = getIntent();
        jsonStringCartData = intent.getStringExtra(LoginActivity.CART_DATA);
        userEmail = intent.getStringExtra(LoginActivity.USER_EMAIL);
        Log.e("test","ViewCart .cart:"+jsonStringCartData);
        Gson gson = new Gson();
        listCartItem = gson.fromJson(jsonStringCartData,new TypeToken<List<CartItem>>() {}.getType());

        initializeViews();
    }
    private void initializeViews()
    {
        rvCart.setHasFixedSize(true);

        //set layout manager
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(this);
        rvCart.setLayoutManager(linearlayoutmanager);

        //set adapter
        adapter = new ViewCartAdapter(listCartItem, ViewCart.this, onItemCheckListener);
        rvCart.setAdapter(adapter);
        rvCart.addItemDecoration(new DividerItemDecoration(this));
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
                    //upIntent.putExtra(LoginActivity.USER_DATA, jsonStringUserData);
                    Gson gson = new Gson();
                    String jsonCartList = gson.toJson(listCartItem);
                    upIntent.putExtra(LoginActivity.CART_DATA,jsonCartList);
                    setResult(RESULT_OK, upIntent);
                    //startActivity(upIntent);
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    ViewCartAdapter.OnSpinnerQuantityClickListener onItemCheckListener = new ViewCartAdapter.OnSpinnerQuantityClickListener() {
        @Override
        public void onItemCheck(CartItem item, int newQuantity) {
            Log.e("test","newQunatity: "+newQuantity);
            for(CartItem i: listCartItem)
            {
                if(i.getItemID() == item.getItemID())
                {
                    i.setQuantity(newQuantity);
                }
            }
        }

        @Override
        public void onItemUncheck(CartItem item) {

        }
    };

    @OnClick(R.id.buttonMail)
    public void sendEmail()
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",userEmail, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "FridgeFriend: Cart List");
        Log.e("test", listCartItem.toString());
        intent.putExtra(Intent.EXTRA_TEXT, "cart list : "+listCartItem.toString());
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }

    @Override
    public void onBackPressed(){
        Log.e("test","back");

        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
        } else {
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //upIntent.putExtra(LoginActivity.USER_DATA, jsonStringUserData);
            Gson gson = new Gson();
            String jsonCartList = gson.toJson(listCartItem);
            upIntent.putExtra(LoginActivity.CART_DATA,jsonCartList);
            setResult(RESULT_OK, upIntent);
            //startActivity(upIntent);
            super.onBackPressed();
        }
    }


}
