package com.example.android.popularmovies.data.local.db.contracts;

import android.provider.BaseColumns;

/**
 * Created by mjan on 29.04.17.
 */
public class DbContract {
    
    public static final class MovieInfoEntry implements BaseColumns {
        
        public static final String TABLE_NAME = "movie_details";
        
        public static final String COLUMN_ID = "id";
        
        public static final String COLUMN_TITLE = "title";
        
        public static final String COLUMN_OVERVIEW = "overview";
        
        public static final String COLUMN_POSTER_PATH = "poster_path";
        
        public static final String COLUMN_RELEASE_DATE = "release_date";
        
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        
        public static final String IS_FAVOURITE = "isFavourite";
    }
    
    public static final class ReviewEntry implements BaseColumns {
        
        public static final String TABLE_NAME = "reviews";
        
        public static final String COLUMN_ID = "id";
    
        public static final String COLUMN_MOVIE_ID = "movie_id";
        
        public static final String COLUMN_AUTHOR = "author";
        
        public static final String COLUMN_CONTENT = "content";
        
        public static final String COLUMN_URL = "url";
    }
    
    public static final class TrailerEntry implements BaseColumns {
        
        public static final String TABLE_NAME = "trailers";
        
        public static final String COLUMN_ID = "id";
    
        public static final String COLUMN_MOVIE_ID = "movie_id";
        
        public static final String COLUMN_NAME = "name";
        
        public static final String COLUMN_SITE = "site";
    
        public static final String COLUMN_KEY = "key";
    }
}
