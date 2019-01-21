package com.cyplay.atproj.asperteam.band;

import android.content.Context;

/**
 * Created by andre on 21-Jan-19.
 */

public class BandConnectionRule {

    static public Permission getConnectionPermission(Context context) {
        return Permission.ACCEPT;
    }

    public  enum Permission {
        ACCEPT,
        NOT_ISTALLED,
        BLUETOOTH_DISABLED
    }
}
