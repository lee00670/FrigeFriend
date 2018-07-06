package com.example.jlee.frigefriend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.textViewSignup) TextView textViewSignup;
    @BindView(R.id.buttonLogin) TextView buttonLogin;
    @BindView(R.id.editTextID)  EditText editTextID;
    @BindView(R.id.editTextPW)  EditText editTextPW;
    @BindView(R.id.textViewWarningLogin)  TextView textViewWarningLogin;

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
                    JSONObject jsonResponse  = new JSONObject(response);
                    boolean foundID = jsonResponse.getBoolean("id");
                    boolean foundPW = jsonResponse.getBoolean("pw");
                    textViewWarningLogin.setEnabled(true);
                    textViewWarningLogin.setText("");
                    if(foundID && foundPW)
                    {
                        Log.e("test","success");
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
