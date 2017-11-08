package kmitl.afinal.nakarin58070064.wallsplash.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.holder.CreateCollectionHolder;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.holder.MyCollectionHolder;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyCollection;

public class MyCollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MyCollection> myCollectionList;

    public MyCollectionAdapter(Context context) {
        this.context = context;
        myCollectionList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == ViewType.ITEM_CREATE) {
            return new CreateCollectionHolder(inflater.inflate(R.layout.item_create_collection, parent, false));
        } else if (viewType == ViewType.ITEM_COLLECTION) {
            return new MyCollectionHolder(inflater.inflate(R.layout.item_my_collection, parent, false));
        } else {
            throw new NullPointerException("no support viewType.");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyCollectionHolder) {
            MyCollection myCollection = myCollectionList.get(position - 1);
            ((MyCollectionHolder) holder).textTitle.setText(myCollection.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return myCollectionList.size() + 1; //include create menu
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ViewType.ITEM_CREATE;
        } else {
            return ViewType.ITEM_COLLECTION;
        }
    }

    public List<MyCollection> getMyCollectionList() {
        return myCollectionList;
    }

    public void setMyCollectionList(List myCollectionList) {
        this.myCollectionList = myCollectionList;
    }

    public MyCollection getMyCollection(int position) {
        return myCollectionList.get(position - 1);
    }

    public class ViewType {
        public static final int ITEM_CREATE = 0;
        public static final int ITEM_COLLECTION = 1;
    }
}
