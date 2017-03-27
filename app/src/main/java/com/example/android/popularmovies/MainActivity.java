package com.example.android.popularmovies;

import com.example.android.popularmovies.model.MoviePoster;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.example.android.popularmovies.utilities.ScreenOrientation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements DownloadListener {
    
    private static final String TAG = MainActivity.class.getSimpleName();
    
    RecyclerView mRecyclerView;
    
    MoviesAdapter mAdapter;
    
    GridLayoutManager mLayoutManager;
    
    MoviesAsyncTask mMoviesAsyncTask;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        initLayoutManager();
        mMoviesAsyncTask = new MoviesAsyncTask(this);
        mMoviesAsyncTask.execute(NetworkUtils.TOP_RATED);
    }
    
    @Override
    public void onDownload(final MoviePoster[] posters) {
        initAdapter(posters);
    }
    
    private void initLayoutManager() {
        mLayoutManager = new GridLayoutManager(this, ScreenOrientation.getColumnsForOrientation(getResources()));
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
    
    private void initAdapter(final MoviePoster[] posters) {
        mAdapter = new MoviesAdapter(posters, getResources());
        mRecyclerView.setAdapter(mAdapter);
    }
}
