package com.example.jlee.frigefriend;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link byNameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link byNameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class byNameFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mJsonCatData;
    private String mJsonUserData;

    List<CategoryData> listCategoryData;
    List<FridgeItem> mListAddedItems= new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private addAdapter mAdapter;
    private int sort_by = MainActivity.SORT_BY_CAT;


    TextView sortCat;
    TextView sortAlpha;

    private OnFragmentInteractionListener mListener;

    public byNameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment byNameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static byNameFragment newInstance(String param1, String param2) {
        byNameFragment fragment = new byNameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());
        if (getArguments() != null) {
            mJsonCatData = getArguments().getString(ARG_PARAM1);
            mJsonUserData = getArguments().getString(ARG_PARAM2);
            Gson gson = new Gson();
            listCategoryData = gson.fromJson(mJsonCatData,new TypeToken<List<CategoryData>>() {}.getType());


        }
    }

    private void buildRecyclerView(View rootView)
    {
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new addAdapter(listCategoryData);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // click item send info to product Info page
        mAdapter.setOnClickListner(new addAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(CategoryData item) {
                Log.e("test","item: "+item);
                Intent intent = new Intent(getActivity(), ProductInfo.class);
                //intent.putExtra("addItem", listCategoryData.get(position));
                intent.putExtra("cat_image", item.getCatImg());
                intent.putExtra("cat_id", item.getCatID());
                intent.putExtra("cat_name", item.getCatName());
                intent.putExtra(LoginActivity.CAT_DATA, mJsonCatData);
                intent.putExtra(LoginActivity.LC_DATA, mJsonCatData);
                Gson gson = new Gson();
                String mJsonFridgeData = gson.toJson(MainActivity.listFridgeItem);
                intent.putExtra(LoginActivity.FRIDGE_DATA, mJsonFridgeData);
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

                if (mListener != null) {
                    mListener.onFragmentInteraction(jsonEditedItem);
                }
                Log.e("test", "Add item: "+jsonEditedItem);

                //updateServerData();
            }
            else if( resultCode == RESULT_CANCELED) {

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_by_name, container, false);

        buildRecyclerView(rootView);

        sortCat = rootView.findViewById(R.id.sortCat1);
        sortAlpha = rootView.findViewById(R.id.sortAlpha);
        sortByCat();

        sortCat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sortByCat();
            }
        });

        sortAlpha.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sortByAlpha();
            }
        });

        //Search Bar
        EditText editTextSearch = rootView.findViewById(R.id.editTextSearchBar);
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

        return rootView;

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

    /*
     * Click Listener for sorting by category
     * */
    void sortByCat() {
        sortList(MainActivity.SORT_BY_CAT);
        sort_by = MainActivity.SORT_BY_CAT;
        setTextViewDrawableColor(sortCat, R.color.colorAccent );
        setTextViewDrawableColor(sortAlpha, R.color.colorPrimaryDark );
        sortCat.setTextColor(getResources().getColor(R.color.colorAccent));
        sortAlpha.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }
    /*
     * Click Listener for sorting by category
     * */
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
                DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), color) );
                //drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_IN));
            }
        }
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction("test");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String sNewItem);
    }
}
