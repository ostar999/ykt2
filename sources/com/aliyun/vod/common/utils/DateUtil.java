package com.aliyun.vod.common.utils;

import cn.hutool.core.date.DatePattern;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/* loaded from: classes2.dex */
public class DateUtil {
    public static String generateTimestamp() {
        return generateTimestamp(System.currentTimeMillis());
    }

    public static String generateTimestamp(long j2) {
        Date date = new Date(j2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.UTC_PATTERN);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return simpleDateFormat.format(date);
    }
}
