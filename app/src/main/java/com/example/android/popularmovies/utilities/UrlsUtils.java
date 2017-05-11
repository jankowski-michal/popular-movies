package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.moviedetails.domain.entities.MovieInfo;
import com.example.android.popularmovies.moviedetails.domain.entities.Trailer;
import com.example.android.popularmovies.moviesgrid.domain.entities.MoviePoster;

public class UrlsUtils {
    
    public static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    
    private static final String BASE_MOVIE_DETAILS_URL = "https://api.themoviedb.org/3/movie/";
    
    private static final String THE_MOVIEDB_API_KEY = "";
    
    private static final String BASE_QUERY = "?language=en-US&api_key=" + THE_MOVIEDB_API_KEY;
    
    public static final String POPULAR = BASE_MOVIE_DETAILS_URL + "popular?page=1" + BASE_QUERY;
    
    public static final String TOP_RATED = BASE_MOVIE_DETAILS_URL + "top_rated?page=1" + BASE_QUERY;
    
    private static final String TRAILERS = "/videos";
    
    private static final String REVIEWS = "/reviews";
    
    private static final String TRAILER_IMG_BASE_URL = "https://img.youtube.com/vi/";
    
    private static final String TRAILER_BASE_URL = "https://www.youtube.com/watch?v=";
    
    public static String getMovieDetailsUrl(String movieId) {
        return BASE_MOVIE_DETAILS_URL + movieId + BASE_QUERY;
    }
    
    public static String getMovieTrailersUrl(String movieId) {
        return BASE_MOVIE_DETAILS_URL + movieId + TRAILERS + BASE_QUERY;
    }
    
    public static String getMovieReviewsUrl(String movieId) {
        return BASE_MOVIE_DETAILS_URL + movieId + REVIEWS + BASE_QUERY;
    }
    
    public static String getPosterUrl(MoviePoster poster) {
        return BASE_POSTER_URL + poster.getPoster_path();
    }
    
    public static String getTrailerImgUrl(Trailer trailer) {
        return TRAILER_IMG_BASE_URL + trailer.getKey() + "/0.jpg";
    }
    
    public static String getTrailerUrl(Trailer trailer) {
        return TRAILER_BASE_URL + trailer.getKey();
    }
    
    public static String getPosterUrl(MovieInfo details) {
        return BASE_POSTER_URL + details.getPoster_path();
    }
}
