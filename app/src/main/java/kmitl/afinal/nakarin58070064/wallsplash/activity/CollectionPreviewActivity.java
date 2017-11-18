package kmitl.afinal.nakarin58070064.wallsplash.activity;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.fragment.WallpaperListFragment;
import kmitl.afinal.nakarin58070064.wallsplash.model.Collection;

public class CollectionPreviewActivity extends AppCompatActivity {

    private Collection collection;

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
        ImageView imageHeader = findViewById(R.id.imageHeader);
        ImageView imageProfile = findViewById(R.id.imageProfile);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvName = findViewById(R.id.tvName);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(null);

        if (collection.getCoverPhoto() != null) {
            Glide.with(this)
                    .load(collection.getCoverPhoto().getUrls().getThumb())
                    .into(imageHeader);
        }

        Glide.with(this)
                .load(collection.getUser().getProfileImage().getMedium())
                .into(imageProfile);

        tvTitle.setText(collection.getTitle());
        tvName.setText(getString(R.string.a_collection_by_user_name, collection.getUser().getName()));

        defineDesign();
    }

    private void defineDesign() {
        /*
            appbar scrolling behavior
         */
        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        final View headerWrapper = findViewById(R.id.headerWrapper);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRange = appBarLayout.getTotalScrollRange();
                float offsetRatio = (float) (scrollRange + verticalOffset) / scrollRange;
                headerWrapper.setAlpha(offsetRatio);
            }
        });
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
