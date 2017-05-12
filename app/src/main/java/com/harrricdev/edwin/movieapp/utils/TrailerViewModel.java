package com.harrricdev.edwin.movieapp.utils;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.harrricdev.edwin.movieapp.data.model.Review;
import com.harrricdev.edwin.movieapp.data.model.Trailer;
import com.harrricdev.edwin.movieapp.ui.moviedetails.TrailerInteractor;

/**
 * Created by edwin on 5/11/17.
 */

public class TrailerViewModel extends BaseObservable {

    private TrailerInteractor trailerInteractor;

    public final ObservableField<Trailer> mReviewObservable = new ObservableField<>();

    public final ObservableField<String> name = new ObservableField<>();



    public TrailerViewModel(TrailerInteractor trailerInteractor){
        this.trailerInteractor = trailerInteractor;
        mReviewObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Trailer trailer = mReviewObservable.get();
                name.set(trailer.getName());

            }
        });
    }

    public void setTrailer(Trailer trailer){
        mReviewObservable.set(trailer);
    }

    @Nullable
    public Trailer getTrailer(){
        return mReviewObservable.get();
    }

    public void showTrailer(){
        Trailer trailer = getTrailer();

        if(trailer != null){
            trailerInteractor.showTrailer(trailer);
        }
    }

}
