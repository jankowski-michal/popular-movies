package com.example.android.popularmovies.moviesgrid;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.moviesgrid.domain.GetMoviePosters;
import com.example.android.popularmovies.moviesgrid.domain.entities.MoviePoster;
import com.example.android.popularmovies.utilities.UrlsUtils;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

class MoviesGridPresenter implements MoviesGridContract.Presenter {
    
    private final MoviesGridContract.View mView;
    
    private Context mContext;
    
    private GetMoviePosters mUseCase;
    
    private String mDownloadUrl;
    
    private PostersObserver mObserver = new PostersObserver();
    
    private CompositeDisposable mDisposable = new CompositeDisposable();
    
    MoviesGridPresenter(MoviesGridFragment view, Context context) {
        mView = view;
        mContext = context;
        mUseCase = new GetMoviePosters(mContext);
    }
    
    @Override
    public void start() {
        mDownloadUrl = UrlsUtils.POPULAR;
        download();
    }
    
    @Override
    public void onClick(final View view) {
        int position = mView.getChildLayoutPosition(view);
        MoviePoster poster = mView.getPoster(position);
        String movieId = "" + poster.getId();
        mView.showMovieDetails(movieId);
    }
    
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.order_by_popular) {
            mDownloadUrl = UrlsUtils.POPULAR;
            mView.showLoadingScreen();
            download();
            return true;
        }
        if (itemThatWasClickedId == R.id.order_by_top_rated) {
            mDownloadUrl = UrlsUtils.TOP_RATED;
            mView.showLoadingScreen();
            download();
            return true;
        }
        if (itemThatWasClickedId == R.id.favourites) {
            mView.showLoadingScreen();
            showFavourites();
            return true;
        }
        return false;
    }
    
    @Override
    public void onRetryClick() {
        download();
        mView.showLoadingScreen();
    }
    
    @Override
    public void stop() {
        mDisposable.clear();
        
    }
    
    private void setPosters(final MoviePoster[] posters) {
        mView.setPosters(posters);
    }
    
    private void download() {
        Single<MoviePoster[]> mObservable;
        if (mDownloadUrl.equals(UrlsUtils.TOP_RATED)) {
            mUseCase.getTopRated(0).subscribe(mObserver);
        } else {
            mUseCase.getPopular(0).subscribe(mObserver);
        }
    }
    
    private void showFavourites() {
        Single<MoviePoster[]> mObservable = mUseCase.getFavourite();
        mObservable.subscribe(mObserver);
    }
    
    private class PostersObserver implements SingleObserver<MoviePoster[]> {
        
        @Override
        public void onSubscribe(@NonNull final Disposable d) {
            mDisposable.add(d);
        }
        
        @Override
        public void onSuccess(@NonNull final MoviePoster[] moviePosters) {
            setPosters(moviePosters);
        }
        
        @Override
        public void onError(Throwable e) {
            mView.showErrorMessage();
            e.printStackTrace();
        }
    }
}
