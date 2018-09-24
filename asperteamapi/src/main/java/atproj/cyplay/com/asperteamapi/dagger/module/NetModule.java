package atproj.cyplay.com.asperteamapi.dagger.module;

import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andre on 24-Mar-18.
 */

@Module
public class NetModule {

    String _baseUrl;

    public NetModule(String baseUrl) {
        _baseUrl = baseUrl;
    }

    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(_baseUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    AsperTeamApi provideAsperTeamApi(Retrofit retrofit) {
        return retrofit.create(AsperTeamApi.class);
    }

}
