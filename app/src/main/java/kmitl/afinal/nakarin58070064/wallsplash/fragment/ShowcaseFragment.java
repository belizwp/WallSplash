package kmitl.afinal.nakarin58070064.wallsplash.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import kmitl.afinal.nakarin58070064.wallsplash.R;

public class ShowcaseFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_showcase, container, false);
        initInstances(rootView, savedInstanceState);
        setHasOptionsMenu(true);
        return rootView;
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        tabLayout = rootView.findViewById(R.id.tabLayout);
        viewPager = rootView.findViewById(R.id.viewPager);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return WallpaperListFragment.newInstance();
                    case 1:
                        return CollectionListFragment.newInstance();
                    default:
                        return null;
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.photos);
                    case 1:
                        return getString(R.string.collections);
                    default:
                        return null;
                }
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_showcase, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
