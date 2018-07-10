package com.example.jlee.frigefriend;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
        //bind data
        holderview.mCatImg.setImageResource(fridgeItemList.get(i).getCatImg());
        holderview.mTextViewItemName.setText(fridgeItemList.get(i).getItemName());

        //get today's date as string
        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//        String formattedDate = df.format(c);
//        //Log.e("test", "today :  "+ formattedDate);

        //get the expiration date from item list as date format
        String dtExpDate = fridgeItemList.get(i).getExpDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date endDate=null;
        try {
            endDate = format.parse(dtExpDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }



        //days left (expiration time - current time)
        long diff = endDate.getTime() - c.getTime();
        Log.e("test", "diff: "+diff);
        String sDaysLeftMessage = "";


        holderview.mTextViewDaysLeft.setText((diff < 0 ? " Expired" : (diff == 0 ? " Last day":
                (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 6 ? " "+TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+" days left":""))));

        if(endDate != null)
        {
            String day          = (String) DateFormat.format("dd",   endDate); // 20
            String monthString  = (String) DateFormat.format("MMM",  endDate); // Jun
            String year         = (String) DateFormat.format("yyyy", endDate);
            String expDate = ""+monthString+" "+day+", "+year;
            holderview.mTextViewExpDate.setText(expDate);
        }
        else
        {
            holderview.mTextViewExpDate.setText("unknown");
        }
        Log.e("test","days left: "+(diff < 0 ? " Expired" : (diff == 0 ? " Last day":
                (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 6 ? " "+TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+" days left":""))));
        Log.e("test", "hours: "+TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS));

        int mProgressBarStatus = (int) (5*(20-TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
        if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 1 && TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) > 0)
        {
            mProgressBarStatus = 99;
        }
        else if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) > 19)
        {
            mProgressBarStatus = 1;
        }
        else if(diff <= 0 )
        {
            mProgressBarStatus=100;
        }

        Log.e("test","diff: "+diff);
        Log.e("test","progressbar: "+mProgressBarStatus);
        holderview.mProgressBar.setProgress(mProgressBarStatus);
        int color=Color.GREEN;
        if(mProgressBarStatus == 100)
        {
            color = Color.RED;
        }
        holderview.mProgressBar.getProgressDrawable().setColorFilter(
                color, android.graphics.PorterDuff.Mode.SRC_IN);


        holderview.mTextViewQuantity.setText(" "+fridgeItemList.get(i).getQuantity()+" "+fridgeItemList.get(i).getQuantityUnit());

        holderview.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {



                String dtStart = "20101015";
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                try {
                    Date date1 = format.parse(dtStart);
                    Log.e("test", "date1: "+date1.toString().substring(0, 15)); // 2010-01-02

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "click on "+fridgeItemList.get(i).getItemName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fridgeItemList.size();
    }

    public void setFilter(List<FridgeItem> newList)
    {
        fridgeItemList = new ArrayList<>();
        fridgeItemList.addAll(newList);
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
