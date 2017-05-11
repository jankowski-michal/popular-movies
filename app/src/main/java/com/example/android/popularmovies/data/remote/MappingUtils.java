package com.example.android.popularmovies.data.remote;

import com.example.android.popularmovies.data.local.db.contracts.DbContract;
import com.example.android.popularmovies.moviedetails.domain.entities.MovieInfo;
import com.example.android.popularmovies.moviedetails.domain.entities.Review;
import com.example.android.popularmovies.moviedetails.domain.entities.Trailer;
import com.example.android.popularmovies.moviesgrid.domain.entities.MoviePoster;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;

import java.io.IOException;

public class MappingUtils {
    
    //todo: make methods not static
    
    public static MovieInfo mapMovieInfo(String objectAsJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(objectAsJson, MovieInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new MovieInfo();
        }
    }
    
    public static MovieInfo mapMovieInfo(Cursor cursor) {
        cursor.moveToPosition(0);
        MovieInfo movieInfo = new MovieInfo();
        movieInfo.setPoster_path(cursor.getString(cursor.getColumnIndex(DbContract.MovieInfoEntry.COLUMN_POSTER_PATH)));
        movieInfo.setId(cursor.getString(cursor.getColumnIndex(DbContract.MovieInfoEntry.COLUMN_ID)));
        movieInfo.setTitle(cursor.getString(cursor.getColumnIndex(DbContract.MovieInfoEntry.COLUMN_TITLE)));
        movieInfo.setOverview(cursor.getString(cursor.getColumnIndex(DbContract.MovieInfoEntry.COLUMN_OVERVIEW)));
        movieInfo.setRelease_date(cursor.getString(cursor.getColumnIndex(DbContract.MovieInfoEntry.COLUMN_RELEASE_DATE)));
        movieInfo.setFavourite("1".equals(cursor.getString(cursor.getColumnIndex(DbContract.MovieInfoEntry.IS_FAVOURITE))));
        movieInfo.setVote_average(Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbContract.MovieInfoEntry.COLUMN_VOTE_AVERAGE))));
        return movieInfo;
    }
    
    public static Review[] mapReviews(Cursor cursor) {
        Review[] reviews = new Review[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Review review = new Review();
            review.setId(cursor.getString(cursor.getColumnIndex(DbContract.ReviewEntry.COLUMN_ID)));
            review.setAuthor(cursor.getString(cursor.getColumnIndex(DbContract.ReviewEntry.COLUMN_AUTHOR)));
            review.setContent(cursor.getString(cursor.getColumnIndex(DbContract.ReviewEntry.COLUMN_CONTENT)));
            review.setUrl(cursor.getString(cursor.getColumnIndex(DbContract.ReviewEntry.COLUMN_URL)));
            reviews[i] = review;
        }
        return reviews;
    }
    
    public static Trailer[] mapTrailers(Cursor cursor) {
        Trailer[] trailers = new Trailer[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Trailer trailer = new Trailer();
            trailer.setId(cursor.getString(cursor.getColumnIndex(DbContract.TrailerEntry.COLUMN_ID)));
            trailer.setKey(cursor.getString(cursor.getColumnIndex(DbContract.TrailerEntry.COLUMN_KEY)));
            trailer.setName(cursor.getString(cursor.getColumnIndex(DbContract.TrailerEntry.COLUMN_NAME)));
            trailer.setSite(cursor.getString(cursor.getColumnIndex(DbContract.TrailerEntry.COLUMN_SITE)));
            trailers[i] = trailer;
        }
        return trailers;
    }
    
    public static MoviePoster[] mapMoviePosters(Cursor cursor) {
        MoviePoster[] posters = new MoviePoster[cursor.getCount()];
        for (int position = 0; position < cursor.getCount(); position++) {
            cursor.moveToPosition(position);
            MoviePoster poster = new MoviePoster();
            poster.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbContract.MovieInfoEntry.COLUMN_ID))));
            poster.setPoster_path(cursor.getString(cursor.getColumnIndex(DbContract.MovieInfoEntry.COLUMN_POSTER_PATH)));
            posters[position] = poster;
        }
        return posters;
    }
    
    public static MoviePoster[] mapMoviePosters(String objectAsJson) throws IOException, JSONException {
        JSONObject responseJson = new JSONObject(objectAsJson);
        JSONArray resultsJsonArray = responseJson.getJSONArray("results");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(resultsJsonArray.toString(), MoviePoster[].class);
    }
    
    public static Trailer[] mapTrailers(String objectAsJson) throws JSONException, IOException {
        JSONObject responseJson = new JSONObject(objectAsJson);
        JSONArray resultsJsonArray = responseJson.getJSONArray("results");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(resultsJsonArray.toString(), Trailer[].class);
    }
    
    public static Review[] mapReviews(String objectAsJson) throws IOException, JSONException {
        JSONObject responseJson = new JSONObject(objectAsJson);
        JSONArray resultsJsonArray = responseJson.getJSONArray("results");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(resultsJsonArray.toString(), Review[].class);
    }
}
