package com.xiaomi.push;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes6.dex */
public class hn implements gf {

    /* renamed from: a, reason: collision with root package name */
    private int f25065a;

    /* renamed from: a, reason: collision with other field name */
    gc f536a;

    /* renamed from: a, reason: collision with other field name */
    XMPushService f537a;

    /* renamed from: a, reason: collision with other field name */
    private Exception f538a;

    /* renamed from: e, reason: collision with root package name */
    private long f25069e;

    /* renamed from: f, reason: collision with root package name */
    private long f25070f;

    /* renamed from: a, reason: collision with other field name */
    private long f535a = 0;

    /* renamed from: b, reason: collision with root package name */
    private long f25066b = 0;

    /* renamed from: c, reason: collision with root package name */
    private long f25067c = 0;

    /* renamed from: d, reason: collision with root package name */
    private long f25068d = 0;

    /* renamed from: a, reason: collision with other field name */
    private String f539a = "";

    public hn(XMPushService xMPushService) {
        this.f25069e = 0L;
        this.f25070f = 0L;
        this.f537a = xMPushService;
        b();
        int iMyUid = Process.myUid();
        this.f25070f = TrafficStats.getUidRxBytes(iMyUid);
        this.f25069e = TrafficStats.getUidTxBytes(iMyUid);
    }

    private void b() {
        this.f25066b = 0L;
        this.f25068d = 0L;
        this.f535a = 0L;
        this.f25067c = 0L;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (as.b(this.f537a)) {
            this.f535a = jElapsedRealtime;
        }
        if (this.f537a.m702c()) {
            this.f25067c = jElapsedRealtime;
        }
    }

    private synchronized void c() {
        com.xiaomi.channel.commonutils.logger.b.c("stat connpt = " + this.f539a + " netDuration = " + this.f25066b + " ChannelDuration = " + this.f25068d + " channelConnectedTime = " + this.f25067c);
        fs fsVar = new fs();
        fsVar.f433a = (byte) 0;
        fsVar.a(fr.CHANNEL_ONLINE_RATE.a());
        fsVar.a(this.f539a);
        fsVar.d((int) (System.currentTimeMillis() / 1000));
        fsVar.b((int) (this.f25066b / 1000));
        fsVar.c((int) (this.f25068d / 1000));
        ho.m488a().a(fsVar);
        b();
    }

    public Exception a() {
        return this.f538a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m487a() {
        XMPushService xMPushService = this.f537a;
        if (xMPushService == null) {
            return;
        }
        String strM201a = as.m201a((Context) xMPushService);
        boolean zB = as.b(this.f537a);
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j2 = this.f535a;
        if (j2 > 0) {
            this.f25066b += jElapsedRealtime - j2;
            this.f535a = 0L;
        }
        long j3 = this.f25067c;
        if (j3 != 0) {
            this.f25068d += jElapsedRealtime - j3;
            this.f25067c = 0L;
        }
        if (zB) {
            if ((!TextUtils.equals(this.f539a, strM201a) && this.f25066b > 30000) || this.f25066b > 5400000) {
                c();
            }
            this.f539a = strM201a;
            if (this.f535a == 0) {
                this.f535a = jElapsedRealtime;
            }
            if (this.f537a.m702c()) {
                this.f25067c = jElapsedRealtime;
            }
        }
    }

    @Override // com.xiaomi.push.gf
    public void a(gc gcVar) {
        m487a();
        this.f25067c = SystemClock.elapsedRealtime();
        hq.a(0, fr.CONN_SUCCESS.a(), gcVar.mo450a(), gcVar.a());
    }

    @Override // com.xiaomi.push.gf
    public void a(gc gcVar, int i2, Exception exc) {
        if (this.f25065a == 0 && this.f538a == null) {
            this.f25065a = i2;
            this.f538a = exc;
            hq.b(gcVar.mo450a(), exc);
        }
        if (i2 == 22 && this.f25067c != 0) {
            long jM448a = gcVar.m448a() - this.f25067c;
            if (jM448a < 0) {
                jM448a = 0;
            }
            this.f25068d += jM448a + (gi.b() / 2);
            this.f25067c = 0L;
        }
        m487a();
        int iMyUid = Process.myUid();
        long uidRxBytes = TrafficStats.getUidRxBytes(iMyUid);
        long uidTxBytes = TrafficStats.getUidTxBytes(iMyUid);
        com.xiaomi.channel.commonutils.logger.b.c("Stats rx=" + (uidRxBytes - this.f25070f) + ", tx=" + (uidTxBytes - this.f25069e));
        this.f25070f = uidRxBytes;
        this.f25069e = uidTxBytes;
    }

    @Override // com.xiaomi.push.gf
    public void a(gc gcVar, Exception exc) {
        hq.a(0, fr.CHANNEL_CON_FAIL.a(), 1, gcVar.mo450a(), as.b(this.f537a) ? 1 : 0);
        m487a();
    }

    @Override // com.xiaomi.push.gf
    public void b(gc gcVar) {
        this.f25065a = 0;
        this.f538a = null;
        this.f536a = gcVar;
        this.f539a = as.m201a((Context) this.f537a);
        hq.a(0, fr.CONN_SUCCESS.a());
    }
}
