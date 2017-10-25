package kmitl.afinal.nakarin58070064.wallsplash.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static ApiManager instance;

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    private ApiManager() {

    }

    private UnsplashAPI unsplashAPI;

    public UnsplashAPI getUnsplashApi(final String applicationID) {
        if (unsplashAPI == null) {
            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            request = request.newBuilder()
                                    .addHeader("Authorization", "Client-ID " + applicationID)
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .build();

            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl(UnsplashAPI.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            unsplashAPI = retrofit.create(UnsplashAPI.class);
        }
        return unsplashAPI;
    }
}
