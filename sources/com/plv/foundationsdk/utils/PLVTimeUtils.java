package com.plv.foundationsdk.utils;

import java.util.Locale;

/* loaded from: classes4.dex */
public class PLVTimeUtils {
    public static int formatToSecond(int i2, int i3, int i4) {
        return (i2 * 3600) + (i3 * 60) + i4;
    }

    public static String generateTime(long j2) {
        return generateTime(j2, false);
    }

    public static String generateTime(long j2, boolean z2) {
        int i2 = (int) (j2 / 1000);
        int i3 = i2 % 60;
        int i4 = (i2 / 60) % 60;
        int i5 = i2 / 3600;
        return (z2 || i5 > 0) ? String.format(Locale.getDefault(), "%02d:%02d:%02d", Integer.valueOf(i5), Integer.valueOf(i4), Integer.valueOf(i3)) : String.format(Locale.getDefault(), "%02d:%02d", Integer.valueOf(i4), Integer.valueOf(i3));
    }
}
