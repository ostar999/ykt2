package com.huawei.hms.hatool;

import android.content.Context;

/* loaded from: classes4.dex */
public class m1 {

    /* renamed from: a, reason: collision with root package name */
    public k f7823a;

    /* renamed from: b, reason: collision with root package name */
    public k f7824b;

    /* renamed from: c, reason: collision with root package name */
    public Context f7825c;

    /* renamed from: d, reason: collision with root package name */
    public String f7826d;

    public m1(Context context) {
        if (context != null) {
            this.f7825c = context.getApplicationContext();
        }
        this.f7823a = new k();
        this.f7824b = new k();
    }

    public m1 a(int i2, String str) {
        k kVar;
        y.c("hmsSdk", "Builder.setCollectURL(int type,String collectURL) is execute.TYPE : " + i2);
        if (!v0.b(str)) {
            str = "";
        }
        if (i2 == 0) {
            kVar = this.f7823a;
        } else {
            if (i2 != 1) {
                y.f("hmsSdk", "Builder.setCollectURL(int type,String collectURL): invalid type!");
                return this;
            }
            kVar = this.f7824b;
        }
        kVar.b(str);
        return this;
    }

    public m1 a(String str) {
        y.c("hmsSdk", "Builder.setAppID is execute");
        this.f7826d = str;
        return this;
    }

    @Deprecated
    public m1 a(boolean z2) {
        y.c("hmsSdk", "Builder.setEnableImei(boolean isReportAndroidImei) is execute.");
        this.f7823a.j().a(z2);
        this.f7824b.j().a(z2);
        return this;
    }

    public void a() {
        if (this.f7825c == null) {
            y.b("hmsSdk", "analyticsConf create(): context is null,create failed!");
            return;
        }
        y.c("hmsSdk", "Builder.create() is execute.");
        j1 j1Var = new j1("_hms_config_tag");
        j1Var.b(new k(this.f7823a));
        j1Var.a(new k(this.f7824b));
        h1.a().a(this.f7825c);
        i1.a().a(this.f7825c);
        o1.c().a(j1Var);
        h1.a().a(this.f7826d);
    }

    @Deprecated
    public m1 b(boolean z2) {
        y.c("hmsSdk", "Builder.setEnableSN(boolean isReportSN) is execute.");
        this.f7823a.j().b(z2);
        this.f7824b.j().b(z2);
        return this;
    }

    @Deprecated
    public m1 c(boolean z2) {
        y.c("hmsSdk", "Builder.setEnableUDID(boolean isReportUDID) is execute.");
        this.f7823a.j().c(z2);
        this.f7824b.j().c(z2);
        return this;
    }
}
