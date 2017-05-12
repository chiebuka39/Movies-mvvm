package com.harrricdev.edwin.movieapp.data.repository;

import com.harrricdev.edwin.movieapp.data.model.Movie;
import com.harrricdev.edwin.movieapp.data.model.MovieList;
import com.harrricdev.edwin.movieapp.data.model.Review;
import com.harrricdev.edwin.movieapp.data.model.Reviews;
import com.harrricdev.edwin.movieapp.data.model.Trailer;
import com.harrricdev.edwin.movieapp.data.model.Trailers;
import com.harrricdev.edwin.movieapp.data.remote.api.ApiUtils;
import com.harrricdev.edwin.movieapp.data.remote.api.MovieApiService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by edwin on 5/6/17.
 */

public class MovieRemoteRepository implements MoviesRepository {

    private MovieApiService apiService;

    public MovieRemoteRepository(MovieApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<List<Movie>> getPopularMovies() {
        Observable<MovieList> movieList =
                apiService.getPopularMovies(ApiUtils.getApiKey());

        return movieList.flatMap(new Function<MovieList, ObservableSource<? extends  List<Movie>>>() {
            @Override
            public ObservableSource<? extends  List<Movie>> apply(MovieList movieList) throws Exception {
                return Observable.just(movieList.getResults());
            }
        });
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovies() {
        Observable<MovieList> movieList =
                apiService.getTopRatedMovies(ApiUtils.getApiKey());

        return movieList.flatMap(new Function<MovieList, ObservableSource<? extends  List<Movie>>>() {
            @Override
            public ObservableSource<? extends  List<Movie>> apply(MovieList movieList) throws Exception {
                return Observable.just(movieList.getResults());
            }
        });
    }



    @Override
    public Observable<Movie> getMovieDetails(long movieId) {
        return apiService.getMovieDetails(movieId, ApiUtils.getApiKey());
    }

    @Override
    public Observable<List<Review>> getReviews(long movieId) {
        Observable<Reviews> reviews =
                apiService.getReviews(movieId,ApiUtils.getApiKey());

        return reviews.flatMap(new Function<Reviews, ObservableSource<? extends List<Review>>>() {
            @Override
            public ObservableSource<? extends List<Review>> apply(Reviews reviews) throws Exception {
                return Observable.just(reviews.getResults());
            }
        });
    }

    @Override
    public Observable<List<Trailer>> getTrailers(long movieId) {
        Observable<Trailers> trailers =
                apiService.getTrailers(movieId,ApiUtils.getApiKey());

        return trailers.flatMap(new Function<Trailers, ObservableSource<? extends List<Trailer>>>() {
            @Override
            public ObservableSource<? extends List<Trailer>> apply(Trailers trailers) throws Exception {
                return Observable.just(trailers.getResults());
            }
        });
    }
}
