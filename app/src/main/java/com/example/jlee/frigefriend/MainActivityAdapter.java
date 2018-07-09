package com.example.jlee.frigefriend;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.Holderview> {

    private List<FridgeItem> fridgeItemList;
    private List<CategoryData> categoryDataList;
    private Context context;

    public MainActivityAdapter(List<FridgeItem> fridgeItemList, List<CategoryData> categoryDataList, Context context) {
        this.fridgeItemList = fridgeItemList;
        this.categoryDataList = categoryDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holderview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fridge_item, viewGroup, false);
        return new Holderview(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull Holderview holderview, final int i) {

        holderview.mCatImg.setImageResource(fridgeItemList.get(i).getCatImg());
        holderview.mTextViewItemName.setText(fridgeItemList.get(i).getItemName());
        holderview.mTextViewDaysLeft.setText(fridgeItemList.get(i).getExpDate());
        holderview.mTextViewExpDate.setText(fridgeItemList.get(i).getExpDate());
        holderview.mTextViewQuantity.setText(""+fridgeItemList.get(i).getQuantity());

        holderview.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

                Toast.makeText(context, "click on "+fridgeItemList.get(i).getItemName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fridgeItemList.size();
    }

    public void setfilter(List<FridgeItem> listitem)
    {
        fridgeItemList = new ArrayList<>();
        fridgeItemList.addAll(listitem);
        notifyDataSetChanged();
    }

    class Holderview extends RecyclerView.ViewHolder
    {
        ImageView mCatImg;
        TextView mTextViewItemName;
        TextView mTextViewDaysLeft;
        ProgressBar mProgressBar;
        TextView mTextViewExpDate;
        TextView mTextViewQuantity;

        Holderview(View v)
        {
            super(v);
            mCatImg = (ImageView) v.findViewById(R.id.catImg);
            mTextViewItemName = (TextView) v.findViewById(R.id.textViewItemName);
            mTextViewDaysLeft = (TextView) v.findViewById(R.id.textViewDaysLeft);
            mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            mTextViewExpDate = (TextView) v.findViewById(R.id.textViewExpDate);
            mTextViewQuantity = (TextView) v.findViewById(R.id.textViewQuantity);
        }

    }



}
