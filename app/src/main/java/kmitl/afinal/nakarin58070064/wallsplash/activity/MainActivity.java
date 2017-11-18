package kmitl.afinal.nakarin58070064.wallsplash.activity;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jp.wasabeef.blurry.Blurry;
import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.MyWallAdapter;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.RecyclerItemClickListener;
import kmitl.afinal.nakarin58070064.wallsplash.database.DatabaseManager;
import kmitl.afinal.nakarin58070064.wallsplash.fragment.ShowcaseFragment;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyPhoto;
import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;
import kmitl.afinal.nakarin58070064.wallsplash.task.LoadLastPhotoTask;
import kmitl.afinal.nakarin58070064.wallsplash.util.ImageUtils;
import kmitl.afinal.nakarin58070064.wallsplash.util.ScreenUtils;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ImageView imageHeader;
    private FrameLayout headerWrapper;
    private FrameLayout headerMenuWrapper;
    private RecyclerView rvMyWall;
    private TextView tvViewAll;
    private MyWallAdapter adapter;

    private static final String KEY_IS_MY_PHOTO_LIST = "IS_MY_PHOTO_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
        defineDesign();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_container, new ShowcaseFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
            update header image bg
         */
        Blurry.with(this)
                .radius(2).sampling(32)
                .async()
                .from(ImageUtils.drawableToBitmap(ScreenUtils.getCurrentWallpaper(this)))
                .into(imageHeader);

        loadLastAddWallpaper();
    }

    private void initInstances() {
        toolbar = findViewById(R.id.toolBar);
        appBarLayout = findViewById(R.id.appBar);
        imageHeader = findViewById(R.id.imageHeader);
        headerWrapper = findViewById(R.id.headerWrapper);
        headerMenuWrapper = findViewById(R.id.headerMenuWrapper);
        rvMyWall = findViewById(R.id.rvMyWall);
        tvViewAll = findViewById(R.id.tvViewAll);

        setSupportActionBar(toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_space_horizontal));

        adapter = new MyWallAdapter(this);

        rvMyWall.setLayoutManager(layoutManager);
        rvMyWall.addItemDecoration(itemDecoration);
        rvMyWall.setAdapter(adapter);

        tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyCollectionActivity.class);
                startActivity(intent);
            }
        });

        rvMyWall.addOnItemTouchListener(new RecyclerItemClickListener(this, rvMyWall,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Photo photo = adapter.getMyPhotoList().get(position).getPhoto();
                        transition(view, photo);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }

    private void loadLastAddWallpaper() {
        int limit = 10;
        new LoadLastPhotoTask(DatabaseManager.getInstance().getDatabase(), new LoadLastPhotoTask.OnPostLoadListener() {
            @Override
            public void onPostLoad(List<MyPhoto> myPhotoList) {
                adapter.setMyPhotoList(myPhotoList);
                adapter.notifyDataSetChanged();
            }
        }).execute(limit);
    }

    private void transition(View view, Photo photo) {
        Intent intent = new Intent(this, WallpaperPreviewActivity.class);
        intent.putExtra(Photo.class.getSimpleName(), photo);
        intent.putExtra(KEY_IS_MY_PHOTO_LIST, true);

        if (Build.VERSION.SDK_INT < 21) {
            startActivity(intent);
        } else {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, view,
                            getString(R.string.transition_photo));
            startActivity(intent, options.toBundle());
        }
    }

    private void defineDesign() {
        /*
            header content position
         */
        int actionBarHeight = 0;

        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        int top;
        if (Build.VERSION.SDK_INT >= 21) {
            top = ScreenUtils.getStatusBarHeight(this) + actionBarHeight;
        } else {
            top = actionBarHeight;
        }
        headerWrapper.setPadding(0, top, 0, 0);

        /*
            header content scrolling behavior
         */
        rvMyWall.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                float alpha = 0;

                int offsetX = rvMyWall.computeHorizontalScrollOffset();
                float w = headerMenuWrapper.getWidth();

                if (offsetX <= w) {
                    alpha = 1 - offsetX / w;
                    headerMenuWrapper.setVisibility(View.VISIBLE);
                } else {
                    headerMenuWrapper.setVisibility(View.GONE);
                }

                headerMenuWrapper.setAlpha(alpha);
            }
        });

        /*
            appbar scrolling behavior
         */
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
