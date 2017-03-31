package com.example.android.popularmovies.moviedetails;

import com.example.android.popularmovies.BasePresenter;
import com.example.android.popularmovies.BaseView;

class MovieDetailsContract {
    
    interface View extends BaseView<Presenter> {
        
        void setTitle(final String title);
        
        void setOverview(final String overview);
        
        void setVote(final Float vote);
        
        void setReleaseDate(final String releaseDate);
        
        void setPoster(final String posterUrl);
        
        void showMovieDetails();
        
        void showLoadingScreen();
        
        void showErrorMessage();
    }
    
    interface Presenter extends BasePresenter {
        
        void onRetryClick();
    }
}
