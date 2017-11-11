package kmitl.afinal.nakarin58070064.wallsplash.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import kmitl.afinal.nakarin58070064.wallsplash.BuildConfig;
import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.model.Collection;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyCollection;
import kmitl.afinal.nakarin58070064.wallsplash.task.AddCollectionTask;

public class DatabaseManager {

    private static DatabaseManager instance;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private final String KEY_FIRST_RUN = "FIRST_RUN";
    private WallSplashDatabase mDatabase;

    private DatabaseManager() {

    }

    public void init(Context context) {
        mDatabase = Room.databaseBuilder(context, WallSplashDatabase.class,
                context.getResources().getString(R.string.db_name))
                .fallbackToDestructiveMigration()
                .build();

        final SharedPreferences prefs = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        if (prefs.getBoolean(KEY_FIRST_RUN, true)) {
            // create default collection for the first time using app
            MyCollection myCollection = new MyCollection();
            myCollection.setTitle("My Favorite");

            new AddCollectionTask(mDatabase, new AddCollectionTask.OnPostAddListener() {
                @Override
                public void onPostAdd(long id) {
                    prefs.edit().putBoolean(KEY_FIRST_RUN, false).apply();
                }
            }).execute(myCollection);
        }
    }

    public WallSplashDatabase getDatabase() {
        return mDatabase;
    }
}
