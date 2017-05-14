package com.harrricdev.edwin.movieapp.ui.movies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;


import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.harrricdev.edwin.movieapp.R;
import com.harrricdev.edwin.movieapp.data.model.Movie;
import com.harrricdev.edwin.movieapp.data.remote.api.MovieApiService;
import com.harrricdev.edwin.movieapp.data.repository.MovieRemoteRepository;

import com.harrricdev.edwin.movieapp.databinding.MoviesBinding;
import com.harrricdev.edwin.movieapp.ui.base.BaseActivity;
import com.harrricdev.edwin.movieapp.ui.moviedetails.DetailActivity;

public class MainActivity extends BaseActivity implements Interactor {


    private MovieAdapter mAdapter;
    private MoviesViewModel mMoviesViewModel;
    private MovieRemoteRepository mMoviesRepository;
    private String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MoviesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setUpToolbar(binding);

        setupViewModels(binding);
        //setupToolbar();
        setupRecyclerView(binding);

        selected = "popular";

        mMoviesViewModel.start(selected);


        /*if (savedInstanceState == null) {
            Fragment fragment = MoviesFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, fragment)
                    .commit();
        }*/
    }

    private void setUpToolbar(MoviesBinding binding) {
        setSupportActionBar(binding.toolbar);
    }

    private void setupViewModels(MoviesBinding binding) {
        mMoviesRepository = new
                MovieRemoteRepository(MovieApiService.Creator.create());

        mMoviesViewModel = new MoviesViewModel(this, mMoviesRepository);
        binding.setMoviesViewModel(mMoviesViewModel);

    }

    private void setupRecyclerView(MoviesBinding binding) {
        mAdapter = new MovieAdapter(mMoviesRepository, this);

        binding.movies.setLayoutManager(new GridLayoutManager(this, 2));
        binding.movies.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_popular) {
            if(!selected.equalsIgnoreCase("popular")){
                selected = "popular";
                mMoviesViewModel.showMovies(true,selected);
            }
            return true;
        }

        if (id == R.id.action_rate) {
            if(!selected.equalsIgnoreCase("rate")){
                selected = "rate";
                mMoviesViewModel.showMovies(true,selected);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMovieDetails(Movie movie) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("MOVIE_ID", movie.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}


