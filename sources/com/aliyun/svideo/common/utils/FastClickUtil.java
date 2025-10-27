package com.aliyun.svideo.common.utils;

import android.util.Log;
import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public class FastClickUtil {
    private static final int MIN_DELAY_TIME = 500;
    private static final int MIN_DELAY_TIME_ACTIVITY = 800;
    private static final String TAG = "FastClickUtil";
    private static String sLastActivitySimpleName;
    private static long sLastClickTime;

    public static boolean isFastClick() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean z2 = jCurrentTimeMillis - sLastClickTime <= 500;
        Log.e(TAG, "log_common_FastClickUtil : " + (jCurrentTimeMillis - sLastClickTime));
        sLastClickTime = jCurrentTimeMillis;
        return z2;
    }

    public static boolean isFastClickActivity(@NonNull String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean z2 = jCurrentTimeMillis - sLastClickTime <= 800;
        sLastClickTime = jCurrentTimeMillis;
        if (str.equals(sLastActivitySimpleName)) {
            return z2;
        }
        sLastActivitySimpleName = str;
        return false;
    }
}
