package com.cyplay.atproj.asperteam.utils;

import android.app.Activity;
import android.content.Context;

import com.cyplay.atproj.asperteam.factories.BandDataObservableFactory;

import goosante.neogia.xyz.stresslibrary.model.RRIntervalData;
import goosante.neogia.xyz.stresslibrary.model.Signal;
import goosante.neogia.xyz.stresslibrary.toolbox.ArrayOperation;
import io.reactivex.Observable;

/**
 * Created by andre on 20-Nov-18.
 */

public class StressDetector {

    public static final int RRI_BUFFER_SIZE = 60;
    public static final float STRESS_THRESHOLD = 0.914f;
    public static final int CANCEL_PERIOD = 1000 * 60 * 20; // 20 minutes

    private static float maxRmssd = 0.0f;
    private static long lastCanceled = System.currentTimeMillis();

    public static Observable<StressEvent> create(Context context) {
        return createRRIntervalObservable(context)
                .map(rriData -> rriData.getRri())
                .buffer(RRI_BUFFER_SIZE, 1)
                .map(rris -> new StressEvent(rris.get(rris.size() - 1), ArrayOperation.rmssd(rris.toArray()), 0))
                .map(stressEvent -> new StressEvent(stressEvent.rri, stressEvent.rmssd, calcStressLevel(stressEvent.rmssd)));
    }

    private static Observable<RRIntervalData> createRRIntervalObservable(Context context) {
        return BandDataObservableFactory.<RRIntervalData>createBandDataObservable(context, Signal.RRI);
    }

    private static float calcStressLevel(float rmssd) {
        if (rmssd > maxRmssd) {
            maxRmssd = rmssd;
        }

        long now = System.currentTimeMillis();
        if (now - lastCanceled  >= CANCEL_PERIOD) {
            maxRmssd = rmssd;
            lastCanceled = now;
        }

        float relRmssd = rmssd / maxRmssd;
        float stressLevel = (1 - relRmssd) * STRESS_THRESHOLD;

        return stressLevel;
    }

    public static class StressEvent {
        public final int rri;
        public final float rmssd;
        public final float stressLevel;

        private StressEvent(int rri, float rmssd, float stressLevel) {
            this.rri = rri;
            this.rmssd = rmssd;
            this.stressLevel = stressLevel;
        }

        @Override
        public String toString() {
            return "StressEvent: {" +
                    "rri: " + rri + ";" +
                    "rmssd: " + rmssd + ";" +
                    "stressLevel: " + stressLevel + ";" +
                    "}";
        }
    }
}
