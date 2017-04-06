package com.example.android.popularmovies.moviesgrid;

import com.example.android.popularmovies.BasePresenter;
import com.example.android.popularmovies.BaseView;
import com.example.android.popularmovies.moviesgrid.domain.entities.MoviePoster;

import android.view.MenuItem;

class MoviesGridContract {
    
    interface View extends BaseView<Presenter> {
        
        int getChildLayoutPosition(final android.view.View view);
        
        void setPosters(final MoviePoster[] posters);
        
        void showMovieDetails(final String movieId);
        
        void showLoadingScreen();
        
        void showErrorMessage();
        
        MoviePoster getPoster(int position);
    }
    
    interface Presenter extends BasePresenter, android.view.View.OnClickListener {
        
        boolean onOptionsItemSelected(final MenuItem item);
        
        void onRetryClick();
        
        void stop();
    }
}
