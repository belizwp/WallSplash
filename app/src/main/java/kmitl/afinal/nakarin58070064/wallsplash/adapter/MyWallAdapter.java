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
import kmitl.afinal.nakarin58070064.wallsplash.adapter.holder.MyWallHolder;
import kmitl.afinal.nakarin58070064.wallsplash.model.MyPhoto;
import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;

public class MyWallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MyPhoto> myPhotoList;

    public MyWallAdapter(Context context) {
        this.context = context;
        myPhotoList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.item_my_wallpaper, parent, false);

        return new MyWallHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyPhoto photo = myPhotoList.get(position);

        Glide.with(context)
                .load(photo.getImageSmall())
                .into(((MyWallHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        return myPhotoList.size();
    }

    public List<MyPhoto> getMyPhotoList() {
        return myPhotoList;
    }

    public void setMyPhotoList(List<MyPhoto> myPhotoList) {
        this.myPhotoList = myPhotoList;
    }
}
