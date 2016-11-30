package com.example.application.myfinal;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivityFragment extends Fragment {

    public List<Movies> movies = new ArrayList<>();

    public String moviesurl = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + BuildConfig.Movies_API_KEY;
    GridView gridView;
    MyAdapter myAdapter;
    private MoviesListener moviesListener ;

    void setMoviesListener(MoviesListener m){
        this.moviesListener=m;
    }

    public MainActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootview.findViewById(R.id.movies_gv);
            if(isOnline()) {
                FetchData(moviesurl);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        moviesListener.setselectedmovie(movies.get(position).getPosterUrl(), movies.get(position).getOverview(), movies.get(position).getTitle(), movies.get(position).getReleasedate(), movies.get(position).getVoteavg(), movies.get(position).getID());
                    }
                });
                return rootview;
            }
        else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                return null;
            }
    }

    public void FetchData(String u) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, u, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            movies = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);
                                String imageUrl = result.getString("poster_path");
                                String overview = result.getString("overview");
                                String title = result.getString("title");
                                String vote = result.getString("vote_average");
                                String release = result.getString("release_date");
                                String id = result.getString("id");
                                Movies movie = new Movies();
                                movie.setOverview(overview);
                                movie.setPosterUrl(imageUrl);
                                movie.setReleasedate(release);
                                movie.setTitle(title);
                                movie.setVoteavg(vote);
                                movie.setID(id);
                                movies.add(movie);
                            }
                                myAdapter = new MyAdapter(getActivity(), movies);
                                gridView.setAdapter(myAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
    public void getFavourite(){
        movies = new ArrayList<>();
        DataBase_Functions dbf = new DataBase_Functions(getContext());
        Cursor CR = dbf.getData(dbf);
        CR.moveToFirst();
        for(int i=0;i<CR.getCount();i++){
            Movies m = new Movies();
            m.setPosterUrl(CR.getString(0));
            m.setTitle(CR.getString(1));
            m.setOverview(CR.getString(2));
            m.setReleasedate(CR.getString(3));
            m.setVoteavg(CR.getString(4));
            m.setID(CR.getString(5));
            movies.add(m);
            CR.moveToNext();
        }
        myAdapter = new MyAdapter(getActivity(),movies);
        gridView.setAdapter(myAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.popular) {
            if(isOnline())
            FetchData(moviesurl);
            else
                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
        } else if (id == R.id.rated) {
            if(isOnline())
            FetchData("http://api.themoviedb.org/3/movie/top_rated?api_key=" + BuildConfig.Movies_API_KEY);
            else
                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }
        else if(id == R.id.fav){
            if(isOnline())
            getFavourite();
            else
                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
