package com.cyplay.atproj.asperteam.band;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by andre on 04-Dec-18.
 */

public class AtBandUtil {

    public static boolean isAppInstalled(Context context) {
        String uri = "com.microsoft.kapp";
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

}
