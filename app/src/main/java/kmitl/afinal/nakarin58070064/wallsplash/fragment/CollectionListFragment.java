package kmitl.afinal.nakarin58070064.wallsplash.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.WallSplash;
import kmitl.afinal.nakarin58070064.wallsplash.activity.CollectionPreviewActivity;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.CollectionListAdapter;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.RecyclerItemClickListener;
import kmitl.afinal.nakarin58070064.wallsplash.model.Collection;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.GridSpacingItemDecoration;
import kmitl.afinal.nakarin58070064.wallsplash.model.SearchCollectionResults;
import kmitl.afinal.nakarin58070064.wallsplash.network.Service;
import kmitl.afinal.nakarin58070064.wallsplash.network.api.ApiManager;
import kmitl.afinal.nakarin58070064.wallsplash.network.api.UnsplashAPI;

public class CollectionListFragment extends Fragment implements Service.ServiceCollectionListener {

    private static final String KEY_COLLECTIONS = "COLLECTION_LIST";
    private static final String KEY_IS_WAIT_QUERY = "IS_WAIT_QUERY";
    private static final String KEY_QUERY = "QUERY";

    private RecyclerView rvCollectionList;
    private TextView tvError;
    private CollectionListAdapter adapter;
    private ContentLoadingProgressBar progressBar;

    private Service service;
    private List<Collection> collectionList;

    private boolean isWaitQuery;
    private String query;

    public static CollectionListFragment newInstance(boolean isWaitQuery) {
        CollectionListFragment fragment = new CollectionListFragment();
        fragment.isWaitQuery = isWaitQuery;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UnsplashAPI api = ApiManager.getInstance().getUnsplashApi(getString(R.string.application_id));
        service = new Service(api, this);

        onRestoreInstanceState(savedInstanceState);
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            collectionList = savedInstanceState.getParcelableArrayList(KEY_COLLECTIONS);
            isWaitQuery = savedInstanceState.getBoolean(KEY_IS_WAIT_QUERY);
            query = savedInstanceState.getString(KEY_QUERY);
        }

        if (isWaitQuery) {
            collectionList = new ArrayList<>();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_COLLECTIONS, (ArrayList<? extends Parcelable>) collectionList);
        outState.putBoolean(KEY_IS_WAIT_QUERY, isWaitQuery);
        outState.putString(KEY_QUERY, query);
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
        progressBar = rootView.findViewById(R.id.progressBar);

        adapter = new CollectionListAdapter(getContext());
        rvCollectionList.setLayoutManager(new LinearLayoutManager(getContext()));
        int space = getResources().getDimensionPixelSize(R.dimen.item_space);
        rvCollectionList.addItemDecoration(new GridSpacingItemDecoration(1, space, true));
        rvCollectionList.setAdapter(adapter);
        rvCollectionList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                rvCollectionList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Collection collection = collectionList.get(position);
                startCollectionActivity(collection);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        progressBar.show();

        if (collectionList == null) {
            getCollections();
        } else {
            display(collectionList);
        }
    }

    private void startCollectionActivity(Collection collection) {
        Intent intent = new Intent(getContext(), CollectionPreviewActivity.class);
        intent.putExtra(Collection.class.getSimpleName(), collection);
        startActivity(intent);
    }

    private void display(List<Collection> collections) {
        progressBar.hide();

        if (collections == null) {
            tvError.setVisibility(View.VISIBLE);
            rvCollectionList.setVisibility(View.GONE);
        } else if (collections.size() == 0) {
            tvError.setVisibility(View.VISIBLE);
            rvCollectionList.setVisibility(View.GONE);
            tvError.setText(R.string.not_found_any_result);
        } else {
            collectionList = collections;
            tvError.setVisibility(View.GONE);
            rvCollectionList.setVisibility(View.VISIBLE);
            adapter.setCollectionList(collections);
            adapter.notifyDataSetChanged();
        }
    }

    private void getCollections() {
        progressBar.show();
        service.getCollections(null, WallSplash.MAX_RESULT_PER_PAGE);
    }

    public void queryCollection(String query) {
        progressBar.show();
        this.query = query;
        service.searchCollections(query, null, WallSplash.MAX_RESULT_PER_PAGE);
    }

    @Override
    public void onLoadCollectionSuccess(List<Collection> collectionList) {
        display(collectionList);
    }

    @Override
    public void onSearchCollectionResult(SearchCollectionResults searchCollectionResults) {
        isWaitQuery = false;
        display(searchCollectionResults.getResults());
    }

    @Override
    public void onFailure(Throwable t) {
        display(null);
    }
}
