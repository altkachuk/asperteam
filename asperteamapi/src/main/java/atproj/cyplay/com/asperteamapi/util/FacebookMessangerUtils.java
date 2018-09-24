package atproj.cyplay.com.asperteamapi.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by andre on 22-May-18.
 */

public class FacebookMessangerUtils {

    static public String fb_messanger_uri = "com.facebook.orca";

    static public boolean isInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(fb_messanger_uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    static public void startMessanger(final Activity activity, String fbId) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setPackage("com.facebook.orca");
        intent.setData(Uri.parse("https://m.me/"+fbId));
        activity.startActivity(intent);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        openLastActivity(activity);
                    }
                },
                100);
    }

    static public void startMessanger(final Activity activity) {
        Intent intent = activity.getPackageManager().getLaunchIntentForPackage("com.facebook.orca");
        activity.startActivity(intent);
    }

    static private void openLastActivity(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());

        Bundle extras = activity.getIntent().getExtras();
        if (extras != null)
            intent.putExtras(extras);

        /*if (activity.getIntent().getExtras().get("id") != null)
            intent.putExtra("id", activity.getIntent().getExtras().get("id").toString());
        if (activity.getIntent().getExtras().get("role_type") != null)
            intent.putExtra("role_type", activity.getIntent().getExtras().get("role_type").toString());*/

        activity.startActivity(intent);
        activity.finish();
    }
}
