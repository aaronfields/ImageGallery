package io.github.aaronfields.gridimagegallery;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by aaronfields on 10/5/16.
 */

public interface FlickrRecentService {

    @GET("rest/?method=flickr.photos.getRecent&api_key=2fbd51c2f84ff08464b5a5bdbe6e89be&format=json&nojsoncallback=1&auth_token=72157671368745794-3a3f131149d72faa&api_sig=757e2fd9d58b0fd0d5e4b41d5cb615ef")
    Call<PhotosResponse> getAll();

}
