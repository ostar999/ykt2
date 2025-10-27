package com.hyphenate.util;

/* loaded from: classes4.dex */
public class PerfUtils {
    public static int getSpeed(long j2, long j3) {
        return (int) (j2 / (j3 / 1000));
    }

    public static int getTimeSpendSecond(long j2) {
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() - j2);
        if (iCurrentTimeMillis == 0) {
            return 1;
        }
        return iCurrentTimeMillis;
    }
}
