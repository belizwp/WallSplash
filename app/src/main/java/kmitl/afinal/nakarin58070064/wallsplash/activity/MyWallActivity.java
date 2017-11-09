package kmitl.afinal.nakarin58070064.wallsplash.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.database.DatabaseManager;
import kmitl.afinal.nakarin58070064.wallsplash.database.WallSplashDatabase;
import kmitl.afinal.nakarin58070064.wallsplash.fragment.WallpaperListFragment;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyCollection;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyPhoto;
import kmitl.afinal.nakarin58070064.wallsplash.task.LoadMyPhotoTask;

public class MyWallActivity extends AppCompatActivity {

    private WallSplashDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wall);

        initInstances(savedInstanceState);
    }

    private void initInstances(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initDB();

        MyCollection myCollection = getIntent().getParcelableExtra(MyCollection.class.getSimpleName());

        if (myCollection != null) {
            setTitle(myCollection.getTitle());
            loadData(myCollection.getId());
        }
    }

    private void initDB() {
        database = DatabaseManager.getInstance().getDatabase();
    }

    private void loadData(final int myCollectionId) {
        new LoadMyPhotoTask(database, new LoadMyPhotoTask.OnPostLoadListener() {
            @Override
            public void onPostLoad(List<MyPhoto> myPhotoList) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_container, WallpaperListFragment.newInstance(myPhotoList))
                        .commit();
            }
        }).execute(myCollectionId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
