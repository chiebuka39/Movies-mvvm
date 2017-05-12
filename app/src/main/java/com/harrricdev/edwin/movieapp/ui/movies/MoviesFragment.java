package com.harrricdev.edwin.movieapp.ui.movies;

import com.harrricdev.edwin.movieapp.R;
import com.harrricdev.edwin.movieapp.data.model.Movie;
import com.harrricdev.edwin.movieapp.data.remote.api.MovieApiService;
import com.harrricdev.edwin.movieapp.data.repository.MovieRemoteRepository;
import com.harrricdev.edwin.movieapp.ui.base.BaseFragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.harrricdev.edwin.movieapp.databinding.MovieListBinding;
import com.harrricdev.edwin.movieapp.ui.moviedetails.DetailActivity;
import com.harrricdev.edwin.movieapp.ui.moviedetails.MovieDetailsFragment;

/**
 * Created by edwin on 5/6/17.
 */

public class MoviesFragment extends BaseFragment implements Interactor {

    private static final String TAG = MoviesFragment.class.getName();
    private MovieListBinding binding;

    private MovieAdapter mAdapter;
    private MoviesViewModel mMoviesViewModel;
    private MovieRemoteRepository mMoviesRepository;

    public static Fragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewModels();
        //setupToolbar();
        setupRecyclerView();

        mMoviesViewModel.start();
    }

    private void setupToolbar() {
        //binding.toolbar.setTitle(R.string.app_name);
    }

    private void setupViewModels() {
        mMoviesRepository = new
                MovieRemoteRepository(MovieApiService.Creator.create());

        mMoviesViewModel = new MoviesViewModel(getContext(), mMoviesRepository);
        binding.setMoviesViewModel(mMoviesViewModel);

    }

    private void setupRecyclerView() {
        mAdapter = new MovieAdapter(mMoviesRepository, this);

        binding.movies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.movies.setAdapter(mAdapter);
    }

    @Override
    public void showMovieDetails(Movie movie) {
        ///Toast.makeText(getContext(), movie.toString(), Toast.LENGTH_SHORT).show();

        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, MovieDetailsFragment.newInstance(movie.getId()));
        ft.addToBackStack(null);
        ft.commit();*/
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("MOVIE_ID", movie.getId());
        intent.putExtras(bundle);
        getActivity().startActivity(intent);

        //Toast.makeText(getActivity().getApplicationContext(), movie.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
