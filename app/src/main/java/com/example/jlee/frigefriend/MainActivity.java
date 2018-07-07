package com.example.jlee.frigefriend;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String jsonStringUserData = intent.getStringExtra(LoginActivity.USER_DATA);
        Log.e("test","UserData:"+jsonStringUserData);

        Gson gson = new Gson();
        UserData userData = gson.fromJson(jsonStringUserData, UserData.class);
        Log.e("test", "MaingActivity: userData:"+userData.toString());
    }

}
