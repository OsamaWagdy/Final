package com.example.application.myfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent sentIntent = getIntent();
        Bundle sentBundle = sentIntent.getExtras();
        DetailActivityFragment mDetailsFragment = new DetailActivityFragment();
        mDetailsFragment.setArguments(sentBundle);
        getSupportFragmentManager().beginTransaction().add(R.id.f2, mDetailsFragment, "").commit();
    }

}
