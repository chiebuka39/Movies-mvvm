package com.harrricdev.edwin.movieapp.ui.moviedetails;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.harrricdev.edwin.movieapp.R;
import com.harrricdev.edwin.movieapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        setSupportActionBar(binding.toolbar);

        Bundle args = getIntent().getExtras();
        Long movieId;

        if(args != null){
            movieId = args.getLong("MOVIE_ID");

            if(savedInstanceState == null){
                Fragment fragment = MovieDetailsFragment.newInstance(movieId);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.detail_container, fragment)
                        .commit();
            }
        }



    }




}
