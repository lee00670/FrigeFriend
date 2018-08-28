package com.example.jlee.frigefriend;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class byLCategoryAdapter extends RecyclerView.Adapter<byLCategoryAdapter.addViewHolder> {

    private List<LCData> listLCategoryData;
    private byLCategoryAdapter.OnItemClickListner mListner;

    public interface OnItemClickListner {
        void onItemClick(LCData item);
    }

    public void setOnClickListner(byLCategoryAdapter.OnItemClickListner listner) {
        mListner = listner;
    }


    public static class addViewHolder extends RecyclerView.ViewHolder{

        //public ImageView mImageView;
        public TextView mTextLCName;

        public addViewHolder(@NonNull View itemView, final byLCategoryAdapter.OnItemClickListner listner) {
            super(itemView);

            //mImageView = itemView.findViewById(R.id.imageview1);
            mTextLCName = itemView.findViewById(R.id.textViewLCName);



        }
    } //END OF public static class addViewHolder

    public byLCategoryAdapter(List<LCData> addList) {
        listLCategoryData = addList;

    }

    @NonNull
    @Override
    public byLCategoryAdapter.addViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lcdata_item, viewGroup, false);
        byLCategoryAdapter.addViewHolder avh = new byLCategoryAdapter.addViewHolder(v, mListner);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull byLCategoryAdapter.addViewHolder addViewHolder, final int i) {
        final byLCategoryAdapter.addViewHolder baseViewHolder = (byLCategoryAdapter.addViewHolder) addViewHolder;
        LCData currentItem = listLCategoryData.get(i);

        //addViewHolder.mImageView.setImageResource(currentItem.getCatImg());
        addViewHolder.mTextLCName.setText(currentItem.getName());

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if (mListner != null) {
                    int position = i;
                    if(position != RecyclerView.NO_POSITION){
                        mListner.onItemClick(listLCategoryData.get(i));
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listLCategoryData.size();
    }

}
