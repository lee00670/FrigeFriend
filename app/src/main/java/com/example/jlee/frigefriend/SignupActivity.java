package com.example.jlee.frigefriend;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class SignupActivity extends AppCompatActivity {

    private String userID;
    private String userPassword;
    private String userEmail;
    private AlertDialog dialog;
    private boolean validateID = false;
    private boolean validatePW = false;
    private boolean validateEmail = false;
    public SignupActivity() {
    }

    @BindView(R.id.textViewCancel) TextView textViewCancel;
    @BindView(R.id.editTextID) EditText editTextID;
    @BindView(R.id.editTextPW)  EditText edtiTextPW;
    @BindView(R.id.editTextEmail) EditText editTextEMail;
    @BindView(R.id.textViewIDWarning) TextView textViewIDWarning;
    @BindView(R.id.textViewPWWarning) TextView textViewPWWarning;
    @BindView(R.id.textViewEmailWarning) TextView textViewEmailWarning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        final Button createAccountButton = (Button) findViewById(R.id.buttonCreateAccount);
        createAccountButton.setOnClickListener(new OnClickListener(){

        @Override
        public void onClick(View view){

        }
        });

    }

    @OnFocusChange(R.id.editTextID)
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            // code to execute when EditText loses focus
            textViewIDWarning.setEnabled(true);
            textViewIDWarning.setText("");
            editTextID.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                     editable.toString();
                }
            });
            String userID = editTextID.getText().toString();

            if(editTextID.getText().toString().equals(""))
            {
                textViewIDWarning.setText("Please enter ID");
                return;
            }

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try
                    {
                        JSONObject jsonResponse  = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        Log.e("err", "try");
                        if(success)
                        {
                            String userID = editTextID.getText().toString();
                            textViewIDWarning.setText("userID.length()" );
                            if(userID.equals(null))
                            {
                                Log.e("err", "try");
                            }
                            if(userID.length() > 6)
                            {
                                if(TextUtils.isDigitsOnly(userID.substring(0,1)))
                                {
                                    textViewIDWarning.setText("The first character cannot be digit." );
                                }
                                else
                                {
                                    textViewIDWarning.setText("You can use this ID." );
                                    validateID = true;
                                }
                            }
                            else
                            {
                                textViewIDWarning.setText("The lengh should be at least over 6." );
                            }

                        }
                        else
                        {
                            validateID = false;
                            textViewIDWarning.setText("This ID already exists.");
                        }

                    }catch(Exception e)
                    {
                        Log.e("err", "err");
                        e.printStackTrace();
                    }
                }
            };

            ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
            RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
            queue.add(validateRequest);
            queue.start();

        }
    }
    @OnClick(R.id.textViewCancel)
    public void clickCancel(View v){
        Log.e("test","cancel");
        Intent cancelSignupIntent = new Intent(SignupActivity.this, LoginActivity.class);
        SignupActivity.this.startActivity(cancelSignupIntent);
        finish();
    }
}
