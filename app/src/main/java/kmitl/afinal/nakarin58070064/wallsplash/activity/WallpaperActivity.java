package kmitl.afinal.nakarin58070064.wallsplash.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.afinal.nakarin58070064.wallsplash.BuildConfig;
import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;
import kmitl.afinal.nakarin58070064.wallsplash.task.DownloadWallpaperTask;
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
    private ProgressDialog pDialog;

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
                bottomSheetDialog.dismiss();
                setWallpaper();
                break;
            case R.id.action_add_to_collection:
                bottomSheetDialog.dismiss();
                addToCollection();
                break;
            case R.id.action_download:
                checkPermission(REQUST_DOWNLOAD);
                bottomSheetDialog.dismiss();
                break;
            case R.id.action_unsplash:
                bottomSheetDialog.dismiss();
                viewOnUnsplash();
                break;
            case R.id.action_share:
                bottomSheetDialog.dismiss();
                share();
                break;
        }
    }

    private void setWallpaper() {
        showProgressDialog();
        new DownloadWallpaperTask(getApplicationContext(), new DownloadWallpaperTask.OnPostLoadListener() {
            @Override
            public void onPostLoad(File file) {
                if (file != null) {
                    Uri uri = FileProvider.getUriForFile(getApplicationContext(),
                            BuildConfig.APPLICATION_ID + getString(R.string.fileprovider_postfix), file);

                    Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(uri, "image/jpeg");
                    intent.putExtra("mimeType", "image/jpeg");
                    startActivity(Intent.createChooser(intent, getString(R.string.set_as)));

                    dismissProgressDialog();
                }
            }
        }).execute(photo.getUrls().getFull());
    }

    private void addToCollection() {
        Intent intent = new Intent(WallpaperActivity.this, AddToCollectionActivity.class);
        intent.putExtra(Photo.class.getSimpleName(), photo);
        startActivity(intent);
    }

    private void download() {
        WallpaperDownloader.prepare(this)
                .photo(photo)
                .start();
    }

    private void share() {
        String UTM_PARAMS = getString(R.string.utm_params);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.unsplash));
        intent.putExtra(Intent.EXTRA_TEXT, photo.getLinks().getHtml() + UTM_PARAMS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getString(R.string.action_share)));
    }

    private void viewOnUnsplash() {
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
                    showToast(getString(R.string.need_download_permission));
                }
                return;
            }
        }
    }

    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(WallpaperActivity.this);
            pDialog.setMessage(getString(R.string.downloading_wallpaper));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
