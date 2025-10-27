package com.xiaomi.clientreport.manager;

import android.content.Context;
import com.xiaomi.clientreport.data.Config;
import com.xiaomi.clientreport.data.EventClientReport;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.clientreport.processor.IEventProcessor;
import com.xiaomi.clientreport.processor.IPerfProcessor;
import com.xiaomi.push.ai;
import com.xiaomi.push.az;
import com.xiaomi.push.ba;
import com.xiaomi.push.bb;
import com.xiaomi.push.bc;
import com.xiaomi.push.be;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static volatile a f24493a;

    /* renamed from: a, reason: collision with other field name */
    private Context f94a;

    /* renamed from: a, reason: collision with other field name */
    private Config f95a;

    /* renamed from: a, reason: collision with other field name */
    private IEventProcessor f96a;

    /* renamed from: a, reason: collision with other field name */
    private IPerfProcessor f97a;

    /* renamed from: a, reason: collision with other field name */
    private ExecutorService f99a = Executors.newSingleThreadExecutor();

    /* renamed from: a, reason: collision with other field name */
    private HashMap<String, HashMap<String, com.xiaomi.clientreport.data.a>> f98a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> f24494b = new HashMap<>();

    private a(Context context) {
        this.f94a = context;
    }

    public static a a(Context context) {
        if (f24493a == null) {
            synchronized (a.class) {
                if (f24493a == null) {
                    f24493a = new a(context);
                }
            }
        }
        return f24493a;
    }

    private void a(Runnable runnable, int i2) {
        ai.a(this.f94a).a(runnable, i2);
    }

    private void d() {
        int iB = be.b(this.f94a);
        int eventUploadFrequency = (int) a().getEventUploadFrequency();
        if (iB >= 0) {
            synchronized (a.class) {
                if (!ai.a(this.f94a).a(new ba(this.f94a), eventUploadFrequency, iB)) {
                    ai.a(this.f94a).m196a(100886);
                    ai.a(this.f94a).a(new ba(this.f94a), eventUploadFrequency, iB);
                }
            }
        }
    }

    private void e() {
        int iA = be.a(this.f94a);
        int perfUploadFrequency = (int) a().getPerfUploadFrequency();
        if (iA >= 0) {
            synchronized (a.class) {
                if (!ai.a(this.f94a).a(new bb(this.f94a), perfUploadFrequency, iA)) {
                    ai.a(this.f94a).m196a(100887);
                    ai.a(this.f94a).a(new bb(this.f94a), perfUploadFrequency, iA);
                }
            }
        }
    }

    public synchronized Config a() {
        if (this.f95a == null) {
            this.f95a = Config.defaultConfig(this.f94a);
        }
        return this.f95a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m121a() {
        a(this.f94a).d();
        a(this.f94a).e();
    }

    public void a(Config config, IEventProcessor iEventProcessor, IPerfProcessor iPerfProcessor) {
        this.f95a = config;
        this.f96a = iEventProcessor;
        this.f97a = iPerfProcessor;
        iEventProcessor.setEventMap(this.f24494b);
        this.f97a.setPerfMap(this.f98a);
    }

    public void a(EventClientReport eventClientReport) {
        if (a().isEventUploadSwitchOpen()) {
            this.f99a.execute(new az(this.f94a, eventClientReport, this.f96a));
            a(new b(this), 30);
        }
    }

    public void a(PerfClientReport perfClientReport) {
        if (a().isPerfUploadSwitchOpen()) {
            this.f99a.execute(new az(this.f94a, perfClientReport, this.f97a));
            a(new c(this), 30);
        }
    }

    public void a(boolean z2, boolean z3, long j2, long j3) {
        Config config = this.f95a;
        if (config != null) {
            if (z2 == config.isEventUploadSwitchOpen() && z3 == this.f95a.isPerfUploadSwitchOpen() && j2 == this.f95a.getEventUploadFrequency() && j3 == this.f95a.getPerfUploadFrequency()) {
                return;
            }
            long eventUploadFrequency = this.f95a.getEventUploadFrequency();
            long perfUploadFrequency = this.f95a.getPerfUploadFrequency();
            Config configBuild = Config.getBuilder().setAESKey(be.m227a(this.f94a)).setEventEncrypted(this.f95a.isEventEncrypted()).setEventUploadSwitchOpen(z2).setEventUploadFrequency(j2).setPerfUploadSwitchOpen(z3).setPerfUploadFrequency(j3).build(this.f94a);
            this.f95a = configBuild;
            if (!configBuild.isEventUploadSwitchOpen()) {
                ai.a(this.f94a).m196a(100886);
            } else if (eventUploadFrequency != configBuild.getEventUploadFrequency()) {
                com.xiaomi.channel.commonutils.logger.b.c(this.f94a.getPackageName() + "reset event job " + configBuild.getEventUploadFrequency());
                d();
            }
            if (!this.f95a.isPerfUploadSwitchOpen()) {
                ai.a(this.f94a).m196a(100887);
                return;
            }
            if (perfUploadFrequency != configBuild.getPerfUploadFrequency()) {
                com.xiaomi.channel.commonutils.logger.b.c(this.f94a.getPackageName() + "reset perf job " + configBuild.getPerfUploadFrequency());
                e();
            }
        }
    }

    public void b() {
        if (a().isEventUploadSwitchOpen()) {
            bc bcVar = new bc();
            bcVar.a(this.f94a);
            bcVar.a(this.f96a);
            this.f99a.execute(bcVar);
        }
    }

    public void c() {
        if (a().isPerfUploadSwitchOpen()) {
            bc bcVar = new bc();
            bcVar.a(this.f97a);
            bcVar.a(this.f94a);
            this.f99a.execute(bcVar);
        }
    }
}
