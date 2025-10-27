package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.clientreport.data.EventClientReport;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.clientreport.manager.ClientReportClient;

/* loaded from: classes6.dex */
public class fl {

    /* renamed from: a, reason: collision with root package name */
    private static volatile fl f24852a;

    /* renamed from: a, reason: collision with other field name */
    private Context f421a;

    private fl(Context context) {
        this.f421a = context;
    }

    public static fl a(Context context) {
        if (f24852a == null) {
            synchronized (fl.class) {
                if (f24852a == null) {
                    f24852a = new fl(context);
                }
            }
        }
        return f24852a;
    }

    private void a(com.xiaomi.clientreport.data.a aVar) {
        if (aVar instanceof PerfClientReport) {
            ClientReportClient.reportPerf(this.f421a, (PerfClientReport) aVar);
        } else if (aVar instanceof EventClientReport) {
            ClientReportClient.reportEvent(this.f421a, (EventClientReport) aVar);
        }
    }

    public void a(String str, int i2, long j2, long j3) {
        if (i2 < 0 || j3 < 0 || j2 <= 0) {
            return;
        }
        PerfClientReport perfClientReportA = fk.a(this.f421a, i2, j2, j3);
        perfClientReportA.setAppPackageName(str);
        a(perfClientReportA);
    }

    public void a(String str, Intent intent, int i2, String str2) {
        if (intent == null) {
            return;
        }
        a(str, fk.m418a(intent.getIntExtra("eventMessageType", -1)), intent.getStringExtra("messageId"), i2, System.currentTimeMillis(), str2);
    }

    public void a(String str, Intent intent, String str2) {
        if (intent == null) {
            return;
        }
        a(str, fk.m418a(intent.getIntExtra("eventMessageType", -1)), intent.getStringExtra("messageId"), 5001, System.currentTimeMillis(), str2);
    }

    public void a(String str, Intent intent, Throwable th) {
        if (intent == null) {
            return;
        }
        a(str, fk.m418a(intent.getIntExtra("eventMessageType", -1)), intent.getStringExtra("messageId"), 5001, System.currentTimeMillis(), th.getMessage());
    }

    public void a(String str, String str2, String str3, int i2, long j2, String str4) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        EventClientReport eventClientReportA = fk.a(this.f421a, str2, str3, i2, j2, str4);
        eventClientReportA.setAppPackageName(str);
        a(eventClientReportA);
    }

    public void a(String str, String str2, String str3, int i2, String str4) {
        a(str, str2, str3, i2, System.currentTimeMillis(), str4);
    }

    public void a(String str, String str2, String str3, String str4) {
        a(str, str2, str3, 5002, System.currentTimeMillis(), str4);
    }

    public void a(String str, String str2, String str3, Throwable th) {
        a(str, str2, str3, 5001, System.currentTimeMillis(), th.getMessage());
    }

    public void b(String str, String str2, String str3, String str4) {
        a(str, str2, str3, 5001, System.currentTimeMillis(), str4);
    }

    public void c(String str, String str2, String str3, String str4) {
        a(str, str2, str3, 4002, System.currentTimeMillis(), str4);
    }
}
