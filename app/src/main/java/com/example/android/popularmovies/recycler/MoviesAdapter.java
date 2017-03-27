package com.example.android.popularmovies.recycler;

import com.example.android.popularmovies.R;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    
    private static final String TAG = MoviesAdapter.class.getSimpleName();
    
    private String[] mMoviesSet;
    
    public MoviesAdapter(@NonNull final String[] moviesSet) {
        mMoviesSet = moviesSet;
    }
    
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final boolean attachToRoot = false;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_entry, parent, attachToRoot);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(final MoviesAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder, position: " + position);
        holder.setTitle(mMoviesSet[position]);
    }
    
    @Override
    public int getItemCount() {
        return mMoviesSet.length;
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        
        private final TextView mTitle;
        
        ViewHolder(final View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.movie_title);
        }
        
        void setTitle(@NonNull String title) {
            mTitle.setText(title);
        }
    }
}
