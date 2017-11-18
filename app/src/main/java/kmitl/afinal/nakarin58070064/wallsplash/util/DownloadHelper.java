package kmitl.afinal.nakarin58070064.wallsplash.util;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;

public class DownloadHelper {

    private final Context context;
    private Photo photo;

    private DownloadHelper(Context context) {
        this.context = context;
    }

    public DownloadHelper photo(@NonNull Photo photo) {
        this.photo = photo;
        return this;
    }

    public void start() {
        String fileName = photo.getId() + ".jpeg";
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File target = new File(directory, fileName);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                return;
            }
        }

        String url = photo.getUrls().getFull();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setMimeType("image/jpeg");
        request.setTitle(context.getString(R.string.name_download_file, photo.getId()));
        request.setDescription(context.getString(R.string.a_photo_by, photo.getUser().getName()));
        request.allowScanningByMediaScanner();
        request.setVisibleInDownloadsUi(false);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationUri(Uri.fromFile(target));

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        try {
            downloadManager.enqueue(request);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        Toast.makeText(context, R.string.download_started, Toast.LENGTH_SHORT).show();
    }

    public static DownloadHelper prepare(@NonNull Context context) {
        return new DownloadHelper(context);
    }

}
