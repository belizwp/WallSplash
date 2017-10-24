package kmitl.afinal.nakarin58070064.wallsplash.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.CollectionListAdapter;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.WallpaperListAdapter;
import kmitl.afinal.nakarin58070064.wallsplash.model.GridSpacingItemDecoration;
import kmitl.afinal.nakarin58070064.wallsplash.util.ScreenUtils;

public class CollectionListFragment extends Fragment {

    private RecyclerView rvCollectionList;
    private TextView tvError;
    private CollectionListAdapter adapter;

    public static CollectionListFragment newInstance() {
        Bundle args = new Bundle();
        CollectionListFragment fragment = new CollectionListFragment();
        fragment.setArguments(args);
        return fragment;
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

        adapter = new CollectionListAdapter();
        rvCollectionList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCollectionList.setAdapter(adapter);
    }
}
