package com.example.android.popularmovies.moviedetails;

import com.example.android.popularmovies.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MovieDetailsActivity extends AppCompatActivity {
    
    private MovieDetailsContract.Presenter mPresenter;
    
    private MovieDetailsFragment mMovieDetailsFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.movie_details_activity);
        
        /*
        * After Device Orientation Changed movie details are always downloaded again.
        * This is because application is not storing movies list in stage 1 project.
        * Proper handling of re-downloading data will be implemented in stage 2.
        * */
        
        initView();
        initPresenter();
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    
    private void initView() {
        mMovieDetailsFragment = MovieDetailsFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.contentFrame, mMovieDetailsFragment);
        transaction.commit();
    }
    
    private void initPresenter() {
        mPresenter = new MovieDetailsPresenter(mMovieDetailsFragment, getMovieId());
        mMovieDetailsFragment.setPresenter(mPresenter);
        mPresenter.start();
    }
    
    private String getMovieId() {
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            return intent.getStringExtra(Intent.EXTRA_TEXT);
        }
        return null;
    }
}
