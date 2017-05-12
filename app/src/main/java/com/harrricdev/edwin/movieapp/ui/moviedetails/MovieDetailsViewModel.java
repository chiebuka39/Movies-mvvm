package com.harrricdev.edwin.movieapp.ui.moviedetails;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import com.harrricdev.edwin.movieapp.data.model.Movie;
import com.harrricdev.edwin.movieapp.data.model.Review;
import com.harrricdev.edwin.movieapp.data.model.Trailer;
import com.harrricdev.edwin.movieapp.data.repository.MoviesRepository;
import com.harrricdev.edwin.movieapp.utils.MovieViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by edwin on 5/11/17.
 */

public class MovieDetailsViewModel extends MovieViewModel {


    public final ObservableBoolean isMovieLoading = new ObservableBoolean();


    public final ObservableBoolean isLoading = new ObservableBoolean(true);

    public final ObservableBoolean errorViewShowing = new ObservableBoolean(true);
    public final ObservableField<String> errorString = new ObservableField<>();

    public final ObservableList<Review> reviews = new ObservableArrayList<>();
    public final ObservableBoolean isReviewLoading = new ObservableBoolean(true);
    public final ObservableBoolean emptyReviewViewShowing = new ObservableBoolean(false);


    public final ObservableList<Trailer> trailers = new ObservableArrayList<>();
    public final ObservableBoolean isTrailerLoading = new ObservableBoolean(true);
    public final ObservableBoolean emptyTrailerViewShowing = new ObservableBoolean(false);



    private final MoviesRepository mMoviesRepository;

    public MovieDetailsViewModel(MoviesRepository moviesRepository) {
        super(moviesRepository);
        mMoviesRepository = moviesRepository;
        setUpChangeCallbacks();
    }



    public void getMovieDetails(long movieId) {
        isMovieLoading.set(true);
        errorViewShowing.set(false);
        mMoviesRepository.getMovieDetails(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Movie>() {
                    @Override
                    public void onNext(Movie value) {
                        setMovie(value);

                    }

                    @Override
                    public void onError(Throwable e) {
                        errorViewShowing.set(true);
                        isLoading.set(false);
                    }

                    @Override
                    public void onComplete() {
                        isMovieLoading.set(false);
                        errorViewShowing.set(false);
                        //Log.v("DETAILS_1", "set to fales")
                        boolean loading = isMovieLoading.get() || isReviewLoading.get() || isTrailerLoading.get(); //|| isTrailerLoading;
                        isLoading.set(loading);
                    }
                });


        mMoviesRepository.getReviews(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Review>>() {
                    @Override
                    public void onNext(List<Review> value) {
                        boolean isEmpty = value == null || value.isEmpty();

                        if(!isEmpty) {
                            reviews.clear();
                            reviews.addAll(value);
                        }
                        emptyReviewViewShowing.set(isEmpty);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorViewShowing.set(true);
                        isLoading.set(false);
                    }

                    @Override
                    public void onComplete() {
                        isReviewLoading.set(false);
                        errorViewShowing.set(false);
                        boolean loading = isMovieLoading.get() || isReviewLoading.get() || isTrailerLoading.get(); //|| isTrailerLoading;
                        isLoading.set(loading);
                    }
                });


        mMoviesRepository.getTrailers(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Trailer>>() {
                    @Override
                    public void onNext(List<Trailer> value) {
                        boolean isEmpty = value == null || value.isEmpty();

                        if(!isEmpty) {
                            trailers.clear();
                            trailers.addAll(value);
                        }
                        emptyTrailerViewShowing.set(isEmpty);
                        //isTrailerLoading.set(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        errorViewShowing.set(true);
                        isLoading.set(false);
                    }

                    @Override
                    public void onComplete() {
                        isTrailerLoading.set(false);
                        errorViewShowing.set(false);
                        boolean loading = isMovieLoading.get() || isReviewLoading.get() || isTrailerLoading.get(); //|| isTrailerLoading;
                        isLoading.set(loading);
                    }
                });


    }

    private void setUpChangeCallbacks() {

        setUpReviews();
        setUpTrailers();
    }

    private void setUpReviews() {
        //reviews.add

    }

    private void setUpTrailers() {

    }


}
