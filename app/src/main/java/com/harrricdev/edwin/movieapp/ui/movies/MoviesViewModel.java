package com.harrricdev.edwin.movieapp.ui.movies;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.harrricdev.edwin.movieapp.data.model.Movie;
import com.harrricdev.edwin.movieapp.data.repository.MoviesRepository;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by edwin on 5/6/17.
 */

public class MoviesViewModel extends BaseObservable {

    public final ObservableList<Movie> movies = new ObservableArrayList<>();

    public final ObservableBoolean moviesLoading = new ObservableBoolean(false);

    public final ObservableBoolean emptyViewShowing = new ObservableBoolean(false);

    public final ObservableBoolean errorViewShowing = new ObservableBoolean(false);

    public final ObservableField<String> errorString = new ObservableField<>();

    private final MoviesRepository mMoviesRepository;
    private final Context mContext;

    public MoviesViewModel(@NonNull Context context, @NonNull MoviesRepository moviesRepository){
        mContext = context;
        mMoviesRepository = moviesRepository;

    }



    public void start(String sort) {
        showMovies(movies.isEmpty(), sort);
    }

    public void showMovies(boolean showLoading, String sort) {
        moviesLoading.set(showLoading);
        errorViewShowing.set(false);
        emptyViewShowing.set(false);
        movies.clear();

        if(sort.equalsIgnoreCase("popular")){
            showPopular();
        }else{
            showTop();
        }


    }

    private void showTop() {

        mMoviesRepository.getTopRatedMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Movie>>() {
                    @Override
                    public void onNext(List<Movie> value) {
                        boolean isEmpty = value == null || value.isEmpty();

                        if(!isEmpty) {
                            movies.clear();
                            movies.addAll(value);
                        }
                        emptyViewShowing.set(isEmpty);

                    }

                    @Override
                    public void onError(Throwable e) {
                        errorViewShowing.set(true);
                        moviesLoading.set(false);
                        emptyViewShowing.set(false);

                        errorString.set(getErrorMessage(e));
                    }

                    @Override
                    public void onComplete() {
                        moviesLoading.set(false);
                        errorViewShowing.set(false);
                    }
                });

    }

    private void showPopular(){

        mMoviesRepository.getPopularMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Movie>>() {
                    @Override
                    public void onNext(List<Movie> value) {
                        boolean isEmpty = value == null || value.isEmpty();

                        if(!isEmpty) {
                            movies.clear();
                            movies.addAll(value);
                        }
                        emptyViewShowing.set(isEmpty);

                    }

                    @Override
                    public void onError(Throwable e) {
                        errorViewShowing.set(true);
                        moviesLoading.set(false);
                        emptyViewShowing.set(false);

                        errorString.set(getErrorMessage(e));
                    }

                    @Override
                    public void onComplete() {
                        moviesLoading.set(false);
                        errorViewShowing.set(false);
                    }
                });

    }

    private String getErrorMessage(Throwable e) {
        if(e instanceof HttpException) {
            return "Hey something seems wrong with the server";
        }
        if(e instanceof IOException) {
            return "Hey please check your connection or something";
        } else {
            return "well, we screwed up";
        }

    }



    private void setUpChangeCallbacks() {

    }


}
