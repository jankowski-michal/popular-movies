package com.example.android.popularmovies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import android.support.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoviePoster {
    
    private int id;
    
    @Nullable
    private String poster_path;
    
    public MoviePoster() {
        
    }
    
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
