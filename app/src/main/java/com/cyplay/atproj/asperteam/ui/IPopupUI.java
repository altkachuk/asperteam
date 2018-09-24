package com.cyplay.atproj.asperteam.ui;

import android.arch.core.util.Function;

/**
 * Created by andre on 06-Jun-18.
 */

public interface IPopupUI {
    void showPopup(int requestCode, String title, String description, String positiveText);

    void showPopup(int requestCode, int imageRes, String title, String description, String positiveText);

    void showPopup(int requestCode, String title, String description, String positiveText, String negativeText);

    void showPopup(int requestCode, int imageRes, String title, String description, String positiveText, String negativeText);
}
