package com.harrricdev.edwin.movieapp.ui.moviedetails;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.harrricdev.edwin.movieapp.R;
import com.harrricdev.edwin.movieapp.data.db.MoviesContract;
import com.harrricdev.edwin.movieapp.data.model.Trailer;
import com.harrricdev.edwin.movieapp.data.remote.api.MovieApiService;
import com.harrricdev.edwin.movieapp.data.repository.MovieRemoteRepository;

import com.harrricdev.edwin.movieapp.databinding.MovieDetailsBinding;
import com.harrricdev.edwin.movieapp.ui.base.BaseFragment;
import com.harrricdev.edwin.movieapp.utils.GlideUtils;
import com.harrricdev.edwin.movieapp.utils.ImageSaver;
import com.harrricdev.edwin.movieapp.utils.OfflineImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by edwin on 5/11/17.
 */

public class MovieDetailsFragment extends BaseFragment implements TrailerInteractor,
        DetailsInteractor, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String ARG_MOVIE_ID = "MovieDetailsFragment.MOVIE_ID";

    private static final int TASK_LOADER_ID = 0;

    private int theMovieId = 0;

    private boolean favourited = false;

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

        mViewModel = new MovieDetailsViewModel(movieRemoteRepository, this);
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
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
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

    @Override
    public void makeFavourite(String title) {
        if(!favourited){
            //Bitmap bitmap = null;


                Glide.with(getActivity())
                            .load(mViewModel.getPosterUrl())
                            .into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(final GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    new AsyncTask<Void, Void, Void>() {
                                        @Override
                                        protected Void doInBackground(Void... params) {
                                            final Bitmap bitmap = GlideUtils.getBitmap(resource);
                                            new ImageSaver(getActivity()).
                                                    setFileName(mMovieId+"").
                                                    setDirectoryName("images").
                                                    save(bitmap);
                                            return null;
                                        }
                                    }.execute();
                                }
                            });



            //OfflineImage.saveToInternalStorage(bitmap, getActivity().getApplicationContext(), mMovieId+"");


            ContentValues contentValues = new ContentValues();
            // Put the task description and selected mPriority into the ContentValues
            contentValues.put(MoviesContract.MovieEntry.COLUMN_TITLE, title);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_NUMBER, mMovieId+"");
            Log.v("HARRY5", mMovieId+"");


            // Insert the content values via a ContentResolver
            Uri uri = getActivity().getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, contentValues);

            // Display the URI that's returned with a Toast
            // [Hint] Don't forget to call finish() to return to MainActivity after this insert is complete
            if(uri != null) {
                Toast.makeText(getActivity().getBaseContext(), "Success", Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(getActivity().getApplicationContext(), title, Toast.LENGTH_SHORT).show();
        }else{
            int id = 0;
            String stringId = Integer.toString(theMovieId);
            Uri uri = MoviesContract.MovieEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(stringId).build();
            id = getActivity().getContentResolver().delete(uri, null, null);
            //getActivity().getSupportLoaderManager().restartLoader(0, null, FavouriteFragment.class);
            if(id != 0) {
                Toast.makeText(getActivity().getBaseContext(), "Successfully deleted", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(getActivity()) {
            Cursor mfav = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mfav != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mfav);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getActivity().getContentResolver().query(MoviesContract.MovieEntry.CONTENT_URI,
                            null,
                            MoviesContract.MovieEntry.COLUMN_NUMBER+ " = ?",
                            new String[]{mMovieId+""},
                            null);

                } catch (Exception e) {
                    Log.e("HARRY", "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
                //PRIORITY + " = ?"
            }

            public void deliverResult(Cursor data) {
                mfav = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favourited = getFavourited(data);
        Toast.makeText(getActivity().getApplication(), favourited+"", Toast.LENGTH_SHORT).show();
    }

    private boolean getFavourited(Cursor mCursor) {

        int idIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry._ID);
        int titleIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_TITLE);
        int numberIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_NUMBER);


        // Determine the values of the wanted data

        String number = "";
        if(mCursor.moveToFirst()){
            number = mCursor.getString(numberIndex);
            theMovieId = mCursor.getInt(idIndex);
            Toast.makeText(getActivity(), theMovieId+"", Toast.LENGTH_SHORT).show();
        }



        if(number.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favourited = false;
    }

    private RequestListener posterListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            final Bitmap bitmap = GlideUtils.getBitmap(resource);
            OfflineImage.saveToInternalStorage(bitmap, getActivity().getApplicationContext(),
                    mMovieId+"");
            Log.v("HARRY3", "saved");
            return false;
        }
    };


}
