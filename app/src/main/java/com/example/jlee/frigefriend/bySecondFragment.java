package com.example.jlee.frigefriend;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link bySecondFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link bySecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bySecondFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "cat";
    private static final String ARG_PARAM2 = "cat_name";

    // TODO: Rename and change types of parameters
    private String mJsonCategory;
    private String mCatName;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private bySecondAdapter mAdapter;
    List<CategoryData> listCategoryData;



    View rootView;

    private OnFragmentInteractionListener mListener;

    public bySecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment bySecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static bySecondFragment newInstance(String param1, String param2) {
        bySecondFragment fragment = new bySecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mJsonCategory = getArguments().getString(ARG_PARAM1);
            mCatName = getArguments().getString(ARG_PARAM2);
            Gson gson = new Gson();
            listCategoryData = gson.fromJson(mJsonCategory,new TypeToken<List<CategoryData>>() {}.getType());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_by_second, container, false);
        TextView textViewCatName = rootView.findViewById(R.id.large_category_name);
        textViewCatName.setText(mCatName);
        buildRecyclerView();
        return rootView;
    }

    private void buildRecyclerView()
    {
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new bySecondAdapter(listCategoryData);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // click item send info to product Info page
        mAdapter.setOnClickListner(new bySecondAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(CategoryData item) {
                Log.e("test","onItemClick "+item);
                Log.e("test","item: "+item);
                Intent intent = new Intent(getActivity(), ProductInfo.class);
                //intent.putExtra("addItem", listCategoryData.get(position));
                intent.putExtra("cat_image", item.getCatImg());
                intent.putExtra("cat_id", item.getCatID());
                intent.putExtra("cat_name", item.getCatName());
                intent.putExtra(LoginActivity.CAT_DATA, mJsonCategory);
                intent.putExtra(LoginActivity.LC_DATA, mJsonCategory);
                Gson gson = new Gson();
                String mJsonFridgeData = gson.toJson(MainActivity.listFridgeItem);
                intent.putExtra(LoginActivity.FRIDGE_DATA, mJsonFridgeData);
                startActivityForResult(intent, MainActivity.REQUEST_CODE_ADD);
                //mListener.onFragmentInteractionFromSecond(item.getCatID());
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
                    mListener.onFragmentInteractionFromSecond(jsonEditedItem);
                }
                Log.e("test", "Add item: "+jsonEditedItem);

                //updateServerData();
            }
            else if( resultCode == RESULT_CANCELED) {

            }
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
        void onFragmentInteractionFromSecond(String sNewItem);
    }
}
