package com.example.mrhelper.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mrhelper.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.example.mrhelper.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.example.mrhelper.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.mrhelper.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.example.mrhelper.DataBases.UserHelperClass;
import com.example.mrhelper.R;
import com.example.mrhelper.Services.Electrician;
import com.example.mrhelper.Services.HomeTutor;
import com.example.mrhelper.Services.Pathalogist;
import com.example.mrhelper.Services.Plumber;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView featuredRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    private static final String TAG = "MyActivity";

    static final float END_SCALE = 0.7f;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    LinearLayout contentView;
    TextView allServices, search;
    ImageView elec, plumb, doc, teach;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        search = findViewById(R.id.search);
        elec = findViewById(R.id.elec_img);
        plumb = findViewById(R.id.plumb_img);
        doc = findViewById(R.id.doc_img);
        teach = findViewById(R.id.teach_img);

        featuredRecycler = findViewById(R.id.featured_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content_m);

        allServices = findViewById(R.id.all_services);

        navigationDrawer();

        featuredRecycler();
        categoriesRecycler();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, Search.class);
                startActivity(intent);
            }
        });

        elec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, Electrician.class);
                startActivity(intent);
            }
        });

        plumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, Plumber.class);
                startActivity(intent);
            }
        });

        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, Pathalogist.class);
                startActivity(intent);
            }
        });

        teach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, HomeTutor.class);
                startActivity(intent);
            }
        });

        allServices.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(UserDashboard.this, AllCategories.class);
                startActivity(intent);
            }
        });
    }

    private void navigationDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navigation_home);

        menuIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                break;

            case R.id.nav_all_categories:
                startActivity(new Intent(getApplicationContext(), AllCategories.class));
                break;

            case R.id.navigation_search:
                startActivity(new Intent(getApplicationContext(), Search.class));
                break;

            case R.id.nav_profile:
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                String name = getIntent().getStringExtra("name");
                String username = getIntent().getStringExtra("username");
                String email = getIntent().getStringExtra("email");
                String phoneNo = getIntent().getStringExtra("phoneNo");
                String password = getIntent().getStringExtra("password");

                Log.e(TAG, "onNavigationItemSelected: Error" + phoneNo, null);
                UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
                reference.child(phoneNo).setValue(helperClass);

                Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                intent.putExtra("name", name);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                intent.putExtra("phoneNo", phoneNo);
                intent.putExtra("password", password);
                startActivity(intent);
                break;

            case R.id.nav_share:
                break;

            case R.id.nav_rate_us:
                startActivity(new Intent(getApplicationContext(), RateUs.class));
                break;
        }
        return true;
    }

    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.qline, "Qline Diagnostics", "TB Test, Allergy Testing, Hormone testing and Blood Group Test"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.krish, "Krish Education", "Home Tutors for class XI and XII Commerce students."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.plumb, "Bukhari Plumber", "Tap Repairing, Pipeline System for whole building and other services."));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);

    }

    private void categoriesRecycler() {

        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});

        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.repair_icon, "Repairing"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient2, R.drawable.education_icon, "Education"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient3, R.drawable.medical_icon, "Medical"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient4, R.drawable.house_clean_icon, "HomeCleaning"));

        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }
}
