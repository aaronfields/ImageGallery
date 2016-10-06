package io.github.aaronfields.gridimagegallery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by aaronfields on 10/4/16.
 */

public interface FlickrSearchService {

    @GET("rest/?method=flickr.photos.search&api_key=54c1da9d17aca27d9f39d4fbbb282cf2")

    Call<PhotosResponse> getAll(@Query("text") String searchText, @Query("format") String json, @Query("nojsoncallback") int no);
}
