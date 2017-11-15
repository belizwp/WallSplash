package kmitl.afinal.nakarin58070064.wallsplash.task;

import android.os.AsyncTask;

import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.database.WallSplashDatabase;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyCollection;

public class LoadCollectionTask extends AsyncTask<Void, Void, List<MyCollection>> {

    private WallSplashDatabase db;
    private OnPostLoadListener listener;

    public LoadCollectionTask(WallSplashDatabase db, OnPostLoadListener l) {
        this.db = db;
        this.listener = l;
    }

    @Override
    protected List<MyCollection> doInBackground(Void... voids) {
        return db.myCollectionDao().getAllWithCover();
    }

    @Override
    protected void onPostExecute(List<MyCollection> myCollections) {
        super.onPostExecute(myCollections);
        listener.onPostLoad(myCollections);
    }

    public interface OnPostLoadListener {
        void onPostLoad(List<MyCollection> myCollections);
    }
}
