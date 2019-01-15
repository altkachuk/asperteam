package com.cyplay.atproj.asperteam.views;

/**
 * Created by andre on 10-Jan-19.
 */

public interface StressView extends LoadingView {

    void showPopup();
    void hidePopup();
    boolean checkPermissions();
    void requestPermisions();
    void openProblemCategoriesActivity();
}
