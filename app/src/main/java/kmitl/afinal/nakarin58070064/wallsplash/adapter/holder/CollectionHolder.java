package kmitl.afinal.nakarin58070064.wallsplash.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kmitl.afinal.nakarin58070064.wallsplash.R;

public class CollectionHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public ImageView imageProfile;
    public TextView textName;
    public TextView textTotalPhotos;
    public TextView textTitle;

    public CollectionHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView);
        imageProfile = itemView.findViewById(R.id.imageProfile);
        textName = itemView.findViewById(R.id.tvName);
        textTotalPhotos = itemView.findViewById(R.id.totalPhotos);
        textTitle = itemView.findViewById(R.id.tvTitle);
    }
}
