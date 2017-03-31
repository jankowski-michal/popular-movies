package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.model.MovieDetails;
import com.example.android.popularmovies.model.MoviePoster;

public class UrlsUtils {
    
    public static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    
    private static final String BASE_MOVIE_DETAILS_URL = "https://api.themoviedb.org/3/movie/";
    
    private static final String THE_MOVIEDB_API_KEY = "";
    
    private static final String BASE_QUERY = "?language=en-US&api_key=" + THE_MOVIEDB_API_KEY;
    
    public static final String POPULAR = BASE_MOVIE_DETAILS_URL + "popular?page=1" + BASE_QUERY;
    
    public static final String TOP_RATED = BASE_MOVIE_DETAILS_URL + "top_rated?page=1" + BASE_QUERY;
    
    public static String getMovieDetailsUrl(String movieId) {
        return BASE_MOVIE_DETAILS_URL + movieId + BASE_QUERY;
    }
    
    public static String getPosterUrl(MoviePoster poster) {
        return BASE_POSTER_URL + poster.getPoster_path();
    }
    
    public static String getPosterUrl(MovieDetails details) {
        return BASE_POSTER_URL + details.getPoster_path();
    }
}
