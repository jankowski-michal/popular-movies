package com.example.android.popularmovies.moviedetails;

import com.example.android.popularmovies.moviedetails.domain.GetMovieDetails;
import com.example.android.popularmovies.moviedetails.domain.entities.MovieDetails;
import com.example.android.popularmovies.moviedetails.domain.entities.Trailer;
import com.example.android.popularmovies.utilities.UrlsUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {
    
    private final String mMovieId;
    
    private final MovieDetailsContract.View mView;
    
    private final SingleObserver<MovieDetails> mRemoteMovieDetailsObserver = new MovieDetailsRemoteObserver();
    
    private final SingleObserver<MovieDetails> mLocalMovieDetailsObserver = new MovieDetailsLocalObserver();
    
    private final CompletableObserver favouriteObserver = new FavouriteObserver();
    
    private final Context mContext;
    
    private final GetMovieDetails mUseCase;
    
    private CompositeDisposable mDisposable = new CompositeDisposable();
    
    private MovieDetails mMovieDetails;
    
    MovieDetailsPresenter(MovieDetailsFragment view, String movieId, Context context) {
        mView = view;
        mMovieId = movieId;
        mContext = context;
        mUseCase = new GetMovieDetails(mContext);
    }
    
    @Override
    public void stop() {
        mDisposable.clear();
    }
    
    @Override
    public void start() {
        mUseCase.getLocalMovieDetails(mMovieId)
                .subscribe(mLocalMovieDetailsObserver);
    }
    
    @Override
    public void onRetryClick() {
        mView.showLoadingScreen();
        getDataFromRemote();
    }
    
    @Override
    public void onTrailerClick(final Trailer trailer) {
        String url = UrlsUtils.getTrailerUrl(trailer);
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
    
    @Override
    public void onFavouriteClick() {
        if (mMovieDetails.getMovieInfo().isFavourite()) {
            mMovieDetails.getMovieInfo().setFavourite(false);
        } else {
            mMovieDetails.getMovieInfo().setFavourite(true);
        }
        mUseCase.saveToCache(mMovieDetails).subscribe(favouriteObserver);
    }
    
    @Override
    public void onRefreshPull() {
        mView.showLoadingScreen();
        getDataFromRemote();
        //todo: add pasing favourite state
    }
    
    private void getDataFromRemote() {
        if (mMovieDetails != null) {
            mUseCase.getRemoteMovieDetails(mMovieId, mMovieDetails.getMovieInfo().isFavourite()).subscribe(mRemoteMovieDetailsObserver);
        } else {
            mUseCase.getRemoteMovieDetails(mMovieId, false).subscribe(mRemoteMovieDetailsObserver);
        }
    }
    
    private void displayCompleteMovieDetails() {
        String posterUrl = UrlsUtils.getPosterUrl(mMovieDetails.getMovieInfo());
        mView.setFavourite(mMovieDetails.getMovieInfo().isFavourite());
        mView.setTitle(mMovieDetails.getMovieInfo().getTitle());
        mView.setReleaseDate(mMovieDetails.getMovieInfo().getRelease_date());
        mView.setVote(mMovieDetails.getMovieInfo().getVote_average());
        mView.setOverview(mMovieDetails.getMovieInfo().getOverview());
        mView.setPoster(posterUrl);
        mView.showMovieDetails();
        if (mMovieDetails.getReviews() != null && mMovieDetails.getReviews().length>0) {
            System.out.println("show reviews - ok " + mMovieDetails.getReviews().length);
            mView.showReviews(mMovieDetails.getReviews());
        } else {
            mView.setEmptyReviews();
        }
        if (mMovieDetails.getTrailers() != null && mMovieDetails.getTrailers().length>0) {
            System.out.println("show trailers - ok " + mMovieDetails.getReviews().length);
            mView.showTrailers(mMovieDetails.getTrailers());
        } else {
            mView.setEmptyTrailers();
        }
    }
    
    private class FavouriteObserver implements CompletableObserver {
        
        @Override
        public void onSubscribe(@NonNull final Disposable d) {
            mDisposable.add(d);
        }
        
        @Override
        public void onComplete() {
            mView.setFavourite(mMovieDetails.getMovieInfo().isFavourite());
            System.out.println("on complete");
        }
        
        @Override
        public void onError(@NonNull final Throwable e) {
            System.out.println("on complete");
            e.printStackTrace();
        }
    }
    
    private class MovieDetailsLocalObserver implements SingleObserver<MovieDetails> {
        
        @Override
        public void onSubscribe(@NonNull final Disposable d) {
            mDisposable.add(d);
        }
        
        @Override
        public void onSuccess(@NonNull final MovieDetails movieDetails) {
            mMovieDetails = movieDetails;
            displayCompleteMovieDetails();
        }
        
        @Override
        public void onError(Throwable e) {
            getDataFromRemote();
            e.printStackTrace();
        }
    }
    
    private class MovieDetailsRemoteObserver implements SingleObserver<MovieDetails> {
        
        @Override
        public void onSubscribe(@NonNull final Disposable d) {
            mDisposable.add(d);
        }
        
        @Override
        public void onSuccess(@NonNull final MovieDetails movieDetails) {
            mMovieDetails = movieDetails;
            mUseCase.saveToCache(mMovieDetails).subscribe();
            displayCompleteMovieDetails();
            mView.dismissRefresh();
        }
        
        @Override
        public void onError(Throwable e) {
            mView.dismissRefresh();
            mView.showErrorMessage();
            e.printStackTrace();
        }
    }
}