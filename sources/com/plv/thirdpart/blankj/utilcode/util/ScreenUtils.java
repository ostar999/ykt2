package com.plv.thirdpart.blankj.utilcode.util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.annotation.NonNull;

/* loaded from: classes5.dex */
public final class ScreenUtils {
    private ScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static float getScreenDensity() {
        return Utils.getApp().getResources().getDisplayMetrics().density;
    }

    public static int getScreenDensityDpi() {
        return Utils.getApp().getResources().getDisplayMetrics().densityDpi;
    }

    public static int getScreenHeight() {
        return Utils.getApp().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenOrientatedHeight() {
        return isPortrait() ? Math.max(getScreenHeight(), getScreenWidth()) : Math.min(getScreenHeight(), getScreenWidth());
    }

    public static int getScreenOrientatedWidth() {
        return isPortrait() ? Math.min(getScreenWidth(), getScreenHeight()) : Math.max(getScreenWidth(), getScreenHeight());
    }

    public static int getScreenRotation(@NonNull Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        if (rotation == 1) {
            return 90;
        }
        if (rotation != 2) {
            return rotation != 3 ? 0 : 270;
        }
        return 180;
    }

    public static int getScreenWidth() {
        return Utils.getApp().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(Utils.getApp().getContentResolver(), "screen_off_timeout");
        } catch (Settings.SettingNotFoundException e2) {
            e2.printStackTrace();
            return -123;
        }
    }

    public static boolean isLandscape() {
        return Utils.getApp().getResources().getConfiguration().orientation == 2;
    }

    public static boolean isPortrait() {
        return Utils.getApp().getResources().getConfiguration().orientation == 1;
    }

    public static boolean isScreenLock() {
        return ((KeyguardManager) Utils.getApp().getSystemService("keyguard")).inKeyguardRestrictedInputMode();
    }

    public static boolean isTablet() {
        return (Utils.getApp().getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static Bitmap screenShot(@NonNull Activity activity) {
        return screenShot(activity, false);
    }

    public static void setFullScreen(@NonNull Activity activity) {
        activity.requestWindowFeature(1);
        activity.getWindow().setFlags(1024, 1024);
    }

    public static void setLandscape(@NonNull Activity activity) {
        activity.setRequestedOrientation(0);
    }

    public static void setPortrait(@NonNull Activity activity) {
        activity.setRequestedOrientation(1);
    }

    public static void setSleepDuration(int i2) {
        Settings.System.putInt(Utils.getApp().getContentResolver(), "screen_off_timeout", i2);
    }

    public static Bitmap screenShot(@NonNull Activity activity, boolean z2) throws Resources.NotFoundException {
        Bitmap bitmapCreateBitmap;
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap drawingCache = decorView.getDrawingCache();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (z2) {
            Resources resources = activity.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
            bitmapCreateBitmap = Bitmap.createBitmap(drawingCache, 0, dimensionPixelSize, displayMetrics.widthPixels, displayMetrics.heightPixels - dimensionPixelSize);
        } else {
            bitmapCreateBitmap = Bitmap.createBitmap(drawingCache, 0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
        decorView.destroyDrawingCache();
        return bitmapCreateBitmap;
    }
}
