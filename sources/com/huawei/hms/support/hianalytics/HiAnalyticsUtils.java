package com.huawei.hms.support.hianalytics;

import android.content.Context;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.huawei.hianalytics.process.HiAnalyticsManager;
import com.huawei.hianalytics.util.HiAnalyticTools;
import com.huawei.hms.hatool.HmsHiAnalyticsUtils;
import com.huawei.hms.stats.c;
import com.huawei.hms.support.log.HMSLog;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class HiAnalyticsUtils {

    /* renamed from: c, reason: collision with root package name */
    public static final Object f8091c = new Object();

    /* renamed from: d, reason: collision with root package name */
    public static final Object f8092d = new Object();

    /* renamed from: e, reason: collision with root package name */
    public static HiAnalyticsUtils f8093e;

    /* renamed from: a, reason: collision with root package name */
    public int f8094a = 0;

    /* renamed from: b, reason: collision with root package name */
    public boolean f8095b = c.a();

    public static LinkedHashMap<String, String> a(Map<String, String> map) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    public static HiAnalyticsUtils getInstance() {
        HiAnalyticsUtils hiAnalyticsUtils;
        synchronized (f8091c) {
            if (f8093e == null) {
                f8093e = new HiAnalyticsUtils();
            }
            hiAnalyticsUtils = f8093e;
        }
        return hiAnalyticsUtils;
    }

    public static String versionCodeToName(String str) throws NumberFormatException {
        if (!TextUtils.isEmpty(str) && (str.length() == 8 || str.length() == 9)) {
            try {
                Integer.parseInt(str);
                return Integer.parseInt(str.substring(0, str.length() - 7)) + StrPool.DOT + Integer.parseInt(str.substring(str.length() - 7, str.length() - 5)) + StrPool.DOT + Integer.parseInt(str.substring(str.length() - 5, str.length() - 3)) + StrPool.DOT + Integer.parseInt(str.substring(str.length() - 3));
            } catch (NumberFormatException unused) {
            }
        }
        return "";
    }

    public void enableLog(Context context) {
        HMSLog.i("HiAnalyticsUtils", "Enable Log");
        if (this.f8095b) {
            HiAnalyticTools.enableLog(context);
        } else {
            HmsHiAnalyticsUtils.enableLog();
        }
    }

    public boolean getInitFlag() {
        return !this.f8095b ? HmsHiAnalyticsUtils.getInitFlag() : HiAnalyticsManager.getInitFlag(HiAnalyticsConstant.HA_SERVICE_TAG);
    }

    public boolean hasError(Context context) {
        return com.huawei.hms.stats.a.c(context);
    }

    public void onBuoyEvent(Context context, String str, String str2) {
        if (hasError(context) || context == null) {
            return;
        }
        onEvent2(context, str, str2);
    }

    public void onEvent(Context context, String str, Map<String, String> map) {
        if (hasError(context) || map == null || map.isEmpty() || context == null || !getInitFlag()) {
            return;
        }
        if (this.f8095b) {
            com.huawei.hms.stats.b.a(context, 0, str, a(map));
            com.huawei.hms.stats.b.a(context, 1, str, a(map));
        } else {
            HmsHiAnalyticsUtils.onEvent(0, str, a(map));
            HmsHiAnalyticsUtils.onEvent(1, str, a(map));
        }
        a(context);
    }

    public void onEvent2(Context context, String str, String str2) {
        if (hasError(context) || context == null || !getInitFlag()) {
            return;
        }
        if (this.f8095b) {
            com.huawei.hms.stats.b.a(context, str, str2);
        } else {
            HmsHiAnalyticsUtils.onEvent(context, str, str2);
        }
    }

    public void onNewEvent(Context context, String str, Map map) {
        if (hasError(context) || map == null || map.isEmpty() || context == null || !getInitFlag()) {
            return;
        }
        if (this.f8095b) {
            com.huawei.hms.stats.b.a(context, 0, str, a((Map<String, String>) map));
            com.huawei.hms.stats.b.a(context, 1, str, a((Map<String, String>) map));
        } else {
            HmsHiAnalyticsUtils.onEvent(0, str, a((Map<String, String>) map));
            HmsHiAnalyticsUtils.onEvent(1, str, a((Map<String, String>) map));
        }
        a(context);
    }

    public void onReport(Context context, String str, Map map) {
        if (hasError(context) || map == null || map.isEmpty() || context == null || !getInitFlag()) {
            return;
        }
        if (!this.f8095b) {
            HmsHiAnalyticsUtils.onEvent(0, str, a((Map<String, String>) map));
            HmsHiAnalyticsUtils.onEvent(1, str, a((Map<String, String>) map));
            HmsHiAnalyticsUtils.onReport();
        } else {
            com.huawei.hms.stats.b.a(context, 0, str, a((Map<String, String>) map));
            com.huawei.hms.stats.b.a(context, 1, str, a((Map<String, String>) map));
            com.huawei.hms.stats.b.a(context, 0);
            com.huawei.hms.stats.b.a(context, 1);
        }
    }

    public void enableLog() {
        HMSLog.i("HiAnalyticsUtils", "Enable Log");
        if (!this.f8095b) {
            HmsHiAnalyticsUtils.enableLog();
        } else {
            HMSLog.i("HiAnalyticsUtils", "cp needs to pass in the context, this method is not supported");
        }
    }

    public final void a(Context context) {
        synchronized (f8092d) {
            int i2 = this.f8094a;
            if (i2 < 60) {
                this.f8094a = i2 + 1;
            } else {
                this.f8094a = 0;
                if (!this.f8095b) {
                    HmsHiAnalyticsUtils.onReport();
                } else {
                    com.huawei.hms.stats.b.a(context, 0);
                    com.huawei.hms.stats.b.a(context, 1);
                }
            }
        }
    }

    public void onNewEvent(Context context, String str, Map map, int i2) {
        if (hasError(context)) {
            return;
        }
        if (i2 != 0 && i2 != 1) {
            HMSLog.e("HiAnalyticsUtils", "Data reporting type is not supported");
            return;
        }
        if (map == null || map.isEmpty() || context == null || !getInitFlag()) {
            return;
        }
        if (!this.f8095b) {
            HmsHiAnalyticsUtils.onEvent(i2, str, a((Map<String, String>) map));
        } else {
            com.huawei.hms.stats.b.a(context, i2, str, a((Map<String, String>) map));
        }
        a(context);
    }

    public void onReport(Context context, String str, Map map, int i2) {
        if (hasError(context)) {
            return;
        }
        if (i2 != 0 && i2 != 1) {
            HMSLog.e("HiAnalyticsUtils", "Data reporting type is not supported");
            return;
        }
        if (map == null || map.isEmpty() || context == null || !getInitFlag()) {
            return;
        }
        if (!this.f8095b) {
            HmsHiAnalyticsUtils.onEvent(i2, str, a((Map<String, String>) map));
            HmsHiAnalyticsUtils.onReport();
        } else {
            com.huawei.hms.stats.b.a(context, i2, str, a((Map<String, String>) map));
            com.huawei.hms.stats.b.a(context, i2);
        }
    }
}
