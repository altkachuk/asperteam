package atproj.cyplay.com.asperteamapi.domain.interactor;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;

import atproj.cyplay.com.asperteamapi.model.Language;

import java.util.List;

/**
 * Created by andre on 11-Apr-18.
 */

public interface LanguageInteractor {

    void getAllLanguages(final ResourceRequestCallback<List<Language>> callback);
}
