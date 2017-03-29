package com.example.android.popularmovies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import android.support.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoviePoster {
    
    private final int id;
    
    @Nullable
    private final String poster_path;
    
    public MoviePoster(final int id, @Nullable final String poster_path) {
        this.id = id;
        this.poster_path = poster_path;
    }
    
    public int getId() {
        return id;
    }
    
    @Nullable
    public String getPoster_path() {
        return poster_path;
    }
}
