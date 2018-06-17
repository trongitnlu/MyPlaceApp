package com.android.nvtrong.myplace.extension;

import android.app.Application;
import android.content.Context;

import com.android.nvtrong.myplace.data.model.PlaceDAO;

public class MyApplication extends Application {
    public static PlaceDAO placeDAO;
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        placeDAO = PlaceDAO.getInstance(this);
        context = getApplicationContext();
    }
}
