package com.harrricdev.edwin.movieapp.data.remote.api;

import com.harrricdev.edwin.movieapp.data.model.Movie;
import com.harrricdev.edwin.movieapp.data.model.MovieList;
import com.harrricdev.edwin.movieapp.data.model.Reviews;
import com.harrricdev.edwin.movieapp.data.model.Trailers;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by edwin on 5/6/17.
 */

public interface MovieApiService {

    @GET("movie/popular")
    Observable<MovieList> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top-rated")
    Observable<MovieList> getTopRatedMovies(@Query("api_key") String apiKey);

    /**
     * Get movie details
     *
     * @param movieId - movie id
     * @param apiKey - API key
     */
    @GET("movie/{movie_id}")
    Observable<Movie> getMovieDetails(@Path("movie_id") long movieId,
                                      @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Observable<Reviews> getReviews(@Path("movie_id") long movieId,
                                   @Query("api_key") String apikey);

    @GET("movie/{movie_id}/videos")
    Observable<Trailers> getTrailers(@Path("movie_id") long movieId,
                                     @Query("api_key") String apikey);

    class Creator {

        public static MovieApiService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ApiUtils.getBaseUrl())
                    .build();
            return retrofit.create(MovieApiService.class);
        }
    }
}
