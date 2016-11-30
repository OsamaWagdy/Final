package com.example.application.myfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity implements MoviesListener {

    boolean twoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
            MainActivityFragment m = new MainActivityFragment();
            m.setMoviesListener(this);
            getSupportFragmentManager().beginTransaction().add(R.id.f1, m, "").commit();
            if (findViewById(R.id.f2) != null)
                twoPane = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void setselectedmovie(String movieposter, String movieover, String movietitle, String moviedate, String movievote, String movieid) {
        if(!twoPane){
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("PosterUrl", movieposter);
            intent.putExtra("OverView", movieover);
            intent.putExtra("VoteAvg", movievote);
            intent.putExtra("ReleaseDate", moviedate);
            intent.putExtra("Title", movietitle);
            intent.putExtra("ID", movieid);
            startActivity(intent);
        }
        else {
            DetailActivityFragment d = new DetailActivityFragment();
            Bundle extras = new Bundle();
            extras.putString("PosterUrl", movieposter);
            extras.putString("OverView", movieover);
            extras.putString("VoteAvg", movievote);
            extras.putString("ReleaseDate", moviedate);
            extras.putString("Title", movietitle);
            extras.putString("ID", movieid);
            d.setArguments(extras);
            getSupportFragmentManager().beginTransaction().replace(R.id.f2,d,"").commit();
        }
    }

}
