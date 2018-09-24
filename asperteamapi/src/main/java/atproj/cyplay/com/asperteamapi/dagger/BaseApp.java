package atproj.cyplay.com.asperteamapi.dagger;

import android.app.Application;
import android.content.Context;

/**
 * Created by andre on 24-Mar-18.
 */

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void inject(Object object) {
        ;
    }

    public static BaseApp get(Context context) {
        return (BaseApp) context.getApplicationContext();
    }

}
