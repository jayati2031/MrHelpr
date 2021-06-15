package com.example.mrhelper.Common.LoginSignup;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mrhelper.DataBases.PreferenceUtils;
import com.example.mrhelper.DataBases.SessionManager;
import com.example.mrhelper.R;
import com.example.mrhelper.User.UserDashboard;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.CountryCodePicker;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    TextView callSignUp, LogoText, SloganText;
    Button loginBtn;
    ImageView image;
    TextInputLayout phoneNumb, password;
    TextInputEditText phoneNumberEditText, passwordEditText;
    CountryCodePicker ccp2;
    ProgressBar progressBar;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.signUp);
        LogoText = findViewById(R.id.logo_name);
        SloganText = findViewById(R.id.slogan_name);
        loginBtn = findViewById(R.id.login_btn);
        image = findViewById(R.id.logo_image);
        phoneNumb = findViewById(R.id.phone_numb);
        password = findViewById(R.id.password);
        phoneNumberEditText = findViewById(R.id.login_phone_number_edit_text);
        passwordEditText = findViewById(R.id.login_password_edit_text);
        ccp2 = findViewById(R.id.ccp2);
        rememberMe = findViewById(R.id.remember_me);
        progressBar = findViewById(R.id.progressBar);

        if (PreferenceUtils.getPhoneNo(this) != null ){
            Intent intent = new Intent(Login.this, UserDashboard.class);
            startActivity(intent);
        }

        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
        if(sessionManager.checkRememberMe()) {
            HashMap<String,String> rememberMeDetails = sessionManager.getRememberMeDetailFromSession();
            phoneNumberEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPHONENUMBER));
            passwordEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPASSWORD));
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isConnected(Login.this)) {
                    showCustomDialog();
                }
                
                if(!validatePassword() | !validatePhoneNo()){
                    return;
                }
                else {
                    isUser();
                }
            }
        });

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(LogoText,"logo_text");
                pairs[2] = new Pair<View,String>(SloganText,"logo_desc");
                pairs[3] = new Pair<View,String>(phoneNumb,"phone_tran");
                pairs[4] = new Pair<View,String>(password,"password_tran");
                pairs[5] = new Pair<View,String>(loginBtn,"button_tran");
                pairs[6] = new Pair<View,String>(callSignUp,"login_signup_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent,options.toBundle());
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("No Internet Connection! Please connect to internet to proceed further.")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    }
                });
    }

    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        }
        else {
            return false;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhoneNo() {
        String val = phoneNumb.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            phoneNumb.setError("Field cannot be empty");
            return false;
        } else {
            phoneNumb.setError(null);
            phoneNumb.setErrorEnabled(false);
            return true;
        }
    }

    private void isUser() {
        progressBar.setVisibility(View.VISIBLE);
        String phoneNumber = phoneNumb.getEditText().getText().toString().trim();
        if(phoneNumber.charAt(0) == '0') {
            phoneNumber = phoneNumber.substring(1);
        }
        final String userEnteredPhoneNumber = "+" + ccp2.getFullNumber() +phoneNumber;
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        if(rememberMe.isChecked()) {
            SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(phoneNumber,userEnteredPassword);
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("phoneNo").equalTo(userEnteredPhoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phoneNumb.setError(null);
                    phoneNumb.setErrorEnabled(false);
                    String passwordFromDB = dataSnapshot.child(userEnteredPhoneNumber).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)) {
                        phoneNumb.setError(null);
                        phoneNumb.setErrorEnabled(false);
                        String nameFromDB = dataSnapshot.child(userEnteredPhoneNumber).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredPhoneNumber).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredPhoneNumber).child("phoneNo").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredPhoneNumber).child("email").getValue(String.class);

                        PreferenceUtils.savePhoneNo(phoneNoFromDB, Login.this);
                        PreferenceUtils.savePassword(userEnteredPassword, Login.this);

                        Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    phoneNumb.setError("No such User exist");
                    phoneNumb.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
