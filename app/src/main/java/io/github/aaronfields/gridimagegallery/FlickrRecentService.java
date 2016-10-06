package io.github.aaronfields.gridimagegallery;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by aaronfields on 10/5/16.
 */

public interface FlickrRecentService {

    @GET("rest/?method=flickr.photos.getRecent&api_key=54c1da9d17aca27d9f39d4fbbb282cf2&format=json&nojsoncallback=1")
    Call<PhotosResponse> getAll();


}
