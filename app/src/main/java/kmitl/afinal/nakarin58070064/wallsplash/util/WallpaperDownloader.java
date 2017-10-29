package kmitl.afinal.nakarin58070064.wallsplash.util;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;

import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;

public class WallpaperDownloader {

    private final Context context;
    private Photo photo;

    private WallpaperDownloader(Context context) {
        this.context = context;
    }

    public WallpaperDownloader photo(@NonNull Photo photo) {
        this.photo = photo;
        return this;
    }

    public void start() {
        String fileName = photo.getId() + ".jpg";
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File target = new File(directory, fileName);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.d("WallpaperDownloader", "Unable to create directory " + directory.toString());
                return;
            }
        }

        String url = photo.getLinks().getDownload();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setMimeType("image/*");
        request.setTitle(photo.getId());
        request.setDescription("Taken by " + photo.getUser().getName());
        request.allowScanningByMediaScanner();
        request.setVisibleInDownloadsUi(false);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationUri(Uri.fromFile(target));

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        try {
            downloadManager.enqueue(request);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    public static WallpaperDownloader prepare(@NonNull Context context) {
        return new WallpaperDownloader(context);
    }

}
