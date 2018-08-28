package com.example.jlee.frigefriend;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class bySecondAdapter extends RecyclerView.Adapter<bySecondAdapter.addViewHolder> {
    private List<CategoryData> listCategoryData;
    private bySecondAdapter.OnItemClickListner mListner;

    public interface OnItemClickListner {
        void onItemClick(CategoryData item);
    }

    public void setOnClickListner(bySecondAdapter.OnItemClickListner listner) {
        mListner = listner;
    }


    public static class addViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextCatName;

        public addViewHolder(@NonNull View itemView, final bySecondAdapter.OnItemClickListner listner) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageview1);
            mTextCatName = itemView.findViewById(R.id.textView1);

        }
    } //END OF public static class addViewHolder

    public bySecondAdapter(List<CategoryData> addList) {
        listCategoryData = addList;

    }

    @NonNull
    @Override
    public bySecondAdapter.addViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_item, viewGroup, false);
        bySecondAdapter.addViewHolder avh = new bySecondAdapter.addViewHolder(v, mListner);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull bySecondAdapter.addViewHolder addViewHolder, final int i) {
        final bySecondAdapter.addViewHolder baseViewHolder = (bySecondAdapter.addViewHolder) addViewHolder;
        CategoryData currentItem = listCategoryData.get(i);

        addViewHolder.mImageView.setImageResource(currentItem.getCatImg());
        addViewHolder.mTextCatName.setText(currentItem.getCatName());

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

}
