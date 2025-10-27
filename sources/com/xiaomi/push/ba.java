package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.ai;

/* loaded from: classes6.dex */
public class ba extends ai.a {

    /* renamed from: a, reason: collision with root package name */
    private Context f24631a;

    public ba(Context context) {
        this.f24631a = context;
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 100886;
    }

    public boolean a() {
        if (!com.xiaomi.clientreport.manager.a.a(this.f24631a).a().isEventUploadSwitchOpen()) {
            return false;
        }
        boolean z2 = ((int) ((System.currentTimeMillis() - bh.a(this.f24631a).a("sp_client_report_status", "event_last_upload_time", 0L)) / 1000)) >= ((int) com.xiaomi.clientreport.manager.a.a(this.f24631a).a().getEventUploadFrequency()) + (-5);
        com.xiaomi.channel.commonutils.logger.b.c(this.f24631a.getPackageName() + " event upload result " + z2);
        return z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (a()) {
                com.xiaomi.channel.commonutils.logger.b.c(this.f24631a.getPackageName() + " begin upload event");
                com.xiaomi.clientreport.manager.a.a(this.f24631a).b();
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }
}
