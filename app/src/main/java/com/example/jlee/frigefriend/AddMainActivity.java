package com.example.jlee.frigefriend;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class AddMainActivity extends AppCompatActivity
        implements byNameFragment.OnFragmentInteractionListener,
        byCategoryFragment.OnFragmentInteractionListener,
        rootFragment.OnFragmentInteractionListener,
        bySecondFragment.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private PagerAdapter mPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;

    private static String jsonStringCatData;
    private static String jsonStringLCatData;
    private static String jsonStringUserData;
    List<CategoryData> listCategoryData;
    List<FridgeItem> mListAddedItems= new ArrayList<>();
    FridgeItem mNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.addMenu);

        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        //get intent from main activity

        //get the category list from the intent
        Intent intent = getIntent();
        jsonStringCatData = intent.getStringExtra(LoginActivity.CAT_DATA);
        jsonStringLCatData = intent.getStringExtra(LoginActivity.LC_DATA);
        jsonStringUserData = intent.getStringExtra(LoginActivity.USER_DATA);

        Log.e("test","addActivity cat_data :"+jsonStringCatData);
        Gson gson = new Gson();
        listCategoryData = gson.fromJson(jsonStringCatData,new TypeToken<List<CategoryData>>() {}.getType());

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_add_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
                } else {
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    Gson gson = new Gson();
                    String jsonAddedItem = gson.toJson(mListAddedItems);
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
            String jsonEditedItem = gson.toJson(mListAddedItems);
            upIntent.putExtra(LoginActivity.ADD_ITEM,jsonEditedItem);
            setResult(RESULT_OK, upIntent); //go back to main activity
            super.onBackPressed();
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String jsonStringCatData, String jsonStringUserData) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString("cat", jsonStringCatData);
            args.putString("user", jsonStringUserData);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_by_cat, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            View rootView = null;

            Log.e("test", "int : "+getArguments().getInt(ARG_SECTION_NUMBER));
            switch(getArguments().getInt(ARG_SECTION_NUMBER))
            {
                case 1:
//                    rootView = inflater.inflate(R.layout.fragment_by_cat, container, false);
//                    Button b = rootView.findViewById(R.id.button);
//
//                    b.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //byNameFragment.newInstance(getArguments().getString("cat"), getArguments().getString("user"));
//                            callOtherView();
//                        }
//                    });
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_by_name, container, false);
                    break;
            }

            return rootView;
        }
    }
    public static void callOtherView()
    {
        fragmentTransaction.replace(R.id.container, byNameFragment.newInstance(jsonStringCatData, jsonStringUserData));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void onFragmentInteraction(String sNewItem) {
        Gson gson = new Gson();
        mNewItem = gson.fromJson(sNewItem, FridgeItem.class);
        mListAddedItems.add(mNewItem);
        Log.e("test", "onFragmentInteraction");
    }
    public void onFragmentInteractionFromCat(int iNewItem) {
        Gson gson = new Gson();
        //mNewItem = gson.fromJson(sNewItem, FridgeItem.class);
        //mListAddedItems.add(mNewItem);
        Log.e("test", "onFragmentInteraction from Cat  : ID" + iNewItem);
    }
    public void onFragmentInteractionFromRoot() {

        Log.e("test", "onFragmentInteractionFromRoot : " );
    }
    public void onFragmentInteractionFromSecond(String sNewItem) {
        Gson gson = new Gson();
        mNewItem = gson.fromJson(sNewItem, FridgeItem.class);
        mListAddedItems.add(mNewItem);
        Log.e("test", "onFragmentInteraction");
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            switch (position) {
                case 0:

                    return rootFragment.newInstance(jsonStringCatData, jsonStringLCatData);
                case 1:
                    return byNameFragment.newInstance(jsonStringCatData, jsonStringUserData);
        }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return "BY CATEGORY";
                case 1:
                    return "BY NAME";
            }
            return null;
        }
    }




}
