package com.harrricdev.edwin.movieapp.ui.movies;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.harrricdev.edwin.movieapp.R;
import com.harrricdev.edwin.movieapp.data.model.Movie;
import com.harrricdev.edwin.movieapp.data.repository.MoviesRepository;
import com.harrricdev.edwin.movieapp.databinding.MovieItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwin on 5/6/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private List<Movie> movies;
    private Interactor interactor;
    private MoviesRepository moviesRepository;

    public MovieAdapter(MoviesRepository moviesRepository, Interactor interactor) {
        this.interactor = interactor;
        this.moviesRepository = moviesRepository;
        movies = new ArrayList<>();
    }

    public void setMovies(@NonNull List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieItem movieItem = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_movie_layout, parent, false);
        return new MovieViewHolder(movieItem);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final Movie movie = movies.get(position);

        final MovieItemViewModel movieItemViewModel = new MovieItemViewModel(moviesRepository,
                interactor);
        movieItemViewModel.setMovie(movie);
        holder.setMovieItemViewModel(movieItemViewModel);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
