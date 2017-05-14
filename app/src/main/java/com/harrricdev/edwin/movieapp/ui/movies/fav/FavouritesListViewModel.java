package com.harrricdev.edwin.movieapp.ui.movies.fav;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.harrricdev.edwin.movieapp.data.db.FavouriteMovie;
import com.harrricdev.edwin.movieapp.data.db.MoviesContract;

/**
 * Created by edwin on 5/14/17.
 */

public class FavouritesListViewModel extends BaseObservable  {

    public final ObservableList<FavouriteMovie> favouritesMovies = new ObservableArrayList<>();

    public final ObservableField<Cursor> moviesCursor = new ObservableField<>();

    public final ObservableBoolean emptyViewShowing = new ObservableBoolean(false);

    private final Context mContext;



    public FavouritesListViewModel(@NonNull Context context){
        mContext = context;
    }

    public void start(Cursor cursor){
        moviesCursor.set(cursor);
    }

    public void setCursor(Cursor cursor){
        moviesCursor.set(cursor);
    }

    public Cursor getCursor(){
        return moviesCursor.get();
    }


}
