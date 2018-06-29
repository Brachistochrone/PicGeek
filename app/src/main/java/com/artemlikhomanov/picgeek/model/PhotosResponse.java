package com.artemlikhomanov.picgeek.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotosResponse {

    @SerializedName("photos")
    @Expose
    private Photos mPhotos;

    public Photos getPhotos() {
        return mPhotos;
    }
}
