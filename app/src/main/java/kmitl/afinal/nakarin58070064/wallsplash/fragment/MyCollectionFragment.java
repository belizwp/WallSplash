package kmitl.afinal.nakarin58070064.wallsplash.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.MyCollectionAdapter;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.RecyclerItemClickListener;
import kmitl.afinal.nakarin58070064.wallsplash.model.GridSpacingItemDecoration;

public class MyCollectionFragment extends Fragment {

    private RecyclerView rvMyCollection;
    private MyCollectionAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_collection, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        rvMyCollection = rootView.findViewById(R.id.rvMyCollection);

        adapter = new MyCollectionAdapter(getContext());
        rvMyCollection.setLayoutManager(new LinearLayoutManager(getContext()));
        int space = getResources().getDimensionPixelSize(R.dimen.item_space);
        rvMyCollection.addItemDecoration(new GridSpacingItemDecoration(1, space, true));
        rvMyCollection.setAdapter(adapter);
        rvMyCollection.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvMyCollection,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        int type = adapter.getItemViewType(position);
                        if (type == MyCollectionAdapter.ViewType.ITEM_CREATE) {
                            // TODO: show create collection dialog
                            showToast("Create Collection");
                        } else if (type == MyCollectionAdapter.ViewType.ITEM_COLLECTION) {
                            // TODO: start CollectionActivity
                            showToast("Click Collection");
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        int type = adapter.getItemViewType(position);
                        if (type == MyCollectionAdapter.ViewType.ITEM_COLLECTION) {
                            // TODO: edit collection
                            showToast("Edit Collection");
                        }
                    }
                }));
    }

    private void initDB() {

    }

    private void loadData() {

    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
