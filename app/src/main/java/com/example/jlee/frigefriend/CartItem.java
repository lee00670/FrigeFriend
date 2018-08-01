/*
* CartItem
*  Item information in cart list
*  This shows id, name, category id, category image, quantity, quantity unit.
* */

package com.example.jlee.frigefriend;

public class CartItem {
    private  int itemID;
    private String itemName;
    private int catID;
    private int catImg;
    private  int quantity;
    private String quantityUnit;
//constructor
    public CartItem(int itemID, String itemName, int catID, int catImg, int quantity, String quantityUnit) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.catID = catID;
        this.catImg = catImg;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
    }
//getter and setter
    public int getCatImg() {
        return catImg;
    }

    public void setCatImg(int catImg) {
        this.catImg = catImg;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCatID() {
        return catID;
    }

    public void setCatID(int catID) {
        catID = catID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public int getItemID(){
        return itemID;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getQuantityUnit(){
        return quantityUnit;
    }

    @Override
    public String toString() {
        return "{\"itemID\" : " + itemID + ", " +
                "\"itemName\" : " + itemName + ", " +
                "\"catID\" : " + catID + ", " +
                "\"catImg\" : " + catImg + ", " +
                "\"quantity\" : " + quantity+ ", " +
                ", \"quantityUnit\" : " + quantityUnit+
                "}";
    }
}
