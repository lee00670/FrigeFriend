package com.example.jlee.frigefriend;

import android.os.Parcel;
import android.os.Parcelable;

public class addItem implements Parcelable{
    private int mImageResource;
    private String mText1;
    private String mcat;

    public addItem(int imageResource, String text1, String cat) {
        mImageResource = imageResource;
        mText1 = text1;
        mcat = cat;

    }

    protected addItem(Parcel in) {
        mImageResource = in.readInt();
        mText1 = in.readString();
        mcat =in.readString();
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

    public String getMcat() {
        return mcat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mImageResource);
        parcel.writeString(mText1);
        parcel.writeString(mcat);
    }
} // END OF public class addItem
