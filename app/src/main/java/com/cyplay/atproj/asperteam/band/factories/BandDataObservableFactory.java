package com.cyplay.atproj.asperteam.band.factories;

import com.cyplay.atproj.asperteam.band.AtBand;

import io.reactivex.Observable;
import io.reactivex.android.MainThreadDisposable;

/**
 * Created by andre on 20-Nov-18.
 */

public class BandDataObservableFactory {

    static private long lastUpdated = System.currentTimeMillis();

    public static long getLastUpdated() {
        return lastUpdated;
    }

    public static Observable<Integer> createBandDataObservable() {
        return Observable.create(
                subscriber -> {
                    MainThreadDisposable.verifyMainThread();

                    AtBand band = AtBand.getInstance();
                    band.setListener(new AtBand.AtBandListener() {
                        @Override
                        public void onRecieve(int rri) {
                            if (subscriber.isDisposed()) {
                                return;
                            }

                            subscriber.onNext(rri);
                        }
                    });
                }
        );
    }
}
