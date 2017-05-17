package com.harrricdev.edwin.movieapp.ui.movies;

import com.harrricdev.edwin.movieapp.R;
import com.harrricdev.edwin.movieapp.data.model.Movie;
import com.harrricdev.edwin.movieapp.data.remote.api.MovieApiService;
import com.harrricdev.edwin.movieapp.data.repository.MovieRemoteRepository;
import com.harrricdev.edwin.movieapp.ui.base.BaseFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.harrricdev.edwin.movieapp.databinding.MovieListBinding;
import com.harrricdev.edwin.movieapp.ui.moviedetails.DetailActivity;
import com.harrricdev.edwin.movieapp.utils.AnimUtils;

/**
 * Created by edwin on 5/6/17.
 */

public class MoviesFragment extends BaseFragment implements Interactor {

    private static final String ARG_SORT = "MOVIEFRAGMENT.MOVIE_SORT";
    private static final String TAG = MoviesFragment.class.getName();
    private MovieListBinding binding;

    private MovieAdapter mAdapter;
    private MoviesViewModel mMoviesViewModel;
    private MovieRemoteRepository mMoviesRepository;
    private String mSortKey;
    GridLayoutManager layoutManager;
    Activity activity;

    Toolbar toolbar;

    public static Fragment newInstance(String sortKey) {
        Fragment frag = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SORT, sortKey);
        frag.setArguments(args);
        return frag;

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

        retrieveSortKey(savedInstanceState);

        setupViewModels();
        setupToolbar();
        if(savedInstanceState == null){
            animateToolbar();
        }

        setupRecyclerView();

        toolbar =(Toolbar) activity.findViewById(R.id.mainToolbar);



        mMoviesViewModel.start(mSortKey.toLowerCase());
    }

    private void retrieveSortKey(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mSortKey = getArguments() != null ? getArguments().getString(ARG_SORT, "") : "";
        } else {
            mSortKey = savedInstanceState.getString(ARG_SORT, "");
        }
        if (mSortKey == "") {
            throw new IllegalArgumentException("You either passed a wrong value of sort value,"
                    + " or you did not use the newInstance convenience method");
        }
    }

    private void setupToolbar() {
        binding.mainToolbar.setTitle(R.string.app_name);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(ARG_SORT, mSortKey);
    }

    private void setupViewModels() {
        mMoviesRepository = new
                MovieRemoteRepository(MovieApiService.Creator.create());

        mMoviesViewModel = new MoviesViewModel(getContext(), mMoviesRepository);
        binding.setMoviesViewModel(mMoviesViewModel);

    }

    private void setupRecyclerView() {
        mAdapter = new MovieAdapter(mMoviesRepository, this);
        layoutManager = new GridLayoutManager(getContext(),2);
        binding.movies.setLayoutManager(layoutManager);
        binding.movies.addOnScrollListener(toolbarElevation);
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

    private RecyclerView.OnScrollListener toolbarElevation = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            if( newState == RecyclerView.SCROLL_STATE_IDLE
                    && layoutManager.findFirstVisibleItemPosition() == 0
                    && layoutManager.findViewByPosition(0).getTop() == binding.movies.getPaddingTop()
                    && binding.mainToolbar.getTranslationZ() != 0) {

                binding.mainToolbar.setTranslationZ(0f);

                Log.v("HARRY6", "done2");
            }else if( newState == RecyclerView.SCROLL_STATE_DRAGGING &&
                    binding.mainToolbar.getTranslationZ() != -1f) {
                binding.mainToolbar.setTranslationZ(-1f);

            }


        }

    };


    private void animateToolbar(){
        View t = binding.mainToolbar.getChildAt(0);
        if( t != null && t instanceof TextView){
            TextView title = (TextView) t;

            title.setAlpha(0f);
            title.setScaleX(0.8f);

            title.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(300)
                    .setDuration(900)
                    .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(getActivity()));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }
}
