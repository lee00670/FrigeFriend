/*
*  Cart View RecyclerView Adapter
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ViewCartAdapter extends RecyclerView.Adapter<ViewCartAdapter.Holderview> {

    private List<CartItem> cartItemList;
    private Context context;

    interface OnSpinnerQuantityClickListener {
        void onItemCheck(CartItem item, int newQuantity);
        void onItemUncheck(CartItem item);
    }
    final private OnSpinnerQuantityClickListener onSpinnerQuantityClickListener;


    public ViewCartAdapter(List<CartItem> cartItemList, Context context, @NonNull OnSpinnerQuantityClickListener onSpinnerQuantityClickListener) {
        this.cartItemList = cartItemList;
        this.context = context;
        this.onSpinnerQuantityClickListener = onSpinnerQuantityClickListener;
    }

    @NonNull
    @Override
    public ViewCartAdapter.Holderview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item, viewGroup, false);
        return new Holderview(layout);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewCartAdapter.Holderview holderview, final int i) {
        final ViewCartAdapter.Holderview baseViewHolder = (ViewCartAdapter.Holderview) holderview;

        baseViewHolder.bind(cartItemList.get(i), i);


        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Toast.makeText(context, "click on "+cartItemList.get(i).getItemName(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void move(int from, int to) {
        CartItem prev = cartItemList.remove(from);
        cartItemList.add(to > from ? to - 1 : to, prev);
        notifyItemMoved(from, to);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    class Holderview extends RecyclerView.ViewHolder
    {
        ImageView mCatImg;
        TextView mTextViewItemName;
        Spinner mSpinnerQuantity;
        Spinner mSpinnerQuantityUnit;
        CheckBox mCheckBox;

        CardView mItemLayout;

        public Holderview(View v)
        {
            super(v);
            mCatImg = (ImageView) v.findViewById(R.id.catImg);
            mTextViewItemName = (TextView) v.findViewById(R.id.textViewItemName);

            mSpinnerQuantity = (Spinner) v.findViewById(R.id.spinnerQuantityOnCart);
            ArrayAdapter<String> quantityAdapter;
            quantityAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, v.getResources().getStringArray(R.array.quantityNum));
            quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerQuantity.setAdapter(quantityAdapter);

            mSpinnerQuantityUnit = (Spinner) v.findViewById(R.id.spinnerUnitsOnCart);
            ArrayAdapter<String> unitAdapter;
            unitAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, v.getResources().getStringArray(R.array.units));
            unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerQuantityUnit.setAdapter(unitAdapter);
            mCheckBox = v.findViewById(R.id.checkboxOrder);
        }

        public void bind(CartItem cartItem, int position)
        {
            final int pos = position;
            final CartItem currentItem = cartItem;
            mCatImg.setImageResource(cartItem.getCatImg());
            mTextViewItemName.setText(cartItem.getItemName());
            mSpinnerQuantity.setSelection(cartItem.getQuantity());
            Log.e("test","mSpinnerQuantity: "+cartItem.getQuantity() );

            ArrayAdapter myAdap = (ArrayAdapter) mSpinnerQuantityUnit.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(cartItem.getQuantityUnit());
            mSpinnerQuantityUnit.setSelection(spinnerPosition);
            mCheckBox.setOnClickListener(new View.OnClickListener()     {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    if (mCheckBox.isChecked()) {
                        doDelete(pos);
                    }
                }
            });
            mSpinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    String msupplier=mSpinnerQuantity.getSelectedItem().toString();
                    onSpinnerQuantityClickListener.onItemCheck(currentItem, mSpinnerQuantity.getSelectedItemPosition());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }
    }
    public void doDelete(int adapterPosition) {
        Log.e("test","delete : "+ adapterPosition);
        Log.e("test","cartItemList.size() : "+ cartItemList.size());

        cartItemList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }
}
