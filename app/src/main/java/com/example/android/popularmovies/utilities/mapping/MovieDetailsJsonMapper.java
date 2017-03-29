package com.example.android.popularmovies.utilities.mapping;

import com.example.android.popularmovies.model.MovieDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MovieDetailsJsonMapper {
    
    public static MovieDetails map(String objectAsJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(objectAsJson, MovieDetails.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new MovieDetails();
        }
    }
}
