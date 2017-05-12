package com.harrricdev.edwin.movieapp.ui.moviedetails;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.harrricdev.edwin.movieapp.R;
import com.harrricdev.edwin.movieapp.data.model.Movie;
import com.harrricdev.edwin.movieapp.data.model.Review;
import com.harrricdev.edwin.movieapp.data.repository.MoviesRepository;
import com.harrricdev.edwin.movieapp.databinding.MovieItem;
import com.harrricdev.edwin.movieapp.databinding.ReviewItem;
import com.harrricdev.edwin.movieapp.ui.movies.Interactor;
import com.harrricdev.edwin.movieapp.ui.movies.MovieItemViewModel;
import com.harrricdev.edwin.movieapp.ui.movies.MovieViewHolder;
import com.harrricdev.edwin.movieapp.utils.ReviewViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwin on 5/6/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private List<Review> reviews;
    private Interactor interactor;
    private MoviesRepository moviesRepository;

    public ReviewAdapter(MoviesRepository moviesRepository) {
        //this.interactor = interactor;
        this.moviesRepository = moviesRepository;
        reviews = new ArrayList<>();
    }

    public void setReviews(@NonNull List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ReviewItem reviewItem = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_review_layout, parent, false);
        return new ReviewViewHolder(reviewItem);
    }

    @Override
    public void onBindViewHolder(final ReviewViewHolder holder, int position) {
        final Review review = reviews.get(position);

        final ReviewViewModel reviewViewModel = new ReviewViewModel();
        reviewViewModel.setReview(review);
        holder.seReviewViewModel(reviewViewModel);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
