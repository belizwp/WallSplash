package kmitl.afinal.nakarin58070064.wallsplash.fragment;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.MyCollectionAdapter;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.RecyclerItemClickListener;
import kmitl.afinal.nakarin58070064.wallsplash.database.WallSplashDatabase;
import kmitl.afinal.nakarin58070064.wallsplash.model.GridSpacingItemDecoration;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyCollection;
import kmitl.afinal.nakarin58070064.wallsplash.task.AddCollectionTask;
import kmitl.afinal.nakarin58070064.wallsplash.task.DeleteCollectionTask;
import kmitl.afinal.nakarin58070064.wallsplash.task.LoadCollectionTask;
import kmitl.afinal.nakarin58070064.wallsplash.task.UpdateCollectionTask;

public class MyCollectionFragment extends Fragment {

    private WallSplashDatabase database;

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
        initDB();

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
                            add();
                        } else if (type == MyCollectionAdapter.ViewType.ITEM_COLLECTION) {
                            // TODO: start CollectionActivity
                            showToast("Click Collection");
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        int type = adapter.getItemViewType(position);
                        if (type == MyCollectionAdapter.ViewType.ITEM_COLLECTION) {
                            showDialog(adapter.getMyCollection(position));
                        }
                    }
                }));
    }

    private void initDB() {
        database = Room.databaseBuilder(getContext(), WallSplashDatabase.class, "DB")
                .fallbackToDestructiveMigration()
                .build();
    }

    private void loadData() {
        new LoadCollectionTask(database, new LoadCollectionTask.OnPostLoadListener() {
            @Override
            public void onPostLoad(List<MyCollection> myCollections) {
                adapter.setMyCollectionList(myCollections);
                adapter.notifyDataSetChanged();
            }
        }).execute();
    }

    private void add() {
        final MyCollection myCollection = new MyCollection();
        myCollection.setTitle("Test Add");

        new AddCollectionTask(database, new AddCollectionTask.OnPostAddListener() {
            @Override
            public void onPostAdd() {
                loadData();
            }
        }).execute(myCollection);
    }

    private void edit(MyCollection myCollection) {
        myCollection.setTitle("Test Edited");

        new UpdateCollectionTask(database, new UpdateCollectionTask.OnPostUpdateListener() {
            @Override
            public void onPostUpdate() {
                loadData();
            }
        }).execute(myCollection);
    }

    private void delete(MyCollection myCollection) {
        new DeleteCollectionTask(database, new DeleteCollectionTask.OnPostDeleteListener() {
            @Override
            public void onPostDelete() {
                loadData();
            }
        }).execute(myCollection);
    }

    private void showDialog(final MyCollection myCollection) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(R.array.my_collection_option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case 0:
                        edit(myCollection);
                        break;
                    case 1:
                        delete(myCollection);
                        break;
                    default:
                        dialogInterface.dismiss();
                }
            }
        });
        builder.create().show();
    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
