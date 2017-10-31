package kmitl.afinal.nakarin58070064.wallsplash.network;

import java.util.Collection;
import java.util.List;

import kmitl.afinal.nakarin58070064.wallsplash.model.Photo;
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

    @GET("photos/curated")
    Call<List<Photo>> getCuratedPhotos(@Query("page") Integer page, @Query("per_page") Integer perPage,
                                       @Query("order_by") String orderBy);

    @GET("photos/random")
    Call<Photo> getRandomPhoto(@Query("collections") String collections,
                               @Query("featured") Boolean featured, @Query("username") String username,
                               @Query("query") String query, @Query("w") Integer width,
                               @Query("h") Integer height, @Query("orientation") String orientation);

    @GET("photos/random")
    Call<List<Photo>> getRandomPhotos(@Query("collections") String collections,
                                      @Query("featured") boolean featured, @Query("username") String username,
                                      @Query("query") String query, @Query("w") Integer width,
                                      @Query("h") Integer height, @Query("orientation") String orientation,
                                      @Query("count") Integer count);

    @GET("photos/{id}/download")
    Call<String> getPhotoDownloadLink(@Path("id") String id);

    @GET("search/photos")
    Call<SearchResults> searchPhotos(@Query("query") String query, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("collections")
    Call<List<kmitl.afinal.nakarin58070064.wallsplash.model.Collection>> getCollections(@Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("collections/features")
    Call<List<Collection>> getFeaturedCollections(@Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("collections/curated")
    Call<List<Collection>> getCuratedCollections(@Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("collections/{id}")
    Call<Collection> getCollection(@Path("id") String id);

    @GET("collections/curated/{id}")
    Call<Collection> getCuratedCollection(@Path("id") String id);

    @GET("collections/{id}/photos")
    Call<List<Photo>> getCollectionPhotos(@Path("id") String id, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("collections/curated/{id}/photos")
    Call<List<Photo>> getCuratedCollectionPhotos(@Path("id") String id, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @GET("collections/{id}/related")
    Call<List<Collection>> getRelatedCollections(@Path("id") String id);
}
