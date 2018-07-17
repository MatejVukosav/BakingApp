package com.vuki.bakingapp.models;

import android.os.Parcel;

import com.squareup.moshi.Json;

import java.io.Serializable;

/**
 * Created by mvukosav
 */
public class ApiSteps implements Serializable {

    int id;
    @Json(name = "shortDescription")
    String shortDescription;
    @Json(name = "description")
    String description;
    @Json(name = "videoUrl")
    String videoUrl;
    @Json(name = "thumbnailURL")
    String thumbnailUrl;

    protected ApiSteps( Parcel in ) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription( String shortDescription ) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl( String videoUrl ) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl( String thumbnailUrl ) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
