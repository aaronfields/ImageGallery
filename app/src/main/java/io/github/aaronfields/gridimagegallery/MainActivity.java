package io.github.aaronfields.gridimagegallery;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private FlickrSearchService serviceSearch;
    private FlickrRecentService serviceRecent;
    private String searchText;
    private RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.activity_main) View main;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.search) EditText searchEditText;
    @BindView(R.id.search_button) Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        layoutManager = new GridLayoutManager(this, 2);

        setUpRecentRetrofit();
        getRecentPhotos();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchEditText != null) {
                    searchText = searchEditText.getText().toString();
                    recyclerView.setLayoutManager(layoutManager);
                    setUpSearchRetrofit();
                    getSearchPhotos();
                }
            }
        });

//        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                showProgress(true);
//                getPhotos();
//            }
//        });
    }

    private void setUpRecentRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/services/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceRecent = retrofit.create(FlickrRecentService.class);
    }

    private void setUpSearchRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=27d0ffc7de765240a6000e305ad75f0b&text="+searchText)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceSearch = retrofit.create(FlickrSearchService.class);
    }

//    private void showProgress(boolean show) {
//        refreshLayout.setRefreshing(show);
//    }

    private void getRecentPhotos() {
        serviceRecent.getAll().enqueue(new Callback<PhotosResponse>() {
            @Override
            public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                //showProgress(false);

                final PhotosResponse results = response.body();

                if (response.isSuccessful()) {
                    recyclerView.setAdapter(new PhotoAdapter(results));
                }
            }

            @Override
            public void onFailure(Call<PhotosResponse> call, Throwable t) {
                Log.d(getClass().getSimpleName(), t.getMessage());
                Log.d("FAILED", "YOU FAILED");
                Log.d(getClass().getSimpleName(), t.getMessage(), t);
            }
        });
    }

    private void getSearchPhotos() {
        serviceSearch.getAll().enqueue(new Callback<PhotosResponse>() {
            @Override
            public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                //showProgress(false);

                final PhotosResponse results = response.body();

                if (response.isSuccessful()) {
                    recyclerView.setAdapter(new PhotoAdapter(results));
                }
            }

            @Override
            public void onFailure(Call<PhotosResponse> call, Throwable t) {
                Log.d(getClass().getSimpleName(), t.getMessage());
                Log.d("FAILED", "YOU FAILED");
            }
        });
    }

    class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
        private PhotosResponse data;

        public PhotoAdapter(PhotosResponse data) {
            this.data = data;

//            for(Map.Entry<String, Photos> entry : data.entrySet()){
//                this.data.add(new Pair<String, Photos>(entry.getKey(), entry.getValue()));
//            }
        }

        @Override
        public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false));
        }

        @Override
        public void onBindViewHolder(PhotoViewHolder holder, int position) {
            PhotosResponse photoResponse = data;
            List<Photo> photoList = photoResponse.photos.photo;
            Photo photo = photoList.get(position);

            int farmId = photo.farm;
            int serverId = photo.server;
            long ID = photo.id;
            String secret = photo.secret;

            Picasso.with(MainActivity.this).load("https://farm"+farmId+".staticflickr.com/"+serverId+"/"+ID+"_"+secret+".jpg").into(holder.image);

        }

        @Override
        public int getItemCount() {
            return data.photos.photo.size();
        }
    }
}
    class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.image) ImageView image;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

        }
    }


