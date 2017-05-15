package com.harrricdev.edwin.movieapp.ui.movies.fav;


import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harrricdev.edwin.movieapp.R;
import com.harrricdev.edwin.movieapp.data.db.MoviesContract;
import com.harrricdev.edwin.movieapp.databinding.FragmentFavouriteBinding;
import com.harrricdev.edwin.movieapp.ui.movies.MovieAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private FragmentFavouriteBinding binding;
    //com.harrricdev.edwin.movieapp.databinding.FavouritesListBinding
    private FavouritesListViewModel viewModel;

    private FavouritesAdapter mAdapter;

    private static final int TASK_LOADER_ID = 0;

    private Cursor mCursor = null;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        Fragment frag = new FavouriteFragment();
        ;
        return frag;

    }

    @Override
    public void onResume() {
        super.onResume();
        //viewModel = new FavouritesListViewModel(getContext());
       getActivity().getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new FavouritesListViewModel(getContext());
        getActivity().getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewModels();
        //setupToolbar();
        setupRecyclerView();

        viewModel.start(mCursor);
    }

    private void setupRecyclerView() {
        mAdapter = new FavouritesAdapter(getActivity());

        binding.movies.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.movies.setAdapter(mAdapter);
    }

    private void setupViewModels(){

        //viewModel = new FavouritesListViewModel(getContext());
        binding.setFavouritesViewModel(viewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false);

        return binding.getRoot();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(getActivity()) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mTaskData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data

                try {
                    return getActivity().getContentResolver().query(MoviesContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    Log.e("HARRY", "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursor = data;
        viewModel.setCursor(data);
        Log.v("HARRY", data.getCount()+"");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        viewModel.setCursor(null);
    }


}
