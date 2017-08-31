package com.example.fukuisaeko.tmsapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fukuisaeko on 2017-08-08.
 */

public class FavoriteActivity extends AppCompatActivity {
    ArrayList<String> favoriteList;
    private FavoriteAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstaneState) {
        super.onCreate(savedInstaneState);
        setContentView(R.layout.favorite_view);
        recyclerView = (RecyclerView)findViewById(R.id.favorite_recycler);

        Intent intent = getIntent();
        favoriteList =  intent.getStringArrayListExtra("favList");

        LinearLayoutManager linearMng = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearMng);
        adapter = new FavoriteAdapter(favoriteList, this.getApplicationContext());
        recyclerView.setAdapter(adapter);

    }
}
