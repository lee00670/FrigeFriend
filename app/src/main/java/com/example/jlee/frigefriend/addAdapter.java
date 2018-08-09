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
    private OnItemClickListner mListner;

    public interface OnItemClickListner {
        void onItemClick(int position);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                    if (listner != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onItemClick(position);
                        }
                    }
                }
            });

        }
    } //END OF public static class addViewHolder

    public addAdapter(ArrayList<addItem> addList) {
        maddList = addList;

    }

    @NonNull
    @Override
    public addViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_item, viewGroup, false);
        addViewHolder avh = new addViewHolder(v, mListner);
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

    // Search Bar
   public void filterList (ArrayList<addItem> filteredList){
        maddList = filteredList;
        notifyDataSetChanged();
   }


} // END OF public class addAdapter e

