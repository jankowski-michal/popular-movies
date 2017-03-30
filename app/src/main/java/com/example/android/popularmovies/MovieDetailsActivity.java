package com.example.android.popularmovies;

import com.example.android.popularmovies.asynctasks.DownloadAsyncTask;
import com.example.android.popularmovies.asynctasks.DownloadListener;
import com.example.android.popularmovies.asynctasks.MappingAsyncTask;
import com.example.android.popularmovies.asynctasks.MappingListener;
import com.example.android.popularmovies.model.MovieDetails;
import com.example.android.popularmovies.utilities.UrlsUtils;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailsActivity extends AppCompatActivity implements DownloadListener, MappingListener {
    
    private DownloadAsyncTask mDownloadAsyncTask;
    
    private MappingAsyncTask mMappingAsyncTask;
    
    private TextView mTitleTextView;
    
    private TextView mVoteTextView;
    
    private TextView mReleaseDateTextView;
    
    private TextView mOverviewTextView;
    
    private ImageView mThumbnail;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_movie_details);
        initViews();
        loadIntent();
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    
    @Override
    public void onDownloaded(final String responseJson) {
        requestMappingMovieDetails(responseJson);
    }
    
    @Override
    public void onMapped(final Object object) {
        MovieDetails movieDetails = (MovieDetails) object;
        
        String releaseDate = getString(R.string.movie_details_release) + movieDetails.getRelease_date();
        String vote = getString(R.string.movie_details_vote) + movieDetails.getVote_average();
        String overview = getString(R.string.movie_details_overview) + movieDetails.getOverview();
        String posterUrl = UrlsUtils.getPosterUrl(movieDetails);
        
        mTitleTextView.setText(movieDetails.getTitle());
        mReleaseDateTextView.setText(releaseDate);
        mVoteTextView.setText(vote);
        mOverviewTextView.setText(overview);
        setPoster(posterUrl);
    }
    
    void setPoster(@NonNull String posterUrl) {
        Picasso.with(this)
                .load(posterUrl)
                .into(mThumbnail);
    }
    
    private void loadIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String movieId = intent.getStringExtra(Intent.EXTRA_TEXT);
            downloadMovieDetails(Integer.parseInt(movieId));
        }
    }
    
    private void initViews() {
        mTitleTextView = (TextView) findViewById(R.id.movie_title);
        mVoteTextView = (TextView) findViewById(R.id.vote_average);
        mReleaseDateTextView = (TextView) findViewById(R.id.release_date);
        mOverviewTextView = (TextView) findViewById(R.id.overview);
        mThumbnail = (ImageView) findViewById(R.id.poster_thumbnail);
    }
    
    private void downloadMovieDetails(int movieId) {
        if (mDownloadAsyncTask != null) {
            mDownloadAsyncTask.cancel(true);
        }
        mDownloadAsyncTask = new DownloadAsyncTask(this);
        String url = UrlsUtils.getMovieDetailsUrl(movieId);
        mDownloadAsyncTask.execute(url);
    }
    
    private void requestMappingMovieDetails(final String responseJson) {
        if (mMappingAsyncTask != null) {
            mMappingAsyncTask.cancel(true);
        }
        MappingAsyncTask.MappingRequest mappingRequest = new MappingAsyncTask.MappingRequest(responseJson, MappingAsyncTask.MAP_TO_MOVIE_DETAILS);
        mMappingAsyncTask = new MappingAsyncTask(this);
        mMappingAsyncTask.execute(mappingRequest);
    }
}
