package com.example.jlee.frigefriend;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class addAdapter extends RecyclerView.Adapter<addAdapter.addViewHolder> {

    private List<CategoryData> listCategoryData;
    private OnItemClickListner mListner;

    public interface OnItemClickListner {
        void onItemClick(CategoryData item);
    }

    public void setOnClickListner(OnItemClickListner listner) {
        mListner = listner;
    }


    public static class addViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mtextView1;

        public addViewHolder(@NonNull View itemView, final OnItemClickListner listner) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageview1);
            mtextView1 = itemView.findViewById(R.id.textView1);



        }
    } //END OF public static class addViewHolder

    public addAdapter(List<CategoryData> addList) {
        listCategoryData = addList;

    }

    @NonNull
    @Override
    public addViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_item, viewGroup, false);
        addViewHolder avh = new addViewHolder(v, mListner);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull addViewHolder addViewHolder, final int i) {
        final addViewHolder baseViewHolder = (addViewHolder) addViewHolder;
        CategoryData currentItem = listCategoryData.get(i);

        addViewHolder.mImageView.setImageResource(currentItem.getCatImg());
        addViewHolder.mtextView1.setText(currentItem.getCatName());

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if (mListner != null) {
                    int position = i;
                    if(position != RecyclerView.NO_POSITION){
                        mListner.onItemClick(listCategoryData.get(i));
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCategoryData.size();
    }

    // Search Bar
   public void filterList (List<CategoryData> filteredList){
       listCategoryData = filteredList;
       notifyDataSetChanged();
   }


} // END OF public class addAdapter e

