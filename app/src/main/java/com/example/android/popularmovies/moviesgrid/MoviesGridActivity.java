package com.example.android.popularmovies.moviesgrid;

import com.example.android.popularmovies.R;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MoviesGridActivity extends AppCompatActivity {
    
    //todo: we should also remember what kind of order
    public static final String IS_MISSING_DATA = "IS_MISSING_DATA";
    
    private MoviesGridContract.Presenter mPresenter;
    
    private MoviesGridFragment mMoviesGridFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_grid_activity);
        
        boolean isMissingData = true;
        
        /*
        * After Device Orientation Changed list is always downloaded again.
        * This is because application is not storing movies list in stage 1 project.
        * Proper handling of re-downloading data will be implemented in stage 2.
        *
        * if (savedInstanceState != null) {
        *     isMissingData = savedInstanceState.getBoolean(IS_MISSING_DATA);
        * }
        * */
        
        mMoviesGridFragment = (MoviesGridFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mMoviesGridFragment == null) {
            mMoviesGridFragment = MoviesGridFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, mMoviesGridFragment);
            transaction.commit();
        }
        
        mPresenter = new MoviesGridPresenter(mMoviesGridFragment, isMissingData);
        mMoviesGridFragment.setPresenter(mPresenter);
        mPresenter.start();
    }
    
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(IS_MISSING_DATA, mPresenter.isDataMissing());
        super.onSaveInstanceState(outState);
    }
    
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return mPresenter.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
