package kmitl.afinal.nakarin58070064.wallsplash.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.util.FileUtils;

public class DownloadWallpaperTask extends AsyncTask<String, Void, File> {

    private Context context;
    private OnPostLoadListener listener;

    public DownloadWallpaperTask(Context context, OnPostLoadListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected File doInBackground(String... urls) {
        File file = null;
        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(urls[0])
                    .submit()
                    .get();

            file = FileUtils.saveBitmap(context.getCacheDir(),
                    context.getString(R.string.cache_wallpaper_file_name),
                    bitmap, Bitmap.CompressFormat.JPEG, 100);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        listener.onPostLoad(file);
    }

    public interface OnPostLoadListener {
        void onPostLoad(File file);
    }
}
