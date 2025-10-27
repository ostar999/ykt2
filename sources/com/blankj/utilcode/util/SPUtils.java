package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressLint({"ApplySharedPref"})
/* loaded from: classes2.dex */
public final class SPUtils {
    private static final Map<String, SPUtils> SP_UTILS_MAP = new HashMap();
    private SharedPreferences sp;

    private SPUtils(String str) {
        this.sp = Utils.getApp().getSharedPreferences(str, 0);
    }

    public static SPUtils getInstance() {
        return getInstance("", 0);
    }

    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public void clear() {
        clear(false);
    }

    public boolean contains(@NonNull String str) {
        return this.sp.contains(str);
    }

    public Map<String, ?> getAll() {
        return this.sp.getAll();
    }

    public boolean getBoolean(@NonNull String str) {
        return getBoolean(str, false);
    }

    public float getFloat(@NonNull String str) {
        return getFloat(str, -1.0f);
    }

    public int getInt(@NonNull String str) {
        return getInt(str, -1);
    }

    public long getLong(@NonNull String str) {
        return getLong(str, -1L);
    }

    public String getString(@NonNull String str) {
        return getString(str, "");
    }

    public Set<String> getStringSet(@NonNull String str) {
        return getStringSet(str, Collections.emptySet());
    }

    public void put(@NonNull String str, String str2) {
        put(str, str2, false);
    }

    public void remove(@NonNull String str) {
        remove(str, false);
    }

    public static SPUtils getInstance(int i2) {
        return getInstance("", i2);
    }

    public void clear(boolean z2) {
        if (z2) {
            this.sp.edit().clear().commit();
        } else {
            this.sp.edit().clear().apply();
        }
    }

    public boolean getBoolean(@NonNull String str, boolean z2) {
        return this.sp.getBoolean(str, z2);
    }

    public float getFloat(@NonNull String str, float f2) {
        return this.sp.getFloat(str, f2);
    }

    public int getInt(@NonNull String str, int i2) {
        return this.sp.getInt(str, i2);
    }

    public long getLong(@NonNull String str, long j2) {
        return this.sp.getLong(str, j2);
    }

    public String getString(@NonNull String str, String str2) {
        return this.sp.getString(str, str2);
    }

    public Set<String> getStringSet(@NonNull String str, Set<String> set) {
        return this.sp.getStringSet(str, set);
    }

    public void put(@NonNull String str, String str2, boolean z2) {
        if (z2) {
            this.sp.edit().putString(str, str2).commit();
        } else {
            this.sp.edit().putString(str, str2).apply();
        }
    }

    public void remove(@NonNull String str, boolean z2) {
        if (z2) {
            this.sp.edit().remove(str).commit();
        } else {
            this.sp.edit().remove(str).apply();
        }
    }

    private SPUtils(String str, int i2) {
        this.sp = Utils.getApp().getSharedPreferences(str, i2);
    }

    public static SPUtils getInstance(String str) {
        return getInstance(str, 0);
    }

    public static SPUtils getInstance(String str, int i2) {
        if (isSpace(str)) {
            str = "spUtils";
        }
        Map<String, SPUtils> map = SP_UTILS_MAP;
        SPUtils sPUtils = map.get(str);
        if (sPUtils == null) {
            synchronized (SPUtils.class) {
                sPUtils = map.get(str);
                if (sPUtils == null) {
                    sPUtils = new SPUtils(str, i2);
                    map.put(str, sPUtils);
                }
            }
        }
        return sPUtils;
    }

    public void put(@NonNull String str, int i2) {
        put(str, i2, false);
    }

    public void put(@NonNull String str, int i2, boolean z2) {
        if (z2) {
            this.sp.edit().putInt(str, i2).commit();
        } else {
            this.sp.edit().putInt(str, i2).apply();
        }
    }

    public void put(@NonNull String str, long j2) {
        put(str, j2, false);
    }

    public void put(@NonNull String str, long j2, boolean z2) {
        if (z2) {
            this.sp.edit().putLong(str, j2).commit();
        } else {
            this.sp.edit().putLong(str, j2).apply();
        }
    }

    public void put(@NonNull String str, float f2) {
        put(str, f2, false);
    }

    public void put(@NonNull String str, float f2, boolean z2) {
        if (z2) {
            this.sp.edit().putFloat(str, f2).commit();
        } else {
            this.sp.edit().putFloat(str, f2).apply();
        }
    }

    public void put(@NonNull String str, boolean z2) {
        put(str, z2, false);
    }

    public void put(@NonNull String str, boolean z2, boolean z3) {
        if (z3) {
            this.sp.edit().putBoolean(str, z2).commit();
        } else {
            this.sp.edit().putBoolean(str, z2).apply();
        }
    }

    public void put(@NonNull String str, Set<String> set) {
        put(str, set, false);
    }

    public void put(@NonNull String str, Set<String> set, boolean z2) {
        if (z2) {
            this.sp.edit().putStringSet(str, set).commit();
        } else {
            this.sp.edit().putStringSet(str, set).apply();
        }
    }
}
