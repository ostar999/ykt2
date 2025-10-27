package com.alipay.security.mobile.module.http.v2;

import android.content.Context;
import com.alipay.security.mobile.module.http.model.c;
import com.alipay.security.mobile.module.http.model.d;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class b implements a {

    /* renamed from: a, reason: collision with root package name */
    private static a f3469a;

    /* renamed from: b, reason: collision with root package name */
    private static com.alipay.security.mobile.module.http.a f3470b;

    @Override // com.alipay.security.mobile.module.http.v2.a
    public final c a(d dVar) {
        DataReportRequest dataReportRequest = new DataReportRequest();
        dataReportRequest.os = com.alipay.security.mobile.module.a.a.d(dVar.f3459a);
        dataReportRequest.rpcVersion = dVar.f3468j;
        dataReportRequest.bizType = "1";
        HashMap map = new HashMap();
        dataReportRequest.bizData = map;
        map.put("apdid", com.alipay.security.mobile.module.a.a.d(dVar.f3460b));
        dataReportRequest.bizData.put("apdidToken", com.alipay.security.mobile.module.a.a.d(dVar.f3461c));
        dataReportRequest.bizData.put("umidToken", com.alipay.security.mobile.module.a.a.d(dVar.f3462d));
        dataReportRequest.bizData.put("dynamicKey", dVar.f3463e);
        Map<String, String> map2 = dVar.f3464f;
        if (map2 == null) {
            map2 = new HashMap<>();
        }
        dataReportRequest.deviceData = map2;
        return com.alipay.security.mobile.module.http.model.b.a(f3470b.a(dataReportRequest));
    }

    @Override // com.alipay.security.mobile.module.http.v2.a
    public final boolean a(String str) {
        return f3470b.a(str);
    }

    public static a a(Context context, String str) {
        if (context == null) {
            return null;
        }
        if (f3469a == null) {
            f3470b = com.alipay.security.mobile.module.http.b.a(context, str);
            f3469a = new b();
        }
        return f3469a;
    }
}
