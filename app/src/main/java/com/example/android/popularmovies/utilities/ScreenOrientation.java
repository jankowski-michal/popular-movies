package com.example.android.popularmovies.utilities;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ScreenOrientation {
    
    private static final int COLUMNS_ORIENTATION_PORTRAIT = 2;
    
    private static final int ROWS_ORIENTATION_PORTRAIT = 2;
    
    private static final int COLUMN_ORIENTATION_LANDSCAPE = 3;
    
    private static final int ROWS_ORIENTATION_LANDSCAPE = 1;
    
    private static final int TOOLBAR_HEIGHT_DP = 56;
    
    private static final int STATUSBAR_HEIGHT_DP = 24;
    
    public static int getColumnsForOrientation(Resources res) {
        switch (res.getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return COLUMNS_ORIENTATION_PORTRAIT;
            case Configuration.ORIENTATION_LANDSCAPE:
                return COLUMN_ORIENTATION_LANDSCAPE;
            default:
                return COLUMNS_ORIENTATION_PORTRAIT;
        }
    }
    
    private static int getRowsForOrientation(Resources res) {
        switch (res.getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return ROWS_ORIENTATION_PORTRAIT;
            case Configuration.ORIENTATION_LANDSCAPE:
                return ROWS_ORIENTATION_LANDSCAPE;
            default:
                return ROWS_ORIENTATION_PORTRAIT;
        }
    }
    
    public static int getRecyclerElementWidth(Resources res) {
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return Math.round(displayMetrics.widthPixels / getColumnsForOrientation(res));
    }
    
    public static int getRecyclerElementHeight(Resources res) {
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        int toolbarHeight = (TOOLBAR_HEIGHT_DP + STATUSBAR_HEIGHT_DP) * Math.round(displayMetrics.density);
        return Math.round((displayMetrics.heightPixels - toolbarHeight) / getRowsForOrientation(res));
    }
}
