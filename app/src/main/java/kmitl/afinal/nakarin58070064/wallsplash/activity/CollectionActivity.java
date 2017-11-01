package kmitl.afinal.nakarin58070064.wallsplash.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.fragment.WallpaperListFragment;
import kmitl.afinal.nakarin58070064.wallsplash.model.Collection;

public class CollectionActivity extends AppCompatActivity {

    private Collection collection;

    private ImageView imageHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initInstances();

        if (savedInstanceState == null) {
            if (collection != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.content_container,
                                WallpaperListFragment.newInstance(String.valueOf(collection.getId())))
                        .commit();
            }
        }
    }

    private void initInstances() {
        collection = getIntent().getParcelableExtra(Collection.class.getSimpleName());

        Toolbar toolbar = findViewById(R.id.toolBar);
        imageHeader = findViewById(R.id.imageHeader);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(null);

        if (collection.getCoverPhoto() != null) {
            Glide.with(this)
                    .load(collection.getCoverPhoto().getUrls().getThumb())
                    .into(imageHeader);
        }
    }
}
