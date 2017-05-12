package com.harrricdev.edwin.movieapp.utils;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.harrricdev.edwin.movieapp.data.model.Review;

/**
 * Created by edwin on 5/11/17.
 */

public class ReviewViewModel extends BaseObservable {

    public final ObservableField<Review> mReviewObservable = new ObservableField<>();

    public final ObservableField<String> author = new ObservableField<>();

    public final ObservableField<String> content = new ObservableField<>();

    public ReviewViewModel(){
        mReviewObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Review review = mReviewObservable.get();
                author.set(review.getAuthor());
                content.set(review.getContent());
            }
        });
    }

    public void setReview(Review review){
        mReviewObservable.set(review);
    }

    @Nullable
    public Review getReview(){
        return mReviewObservable.get();
    }

}
