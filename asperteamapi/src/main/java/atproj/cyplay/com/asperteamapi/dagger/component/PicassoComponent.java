package atproj.cyplay.com.asperteamapi.dagger.component;

import atproj.cyplay.com.asperteamapi.dagger.module.PicassoModule;
import com.squareup.picasso.Picasso;

import atproj.cyplay.com.asperteamapi.dagger.component.ApplicationComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.OkHttpClientComponent;
import dagger.Component;

;

/**
 * Created by andre on 24-Mar-18.
 */


@Component(modules = {PicassoModule.class}, dependencies = {ApplicationComponent.class, OkHttpClientComponent.class})
public interface PicassoComponent {
    Picasso getPicasso();
}
