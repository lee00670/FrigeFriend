package com.example.jlee.frigefriend;

import android.support.v7.app.AppCompatActivity;

import java.util.List;


public class UserData  {
    private String userID;
    private String userPW;
    private String userEmail;
    private String updateTime;


    private List<FridgeItem> fridgeItems;
    private List<CartItem> cartItems;
    public UserData(String userID, String userPW, String userEmail, String updateTime, List<FridgeItem> fridgeItems, List<CartItem> cartItems) {
        this.userID = userID;
        this.userPW = userPW;
        this.userEmail = userEmail;
        this.updateTime = updateTime;
        this.fridgeItems = fridgeItems;
        this.cartItems = cartItems;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<FridgeItem> getFridgeItems() {
        return fridgeItems;
    }

    public void setFridgeItems(List<FridgeItem> fridgeItems) {
        this.fridgeItems = fridgeItems;
    }

    public String getUserID(){
        return userID;
    }
    public String getUserPW(){
        return userPW;
    }
    public void setUserPW(String newPW){
        userPW = newPW;
    }
    public String getUserEmail(){
        return userEmail;
    }
    public void setUserEmail(String newEmail){
        userEmail = newEmail;
    }
    public List<FridgeItem> getFrideItems(){
        return fridgeItems;
    }

    public void setFrideItems(List<FridgeItem> newItems){
        fridgeItems = newItems;
    }

    public List<CartItem> getCartItems(){
        return cartItems;
    }
    public void setCartItems(List<CartItem> newItems){
        cartItems = newItems;
    }

    @Override
    public String toString() {
        return "{\"userID\" : " + userID +
                ", \"userPW\" : " + userPW + ", " +
                "\"userEmail\" : " + userEmail+
                ", \"updateTime\" : " + updateTime+
                ", \"fridgeItems\" : " + fridgeItems+
                ", \"cartItems\" : " + cartItems+ "}";
    }
}
