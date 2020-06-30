package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    // This is a key for the Flixster JSON response
    public static final String RESULTS_KEY = "results";
    public static final String API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=%s";
    public static final String TAG = "MainActivity";

    private List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        mMovies = new ArrayList<>();

        // Create an adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, mMovies);
        // Set the adapter on the RecyclerView
        rvMovies.setAdapter(movieAdapter);
        // Set a LayoutManager on the RecyclerView
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        // Create HTTP request to fill in the movie information
        AsyncHttpClient client = new AsyncHttpClient();
        String url = String.format(API_URL, getString(R.string.movies_api_key));
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray(RESULTS_KEY);
                    // Add movies to list from Flixster JSON call
                    mMovies.addAll(Movie.fromJSONArray(results));
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception", e);
                    e.printStackTrace();
                }

                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure", throwable);
            }
        });
    }
}