package atproj.cyplay.com.asperteamapi.util;

import android.content.Context;
import android.content.res.Resources;

import atproj.cyplay.com.asperteamapi.R;
import atproj.cyplay.com.asperteamapi.model.RoleType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andre on 14-Apr-18.
 */

public class StringHelper {

    public static String getRoleName(RoleType roleType, Context context) {
        String result = null;
        switch (roleType) {
            case COACH:
                result = context.getString(R.string.coach);
                break;
            case COACH_PERMANENT:
                result = context.getString(R.string.permanent_coach);
                break;
            case PROFILE_MANAGER:
                result = context.getString(R.string.profile_manager);
                break;
            case HR_MANAGER:
                result = context.getString(R.string.hr_manager);
                break;
            case TUTOR:
                result = context.getString(R.string.tutor);
                break;
            case DISABILITY_MANAGER:
                result = context.getString(R.string.disablitiy_manager);
                break;
        }

        return result;
    }

    public static String getSex(int sexRes, Context context) {
        switch (sexRes) {
            case 1:
                return context.getString(R.string.male);
            case 2:
                return context.getString(R.string.female);
            case 3:
                return context.getString(R.string.another);
        }

        return null;
    }

    public static String getHanded(boolean isLeftHanded, Context context) {
        if (isLeftHanded)
            return context.getString(R.string.left_handed);
        return context.getString(R.string.right_handed);
    }

    public static String getBoolean(boolean value, Context context) {
        if (value)
            return context.getString(R.string.yes);
        return context.getString(R.string.no);
    }

    public static String byIdName(String name, Context context) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }
}
