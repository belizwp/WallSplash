package kmitl.afinal.nakarin58070064.wallsplash.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.holder.CollectionHolder;
import kmitl.afinal.nakarin58070064.wallsplash.model.Collection;

public class CollectionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Collection> collectionList;

    public CollectionListAdapter(Context context) {
        this.context = context;
        collectionList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.item_collection, parent, false);

        return new CollectionHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CollectionHolder collectionHolder = (CollectionHolder) holder;
        Collection collection = collectionList.get(position);

        collectionHolder.textTitle.setText(collection.getTitle());
        collectionHolder.textName.setText(collection.getUser().getName());
        collectionHolder.textTotalPhotos.setText(
                context.getString(R.string.total_photos, collection.getTotalPhotos()));

        Glide.with(context)
                .load(collection.getUser().getProfileImage().getMedium())
                .into(collectionHolder.imageProfile);

        if (collection.getCoverPhoto() != null) {
            collectionHolder.imageView.setBackgroundColor(
                    Color.parseColor(collection.getCoverPhoto().getColor()));

            Glide.with(context)
                    .load(collection.getCoverPhoto().getUrls().getSmall())
                    .into(collectionHolder.imageView);
        } else {
            collectionHolder.imageView.setBackgroundColor(Color.BLACK);
            collectionHolder.imageView.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public void setCollectionList(List collectionList) {
        this.collectionList = collectionList;
    }
}
