package com.example.jlee.frigefriend;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class addAdapter extends RecyclerView.Adapter<addAdapter.addViewHolder> {

    private ArrayList<addItem> maddList;

    public static class addViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mtextView1;

        public addViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageview1);
            mtextView1 = itemView.findViewById(R.id.textView1);

        }
    } //END OF public static class addViewHolder

    public addAdapter(ArrayList<addItem> addList) {
        maddList = addList;

    }

    @NonNull
    @Override
    public addViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_item, viewGroup, false);
        addViewHolder avh = new addViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull addViewHolder addViewHolder, int i) {
        addItem currentItem = maddList.get(i);

        addViewHolder.mImageView.setImageResource(currentItem.getimageResource());
        addViewHolder.mtextView1.setText(currentItem.getText1());

    }

    @Override
    public int getItemCount() {
        return maddList.size();
    }
} // END OF public class addAdapter e

