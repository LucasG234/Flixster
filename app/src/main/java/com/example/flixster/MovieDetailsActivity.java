package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MovieDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView detailsTitle = binding.tvTitle;
        TextView detailsOverview = binding.tvOverview;
        RatingBar detailsRatingBar = binding.rbVoteAverage;
        ImageView videoPreview = binding.ivPoster;

        // Unwrap the movie passed in by the Intent
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d(TAG, String.format("Showing details for '%s'", movie.getTitle()));

        // Set the Views
        detailsTitle.setText(movie.getTitle());
        detailsOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        detailsRatingBar.setRating(voteAverage > 0 ? voteAverage / 2 : 0);
        initializeVideoPreview(videoPreview, movie);
    }

    private void initializeVideoPreview(ImageView videoPreview, Movie movie) {
        // Set the preview image with no transformation
        Glide.with(this)
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.backdrop_placeholder)
                .into(videoPreview);

        // Make the API call to find the trailer URL
        String apiUrl = String.format(getString(R.string.movies_videos_url),
                movie.getMovieId(), getString(R.string.movies_api_key));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(apiUrl, new MovieJsonHttpResponseHandler(movie));

        // Add a listener to launch the trailer
        videoPreview.setOnClickListener(new MovieClickListener(movie));
    }

    private class MovieClickListener implements View.OnClickListener {

        private Movie mMovie;

        public MovieClickListener(Movie movie) {
            this.mMovie = movie;
        }

        @Override
        public void onClick(View view) {
            if(mMovie.getTrailerId() != null) {
                Context context = view.getContext();
                Intent trailerIntent = new Intent(context, MovieTrailerActivity.class);
                trailerIntent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(mMovie));

                context.startActivity(trailerIntent);
            }
            else {
                Log.d(TAG, "No trailer available");
            }
        }
    }

    private class MovieJsonHttpResponseHandler extends JsonHttpResponseHandler{

        private Movie mMovie;

        public MovieJsonHttpResponseHandler(Movie movie) {
            this.mMovie = movie;
        }

        @Override
        public void onSuccess(int statusCode, Headers headers, JSON json) {
            Log.d(TAG, "onSuccess");
            JSONObject jsonObject = json.jsonObject;
            try {
                JSONArray results = jsonObject.getJSONArray("results");
                if(results.length() > 0) {
                    JSONObject video = results.getJSONObject(0);
                    String trailerId = video.getString("key");
                    mMovie.setTrailerId(trailerId);
                    Log.d(TAG, trailerId);
                }
            } catch (JSONException e) {
                Log.e(TAG, "JSON Exception", e);
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
            Log.d(TAG, "onFailure");
        }
    }
}