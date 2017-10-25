package kmitl.afinal.nakarin58070064.wallsplash.fragment;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.WallpaperListAdapter;
import kmitl.afinal.nakarin58070064.wallsplash.model.GridSpacingItemDecoration;
import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;
import kmitl.afinal.nakarin58070064.wallsplash.network.ApiManager;
import kmitl.afinal.nakarin58070064.wallsplash.network.UnsplashAPI;
import kmitl.afinal.nakarin58070064.wallsplash.util.ScreenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WallpaperListFragment extends Fragment {

    private static final String KEY_PHOTO_LIST = "PHOTO_LIST";

    private RecyclerView rvWallpaperList;
    private TextView tvError;
    private WallpaperListAdapter adapter;

    private UnsplashAPI api;
    private List<Photo> photoList;

    public static WallpaperListFragment newInstance() {
        WallpaperListFragment fragment = new WallpaperListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = ApiManager.getInstance().getUnsplashApi(getContext().getString(R.string.application_id));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_PHOTO_LIST, (ArrayList<? extends Parcelable>) photoList);
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

        adapter = new WallpaperListAdapter(getContext());
        rvWallpaperList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        int space = (int) ScreenUtils.convertDpToPixel(8, getContext());
        rvWallpaperList.addItemDecoration(new GridSpacingItemDecoration(3, space, true));
        rvWallpaperList.setAdapter(adapter);

        if (savedInstanceState != null) {
            photoList = savedInstanceState.getParcelableArrayList(KEY_PHOTO_LIST);
            display();
        } else {
            getPhotos();
        }
    }

    private void display() {
        adapter.setPhotoList(photoList);
        adapter.notifyDataSetChanged();
    }

    private void getPhotos() {
        Call<List<Photo>> call = api.getPhotos(null, null, null);
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                Log.d("API", "onResponse");
                if (response.isSuccessful()) {
                    photoList = response.body();
                    display();
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d("API", "onFailure " + t.getMessage());
            }
        });
    }
}
