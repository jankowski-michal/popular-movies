package com.example.android.popularmovies.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import static com.example.android.popularmovies.utilities.NetworkUtils.buildUrl;
import static com.example.android.popularmovies.utilities.NetworkUtils.getResponseFromHttpUrl;

public class DownloadAsyncTask extends AsyncTask<String, Void, String> {
    
    private static final String TAG = DownloadAsyncTask.class.getSimpleName();
    
    private final DownloadListener mDownloadListener;
    
    public DownloadAsyncTask(final DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    
    @Override
    protected String doInBackground(final String... params) {
        if (params.length == 0) {
            return null;
        }
        Log.d(TAG, "doInBackground for url: " + params[0]);
        URL url = buildUrl(params[0]);
        try {
            return getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    protected void onPostExecute(final String responseJson) {
        mDownloadListener.onDownloaded(responseJson);
    }
}
