package kmitl.afinal.nakarin58070064.wallsplash.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.holder.WallpaperHolder;
import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;

public class WallpaperListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Photo> photoList;

    public WallpaperListAdapter(Context context) {
        mContext = context;
        photoList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.item_wallpaper, parent, false);

        return new WallpaperHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WallpaperHolder wallpaperHolder = (WallpaperHolder) holder;
        Photo photo = photoList.get(position);

        Glide.with(mContext)
                .load(photo.getUrls().getSmall())
                .into(wallpaperHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }
}
