package atproj.cyplay.com.asperteamapi.util;

import android.app.ActivityManager;
import android.content.Context;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by andre on 12-Jul-18.
 */

public class AsperteamFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
}
