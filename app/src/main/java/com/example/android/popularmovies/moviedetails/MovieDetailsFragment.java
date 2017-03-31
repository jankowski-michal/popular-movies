package com.example.android.popularmovies.moviedetails;

import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MovieDetailsFragment extends Fragment implements MovieDetailsContract.View {
    
    private TextView mTitleTextView;
    
    private TextView mVoteTextView;
    
    private TextView mReleaseDateTextView;
    
    private TextView mOverviewTextView;
    
    private ImageView mThumbnail;
    
    private LinearLayout mLoadingScreen;
    
    private LinearLayout mErrorScreen;
    
    private LinearLayout mMovieDetailsScreen;
    
    private MovieDetailsContract.Presenter mPresenter;
    
    public MovieDetailsFragment() {
        // Required empty public constructor
    }
    
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.movie_details_fragment, container, false);
        initViews(root);
        showLoadingScreen();
        return root;
    }
    
    @Override
    public void setPresenter(@NonNull MovieDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }
    
    @Override
    public void setTitle(final String title) {
        mTitleTextView.setText(title);
    }
    
    @Override
    public void setOverview(final String overview) {
        mOverviewTextView.setText(overview);
    }
    
    @Override
    public void setVote(final Float vote) {
        String voteAppended = getString(R.string.movie_details_vote) + vote;
        mVoteTextView.setText(voteAppended);
    }
    
    @Override
    public void setReleaseDate(final String releaseDate) {
        String releaseDateAppended = getString(R.string.movie_details_release) + releaseDate;
        mReleaseDateTextView.setText(releaseDateAppended);
    }
    
    @Override
    public void setPoster(final String posterUrl) {
        Picasso.with(getContext())
                .load(posterUrl)
                .into(mThumbnail);
    }
    
    @Override
    public void showMovieDetails() {
        mErrorScreen.setVisibility(View.GONE);
        mLoadingScreen.setVisibility(View.GONE);
        mMovieDetailsScreen.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void showLoadingScreen() {
        mErrorScreen.setVisibility(View.GONE);
        mMovieDetailsScreen.setVisibility(View.GONE);
        mLoadingScreen.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void showErrorMessage() {
        mLoadingScreen.setVisibility(View.GONE);
        mMovieDetailsScreen.setVisibility(View.GONE);
        mErrorScreen.setVisibility(View.VISIBLE);
        initOnRetryClick();
    }
    
    public static MovieDetailsFragment newInstance() {
        return new MovieDetailsFragment();
    }
    
    private void initOnRetryClick() {
        mErrorScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mPresenter.onRetryClick();
            }
        });
    }
    
    private void initViews(View root) {
        mTitleTextView = (TextView) root.findViewById(R.id.movie_title);
        mVoteTextView = (TextView) root.findViewById(R.id.vote_average);
        mReleaseDateTextView = (TextView) root.findViewById(R.id.release_date);
        mOverviewTextView = (TextView) root.findViewById(R.id.overview);
        mThumbnail = (ImageView) root.findViewById(R.id.poster_thumbnail);
        mLoadingScreen = (LinearLayout) root.findViewById(R.id.loading_data_layout);
        mErrorScreen = (LinearLayout) root.findViewById(R.id.error_layout);
        mMovieDetailsScreen = (LinearLayout) root.findViewById(R.id.movie_details_screen);
    }
}
