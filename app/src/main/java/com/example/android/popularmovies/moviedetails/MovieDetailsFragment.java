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
import android.widget.TextView;

//todo: verify differences between fragment and v4 app fragment

public class MovieDetailsFragment extends Fragment implements MovieDetailsContract.View {
    
    private TextView mTitleTextView;
    
    private TextView mVoteTextView;
    
    private TextView mReleaseDateTextView;
    
    private TextView mOverviewTextView;
    
    private ImageView mThumbnail;
    
    private MovieDetailsContract.Presenter mPresenter;
    
    public MovieDetailsFragment() {
        // Required empty public constructor
    }
    
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.movie_details_fragment, container, false);
        initViews(root);
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
        String overviewAppended = getString(R.string.movie_details_overview) + overview;
        mOverviewTextView.setText(overviewAppended);
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
    
    public static MovieDetailsFragment newInstance() {
        return new MovieDetailsFragment();
    }
    
    private void initViews(View root) {
        mTitleTextView = (TextView) root.findViewById(R.id.movie_title);
        mVoteTextView = (TextView) root.findViewById(R.id.vote_average);
        mReleaseDateTextView = (TextView) root.findViewById(R.id.release_date);
        mOverviewTextView = (TextView) root.findViewById(R.id.overview);
        mThumbnail = (ImageView) root.findViewById(R.id.poster_thumbnail);
    }
}
