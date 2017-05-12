package com.harrricdev.edwin.movieapp.ui.movies;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.harrricdev.edwin.movieapp.data.model.Movie;

import java.util.List;

/**
 * Created by edwin on 5/6/17.
 */

public class MovieListBinding {

    @BindingAdapter("bind:movies")
    public static  void setMovies(RecyclerView recyclerView, List<Movie> movies) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        if (adapter != null && adapter instanceof MovieAdapter ) {
            ((MovieAdapter) adapter).setMovies(movies);
        }else {
            throw new IllegalStateException("RecyclerView either has no adapter set or the adapter isnt of type MovieAdapter");
        }

    }

}
