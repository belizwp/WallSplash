package kmitl.afinal.nakarin58070064.wallsplash.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kmitl.afinal.nakarin58070064.wallsplash.R;

public class WallpaperListFragment extends Fragment {

    public static WallpaperListFragment newInstance() {
        WallpaperListFragment fragment = new WallpaperListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallpaper_list, container, false);
    }

}
