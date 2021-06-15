package com.example.mrhelper.Common.LoginSignup;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mrhelper.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rilixtech.CountryCodePicker;

public class Register extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button registerBtn;
    TextView login, logoName, sloganName;
    ImageView image;
    CountryCodePicker ccp;
    ScrollView scrollView;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        scrollView = findViewById(R.id.scrollView);
        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.user);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.phone_number);
        regPassword = findViewById(R.id.pass);
        registerBtn = findViewById(R.id.registerBtn);
        login = findViewById(R.id.login);
        logoName = findViewById(R.id.logo_name1);
        sloganName = findViewById(R.id.slogan_name1);
        image = findViewById(R.id.logo_image1);
        ccp = findViewById(R.id.country_code_picker);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                if(!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()){
                    return;
                }

                String name =regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                String _getUserEnteredPhoneNumber = regPhoneNo.getEditText().getText().toString().trim();
                String phoneNo = "+" + ccp.getFullNumber() + _getUserEnteredPhoneNumber;

                Intent intent = new Intent(getApplicationContext(), VerifyPhoneActivity.class);

                intent.putExtra("name", name);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                intent.putExtra("phoneNo", phoneNo);
                intent.putExtra("password", password);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logoName,"logo_text");
                pairs[2] = new Pair<View,String>(sloganName,"logo_desc");
                pairs[3] = new Pair<View,String>(regUsername,"username_tran");
                pairs[4] = new Pair<View,String>(regPassword,"password_tran");
                pairs[5] = new Pair<View,String>(registerBtn,"button_tran");
                pairs[6] = new Pair<View,String>(login,"login_signup_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs);
                startActivity(intent,options.toBundle());
            }
        });
    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();

        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (val.length() < 8) {
            regUsername.setError("Password should have atleast 8 characters");
            return false;
        }else if (!val.matches(passwordVal)) {
            regPassword.setError("Password must contain atleast one alphabet and one number");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }
}