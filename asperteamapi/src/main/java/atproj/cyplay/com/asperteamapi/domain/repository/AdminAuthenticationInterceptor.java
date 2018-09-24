package atproj.cyplay.com.asperteamapi.domain.repository;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by andre on 17-Apr-18.
 */

public class AdminAuthenticationInterceptor implements Interceptor {

    private static String admin_token = "f42946f30ac7ec4f226707627c9b49e1dafc2c0e";

    public AdminAuthenticationInterceptor() {
        ;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request authorisedRequest = originalRequest.newBuilder()
                .header("Authorization", "Token " + admin_token)
                .build();
        return chain.proceed(authorisedRequest);
    }
}
