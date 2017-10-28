package kmitl.afinal.nakarin58070064.wallsplash.activity;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;

public class WallpaperActivity extends AppCompatActivity implements View.OnClickListener {

    private Photo photo;

    private PhotoView photoView;
    private CircleImageView imagePhrofile;
    private TextView tvName;
    private ImageButton btnOption;
    private View bottomSheetView;

    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        initInstances();
    }

    private void initInstances() {
        photoView = findViewById(R.id.photoView);
        imagePhrofile = findViewById(R.id.imageProfile);
        tvName = findViewById(R.id.tvName);
        btnOption = findViewById(R.id.btnOption);
        bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_wallpaper, null);

        initBottomDialog();

        Intent intent = getIntent();
        photo = intent.getParcelableExtra(Photo.class.getSimpleName());

        if (photo != null) {
            Glide.with(this)
                    .load(photo.getUrls().getRegular())
                    .apply(RequestOptions.noTransformation())
                    .into(photoView);

            Glide.with(this)
                    .load(photo.getUser().getProfileImage().getMedium())
                    .into(imagePhrofile);

            tvName.setText(photo.getUser().getName());
        }

        btnOption.setOnClickListener(this);
    }

    private void initBottomDialog() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);

        View actSetWall = bottomSheetView.findViewById(R.id.action_set_wallpaper);
        actSetWall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOption:
                bottomSheetDialog.show();
                break;
            case R.id.action_set_wallpaper:
                setWallpaper();
                break;
            case R.id.action_add_to_collection:
                addToCollection();
                break;
            case R.id.action_download:
                download();
                break;
            case R.id.action_unsplash:
                viewOnUnsplash();
                break;
        }
    }

    private void setWallpaper() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                    Bitmap bitmap = Glide.with(getApplicationContext())
                            .asBitmap()
                            .load(photo.getUrls().getRegular())
                            .submit().get();
                    wallpaperManager.setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void addToCollection() {

    }

    private void download() {

    }

    private void viewOnUnsplash() {

    }
}
