package kmitl.afinal.nakarin58070064.wallsplash.network.api;

import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;
import kmitl.afinal.nakarin58070064.wallsplash.model.SearchCollectionResults;
import kmitl.afinal.nakarin58070064.wallsplash.model.SearchResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UnsplashAPI {

    String BASE_URL = "https://api.unsplash.com/";

    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") String id, @Query("w") Integer width, @Query("h") Integer height);

    @GET("photos")
    Call<List<Photo>> getPhotos(@Query("page") Integer page, @Query("per_page") Integer perPage,
                                @Query("order_by") String orderBy);

    @GET("search/photos")
    Call<SearchResults> searchPhotos(@Query("query") String query, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("search/collections")
    Call<SearchCollectionResults> searchCollections(@Query("query") String query, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("collections")
    Call<List<kmitl.afinal.nakarin58070064.wallsplash.model.Collection>> getCollections(@Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("collections/{id}/photos")
    Call<List<Photo>> getCollectionPhotos(@Path("id") String id, @Query("page") Integer page, @Query("per_page") Integer perPage);

}
