/*
* * Signup Activity
*
* */
package com.example.jlee.frigefriend;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class SignupActivity extends AppCompatActivity {
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
    @BindView(R.id.editTextPW)  EditText editTextPW;
    @BindView(R.id.editTextEmail) EditText editTextEMail;
    @BindView(R.id.textViewIDWarning) TextView textViewIDWarning;
    @BindView(R.id.textViewPWWarning) TextView textViewPWWarning;
    @BindView(R.id.textViewEmailWarning) TextView textViewEmailWarning;
    @BindView(R.id.buttonCreateAccount) Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        textViewPWWarning.setEnabled(true);
        textViewIDWarning.setEnabled(true);
        textViewEmailWarning.setEnabled(true);

    }

    @OnClick (R.id.buttonCreateAccount)
    void createAccount()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    JSONObject jsonResponse  = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success)
                    {
                       Log.e("test", "user id validated");

                        Response.Listener<String> createIDResponseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try
                                {
                                    JSONObject jsonResponse  = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    Log.e("test"," createIDResponseListener success : "+success);
                                    if(success)
                                    {
                                       // Toast.makeText(getApplicationContext(), "ID is created. Please log in.", Toast.LENGTH_SHORT).show();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                        dialog = builder.setMessage("ID is created. Please log in.")
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        SignupActivity.this.finish();
                                                    }
                                                })
                                                .create();
                                        dialog.show();
                                    }
                                    else
                                    {
                                        Log.e("test", "create user failed");
                                        //Toast.makeText(getApplicationContext(), "ID is not created. Please try again.", Toast.LENGTH_SHORT).show();

                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                        dialog = builder.setMessage("ID is not created. Please try again.")
                                                .setNegativeButton("OK", null)
                                                .create();
                                        dialog.show();

                                    }

                                }catch(Exception e)
                                {
                                    Log.e("err", "err");
                                    e.printStackTrace();
                                }
                            }
                        };

                        SignupRequest signupRequest = new SignupRequest(editTextID.getText().toString(),
                                editTextPW.getText().toString(), editTextEMail.getText().toString(), createIDResponseListener);
                        RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                        queue.add(signupRequest);
                        queue.start();


                    }
                    else
                    {
                        Log.e("test", "validity failed");
                        textViewIDWarning.setText("ID already exists. Please enter other one.");


                    }

                }catch(Exception e)
                {
                    Log.e("err", "err");
                    e.printStackTrace();
                }
            }
        };

        ValidateRequest validateRequest = new ValidateRequest(editTextID.getText().toString(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
        queue.add(validateRequest);
        queue.start();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }

    }
    @OnTextChanged(value = {R.id.editTextID, R.id.editTextEmail, R.id.editTextPW},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void validateView(Editable editable) {
        View view = getCurrentFocus();
        switch(view.getId())
        {
            case R.id.editTextID:

                validateID = false;
                String userID = editable.toString();
                if(userID.equals(null))
                {
                    //textViewIDWarning.setText("Please enter ID");
                    return;
                }
                if(userID.length() > 6)
                {
                    if(TextUtils.isDigitsOnly(userID.substring(0,1)))
                    {
                        //textViewIDWarning.setText("The first character cannot be digit." );
                    }
                    else
                    {
                        validateID = true;
                        textViewIDWarning.setText("");
                    }
                }
                else
                {
                    //textViewIDWarning.setText("The length should be at least over 6." );
                }
                break;
            case R.id.editTextPW:
                String password = editTextPW.getText().toString();
                if(password.length() >= 8)
                    textViewPWWarning.setText("");
                validatePW = password.length() < 8 ? false:true;
                break;
            case R.id.editTextEmail:
                String email = editTextEMail.getText().toString().trim();
                validateEmail = !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if(validateEmail)
                {
                    textViewEmailWarning.setText("");
                }
                break;
            default:
                break;
        }
        buttonCreateAccount.setEnabled(validateID && validatePW && validateEmail);
    }

    @OnFocusChange({R.id.editTextID, R.id.editTextPW, R.id.editTextEmail})
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            switch(view.getId())
            {
                case R.id.editTextID:

                    validateID = false;
                    String userID = editTextID.getText().toString();
                    if(userID.equals(null))
                    {
                        //textViewIDWarning.setText("Please enter ID");
                        return;
                    }
                    if(userID.length() > 6)
                    {
                        if(TextUtils.isDigitsOnly(userID.substring(0,1)))
                        {
                            textViewIDWarning.setText("The first character cannot be digit." );
                        }
                        else
                        {
                            textViewIDWarning.setText("" );
                            validateID = true;
                        }
                    }
                    else
                    {
                        textViewIDWarning.setText(userID.length() == 0? "":"The length should be at least over 6." );

                    }

                    break;

                case R.id.editTextPW:
                    Log.e("test", "editTextPW");
                    String password = editTextPW.getText().toString();

                    if(password.length() ==0)
                    {
                        textViewPWWarning.setText("");
                    }
                    else
                    {
                        textViewPWWarning.setText(password.length() < 8 ? "The length should be at least over 8.":"");
                    }
                    validatePW = password.length() < 8 ? false:true;
                    break;
                case R.id.editTextEmail:
                    String email = editTextEMail.getText().toString().trim();
                    validateEmail = !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
                    if(email.length() == 0)
                    {
                        textViewEmailWarning.setText("");
                    }
                    else
                    {
                        textViewEmailWarning.setText(validateEmail?"":"please enter valid email address");
                    }

                    break;
                default:
                    break;
            }
            buttonCreateAccount.setEnabled(validateID && validatePW && validateEmail);
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
