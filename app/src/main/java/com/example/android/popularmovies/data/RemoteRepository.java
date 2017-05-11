package com.example.android.popularmovies.data;

import com.example.android.popularmovies.data.remote.MappingUtils;
import com.example.android.popularmovies.moviedetails.domain.entities.MovieDetails;
import com.example.android.popularmovies.moviesgrid.domain.entities.MoviePoster;
import com.example.android.popularmovies.utilities.UrlsUtils;

import android.content.Context;

import java.net.URL;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static com.example.android.popularmovies.utilities.NetworkUtils.buildUrl;
import static com.example.android.popularmovies.utilities.NetworkUtils.getResponseFromHttpUrl;

public class RemoteRepository {
    
    private Context mContext;
    
    public RemoteRepository(Context context) {
        mContext = context;
    }
    
    public Single<MoviePoster[]> getMovies(final String uri) {
        return Single.create(new SingleOnSubscribe<MoviePoster[]>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<MoviePoster[]> e) throws Exception {
                URL url = buildUrl(uri);
                MoviePoster[] posters = MappingUtils.mapMoviePosters(getResponseFromHttpUrl(url));
                e.onSuccess(posters);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public Single<MovieDetails> getMovieDetails(final String movieId,  final boolean isFavourite) {
        return Single.create(new SingleOnSubscribe<MovieDetails>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<MovieDetails> e) throws Exception {
                
                MovieDetails movieDetails = new MovieDetails();
                
                URL url = buildUrl(UrlsUtils.getMovieDetailsUrl(movieId));
                movieDetails.setMovieInfo(MappingUtils.mapMovieInfo(getResponseFromHttpUrl(url)));
                movieDetails.getMovieInfo().setFavourite(isFavourite);
                
                url = buildUrl(UrlsUtils.getMovieReviewsUrl(movieId));
                movieDetails.setReviews(MappingUtils.mapReviews(getResponseFromHttpUrl(url)));
                
                url = buildUrl(UrlsUtils.getMovieTrailersUrl(movieId));
                movieDetails.setTrailers(MappingUtils.mapTrailers(getResponseFromHttpUrl(url)));
                
                e.onSuccess(movieDetails);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
