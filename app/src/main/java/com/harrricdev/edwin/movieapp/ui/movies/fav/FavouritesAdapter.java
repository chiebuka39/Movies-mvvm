package com.harrricdev.edwin.movieapp.ui.movies.fav;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harrricdev.edwin.movieapp.R;
import com.harrricdev.edwin.movieapp.data.db.FavouriteMovie;
import com.harrricdev.edwin.movieapp.data.db.MoviesContract;
import com.harrricdev.edwin.movieapp.databinding.FavouriteItem;

/**
 * Created by edwin on 5/13/17.
 */

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouriteViewHolder> {

    private Cursor mCursor = null;
    private Context mContext;

    public FavouritesAdapter(Context context){
        this.mContext = context;
    }

    public void setCursor(Cursor cursor){

        Log.v("HARRY", "called");
        if(cursor != null){
            mCursor = cursor;
            notifyDataSetChanged();
            Log.v("HARRY2", cursor.getCount()+"");
        }


    }


    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FavouriteItem item = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.item_favourites_layout, parent, false);

        return new FavouriteViewHolder(item);
    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {

        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry._ID);
        int titleIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_TITLE);
        int numberIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_NUMBER);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String title = mCursor.getString(titleIndex);
        String number = mCursor.getString(titleIndex);

        FavouriteMovie movie = new FavouriteMovie(id,title,number);

        final FavouritesViewModel favouritesViewModel = new FavouritesViewModel();
        favouritesViewModel.setFavouriteMovie(movie);
        holder.setFavouritesiewModel(favouritesViewModel);


    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        Log.v("HARRY2", mCursor.getCount()+"");
        return mCursor.getCount();
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder {

        FavouriteItem favouriteItem;

        public FavouriteViewHolder(FavouriteItem favouriteItem) {
            super(favouriteItem.getRoot());
            this.favouriteItem = favouriteItem;
        }

        public void setFavouritesiewModel(@NonNull FavouritesViewModel favouritesiewModel){
            favouriteItem.setFav(favouritesiewModel);
            favouriteItem.executePendingBindings();
        }
    }
}
