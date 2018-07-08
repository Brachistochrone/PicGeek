package com.artemlikhomanov.picgeek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Photos implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mNumberOfPage);
        dest.writeValue(this.mTotalPages);
    }

    private Photos(Parcel in) {
        this.mNumberOfPage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mTotalPages = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Photos> CREATOR = new Parcelable.Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel source) {
            return new Photos(source);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };
}
