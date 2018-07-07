package com.example.jlee.frigefriend;

public class CartItem {
    private  int itemID;
    private  int quantity;
    private String quantityUnit;

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
        return "{\"itemID\" : " + itemID + ", \"quantity\" : " + quantity + ", \"quantityUnit\" : " + quantityUnit + "}";
    }
}
