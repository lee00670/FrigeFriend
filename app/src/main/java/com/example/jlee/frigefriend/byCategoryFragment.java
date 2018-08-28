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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link byCategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link byCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class byCategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "cat";
    private static final String ARG_PARAM2 = "large_cat";

    // TODO: Rename and change types of parameters
    private String mJsonCategory;
    private String mJsonLCategory;
    List<LCData> listLCategoryData;
    List<CategoryData> listCategoryData;
    View rootView;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private byLCategoryAdapter mAdapter;
    private int sort_by = MainActivity.SORT_BY_CAT;

    private OnFragmentInteractionListener mListener;

    public byCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment byCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static byCategoryFragment newInstance(String param1, String param2) {
        byCategoryFragment fragment = new byCategoryFragment();
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
            mJsonLCategory = getArguments().getString(ARG_PARAM2);
            Gson gson = new Gson();
            listLCategoryData = gson.fromJson(mJsonLCategory,new TypeToken<List<LCData>>() {}.getType());
            listCategoryData = gson.fromJson(mJsonCategory,new TypeToken<List<CategoryData>>() {}.getType());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_by_category, container, false);
        buildRecyclerView();
        return rootView;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionFromCat(1);
        }
    }

    private void buildRecyclerView()
    {
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new byLCategoryAdapter(listLCategoryData);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // click item send info to product Info page
        mAdapter.setOnClickListner(new byLCategoryAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(LCData item) {
                Log.e("test","onItemClick "+item.getLCID());
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                /*
                 * IMPORTANT: We use the "root frame" defined in
                 * "root_fragment.xml" as the reference to replace fragment
                 */
                ArrayList<CategoryData> newList = new ArrayList<>();

                for(CategoryData catItem : listCategoryData )
                {
                    if(catItem.getLCID() == item.getLCID())
                    {
                        newList.add(catItem);
                    }
                }
                Log.e("test", "selected cat: "+newList);
                Gson gson = new Gson();
                String jsonSelectedCategory = gson.toJson(newList);

                trans.replace(R.id.root_frame, bySecondFragment.newInstance(jsonSelectedCategory, item.getName()));

                /*
                 * IMPORTANT: The following lines allow us to add the fragment
                 * to the stack and return to it later, by pressing back
                 */
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);

                trans.commit();
                mListener.onFragmentInteractionFromCat(item.getLCID());
            }
        });
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
        void onFragmentInteractionFromCat(int iNewItem);
    }
}
