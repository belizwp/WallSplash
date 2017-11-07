package kmitl.afinal.nakarin58070064.wallsplash.task;

import android.os.AsyncTask;

import kmitl.afinal.nakarin58070064.wallsplash.database.WallSplashDatabase;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyCollection;

public class UpdateCollectionTask extends AsyncTask<MyCollection, Void, Void> {

    private WallSplashDatabase db;
    private OnPostUpdateListener listener;

    public UpdateCollectionTask(WallSplashDatabase db, OnPostUpdateListener l) {
        this.db = db;
        this.listener = l;
    }

    @Override
    protected Void doInBackground(MyCollection... myCollections) {
        for (int i = 0; i < myCollections.length; i++) {
            db.myCollectionDao().update(myCollections[i]);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onPostUpdate();
    }

    public interface OnPostUpdateListener {
        void onPostUpdate();
    }
}
