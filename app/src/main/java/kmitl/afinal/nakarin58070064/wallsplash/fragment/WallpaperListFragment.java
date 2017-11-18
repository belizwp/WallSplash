package kmitl.afinal.nakarin58070064.wallsplash.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.WallSplash;
import kmitl.afinal.nakarin58070064.wallsplash.activity.WallpaperPreviewActivity;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.RecyclerItemClickListener;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.WallpaperListAdapter;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.GridSpacingItemDecoration;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyPhoto;
import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;
import kmitl.afinal.nakarin58070064.wallsplash.model.SearchResults;
import kmitl.afinal.nakarin58070064.wallsplash.network.api.ApiManager;
import kmitl.afinal.nakarin58070064.wallsplash.network.api.UnsplashAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WallpaperListFragment extends Fragment {

    private static final String KEY_PHOTO_LIST = "PHOTO_LIST";
    private static final String KEY_COLLECTION_ID = "COLLECTION_ID";
    private static final String KEY_MY_PHOTO_LIST = "MY_PHOTO_LIST";
    private static final String KEY_IS_MY_PHOTO_LIST = "IS_MY_PHOTO_LIST";
    private static final String KEY_IS_WAIT_QUERY = "IS_WAIT_QUERY";
    private static final String KEY_QUERY = "QUERY";

    private RecyclerView rvWallpaperList;
    private TextView tvError;
    private WallpaperListAdapter adapter;
    private ContentLoadingProgressBar progressBar;

    private UnsplashAPI api;
    private List<Photo> photoList;

    private String collectionId;

    private boolean isMyPhotoList;

    private boolean isWaitQuery;
    private String query;

    public static WallpaperListFragment newInstance(boolean isWaitQuery) {
        WallpaperListFragment fragment = new WallpaperListFragment();
        fragment.isWaitQuery = isWaitQuery;
        return fragment;
    }

    public static WallpaperListFragment newInstance(String collectionId) {
        Bundle args = new Bundle();
        args.putString(KEY_COLLECTION_ID, collectionId);
        WallpaperListFragment fragment = new WallpaperListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static WallpaperListFragment newInstance(List<MyPhoto> myPhotoList) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_MY_PHOTO_LIST, (ArrayList<? extends Parcelable>) myPhotoList);
        args.putBoolean(KEY_IS_MY_PHOTO_LIST, true);
        WallpaperListFragment fragment = new WallpaperListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = ApiManager.getInstance().getUnsplashApi(getContext().getString(R.string.application_id));

        onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_PHOTO_LIST, (ArrayList<? extends Parcelable>) photoList);
        outState.putBoolean(KEY_IS_WAIT_QUERY, isWaitQuery);
        outState.putString(KEY_QUERY, query);
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            photoList = savedInstanceState.getParcelableArrayList(KEY_PHOTO_LIST);
            isWaitQuery = savedInstanceState.getBoolean(KEY_IS_WAIT_QUERY, false);
            query = savedInstanceState.getString(KEY_QUERY);
        }

        if (getArguments() != null) {
            collectionId = getArguments().getString(KEY_COLLECTION_ID);
            if (getArguments().getParcelableArrayList(KEY_MY_PHOTO_LIST) != null) {
                List<MyPhoto> myPhotoList = getArguments().getParcelableArrayList(KEY_MY_PHOTO_LIST);
                photoList = convertMyPhotoToPhoto(myPhotoList);
                isMyPhotoList = getArguments().getBoolean(KEY_IS_MY_PHOTO_LIST);
            }
        }


        if (isWaitQuery) {
            photoList = new ArrayList<>();
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
        progressBar = rootView.findViewById(R.id.progressBar);

        adapter = new WallpaperListAdapter(getContext());
        rvWallpaperList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        int space = getResources().getDimensionPixelSize(R.dimen.item_space);
        rvWallpaperList.addItemDecoration(new GridSpacingItemDecoration(3, space, true));
        rvWallpaperList.setAdapter(adapter);
        rvWallpaperList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                rvWallpaperList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Photo photo = photoList.get(position);
                startWallpaperActivity(view, photo);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        progressBar.show();

        if (photoList == null) {
            getPhotos();
        } else {
            display(photoList);
        }
    }

    private void startWallpaperActivity(View view, Photo photo) {
        Intent intent = new Intent(getContext(), WallpaperPreviewActivity.class);
        intent.putExtra(Photo.class.getSimpleName(), photo);
        intent.putExtra(KEY_IS_MY_PHOTO_LIST, isMyPhotoList);

        if (Build.VERSION.SDK_INT < 21) {
            startActivity(intent);
        } else {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(getActivity(), view,
                            getString(R.string.transition_photo));
            startActivity(intent, options.toBundle());
        }
    }

    private void display(List<Photo> photos) {
        progressBar.hide();

        if (photos == null) {
            tvError.setVisibility(View.VISIBLE);
            rvWallpaperList.setVisibility(View.GONE);
        } else if (photos.size() == 0) {
            tvError.setVisibility(View.VISIBLE);
            rvWallpaperList.setVisibility(View.GONE);
            tvError.setText(R.string.not_found_any_result);
        } else {
            tvError.setVisibility(View.GONE);
            rvWallpaperList.setVisibility(View.VISIBLE);
            adapter.setPhotoList(photos);
            adapter.notifyDataSetChanged();
        }
    }

    private void getPhotos() {
        progressBar.show();

        Call<List<Photo>> call;

        if (collectionId != null) {
            call = api.getCollectionPhotos(collectionId, null, WallSplash.MAX_RESULT_PER_PAGE);
        } else {
            call = api.getPhotos(null, WallSplash.MAX_RESULT_PER_PAGE, null);
        }

        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()) {
                    photoList = response.body();
                    display(photoList);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                display(null);
            }
        });
    }

    private List<Photo> convertMyPhotoToPhoto(List<MyPhoto> myPhotoList) {
        List<Photo> newPhotoList = new ArrayList<>();

        for (int i = 0; i < myPhotoList.size(); i++) {
            newPhotoList.add(myPhotoList.get(i).getPhoto());
        }

        return newPhotoList;
    }

    public void queryWallpaper(String query) {
        progressBar.show();
        Call<SearchResults> call = api.searchPhotos(query, null, WallSplash.MAX_RESULT_PER_PAGE);
        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                if (response.isSuccessful()) {
                    isWaitQuery = false;
                    SearchResults searchResults = response.body();
                    photoList = searchResults.getResults();
                    display(photoList);
                }
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                display(null);
            }
        });
    }
}
