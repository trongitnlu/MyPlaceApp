package com.android.nvtrong.myplace.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nvtrong on 4/9/2018.
 */

public class APIUltis {
    public static final String Base_Url = "https://maps.googleapis.com/maps/api/";

    public static ServiceAPI getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ServiceAPI.class);
    }
}
