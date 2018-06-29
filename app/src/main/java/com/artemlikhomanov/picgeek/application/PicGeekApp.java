package com.artemlikhomanov.picgeek.application;

import android.app.Application;

import com.artemlikhomanov.picgeek.model.Const;
import com.artemlikhomanov.picgeek.rest.FlickrApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PicGeekApp extends Application {

    private static FlickrApiInterface sInterface;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sInterface = retrofit.create(FlickrApiInterface.class);
    }

    public static FlickrApiInterface getApi() {
        return sInterface;
    }
}
