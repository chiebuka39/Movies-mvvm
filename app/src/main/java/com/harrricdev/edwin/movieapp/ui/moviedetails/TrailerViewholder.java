package com.harrricdev.edwin.movieapp.ui.moviedetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.harrricdev.edwin.movieapp.databinding.TrailerItem;
import com.harrricdev.edwin.movieapp.utils.ReviewViewModel;
import com.harrricdev.edwin.movieapp.utils.TrailerViewModel;

/**
 * Created by edwin on 5/12/17.
 */

public class TrailerViewholder extends RecyclerView.ViewHolder {

    private TrailerItem trailerItem;

    public TrailerViewholder(TrailerItem trailerItem) {
        super(trailerItem.getRoot());
        this.trailerItem = trailerItem;
    }

    public void seTrailerViewModel(@NonNull TrailerViewModel trailerViewModel) {
        trailerItem.setTrailerModel(trailerViewModel);
        trailerItem.executePendingBindings();
    }
}
