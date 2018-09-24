package atproj.cyplay.com.asperteamapi.dagger.module;

import android.app.Application;
import android.content.Context;

import atproj.cyplay.com.asperteamapi.domain.repository.AdminAuthenticationInterceptor;
import atproj.cyplay.com.asperteamapi.domain.repository.AdminTokenAuthenticator;
import atproj.cyplay.com.asperteamapi.domain.repository.ForceCacheInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by andre on 06-Apr-18.
 */

@Module
public class AdminOkHttpClientModule {

    String _baseUrl;

    public AdminOkHttpClientModule(String baseUrl) {
        _baseUrl = baseUrl;
    }

    @Provides
    Cache provideHttpCache(Application application) {
        int cachSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cachSize);
        return cache;
    }

    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides CookieJar provideCookie() {
        OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
        cookieHelper.setCookie(_baseUrl, "cookie_name", "cookie_value");
        return cookieHelper.cookieJar();
    }

    @Provides
    Interceptor provideInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    OkHttpClient provideOkHttpClient(Context context, CookieJar cookie, Cache cache, Interceptor interceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder().cookieJar(cookie);//.sslSocketFactory(SafeOkHttpClientUtil.createAllTrustSSLSocketFactory());
        client.cache(cache);
        client.authenticator(new AdminTokenAuthenticator());
        client.addInterceptor(interceptor);
        client.addInterceptor(new ForceCacheInterceptor(context));
        client.addNetworkInterceptor(new AdminAuthenticationInterceptor());
        client.followRedirects(false);
        return client.build();
    }
}
