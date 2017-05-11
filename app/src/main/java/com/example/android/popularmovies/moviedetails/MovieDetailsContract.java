package com.example.android.popularmovies.moviedetails;

import com.example.android.popularmovies.BasePresenter;
import com.example.android.popularmovies.BaseView;
import com.example.android.popularmovies.moviedetails.domain.entities.Review;
import com.example.android.popularmovies.moviedetails.domain.entities.Trailer;

public class MovieDetailsContract {
    
    interface View extends BaseView<Presenter> {
        
        void setTitle(final String title);
        
        void setOverview(final String overview);
        
        void setVote(final Float vote);
        
        void setReleaseDate(final String releaseDate);
        
        void setPoster(final String posterUrl);
        
        void showMovieDetails();
        
        void showLoadingScreen();
        
        void showErrorMessage();
        
        void showReviews(Review[] reviews);
        
        void showTrailers(Trailer[] trailers);
        
        void setFavourite(boolean isFavourite);
        
        void setEmptyTrailers();
        
        void setEmptyReviews();
        
        void dismissRefresh();
    }
    
    public interface Presenter extends BasePresenter {
        
        void stop();
        
        void onRetryClick();
        
        void onTrailerClick(Trailer trailer);
        
        void onFavouriteClick();
        
        void onRefreshPull();
    }
}
