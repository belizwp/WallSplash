package kmitl.afinal.nakarin58070064.wallsplash.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;

import kmitl.afinal.nakarin58070064.wallsplash.R;
import kmitl.afinal.nakarin58070064.wallsplash.fragment.ShowcaseFragment;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private final String TAG = ShowcaseFragment.class.getSimpleName();

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initInstances(savedInstanceState);
    }

    private void initInstances(Bundle savedInstanceState) {
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_container, ShowcaseFragment.newInstance(true), TAG)
                    .commit();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ShowcaseFragment showcaseFragment = (ShowcaseFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        showcaseFragment.query(query);
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
