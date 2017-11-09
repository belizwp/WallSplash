package kmitl.afinal.nakarin58070064.wallsplash.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import kmitl.afinal.nakarin58070064.wallsplash.R;

public class DatabaseManager {

    private static DatabaseManager instance;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private WallSplashDatabase mDatabase;

    private DatabaseManager() {

    }

    public void init(Context context) {
        mDatabase = Room.databaseBuilder(context, WallSplashDatabase.class,
                context.getResources().getString(R.string.db_name))
                .fallbackToDestructiveMigration()
                .build();
    }

    public WallSplashDatabase getDatabase() {
        return mDatabase;
    }
}
