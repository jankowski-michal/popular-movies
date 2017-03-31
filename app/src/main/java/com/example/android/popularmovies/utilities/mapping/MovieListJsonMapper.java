package com.example.android.popularmovies.utilities.mapping;

import com.example.android.popularmovies.model.MoviePoster;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MovieListJsonMapper {
    
    public static MoviePoster[] map(String objectAsJson) {
        try {
            JSONObject responseJson = new JSONObject(objectAsJson);
            JSONArray resultsJsonArray = responseJson.getJSONArray("results");
            return getPotersFromResults(resultsJsonArray);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return new MoviePoster[0];
    }
    
    private static MoviePoster[] getPotersFromResults(final JSONArray resultsJsonArray) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(resultsJsonArray.toString(), MoviePoster[].class);
    }
}
