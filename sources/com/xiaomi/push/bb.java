package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.ai;

/* loaded from: classes6.dex */
public class bb extends ai.a {

    /* renamed from: a, reason: collision with root package name */
    private Context f24632a;

    public bb(Context context) {
        this.f24632a = context;
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 100887;
    }

    public boolean a() {
        if (!com.xiaomi.clientreport.manager.a.a(this.f24632a).a().isPerfUploadSwitchOpen()) {
            return false;
        }
        boolean z2 = ((int) ((System.currentTimeMillis() - bh.a(this.f24632a).a("sp_client_report_status", "perf_last_upload_time", 0L)) / 1000)) >= ((int) com.xiaomi.clientreport.manager.a.a(this.f24632a).a().getPerfUploadFrequency()) + (-5);
        com.xiaomi.channel.commonutils.logger.b.c(this.f24632a.getPackageName() + "perf upload result " + z2);
        return z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (a()) {
                com.xiaomi.clientreport.manager.a.a(this.f24632a).c();
                com.xiaomi.channel.commonutils.logger.b.c(this.f24632a.getPackageName() + "perf  begin upload");
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }
}
