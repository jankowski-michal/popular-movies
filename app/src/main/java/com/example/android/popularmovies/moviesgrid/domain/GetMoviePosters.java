package com.example.android.popularmovies.moviesgrid.domain;

import com.example.android.popularmovies.data.LocalRepository;
import com.example.android.popularmovies.data.RemoteRepository;
import com.example.android.popularmovies.moviesgrid.domain.entities.MoviePoster;
import com.example.android.popularmovies.utilities.UrlsUtils;

import android.content.Context;

import io.reactivex.Single;

public class GetMoviePosters {
    
    private Context mContext;
    
    private RemoteRepository mRemoteRepository;
    
    private LocalRepository mLocalRepository;
    
    public GetMoviePosters(final Context context) {
        mContext = context;
        mRemoteRepository = new RemoteRepository(mContext);
        mLocalRepository = new LocalRepository(mContext);
    }
    
    public Single<MoviePoster[]> getFavourite() {
        return mLocalRepository.requestFavouriteMovies();
    }
    
    public Single<MoviePoster[]> getTopRated(int page) {
        return mRemoteRepository.getMovies(UrlsUtils.TOP_RATED);
    }
    
    public Single<MoviePoster[]> getPopular(int page) {
        return mRemoteRepository.getMovies(UrlsUtils.POPULAR);
    }
}
