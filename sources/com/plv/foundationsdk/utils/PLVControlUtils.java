package com.plv.foundationsdk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class PLVControlUtils {
    private static final String ANDROID = "android";
    private static final String ANDROID_OS_SYSTEM_PROPERTIES = "android.os.SystemProperties";
    private static final String BOOL = "bool";
    private static final String CONFIG_SHOW_NAVIGATION_BAR = "config_showNavigationBar";
    private static final String DIMEN = "dimen";
    private static final String GET = "get";
    private static final String HAS_VIRTUAL_NAVIGATION_BAR = "hasVirtualNavigationBar:";
    private static final String NAVIGATION_BAR_HEIGHT = "navigation_bar_height";
    private static final String QEMU_HW_MAINKEYS = "qemu.hw.mainkeys";
    private static final String STATUS_BAR_HEIGHT = "status_bar_height";
    private static final String TAG = "PLVControlUtils";
    private static int streamMusicVolume;

    public static void closeSound(Context context) {
        AudioManager audioManager = (AudioManager) context.getApplicationContext().getSystemService("audio");
        streamMusicVolume = audioManager.getStreamVolume(3);
        audioManager.setStreamVolume(3, 0, 0);
    }

    public static int getBrightness(Activity activity) {
        try {
            float f2 = activity.getWindow().getAttributes().screenBrightness;
            return f2 == -1.0f ? Math.round((Settings.System.getInt(activity.getContentResolver(), "screen_brightness") * 100) / 255.0f) : Math.round(f2 * 100.0f);
        } catch (Settings.SettingNotFoundException e2) {
            PLVCommonLog.e(TAG, e2.getMessage());
            return 0;
        }
    }

    public static int[] getDisplayWH(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier(NAVIGATION_BAR_HEIGHT, DIMEN, "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier(STATUS_BAR_HEIGHT, DIMEN, "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getVolume(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        return (int) Math.round((audioManager.getStreamVolume(3) / audioManager.getStreamMaxVolume(3)) * 100.0d);
    }

    public static int getVolumeValidProgress(Context context) {
        return (int) Math.ceil(100.0d / ((AudioManager) context.getSystemService("audio")).getStreamMaxVolume(3));
    }

    public static boolean hasVirtualNavigationBar(Context context) throws ClassNotFoundException {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier(CONFIG_SHOW_NAVIGATION_BAR, BOOL, "android");
        boolean z2 = false;
        boolean z3 = identifier > 0 ? resources.getBoolean(identifier) : false;
        try {
            Class<?> cls = Class.forName(ANDROID_OS_SYSTEM_PROPERTIES);
            String str = (String) cls.getMethod(GET, String.class).invoke(cls, QEMU_HW_MAINKEYS);
            if (!"1".equals(str)) {
                z2 = "0".equals(str) ? true : z3;
            }
            return z2;
        } catch (ClassNotFoundException e2) {
            PLVCommonLog.e(TAG, HAS_VIRTUAL_NAVIGATION_BAR + e2.getMessage());
            return z3;
        } catch (IllegalAccessException e3) {
            PLVCommonLog.e(TAG, HAS_VIRTUAL_NAVIGATION_BAR + e3.getMessage());
            return z3;
        } catch (NoSuchMethodException e4) {
            PLVCommonLog.e(TAG, HAS_VIRTUAL_NAVIGATION_BAR + e4.getMessage());
            return z3;
        } catch (InvocationTargetException e5) {
            PLVCommonLog.e(TAG, HAS_VIRTUAL_NAVIGATION_BAR + e5.getMessage());
            return z3;
        }
    }

    public static boolean isOpenNetwork(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
    }

    public static boolean isOpenSound(Context context) {
        return ((AudioManager) context.getApplicationContext().getSystemService("audio")).getStreamVolume(3) != 0;
    }

    public static void openSound(Context context) {
        AudioManager audioManager = (AudioManager) context.getApplicationContext().getSystemService("audio");
        int i2 = streamMusicVolume;
        if (i2 == 0) {
            audioManager.setStreamVolume(3, audioManager.getStreamMaxVolume(3) / 3, 0);
        } else {
            audioManager.setStreamVolume(3, i2, 0);
        }
    }

    public static void releaseTheAudioFocus(Context context) {
        ((AudioManager) context.getApplicationContext().getSystemService("audio")).abandonAudioFocus(null);
    }

    public static int requestTheAudioFocus(Context context) {
        return ((AudioManager) context.getApplicationContext().getSystemService("audio")).requestAudioFocus(null, 3, 2);
    }

    public static void setBrightness(Activity activity, int i2) {
        if (i2 <= 0 && i2 != -1) {
            i2 = 0;
        } else if (i2 > 100) {
            i2 = 100;
        }
        Window window = activity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (i2 == -1) {
            attributes.screenBrightness = -1.0f;
        } else {
            attributes.screenBrightness = i2 / 100.0f;
        }
        window.setAttributes(attributes);
    }

    public static void setVolume(Context context, int i2) {
        if (i2 < 0) {
            i2 = 0;
        } else if (i2 > 100) {
            i2 = 100;
        }
        ((AudioManager) context.getSystemService("audio")).setStreamVolume(3, (int) (r5.getStreamMaxVolume(3) * (i2 / 100.0d)), 0);
    }

    public static int getVolumeValidProgress(Context context, int i2) {
        return Math.max(getVolumeValidProgress(context), i2);
    }
}
