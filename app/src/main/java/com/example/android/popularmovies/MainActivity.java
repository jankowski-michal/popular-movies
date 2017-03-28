package com.example.android.popularmovies;

import com.example.android.popularmovies.model.MoviePoster;
import com.example.android.popularmovies.utilities.GridElementsDimensionUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements DownloadListener, View.OnClickListener {
    
    private RecyclerView mRecyclerView;
    
    private MoviesAsyncTask mMoviesAsyncTask;
    
    private MoviesAdapter mAdapter;
    
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
    
    @Override
    public void onClick(final View view) {
        int position = mRecyclerView.getChildLayoutPosition(view);
        MoviePoster poster = mAdapter.getPoster(position);
        startChildActivity(poster.getId() + "");
    }
    
    public void startChildActivity(String movieId) {
        Intent startChildActivityIntent = new Intent(this, MovieDetailsActivity.class);
        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, movieId);
        startActivity(startChildActivityIntent);
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
        mAdapter = new MoviesAdapter(posters, getResources(), this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
