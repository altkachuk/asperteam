package com.cyplay.atproj.asperteam.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by andre on 24-Nov-18.
 */

public class ApplicationUtil {

    public static void startActivityFinishWithIgnoreBatterOptimization(Activity srcActivity, Class dstActivity) {
        Context context = srcActivity.getApplicationContext();
        Intent intent = new Intent(context, dstActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = context.getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (pm.isIgnoringBatteryOptimizations(packageName))
                intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
            else {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
            }
        }
        srcActivity.startActivity(intent);
        srcActivity.finish();
    }

    public static <T> void startActivityFinishWithIgnoreBatterOptimization(Activity srcActivity, Class dstActivity, HashMap<String, T> extra) {
        Context context = srcActivity.getApplicationContext();
        Intent intent = new Intent(context, dstActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = context.getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (pm.isIgnoringBatteryOptimizations(packageName))
                intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
            else {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
            }
        }

        for (String key : extra.keySet()) {
            T value = extra.get(key);
            try {
                Method putExtra = intent.getClass().getDeclaredMethod("putExtra", String.class, value.getClass());
                putExtra.invoke(intent, key, value);
            } catch (Exception e) {
                ;
            }
        }

        srcActivity.startActivity(intent);
        srcActivity.finish();
    }

    public static void startActivityWithIgnoreBatterOptimization(Activity srcActivity, Class dstActivity) {
        Context context = srcActivity.getApplicationContext();
        Intent intent = new Intent(context, dstActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = context.getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (pm.isIgnoringBatteryOptimizations(packageName))
                intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
            else {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
            }
        }
        srcActivity.startActivity(intent);
    }

    public static <T> void startActivityWithIgnoreBatterOptimization(Activity srcActivity, Class dstActivity, HashMap<String, T> extra) {
        Context context = srcActivity.getApplicationContext();
        Intent intent = new Intent(context, dstActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = context.getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (pm.isIgnoringBatteryOptimizations(packageName))
                intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
            else {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
            }
        }

        for (String key : extra.keySet()) {
            T value = extra.get(key);
            try {
                Method putExtra = intent.getClass().getDeclaredMethod("putExtra", String.class, value.getClass());
                putExtra.invoke(intent, key, value);
            } catch (Exception e) {
                ;
            }
        }

        srcActivity.startActivity(intent);
    }

    public static void startFinishActivity(Activity srcActivity, Class dstActivity) {
        Context context = srcActivity.getApplicationContext();
        Intent intent = new Intent(context, dstActivity);
        srcActivity.startActivity(intent);
        srcActivity.finish();
    }

    public static <T> void startFinishActivity(Activity srcActivity, Class dstActivity, HashMap<String, T> extra) {
        Context context = srcActivity.getApplicationContext();
        Intent intent = new Intent(context, dstActivity);

        for (String key : extra.keySet()) {
            T value = extra.get(key);
            try {
                Method putExtra = intent.getClass().getDeclaredMethod("putExtra", String.class, value.getClass());
                putExtra.invoke(intent, key, value);
            } catch (Exception e) {
                ;
            }
        }

        srcActivity.startActivity(intent);
        srcActivity.finish();
    }


}
