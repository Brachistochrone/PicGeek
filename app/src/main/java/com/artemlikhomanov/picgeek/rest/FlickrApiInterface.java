package com.artemlikhomanov.picgeek.rest;

import com.artemlikhomanov.picgeek.model.PhotosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApiInterface {

    @GET("rest")
    Call<PhotosResponse> getPics(@Query("method") String method, @Query("api_key") String api_key,
                                 @Query("extras") String extras, @Query("page") String page,
                                 @Query("format") String format, @Query("nojsoncallback") String nojsoncallback);

    @GET("rest")
    Call<PhotosResponse> getPics(@Query("method") String method, @Query("api_key") String api_key,
                                 @Query("extras") String extras, @Query("format") String format,
                                 @Query("nojsoncallback") String nojsoncallback);
}
