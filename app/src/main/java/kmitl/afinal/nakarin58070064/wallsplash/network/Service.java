package kmitl.afinal.nakarin58070064.wallsplash.network;

import android.support.annotation.Nullable;

import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.model.Collection;
import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;
import kmitl.afinal.nakarin58070064.wallsplash.model.SearchCollectionResults;
import kmitl.afinal.nakarin58070064.wallsplash.model.SearchResults;
import kmitl.afinal.nakarin58070064.wallsplash.network.api.UnsplashAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Service {

    private UnsplashAPI api;
    private ServiceListener listener;

    public Service(UnsplashAPI api, ServiceListener listener) {
        this.api = api;
        this.listener = listener;
    }

    public void getCollectionPhotos(@Nullable String collectionId, @Nullable Integer page, @Nullable Integer per_page) {
        Call<List<Photo>> call = api.getCollectionPhotos(collectionId, page, per_page);
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()) {
                    ((ServicePhotoListener) listener).onLoadPhotosSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void getCollections(@Nullable Integer page, @Nullable Integer per_page) {
        Call<List<Collection>> call = api.getCollections(page, per_page);
        call.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if (response.isSuccessful()) {
                    ((ServiceCollectionListener) listener).onLoadCollectionSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void getPhotos(@Nullable Integer page, @Nullable Integer per_page, @Nullable String order) {
        Call<List<Photo>> call = api.getPhotos(page, per_page, order);
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()) {
                    ((ServicePhotoListener) listener).onLoadPhotosSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void searchPhotos(@Nullable String query, @Nullable Integer page, @Nullable Integer per_page) {
        Call<SearchResults> call = api.searchPhotos(query, page, per_page);
        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                if (response.isSuccessful()) {
                    ((ServicePhotoListener) listener).onSearchResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void searchCollections(@Nullable String query, @Nullable Integer page, @Nullable Integer per_page) {
        Call<SearchCollectionResults> call = api.searchCollections(query, page, per_page);
        call.enqueue(new Callback<SearchCollectionResults>() {
            @Override
            public void onResponse(Call<SearchCollectionResults> call, Response<SearchCollectionResults> response) {
                if (response.isSuccessful()) {
                    ((ServiceCollectionListener) listener).onSearchCollectionResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchCollectionResults> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    private interface ServiceListener {
        void onFailure(Throwable t);
    }

    public interface ServicePhotoListener extends ServiceListener {
        void onLoadPhotosSuccess(List<Photo> photoList);

        void onSearchResult(SearchResults searchResults);
    }

    public interface ServiceCollectionListener extends ServiceListener {
        void onLoadCollectionSuccess(List<Collection> collectionList);

        void onSearchCollectionResult(SearchCollectionResults searchCollectionResults);
    }
}
