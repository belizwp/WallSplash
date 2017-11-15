package kmitl.afinal.nakarin58070064.wallsplash.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kmitl.afinal.nakarin58070064.wallsplash.R;

public class MyCollectionHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textTitle;
    public TextView textPhotos;

    public MyCollectionHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView);
        textTitle = itemView.findViewById(R.id.tvTitle);
        textPhotos = itemView.findViewById(R.id.totalPhotos);

    }
}
