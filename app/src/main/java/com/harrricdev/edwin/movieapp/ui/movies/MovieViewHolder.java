package com.harrricdev.edwin.movieapp.ui.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.harrricdev.edwin.movieapp.databinding.MovieItem;

/**
 * Created by edwin on 5/6/17.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {

    private MovieItem movieItem;

    public MovieViewHolder(MovieItem movieItem) {
        super(movieItem.getRoot());
        this.movieItem = movieItem;
    }

    public void setMovieItemViewModel(@NonNull MovieItemViewModel movieItemViewModel) {
        movieItem.setMovieItemViewModel(movieItemViewModel);
        movieItem.executePendingBindings();
    }
}
