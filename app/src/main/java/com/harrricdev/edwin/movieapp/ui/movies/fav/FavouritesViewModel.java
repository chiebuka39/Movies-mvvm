package com.harrricdev.edwin.movieapp.ui.movies.fav;

import android.databinding.ObservableField;

import com.harrricdev.edwin.movieapp.data.db.FavouriteMovie;

import io.reactivex.Observable;

/**
 * Created by edwin on 5/13/17.
 */

public class FavouritesViewModel {

    public final ObservableField<FavouriteMovie> mFavouriteObservable = new ObservableField<>();

    public final ObservableField<String> title = new ObservableField<>();

    public FavouritesViewModel(){
        mFavouriteObservable.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable observable, int i) {
                FavouriteMovie favouriteMovie = mFavouriteObservable.get();

                title.set(favouriteMovie.getTitle());
            }
        });
    }

    public void setFavouriteMovie(FavouriteMovie movie){
        mFavouriteObservable.set(movie);
    }

    public FavouriteMovie getFavouriteMovie(){
        return mFavouriteObservable.get();
    }
}
