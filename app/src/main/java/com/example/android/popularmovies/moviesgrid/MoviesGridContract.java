package com.example.android.popularmovies.moviesgrid;

import com.example.android.popularmovies.BasePresenter;
import com.example.android.popularmovies.BaseView;
import com.example.android.popularmovies.model.MoviePoster;

import android.content.res.Resources;
import android.view.MenuItem;

class MoviesGridContract {
    
    interface View extends BaseView<Presenter> {
        
        int getChildLayoutPosition(final android.view.View view);
        
        void setPosters(final MoviePoster[] posters);
        
        void showMovieDetails(final String movieId);
        
        MoviePoster getPoster(int position);
        
        Resources getResporces();
    }
    
    interface Presenter extends BasePresenter, android.view.View.OnClickListener {
        
        boolean isDataMissing();
        
        boolean onOptionsItemSelected(final MenuItem item);
    }
}
