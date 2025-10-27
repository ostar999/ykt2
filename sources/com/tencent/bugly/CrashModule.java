package com.tencent.bugly;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.aa;
import com.tencent.bugly.proguard.al;
import com.tencent.bugly.proguard.aq;
import com.tencent.bugly.proguard.at;
import com.tencent.bugly.proguard.au;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.u;
import com.tencent.bugly.proguard.z;

/* loaded from: classes6.dex */
public class CrashModule extends o {
    public static final int MODULE_ID = 1004;

    /* renamed from: c, reason: collision with root package name */
    private static int f17306c;

    /* renamed from: e, reason: collision with root package name */
    private static CrashModule f17307e = new CrashModule();

    /* renamed from: a, reason: collision with root package name */
    private long f17308a;

    /* renamed from: b, reason: collision with root package name */
    private BuglyStrategy.a f17309b;

    /* renamed from: d, reason: collision with root package name */
    private boolean f17310d = false;

    private synchronized void a(Context context, BuglyStrategy buglyStrategy) {
        if (buglyStrategy == null) {
            return;
        }
        String libBuglySOFilePath = buglyStrategy.getLibBuglySOFilePath();
        if (!TextUtils.isEmpty(libBuglySOFilePath)) {
            aa.a(context).f17434t = libBuglySOFilePath;
            al.a("setted libBugly.so file path :%s", libBuglySOFilePath);
        }
        if (buglyStrategy.getCrashHandleCallback() != null) {
            this.f17309b = buglyStrategy.getCrashHandleCallback();
            al.a("setted CrashHanldeCallback", new Object[0]);
        }
        if (buglyStrategy.getAppReportDelay() > 0) {
            long appReportDelay = buglyStrategy.getAppReportDelay();
            this.f17308a = appReportDelay;
            al.a("setted delay: %d", Long.valueOf(appReportDelay));
        }
    }

    public static CrashModule getInstance() {
        CrashModule crashModule = f17307e;
        crashModule.id = 1004;
        return crashModule;
    }

    @Override // com.tencent.bugly.proguard.o
    public String[] getTables() {
        return new String[]{"t_cr"};
    }

    public synchronized boolean hasInitialized() {
        return this.f17310d;
    }

    @Override // com.tencent.bugly.proguard.o
    public synchronized void init(Context context, boolean z2, BuglyStrategy buglyStrategy) {
        if (context != null) {
            if (!this.f17310d) {
                al.a("Initializing crash module.", new Object[0]);
                u uVarA = u.a();
                int i2 = f17306c + 1;
                f17306c = i2;
                uVarA.a(i2);
                this.f17310d = true;
                CrashReport.setContext(context);
                a(context, buglyStrategy);
                at atVarA = at.a(context, z2, this.f17309b);
                atVarA.f17596t.a();
                if (buglyStrategy != null) {
                    atVarA.B = buglyStrategy.getCallBackType();
                    atVarA.C = buglyStrategy.getCloseErrorCallback();
                    at.f17590o = buglyStrategy.isUploadSpotCrash();
                    aa.a(context).S = buglyStrategy.isEnableRecordAnrMainStack();
                    if (buglyStrategy.isEnableCatchAnrTrace()) {
                        atVarA.f17597u.enableCatchAnrTrace();
                    } else {
                        atVarA.f17597u.disableCatchAnrTrace();
                    }
                } else {
                    atVarA.f17597u.enableCatchAnrTrace();
                }
                if (aa.b().f17418d.equals(z.a(atVarA.f17594c))) {
                    atVarA.f17597u.removeEmptyNativeRecordFiles();
                }
                if (buglyStrategy == null || buglyStrategy.isEnableNativeCrashMonitor()) {
                    atVarA.e();
                } else {
                    al.a("[crash] Closed native crash monitor!", new Object[0]);
                    atVarA.d();
                }
                if (buglyStrategy == null || buglyStrategy.isEnableANRCrashMonitor()) {
                    atVarA.f();
                } else {
                    al.a("[crash] Closed ANR monitor!", new Object[0]);
                    atVarA.g();
                }
                if (buglyStrategy != null) {
                    at.f17580e = buglyStrategy.isMerged();
                }
                atVarA.a(buglyStrategy != null ? buglyStrategy.getAppReportDelay() : 0L);
                atVarA.f17597u.checkUploadRecordCrash();
                au.a(context);
                aq aqVarA = aq.a();
                aqVarA.a("android.net.conn.CONNECTIVITY_CHANGE");
                aqVarA.a(context);
                u uVarA2 = u.a();
                int i3 = f17306c - 1;
                f17306c = i3;
                uVarA2.a(i3);
            }
        }
    }

    @Override // com.tencent.bugly.proguard.o
    public void onServerStrategyChanged(StrategyBean strategyBean) {
        at atVarA;
        if (strategyBean == null || (atVarA = at.a()) == null) {
            return;
        }
        atVarA.f17596t.a(strategyBean);
        atVarA.f17597u.onStrategyChanged(strategyBean);
        atVarA.f17600x.b();
    }
}
