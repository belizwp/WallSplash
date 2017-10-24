package kmitl.afinal.nakarin58070064.wallsplash.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.WallpaperListAdapter;
import kmitl.afinal.nakarin58070064.wallsplash.model.GridSpacingItemDecoration;
import kmitl.afinal.nakarin58070064.wallsplash.util.ScreenUtils;

public class WallpaperListFragment extends Fragment {

    private RecyclerView rvWallpaperList;
    private TextView tvError;
    private WallpaperListAdapter adapter;

    public static WallpaperListFragment newInstance() {
        WallpaperListFragment fragment = new WallpaperListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallpaper_list, container, false);
        initInstance(rootView, savedInstanceState);
        return rootView;
    }

    private void initInstance(View rootView, Bundle savedInstanceState) {
        rvWallpaperList = rootView.findViewById(R.id.rvWallpaperList);
        tvError = rootView.findViewById(R.id.tvError);

        adapter = new WallpaperListAdapter();
        rvWallpaperList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        int space = (int) ScreenUtils.convertDpToPixel(8, getContext());
        rvWallpaperList.addItemDecoration(new GridSpacingItemDecoration(3, space, true));
        rvWallpaperList.setAdapter(adapter);

    }

}
