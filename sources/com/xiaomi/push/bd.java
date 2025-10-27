package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes6.dex */
public class bd implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private Context f24634a;

    /* renamed from: a, reason: collision with other field name */
    private com.xiaomi.clientreport.processor.d f206a;

    public bd(Context context, com.xiaomi.clientreport.processor.d dVar) {
        this.f24634a = context;
        this.f206a = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            com.xiaomi.clientreport.processor.d dVar = this.f206a;
            if (dVar != null) {
                dVar.b();
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }
}
