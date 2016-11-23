package com.example.application.myfinal;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public List<Movies> movies = new ArrayList<>();

    public String url = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+BuildConfig.Movies_API_KEY;
    GridView gridView ;
    MyAdapter myAdapter ;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootview.findViewById(R.id.movies_gv);
        myAdapter = new MyAdapter(getActivity(),movies);
        gridView.setAdapter(myAdapter);
        FetchData(url);
        return rootview;
    }
    public void FetchData(String u){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, u, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject result = jsonArray.getJSONObject(i);
                                String imageUrl = result .getString("poster_path");
                                String overview = result .getString("overview");
                                String title = result .getString("title");
                                String vote = result .getString("vote_average");
                                String release = result .getString("release_date");
                                Movies movie = new Movies();
                                movie.setOverview(overview);
                                movie.setPosterUrl(imageUrl);
                                movie.setReleasedate(release);
                                movie.setTitle(title);
                                movie.setVoteavg(vote);
                                movies.add(movie);
                            }
                            myAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
