package kmitl.afinal.nakarin58070064.wallsplash.task;

import android.os.AsyncTask;

import kmitl.afinal.nakarin58070064.wallsplash.database.WallSplashDatabase;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyCollection;

public class AddCollectionTask extends AsyncTask<MyCollection, Void, Long> {

    private WallSplashDatabase db;
    private OnPostAddListener listener;

    public AddCollectionTask(WallSplashDatabase db, OnPostAddListener l) {
        this.db = db;
        this.listener = l;
    }

    @Override
    protected Long doInBackground(MyCollection... myCollections) {
        return db.myCollectionDao().insert(myCollections[0]);
    }

    @Override
    protected void onPostExecute(Long along) {
        super.onPostExecute(along);
        listener.onPostAdd(along);
    }

    public interface OnPostAddListener {
        void onPostAdd(long id);
    }
}
