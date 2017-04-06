package com.example.android.popularmovies.moviedetails.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trailer {
    
    String id;
    
    String key;
    
    String name;
    
    String site;
    
    public Trailer() {
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(final String key) {
        this.key = key;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getSite() {
        return site;
    }
    
    public void setSite(final String site) {
        this.site = site;
    }
}
