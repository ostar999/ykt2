package com.tencent.smtt.utils;

import android.content.Context;
import com.tencent.smtt.sdk.TbsLogReport;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class r {

    /* renamed from: a, reason: collision with root package name */
    private static r f21605a;

    /* renamed from: c, reason: collision with root package name */
    private boolean f21607c = false;

    /* renamed from: b, reason: collision with root package name */
    private Map<String, Long> f21606b = new HashMap();

    private r() {
    }

    public static r a() {
        if (f21605a == null) {
            synchronized (TbsLogReport.class) {
                if (f21605a == null) {
                    f21605a = new r();
                }
            }
        }
        return f21605a;
    }

    private boolean a(long j2) {
        return j2 <= 100000 && j2 > 0;
    }

    private long b(String str) {
        Long l2 = this.f21606b.get(str);
        if (l2 != null) {
            return l2.longValue();
        }
        return 0L;
    }

    private String b() {
        long jB = b("init_tbs_end") - b("init_tbs_Start");
        long jB2 = b("preinit_finish") - b("preinit_start");
        long jB3 = b("create_webview_end") - b("create_webview_start");
        StringBuilder sb = new StringBuilder();
        sb.append("");
        if (!a(jB)) {
            jB = -1;
        }
        sb.append(jB);
        sb.append(",");
        String string = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(string);
        if (!a(jB2)) {
            jB2 = -1;
        }
        sb2.append(jB2);
        sb2.append(",");
        String string2 = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(string2);
        if (!a(jB3)) {
            jB3 = -1;
        }
        sb3.append(jB3);
        return sb3.toString();
    }

    private String c() {
        long jB = b("init_tbs_end") - b("init_tbs_Start");
        long jB2 = b("preinit_finish") - b("preinit_start");
        long jB3 = b("create_webview_end") - b("create_webview_start");
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append("initX5Environment: ");
        if (!a(jB)) {
            jB = -1;
        }
        sb.append(jB);
        sb.append(",");
        String string = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(string);
        sb2.append("preInit: ");
        if (!a(jB2)) {
            jB2 = -1;
        }
        sb2.append(jB2);
        sb2.append(",");
        String string2 = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(string2);
        sb3.append("webview: ");
        if (!a(jB3)) {
            jB3 = -1;
        }
        sb3.append(jB3);
        return sb3.toString();
    }

    public void a(Context context) {
        if (this.f21607c) {
            return;
        }
        TbsLog.i("TbsTimeRecorder", c());
        this.f21607c = true;
        TbsLogReport tbsLogReport = TbsLogReport.getInstance(context);
        TbsLogReport.TbsLogInfo tbsLogInfo = tbsLogReport.tbsLogInfo();
        tbsLogInfo.setFailDetail(b());
        tbsLogReport.eventReport(TbsLogReport.EventType.TYPE_CORE_LOAD_PERFORMANCE, tbsLogInfo);
    }

    public void a(String str) {
        this.f21606b.put(str, Long.valueOf(System.currentTimeMillis()));
    }
}
