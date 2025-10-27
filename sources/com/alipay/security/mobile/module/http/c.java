package com.alipay.security.mobile.module.http;

import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportResult;

/* loaded from: classes2.dex */
final class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ DataReportRequest f3439a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ b f3440b;

    public c(b bVar, DataReportRequest dataReportRequest) {
        this.f3440b = bVar;
        this.f3439a = dataReportRequest;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            DataReportResult unused = b.f3435e = this.f3440b.f3438c.a();
        } catch (Throwable th) {
            DataReportResult unused2 = b.f3435e = new DataReportResult();
            b.f3435e.success = false;
            b.f3435e.resultCode = "static data rpc upload error, " + com.alipay.security.mobile.module.a.a.a(th);
            com.alipay.security.mobile.module.a.a.a(th);
        }
    }
}
