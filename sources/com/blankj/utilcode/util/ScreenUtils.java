package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import com.hjq.permissions.Permission;

/* loaded from: classes2.dex */
public final class ScreenUtils {
    private ScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static int getAppScreenHeight() {
        WindowManager windowManager = (WindowManager) Utils.getApp().getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.y;
    }

    public static int getAppScreenWidth() {
        WindowManager windowManager = (WindowManager) Utils.getApp().getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.x;
    }

    public static float getScreenDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    public static int getScreenDensityDpi() {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    public static int getScreenHeight() {
        WindowManager windowManager = (WindowManager) Utils.getApp().getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        return point.y;
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
        WindowManager windowManager = (WindowManager) Utils.getApp().getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        return point.x;
    }

    public static float getScreenXDpi() {
        return Resources.getSystem().getDisplayMetrics().xdpi;
    }

    public static float getScreenYDpi() {
        return Resources.getSystem().getDisplayMetrics().ydpi;
    }

    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(Utils.getApp().getContentResolver(), "screen_off_timeout");
        } catch (Settings.SettingNotFoundException e2) {
            e2.printStackTrace();
            return -123;
        }
    }

    public static boolean isFullScreen(@NonNull Activity activity) {
        return (activity.getWindow().getAttributes().flags & 1024) == 1024;
    }

    public static boolean isLandscape() {
        return Utils.getApp().getResources().getConfiguration().orientation == 2;
    }

    public static boolean isPortrait() {
        return Utils.getApp().getResources().getConfiguration().orientation == 1;
    }

    public static boolean isScreenLock() {
        KeyguardManager keyguardManager = (KeyguardManager) Utils.getApp().getSystemService("keyguard");
        if (keyguardManager == null) {
            return false;
        }
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    public static Bitmap screenShot(@NonNull Activity activity) {
        return screenShot(activity, false);
    }

    public static void setFullScreen(@NonNull Activity activity) {
        activity.getWindow().addFlags(1024);
    }

    @SuppressLint({"SourceLockedOrientationActivity"})
    public static void setLandscape(@NonNull Activity activity) {
        activity.setRequestedOrientation(0);
    }

    public static void setNonFullScreen(@NonNull Activity activity) {
        activity.getWindow().clearFlags(1024);
    }

    @SuppressLint({"SourceLockedOrientationActivity"})
    public static void setPortrait(@NonNull Activity activity) {
        activity.setRequestedOrientation(1);
    }

    @RequiresPermission(Permission.WRITE_SETTINGS)
    public static void setSleepDuration(int i2) {
        Settings.System.putInt(Utils.getApp().getContentResolver(), "screen_off_timeout", i2);
    }

    public static void toggleFullScreen(@NonNull Activity activity) {
        boolean zIsFullScreen = isFullScreen(activity);
        Window window = activity.getWindow();
        if (zIsFullScreen) {
            window.clearFlags(1024);
        } else {
            window.addFlags(1024);
        }
    }

    public int calculateDistanceByX(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return getScreenWidth() - iArr[0];
    }

    public int calculateDistanceByY(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return getScreenHeight() - iArr[1];
    }

    public int getViewX(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return iArr[0];
    }

    public int getViewY(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return iArr[1];
    }

    public static Bitmap screenShot(@NonNull Activity activity, boolean z2) {
        Bitmap bitmapView2Bitmap = UtilsBridge.view2Bitmap(activity.getWindow().getDecorView());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (!z2) {
            return Bitmap.createBitmap(bitmapView2Bitmap, 0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
        int statusBarHeight = UtilsBridge.getStatusBarHeight();
        return Bitmap.createBitmap(bitmapView2Bitmap, 0, statusBarHeight, displayMetrics.widthPixels, displayMetrics.heightPixels - statusBarHeight);
    }
}
