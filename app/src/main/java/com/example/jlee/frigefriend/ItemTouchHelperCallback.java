package com.example.jlee.frigefriend;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jlee.frigefriend.MainActivityAdapter;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

public class ItemTouchHelperCallback extends ItemTouchHelperExtension.Callback {

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //return makeMovementFlags(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.START);
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN , ItemTouchHelper.UP | ItemTouchHelper.DOWN);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        MainActivityAdapter adapter = (MainActivityAdapter) recyclerView.getAdapter();
        adapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dY != 0 && dX == 0) super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        MainActivityAdapter.Holderview holder = (MainActivityAdapter.Holderview) viewHolder;

        if (viewHolder instanceof MainActivityAdapter.Holderview)
            holder.itemView.setTranslationX(dX);
    }
//    @Override
//    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        if (dY != 0 && dX == 0) super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        MainActivityAdapter.Holderview holder = (MainActivityAdapter.Holderview) viewHolder;

//        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//
//            View itemView = viewHolder.itemView;
//
//            Paint paint = new Paint();
//            Paint paintCart = new Paint();
//            Bitmap bitmap;
//
//            if (dX < 0) { // swiping left
//                paint.setColor(itemView.getResources().getColor(R.color.colorAccent));
//                paintCart.setColor(itemView.getResources().getColor(R.color.colorGray));
////                  bitmap = BitmapFactory.decodeResource(, R.drawable.ic_shopping_cart_black_24dp);
////                 float height = (itemView.getHeight() / 2) - (bitmap.getHeight() / 2);
////                float bitmapWidth = bitmap.getWidth();
//                Drawable iconDelete = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_delete_black_24dp);
//                Drawable iconCart = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_shopping_cart_black_24dp);
//// compute top and left margin to the view bounds
//                iconDelete.setBounds((int) (viewHolder.itemView.getRight()+dX*1/3 ), (int)(itemView.getTop()+iconDelete.getIntrinsicHeight()),
//                        (int)(viewHolder.itemView.getRight()+dX*1/3+iconDelete.getIntrinsicWidth()) , (int)(itemView.getTop() + iconDelete.getIntrinsicHeight()+iconDelete.getIntrinsicHeight()));
//                iconCart.setBounds((int) (viewHolder.itemView.getRight()+dX*5/6 ), (int)(itemView.getTop()+iconCart.getIntrinsicHeight()),
//                        (int)(viewHolder.itemView.getRight()+dX*5/6+iconCart.getIntrinsicWidth()) , (int)(itemView.getTop() + iconCart.getIntrinsicHeight()+iconCart.getIntrinsicHeight()));
//
//
//                Rect rect = new Rect();
//                rect.set( (int) (itemView.getRight() + dX/2-5),  itemView.getTop(), itemView.getRight(), itemView.getBottom());
//                Log.e("test", "x: "+ (itemView.getRight() + dX/2-5)+", dX : "+dX+", x2 : "+(itemView.getRight()));
//
//                TextView textViewDelete = new TextView(itemView.getContext());
//                textViewDelete.setText("Delete");
//                textViewDelete.setBackgroundColor(itemView.getResources().getColor(R.color.colorAccent));
//                textViewDelete.setGravity(Gravity.CENTER);
//                int widthSpec = View.MeasureSpec.makeMeasureSpec(rect.width(), View.MeasureSpec.EXACTLY);
//                int heightSpec = View.MeasureSpec.makeMeasureSpec(rect.height(), View.MeasureSpec.EXACTLY);
//                textViewDelete.measure(widthSpec, heightSpec);
//                textViewDelete.layout(0, 0, rect.width(), rect.height());
//                c.save();
//                c.translate(rect.left, rect.top);
//                textViewDelete.draw(c);
//                c.restore();
//
//                Rect rectCart = new Rect();
//                rectCart.set( (int) (itemView.getRight()+dX),  itemView.getTop(),(int)(itemView.getRight() + dX/2+5), itemView.getBottom());
//                Log.e("test", "x_ : "+(itemView.getRight()+2*dX)+", dX : "+dX+", x_2 : "+(itemView.getRight()+dX-5));
//                TextView textViewCart = new TextView(itemView.getContext());
//                textViewCart.setText("Add to Cart");
//                textViewCart.setBackgroundColor(itemView.getResources().getColor(R.color.colorPrimary));
//                textViewCart.setGravity(Gravity.CENTER);
//                int widthSpec2 = View.MeasureSpec.makeMeasureSpec(rectCart.width(), View.MeasureSpec.EXACTLY);
//                int heightSpec2 = View.MeasureSpec.makeMeasureSpec(rectCart.height(), View.MeasureSpec.EXACTLY);
//                textViewCart.measure(widthSpec2, heightSpec2);
//                textViewCart.layout(0, 0, rectCart.width(), rectCart.height());
//
//
//                c.save();
//                c.translate(rectCart.left, rectCart.top);
//                textViewCart.draw(c);
//                c.restore();
//
//
//                textViewCart.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.e("test", "CART!!!!!!!!!!!!!!");
//                    }
//                });
//
//                textViewDelete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Log.e("test", "delete!!!!!!!!!!!!!!");
//                        //doDelete(v..getAdapterPosition());
//                    }
//                });
//
////                c.drawRect((float) itemView.getRight() + dX/2-5, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), paint);
////                c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight()+dX/2+5, (float) itemView.getBottom(), paintCart);
//////                  c.drawBitmap(bitmap, ((float) itemView.getRight() - bitmapWidth) - 96f, (float) itemView.getTop() + height, null);
////
////                iconDelete.draw(c);
////                iconCart.draw(c);
//
//            }
//            else{
//                paint.setColor(itemView.getResources().getColor(R.color.colorAccent));
//                //  bitmap = BitmapFactory.decodeResource(itemView.getResources(), R.drawable.ic_shopping_cart_black_24dp);
//                // float height = (itemView.getHeight() / 2) - (bitmap.getHeight() / 2);
//                //float bitmapWidth = bitmap.getWidth();
//
//                c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), paint);
//                //  c.drawBitmap(bitmap, ((float) itemView.getRight() - bitmapWidth) - 96f, (float) itemView.getTop() + height, null);
//
//            }
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//
//        }

//        if (viewHolder instanceof MainActivityAdapter.Holderview)
//            holder.itemView.setTranslationX(dX);
//    }


}