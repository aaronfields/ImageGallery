package io.github.aaronfields.gridimagegallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aaronfields on 10/5/16.
 */

public class Photos implements Serializable{

    public int page;
    public int pages;
    public int perpage;
    public long total;
    @SerializedName("photo")
    @Expose public List<Photo> photo;
}
