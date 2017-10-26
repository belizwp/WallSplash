package kmitl.afinal.nakarin58070064.wallsplash.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;

public class WallpaperActivity extends AppCompatActivity {

    private Photo photo;
    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        initInstances();
    }

    private void initInstances() {
        Intent intent = getIntent();
        photo = intent.getParcelableExtra(Photo.class.getSimpleName());
        photoView = findViewById(R.id.photoView);

        if (photo != null) {
            photoView.setBackgroundColor(Color.parseColor(photo.getColor()));

            Glide.with(this)
                    .load(photo.getUrls().getRegular())
                    .apply(RequestOptions.noTransformation())
                    .into(photoView);
        }
    }
}
