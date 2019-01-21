package com.cyplay.atproj.asperteam.band;

import android.app.Activity;

import atproj.cyplay.com.asperteamapi.domain.interactor.StressInteractor;

/**
 * Created by andre on 21-Jan-19.
 */

public interface IBand {

    void init(StressInteractor stressInteractor, String id, float levelMax);

    void addListener(BandListener listener);
    void removeListener(BandListener listener);
    void registerListener(Activity activity);
    void registerListener();

    void gotoIdle();
    void gotoActive();

    void subscribe();
    void connect();
    void stop();
}
