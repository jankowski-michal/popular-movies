package com.example.android.popularmovies;

import com.example.android.popularmovies.asynctasks.DownloadAsyncTask;
import com.example.android.popularmovies.asynctasks.DownloadListener;
import com.example.android.popularmovies.asynctasks.MappingAsyncTask;
import com.example.android.popularmovies.asynctasks.MappingListener;
import com.example.android.popularmovies.model.MoviePoster;
import com.example.android.popularmovies.utilities.ImagesDimensionUtils;
import com.example.android.popularmovies.utilities.UrlsUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements DownloadListener, View.OnClickListener, MappingListener {
    
    private RecyclerView mRecyclerView;
    
    private DownloadAsyncTask mDownloadAsyncTask;
    
    private MappingAsyncTask mMappingAsyncTask;
    
    private MoviesAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        initLayoutManager();
        downloadPostersForm(UrlsUtils.POPULAR);
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
            downloadPostersForm(UrlsUtils.POPULAR);
            return true;
        }
        if (itemThatWasClickedId == R.id.order_by_top_rated) {
            downloadPostersForm(UrlsUtils.TOP_RATED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onDownloaded(final String responseJson) {
        requestMappingMoviesList(responseJson);
    }
    
    @Override
    public void onClick(final View view) {
        int position = mRecyclerView.getChildLayoutPosition(view);
        MoviePoster poster = mAdapter.getPoster(position);
        startChildActivity(poster.getId() + "");
    }
    
    @Override
    public void onMapped(final Object object) {
        MoviePoster[] posters = (MoviePoster[]) object;
        initAdapter(posters);
    }
    
    public void startChildActivity(String movieId) {
        Intent startChildActivityIntent = new Intent(this, MovieDetailsActivity.class);
        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, movieId);
        startActivity(startChildActivityIntent);
    }
    
    private void requestMappingMoviesList(final String responseJson) {
        if (mMappingAsyncTask != null) {
            mMappingAsyncTask.cancel(true);
        }
        MappingAsyncTask.MappingRequest mappingRequest = new MappingAsyncTask.MappingRequest(responseJson, MappingAsyncTask.MAP_TO_MOVIES_LIST);
        mMappingAsyncTask = new MappingAsyncTask(this);
        mMappingAsyncTask.execute(mappingRequest);
    }
    
    private void downloadPostersForm(String url) {
        if (mDownloadAsyncTask != null) {
            mDownloadAsyncTask.cancel(true);
        }
        mDownloadAsyncTask = new DownloadAsyncTask(this);
        mDownloadAsyncTask.execute(url);
    }
    
    private void initLayoutManager() {
        int screenOrientation = getResources().getConfiguration().orientation;
        final GridLayoutManager layoutManager = new GridLayoutManager(this, ImagesDimensionUtils.getColumnsForOrientation(screenOrientation));
        mRecyclerView.setLayoutManager(layoutManager);
    }
    
    private void initAdapter(final MoviePoster[] posters) {
        mAdapter = new MoviesAdapter(posters, getResources(), this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
