package com.harrricdev.edwin.movieapp.ui.movies;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;


import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.harrricdev.edwin.movieapp.R;

import com.harrricdev.edwin.movieapp.databinding.MoviesBinding;
import com.harrricdev.edwin.movieapp.ui.base.BaseActivity;
import com.harrricdev.edwin.movieapp.ui.movies.fav.FavouriteFragment;

public class MainActivity extends BaseActivity {




    public MoviesBinding binding;


    private String dItem = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setUpToolbar(binding);


       //getWindow().setStatusBarColor(Color.parseColor("#20111111"));

        if (savedInstanceState == null) {
            changeSortFragment("popular");
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_popular:
                        if(!dItem.equalsIgnoreCase("popular")){
                            dItem = "popular";
                            changeSortFragment(dItem);
                            Toast.makeText(MainActivity.this, "Popular", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.action_rate:
                        if(!dItem.equalsIgnoreCase("top_rated")){
                            dItem = "top_rated";
                            changeSortFragment(dItem);
                            Toast.makeText(MainActivity.this, "Top Rated", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.action_favourite:
                        if(!dItem.equalsIgnoreCase("favourite")){
                            dItem = "favourite";
                            Fragment fragment = FavouriteFragment.newInstance();
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.container, fragment)
                                    .commit();
                            Toast.makeText(MainActivity.this, "favourite", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return true;
            }
        });


    }

    private void changeSortFragment(String sortKey) {
        Fragment fragment = MoviesFragment.newInstance(sortKey);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment)
                .commit();
    }

    private void setUpToolbar(MoviesBinding binding) {
        //setSupportActionBar(binding.mainToolbar);
    }



}


