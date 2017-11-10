package kmitl.afinal.nakarin58070064.wallsplash.task;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.concurrent.ExecutionException;

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
            file = Glide.with(context)
                    .asFile()
                    .load(urls[0])
                    .submit()
                    .get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
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
