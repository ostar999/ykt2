package com.umeng.analytics;

import android.content.Context;
import com.umeng.analytics.pro.o;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import com.umeng.commonsdk.framework.UMSenderStateNotify;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class CoreProtocol implements UMLogDataProtocol, UMSenderStateNotify {

    /* renamed from: a, reason: collision with root package name */
    private static Context f22348a;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final CoreProtocol f22349a = new CoreProtocol();

        private a() {
        }
    }

    public static CoreProtocol getInstance(Context context) {
        if (f22348a == null && context != null) {
            f22348a = context.getApplicationContext();
        }
        return a.f22349a;
    }

    @Override // com.umeng.commonsdk.framework.UMSenderStateNotify
    public void onConnectionAvailable() {
        o.a(f22348a).a();
    }

    @Override // com.umeng.commonsdk.framework.UMSenderStateNotify
    public void onSenderIdle() {
        o.a(f22348a).b();
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public void removeCacheData(Object obj) {
        o.a(f22348a).a(obj);
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public JSONObject setupReportData(long j2) {
        return o.a(f22348a).a(j2);
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public void workEvent(Object obj, int i2) {
        o.a(f22348a).a(obj, i2);
    }

    private CoreProtocol() {
    }
}
