package com.example.android.popularmovies.data.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.popularmovies.data.local.db.contracts.DbContract.MovieInfoEntry;
import static com.example.android.popularmovies.data.local.db.contracts.DbContract.ReviewEntry;
import static com.example.android.popularmovies.data.local.db.contracts.DbContract.TrailerEntry;

public class MovieDbHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "moviedetails.db";
    
    private static final int DATABASE_VERSION = 2;
    
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(final SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_DETAILS_TABLE = "CREATE TABLE " + MovieInfoEntry.TABLE_NAME + " (" +
                MovieInfoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieInfoEntry.COLUMN_ID + " TEXT NOT NULL, " +
                MovieInfoEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieInfoEntry.COLUMN_OVERVIEW + " TEXT NOT NULL,  " +
                MovieInfoEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MovieInfoEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieInfoEntry.COLUMN_VOTE_AVERAGE + " FLOAT NOT NULL, " +
                MovieInfoEntry.IS_FAVOURITE + " BOOLEAN" +
                "); ";
        
        final String SQL_CREATE_REVIEWS_TABLE = "CREATE TABLE " + ReviewEntry.TABLE_NAME + " (" +
                ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ReviewEntry.COLUMN_ID + " TEXT NOT NULL, " +
                ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL,  " +
                ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                ReviewEntry.COLUMN_URL + " TEXT NOT NULL, " +
                TrailerEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL" +
                "); ";
        
        final String SQL_CREATE_TRAILERS_TABLE = "CREATE TABLE " + TrailerEntry.TABLE_NAME + " (" +
                TrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TrailerEntry.COLUMN_ID + " TEXT NOT NULL, " +
                TrailerEntry.COLUMN_NAME + " TEXT NOT NULL,  " +
                TrailerEntry.COLUMN_SITE + " TEXT NOT NULL,  " +
                TrailerEntry.COLUMN_KEY + " TEXT NOT NULL,  " +
                TrailerEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL" +
                "); ";
        
        db.execSQL(SQL_CREATE_MOVIE_DETAILS_TABLE);
        db.execSQL(SQL_CREATE_REVIEWS_TABLE);
        db.execSQL(SQL_CREATE_TRAILERS_TABLE);
    }
    
    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieInfoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReviewEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrailerEntry.TABLE_NAME);
        onCreate(db);
    }
}
