package com.example.android.popularmovies.moviedetails.view;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.moviedetails.MovieDetailsContract;
import com.example.android.popularmovies.moviedetails.domain.entities.Trailer;
import com.example.android.popularmovies.utilities.UrlsUtils;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TrailerAdapter extends PagerAdapter {
    
    private final Trailer[] mTrailers;
    
    private Context mContext;
    
    private MovieDetailsContract.Presenter mPresenter;
    
    public TrailerAdapter(@NonNull final Trailer[] trailers, Context context, MovieDetailsContract.Presenter presenter) {
        mTrailers = trailers;
        mContext = context;
        mPresenter = presenter;
    }
    
    @Override
    public int getCount() {
        return mTrailers.length;
    }
    
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.movie_details_trailer_entry, container, false);
        setReview(position, view);
        container.addView(view);
        return view;
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        return mTrailers[position].getName();
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
    
    void setReview(final int position, ViewGroup view) {
        ImageView trailerImage = (ImageView) view.findViewById(R.id.trailer_image);
        Trailer trailer = mTrailers[position];
        trailerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mPresenter.onTrailerClick(mTrailers[position]);
            }
        });
        Picasso.with(mContext)
                .load(UrlsUtils.getTrailerImgUrl(trailer))
                .into(trailerImage);
    }
}