package com.example.jlee.frigefriend;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignupRequest extends StringRequest {

    //final static private String URL="http://192.168.0.132/UserSignup.php";
    final static private String URL=MainActivity.ServerURL + "UserSignup.php";
    private Map<String, String> parameters;

    public SignupRequest(String userID, String userPassword, String userEmail, String updateTime, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userEmail", userEmail);
        parameters.put("updateTime", updateTime);

        Log.e("test", "userID : "+userID);
        Log.e("test", "userPassword : "+userPassword);
        Log.e("test", "userEmail : "+userEmail);
        //test again
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
