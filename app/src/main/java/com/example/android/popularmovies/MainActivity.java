package com.example.android.popularmovies;

import com.example.android.popularmovies.model.MoviePoster;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.example.android.popularmovies.utilities.GridElementsDimensionUtils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements DownloadListener {
    
    private RecyclerView mRecyclerView;
    
    private MoviesAsyncTask mMoviesAsyncTask;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        initLayoutManager();
        downloadPostersForm(NetworkUtils.POPULAR);
    }
    
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.order_by_popular) {
            downloadPostersForm(NetworkUtils.POPULAR);
            return true;
        }
        if (itemThatWasClickedId == R.id.order_by_top_rated) {
            downloadPostersForm(NetworkUtils.TOP_RATED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onDownloaded(final MoviePoster[] posters) {
        initAdapter(posters);
    }
    
    private void downloadPostersForm(String url) {
        if (mMoviesAsyncTask != null) {
            mMoviesAsyncTask.cancel(true);
        }
        mMoviesAsyncTask = new MoviesAsyncTask(this);
        mMoviesAsyncTask.execute(url);
    }
    
    private void initLayoutManager() {
        final GridLayoutManager layoutManager = new GridLayoutManager(this, GridElementsDimensionUtils.getColumnsForOrientation(getResources()));
        mRecyclerView.setLayoutManager(layoutManager);
    }
    
    private void initAdapter(final MoviePoster[] posters) {
        final MoviesAdapter adapter = new MoviesAdapter(posters, getResources());
        mRecyclerView.setAdapter(adapter);
    }
}
