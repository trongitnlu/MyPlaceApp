package com.android.nvtrong.myplace.service;

import com.android.nvtrong.myplace.data.google.DirectionRoot;
import com.android.nvtrong.myplace.data.google.GeocodingRoot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ServiceAPI {

    @GET("place/nearbysearch/json")
    Call<GeocodingRoot> getLocationByType(@Query("location") String location, @Query("radius") String radius, @Query("type") String type, @Query("key") String key);
}
