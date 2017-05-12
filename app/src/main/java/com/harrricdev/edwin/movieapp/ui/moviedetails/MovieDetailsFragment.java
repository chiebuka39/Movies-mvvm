package com.harrricdev.edwin.movieapp.ui.moviedetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harrricdev.edwin.movieapp.R;
import com.harrricdev.edwin.movieapp.data.model.Trailer;
import com.harrricdev.edwin.movieapp.data.remote.api.MovieApiService;
import com.harrricdev.edwin.movieapp.data.repository.MovieRemoteRepository;

import com.harrricdev.edwin.movieapp.databinding.MovieDetailsBinding;
import com.harrricdev.edwin.movieapp.ui.base.BaseFragment;
import com.harrricdev.edwin.movieapp.ui.movies.MovieAdapter;

/**
 * Created by edwin on 5/11/17.
 */

public class MovieDetailsFragment extends BaseFragment implements TrailerInteractor {

    private static final String ARG_MOVIE_ID = "MovieDetailsFragment.MOVIE_ID";

    private MovieDetailsBinding mBinding;
    ReviewAdapter reviewAdapter;
    TrailerAdapter trailerAdapter;
    private long mMovieId;
    private MovieDetailsViewModel mViewModel;
    MovieRemoteRepository movieRemoteRepository;

    /**
     * Convenience method for creating an instance of the {@link MovieDetailsFragment}
     *
     * @param movieId - movie id
     * @return the instance of the fragment
     */
    public static Fragment newInstance(long movieId) {
        Fragment frag = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_MOVIE_ID, movieId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrieveMovieId(savedInstanceState);

        setupToolbar();

        setupViewModel();

        setupRecyclerView();
    }

    private void setupToolbar() {
        mBinding.toolbar.setNavigationIcon(R.drawable.nav_back);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void setupRecyclerView() {
        reviewAdapter = new ReviewAdapter(movieRemoteRepository);

        mBinding.reviews.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.reviews.setAdapter(reviewAdapter);

        trailerAdapter = new TrailerAdapter(this);

        mBinding.trailers.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.trailers.setAdapter(trailerAdapter);
    }

    private void setupViewModel() {
        movieRemoteRepository = new
                MovieRemoteRepository(MovieApiService.Creator.create());

        mViewModel = new MovieDetailsViewModel(movieRemoteRepository);
        mBinding.setViewModel(mViewModel);
        mViewModel.getMovieDetails(mMovieId);
    }

    private void retrieveMovieId(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mMovieId = getArguments() != null ? getArguments().getLong(ARG_MOVIE_ID, -1) : -1;
        } else {
            mMovieId = savedInstanceState.getLong(ARG_MOVIE_ID, -1);
        }
        if (mMovieId == -1) {
            throw new IllegalArgumentException("You either passed a wrong value of movie id,"
                    + " or you did not use the newInstance convenience method");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(ARG_MOVIE_ID, mMovieId);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void showTrailer(Trailer trailer) {
        String url = "https://www.youtube.com/watch?v="+ trailer.getKey();
        Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(url));
        getActivity().startActivity(intent);
    }
}
