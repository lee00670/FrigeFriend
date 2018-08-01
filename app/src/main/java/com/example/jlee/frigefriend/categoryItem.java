package com.example.jlee.frigefriend;

public class categoryItem {
    private int mImageResource;
    private String mText1;
    public int mImageCheck;

    public categoryItem(int imageRecourse, String text1){

        mImageResource= imageRecourse;
        mText1=text1;

    }

//    public categoryItem(int milk, String milk1) {
//    }

    public void changetext1 (String text){
        mText1 = text;
    }

   public void displayCheck (int image) {
        mImageCheck = image;
    }

    public void hideCheck(int image) {
        mImageCheck = image;
    }


    public int getmImageResource(){
        return mImageResource;
    }
    public int getCheckImageResource(){
        return mImageCheck;
    }


    public String getmText1(){
        return mText1;
    }
}
