package io.github.aaronfields.gridimagegallery;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

        ButterKnife.bind(this);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new GridLayoutManager(this, 3);
        } else {
            layoutManager = new GridLayoutManager(this, 6);
        }


        recyclerView.setLayoutManager(layoutManager);

        setUpRecentRetrofit();
        getRecentPhotos();

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (searchText == null){
                    showProgress(true);
                    getRecentPhotos();
                } else{
                    showProgress(true);
                    getSearchPhotos();
                }

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("PhotoResponse", Singleton.getInstance().getPhotoResponse());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null) {
            //PhotosResponse photoResponse = savedInstanceState.getSerializable("PhotosResponse");

        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photo_gallery, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchText = query;
                searchView.clearFocus();
                setUpSearchRetrofit();
                getSearchPhotos();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getActionBar();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                getRecentPhotos();
                searchText = null;
                item.collapseActionView();

                View view = this.getCurrentFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                .baseUrl("https://api.flickr.com/services/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceSearch = retrofit.create(FlickrSearchService.class);
    }

    private void showProgress(boolean show) {
        refreshLayout.setRefreshing(show);
    }

    private void getRecentPhotos() {
        serviceRecent.getAll().enqueue(new Callback<PhotosResponse>() {
            @Override
            public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                showProgress(false);

                final PhotosResponse results = response.body();

                if (response.isSuccessful()) {
                    recyclerView.setAdapter(new PhotoAdapter(results));
                    Log.d("SUCCESS", "REJOICE");
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
        serviceSearch.getAll(searchText, "json", 1).enqueue(new Callback<PhotosResponse>() {
            @Override
            public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                showProgress(false);

                final PhotosResponse results = response.body();

                if (response.isSuccessful()) {
                    recyclerView.setAdapter(new PhotoAdapter(results));
                    Log.d("SUCCESS", "REJOICE");
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

        Context context = getParent();
        public PhotosResponse mData;



        public PhotoAdapter(PhotosResponse data) {
            mData = data;
        }

        public PhotosResponse getData() {
            return mData;
        }

        @Override
        public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false));
        }


        @Override
        public void onBindViewHolder(PhotoViewHolder holder, int position) {
            PhotosResponse photoResponse = mData;
            Singleton.getInstance().setPhotoResponse(photoResponse);

            List<Photo> mPhotoList = photoResponse.photos.photo;
            Photo photo = mPhotoList.get(position);

            int farmId = photo.farm;
            int serverId = photo.server;
            long ID = photo.id;
            String secret = photo.secret;
            final String imageUrl = "https://farm"+farmId+".staticflickr.com/"+serverId+"/"+ID+"_"+secret+".jpg";

            Picasso.with(MainActivity.this).load(imageUrl).into(holder.image);

        }

        @Override
        public int getItemCount() {
            return mData.photos.photo.size();
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
            Context context = view.getContext();
            int position = getAdapterPosition();

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("position",position);
            context.startActivity(intent);


        }

    }



