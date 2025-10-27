package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes6.dex */
public class az implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private Context f24621a;

    /* renamed from: a, reason: collision with other field name */
    private com.xiaomi.clientreport.data.a f201a;

    /* renamed from: a, reason: collision with other field name */
    private com.xiaomi.clientreport.processor.d f202a;

    public az(Context context, com.xiaomi.clientreport.data.a aVar, com.xiaomi.clientreport.processor.d dVar) {
        this.f24621a = context;
        this.f201a = aVar;
        this.f202a = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.clientreport.processor.d dVar = this.f202a;
        if (dVar != null) {
            dVar.mo122a(this.f201a);
        }
    }
}
