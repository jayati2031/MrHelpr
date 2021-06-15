package com.example.mrhelper.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mrhelper.R;

public class RateUs extends AppCompatActivity {

    Button submit_btn;
    RatingBar ratingBar;

    float myRating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        submit_btn = findViewById(R.id.button);
        ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int v = (int) rating;
                String message = null;

                myRating = ratingBar.getRating();

                switch (v) {
                    case 1:
                        message="Sorry to hear that! :(";
                        break;

                    case 2:
                        message="We always accept suggestions!";
                        break;

                    case 3:
                        message="Good enough!";
                        break;

                    case 4:
                        message="Great! Thank you!";
                        break;

                    case 5:
                        message="Awesome! You are the best!";
                        break;
                }

                Toast.makeText(RateUs.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RateUs.this, "Your rating is: " + myRating, Toast.LENGTH_SHORT).show();
                RateUs.super.onBackPressed();
            }
        });
    }
}
