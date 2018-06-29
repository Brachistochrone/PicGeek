package com.artemlikhomanov.picgeek.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("owner")
    @Expose
    private String mOwner;

    @SerializedName("title")
    @Expose
    private String mTitle;

    @SerializedName("latitude")
    @Expose
    private Double mLatitude;

    @SerializedName("longitude")
    @Expose
    private Double mLongitude;

    @SerializedName("url_sq")
    @Expose
    private String mUrl_sq;

    @SerializedName("url_q")
    @Expose
    private String mUrl_q;

    @SerializedName("url_c")
    @Expose
    private String mUrl_c;

    @SerializedName("url_o")
    @Expose
    private String mUrl_o;


    public String getId () {
        return mId;
    }

    public String getOwner () {
        return mOwner;
    }

    public String getTitle () {
        return mTitle;
    }

    public Double getLatitude () {
        return mLatitude;
    }

    public Double getLongitude () {
        return mLongitude;
    }

    public String getUrl_sq () {
        return mUrl_sq;
    }

    public String getUrl_q () {
        return mUrl_q;
    }

    public String getUrl_c () {
        return mUrl_c;
    }

    public String getUrl_o () {
        return mUrl_o;
    }
}
