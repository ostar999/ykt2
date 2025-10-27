package com.aliyun.sls.android.producer.utils;

import android.content.Context;
import android.os.SystemClock;
import cn.hutool.core.text.StrPool;
import com.aliyun.sls.android.producer.Log;
import com.aliyun.sls.android.producer.LogProducerHttpTool;
import com.just.agentweb.DefaultWebClient;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public class TimeUtils {
    private static long elapsedRealTime;
    private static long serverTime;

    private TimeUtils() {
    }

    public static void fixTime(Log log) {
        Map<String, String> content;
        String strValueOf;
        if (log == null || (content = log.getContent()) == null || content.size() == 0 || !content.containsKey("local_timestamp")) {
            return;
        }
        Date date = new Date();
        String str = content.get("local_timestamp");
        if (str.length() < 10) {
            strValueOf = String.valueOf(System.currentTimeMillis());
        } else {
            strValueOf = str.substring(0, 10) + String.valueOf(date.getTime()).substring(10);
        }
        date.setTime(safe2Long(strValueOf));
        content.put("local_time_fixed", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault()).format(date));
        content.put("local_timestamp_fixed", strValueOf);
    }

    public static long getTimeInMillis() {
        if (0 == elapsedRealTime) {
            return System.currentTimeMillis() / 1000;
        }
        return serverTime + ((SystemClock.elapsedRealtime() - elapsedRealTime) / 1000);
    }

    private static long safe2Long(String time) {
        try {
            return Long.parseLong(time);
        } catch (Throwable unused) {
            return System.currentTimeMillis();
        }
    }

    public static void startUpdateServerTime(Context context, final String endpoint, final String project) {
        if (context == null || context.getPackageManager().checkPermission("android.permission.INTERNET", context.getPackageName()) == 0) {
            if (endpoint.contains("http")) {
                endpoint = endpoint.substring(endpoint.indexOf("://") + 3);
            }
            final String str = DefaultWebClient.HTTPS_SCHEME + project + StrPool.DOT + endpoint + "/servertime";
            final String[] strArr = {"x-log-apiversion", "0.6.0"};
            ThreadUtils.exec(new Runnable() { // from class: com.aliyun.sls.android.producer.utils.TimeUtils.1
                @Override // java.lang.Runnable
                public void run() {
                    LogProducerHttpTool.android_http_post(str, "GET", strArr, new byte[0]);
                }
            });
        }
    }

    public static void updateServerTime(long timeInMillis) {
        serverTime = timeInMillis;
        elapsedRealTime = SystemClock.elapsedRealtime();
    }
}
