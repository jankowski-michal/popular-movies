package com.example.android.popularmovies.data;

import com.example.android.popularmovies.data.local.db.DatabaseConnector;
import com.example.android.popularmovies.data.remote.MappingUtils;
import com.example.android.popularmovies.moviedetails.domain.entities.MovieDetails;
import com.example.android.popularmovies.moviedetails.domain.entities.MovieInfo;
import com.example.android.popularmovies.moviedetails.domain.entities.Review;
import com.example.android.popularmovies.moviedetails.domain.entities.Trailer;
import com.example.android.popularmovies.moviesgrid.domain.entities.MoviePoster;

import android.content.Context;
import android.database.Cursor;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class LocalRepository {
    
    private DatabaseConnector dbConnector;
    
    public LocalRepository(final Context context) {
        dbConnector = new DatabaseConnector(context);
    }
    
    public Completable requestRemoveAndSave(final MovieDetails movieDetails) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final CompletableEmitter e) throws Exception {
                save(movieDetails);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public Single<MovieDetails> requestMovieDetails(final String movieId) {
        return Single.create(new SingleOnSubscribe<MovieDetails>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<MovieDetails> e) throws Exception {
                MovieDetails movieDetails = getMovieDetails(movieId);
                e.onSuccess(movieDetails);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public Single<MoviePoster[]> requestFavouriteMovies() {
        return Single.create(new SingleOnSubscribe<MoviePoster[]>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<MoviePoster[]> e) throws Exception {
                Cursor cursor = dbConnector.getFavourites();
                MoviePoster[] posters = MappingUtils.mapMoviePosters(cursor);
                e.onSuccess(posters);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    private void save(final MovieDetails movieDetails) {
        dbConnector.deleteMovieDetails(movieDetails.getMovieInfo().getId());
        dbConnector.save(movieDetails);
    }
    
    @android.support.annotation.NonNull
    private MovieDetails getMovieDetails(final String movieId) {
        Cursor cursor = dbConnector.getMovieInfo(movieId);
        MovieDetails movieDetails = new MovieDetails();
        MovieInfo movieInfo = MappingUtils.mapMovieInfo(cursor);
        
        cursor = dbConnector.getTrailers(movieId);
        Trailer[] trailers = MappingUtils.mapTrailers(cursor);
        
        cursor = dbConnector.getReviews(movieId);
        Review[] reviews = MappingUtils.mapReviews(cursor);
        
        movieDetails.setMovieInfo(movieInfo);
        movieDetails.setReviews(reviews);
        movieDetails.setTrailers(trailers);
        return movieDetails;
    }
    
    //todo: unsubscribe on destroy
    //todo: should we create new object for each method call?
    //todo: what happens when observable single/completeable finishes
    //todo: can we start single again for different data etc - movie itd etc.
    //todo: use one on one meeting and ask about fragment on created
}
