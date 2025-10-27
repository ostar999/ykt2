package com.tencent.liteav.audio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;

/* loaded from: classes6.dex */
public class TXCAudioSettings {
    private static final String AUDIO_SETTINGS = "txc_audio_settings";
    private static final String TAG = "TXCAudioSettings";
    public static final String TIMESTAMP_ROLLBACK_TO_STABLE_SAMPLERATE = "timestamp_rollback_to_stable_samplerate";
    private final SharedPreferences mSharedPreferences;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final TXCAudioSettings f18152a = new TXCAudioSettings();
    }

    private Object get(String str, Object obj) {
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences == null) {
            TXCLog.e(TAG, "getSharedPreferences is null");
            return obj;
        }
        try {
            return obj instanceof String ? sharedPreferences.getString(str, obj.toString()) : obj instanceof Integer ? Integer.valueOf(sharedPreferences.getInt(str, ((Integer) obj).intValue())) : obj instanceof Boolean ? Boolean.valueOf(sharedPreferences.getBoolean(str, ((Boolean) obj).booleanValue())) : obj instanceof Long ? Long.valueOf(sharedPreferences.getLong(str, ((Long) obj).longValue())) : obj instanceof Float ? Float.valueOf(sharedPreferences.getFloat(str, ((Float) obj).floatValue())) : obj instanceof Double ? Double.valueOf(Double.longBitsToDouble(sharedPreferences.getLong(str, Double.doubleToLongBits(((Double) obj).doubleValue())))) : obj;
        } catch (Exception e2) {
            TXCLog.e(TAG, "get value from sharedpreference failed for key: %s", str, e2);
            return obj;
        }
    }

    public static TXCAudioSettings getInstance() {
        return a.f18152a;
    }

    @SuppressLint({"ApplySharedPref"})
    private void set(String str, Object obj) {
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences == null) {
            TXCLog.e(TAG, "getSharedPreferences is null");
            return;
        }
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        if (obj instanceof Integer) {
            editorEdit.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof String) {
            editorEdit.putString(str, obj.toString());
        } else if (obj instanceof Boolean) {
            editorEdit.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Long) {
            editorEdit.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof Float) {
            editorEdit.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof Double) {
            editorEdit.putLong(str, Double.doubleToRawLongBits(((Double) obj).doubleValue()));
        }
        editorEdit.commit();
    }

    public long getLong(String str, long j2) {
        return ((Long) get(str, Long.valueOf(j2))).longValue();
    }

    public void setLong(String str, long j2) {
        set(str, Long.valueOf(j2));
    }

    private TXCAudioSettings() {
        Context appContext = TXCCommonUtil.getAppContext();
        if (appContext != null) {
            this.mSharedPreferences = appContext.getSharedPreferences(AUDIO_SETTINGS, 0);
        } else {
            this.mSharedPreferences = null;
            TXCLog.e(TAG, "context is null, getSharedPreferences failed.");
        }
    }
}
