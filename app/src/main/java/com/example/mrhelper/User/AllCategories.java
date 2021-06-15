package com.example.mrhelper.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mrhelper.R;
import com.example.mrhelper.Services.Carpenter;
import com.example.mrhelper.Services.Dietician;
import com.example.mrhelper.Services.Electrician;
import com.example.mrhelper.Services.HomeCleaner;
import com.example.mrhelper.Services.HomeTutor;
import com.example.mrhelper.Services.Pathalogist;
import com.example.mrhelper.Services.Physiotherapy;
import com.example.mrhelper.Services.Plumber;
import com.example.mrhelper.Services.Yoga;

public class AllCategories extends AppCompatActivity {

    ImageView backBtn;
    Button electrician,homeCleaner, plumber, carpenter, dietician, homeTutor, physiotherapy, pathalogist, yoga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        backBtn = findViewById(R.id.back_pressed);
        electrician = findViewById(R.id.elec);
        homeCleaner = findViewById(R.id.home_clean);
        plumber = findViewById(R.id.plumb);
        carpenter = findViewById(R.id.carpenter);
        dietician = findViewById(R.id.diet);
        homeTutor = findViewById(R.id.tutor);
        physiotherapy = findViewById(R.id.physio);
        pathalogist = findViewById(R.id.pathologist);
        yoga = findViewById(R.id.yoga);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCategories.super.onBackPressed();
            }
        });

        electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCategories.this, Electrician.class);
                startActivity(intent);
            }
        });

        homeCleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCategories.this, HomeCleaner.class);
                startActivity(intent);
            }
        });

        plumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCategories.this, Plumber.class);
                startActivity(intent);
            }
        });

        carpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCategories.this, Carpenter.class);
                startActivity(intent);
            }
        });

        dietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCategories.this, Dietician.class);
                startActivity(intent);
            }
        });

        homeTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCategories.this, HomeTutor.class);
                startActivity(intent);
            }
        });

        physiotherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCategories.this, Physiotherapy.class);
                startActivity(intent);
            }
        });

        pathalogist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCategories.this, Pathalogist.class);
                startActivity(intent);
            }
        });

        yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCategories.this, Yoga.class);
                startActivity(intent);
            }
        });
    }
}
