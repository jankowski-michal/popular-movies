package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.R;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ImagesDimensionUtils {
    
    private static final int COLUMNS_ORIENTATION_PORTRAIT = 2;
    
    private static final int ROWS_ORIENTATION_PORTRAIT = 2;
    
    private static final int COLUMN_ORIENTATION_LANDSCAPE = 3;
    
    private static final int ROWS_ORIENTATION_LANDSCAPE = 1;
    
    private static final int DEFAULT_TOOLBAR_HEIGHT_DP = 56;
    
    private static final int STATUS_BAR_HEIGHT_DP = 24;
    
    private static int getToolbarHeightPixels(Context context) {
        TypedValue tv = new TypedValue();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, displayMetrics);
        }
        return DEFAULT_TOOLBAR_HEIGHT_DP * Math.round(displayMetrics.density);
    }
    
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
    
    public static int getGridElementWidth(Context context) {
        Resources res = context.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        int orientation = res.getConfiguration().orientation;
        return displayMetrics.widthPixels / getColumnsForOrientation(orientation);
    }
    
    public static int getGridElementHeight(Context context) {
        Resources res = context.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        int toolbarHeightPixels = STATUS_BAR_HEIGHT_DP * Math.round(displayMetrics.density) + getToolbarHeightPixels(context);
        int orientation = res.getConfiguration().orientation;
        return (displayMetrics.heightPixels - toolbarHeightPixels) / getRowsForOrientation(orientation);
    }
}
