package kmitl.afinal.nakarin58070064.wallsplash.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import kmitl.afinal.nakarin58070064.wallsplash.dao.MyCollectionDao;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyCollection;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyPhoto;

@Database(entities = {MyCollection.class, MyPhoto.class}, version = 1)
public abstract class WallSplashDatabase extends RoomDatabase {

    public abstract MyCollectionDao myCollectionDao();

}
