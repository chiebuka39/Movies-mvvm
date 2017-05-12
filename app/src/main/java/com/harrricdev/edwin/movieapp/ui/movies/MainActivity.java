package com.harrricdev.edwin.movieapp.ui.movies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.harrricdev.edwin.movieapp.R;
import com.harrricdev.edwin.movieapp.data.remote.api.MovieApiService;
import com.harrricdev.edwin.movieapp.data.repository.MovieRemoteRepository;
import com.harrricdev.edwin.movieapp.databinding.ActivityMainBinding;
import com.harrricdev.edwin.movieapp.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {


    MoviesViewModel moviesViewModel;
    private MovieRemoteRepository mMoviesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolbar(binding);



        //setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment fragment = MoviesFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, fragment)
                    .commit();
        }
    }

    private void setUpToolbar(ActivityMainBinding binding) {
        setSupportActionBar(binding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


