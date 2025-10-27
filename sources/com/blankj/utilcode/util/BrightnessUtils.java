package com.blankj.utilcode.util;

import android.content.ContentResolver;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;

/* loaded from: classes2.dex */
public final class BrightnessUtils {
    private BrightnessUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static int getBrightness() {
        try {
            return Settings.System.getInt(Utils.getApp().getContentResolver(), "screen_brightness");
        } catch (Settings.SettingNotFoundException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static int getWindowBrightness(@NonNull Window window) {
        float f2 = window.getAttributes().screenBrightness;
        return f2 < 0.0f ? getBrightness() : (int) (f2 * 255.0f);
    }

    public static boolean isAutoBrightnessEnabled() {
        try {
            return Settings.System.getInt(Utils.getApp().getContentResolver(), "screen_brightness_mode") == 1;
        } catch (Settings.SettingNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean setAutoBrightnessEnabled(boolean z2) {
        return Settings.System.putInt(Utils.getApp().getContentResolver(), "screen_brightness_mode", z2 ? 1 : 0);
    }

    public static boolean setBrightness(@IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i2) {
        ContentResolver contentResolver = Utils.getApp().getContentResolver();
        boolean zPutInt = Settings.System.putInt(contentResolver, "screen_brightness", i2);
        contentResolver.notifyChange(Settings.System.getUriFor("screen_brightness"), null);
        return zPutInt;
    }

    public static void setWindowBrightness(@NonNull Window window, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i2) {
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.screenBrightness = i2 / 255.0f;
        window.setAttributes(attributes);
    }
}
