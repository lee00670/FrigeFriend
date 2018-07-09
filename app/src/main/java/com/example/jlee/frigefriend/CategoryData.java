package com.example.jlee.frigefriend;

public class CategoryData {

    private int catID;
    private String catName;
    private int catImg;
    private int LCID;

    public CategoryData(int catID, String catName, int catImg, int LCID) {
        this.catID = catID;
        this.catName = catName;
        this.catImg = catImg;
        this.LCID = LCID;
    }

    public int getCatID() {
        return catID;
    }

    public void setCatID(int catID) {
        this.catID = catID;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatImg() {
        return catImg;
    }

    public void setCatImg(int catImg) {
        this.catImg = catImg;
    }

    public int getLCID() {
        return LCID;
    }

    public void setLCID(int LCID) {
        this.LCID = LCID;
    }

    @Override
    public String toString() {
        return "{\"catID\" : " + catID + ", \"catName\" : " + catName + ", \"catImg\" : " + catImg+
                ", \"LCID\" : " + LCID+ "}";
    }
}
