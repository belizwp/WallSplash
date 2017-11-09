package kmitl.afinal.nakarin58070064.wallsplash.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.model.MyPhoto;

@Dao
public interface MyPhotoDao {

    @Query("SELECT * FROM MyPhoto WHERE current_collection == :myCollectionId")
    List<MyPhoto> getAll(int myCollectionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToCollection(MyPhoto myPhoto);

    @Query("SELECT * FROM MyPhoto ORDER BY time_create DESC LIMIT :limit")
    List<MyPhoto> getLast(int limit);
}
