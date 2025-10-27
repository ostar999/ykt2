package com.aliyun.svideo.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes2.dex */
public class DateTimeUtils {
    public static String formatMs(long j2) {
        int i2 = (int) (j2 / 1000);
        int i3 = i2 % 60;
        int i4 = (i2 / 60) % 60;
        int i5 = i2 / 3600;
        StringBuilder sb = new StringBuilder("");
        if (i5 > 9) {
            sb.append(i5);
            sb.append(":");
        } else if (i5 > 0) {
            sb.append("0");
            sb.append(i5);
            sb.append(":");
        }
        if (i4 > 9) {
            sb.append(i4);
            sb.append(":");
        } else if (i4 > 0) {
            sb.append("0");
            sb.append(i4);
            sb.append(":");
        } else {
            sb.append(TarConstants.VERSION_POSIX);
            sb.append(":");
        }
        if (i3 > 9) {
            sb.append(i3);
        } else if (i3 > 0) {
            sb.append("0");
            sb.append(i3);
        } else {
            sb.append(TarConstants.VERSION_POSIX);
        }
        return sb.toString();
    }

    public static String getDateTimeFromMillisecond(Long l2) {
        return new SimpleDateFormat("yyyy-MM-dd-HHmmssSSS").format(new Date(l2.longValue()));
    }
}
