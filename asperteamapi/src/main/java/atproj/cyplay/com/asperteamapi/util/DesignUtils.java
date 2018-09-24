package atproj.cyplay.com.asperteamapi.util;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

/**
 * Created by andre on 16-Apr-18.
 */

public class DesignUtils {

    public static void setTextColor(TextView textview, int color) {
        if (textview != null)
            textview.setTextColor(color);
    }

    public static void setBackgroundColor(View view, int color) {
        try {
            if (view != null) {
                GradientDrawable drawable = (GradientDrawable) view.getBackground();
                if (drawable != null)
                    drawable.setColor(color);
                else
                    view.setBackgroundColor(color);
            }
        } catch (Exception e) {
            view.setBackgroundColor(color);
        }
    }

    static public int dpToPixel(Context context, float dp){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int px = (int)(dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
