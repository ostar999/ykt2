package com.blankj.utilcode.util;

import android.graphics.Color;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.InputDeviceCompat;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;

/* loaded from: classes2.dex */
public final class ColorUtils {
    private ColorUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static int getColor(@ColorRes int i2) {
        return ContextCompat.getColor(Utils.getApp(), i2);
    }

    public static int getRandomColor() {
        return getRandomColor(true);
    }

    public static String int2ArgbString(@ColorInt int i2) {
        String hexString = Integer.toHexString(i2);
        while (hexString.length() < 6) {
            hexString = "0" + hexString;
        }
        while (hexString.length() < 8) {
            hexString = "f" + hexString;
        }
        return DictionaryFactory.SHARP + hexString;
    }

    public static String int2RgbString(@ColorInt int i2) {
        String hexString = Integer.toHexString(i2 & 16777215);
        while (hexString.length() < 6) {
            hexString = "0" + hexString;
        }
        return DictionaryFactory.SHARP + hexString;
    }

    public static boolean isLightColor(@ColorInt int i2) {
        return ((((double) Color.red(i2)) * 0.299d) + (((double) Color.green(i2)) * 0.587d)) + (((double) Color.blue(i2)) * 0.114d) >= 127.5d;
    }

    public static int setAlphaComponent(@ColorInt int i2, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return (i2 & 16777215) | (((int) ((f2 * 255.0f) + 0.5f)) << 24);
    }

    public static int setAlphaComponent(@ColorInt int i2, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i3) {
        return (i2 & 16777215) | (i3 << 24);
    }

    public static int setBlueComponent(@ColorInt int i2, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return (i2 & InputDeviceCompat.SOURCE_ANY) | ((int) ((f2 * 255.0f) + 0.5f));
    }

    public static int setBlueComponent(@ColorInt int i2, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i3) {
        return (i2 & InputDeviceCompat.SOURCE_ANY) | i3;
    }

    public static int setGreenComponent(@ColorInt int i2, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return (i2 & (-65281)) | (((int) ((f2 * 255.0f) + 0.5f)) << 8);
    }

    public static int setGreenComponent(@ColorInt int i2, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i3) {
        return (i2 & (-65281)) | (i3 << 8);
    }

    public static int setRedComponent(@ColorInt int i2, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return (i2 & (-16711681)) | (((int) ((f2 * 255.0f) + 0.5f)) << 16);
    }

    public static int setRedComponent(@ColorInt int i2, @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i3) {
        return (i2 & (-16711681)) | (i3 << 16);
    }

    public static int string2Int(@NonNull String str) {
        return Color.parseColor(str);
    }

    public static int getRandomColor(boolean z2) {
        return (z2 ? ((int) (Math.random() * 256.0d)) << 24 : -16777216) | ((int) (Math.random() * 1.6777216E7d));
    }
}
