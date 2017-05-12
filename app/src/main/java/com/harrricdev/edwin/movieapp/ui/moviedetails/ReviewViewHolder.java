package com.harrricdev.edwin.movieapp.ui.moviedetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;


import com.harrricdev.edwin.movieapp.databinding.ReviewItem;
import com.harrricdev.edwin.movieapp.utils.ReviewViewModel;

/**
 * Created by edwin on 5/6/17.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private ReviewItem reviewItem;

    public ReviewViewHolder(ReviewItem reviewItem) {
        super(reviewItem.getRoot());
        this.reviewItem = reviewItem;
    }

    public void seReviewViewModel(@NonNull ReviewViewModel reviewViewModel) {
        reviewItem.setReviewModel(reviewViewModel);
        reviewItem.executePendingBindings();
    }
}
