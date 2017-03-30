package com.example.android.popularmovies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDetails {
    
    String title;
    
    String overview;
    
    String poster_path;
    
    String release_date;
    
    float vote_average;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public void setOverview(final String overview) {
        this.overview = overview;
    }
    
    public String getPoster_path() {
        return poster_path;
    }
    
    public void setPoster_path(final String posterPath) {
        this.poster_path = posterPath;
    }
    
    public String getRelease_date() {
        return release_date;
    }
    
    public void setRelease_date(final String release_date) {
        this.release_date = release_date;
    }
    
    public float getVote_average() {
        return vote_average;
    }
    
    public void setVote_average(final float vote_average) {
        this.vote_average = vote_average;
    }
}
