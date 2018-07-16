package com.example.jlee.frigefriend;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.CategoryViewHolder>{

    private  ArrayList<categoryItem> mcategoryList;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageview1);
            mTextView1 = itemView.findViewById(R.id.textView1);
        }
    }

    public  categoryAdapter(ArrayList<categoryItem> categoryList){
        mcategoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item, viewGroup, false);
        CategoryViewHolder cvh = new CategoryViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        categoryItem currentItem = mcategoryList.get(i);

        categoryViewHolder.mImageView.setImageResource(currentItem.getmImageResource());
        categoryViewHolder.mTextView1.setText(currentItem.getmText1());

    }

    @Override
    public int getItemCount() {
        return mcategoryList.size();
    }
}