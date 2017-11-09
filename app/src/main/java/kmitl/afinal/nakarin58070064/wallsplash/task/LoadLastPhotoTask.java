package kmitl.afinal.nakarin58070064.wallsplash.task;

import android.os.AsyncTask;

import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.database.WallSplashDatabase;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyPhoto;

public class LoadLastPhotoTask extends AsyncTask<Integer, Void, List<MyPhoto>> {

    private WallSplashDatabase db;
    private OnPostLoadListener listener;

    public LoadLastPhotoTask(WallSplashDatabase db, OnPostLoadListener l) {
        this.db = db;
        this.listener = l;
    }

    @Override
    protected List<MyPhoto> doInBackground(Integer... integers) {
        return db.myPhotoDao().getLast(integers[0]);
    }

    @Override
    protected void onPostExecute(List<MyPhoto> myPhotoList) {
        super.onPostExecute(myPhotoList);
        listener.onPostLoad(myPhotoList);
    }

    public interface OnPostLoadListener {
        void onPostLoad(List<MyPhoto> myPhotoList);
    }
}
