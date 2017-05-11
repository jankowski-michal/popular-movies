package com.example.android.popularmovies.moviesgrid;

import com.example.android.popularmovies.R;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MoviesGridActivity extends AppCompatActivity {
    
    private MoviesGridContract.Presenter mPresenter;
    
    private MoviesGridFragment mMoviesGridFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_grid_activity);
        
        mMoviesGridFragment = (MoviesGridFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mMoviesGridFragment == null) {
            mMoviesGridFragment = MoviesGridFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, mMoviesGridFragment);
            transaction.commit();
        }
        
        mPresenter = new MoviesGridPresenter(mMoviesGridFragment, this);
        mMoviesGridFragment.setPresenter(mPresenter);
        mPresenter.start();
    }
    
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return mPresenter.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onDestroy() {
        mPresenter.stop();
        super.onDestroy();
    }
}
