package com.example.flixster.models;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    // These fields are JSON keys
    public static final String POSTER_PATH_KEY = "poster_path";
    public static final String BACKDROP_PATH_KEY = "backdrop_path";
    public static final String TITLE_KEY = "title";
    public static final String OVERVIEW_KEY = "overview";
    public static final String VOTE_AVERAGE_KEY = "vote_average";
    public static final String ID_KEY = "id";

    // Fields cannot be private to be captured by Parceler
    String posterPath;
    String backdropPath;
    String title;
    String overview;
    Double voteAverage;
    Integer movieId;
    // trailerId is loaded as needed when movie details are generated
    @Nullable
    String trailerId;

    // Default constructor needed for Parceler
    public Movie() {
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString(POSTER_PATH_KEY);
        backdropPath = jsonObject.getString(BACKDROP_PATH_KEY);
        title = jsonObject.getString(TITLE_KEY);
        overview = jsonObject.getString(OVERVIEW_KEY);
        voteAverage = jsonObject.getDouble(VOTE_AVERAGE_KEY);
        movieId = jsonObject.getInt(ID_KEY);
    }

    public static List<Movie> fromJSONArray(JSONArray jsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            movies.add(new Movie(jsonArray.getJSONObject(i)));
        }

        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setTrailerId(@Nullable String trailerId) {
        this.trailerId = trailerId;
    }

    @Nullable
    public String getTrailerId() {
        return trailerId;
    }
}
