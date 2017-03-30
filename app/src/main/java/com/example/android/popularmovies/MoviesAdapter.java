package com.example.android.popularmovies;

import com.example.android.popularmovies.model.MoviePoster;
import com.example.android.popularmovies.utilities.ImagesDimensionUtils;
import com.example.android.popularmovies.utilities.UrlsUtils;
import com.squareup.picasso.Picasso;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    
    private static final String TAG = MoviesAdapter.class.getSimpleName();
    
    private final int mViewWidth;
    
    private final int mViewHeight;
    
    private MoviePoster[] mPosters;
    
    private View.OnClickListener mOnClickListener;
    
    public MoviesAdapter(@NonNull final MoviePoster[] posters, Resources res, View.OnClickListener onClickListener) {
        mPosters = posters;
        mViewWidth = ImagesDimensionUtils.getGridElementWidth(res);
        mViewHeight = ImagesDimensionUtils.getGridElementHeight(res);
        mOnClickListener = onClickListener;
    }
    
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final boolean attachToRoot = false;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_entry, parent, attachToRoot);
        view.setOnClickListener(mOnClickListener);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(final MoviesAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder, position: " + position);
        MoviePoster poster = mPosters[position];
        if (poster != null) {
            holder.setPoster(poster);
        }
    }
    
    @Override
    public int getItemCount() {
        return mPosters.length;
    }
    
    public MoviePoster getPoster(int position) {
        return mPosters[position];
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        
        private final ImageView mMoviePoster;
        
        ViewHolder(final View view) {
            super(view);
            mMoviePoster = (ImageView) view.findViewById(R.id.movie_poster);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mViewWidth, mViewHeight);
            mMoviePoster.setLayoutParams(layoutParams);
        }
        
        void setPoster(@NonNull MoviePoster poster) {
            Log.d(TAG, "Picasso load: " + poster.getPoster_path());
            Picasso.with(itemView.getContext())
                    .load(UrlsUtils.getPosterUrl(poster))
                    .into(mMoviePoster);
        }
    }
}
