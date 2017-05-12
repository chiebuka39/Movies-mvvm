package com.harrricdev.edwin.movieapp.ui.moviedetails;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.harrricdev.edwin.movieapp.R;
import com.harrricdev.edwin.movieapp.data.model.Trailer;
import com.harrricdev.edwin.movieapp.databinding.TrailerItem;
import com.harrricdev.edwin.movieapp.utils.TrailerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwin on 5/12/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewholder> {

    private List<Trailer> trailers;
    private TrailerInteractor mTrailerInteractor;

    public TrailerAdapter( TrailerInteractor trailerInteractor){
        trailers = new ArrayList<>();
        this.mTrailerInteractor = trailerInteractor;
    }

    public void setTrailers(@NonNull List<Trailer> trailers){

        this.trailers = trailers;
        notifyDataSetChanged();
    }


    @Override
    public TrailerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        TrailerItem trailerItem = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_trailer_layout,
                parent, false);
        return new TrailerViewholder(trailerItem);
    }

    @Override
    public void onBindViewHolder(TrailerViewholder holder, int position) {
        final Trailer trailer = trailers.get(position);

        final TrailerViewModel trailerViewModel = new TrailerViewModel(mTrailerInteractor);
        trailerViewModel.setTrailer(trailer);
        holder.seTrailerViewModel(trailerViewModel);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}
