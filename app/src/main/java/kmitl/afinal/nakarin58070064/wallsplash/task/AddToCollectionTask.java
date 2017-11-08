package kmitl.afinal.nakarin58070064.wallsplash.task;

import android.os.AsyncTask;

import kmitl.afinal.nakarin58070064.wallsplash.database.WallSplashDatabase;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyCollection;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyPhoto;

public class AddToCollectionTask extends AsyncTask<MyPhoto, Void, Void> {

    private WallSplashDatabase db;
    private OnPostAddListener listener;

    public AddToCollectionTask(WallSplashDatabase db, OnPostAddListener l) {
        this.db = db;
        this.listener = l;
    }

    @Override
    protected Void doInBackground(MyPhoto... myPhotos) {
        for (int i = 0; i < myPhotos.length; i++) {
            db.myPhotoDao().addToCollection(myPhotos[i]);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onPostAdd();
    }

    public interface OnPostAddListener {
        void onPostAdd();
    }
}
