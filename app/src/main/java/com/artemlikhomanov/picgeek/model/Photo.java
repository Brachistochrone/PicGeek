package com.artemlikhomanov.picgeek.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mOwner);
        dest.writeString(this.mTitle);
        dest.writeValue(this.mLatitude);
        dest.writeValue(this.mLongitude);
        dest.writeString(this.mUrl_sq);
        dest.writeString(this.mUrl_q);
        dest.writeString(this.mUrl_c);
        dest.writeString(this.mUrl_o);
    }

    private Photo(Parcel in) {
        this.mId = in.readString();
        this.mOwner = in.readString();
        this.mTitle = in.readString();
        this.mLatitude = (Double) in.readValue(Double.class.getClassLoader());
        this.mLongitude = (Double) in.readValue(Double.class.getClassLoader());
        this.mUrl_sq = in.readString();
        this.mUrl_q = in.readString();
        this.mUrl_c = in.readString();
        this.mUrl_o = in.readString();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
