package com.example.android.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    
    static final String BASE = "http://image.tmdb.org/t/p/w185/";
    
    private static final String KEY = "";
    
    public static final String POPULAR =
            "https://api.themoviedb.org/3/movie/popular?page=1&language=en-US&api_key=" + KEY;
    
    public static final String TOP_RATED =
            "https://api.themoviedb.org/3/movie/top_rated?page=1&language=en-US&api_key=" + KEY;
    
    public static URL buildUrl(String url) {
        Uri build = Uri.parse(url);
        try {
            return new URL(build.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
