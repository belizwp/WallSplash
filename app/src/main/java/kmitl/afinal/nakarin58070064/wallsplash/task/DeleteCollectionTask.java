package kmitl.afinal.nakarin58070064.wallsplash.task;

import android.os.AsyncTask;

import kmitl.afinal.nakarin58070064.wallsplash.database.WallSplashDatabase;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyCollection;

public class DeleteCollectionTask extends AsyncTask<MyCollection, Void, Void> {

    private WallSplashDatabase db;
    private OnPostDeleteListener listener;

    public DeleteCollectionTask(WallSplashDatabase db, OnPostDeleteListener l) {
        this.db = db;
        this.listener = l;
    }

    @Override
    protected Void doInBackground(MyCollection... myCollections) {
        for (int i = 0; i < myCollections.length; i++) {
            db.myCollectionDao().delete(myCollections[i]);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onPostDelete();
    }

    public interface OnPostDeleteListener {
        void onPostDelete();
    }
}
