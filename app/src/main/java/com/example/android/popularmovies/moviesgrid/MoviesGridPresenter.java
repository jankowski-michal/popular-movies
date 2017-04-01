package com.example.android.popularmovies.moviesgrid;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.asynctasks.DownloadAsyncTask;
import com.example.android.popularmovies.asynctasks.DownloadListener;
import com.example.android.popularmovies.asynctasks.MappingAsyncTask;
import com.example.android.popularmovies.asynctasks.MappingListener;
import com.example.android.popularmovies.model.MoviePoster;
import com.example.android.popularmovies.utilities.UrlsUtils;

import android.view.MenuItem;
import android.view.View;

class MoviesGridPresenter implements MoviesGridContract.Presenter, DownloadListener, MappingListener {
    
    private final MoviesGridContract.View mView;
    
    private String mDownloadUrl;
    
    private DownloadAsyncTask mDownloadAsyncTask;
    
    private MappingAsyncTask mMappingAsyncTask;
    
    private boolean mIsDataMissing;
    
    MoviesGridPresenter(MoviesGridFragment view, boolean isDataMissing) {
        mView = view;
        mIsDataMissing = isDataMissing;
    }
    
    @Override
    public void start() {
        if (mIsDataMissing) {
            mDownloadUrl = UrlsUtils.POPULAR;
            downloadPosters();
        }
    }
    
    @Override
    public void onMapped(final Object object) {
        MoviePoster[] posters = (MoviePoster[]) object;
        if (posters == null || posters.length == 0) {
            mView.showErrorMessage();
        } else {
            setPosters(posters);
            mIsDataMissing = false;
        }
    }
    
    @Override
    public void onDownloaded(final String responseJson) {
        requestMappingMoviesList(responseJson);
    }
    
    @Override
    public void onClick(final View view) {
        int position = mView.getChildLayoutPosition(view);
        MoviePoster poster = mView.getPoster(position);
        String movieId = "" + poster.getId();
        mView.showMovieDetails(movieId);
    }
    
    @Override
    public boolean isDataMissing() {
        return mIsDataMissing;
    }
    
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.order_by_popular) {
            mDownloadUrl = UrlsUtils.POPULAR;
            downloadPosters();
            mView.showLoadingScreen();
            return true;
        }
        if (itemThatWasClickedId == R.id.order_by_top_rated) {
            mDownloadUrl = UrlsUtils.TOP_RATED;
            downloadPosters();
            mView.showLoadingScreen();
            return true;
        }
        return false;
    }
    
    @Override
    public void onRetryClick() {
        downloadPosters();
        mView.showLoadingScreen();
    }
    
    private void setPosters(final MoviePoster[] posters) {
        mView.setPosters(posters);
    }
    
    private void requestMappingMoviesList(final String responseJson) {
        if (mMappingAsyncTask != null) {
            mMappingAsyncTask.cancel(true);
        }
        MappingAsyncTask.MappingRequest mappingRequest = MappingAsyncTask.MappingRequest.moviesListRequest(responseJson);
        mMappingAsyncTask = new MappingAsyncTask(this);
        mMappingAsyncTask.execute(mappingRequest);
    }
    
    private void downloadPosters() {
        if (mDownloadAsyncTask != null) {
            mDownloadAsyncTask.cancel(true);
        }
        mDownloadAsyncTask = new DownloadAsyncTask(this);
        mDownloadAsyncTask.execute(mDownloadUrl);
    }
}
