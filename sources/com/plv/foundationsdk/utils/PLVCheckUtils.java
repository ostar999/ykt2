package com.plv.foundationsdk.utils;

import android.text.TextUtils;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public class PLVCheckUtils {
    public static void checkCodeThrow(int i2, String str) throws Exception {
        if (i2 == 200) {
            return;
        }
        throw new Exception("code is " + i2 + ", message is " + str);
    }

    public static String checkEmptyParams(String... strArr) {
        String strCheckParams = checkParams(strArr);
        if (strCheckParams == null) {
            return null;
        }
        return strCheckParams + " is empty";
    }

    public static String checkNullParam(Object obj, String str) {
        if (obj != null) {
            return null;
        }
        return str + " is null";
    }

    public static String checkParams(String... strArr) {
        for (int i2 = 0; i2 <= strArr.length - 2; i2 += 2) {
            if (TextUtils.isEmpty(strArr[i2])) {
                return strArr[i2 + 1];
            }
        }
        return null;
    }

    public static void uncaught(@NonNull Throwable th) {
        Thread threadCurrentThread = Thread.currentThread();
        threadCurrentThread.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread, th);
    }
}
