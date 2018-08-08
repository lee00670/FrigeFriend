package com.example.jlee.frigefriend;

import android.os.Parcel;
import android.os.Parcelable;

public class addItem implements Parcelable{
    private int mImageResource;
    private String mText1;

    public addItem(int imageResource, String text1) {
        mImageResource = imageResource;
        mText1 = text1;

    }

    protected addItem(Parcel in) {
        mImageResource = in.readInt();
        mText1 = in.readString();
    }

    public static final Creator<addItem> CREATOR = new Creator<addItem>() {
        @Override
        public addItem createFromParcel(Parcel in) {
            return new addItem(in);
        }

        @Override
        public addItem[] newArray(int size) {
            return new addItem[size];
        }
    };

    public int getimageResource() {
        return mImageResource;
    }

    public String getText1(){
        return mText1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mImageResource);
        parcel.writeString(mText1);
    }
} // END OF public class addItem
