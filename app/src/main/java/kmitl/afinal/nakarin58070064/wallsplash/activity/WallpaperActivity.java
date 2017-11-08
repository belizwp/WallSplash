package kmitl.afinal.nakarin58070064.wallsplash.activity;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;
import kmitl.afinal.nakarin58070064.wallsplash.util.ScreenUtils;
import kmitl.afinal.nakarin58070064.wallsplash.util.WallpaperDownloader;

public class WallpaperActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_IS_MY_PHOTO_LIST = "IS_MY_PHOTO_LIST";

    private Photo photo;

    private PhotoView photoView;
    private CircleImageView imagePhrofile;
    private TextView tvName;
    private ImageButton btnOption;
    private View bottomSheetView;
    private BottomSheetDialog bottomSheetDialog;

    private final int REQUST_DOWNLOAD = 0;

    private boolean isMyPhotoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        initInstances(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Photo.class.getSimpleName(), photo);
    }

    private void initInstances(Bundle savedInstanceState) {
        photoView = findViewById(R.id.photoView);
        imagePhrofile = findViewById(R.id.imageProfile);
        tvName = findViewById(R.id.tvName);
        btnOption = findViewById(R.id.btnOption);

        Intent intent = getIntent();
        photo = intent.getParcelableExtra(Photo.class.getSimpleName());
        isMyPhotoList = intent.getBooleanExtra(KEY_IS_MY_PHOTO_LIST, true);

        if (photo == null && savedInstanceState != null) {
            photo = savedInstanceState.getParcelable(Photo.class.getSimpleName());
        }

        initBottomDialog();

        applyLowQualityImage();

        Glide.with(WallpaperActivity.this)
                .load(photo.getUser().getProfileImage().getMedium())
                .into(imagePhrofile);

        tvName.setText(photo.getUser().getName());

        btnOption.setOnClickListener(this);
    }

    private void applyLowQualityImage() {
        Glide.with(WallpaperActivity.this)
                .load(photo.getUrls().getSmall())
                .apply(RequestOptions.overrideOf(getResources().getInteger(R.integer.max_image_size)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                        photoView.setImageDrawable(resource);
                        applyHighQualityImage();
                    }
                });
    }

    private void applyHighQualityImage() {
        Glide.with(WallpaperActivity.this)
                .load(photo.getUrls().getRegular())
                .apply(RequestOptions.overrideOf(getResources().getInteger(R.integer.max_image_size)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                        photoView.setImageDrawable(resource);
                    }
                });
    }

    private void initBottomDialog() {
        bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_wallpaper, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);

        View actSetWallpaper = bottomSheetView.findViewById(R.id.action_set_wallpaper);
        View actAddToCollection = bottomSheetView.findViewById(R.id.action_add_to_collection);
        View actDownload = bottomSheetView.findViewById(R.id.action_download);
        View actShare = bottomSheetView.findViewById(R.id.action_share);
        View actUnsplash = bottomSheetView.findViewById(R.id.action_unsplash);

        actSetWallpaper.setOnClickListener(this);
        actAddToCollection.setOnClickListener(this);
        actDownload.setOnClickListener(this);
        actShare.setOnClickListener(this);
        actUnsplash.setOnClickListener(this);

        if (isMyPhotoList) {
            actAddToCollection.setVisibility(View.GONE);
        }
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
                checkPermission(REQUST_DOWNLOAD);
                break;
            case R.id.action_unsplash:
                viewOnUnsplash();
                break;
            case R.id.action_share:
                share();
                break;
        }
    }

    private void setWallpaper() {
        showToast("set wallpaper");
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
        showToast("add to collection");
        Intent intent = new Intent(WallpaperActivity.this, AddToCollectionActivity.class);
        intent.putExtra(Photo.class.getSimpleName(), photo);
        startActivity(intent);
    }

    private void download() {
        showToast("download");
        WallpaperDownloader.prepare(this)
                .photo(photo)
                .start();
    }

    private void share() {
        showToast("share");
        String UTM_PARAMS = "?utm_source=wallsplash&utm_medium=referral&utm_campaign=api-credit";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Unsplash");
        intent.putExtra(Intent.EXTRA_TEXT, photo.getLinks().getHtml() + UTM_PARAMS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getString(R.string.action_share)));
    }

    private void viewOnUnsplash() {
        showToast("unsplash");
        String url = photo.getLinks().getHtml();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void checkPermission(int REQUST_CODE) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUST_CODE);
            }
        } else {
            switch (REQUST_CODE) {
                case REQUST_DOWNLOAD:
                    download();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUST_DOWNLOAD: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    download();
                } else {
                    showToast("need perm to download");
                }
                return;
            }
        }
    }
}
