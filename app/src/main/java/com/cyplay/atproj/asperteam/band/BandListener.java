package com.cyplay.atproj.asperteam.band;

import com.cyplay.atproj.asperteam.band.detector.StressDetector;

/**
 * Created by andre on 21-Jan-19.
 */

public interface BandListener {
    void onDataUpdate(StressDetector.StressData stressData);
    void onStressDetected(StressDetector.StressData stressData);
    void onUpdate();
}
