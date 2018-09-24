package atproj.cyplay.com.asperteamapi.domain.repository;

import android.content.Context;

import java.io.IOException;

import atproj.cyplay.com.asperteamapi.util.NetworkUtil;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by andre on 21-May-18.
 */

public class ForceCacheInterceptor implements Interceptor {

    private Context _context;

    public ForceCacheInterceptor(Context context) {
        _context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (!NetworkUtil.internetAvailable(_context))
            builder.cacheControl(CacheControl.FORCE_CACHE);

        return chain.proceed(builder.build());
    }
}
