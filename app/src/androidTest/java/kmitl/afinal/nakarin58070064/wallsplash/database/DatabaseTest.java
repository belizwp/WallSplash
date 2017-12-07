package kmitl.afinal.nakarin58070064.wallsplash.database;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.dao.MyCollectionDao;
import kmitl.afinal.nakarin58070064.wallsplash.dao.MyPhotoDao;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyCollection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private WallSplashDatabase database;
    private MyCollectionDao myCollectionDao;
    private MyPhotoDao myPhotoDao;

    @Before
    public void setup() {
        database = DatabaseManager.getInstance().getDatabase();

        myCollectionDao = database.myCollectionDao();
        myPhotoDao = database.myPhotoDao();

        myCollectionDao.nukeTable();
        myPhotoDao.nukeTable();
    }

    @Test
    public void readAndReadCollection() {
        MyCollection myCollection = new MyCollection();
        myCollection.setTitle("Title");
        myCollectionDao.insert(myCollection);

        List<MyCollection> list = myCollectionDao.getAll();
        assertThat(list.get(0).getTitle(), equalTo(myCollection.getTitle()));
    }

    @Test
    public void updateCollection() {
        MyCollection myCollection = new MyCollection();
        myCollection.setTitle("Title");
        myCollectionDao.insert(myCollection);

        MyCollection newCollection = myCollectionDao.getAll().get(0);
        newCollection.setTitle("Edited Title");
        myCollectionDao.update(newCollection);

        assertThat(myCollectionDao.getAll().get(0).getTitle(), equalTo(newCollection.getTitle()));
    }

    @Test
    public void deleteCollection() {
        MyCollection myCollection = new MyCollection();
        myCollection.setTitle("Title");
        myCollectionDao.insert(myCollection);

        MyCollection getCollection = myCollectionDao.getAll().get(0);
        myCollectionDao.delete(getCollection);

        assertThat(myCollectionDao.getAll().size(), equalTo(0));
    }

}
