package com.xiaomi.push;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.SystemClock;
import com.xiaomi.push.fm;
import com.xiaomi.push.service.XMJobService;

@TargetApi(21)
/* loaded from: classes6.dex */
public class fo implements fm.a {

    /* renamed from: a, reason: collision with root package name */
    JobScheduler f24855a;

    /* renamed from: a, reason: collision with other field name */
    Context f426a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f427a = false;

    public fo(Context context) {
        this.f426a = context;
        this.f24855a = (JobScheduler) context.getSystemService("jobscheduler");
    }

    @Override // com.xiaomi.push.fm.a
    /* renamed from: a */
    public void mo423a() {
        this.f427a = false;
        this.f24855a.cancel(1);
    }

    public void a(long j2) {
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(this.f426a.getPackageName(), XMJobService.class.getName()));
        builder.setMinimumLatency(j2);
        builder.setOverrideDeadline(j2);
        builder.setRequiredNetworkType(1);
        builder.setPersisted(false);
        com.xiaomi.channel.commonutils.logger.b.c("schedule Job = " + builder.build().getId() + " in " + j2);
        this.f24855a.schedule(builder.build());
    }

    @Override // com.xiaomi.push.fm.a
    public void a(boolean z2) {
        if (z2 || this.f427a) {
            long jB = gi.b();
            if (z2) {
                mo423a();
                jB -= SystemClock.elapsedRealtime() % jB;
            }
            this.f427a = true;
            a(jB);
        }
    }

    @Override // com.xiaomi.push.fm.a
    /* renamed from: a */
    public boolean mo422a() {
        return this.f427a;
    }
}
