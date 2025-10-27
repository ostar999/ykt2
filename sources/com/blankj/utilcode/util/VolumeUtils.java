package com.blankj.utilcode.util;

import android.media.AudioManager;
import android.os.Build;

/* loaded from: classes2.dex */
public class VolumeUtils {
    public static int getMaxVolume(int i2) {
        return ((AudioManager) Utils.getApp().getSystemService("audio")).getStreamMaxVolume(i2);
    }

    public static int getMinVolume(int i2) {
        AudioManager audioManager = (AudioManager) Utils.getApp().getSystemService("audio");
        if (Build.VERSION.SDK_INT >= 28) {
            return audioManager.getStreamMinVolume(i2);
        }
        return 0;
    }

    public static int getVolume(int i2) {
        return ((AudioManager) Utils.getApp().getSystemService("audio")).getStreamVolume(i2);
    }

    public static void setVolume(int i2, int i3, int i4) {
        try {
            ((AudioManager) Utils.getApp().getSystemService("audio")).setStreamVolume(i2, i3, i4);
        } catch (SecurityException unused) {
        }
    }
}
