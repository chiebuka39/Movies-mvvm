package com.harrricdev.edwin.movieapp.ui.movies.fav;

import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.harrricdev.edwin.movieapp.data.db.FavouriteMovie;

import java.util.List;

/**
 * Created by edwin on 5/14/17.
 */

public class FavouritesListBinding {

    @BindingAdapter("bind:fav")
    public static  void setMovies(RecyclerView recyclerView, Cursor cursor) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        if (adapter != null && adapter instanceof FavouritesAdapter ) {
            ((FavouritesAdapter) adapter).setCursor(cursor);
        }else {
            throw new IllegalStateException("RecyclerView either has no adapter set or the adapter isnt of type MovieAdapter");
        }

    }
}
