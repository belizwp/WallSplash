package kmitl.afinal.nakarin58070064.wallsplash.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import jp.wasabeef.blurry.Blurry;
import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.MyWallAdapter;
import kmitl.afinal.nakarin58070064.wallsplash.util.ImageUtils;
import kmitl.afinal.nakarin58070064.wallsplash.util.MenuTintUtils;
import kmitl.afinal.nakarin58070064.wallsplash.util.ScreenUtils;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ImageView imageHeader;
    private FrameLayout headerWrapper;
    private FrameLayout headerMenuWrapper;
    private RecyclerView rvMyWall;
    private TextView tvViewAll;
    private Menu menu;
    private MyWallAdapter adapter;
    private boolean collapse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();
        defineDesign();
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

        adapter = new MyWallAdapter();

        rvMyWall.setLayoutManager(layoutManager);
        rvMyWall.addItemDecoration(itemDecoration);
        rvMyWall.setAdapter(adapter);
    }

    private void defineDesign() {
        /*
            update header image bg
         */
        Bitmap bitmap = ImageUtils.drawableToBitmap(ScreenUtils.getCurrentWallpaper(this));
        Blurry.with(this)
                .color(Color.argb(100, 0, 0, 0))
                .radius(2).sampling(32)
                .from(bitmap)
                .into(imageHeader);

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
    }

    private void setToolBarOverflowIconColor(int color) {
        toolbar.setTitleTextColor(color);
        Drawable drawable = toolbar.getOverflowIcon();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), color);
            toolbar.setOverflowIcon(drawable);
        }

        MenuTintUtils.tintAllIcons(menu, color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;

        /*
            appbar scrolling behavior
         */
        setToolBarOverflowIconColor(Color.WHITE); // default
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float scrollRange = appBarLayout.getTotalScrollRange();
                float offsetRatio = (scrollRange + verticalOffset) / scrollRange;

                if (!collapse && offsetRatio < 0.2) {
                    collapse = true;
                    setToolBarOverflowIconColor(Color.BLACK);
                } else if (collapse && offsetRatio >= 0.2) {
                    collapse = false;
                    setToolBarOverflowIconColor(Color.WHITE);
                }
                headerWrapper.setAlpha(offsetRatio);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
