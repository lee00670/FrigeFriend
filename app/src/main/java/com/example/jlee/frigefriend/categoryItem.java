package com.example.jlee.frigefriend;

public class categoryItem {
    private int mImageResource;
    private String mText1;

    public categoryItem(int imageRecourse, String text1){

        mImageResource= imageRecourse;
        mText1=text1;
    }

    public int getmImageResource(){
        return mImageResource;
    }

    public String getmText1(){
        return mText1;
    }
}
