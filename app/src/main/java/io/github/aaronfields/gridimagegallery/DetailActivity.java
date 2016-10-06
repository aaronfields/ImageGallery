package io.github.aaronfields.gridimagegallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private int position;
    private PhotosResponse photoResponse;

    private ImageView detailImage;
    private TextView imageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailImage = (ImageView) findViewById(R.id.detail_image);
        imageText = (TextView) findViewById(R.id.detail_image_text);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        Log.d("GOT INTENT", "SUCCESS");

        photoResponse = Singleton.getInstance().getPhotoResponse();

        List<Photo> mPhotoList = photoResponse.photos.photo;
        Photo photo = mPhotoList.get(position);

        int farmId = photo.farm;
        int serverId = photo.server;
        long ID = photo.id;
        String secret = photo.secret;
        String title = photo.title;

        imageText.setText(title);

        final String imageUrl = "https://farm"+farmId+".staticflickr.com/"+serverId+"/"+ID+"_"+secret+".jpg";

        Picasso.with(DetailActivity.this).load(imageUrl).into(detailImage);


    }
}
