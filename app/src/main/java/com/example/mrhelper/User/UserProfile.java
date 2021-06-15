package com.example.mrhelper.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mrhelper.Common.LoginSignup.Login;
import com.example.mrhelper.DataBases.PreferenceUtils;
import com.example.mrhelper.DataBases.UserHelperClass;
import com.example.mrhelper.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {
    TextInputLayout fullName, email, phoneNo, password;
    TextView fullNameLabel, usernameLabel;
    Button update, logout;
    DatabaseReference reference;
    String user_username, user_name, user_email, user_phoneNo, user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fullName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneNo = findViewById(R.id.phone_no1);
        password = findViewById(R.id.passs);
        fullNameLabel = findViewById(R.id.full_name);
        usernameLabel = findViewById(R.id.user_name);
        update = findViewById(R.id.update);
        logout = findViewById(R.id.logout);
        reference = FirebaseDatabase.getInstance().getReference("users");

        showAllUserData();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNameChanged()  || isPasswordChanged()) {
                    Toast.makeText(UserProfile.this, "Data has been updated", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(UserProfile.this, "Data is same and cannot be updated", Toast.LENGTH_LONG).show();
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.savePassword(null, UserProfile.this);
                PreferenceUtils.savePhoneNo(null, UserProfile.this);
                Intent intent = new Intent(UserProfile.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        user_username = intent.getStringExtra("username");
        user_name = intent.getStringExtra("name");
        user_email = intent.getStringExtra("email");
        user_phoneNo = intent.getStringExtra("phoneNo");
        user_password = intent.getStringExtra("password");

        fullNameLabel.setText(user_name);
        usernameLabel.setText(user_username);
        fullName.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_phoneNo);
        password.getEditText().setText(user_password);
    }

    private boolean isPasswordChanged(){
        if(!user_password.equals(password.getEditText().getText().toString()))
        {
            reference.child(user_phoneNo).child("password").setValue(password.getEditText().getText().toString());
            user_password = password.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isNameChanged(){
        if(!user_name.equals(fullName.getEditText().getText().toString())){
            reference.child(user_phoneNo).child("name").setValue(fullName.getEditText().getText().toString());
            user_name = fullName.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }

    }
}
