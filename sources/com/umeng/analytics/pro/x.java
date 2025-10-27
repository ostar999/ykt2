package com.umeng.analytics.pro;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;

/* loaded from: classes6.dex */
class x implements w {

    /* renamed from: a, reason: collision with root package name */
    private long f22953a = AnalyticsConfig.kContinueSessionMillis;

    @Override // com.umeng.analytics.pro.w
    public void a(long j2) {
        this.f22953a = j2;
    }

    @Override // com.umeng.analytics.pro.w
    public long a() {
        return this.f22953a;
    }

    @Override // com.umeng.analytics.pro.w
    public String a(Context context) {
        String appkey = UMUtils.getAppkey(context);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (appkey != null) {
            return UMUtils.MD5(jCurrentTimeMillis + appkey + "02:00:00:00:00:00");
        }
        throw new RuntimeException("Appkey is null or empty, Please check!");
    }

    @Override // com.umeng.analytics.pro.w
    public boolean a(long j2, long j3) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        return (j2 == 0 || jCurrentTimeMillis - j2 >= this.f22953a) && j3 > 0 && jCurrentTimeMillis - j3 > this.f22953a;
    }

    @Override // com.umeng.analytics.pro.w
    public void a(Context context, String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(context).edit();
            editorEdit.putString("session_id", str);
            editorEdit.putLong(u.f22934b, 0L);
            editorEdit.putLong(u.f22937e, jCurrentTimeMillis);
            editorEdit.putLong(u.f22938f, 0L);
            editorEdit.commit();
        } catch (Exception unused) {
        }
    }
}
