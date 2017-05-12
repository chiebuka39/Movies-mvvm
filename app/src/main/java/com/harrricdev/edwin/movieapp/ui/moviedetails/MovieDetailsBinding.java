package com.harrricdev.edwin.movieapp.ui.moviedetails;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.harrricdev.edwin.movieapp.data.model.Movie;
import com.harrricdev.edwin.movieapp.data.model.Review;
import com.harrricdev.edwin.movieapp.data.model.Trailer;
import com.harrricdev.edwin.movieapp.ui.movies.MovieAdapter;

import java.util.List;

/**
 * Created by edwin on 5/6/17.
 */

public class MovieDetailsBinding {

    @BindingAdapter("bind:reviews")
    public static  void setReviews(RecyclerView recyclerView, List<Review> reviews) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        if (adapter != null && adapter instanceof ReviewAdapter ) {
            ((ReviewAdapter) adapter).setReviews(reviews);
        }else {
            throw new IllegalStateException("RecyclerView either has no adapter set or the adapter isnt of type MovieAdapter");
        }

    }

    @BindingAdapter("bind:trailers")
    public static  void setTrailers(RecyclerView recyclerView, List<Trailer> trailers) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        if (adapter != null && adapter instanceof TrailerAdapter ) {
            ((TrailerAdapter) adapter).setTrailers(trailers);
        }else {
            throw new IllegalStateException("RecyclerView either has no adapter set or the adapter isnt of type MovieAdapter");
        }

    }

}
