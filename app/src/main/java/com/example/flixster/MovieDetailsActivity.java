package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie mMovie;

    private TextView mDetailsTitle;
    private TextView mDetailsOverview;
    private RatingBar mDetailsRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mDetailsTitle = findViewById(R.id.tvTitle);
        mDetailsOverview = findViewById(R.id.tvOverview);
        mDetailsRatingBar = findViewById(R.id.rbVoteAverage);

        // Unwrap the movie passed in by the Intent
        mMovie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", mMovie.getTitle()));

        // Set the Views
        mDetailsTitle.setText(mMovie.getTitle());
        mDetailsOverview.setText(mMovie.getOverview());
        float voteAverage = mMovie.getVoteAverage().floatValue();
        mDetailsRatingBar.setRating(voteAverage > 0 ? voteAverage / 2 : 0);
    }
}