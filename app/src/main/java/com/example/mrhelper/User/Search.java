package com.example.mrhelper.User;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.mrhelper.DataBases.SessionManager;
import com.example.mrhelper.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;
    MaterialSearchView searchView;
    ListView listView;

    String[] lstSource = {
            "Electrician",
            "Home Cleaner",
            "Plumber",
            "Carpenter",
            "Dietician",
            "Home Tutor",
            "Physiotherapy",
            "Pathalogist",
            "Yoga and Fitness Trainer",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Here");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        listView = findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,lstSource);
        listView.setAdapter(adapter);

        searchView = findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                listView = findViewById(R.id.listView);
                ArrayAdapter adapter = new ArrayAdapter(Search.this, android.R.layout.simple_list_item_1,lstSource);
                listView.setAdapter(adapter);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()) {
                    List<String> lstFound = new ArrayList<String>();
                    for(String item:lstSource) {
                        if(item.contains(newText)) {
                            lstFound.add(item);
                        }
                    }

                    ArrayAdapter adapter = new ArrayAdapter(Search.this, android.R.layout.simple_list_item_1,lstFound);
                    listView.setAdapter(adapter);
                }
                else {
                    ArrayAdapter adapter = new ArrayAdapter(Search.this, android.R.layout.simple_list_item_1,lstSource);
                    listView.setAdapter(adapter);
                }
                return true;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

}
