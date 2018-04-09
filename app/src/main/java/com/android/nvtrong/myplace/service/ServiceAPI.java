package com.android.nvtrong.myplace.service;

import com.android.nvtrong.myplace.data.google.GeocodingRoot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nvtrong on 4/9/2018.
 */

public interface ServiceAPI {
    @GET("geocode/json")
    Call<GeocodingRoot> getLocation(@Query("address") String address, @Query("key") String key);


}
