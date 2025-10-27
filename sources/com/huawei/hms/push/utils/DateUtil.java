package com.huawei.hms.push.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.text.StrPool;
import com.huawei.hms.support.log.HMSLog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class DateUtil {
    public static String parseMilliSecondToString(Long l2) {
        if (l2 == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(DatePattern.UTC_MS_PATTERN).format(l2);
        } catch (Exception e2) {
            HMSLog.e(com.catchpig.mvvm.utils.DateUtil.TAG, "parseMilliSecondToString Exception.", e2);
            return null;
        }
    }

    public static long parseUtcToMillisecond(String str) throws StringIndexOutOfBoundsException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.UTC_MS_PATTERN, Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.parse(str.substring(0, str.indexOf(StrPool.DOT)) + (str.substring(str.indexOf(StrPool.DOT)).substring(0, 4) + "Z")).getTime();
    }
}
