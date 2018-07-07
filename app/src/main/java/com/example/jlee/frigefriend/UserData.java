package com.example.jlee.frigefriend;

import android.support.v7.app.AppCompatActivity;

import java.util.List;


public class UserData  {
    private String userID;
    private String userPW;
    private String userEmail;
    private List<FridgeItem> fridgeItems;
    private List<CartItem> cartItems;



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
        return "{\"userID\" : " + userID + ", \"userPW\" : " + userPW + ", \"userEmail\" : " + userEmail+
                ", \"fridgeItems\" : " + fridgeItems+ ", \"cartItems\" : " + cartItems+ "}";
    }
}
