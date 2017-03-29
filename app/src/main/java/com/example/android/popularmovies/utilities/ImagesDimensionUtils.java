package com.example.android.popularmovies.utilities;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ImagesDimensionUtils {
    
    private static final int COLUMNS_ORIENTATION_PORTRAIT = 2;
    
    private static final int ROWS_ORIENTATION_PORTRAIT = 2;
    
    private static final int COLUMN_ORIENTATION_LANDSCAPE = 3;
    
    private static final int ROWS_ORIENTATION_LANDSCAPE = 1;
    
    private static final int TOOLBAR_HEIGHT_DP = 56;
    
    private static final int STATUS_BAR_HEIGHT_DP = 24;
    
    public static int getColumnsForOrientation(int orientation) {
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return COLUMNS_ORIENTATION_PORTRAIT;
            case Configuration.ORIENTATION_LANDSCAPE:
                return COLUMN_ORIENTATION_LANDSCAPE;
            default:
                return COLUMNS_ORIENTATION_PORTRAIT;
        }
    }
    
    private static int getRowsForOrientation(int orientation) {
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return ROWS_ORIENTATION_PORTRAIT;
            case Configuration.ORIENTATION_LANDSCAPE:
                return ROWS_ORIENTATION_LANDSCAPE;
            default:
                return ROWS_ORIENTATION_PORTRAIT;
        }
    }
    
    public static int getGridElementWidth(Resources res) {
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        int orientation = res.getConfiguration().orientation;
        return displayMetrics.widthPixels / getColumnsForOrientation(orientation);
    }
    
    public static int getGridElementHeight(Resources res) {
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        int toolbarHeight = (TOOLBAR_HEIGHT_DP + STATUS_BAR_HEIGHT_DP) * Math.round(displayMetrics.density);
        int orientation = res.getConfiguration().orientation;
        return (displayMetrics.heightPixels - toolbarHeight) / getRowsForOrientation(orientation);
    }
}
