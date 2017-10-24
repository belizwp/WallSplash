package kmitl.afinal.nakarin58070064.wallsplash.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.adapter.holder.MyWallHolder;

public class MyWallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.item_my_wallpaper, parent, false);

        return new MyWallHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
