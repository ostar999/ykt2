package org.wrtca.util;

import android.content.Context;

/* loaded from: classes9.dex */
public class ContextUtils {
    private static final String TAG = "ContextUtils";
    private static Context applicationContext;

    public static Context getApplicationContext() {
        return applicationContext;
    }

    public static void initialize(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Application context cannot be null for ContextUtils.initialize.");
        }
        applicationContext = context;
    }
}
