package com.example.android.popularmovies.data.local.db;

import com.example.android.popularmovies.moviedetails.domain.entities.MovieDetails;
import com.example.android.popularmovies.moviedetails.domain.entities.MovieInfo;
import com.example.android.popularmovies.moviedetails.domain.entities.Review;
import com.example.android.popularmovies.moviedetails.domain.entities.Trailer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.android.popularmovies.data.local.db.contracts.DbContract.MovieInfoEntry;
import static com.example.android.popularmovies.data.local.db.contracts.DbContract.ReviewEntry;
import static com.example.android.popularmovies.data.local.db.contracts.DbContract.TrailerEntry;

public class DatabaseConnector {
    
    private SQLiteDatabase mDataBase;
    
    public DatabaseConnector(Context context) {
        MovieDbHelper helper = new MovieDbHelper(context);
        mDataBase = helper.getWritableDatabase();
    }
    
    public void save(MovieDetails movieDetails) {
        save(movieDetails.getMovieInfo());
        
        if (movieDetails.getReviews() != null) {
            for (Review review : movieDetails.getReviews()) {
                save(review, movieDetails.getMovieInfo().getId());
            }
        }
        
        if (movieDetails.getTrailers() != null) {
            for (Trailer trailer : movieDetails.getTrailers()) {
                save(trailer, movieDetails.getMovieInfo().getId());
            }
        }
    }
    
    public Cursor getFavourites() {
        String where = MovieInfoEntry.IS_FAVOURITE + " LIKE 1";
        String[] columns = {
                MovieInfoEntry.COLUMN_ID,
                MovieInfoEntry.COLUMN_POSTER_PATH,
                MovieInfoEntry.IS_FAVOURITE
        };
        return mDataBase.query(
                MovieInfoEntry.TABLE_NAME,
                columns,
                where,
                null, null, null,
                MovieInfoEntry.COLUMN_TITLE
        );
    }
    
    public Cursor getMovieInfo(String movieId) {
        String[] columns = {
                MovieInfoEntry.COLUMN_ID,
                MovieInfoEntry.COLUMN_TITLE,
                MovieInfoEntry.COLUMN_OVERVIEW,
                MovieInfoEntry.COLUMN_POSTER_PATH,
                MovieInfoEntry.COLUMN_VOTE_AVERAGE,
                MovieInfoEntry.COLUMN_RELEASE_DATE,
                MovieInfoEntry.IS_FAVOURITE
        };
        
        String selection = MovieInfoEntry.COLUMN_ID + " LIKE ?";
        
        String[] selectionArgs = {movieId};
        return mDataBase.query(MovieInfoEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                MovieInfoEntry.COLUMN_TITLE);
    }
    
    public Cursor getTrailers(String movieId) {
        String[] columns = {
                TrailerEntry.COLUMN_ID,
                TrailerEntry.COLUMN_NAME,
                TrailerEntry.COLUMN_SITE,
                TrailerEntry.COLUMN_KEY,
                TrailerEntry.COLUMN_MOVIE_ID
        };
        
        String selection = TrailerEntry.COLUMN_MOVIE_ID + " LIKE ?";
        
        String[] selectionArgs = {movieId};
        return mDataBase.query(TrailerEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                TrailerEntry.COLUMN_ID);
    }
    
    public Cursor getReviews(String movieId) {
        String[] columns = {
                ReviewEntry.COLUMN_ID,
                ReviewEntry.COLUMN_AUTHOR,
                ReviewEntry.COLUMN_CONTENT,
                ReviewEntry.COLUMN_URL,
                ReviewEntry.COLUMN_MOVIE_ID
        };
        
        String selection = ReviewEntry.COLUMN_MOVIE_ID + " LIKE ?";
        
        String[] selectionArgs = {movieId};
        return mDataBase.query(ReviewEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                ReviewEntry.COLUMN_ID);
    }
    
    public void deleteMovieDetails(String movieId) {
        deleteMovieInfo(movieId);
        deleteMovieTrailers(movieId);
        deleteMovieReviews(movieId);
    }
    
    private int deleteMovieReviews(final String movieId) {
        return mDataBase.delete(ReviewEntry.TABLE_NAME,
                TrailerEntry.COLUMN_MOVIE_ID + "=" + movieId, null);
    }
    
    private void deleteMovieTrailers(final String movieId) {
        mDataBase.delete(TrailerEntry.TABLE_NAME,
                TrailerEntry.COLUMN_MOVIE_ID + "=" + movieId, null);
    }
    
    private void deleteMovieInfo(final String movieId) {
        mDataBase.delete(MovieInfoEntry.TABLE_NAME,
                MovieInfoEntry.COLUMN_ID + "=" + movieId, null);
    }
    
    private void save(MovieInfo movieInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieInfoEntry.COLUMN_ID, movieInfo.getId());
        contentValues.put(MovieInfoEntry.COLUMN_TITLE, movieInfo.getTitle());
        contentValues.put(MovieInfoEntry.COLUMN_OVERVIEW, movieInfo.getOverview());
        contentValues.put(MovieInfoEntry.COLUMN_POSTER_PATH, movieInfo.getPoster_path());
        contentValues.put(MovieInfoEntry.COLUMN_VOTE_AVERAGE, movieInfo.getVote_average());
        contentValues.put(MovieInfoEntry.COLUMN_RELEASE_DATE, movieInfo.getRelease_date());
        contentValues.put(MovieInfoEntry.IS_FAVOURITE, movieInfo.isFavourite());
        mDataBase.insert(MovieInfoEntry.TABLE_NAME, null, contentValues);
    }
    
    private void save(Trailer trailer, String movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TrailerEntry.COLUMN_ID, trailer.getId());
        contentValues.put(TrailerEntry.COLUMN_NAME, trailer.getName());
        contentValues.put(TrailerEntry.COLUMN_SITE, trailer.getSite());
        contentValues.put(TrailerEntry.COLUMN_KEY, trailer.getKey());
        contentValues.put(TrailerEntry.COLUMN_MOVIE_ID, movieId);
        mDataBase.insert(TrailerEntry.TABLE_NAME, null, contentValues);
    }
    
    private void save(Review review, String movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ReviewEntry.COLUMN_ID, review.getId());
        contentValues.put(ReviewEntry.COLUMN_AUTHOR, review.getAuthor());
        contentValues.put(ReviewEntry.COLUMN_CONTENT, review.getContent());
        contentValues.put(ReviewEntry.COLUMN_URL, review.getUrl());
        contentValues.put(ReviewEntry.COLUMN_MOVIE_ID, movieId);
        mDataBase.insert(ReviewEntry.TABLE_NAME, null, contentValues);
    }
}
