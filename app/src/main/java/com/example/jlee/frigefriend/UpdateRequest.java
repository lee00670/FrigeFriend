package com.example.jlee.frigefriend;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateRequest extends StringRequest {
    //final static private String URL="http://192.168.0.132/UserValidate.php"; //to check the ip address
    final static private String URL=MainActivity.ServerURL + "UpdateData.php"; //to check the ip address
    private Map<String, String> parameters;

    public UpdateRequest(String userID, String userPassword, String userEmail, String updateTime,
                         String jsonFridgeItems, String jsonCartItems, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userEmail", userEmail);
        parameters.put("updateTime", updateTime);
        parameters.put("fridgeItems", jsonFridgeItems);
        parameters.put("cartItems", jsonCartItems);

        Log.e("test", "updateRequest userID : "+userID);
        Log.e("test", "updateRequest userPassword : "+userPassword);
        Log.e("test", "updateRequest userEmail : "+userEmail);
        //test again
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
