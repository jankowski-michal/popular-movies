package com.example.android.popularmovies.moviedetails;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.moviedetails.domain.entities.Review;
import com.example.android.popularmovies.moviedetails.domain.entities.Trailer;
import com.example.android.popularmovies.moviedetails.view.ReviewAdapter;
import com.example.android.popularmovies.moviedetails.view.TrailerAdapter;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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
    
    private ViewPager mReviewsViewPager;
    
    private ViewPager mTrailersViewPager;
    
    private ReviewAdapter mReviewAdapter;
    
    private TrailerAdapter mTrailerAdapter;
    
    private FloatingActionButton mFavouriteButton;
    
    private SwipeRefreshLayout mSwipeContainer;
    
    private View noReviewsMessage, noTrailersMessage;
    
    private boolean favourite;
    
    public MovieDetailsFragment() {
        // Required empty public constructor
    }
    
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.movie_details_fragment, container, false);
        initViews(root);
        mReviewsViewPager = (ViewPager) root.findViewById(R.id.reviews);
        mTrailersViewPager = (ViewPager) root.findViewById(R.id.trailers);
        mFavouriteButton = (FloatingActionButton) root.findViewById(R.id.favourite_button);
        mSwipeContainer = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        noReviewsMessage = root.findViewById(R.id.no_reviews_message);
        noTrailersMessage = root.findViewById(R.id.no_trailers_message);
        mFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mPresenter.onFavouriteClick();
            }
        });
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onRefreshPull();
            }
        });
        showLoadingScreen();
        return root;
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFavourite(favourite);
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
    
    public void showReviews(Review[] reviews) {
        noReviewsMessage.setVisibility(View.GONE);
        mReviewAdapter = new ReviewAdapter(reviews, getContext());
        mReviewsViewPager.setAdapter(mReviewAdapter);
    }
    
    public void showTrailers(Trailer[] trailers) {
        noTrailersMessage.setVisibility(View.GONE);
        mTrailerAdapter = new TrailerAdapter(trailers, getContext(), mPresenter);
        mTrailersViewPager.setAdapter(mTrailerAdapter);
    }
    
    @Override
    public void setFavourite(final boolean setToFavourite) {
        favourite = setToFavourite;
        if (mFavouriteButton == null) {
            return;
        }
        if (setToFavourite) {
            mFavouriteButton.setImageResource(R.drawable.favourite);
        } else {
            mFavouriteButton.setImageResource(R.drawable.notfavourite);
        }
    }
    
    @Override
    public void setEmptyTrailers() {
        noTrailersMessage.setVisibility(View.VISIBLE);
        mTrailersViewPager.setVisibility(View.GONE);
    }
    
    @Override
    public void setEmptyReviews() {
        noReviewsMessage.setVisibility(View.VISIBLE);
        mReviewsViewPager.setVisibility(View.GONE);
    }
    
    @Override
    public void dismissRefresh() {
        mSwipeContainer.setRefreshing(false);
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
