package com.example.android.popularmovies.model;

import android.support.annotation.Nullable;

public class MoviePoster {
    
    private final int id;
    
    @Nullable
    private final String posterPath;
    
    public MoviePoster(final int id, @Nullable final String posterPath) {
        this.id = id;
        this.posterPath = posterPath;
    }
    
    public int getId() {
        return id;
    }
    
    @Nullable
    public String getPosterPath() {
        return posterPath;
    }
}
