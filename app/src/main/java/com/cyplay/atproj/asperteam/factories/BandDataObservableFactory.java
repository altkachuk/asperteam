package com.cyplay.atproj.asperteam.factories;

import android.app.Activity;
import android.content.Context;

import com.microsoft.band.UserConsent;

import goosante.neogia.xyz.stresslibrary.model.Band;
import goosante.neogia.xyz.stresslibrary.model.BandData;
import goosante.neogia.xyz.stresslibrary.model.BandDataListener;
import goosante.neogia.xyz.stresslibrary.model.MsBand;
import goosante.neogia.xyz.stresslibrary.model.RRIntervalData;
import goosante.neogia.xyz.stresslibrary.model.Signal;
import io.reactivex.Observable;
import io.reactivex.android.MainThreadDisposable;

/**
 * Created by andre on 20-Nov-18.
 */

public class BandDataObservableFactory {

    public static <T extends BandData> Observable<T> createBandDataObservable(Context context, Signal signal) {
        return Observable.create(
                subscriber -> {
                    MainThreadDisposable.verifyMainThread();

                    Band band = Band.getSelectedBand();
                    band.addDataListener(signal, new BandDataListener<T>() {
                        @Override
                        public void onBandDataReception(T data) {
                            if (subscriber.isDisposed()) {
                                return;
                            }

                            subscriber.onNext(data);
                        }
                    });
                }
        );
    }
}
