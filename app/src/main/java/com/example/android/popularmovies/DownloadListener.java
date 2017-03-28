package com.example.android.popularmovies;

import com.example.android.popularmovies.model.MoviePoster;

public interface DownloadListener {
    
    void onDownloaded(MoviePoster[] posters);
}
