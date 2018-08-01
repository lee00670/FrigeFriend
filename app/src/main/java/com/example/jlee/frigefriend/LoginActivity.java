/*
* Login page
*
* */
package com.example.jlee.frigefriend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.*;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.textViewSignup) TextView textViewSignup;
    @BindView(R.id.buttonLogin) TextView buttonLogin;
    @BindView(R.id.editTextID)  EditText editTextID;
    @BindView(R.id.editTextPW)  EditText editTextPW;
    @BindView(R.id.textViewWarningLogin)  TextView textViewWarningLogin;

    public static final String USER_DATA = "userData";
    public static final String USER_EMAIL = "userEmail";
    public static final String CAT_DATA = "category";
    public static final String LC_DATA = "lc";
    public static final String CART_DATA = "cart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.textViewSignup)
    void callSingUpPage()
    {
        Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
        LoginActivity.this.startActivity(signupIntent);
    }

    @OnClick(R.id.buttonLogin)
    void callLogin()
    {
          Response.Listener<String> userLoginListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    // parse the ID, PW from response of server
                    JSONObject jsonResponse  = new JSONObject(response);
                    boolean foundID = jsonResponse.getBoolean("id");
                    boolean foundPW = jsonResponse.getBoolean("pw");


                    textViewWarningLogin.setEnabled(true);
                    textViewWarningLogin.setText("");
                    //if id and password are found
                    if(foundID && foundPW)
                    {
                        String jsonStringUserdata = jsonResponse.getString("userdata");
                        String jsonStringCategory = jsonResponse.getString("category");
                        String jsonStringLC = jsonResponse.getString("large_category");

                        Intent LoginActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivityIntent.putExtra(LoginActivity.USER_DATA, jsonStringUserdata);
                        LoginActivityIntent.putExtra(LoginActivity.CAT_DATA, jsonStringCategory);
                        LoginActivityIntent.putExtra(LoginActivity.LC_DATA, jsonStringLC);

                        startActivity(LoginActivityIntent);
                        finish();

                    }
                    else if(foundID && !foundPW)
                    {
                        Log.e("test", "wrong pw");
                        textViewWarningLogin.setText("Wrong ID or password. Please try again.");
                    }
                    if(!foundID)
                    {
                        Log.e("test", "wrong id");
                        textViewWarningLogin.setText("Wrong ID or password. Please try again.");
                    }
                }catch(Exception e)
                {
                    Log.e("err", "err");
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(editTextID.getText().toString(),
                editTextPW.getText().toString(), userLoginListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
        queue.start();
    }
}
