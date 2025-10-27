package com.google.firebase.heartbeatinfo;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;

/* loaded from: classes4.dex */
class HeartBeatInfoStorage {
    private static final String GLOBAL = "fire-global";
    private static HeartBeatInfoStorage instance = null;
    private static final String preferencesName = "FirebaseAppHeartBeat";
    private final SharedPreferences sharedPreferences;

    private HeartBeatInfoStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(preferencesName, 0);
    }

    public static synchronized HeartBeatInfoStorage getInstance(Context context) {
        if (instance == null) {
            instance = new HeartBeatInfoStorage(context);
        }
        return instance;
    }

    public synchronized boolean shouldSendGlobalHeartBeat(long j2) {
        return shouldSendSdkHeartBeat(GLOBAL, j2);
    }

    public synchronized boolean shouldSendSdkHeartBeat(String str, long j2) {
        if (!this.sharedPreferences.contains(str)) {
            this.sharedPreferences.edit().putLong(str, j2).apply();
            return true;
        }
        if (j2 - this.sharedPreferences.getLong(str, -1L) < 86400000) {
            return false;
        }
        this.sharedPreferences.edit().putLong(str, j2).apply();
        return true;
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    @VisibleForTesting
    public HeartBeatInfoStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
}
