package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.model.MoviePoster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesJsonMapper {
    
    public static MoviePoster[] getMoviesList(String objectAsJson) {
        try {
            return getPotersFromResults(objectAsJson);
        } catch (JSONException e) {
            e.printStackTrace();
            return new MoviePoster[0];
        }
    }
    
    private static MoviePoster[] getPotersFromResults(final String objectAsJson) throws JSONException {
        JSONObject responseJson = new JSONObject(objectAsJson);
        JSONArray resultsJsonArray = responseJson.getJSONArray("results");
        MoviePoster[] posters = getPostersArray(resultsJsonArray);
        return posters;
    }
    
    private static MoviePoster[] getPostersArray(final JSONArray resultsJsonArray) throws JSONException {
        MoviePoster[] posters = new MoviePoster[resultsJsonArray.length()];
        for (int i = 0; i < resultsJsonArray.length(); i++) {
            JSONObject posterJson = resultsJsonArray.getJSONObject(i);
            posters[i] = getMoviePoster(posterJson);
        }
        return posters;
    }
    
    private static MoviePoster getMoviePoster(JSONObject posterJson) throws JSONException {
        int id = posterJson.getInt("id");
        String posterPath = NetworkUtils.BASE_POSTER_URL + posterJson.getString("poster_path");
        return new MoviePoster(id, posterPath);
    }
}
