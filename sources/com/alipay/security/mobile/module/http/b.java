package com.alipay.security.mobile.module.http;

import android.content.Context;
import com.alipay.android.phone.mrpc.core.aa;
import com.alipay.android.phone.mrpc.core.h;
import com.alipay.android.phone.mrpc.core.w;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportResult;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class b implements a {

    /* renamed from: d, reason: collision with root package name */
    private static b f3434d;

    /* renamed from: e, reason: collision with root package name */
    private static DataReportResult f3435e;

    /* renamed from: a, reason: collision with root package name */
    private w f3436a;

    /* renamed from: b, reason: collision with root package name */
    private com.alipay.tscenter.biz.rpc.a.a f3437b;

    /* renamed from: c, reason: collision with root package name */
    private com.alipay.tscenter.biz.rpc.report.general.a f3438c;

    private b(Context context, String str) {
        this.f3436a = null;
        this.f3437b = null;
        this.f3438c = null;
        aa aaVar = new aa();
        aaVar.a(str);
        h hVar = new h(context);
        this.f3436a = hVar;
        this.f3437b = (com.alipay.tscenter.biz.rpc.a.a) hVar.a(com.alipay.tscenter.biz.rpc.a.a.class, aaVar);
        this.f3438c = (com.alipay.tscenter.biz.rpc.report.general.a) this.f3436a.a(com.alipay.tscenter.biz.rpc.report.general.a.class, aaVar);
    }

    public static synchronized b a(Context context, String str) {
        if (f3434d == null) {
            f3434d = new b(context, str);
        }
        return f3434d;
    }

    @Override // com.alipay.security.mobile.module.http.a
    public final DataReportResult a(DataReportRequest dataReportRequest) throws InterruptedException {
        if (this.f3438c != null) {
            f3435e = null;
            new Thread(new c(this, dataReportRequest)).start();
            for (int i2 = com.alipay.security.mobile.module.http.constant.a.f3441a; f3435e == null && i2 >= 0; i2 -= 50) {
                Thread.sleep(50L);
            }
        }
        return f3435e;
    }

    @Override // com.alipay.security.mobile.module.http.a
    public final boolean a(String str) {
        com.alipay.tscenter.biz.rpc.a.a aVar;
        String strA;
        if (com.alipay.security.mobile.module.a.a.a(str) || (aVar = this.f3437b) == null) {
            return false;
        }
        try {
            com.alipay.security.mobile.module.a.a.f(str);
            strA = aVar.a();
        } catch (Throwable unused) {
            strA = null;
        }
        if (com.alipay.security.mobile.module.a.a.a(strA)) {
            return false;
        }
        return ((Boolean) new JSONObject(strA).get("success")).booleanValue();
    }
}
