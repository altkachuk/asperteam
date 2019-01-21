package atproj.cyplay.com.asperteamapi.dagger.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andre on 24-Mar-18.
 */

@Module
public class ApplicationModule {

    private final Application _application;

    public ApplicationModule(Application application) {
        _application = application;
    }

    @Provides
    Application provideApplication() {
        return _application;
    }

    @Provides
    Context provideContext() {
        return _application.getApplicationContext();
    }
}
