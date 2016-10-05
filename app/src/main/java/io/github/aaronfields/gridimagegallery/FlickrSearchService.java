package io.github.aaronfields.gridimagegallery;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by aaronfields on 10/4/16.
 */

public interface FlickrSearchService {

    @GET("/&format=json&nojsoncallback=1")
    Call<PhotosResponse> getAll();
}
