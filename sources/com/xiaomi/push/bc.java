package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.clientreport.processor.IEventProcessor;
import com.xiaomi.clientreport.processor.IPerfProcessor;

/* loaded from: classes6.dex */
public class bc implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private Context f24633a;

    /* renamed from: a, reason: collision with other field name */
    private com.xiaomi.clientreport.processor.c f205a;

    public void a(Context context) {
        this.f24633a = context;
    }

    public void a(com.xiaomi.clientreport.processor.c cVar) {
        this.f205a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        bh bhVarA;
        String str;
        long jCurrentTimeMillis;
        try {
            com.xiaomi.clientreport.processor.c cVar = this.f205a;
            if (cVar != null) {
                cVar.a();
            }
            com.xiaomi.channel.commonutils.logger.b.c("begin read and send perf / event");
            com.xiaomi.clientreport.processor.c cVar2 = this.f205a;
            if (cVar2 instanceof IEventProcessor) {
                bhVarA = bh.a(this.f24633a);
                str = "event_last_upload_time";
                jCurrentTimeMillis = System.currentTimeMillis();
            } else {
                if (!(cVar2 instanceof IPerfProcessor)) {
                    return;
                }
                bhVarA = bh.a(this.f24633a);
                str = "perf_last_upload_time";
                jCurrentTimeMillis = System.currentTimeMillis();
            }
            bhVarA.m231a("sp_client_report_status", str, jCurrentTimeMillis);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }
}
