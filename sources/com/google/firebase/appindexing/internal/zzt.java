package com.google.firebase.appindexing.internal;

import android.util.Log;
import com.google.firebase.appindexing.FirebaseAppIndex;

/* loaded from: classes4.dex */
public final class zzt {
    public static boolean isLoggable(int i2) {
        if (Log.isLoggable(FirebaseAppIndex.APP_INDEXING_API_TAG, i2)) {
            return true;
        }
        return Log.isLoggable(FirebaseAppIndex.APP_INDEXING_API_TAG, i2);
    }

    public static int zzn(String str) {
        if (isLoggable(3)) {
            return Log.d(FirebaseAppIndex.APP_INDEXING_API_TAG, str);
        }
        return 0;
    }
}
