package com.harrricdev.edwin.movieapp.data.repository;

import com.harrricdev.edwin.movieapp.data.model.Movie;
import com.harrricdev.edwin.movieapp.data.model.Review;
import com.harrricdev.edwin.movieapp.data.model.Trailer;

;import java.util.List;

import io.reactivex.Observable;

/**
 * Created by edwin on 5/6/17.
 */

public interface MoviesRepository {


    Observable<List<Movie>> getPopularMovies();

    Observable<List<Movie>> getTopRatedMovies();

    Observable<Movie> getMovieDetails(long movieId);

    Observable<List<Review>> getReviews(long movieId);

    Observable<List<Trailer>> getTrailers(long movieId);



}
