/*
* Login Request class: set the parameter of id and password what user entered.
* */
package com.example.jlee.frigefriend;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL= MainActivity.ServerURL + "UserLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
//        Log.e("test", "userID : "+userID);
//        Log.e("test", "userPassword : "+userPassword);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
