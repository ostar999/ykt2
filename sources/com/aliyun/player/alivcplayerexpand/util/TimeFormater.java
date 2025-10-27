package com.aliyun.player.alivcplayerexpand.util;

import cn.hutool.core.date.DatePattern;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.utils.DateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes2.dex */
public class TimeFormater {
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
        } else if (i5 == 0) {
            sb.append(TarConstants.VERSION_POSIX);
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

    public static long getExpirationInGMTFormat(String str) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.UTC_SIMPLE_PATTERN);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return simpleDateFormat.parse(str).getTime() / 1000;
        } catch (ParseException e2) {
            if (OSSLog.isEnableLog()) {
                e2.printStackTrace();
            }
            return (DateUtil.getFixedSkewedTimeMillis() / 1000) + 30;
        }
    }

    public static String formatMs(long j2, boolean z2) {
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
        } else if (i5 == 0 && z2) {
            sb.append(TarConstants.VERSION_POSIX);
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
}
