package com.xiaomi.push;

import android.os.SystemClock;
import com.xiaomi.push.service.XMPushService;
import java.io.IOException;
import java.net.Socket;

/* loaded from: classes6.dex */
public abstract class gj extends gc {

    /* renamed from: a, reason: collision with root package name */
    protected Exception f24934a;

    /* renamed from: a, reason: collision with other field name */
    protected Socket f490a;

    /* renamed from: b, reason: collision with root package name */
    protected XMPushService f24935b;

    /* renamed from: c, reason: collision with root package name */
    private int f24936c;

    /* renamed from: c, reason: collision with other field name */
    String f491c;

    /* renamed from: d, reason: collision with root package name */
    private String f24937d;

    /* renamed from: e, reason: collision with root package name */
    protected volatile long f24938e;

    /* renamed from: f, reason: collision with root package name */
    protected volatile long f24939f;

    /* renamed from: g, reason: collision with root package name */
    protected volatile long f24940g;

    public gj(XMPushService xMPushService, gd gdVar) {
        super(xMPushService, gdVar);
        this.f24934a = null;
        this.f491c = null;
        this.f24938e = 0L;
        this.f24939f = 0L;
        this.f24940g = 0L;
        this.f24935b = xMPushService;
    }

    private void a(gd gdVar) throws Throwable {
        a(gdVar.c(), gdVar.m457a());
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x0276, code lost:
    
        if (android.text.TextUtils.equals(r5, com.xiaomi.push.as.m201a((android.content.Context) r24.f24935b)) == false) goto L70;
     */
    /* JADX WARN: Removed duplicated region for block: B:61:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0286 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0287  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.lang.String r25, int r26) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 657
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.gj.a(java.lang.String, int):void");
    }

    public cy a(String str) {
        cy cyVarA = dc.a().a(str, false);
        if (!cyVarA.b()) {
            hf.a(new gm(this, str));
        }
        return cyVarA;
    }

    @Override // com.xiaomi.push.gc
    /* renamed from: a */
    public String mo450a() {
        return this.f24937d;
    }

    public Socket a() {
        return new Socket();
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void mo462a() {
    }

    public synchronized void a(int i2, Exception exc) {
        if (b() == 2) {
            return;
        }
        a(2, i2, exc);
        ((gc) this).f476a = "";
        try {
            this.f490a.close();
        } catch (Throwable unused) {
        }
        this.f24938e = 0L;
        this.f24939f = 0L;
    }

    public void a(Exception exc) {
        if (SystemClock.elapsedRealtime() - this.f24940g < 300000) {
            if (!as.b(this.f24935b)) {
                return;
            }
            int i2 = this.f24936c + 1;
            this.f24936c = i2;
            if (i2 < 2) {
                return;
            }
            String strMo450a = mo450a();
            com.xiaomi.channel.commonutils.logger.b.m117a("max short conn time reached, sink down current host:" + strMo450a);
            a(strMo450a, 0L, exc);
        }
        this.f24936c = 0;
    }

    public void a(String str, long j2, Exception exc) {
        cy cyVarA = dc.a().a(gd.a(), false);
        if (cyVarA != null) {
            cyVarA.b(str, j2, 0L, exc);
            dc.a().m321c();
        }
    }

    /* renamed from: a */
    public abstract void mo444a(boolean z2);

    @Override // com.xiaomi.push.gc
    public void a(fv[] fvVarArr) throws gn {
        throw new gn("Don't support send Blob");
    }

    @Override // com.xiaomi.push.gc
    public void b(int i2, Exception exc) {
        a(i2, exc);
        if ((exc != null || i2 == 18) && this.f24940g != 0) {
            a(exc);
        }
    }

    @Override // com.xiaomi.push.gc
    public void b(boolean z2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        mo444a(z2);
        if (z2) {
            return;
        }
        this.f24935b.a(new gk(this, 13, jCurrentTimeMillis), com.heytap.mcssdk.constant.a.f7153q);
    }

    public String c() {
        return ((gc) this).f476a;
    }

    public void c(int i2, Exception exc) {
        this.f24935b.a(new gl(this, 2, i2, exc));
    }

    public synchronized void f() {
        try {
            if (!m454c() && !m453b()) {
                a(0, 0, (Exception) null);
                a(((gc) this).f473a);
                return;
            }
            com.xiaomi.channel.commonutils.logger.b.m117a("WARNING: current xmpp has connected");
        } catch (IOException e2) {
            throw new gn(e2);
        }
    }

    public void g() {
        this.f24938e = SystemClock.elapsedRealtime();
    }

    public void h() {
        this.f24939f = SystemClock.elapsedRealtime();
    }
}
