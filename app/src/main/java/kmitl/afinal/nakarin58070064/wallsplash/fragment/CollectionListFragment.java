package kmitl.afinal.nakarin58070064.wallsplash.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.activity.CollectionActivity;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.CollectionListAdapter;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.RecyclerItemClickListener;
import kmitl.afinal.nakarin58070064.wallsplash.model.Collection;
import kmitl.afinal.nakarin58070064.wallsplash.model.GridSpacingItemDecoration;
import kmitl.afinal.nakarin58070064.wallsplash.network.ApiManager;
import kmitl.afinal.nakarin58070064.wallsplash.network.UnsplashAPI;
import kmitl.afinal.nakarin58070064.wallsplash.util.ScreenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionListFragment extends Fragment {

    private static final String KEY_COLLECTIONS = "COLLECTION_LIST";

    private RecyclerView rvCollectionList;
    private TextView tvError;
    private CollectionListAdapter adapter;

    private UnsplashAPI api;
    private List<Collection> collectionList;

    public static CollectionListFragment newInstance() {
        Bundle args = new Bundle();
        CollectionListFragment fragment = new CollectionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = ApiManager.getInstance().getUnsplashApi(getString(R.string.application_id));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_COLLECTIONS, (ArrayList<? extends Parcelable>) collectionList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collection_list, container, false);
        initInstance(rootView, savedInstanceState);
        return rootView;
    }


    private void initInstance(View rootView, Bundle savedInstanceState) {
        rvCollectionList = rootView.findViewById(R.id.rvCollectionList);
        tvError = rootView.findViewById(R.id.tvError);

        adapter = new CollectionListAdapter(getContext());
        rvCollectionList.setLayoutManager(new LinearLayoutManager(getContext()));
        int space = (int) ScreenUtils.convertDpToPixel(8, getContext());
        rvCollectionList.addItemDecoration(new GridSpacingItemDecoration(1, space, true));
        rvCollectionList.setAdapter(adapter);
        rvCollectionList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                rvCollectionList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Collection collection = collectionList.get(position);
                transition(view, collection);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        if (savedInstanceState != null) {
            collectionList = savedInstanceState.getParcelableArrayList(KEY_COLLECTIONS);
            display();
        } else {
            getCollections();
        }
    }

    private void transition(View view, Collection collection) {
        Intent intent = new Intent(getContext(), CollectionActivity.class);
        intent.putExtra(Collection.class.getSimpleName(), collection);
        startActivity(intent);
    }

    private void display() {
        adapter.setCollectionList(collectionList);
        adapter.notifyDataSetChanged();
    }

    private void getCollections() {
        Call<List<Collection>> call = api.getCollections(null, null);
        call.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if (response.isSuccessful()) {
                    collectionList = response.body();
                    display();
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {

            }
        });
    }
}
