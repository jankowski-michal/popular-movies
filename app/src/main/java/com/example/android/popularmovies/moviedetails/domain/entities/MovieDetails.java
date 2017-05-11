package com.example.android.popularmovies.moviedetails.domain.entities;

public class MovieDetails {
    
    private MovieInfo mMovieInfo;
    
    private Review[] mReviews;
    
    private Trailer[] mTrailers;
    
    public MovieInfo getMovieInfo() {
        return mMovieInfo;
    }
    
    public void setMovieInfo(final MovieInfo movieInfo) {
        mMovieInfo = movieInfo;
    }
    
    public Review[] getReviews() {
        return mReviews;
    }
    
    public void setReviews(final Review[] reviews) {
        mReviews = reviews;
    }
    
    public Trailer[] getTrailers() {
        return mTrailers;
    }
    
    public void setTrailers(final Trailer[] trailers) {
        mTrailers = trailers;
    }
}
