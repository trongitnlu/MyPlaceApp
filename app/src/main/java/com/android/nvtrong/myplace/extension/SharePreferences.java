package com.android.nvtrong.myplace.extension;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferences {
    public static final String RADIUS_SEARCH = "radius_search";
    public static final String DATA_PREFERENCES = "data_preferences";

    public static int getPreferencesRadius(Context context) {
        SharedPreferences sharePreferences = context.getSharedPreferences(DATA_PREFERENCES, Context.MODE_PRIVATE);
        return sharePreferences.getInt(RADIUS_SEARCH, 500);
    }
    public static void setPreferencesRadius(Context context, int radius) {
        SharedPreferences sharePreferences = context.getSharedPreferences(DATA_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharePreferences.edit();
        editor.putInt(RADIUS_SEARCH, radius);
        editor.commit();
    }
}
