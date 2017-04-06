package com.example.android.popularmovies.moviedetails.domain;

import com.example.android.popularmovies.data.LocalRepository;
import com.example.android.popularmovies.data.RemoteRepository;
import com.example.android.popularmovies.moviedetails.domain.entities.MovieDetails;

import android.content.Context;

import io.reactivex.Completable;
import io.reactivex.Single;

public class GetMovieDetails {
    
    private final Context mContext;
    
    private final RemoteRepository mRemoteRepository;
    
    private final LocalRepository mLocalRepository;
    
    public GetMovieDetails(final Context context) {
        mContext = context;
        mRemoteRepository = new RemoteRepository(mContext);
        mLocalRepository = new LocalRepository(mContext);
    }
    
    public Completable saveToCache(MovieDetails movieDetails) {
        return mLocalRepository.requestRemoveAndSave(movieDetails);
    }
    
    public Single<MovieDetails> getLocalMovieDetails(String movieId) {
        return mLocalRepository.requestMovieDetails(movieId);
    }
    
    public Single<MovieDetails> getRemoteMovieDetails(String movieId, boolean isFavourite) {
        return mRemoteRepository.getMovieDetails(movieId, isFavourite);
    }
}
    
  
