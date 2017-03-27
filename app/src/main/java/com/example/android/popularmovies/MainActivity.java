package com.example.android.popularmovies;

import com.example.android.popularmovies.recycler.MoviesAdapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    
    private static final int COLUMNS = 2;
    
    public String[] mock = {
            "Movie 1",
            "Movie 2",
            "Movie 3",
            "Movie 4",
            "Movie 5",
            "Movie 6",
            "Movie 7",
            "Movie 8"
    };
    
    RecyclerView mRecyclerView;
    
    MoviesAdapter mAdapter;
    
    RecyclerView.LayoutManager mLayoutManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }
    
    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        initLayoutManager();
        initAdapter();
    }
    
    private void initLayoutManager() {
        mLayoutManager = new GridLayoutManager(this, COLUMNS);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
    
    private void initAdapter() {
        mAdapter = new MoviesAdapter(mock);
        mRecyclerView.setAdapter(mAdapter);
    }
}
