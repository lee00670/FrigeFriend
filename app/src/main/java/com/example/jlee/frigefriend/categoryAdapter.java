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
import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.CategoryViewHolder>{

    private  List<CategoryData> mCategoryList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener =listener;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageViewCat;
        public TextView mTextViewCat;
        public ImageView mImageCheck;
        public CategoryViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            mImageViewCat = itemView.findViewById(R.id.imageCat);
            mTextViewCat = itemView.findViewById(R.id.textViewCat);
            mImageCheck = itemView.findViewById(R.id.imageViewCheckMark);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }

    public  categoryAdapter(List<CategoryData> categoryList){
        mCategoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item, viewGroup, false);
        CategoryViewHolder cvh = new CategoryViewHolder(v, mListener);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        CategoryData currentItem = mCategoryList.get(i);

        categoryViewHolder.mImageViewCat.setImageResource(currentItem.getCatImg());
        categoryViewHolder.mTextViewCat.setText(currentItem.getCatName());
        categoryViewHolder.mImageCheck.setImageResource(R.drawable.ic_check_black_24dp);
        categoryViewHolder.mImageCheck.setVisibility(currentItem.isChecked()?View.VISIBLE:View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }
}
