package com.iteritory.itcmaterialnavigationdrawer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.view.View.OnClickListener;
import android.widget.Toast;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.PendingIntent.getActivity;

public class LoginActivity extends AppCompatActivity implements OnClickListener{
    private static FragmentManager fragmentManager;
    private View view;
    Activity context;
    private EditText emailid;
    private EditText password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private CheckBox checkbox;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private AppCompatAutoCompleteTextView autoTextViewCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        setListeners();


    }
    private void initViews(){
        emailid = findViewById(R.id.login_emailid);
        password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.loginBtn);
        forgotPassword = findViewById(R.id.forgot_password);
        signUp = findViewById(R.id.createAccount);
        checkbox =  findViewById(R.id.show_hide_password);
        loginLayout = findViewById(R.id.login_layout);
        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            forgotPassword.setTextColor(csl);
            checkbox.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }

    }
    private void setListeners() {
        loginButton.setOnClickListener(this);
        signUp.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    checkbox.setText(R.string.show_pwd);// change
                    // checkbox
                    // text

                    password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password

                } else {
                    checkbox.setText(R.string.hide_pwd);// change
                    // checkbox
                    // text

                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password

                }
            }
        });


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;
            case R.id.createAccount:
                startActivity(new Intent(LoginActivity.this, Signup_Activity.class));
                break;
            case R.id.forgot_password:
                startActivity(new Intent(LoginActivity.this, Forgot_password.class));

        }
    }

    private void checkValidation(){

        String getEmailId = emailid.getText().toString();


        String getPassword = password.getText().toString();

        // Check pattern for email id
        Pattern p = Pattern.compile(Utils.regEx);

        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            Toast.makeText(LoginActivity.this,
                    "Enter Both Credentials.",Toast.LENGTH_SHORT);

        }
        // Check if email id is valid or not
        else if (!m.find())
            Toast.makeText(getApplicationContext(),
                    "Your Email Id is Invalid.",Toast.LENGTH_SHORT);
            // Else do login and do your stuff
        else {
            Toast.makeText(getApplicationContext(), "Successfully Login.", Toast.LENGTH_SHORT)
                    .show();

        }
    }

}
