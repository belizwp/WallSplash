package kmitl.afinal.nakarin58070064.wallsplash.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.MyCollectionAdapter;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.RecyclerItemClickListener;
import kmitl.afinal.nakarin58070064.wallsplash.database.DatabaseManager;
import kmitl.afinal.nakarin58070064.wallsplash.database.WallSplashDatabase;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.GridSpacingItemDecoration;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyCollection;
import kmitl.afinal.nakarin58070064.wallsplash.task.AddCollectionTask;
import kmitl.afinal.nakarin58070064.wallsplash.task.DeleteCollectionTask;
import kmitl.afinal.nakarin58070064.wallsplash.task.LoadCollectionTask;
import kmitl.afinal.nakarin58070064.wallsplash.task.UpdateCollectionTask;

public class MyCollectionFragment extends Fragment {

    private WallSplashDatabase database;

    private RecyclerView rvMyCollection;
    private MyCollectionAdapter adapter;

    private MyCollectionFragmentListener listener;

    private final int EDIT = 0;
    private final int DELETE = 1;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyCollectionFragmentListener) {
            listener = (MyCollectionFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MyCollectionFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
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
                            showCreateDialog(null, -1);
                        } else if (type == MyCollectionAdapter.ViewType.ITEM_COLLECTION) {
                            listener.onCollectionClick(adapter.getMyCollection(position));
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        int type = adapter.getItemViewType(position);
                        if (type == MyCollectionAdapter.ViewType.ITEM_COLLECTION) {
                            showDialog(adapter.getMyCollection(position), position);
                        }
                    }
                }));
    }

    private void initDB() {
        database = DatabaseManager.getInstance().getDatabase();
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

    private void add(final MyCollection myCollection) {
        new AddCollectionTask(database, new AddCollectionTask.OnPostAddListener() {
            @Override
            public void onPostAdd(long id) {
                myCollection.setId((int) id);
                adapter.getMyCollectionList().add(myCollection);
                adapter.notifyItemInserted(adapter.getItemCount());
                rvMyCollection.smoothScrollToPosition(adapter.getItemCount());
            }
        }).execute(myCollection);
    }

    private void edit(final MyCollection edited, final int position) {
        new UpdateCollectionTask(database, new UpdateCollectionTask.OnPostUpdateListener() {
            @Override
            public void onPostUpdate() {
                int listIndex = position - 1;
                adapter.getMyCollectionList().set(listIndex, edited);
                adapter.notifyItemChanged(position);
            }
        }).execute(edited);
    }

    private void delete(final MyCollection myCollection, final int position) {
        new DeleteCollectionTask(database, new DeleteCollectionTask.OnPostDeleteListener() {
            @Override
            public void onPostDelete() {
                adapter.getMyCollectionList().remove(myCollection);
                adapter.notifyItemRemoved(position);
            }
        }).execute(myCollection);
    }

    private void showDialog(final MyCollection myCollection, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(R.array.my_collection_option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case EDIT:
                        showCreateDialog(myCollection, position);
                        break;
                    case DELETE:
                        delete(myCollection, position);
                        break;
                    default:
                        dialogInterface.dismiss();
                }
            }
        });
        builder.create().show();
    }

    private void showCreateDialog(final MyCollection myCollection, final int position) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        } else {
            builder = new AlertDialog.Builder(getActivity());
        }

        builder.setTitle(R.string.title_of_collection);

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        if (myCollection != null) {
            input.setText(myCollection.getTitle());

            builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myCollection.setTitle(input.getText().toString());
                    edit(myCollection, position);
                }
            });
        } else {
            builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyCollection newCollection = new MyCollection();
                    newCollection.setTitle(input.getText().toString());
                    add(newCollection);
                }
            });
        }

        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    public interface MyCollectionFragmentListener {
        void onCollectionClick(MyCollection collection);
    }
}
