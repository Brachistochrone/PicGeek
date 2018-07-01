package com.artemlikhomanov.picgeek.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Photos {

    @SerializedName("page")
    @Expose
    private Integer mNumberOfPage;

    @SerializedName("pages")
    @Expose
    private Integer mTotalPages;

    @SerializedName("perpage")
    @Expose
    private Integer mPerPage;

    @SerializedName("total")
    @Expose
    private Integer mTotalPhotos;

    @SerializedName("photo")
    @Expose
    private List<Photo> mPhotos;

    /*обязательно конструктор*/
    public Photos () {
        mPhotos = new ArrayList<>();
    }

    public Integer getNumberOfPage() {
        return mNumberOfPage;
    }

    public Integer getTotalPages() {
        return mTotalPages;
    }

    public Integer getPerPage() {
        return mPerPage;
    }

    public Integer getTotalPhotos() {
        return mTotalPhotos;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public String nextPage() {
        return String.valueOf(mNumberOfPage + 1);
    }

    public boolean isLastPage () {
        return mNumberOfPage.equals(mTotalPages);
    }
}
