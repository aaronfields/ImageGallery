package io.github.aaronfields.gridimagegallery;

import java.io.Serializable;

/**
 * Created by aaronfields on 10/6/16.
 */

public class Singleton implements Serializable{

    private PhotosResponse mPhotoResponse;

    private static Singleton singleton;

    private Singleton(){
        mPhotoResponse = new PhotosResponse();
    }

    public static Singleton getInstance() {
        if(singleton == null){
            singleton = new Singleton();

        }
        return singleton;
    }

    public PhotosResponse getPhotoResponse() {
        return mPhotoResponse;
    }

    public void setPhotoResponse(PhotosResponse photoResponse) {
        mPhotoResponse = photoResponse;
    }

}
