package com.example.mrhelper.Services;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrhelper.HelperClasses.ServicesAdapter.plumberAdapter;
import com.example.mrhelper.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Plumber extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    List<String> pName,pPhoneNo,pAddress,pHours,pImages;

    ProgressBar progressBar;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber);

        recyclerView = findViewById(R.id.recyclerView12);
        progressBar = findViewById(R.id.progres);
        backBtn = findViewById(R.id.back_pressed);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plumber.super.onBackPressed();
            }
        });

        pName = new ArrayList<>();
        pPhoneNo = new ArrayList<>();
        pAddress = new ArrayList<>();
        pHours = new ArrayList<>();
        pImages = new ArrayList<>();

        startXLFileReading();

        recyclerView.setLayoutManager(new LinearLayoutManager(Plumber.this));
        adapter = new plumberAdapter(Plumber.this, pName, pPhoneNo, pAddress, pHours, pImages);
        recyclerView.setAdapter((RecyclerView.Adapter) adapter);
    }

    public void startXLFileReading(){
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("plumber.xls");
            Workbook workbook = Workbook.getWorkbook(is);
            Sheet s = workbook.getSheet(0);
            int r = s.getRows();
            for (int i = 0; i < r; i++){
                Cell[] row = s.getRow(i);
                pImages.add(row[0].getContents());
                pName.add( row[1].getContents());
                pPhoneNo.add(row[2].getContents());
                pAddress.add(row[3].getContents());
                pHours.add(row[4].getContents());
            }
            progressBar.setVisibility(View.GONE);
        }
        catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }
}
