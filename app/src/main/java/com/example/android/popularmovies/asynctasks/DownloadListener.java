package com.example.android.popularmovies.asynctasks;

public interface DownloadListener {
    
    void onDownloaded(String responseJson);
}
