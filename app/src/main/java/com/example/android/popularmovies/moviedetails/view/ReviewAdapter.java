package com.example.android.popularmovies.moviedetails.view;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.moviedetails.domain.entities.Review;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReviewAdapter extends PagerAdapter {
    
    private final Review[] mReviews;
    
    private Context mContext;
    
    public ReviewAdapter(@NonNull final Review[] reviews, Context context) {
        mReviews = reviews;
        mContext = context;
    }
    
    @Override
    public int getCount() {
        return mReviews.length;
    }
    
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.movie_details_review_entry, container, false);
        setReview(position, view);
        container.addView(view);
        return view;
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        return mReviews[position].getAuthor();
    }
    
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
    
    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
    
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    
    void setReview(int position, ViewGroup view) {
        TextView author = (TextView) view.findViewById(R.id.author);
        TextView content = (TextView) view.findViewById(R.id.content);
        Review review = mReviews[position];
        author.setText(review.getAuthor());
        content.setText(review.getContent());
    }
}