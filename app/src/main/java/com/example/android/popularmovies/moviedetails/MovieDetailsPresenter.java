package com.example.android.popularmovies.moviedetails;

import com.example.android.popularmovies.asynctasks.DownloadAsyncTask;
import com.example.android.popularmovies.asynctasks.DownloadListener;
import com.example.android.popularmovies.asynctasks.MappingAsyncTask;
import com.example.android.popularmovies.asynctasks.MappingListener;
import com.example.android.popularmovies.model.MovieDetails;
import com.example.android.popularmovies.utilities.UrlsUtils;

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter, DownloadListener, MappingListener {
    
    private final String mMovieId;
    
    private final MovieDetailsContract.View mView;
    
    private DownloadAsyncTask mDownloadAsyncTask;
    
    private MappingAsyncTask mMappingAsyncTask;
    
    MovieDetailsPresenter(MovieDetailsFragment view, String movieId) {
        mView = view;
        mMovieId = movieId;
    }
    
    @Override
    public void start() {
        downloadMovieDetails();
    }
    
    @Override
    public void onDownloaded(final String responseJson) {
        requestMappingMovieDetails(responseJson);
    }
    
    @Override
    public void onMapped(final Object object) {
        MovieDetails movieDetails = (MovieDetails) object;
        if (movieDetails == null) {
            mView.showErrorMessage();
        } else {
            displayMovieDetails(movieDetails);
        }
    }
    
    @Override
    public void onRetryClick() {
        downloadMovieDetails();
        mView.showLoadingScreen();
    }
    
    private void displayMovieDetails(final MovieDetails movieDetails) {
        String posterUrl = UrlsUtils.getPosterUrl(movieDetails);
        
        mView.setTitle(movieDetails.getTitle());
        mView.setReleaseDate(movieDetails.getRelease_date());
        mView.setVote(movieDetails.getVote_average());
        mView.setOverview(movieDetails.getOverview());
        mView.setPoster(posterUrl);
        
        mView.showMovieDetails();
    }
    
    private void requestMappingMovieDetails(final String responseJson) {
        if (mMappingAsyncTask != null) {
            mMappingAsyncTask.cancel(true);
        }
        MappingAsyncTask.MappingRequest mappingRequest = MappingAsyncTask.MappingRequest.movieDetailsRequest(responseJson);
        mMappingAsyncTask = new MappingAsyncTask(this);
        mMappingAsyncTask.execute(mappingRequest);
    }
    
    private void downloadMovieDetails() {
        if (mDownloadAsyncTask != null) {
            mDownloadAsyncTask.cancel(true);
        }
        mDownloadAsyncTask = new DownloadAsyncTask(this);
        String url = UrlsUtils.getMovieDetailsUrl(mMovieId);
        mDownloadAsyncTask.execute(url);
    }
}
