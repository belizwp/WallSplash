package kmitl.afinal.nakarin58070064.wallsplash;

import android.app.Application;

import kmitl.afinal.nakarin58070064.wallsplash.database.DatabaseManager;

public class WallSplash extends Application {

    public static final int MAX_RESULT_PER_PAGE = 30;

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseManager.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
