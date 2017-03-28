package com.example.android.popularmovies;

import com.example.android.popularmovies.utilities.NetworkUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MovieDetailsActivity extends AppCompatActivity {
    
    private TextView mTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        mTextView = (TextView) findViewById(R.id.movie_title);
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            String textEntered = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            mTextView.setText(textEntered);
        }
    }
}
