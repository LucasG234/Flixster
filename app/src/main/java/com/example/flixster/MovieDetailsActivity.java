package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView detailsTitle = binding.tvTitle;
        TextView detailsOverview = binding.tvOverview;
        RatingBar detailsRatingBar = binding.rbVoteAverage;

        // Unwrap the movie passed in by the Intent
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // Set the Views
        detailsTitle.setText(movie.getTitle());
        detailsOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        detailsRatingBar.setRating(voteAverage > 0 ? voteAverage / 2 : 0);
    }
}