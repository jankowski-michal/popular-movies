package com.example.android.popularmovies.asynctasks;

import com.example.android.popularmovies.utilities.mapping.MovieDetailsJsonMapper;
import com.example.android.popularmovies.utilities.mapping.MovieListJsonMapper;

import android.os.AsyncTask;
import android.util.Log;

public class MappingAsyncTask extends AsyncTask<MappingAsyncTask.MappingRequest, Void, Object> {
    
    public static final int MAP_TO_MOVIES_LIST = 0;
    
    public static final int MAP_TO_MOVIE_DETAILS = 1;
    
    private static final String TAG = MappingAsyncTask.class.getSimpleName();
    
    private MappingListener mMapperListener;
    
    public MappingAsyncTask(final MappingListener mapperListener) {
        mMapperListener = mapperListener;
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    
    @Override
    protected Object doInBackground(final MappingRequest... params) {
        if (params.length == 0) {
            return null;
        }
        MappingRequest mappingRequest = params[0];
        if (mappingRequest.responseJson == null) {
            return null;
        }
        return map(mappingRequest);
    }
    
    @Override
    protected void onPostExecute(final Object object) {
        mMapperListener.onMapped(object);
    }
    
    private Object map(MappingRequest mappingRequest) {
        Log.d(TAG, "doInBackground for responseJson: " + mappingRequest.responseJson);
        switch (mappingRequest.MAP_TO) {
            case MAP_TO_MOVIES_LIST:
                return MovieListJsonMapper.map(mappingRequest.responseJson);
            case MAP_TO_MOVIE_DETAILS:
                return MovieDetailsJsonMapper.map(mappingRequest.responseJson);
            default:
                return null;
        }
    }
    
    public static class MappingRequest {
        
        private String responseJson;
        
        private int MAP_TO;
        
        public MappingRequest(final String responseJson, final int MAP_TO) {
            this.responseJson = responseJson;
            this.MAP_TO = MAP_TO;
        }
    }
}
