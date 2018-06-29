package com.example.jlee.frigefriend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signupTextView = (TextView) findViewById(R.id.textViewSignup);
        signupTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
                LoginActivity.this.startActivity(signupIntent);
            }
        });
    }
}
