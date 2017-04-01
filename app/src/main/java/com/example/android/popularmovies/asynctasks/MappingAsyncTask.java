package com.example.android.popularmovies.asynctasks;

import com.example.android.popularmovies.utilities.mapping.MovieDetailsJsonMapper;
import com.example.android.popularmovies.utilities.mapping.MovieListJsonMapper;

import android.os.AsyncTask;
import android.util.Log;

public class MappingAsyncTask extends AsyncTask<MappingAsyncTask.MappingRequest, Void, Object> {
    
    private static final int MAP_TO_MOVIES_LIST = 0;
    
    private static final int MAP_TO_MOVIE_DETAILS = 1;
    
    private static final String TAG = MappingAsyncTask.class.getSimpleName();
    
    private final MappingListener mMapperListener;
    
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
                throw new IllegalArgumentException("mappingRequest.MAP_TO is incorrect");
        }
    }
    
    public static class MappingRequest {
        
        private final String responseJson;
        
        private final int MAP_TO;
        
        private MappingRequest(final String responseJson, final int MAP_TO) {
            this.responseJson = responseJson;
            this.MAP_TO = MAP_TO;
        }
        
        public static MappingRequest moviesListRequest(final String responseJson) {
            return new MappingRequest(responseJson, MAP_TO_MOVIES_LIST);
        }
        
        public static MappingRequest movieDetailsRequest(final String responseJson) {
            return new MappingRequest(responseJson, MAP_TO_MOVIE_DETAILS);
        }
    }
}
