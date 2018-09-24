package atproj.cyplay.com.asperteamapi.dagger.module;

import android.content.Context;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by andre on 06-Apr-18.
 */

@Module
public class PicassoModule {

    @Provides
    OkHttp3Downloader provideOkHttp3Downloader(OkHttpClient okHttpClient){
        return new OkHttp3Downloader(okHttpClient);
    }

    @Provides
    Picasso providePicasso(Context context, OkHttp3Downloader okHttp3Downloader) {
        return new Picasso.Builder(context).
                downloader(okHttp3Downloader).
                build();
    }
}
