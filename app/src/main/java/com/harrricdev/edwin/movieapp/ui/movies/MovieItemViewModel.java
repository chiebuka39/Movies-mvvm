package com.harrricdev.edwin.movieapp.ui.movies;

import com.harrricdev.edwin.movieapp.data.model.Movie;
import com.harrricdev.edwin.movieapp.data.repository.MoviesRepository;
import com.harrricdev.edwin.movieapp.utils.MovieViewModel;

/**
 * Created by edwin on 5/6/17.
 */

public class MovieItemViewModel extends MovieViewModel {

    private Interactor interactor;

    public MovieItemViewModel(MoviesRepository moviesRepository, Interactor interactor) {
        super(moviesRepository);
        this.interactor = interactor;
    }

    public void clickMovieItem() {
        Movie movie = getMovie();
        if(movie != null){
            interactor.showMovieDetails(movie);
        }
    }
}
