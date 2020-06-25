package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String mPosterPath;
    private String mBackdropPath;
    private String mTitle;
    private String mOverview;

    public Movie(JSONObject jsonObject) throws JSONException {
        mPosterPath = jsonObject.getString("poster_path");
        mBackdropPath = jsonObject.getString("backdrop_path");
        mTitle = jsonObject.getString("title");
        mOverview = jsonObject.getString("overview");
    }

    public static List<Movie> fromJSONArray(JSONArray jsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            movies.add(new Movie(jsonArray.getJSONObject(i)));
        }

        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", mPosterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", mBackdropPath);
    }

    public String getTitle() {
        return mTitle;
    }

    public String getOverview() {
        return mOverview;
    }
}
