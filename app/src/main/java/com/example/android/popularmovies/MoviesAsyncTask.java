package com.example.android.popularmovies;

import com.example.android.popularmovies.model.MoviePoster;
import com.example.android.popularmovies.utilities.MoviesJsonMapper;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import static com.example.android.popularmovies.utilities.NetworkUtils.buildUrl;
import static com.example.android.popularmovies.utilities.NetworkUtils.getResponseFromHttpUrl;

public class MoviesAsyncTask extends AsyncTask<String, Void, MoviePoster[]> {
    
    private static final String TAG = MoviesAsyncTask.class.getSimpleName();
    
    private DownloadListener mDownloadListener;
    
    public MoviesAsyncTask(final DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    
    @Override
    protected MoviePoster[] doInBackground(final String... params) {
        if (params.length == 0) {
            return new MoviePoster[0];
        }
        Log.d(TAG, "doInBackground for url: " + params[0]);
        URL url = buildUrl(params[0]);
        try {
            String responseJson = getResponseFromHttpUrl(url);
            return MoviesJsonMapper.getMoviesList(responseJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MoviePoster[0];
    }
    
    @Override
    protected void onPostExecute(final MoviePoster[] posters) {
        mDownloadListener.onDownload(posters);
    }
}
