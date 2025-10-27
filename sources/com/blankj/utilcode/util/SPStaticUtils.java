package com.blankj.utilcode.util;

import androidx.annotation.NonNull;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public final class SPStaticUtils {
    private static SPUtils sDefaultSPUtils;

    public static void clear() {
        clear(getDefaultSPUtils());
    }

    public static boolean contains(@NonNull String str) {
        return contains(str, getDefaultSPUtils());
    }

    public static Map<String, ?> getAll() {
        return getAll(getDefaultSPUtils());
    }

    public static boolean getBoolean(@NonNull String str) {
        return getBoolean(str, getDefaultSPUtils());
    }

    private static SPUtils getDefaultSPUtils() {
        SPUtils sPUtils = sDefaultSPUtils;
        return sPUtils != null ? sPUtils : SPUtils.getInstance();
    }

    public static float getFloat(@NonNull String str) {
        return getFloat(str, getDefaultSPUtils());
    }

    public static int getInt(@NonNull String str) {
        return getInt(str, getDefaultSPUtils());
    }

    public static long getLong(@NonNull String str) {
        return getLong(str, getDefaultSPUtils());
    }

    public static String getString(@NonNull String str) {
        return getString(str, getDefaultSPUtils());
    }

    public static Set<String> getStringSet(@NonNull String str) {
        return getStringSet(str, getDefaultSPUtils());
    }

    public static void put(@NonNull String str, String str2) {
        put(str, str2, getDefaultSPUtils());
    }

    public static void remove(@NonNull String str) {
        remove(str, getDefaultSPUtils());
    }

    public static void setDefaultSPUtils(SPUtils sPUtils) {
        sDefaultSPUtils = sPUtils;
    }

    public static void clear(boolean z2) {
        clear(z2, getDefaultSPUtils());
    }

    public static boolean contains(@NonNull String str, @NonNull SPUtils sPUtils) {
        return sPUtils.contains(str);
    }

    public static Map<String, ?> getAll(@NonNull SPUtils sPUtils) {
        return sPUtils.getAll();
    }

    public static boolean getBoolean(@NonNull String str, boolean z2) {
        return getBoolean(str, z2, getDefaultSPUtils());
    }

    public static float getFloat(@NonNull String str, float f2) {
        return getFloat(str, f2, getDefaultSPUtils());
    }

    public static int getInt(@NonNull String str, int i2) {
        return getInt(str, i2, getDefaultSPUtils());
    }

    public static long getLong(@NonNull String str, long j2) {
        return getLong(str, j2, getDefaultSPUtils());
    }

    public static String getString(@NonNull String str, String str2) {
        return getString(str, str2, getDefaultSPUtils());
    }

    public static Set<String> getStringSet(@NonNull String str, Set<String> set) {
        return getStringSet(str, set, getDefaultSPUtils());
    }

    public static void put(@NonNull String str, String str2, boolean z2) {
        put(str, str2, z2, getDefaultSPUtils());
    }

    public static void remove(@NonNull String str, boolean z2) {
        remove(str, z2, getDefaultSPUtils());
    }

    public static void clear(@NonNull SPUtils sPUtils) {
        sPUtils.clear();
    }

    public static boolean getBoolean(@NonNull String str, @NonNull SPUtils sPUtils) {
        return sPUtils.getBoolean(str);
    }

    public static float getFloat(@NonNull String str, @NonNull SPUtils sPUtils) {
        return sPUtils.getFloat(str);
    }

    public static int getInt(@NonNull String str, @NonNull SPUtils sPUtils) {
        return sPUtils.getInt(str);
    }

    public static long getLong(@NonNull String str, @NonNull SPUtils sPUtils) {
        return sPUtils.getLong(str);
    }

    public static String getString(@NonNull String str, @NonNull SPUtils sPUtils) {
        return sPUtils.getString(str);
    }

    public static Set<String> getStringSet(@NonNull String str, @NonNull SPUtils sPUtils) {
        return sPUtils.getStringSet(str);
    }

    public static void put(@NonNull String str, int i2) {
        put(str, i2, getDefaultSPUtils());
    }

    public static void remove(@NonNull String str, @NonNull SPUtils sPUtils) {
        sPUtils.remove(str);
    }

    public static void clear(boolean z2, @NonNull SPUtils sPUtils) {
        sPUtils.clear(z2);
    }

    public static boolean getBoolean(@NonNull String str, boolean z2, @NonNull SPUtils sPUtils) {
        return sPUtils.getBoolean(str, z2);
    }

    public static float getFloat(@NonNull String str, float f2, @NonNull SPUtils sPUtils) {
        return sPUtils.getFloat(str, f2);
    }

    public static int getInt(@NonNull String str, int i2, @NonNull SPUtils sPUtils) {
        return sPUtils.getInt(str, i2);
    }

    public static long getLong(@NonNull String str, long j2, @NonNull SPUtils sPUtils) {
        return sPUtils.getLong(str, j2);
    }

    public static String getString(@NonNull String str, String str2, @NonNull SPUtils sPUtils) {
        return sPUtils.getString(str, str2);
    }

    public static Set<String> getStringSet(@NonNull String str, Set<String> set, @NonNull SPUtils sPUtils) {
        return sPUtils.getStringSet(str, set);
    }

    public static void put(@NonNull String str, int i2, boolean z2) {
        put(str, i2, z2, getDefaultSPUtils());
    }

    public static void remove(@NonNull String str, boolean z2, @NonNull SPUtils sPUtils) {
        sPUtils.remove(str, z2);
    }

    public static void put(@NonNull String str, long j2) {
        put(str, j2, getDefaultSPUtils());
    }

    public static void put(@NonNull String str, long j2, boolean z2) {
        put(str, j2, z2, getDefaultSPUtils());
    }

    public static void put(@NonNull String str, float f2) {
        put(str, f2, getDefaultSPUtils());
    }

    public static void put(@NonNull String str, float f2, boolean z2) {
        put(str, f2, z2, getDefaultSPUtils());
    }

    public static void put(@NonNull String str, boolean z2) {
        put(str, z2, getDefaultSPUtils());
    }

    public static void put(@NonNull String str, boolean z2, boolean z3) {
        put(str, z2, z3, getDefaultSPUtils());
    }

    public static void put(@NonNull String str, Set<String> set) {
        put(str, set, getDefaultSPUtils());
    }

    public static void put(@NonNull String str, Set<String> set, boolean z2) {
        put(str, set, z2, getDefaultSPUtils());
    }

    public static void put(@NonNull String str, String str2, @NonNull SPUtils sPUtils) {
        sPUtils.put(str, str2);
    }

    public static void put(@NonNull String str, String str2, boolean z2, @NonNull SPUtils sPUtils) {
        sPUtils.put(str, str2, z2);
    }

    public static void put(@NonNull String str, int i2, @NonNull SPUtils sPUtils) {
        sPUtils.put(str, i2);
    }

    public static void put(@NonNull String str, int i2, boolean z2, @NonNull SPUtils sPUtils) {
        sPUtils.put(str, i2, z2);
    }

    public static void put(@NonNull String str, long j2, @NonNull SPUtils sPUtils) {
        sPUtils.put(str, j2);
    }

    public static void put(@NonNull String str, long j2, boolean z2, @NonNull SPUtils sPUtils) {
        sPUtils.put(str, j2, z2);
    }

    public static void put(@NonNull String str, float f2, @NonNull SPUtils sPUtils) {
        sPUtils.put(str, f2);
    }

    public static void put(@NonNull String str, float f2, boolean z2, @NonNull SPUtils sPUtils) {
        sPUtils.put(str, f2, z2);
    }

    public static void put(@NonNull String str, boolean z2, @NonNull SPUtils sPUtils) {
        sPUtils.put(str, z2);
    }

    public static void put(@NonNull String str, boolean z2, boolean z3, @NonNull SPUtils sPUtils) {
        sPUtils.put(str, z2, z3);
    }

    public static void put(@NonNull String str, Set<String> set, @NonNull SPUtils sPUtils) {
        sPUtils.put(str, set);
    }

    public static void put(@NonNull String str, Set<String> set, boolean z2, @NonNull SPUtils sPUtils) {
        sPUtils.put(str, set, z2);
    }
}
