package io.github.aaronfields.gridimagegallery;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aaronfields on 10/5/16.
 */

public class PhotosResponse implements Serializable{

    @SerializedName("photos")
    public Photos photos;
    public String stat;
}
