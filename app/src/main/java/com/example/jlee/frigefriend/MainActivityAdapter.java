/*
*
* RecyclerView List Adapter
*
* */
package com.example.jlee.frigefriend;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopeer.itemtouchhelperextension.Extension;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.Holderview> {

    public static final int ITEM_TYPE_RECYCLER_WIDTH = 1000;
    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    public static final int ITEM_TYPE_ACTION_WIDTH_NO_SPRING = 1002;
    public static final int ITEM_TYPE_NO_SWIPE = 1003;

    private List<FridgeItem> fridgeItemList;
    private List<CategoryData> categoryDataList;
    private Context context;
    private ItemTouchHelperExtension mItemTouchHelperExtension;

    interface OnItemCheckListener {
        void onItemCheck(FridgeItem item);
        void onItemUncheck(FridgeItem item);
        void onItemClick(FridgeItem item);
    }
    final private OnItemCheckListener onItemClickListener;

    public MainActivityAdapter (List<FridgeItem> items, @NonNull OnItemCheckListener onItemCheckListener) {
        this.onItemClickListener = onItemCheckListener;
    }

    public MainActivityAdapter(List<FridgeItem> fridgeItemList, List<CategoryData> categoryDataList, Context context, @NonNull OnItemCheckListener onItemCheckListener) {
        this.fridgeItemList = fridgeItemList;
        this.categoryDataList = categoryDataList;
        this.context = context;
        this.onItemClickListener = onItemCheckListener;
    }

    public void setItemTouchHelperExtension(ItemTouchHelperExtension itemTouchHelperExtension) {
        mItemTouchHelperExtension = itemTouchHelperExtension;
    }

    @NonNull
    @Override
    public Holderview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fridge_item, viewGroup, false);
        //return new ItemSwipeWithActionWidthViewHolder(layout);
        return new Holderview(layout);
    }
    private Map<String, Boolean> checkBoxStates = new HashMap<>();
    private Boolean bUncheck = false;
    @Override
    public void onBindViewHolder(@NonNull Holderview holderview, final int i) {
        //bind data
        final Holderview baseViewHolder = (Holderview) holderview;
        baseViewHolder.bind(fridgeItemList.get(i), i);

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                //FridgeItem item = (FridgeItem) view.findViewById(R.id.item_layout);
                Log.e("test","clicked: "+fridgeItemList.get(i).getItemName()+", pos: "+i);
                onItemClickListener.onItemClick(fridgeItemList.get(i));
                Toast.makeText(context, "click on "+fridgeItemList.get(i).getItemName(), Toast.LENGTH_LONG).show();
            }
        });


        Boolean checkedState = checkBoxStates.get(bUncheck ? false: baseViewHolder.mCheckBox.isChecked());
        baseViewHolder.mCheckBox.setChecked(checkedState == null ? false : checkedState);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        Log.e("test","Clicked;0");
    }

    public void doUncheck(int adapterPosition) {
        bUncheck = true;
        notifyDataSetChanged();
        bUncheck = false;
    }
    public void move(int from, int to) {
        FridgeItem prev = fridgeItemList.remove(from);
        fridgeItemList.add(to > from ? to - 1 : to, prev);
        notifyItemMoved(from, to);

    }

    @Override
    public int getItemViewType(int position) {

        return ITEM_TYPE_ACTION_WIDTH;
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
        CheckBox mCheckBox;

        CardView mItemLayout;
        RelativeLayout mItemActionLayout;

        public Holderview(View v)
        {
            super(v);
            mCatImg = (ImageView) v.findViewById(R.id.catImg);
            mTextViewItemName = (TextView) v.findViewById(R.id.textViewItemName);

            mTextViewDaysLeft = (TextView) v.findViewById(R.id.textViewDaysLeft);
            mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            mTextViewExpDate = (TextView) v.findViewById(R.id.textViewExpDate);
            mTextViewQuantity = (TextView) v.findViewById(R.id.textViewQuantity);
            Typeface typeface = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/Aller_Lt.ttf");
            mTextViewItemName.setTypeface(typeface);
            mTextViewDaysLeft.setTypeface(typeface);
            mTextViewExpDate.setTypeface(typeface);
            mTextViewQuantity.setTypeface(typeface);
            mItemLayout = (CardView) v.findViewById(R.id.item_layout);
            mItemActionLayout = (RelativeLayout) v.findViewById(R.id.view_list_action_container);
            mCheckBox = (CheckBox) v.findViewById(R.id.checkboxRV);
            //mCheckBox.setClickable(false);
        }
        View getViewToSwipe() {
            return mItemLayout;
        }
        View getViewToAnimate() {
            return mItemActionLayout;
        }

        public void bind(FridgeItem fridgeItem, int position)
        {
            mCatImg.setImageResource(fridgeItem.getCatImg());
            mTextViewItemName.setText(fridgeItem.getItemName());
            final int pos = position;
            final FridgeItem currentItem = fridgeItem;

            //get today's date as string
            Date c = Calendar.getInstance().getTime();

            //get the expiration date from item list as date format
            String dtExpDate = fridgeItem.getExpDate();
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

            mTextViewDaysLeft.setText((diff < 0 ? " Expired" : (diff == 0 ? " Last day":
                    (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 6 ? " "+TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+" days left":""))));

            if(endDate != null)
            {
                String day          = (String) DateFormat.format("dd",   endDate); // 20
                String monthString  = (String) DateFormat.format("MMM",  endDate); // Jun
                String year         = (String) DateFormat.format("yyyy", endDate);
                String expDate = ""+monthString+" "+day+", "+year;
                mTextViewExpDate.setText(expDate);
            }
            else
            {
                mTextViewExpDate.setText("unknown");
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
            mProgressBar.setProgress(mProgressBarStatus);
            int color=Color.GREEN;
            if(mProgressBarStatus == 100)
            {
                color = Color.RED;
            }
            mProgressBar.getProgressDrawable().setColorFilter(
                    color, android.graphics.PorterDuff.Mode.SRC_IN);
            mTextViewQuantity.setText(" "+fridgeItem.getQuantity()+" "+fridgeItem.getQuantityUnit());
            mCheckBox.setChecked(fridgeItem.getChecked()==1?true:false);
            Log.e("test", "test check?");

            mCheckBox.setOnClickListener(new View.OnClickListener()     {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;

                    if (mCheckBox.isChecked()) {
                        onItemClickListener.onItemCheck(currentItem);
                    } else {
                        onItemClickListener.onItemUncheck(currentItem);
                    }
//                    Toast.makeText(
//                            v.getContext(), ""+pos + cb.isChecked(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    class ItemSwipeWithActionWidthViewHolder extends Holderview implements Extension {

        TextView mActionViewDelete;
        TextView mActionViewAddToCart;

        public ItemSwipeWithActionWidthViewHolder(View itemView) {
            super(itemView);
            mActionViewDelete = (TextView) itemView.findViewById(R.id.view_list_action_delete);
            mActionViewAddToCart = (TextView) itemView.findViewById(R.id.view_list_action_addToCart);
        }

        @Override
        public float getActionWidth() {
            return mItemActionLayout.getWidth();
        }
    }

}
