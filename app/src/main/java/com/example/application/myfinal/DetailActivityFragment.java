package com.example.application.myfinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.application.myfinal.R.id.poster;



public class DetailActivityFragment extends Fragment {

    List<Movies> trailer = new ArrayList<>();
    List<Movies> review = new ArrayList<>();

    String PosterUrl , OverView , Title , VoteAvg , ReleaseDate , ID ;


    ImageView image;
    TextView title,releasedate,vote,overview;
    Button favorite;
    NonScrollList trailerlist , reviewlist;
    TrailersAdapter trailersAdapter;
    ReviewsAdapter reviewsAdapter ;


    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_detail, container, false);
        Bundle sentBundle = getArguments();
        image = (ImageView) rootview.findViewById(poster);
        title = (TextView) rootview.findViewById(R.id.title);
        overview = (TextView) rootview.findViewById(R.id.overview);
        releasedate = (TextView) rootview.findViewById(R.id.releasedate);
        vote = (TextView) rootview.findViewById(R.id.vote);
        favorite = (Button) rootview.findViewById(R.id.favor);
        trailerlist = (NonScrollList) rootview.findViewById(R.id.trailers);
        reviewlist = (NonScrollList) rootview.findViewById(R.id.reviews);
            PosterUrl = sentBundle.getString("PosterUrl");
            OverView = sentBundle.getString("OverView");
            Title = sentBundle.getString("Title");
            VoteAvg = sentBundle.getString("VoteAvg");
            ReleaseDate = sentBundle.getString("ReleaseDate");
            ID = sentBundle.getString("ID");
            ((TextView) rootview.findViewById(R.id.overview)).setText("OverView : \n"+OverView);
            ((TextView) rootview.findViewById(R.id.title)).setText("Title : "+Title);
            ((TextView) rootview.findViewById(R.id.releasedate)).setText("Release Date : "+ReleaseDate);
            ((TextView) rootview.findViewById(R.id.vote)).setText("Rate : "+VoteAvg);
            ((Button) rootview.findViewById(R.id.favor)).setText("MAKE AS FAVOURITE");
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+PosterUrl).into(image);
            FetchTrailers("http://api.themoviedb.org/3/movie/"+ID+"/trailers?api_key="+BuildConfig.Movies_API_KEY);
            FetchReviews("http://api.themoviedb.org/3/movie/"+ID+"/reviews?api_key="+BuildConfig.Movies_API_KEY);
            DataBase_Functions df = new DataBase_Functions(getContext());
            if(df.isMovieExist(df,ID)){
                favorite.setText("MAKE AS UNFAVOURITE");
            }
            else {
                favorite.setText("MAKE AS FAVOURITE");
            }
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(favorite.getText()=="MAKE AS FAVOURITE"){
                        favorite.setText("MAKE AS UNFAVOURITE");
                        DataBase_Functions df = new DataBase_Functions(getContext());
                        df.insertData(df,PosterUrl,Title,VoteAvg,OverView,ReleaseDate,ID);
                    }
                    else if(favorite.getText()=="MAKE AS UNFAVOURITE"){
                        favorite.setText("MAKE AS FAVOURITE");
                        DataBase_Functions df = new DataBase_Functions(getContext());
                        df.deleteData(df,PosterUrl);
                    }
                }
            });
            trailerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.youtube.com/watch?v="+trailer.get(position).getTrailerSrc())));
                }
            });
        return rootview;
    }

    public void FetchTrailers(String trailers){
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, trailers, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            trailer = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("youtube");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);
                                String Name = result.getString("name");
                                String Source = result.getString("source");
                                Movies movie = new Movies();
                                movie.setTrailerName(Name);
                                movie.setTrailerSrc(Source);
                                trailer.add(movie);
                            }
                            trailersAdapter = new TrailersAdapter(getActivity(),trailer);
                            trailerlist.setAdapter(trailersAdapter);
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
        AppController.getInstance().addToRequestQueue(jor);
    }
    public void FetchReviews (String reviews){
        JsonObjectRequest sbe = new JsonObjectRequest(Request.Method.GET, reviews, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            review = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);
                                String Author = result.getString("author");
                                String Content = result.getString("content");
                                Movies movie = new Movies();
                                movie.setReviewAuthor(Author);
                                movie.setReviewContent(Content);
                                review.add(movie);
                            }
                            reviewsAdapter = new ReviewsAdapter(getActivity(),review);
                            reviewlist.setAdapter(reviewsAdapter);
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
        AppController.getInstance().addToRequestQueue(sbe);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
