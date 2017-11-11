package kmitl.afinal.nakarin58070064.wallsplash.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.R;

public class ShowcaseFragment extends Fragment {

    private static final String KEY_IS_WAIT_QUERY = "IS_WAIT_QUERY";

    private boolean isWaitQuery;

    public static ShowcaseFragment newInstance(boolean isWaitQuery) {
        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_WAIT_QUERY, isWaitQuery);
        ShowcaseFragment fragment = new ShowcaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isWaitQuery = getArguments().getBoolean(KEY_IS_WAIT_QUERY, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_showcase, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        ViewPager viewPager = rootView.findViewById(R.id.viewPager);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return WallpaperListFragment.newInstance(isWaitQuery);
                    case 1:
                        return CollectionListFragment.newInstance(isWaitQuery);
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

    public void query(String query) {
        List<Fragment> fragmentList = getChildFragmentManager().getFragments();

        for (int i = 0; i < fragmentList.size(); i++) {

            Fragment fragment = fragmentList.get(i);

            if (fragment instanceof WallpaperListFragment) {
                ((WallpaperListFragment) fragment).queryWallpaper(query);

            } else if (fragment instanceof CollectionListFragment) {
                ((CollectionListFragment) fragment).queryCollection(query);
            }
        }
    }
}
