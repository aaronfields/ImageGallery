package io.github.aaronfields.gridimagegallery;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaronfields on 10/5/16.
 */

public class PhotosResponse {

    @SerializedName("photos")
    public Photos photos;
    public String stat;
}
