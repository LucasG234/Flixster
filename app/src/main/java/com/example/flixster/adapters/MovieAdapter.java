package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context mContext;
    private List<Movie> mMovies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.mContext = context;
        this.mMovies = movies;
    }

    // Inflate a layout from XML and return a ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        ItemMovieBinding movieBinding = ItemMovieBinding.inflate(LayoutInflater.from(mContext));
        View movieView = movieBinding.getRoot();
        return new ViewHolder(movieView, movieBinding);
    }

    // Populate data into the view, through the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // Get the movie at the position
        Movie movie = mMovies.get(position);
        // Bind the movie data into the ViewHolder
        holder.bind(movie);
    }

    // Return the total count of the items in the list
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    // Each instance represents one item in the list
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleText;
        private TextView mOverviewText;
        private ImageView mPosterImage;

        public ViewHolder(@NonNull View itemView, ItemMovieBinding itemBinding) {
            super(itemView);
            mTitleText = itemBinding.tvTitle;
            mOverviewText = itemBinding.tvOverview;
            mPosterImage = itemBinding.ivPoster;

            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            mTitleText.setText(movie.getTitle());
            mOverviewText.setText(movie.getOverview());

            // Select image depending on orientation
            String url;
            int placeHolderId;
            if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                url = movie.getBackdropPath();
                placeHolderId = R.drawable.backdrop_placeholder;
            } else {
                url = movie.getPosterPath();
                placeHolderId = R.drawable.poster_placeholder;
            }
            RoundedCornersTransformation transformation = new RoundedCornersTransformation(30, 10);

            Glide.with(mContext)
                    .load(url)
                    .placeholder(placeHolderId)
                    .transform(transformation)
                    .into(mPosterImage);
        }

        @Override
        public void onClick(View view) {
            // Get position and ensure validity
            int position = getAdapterPosition();

            if(position != RecyclerView.NO_POSITION) {
                Movie movie = mMovies.get(position);

                // Create an intent for a MovieDetailsActivity
                Intent detailsIntent = new Intent(mContext, MovieDetailsActivity.class);
                // Serialize the movie using parceler, with its short name as the key
                detailsIntent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                mContext.startActivity(detailsIntent);
            }

        }
    }
}
