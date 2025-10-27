package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.C;
import com.xiaomi.clientreport.data.Config;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.ai;
import com.xiaomi.push.dc;
import com.xiaomi.push.dl;
import com.xiaomi.push.fa;
import com.xiaomi.push.fk;
import com.xiaomi.push.fm;
import com.xiaomi.push.fv;
import com.xiaomi.push.ga;
import com.xiaomi.push.gc;
import com.xiaomi.push.gd;
import com.xiaomi.push.gf;
import com.xiaomi.push.gh;
import com.xiaomi.push.gi;
import com.xiaomi.push.gn;
import com.xiaomi.push.gr;
import com.xiaomi.push.gs;
import com.xiaomi.push.gt;
import com.xiaomi.push.gv;
import com.xiaomi.push.hh;
import com.xiaomi.push.ho;
import com.xiaomi.push.hq;
import com.xiaomi.push.hr;
import com.xiaomi.push.hu;
import com.xiaomi.push.hw;
import com.xiaomi.push.ib;
import com.xiaomi.push.ic;
import com.xiaomi.push.jb;
import com.xiaomi.push.je;
import com.xiaomi.push.jf;
import com.xiaomi.push.jp;
import com.xiaomi.push.jv;
import com.xiaomi.push.service.at;
import com.xiaomi.push.service.k;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class XMPushService extends Service implements gf {

    /* renamed from: a, reason: collision with root package name */
    public static int f25542a;

    /* renamed from: b, reason: collision with root package name */
    private static final int f25543b = Process.myPid();

    /* renamed from: a, reason: collision with other field name */
    private ContentObserver f951a;

    /* renamed from: a, reason: collision with other field name */
    private ga f953a;

    /* renamed from: a, reason: collision with other field name */
    private gc f954a;

    /* renamed from: a, reason: collision with other field name */
    private gd f955a;

    /* renamed from: a, reason: collision with other field name */
    private e f957a;

    /* renamed from: a, reason: collision with other field name */
    private bd f959a;

    /* renamed from: a, reason: collision with other field name */
    private com.xiaomi.push.service.d f960a;

    /* renamed from: a, reason: collision with other field name */
    private String f963a;

    /* renamed from: a, reason: collision with other field name */
    private long f949a = 0;

    /* renamed from: a, reason: collision with other field name */
    protected Class f962a = XMJobService.class;

    /* renamed from: a, reason: collision with other field name */
    private as f958a = null;

    /* renamed from: a, reason: collision with other field name */
    private com.xiaomi.push.service.k f961a = null;

    /* renamed from: a, reason: collision with other field name */
    Messenger f952a = null;

    /* renamed from: a, reason: collision with other field name */
    private ArrayList<l> f964a = new ArrayList<>();

    /* renamed from: a, reason: collision with other field name */
    private gh f956a = new bp(this);

    /* renamed from: a, reason: collision with other field name */
    final BroadcastReceiver f950a = new cb(this);

    public class a extends i {

        /* renamed from: a, reason: collision with other field name */
        at.b f965a;

        public a(at.b bVar) {
            super(9);
            this.f965a = bVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "bind the client. " + this.f965a.f25597g;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() {
            String str;
            try {
                if (!XMPushService.this.m702c()) {
                    com.xiaomi.channel.commonutils.logger.b.d("trying bind while the connection is not created, quit!");
                    return;
                }
                at atVarA = at.a();
                at.b bVar = this.f965a;
                at.b bVarA = atVarA.a(bVar.f25597g, bVar.f1015b);
                if (bVarA == null) {
                    str = "ignore bind because the channel " + this.f965a.f25597g + " is removed ";
                } else if (bVarA.f1010a == at.c.unbind) {
                    bVarA.a(at.c.binding, 0, 0, (String) null, (String) null);
                    XMPushService.this.f954a.a(bVarA);
                    hq.a(XMPushService.this, bVarA);
                    return;
                } else {
                    str = "trying duplicate bind, ingore! " + bVarA.f1010a;
                }
                com.xiaomi.channel.commonutils.logger.b.m117a(str);
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
                XMPushService.this.a(10, e2);
            } catch (Throwable unused) {
            }
        }
    }

    public static class b extends i {

        /* renamed from: a, reason: collision with root package name */
        private final at.b f25545a;

        public b(at.b bVar) {
            super(12);
            this.f25545a = bVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "bind time out. chid=" + this.f25545a.f25597g;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() {
            this.f25545a.a(at.c.unbind, 1, 21, (String) null, (String) null);
        }

        public boolean equals(Object obj) {
            if (obj instanceof b) {
                return TextUtils.equals(((b) obj).f25545a.f25597g, this.f25545a.f25597g);
            }
            return false;
        }

        public int hashCode() {
            return this.f25545a.f25597g.hashCode();
        }
    }

    public class c extends i {

        /* renamed from: a, reason: collision with root package name */
        private fv f25546a;

        public c(fv fvVar) {
            super(8);
            this.f25546a = fvVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "receive a message.";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() {
            XMPushService.this.f958a.a(this.f25546a);
        }
    }

    public class d extends i {
        public d() {
            super(1);
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "do reconnect..";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() {
            if (XMPushService.this.m698a()) {
                XMPushService.this.f();
            } else {
                com.xiaomi.channel.commonutils.logger.b.m117a("should not connect. quit the job.");
            }
        }
    }

    public class e extends BroadcastReceiver {
        public e() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            XMPushService.this.onStart(intent, XMPushService.f25542a);
        }
    }

    public class f extends i {

        /* renamed from: a, reason: collision with other field name */
        public Exception f967a;

        /* renamed from: b, reason: collision with root package name */
        public int f25550b;

        public f(int i2, Exception exc) {
            super(2);
            this.f25550b = i2;
            this.f967a = exc;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "disconnect the connection.";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() {
            XMPushService.this.a(this.f25550b, this.f967a);
        }
    }

    public class g extends i {
        public g() {
            super(65535);
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "Init Job";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() {
            XMPushService.this.c();
        }
    }

    public class h extends i {

        /* renamed from: a, reason: collision with root package name */
        private Intent f25552a;

        public h(Intent intent) {
            super(15);
            this.f25552a = intent;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "Handle intent action = " + this.f25552a.getAction();
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() throws PackageManager.NameNotFoundException, NumberFormatException {
            XMPushService.this.c(this.f25552a);
        }
    }

    public static abstract class i extends k.b {
        public i(int i2) {
            super(i2);
        }

        public abstract String a();

        /* renamed from: a */
        public abstract void mo463a();

        @Override // java.lang.Runnable
        public void run() {
            int i2 = this.f25690a;
            if (i2 != 4 && i2 != 8) {
                com.xiaomi.channel.commonutils.logger.b.m117a("JOB: " + a());
            }
            mo463a();
        }
    }

    public class j extends i {
        public j() {
            super(5);
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "ask the job queue to quit";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() {
            XMPushService.this.f961a.m745a();
        }
    }

    public class k extends i {

        /* renamed from: a, reason: collision with root package name */
        private gt f25554a;

        public k(gt gtVar) {
            super(8);
            this.f25554a = gtVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "receive a message.";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() {
            XMPushService.this.f958a.a(this.f25554a);
        }
    }

    public interface l {
        /* renamed from: a */
        void mo495a();
    }

    public class m extends i {

        /* renamed from: a, reason: collision with other field name */
        boolean f970a;

        public m(boolean z2) {
            super(4);
            this.f970a = z2;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "send ping..";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() {
            if (XMPushService.this.m702c()) {
                try {
                    if (!this.f970a) {
                        hq.a();
                    }
                    XMPushService.this.f954a.b(this.f970a);
                } catch (gn e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                    XMPushService.this.a(10, e2);
                }
            }
        }
    }

    public class n extends i {

        /* renamed from: a, reason: collision with other field name */
        at.b f971a;

        public n(at.b bVar) {
            super(4);
            this.f971a = bVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "rebind the client. " + this.f971a.f25597g;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() {
            try {
                this.f971a.a(at.c.unbind, 1, 16, (String) null, (String) null);
                gc gcVar = XMPushService.this.f954a;
                at.b bVar = this.f971a;
                gcVar.a(bVar.f25597g, bVar.f1015b);
                this.f971a.a(at.c.binding, 1, 16, (String) null, (String) null);
                XMPushService.this.f954a.a(this.f971a);
            } catch (gn e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
                XMPushService.this.a(10, e2);
            }
        }
    }

    public class o extends i {
        public o() {
            super(3);
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "reset the connection.";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() {
            XMPushService.this.a(11, (Exception) null);
            if (XMPushService.this.m698a()) {
                XMPushService.this.f();
            }
        }
    }

    public class p extends i {

        /* renamed from: a, reason: collision with other field name */
        at.b f972a;

        /* renamed from: a, reason: collision with other field name */
        String f973a;

        /* renamed from: b, reason: collision with root package name */
        int f25559b;

        /* renamed from: b, reason: collision with other field name */
        String f974b;

        public p(at.b bVar, int i2, String str, String str2) {
            super(9);
            this.f972a = bVar;
            this.f25559b = i2;
            this.f973a = str;
            this.f974b = str2;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        public String a() {
            return "unbind the channel. " + this.f972a.f25597g;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo463a() {
            if (this.f972a.f1010a != at.c.unbind && XMPushService.this.f954a != null) {
                try {
                    gc gcVar = XMPushService.this.f954a;
                    at.b bVar = this.f972a;
                    gcVar.a(bVar.f25597g, bVar.f1015b);
                } catch (gn e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                    XMPushService.this.a(10, e2);
                }
            }
            this.f972a.a(at.c.unbind, this.f25559b, 0, this.f974b, this.f973a);
        }
    }

    static {
        dc.a("app.chat.xiaomi.net", "app.chat.xiaomi.net");
        dc.a("app.chat.xiaomi.net", "42.62.94.2:443");
        dc.a("app.chat.xiaomi.net", "114.54.23.2");
        dc.a("app.chat.xiaomi.net", "111.13.142.2");
        dc.a("app.chat.xiaomi.net", "111.206.200.2");
        f25542a = 1;
    }

    @TargetApi(11)
    public static Notification a(Context context) {
        Intent intent = new Intent(context, (Class<?>) XMPushService.class);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(context.getApplicationInfo().icon);
        builder.setContentTitle("Push Service");
        builder.setContentText("Push Service");
        builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));
        return builder.getNotification();
    }

    private gt a(gt gtVar, String str, String str2) {
        StringBuilder sb;
        String str3;
        at atVarA = at.a();
        List<String> listM718a = atVarA.m718a(str);
        if (listM718a.isEmpty()) {
            sb = new StringBuilder();
            str3 = "open channel should be called first before sending a packet, pkg=";
        } else {
            gtVar.o(str);
            str = gtVar.k();
            if (TextUtils.isEmpty(str)) {
                str = listM718a.get(0);
                gtVar.l(str);
            }
            at.b bVarA = atVarA.a(str, gtVar.m());
            if (!m702c()) {
                sb = new StringBuilder();
                str3 = "drop a packet as the channel is not connected, chid=";
            } else {
                if (bVarA != null && bVarA.f1010a == at.c.binded) {
                    if (TextUtils.equals(str2, bVarA.f25599i)) {
                        return gtVar;
                    }
                    sb = new StringBuilder();
                    sb.append("invalid session. ");
                    sb.append(str2);
                    com.xiaomi.channel.commonutils.logger.b.m117a(sb.toString());
                    return null;
                }
                sb = new StringBuilder();
                str3 = "drop a packet as the channel is not opened, chid=";
            }
        }
        sb.append(str3);
        sb.append(str);
        com.xiaomi.channel.commonutils.logger.b.m117a(sb.toString());
        return null;
    }

    private at.b a(String str, Intent intent) {
        at.b bVarA = at.a().a(str, intent.getStringExtra(ax.f25626p));
        if (bVarA == null) {
            bVarA = new at.b(this);
        }
        bVarA.f25597g = intent.getStringExtra(ax.f25628r);
        bVarA.f1015b = intent.getStringExtra(ax.f25626p);
        bVarA.f25593c = intent.getStringExtra(ax.f25630t);
        bVarA.f1012a = intent.getStringExtra(ax.f25636z);
        bVarA.f25595e = intent.getStringExtra(ax.f25634x);
        bVarA.f25596f = intent.getStringExtra(ax.f25635y);
        bVarA.f1014a = intent.getBooleanExtra(ax.f25633w, false);
        bVarA.f25598h = intent.getStringExtra(ax.f25632v);
        bVarA.f25599i = intent.getStringExtra(ax.C);
        bVarA.f25594d = intent.getStringExtra(ax.f25631u);
        bVarA.f1011a = this.f960a;
        bVarA.a((Messenger) intent.getParcelableExtra(ax.G));
        bVarA.f1004a = getApplicationContext();
        at.a().a(bVarA);
        return bVarA;
    }

    /* renamed from: a, reason: collision with other method in class */
    private String m686a() {
        String strB;
        com.xiaomi.push.ao.a();
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        Object obj = new Object();
        if ("com.xiaomi.xmsf".equals(getPackageName())) {
            ba baVarA = ba.a(this);
            strB = null;
            while (true) {
                if (!TextUtils.isEmpty(strB) && baVarA.a() != 0) {
                    break;
                }
                if (TextUtils.isEmpty(strB)) {
                    strB = com.xiaomi.push.n.m677a("ro.miui.region");
                    if (TextUtils.isEmpty(strB)) {
                        strB = com.xiaomi.push.n.m677a("ro.product.locale.region");
                    }
                }
                try {
                    synchronized (obj) {
                        obj.wait(100L);
                    }
                } catch (InterruptedException unused) {
                }
            }
        } else {
            strB = com.xiaomi.push.n.b();
        }
        if (!TextUtils.isEmpty(strB)) {
            strB = com.xiaomi.push.n.a(strB).name();
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("wait region :" + strB + " cost = " + (SystemClock.elapsedRealtime() - jElapsedRealtime));
        return strB;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(android.content.Intent r9) {
        /*
            r8 = this;
            java.lang.String r0 = com.xiaomi.push.service.ax.f25636z
            java.lang.String r0 = r9.getStringExtra(r0)
            java.lang.String r1 = com.xiaomi.push.service.ax.C
            java.lang.String r1 = r9.getStringExtra(r1)
            java.lang.String r2 = "ext_packet"
            android.os.Bundle r2 = r9.getBundleExtra(r2)
            com.xiaomi.push.service.at r3 = com.xiaomi.push.service.at.a()
            if (r2 == 0) goto L39
            com.xiaomi.push.gs r9 = new com.xiaomi.push.gs
            r9.<init>(r2)
            com.xiaomi.push.gt r9 = r8.a(r9, r0, r1)
            com.xiaomi.push.gs r9 = (com.xiaomi.push.gs) r9
            if (r9 != 0) goto L26
            return
        L26:
            java.lang.String r0 = r9.k()
            java.lang.String r1 = r9.m()
            com.xiaomi.push.service.at$b r0 = r3.a(r0, r1)
            java.lang.String r0 = r0.f25598h
            com.xiaomi.push.fv r9 = com.xiaomi.push.fv.a(r9, r0)
            goto L88
        L39:
            java.lang.String r0 = "ext_raw_packet"
            byte[] r0 = r9.getByteArrayExtra(r0)
            r1 = 0
            if (r0 == 0) goto L87
            java.lang.String r2 = com.xiaomi.push.service.ax.f25626p
            r4 = 0
            long r4 = r9.getLongExtra(r2, r4)
            java.lang.String r2 = com.xiaomi.push.service.ax.f25627q
            java.lang.String r2 = r9.getStringExtra(r2)
            java.lang.String r6 = "ext_chid"
            java.lang.String r6 = r9.getStringExtra(r6)
            java.lang.String r7 = java.lang.Long.toString(r4)
            com.xiaomi.push.service.at$b r3 = r3.a(r6, r7)
            if (r3 == 0) goto L87
            com.xiaomi.push.fv r7 = new com.xiaomi.push.fv
            r7.<init>()
            int r6 = java.lang.Integer.parseInt(r6)     // Catch: java.lang.NumberFormatException -> L6c
            r7.a(r6)     // Catch: java.lang.NumberFormatException -> L6c
        L6c:
            java.lang.String r6 = "SECMSG"
            r7.a(r6, r1)
            java.lang.String r1 = "xiaomi.com"
            r7.a(r4, r1, r2)
            java.lang.String r1 = "ext_pkt_id"
            java.lang.String r9 = r9.getStringExtra(r1)
            r7.a(r9)
            java.lang.String r9 = r3.f25598h
            r7.a(r0, r9)
            r9 = r7
            goto L88
        L87:
            r9 = r1
        L88:
            if (r9 == 0) goto L92
            com.xiaomi.push.service.be r0 = new com.xiaomi.push.service.be
            r0.<init>(r8, r9)
            r8.c(r0)
        L92:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.XMPushService.a(android.content.Intent):void");
    }

    private void a(Intent intent, int i2) {
        byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
        boolean booleanExtra = intent.getBooleanExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
        je jeVar = new je();
        try {
            jp.a(jeVar, byteArrayExtra);
            com.xiaomi.push.ai.a(getApplicationContext()).a((ai.a) new com.xiaomi.push.service.b(jeVar, new WeakReference(this), booleanExtra), i2);
        } catch (jv unused) {
            com.xiaomi.channel.commonutils.logger.b.d("aw_ping : send help app ping  error");
        }
    }

    private void a(String str, int i2) {
        Collection<at.b> collectionM717a = at.a().m717a(str);
        if (collectionM717a != null) {
            for (at.b bVar : collectionM717a) {
                if (bVar != null) {
                    a(new p(bVar, i2, null, null));
                }
            }
        }
        at.a().m720a(str);
    }

    public static boolean a(int i2, String str) {
        if (TextUtils.equals(str, "Enter") && i2 == 1) {
            return true;
        }
        return TextUtils.equals(str, "Leave") && i2 == 2;
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m690a(String str, Intent intent) {
        at.b bVarA = at.a().a(str, intent.getStringExtra(ax.f25626p));
        boolean z2 = false;
        if (bVarA == null || str == null) {
            return false;
        }
        String stringExtra = intent.getStringExtra(ax.C);
        String stringExtra2 = intent.getStringExtra(ax.f25632v);
        if (!TextUtils.isEmpty(bVarA.f25599i) && !TextUtils.equals(stringExtra, bVarA.f25599i)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("session changed. old session=" + bVarA.f25599i + ", new session=" + stringExtra + " chid = " + str);
            z2 = true;
        }
        if (stringExtra2.equals(bVarA.f25598h)) {
            return z2;
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("security changed. chid = " + str + " sechash = " + com.xiaomi.push.ax.a(stringExtra2));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(String str, String str2, Context context) {
        if (TextUtils.equals("Leave", str) && !TextUtils.equals("Enter", com.xiaomi.push.service.g.a(context).m741a(str2))) {
            return false;
        }
        if (com.xiaomi.push.service.g.a(context).a(str2, str) != 0) {
            return true;
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("update geofence statue failed geo_id:" + str2);
        return false;
    }

    private void b(Intent intent) {
        String stringExtra = intent.getStringExtra(ax.f25636z);
        String stringExtra2 = intent.getStringExtra(ax.C);
        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("ext_packets");
        int length = parcelableArrayExtra.length;
        gs[] gsVarArr = new gs[length];
        intent.getBooleanExtra("ext_encrypt", true);
        for (int i2 = 0; i2 < parcelableArrayExtra.length; i2++) {
            gs gsVar = new gs((Bundle) parcelableArrayExtra[i2]);
            gsVarArr[i2] = gsVar;
            gs gsVar2 = (gs) a(gsVar, stringExtra, stringExtra2);
            gsVarArr[i2] = gsVar2;
            if (gsVar2 == null) {
                return;
            }
        }
        at atVarA = at.a();
        fv[] fvVarArr = new fv[length];
        for (int i3 = 0; i3 < length; i3++) {
            gs gsVar3 = gsVarArr[i3];
            fvVarArr[i3] = fv.a(gsVar3, atVarA.a(gsVar3.k(), gsVar3.m()).f25598h);
        }
        c(new com.xiaomi.push.service.c(this, fvVarArr));
    }

    private void b(boolean z2) {
        this.f949a = System.currentTimeMillis();
        if (m702c()) {
            if (this.f954a.m455d() || this.f954a.m456e() || com.xiaomi.push.as.d(this)) {
                c(new m(z2));
                return;
            }
            c(new f(17, null));
        }
        a(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        String str;
        com.xiaomi.push.service.a aVarA = com.xiaomi.push.service.a.a(getApplicationContext());
        String strA = aVarA.a();
        if (TextUtils.isEmpty(strA)) {
            strA = m686a();
        }
        if (TextUtils.isEmpty(strA)) {
            this.f963a = com.xiaomi.push.q.China.name();
        } else {
            this.f963a = strA;
            aVarA.a(strA);
            if (com.xiaomi.push.q.Global.name().equals(this.f963a)) {
                str = "app.chat.global.xiaomi.net";
            } else if (com.xiaomi.push.q.Europe.name().equals(this.f963a)) {
                str = "fr.app.chat.global.xiaomi.net";
            } else if (com.xiaomi.push.q.Russia.name().equals(this.f963a)) {
                str = "ru.app.chat.global.xiaomi.net";
            } else if (com.xiaomi.push.q.India.name().equals(this.f963a)) {
                str = "idmb.app.chat.global.xiaomi.net";
            }
            gd.a(str);
        }
        if (m693g()) {
            bz bzVar = new bz(this, 11);
            a(bzVar);
            t.a(new ca(this, bzVar));
        }
        com.xiaomi.push.ai.a(this).a((ai.a) new com.xiaomi.push.service.f(this), 86400);
        try {
            if (com.xiaomi.push.v.m771a()) {
                this.f960a.a(this);
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Intent intent) throws PackageManager.NameNotFoundException, NumberFormatException {
        String str;
        com.xiaomi.push.service.d dVar;
        boolean z2;
        int i2;
        String str2;
        i nVar;
        String strB;
        String str3;
        i cdVar;
        at atVarA = at.a();
        boolean z3 = true;
        int i3 = 0;
        if (ax.f25614d.equalsIgnoreCase(intent.getAction()) || ax.f25620j.equalsIgnoreCase(intent.getAction())) {
            String stringExtra = intent.getStringExtra(ax.f25628r);
            if (!TextUtils.isEmpty(intent.getStringExtra(ax.f25632v))) {
                if (stringExtra == null) {
                    str = "channel id is empty, do nothing!";
                    com.xiaomi.channel.commonutils.logger.b.d(str);
                    return;
                }
                boolean zM690a = m690a(stringExtra, intent);
                at.b bVarA = a(stringExtra, intent);
                if (com.xiaomi.push.as.b(this)) {
                    if (!m702c()) {
                        a(true);
                        return;
                    }
                    at.c cVar = bVarA.f1010a;
                    if (cVar == at.c.unbind) {
                        nVar = new a(bVarA);
                    } else if (zM690a) {
                        nVar = new n(bVarA);
                    } else if (cVar == at.c.binding) {
                        str2 = String.format("the client is binding. %1$s %2$s.", bVarA.f25597g, at.b.a(bVarA.f1015b));
                    } else {
                        if (cVar != at.c.binded) {
                            return;
                        }
                        dVar = this.f960a;
                        z2 = true;
                        i2 = 0;
                    }
                    c(nVar);
                }
                dVar = this.f960a;
                z2 = false;
                i2 = 2;
                dVar.a(this, bVarA, z2, i2, null);
                return;
            }
            str2 = "security is empty. ignore.";
            com.xiaomi.channel.commonutils.logger.b.m117a(str2);
            return;
        }
        if (ax.f25619i.equalsIgnoreCase(intent.getAction())) {
            String stringExtra2 = intent.getStringExtra(ax.f25636z);
            String stringExtra3 = intent.getStringExtra(ax.f25628r);
            String stringExtra4 = intent.getStringExtra(ax.f25626p);
            com.xiaomi.channel.commonutils.logger.b.m117a("Service called close channel chid = " + stringExtra3 + " res = " + at.b.a(stringExtra4));
            if (TextUtils.isEmpty(stringExtra3)) {
                Iterator<String> it = atVarA.m718a(stringExtra2).iterator();
                while (it.hasNext()) {
                    a(it.next(), 2);
                }
                return;
            } else if (TextUtils.isEmpty(stringExtra4)) {
                a(stringExtra3, 2);
                return;
            } else {
                a(stringExtra3, stringExtra4, 2, null, null);
                return;
            }
        }
        if (ax.f25615e.equalsIgnoreCase(intent.getAction())) {
            a(intent);
            return;
        }
        if (ax.f25617g.equalsIgnoreCase(intent.getAction())) {
            b(intent);
            return;
        }
        if (ax.f25616f.equalsIgnoreCase(intent.getAction())) {
            gt gtVarA = a(new gr(intent.getBundleExtra("ext_packet")), intent.getStringExtra(ax.f25636z), intent.getStringExtra(ax.C));
            if (gtVarA == null) {
                return;
            } else {
                nVar = new be(this, fv.a(gtVarA, atVarA.a(gtVarA.k(), gtVarA.m()).f25598h));
            }
        } else {
            if (!ax.f25618h.equalsIgnoreCase(intent.getAction())) {
                if (!ax.f25621k.equals(intent.getAction())) {
                    at.b bVarA2 = null;
                    if (ax.f25622l.equals(intent.getAction())) {
                        String stringExtra5 = intent.getStringExtra(ax.f25636z);
                        List<String> listM718a = atVarA.m718a(stringExtra5);
                        if (!listM718a.isEmpty()) {
                            String stringExtra6 = intent.getStringExtra(ax.f25628r);
                            String stringExtra7 = intent.getStringExtra(ax.f25626p);
                            if (TextUtils.isEmpty(stringExtra6)) {
                                stringExtra6 = listM718a.get(0);
                            }
                            if (TextUtils.isEmpty(stringExtra7)) {
                                Collection<at.b> collectionM717a = atVarA.m717a(stringExtra6);
                                if (collectionM717a != null && !collectionM717a.isEmpty()) {
                                    bVarA2 = collectionM717a.iterator().next();
                                }
                            } else {
                                bVarA2 = atVarA.a(stringExtra6, stringExtra7);
                            }
                            if (bVarA2 != null) {
                                if (intent.hasExtra(ax.f25634x)) {
                                    bVarA2.f25595e = intent.getStringExtra(ax.f25634x);
                                }
                                if (intent.hasExtra(ax.f25635y)) {
                                    bVarA2.f25596f = intent.getStringExtra(ax.f25635y);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        str3 = "open channel should be called first before update info, pkg=" + stringExtra5;
                    } else if ("com.xiaomi.mipush.REGISTER_APP".equals(intent.getAction())) {
                        if (ba.a(getApplicationContext()).m725a() && ba.a(getApplicationContext()).a() == 0) {
                            str3 = "register without being provisioned. " + intent.getStringExtra("mipush_app_package");
                        } else {
                            byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
                            String stringExtra8 = intent.getStringExtra("mipush_app_package");
                            boolean booleanExtra = intent.getBooleanExtra("mipush_env_chanage", false);
                            int intExtra = intent.getIntExtra("mipush_env_type", 1);
                            u.a(this).d(stringExtra8);
                            if (!booleanExtra || "com.xiaomi.xmsf".equals(getPackageName())) {
                                a(byteArrayExtra, stringExtra8);
                                return;
                            }
                            cdVar = new cd(this, 14, intExtra, byteArrayExtra, stringExtra8);
                        }
                    } else {
                        if ("com.xiaomi.mipush.SEND_MESSAGE".equals(intent.getAction()) || "com.xiaomi.mipush.UNREGISTER_APP".equals(intent.getAction())) {
                            String stringExtra9 = intent.getStringExtra("mipush_app_package");
                            byte[] byteArrayExtra2 = intent.getByteArrayExtra("mipush_payload");
                            boolean booleanExtra2 = intent.getBooleanExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
                            if ("com.xiaomi.mipush.UNREGISTER_APP".equals(intent.getAction())) {
                                u.a(this).a(stringExtra9);
                            }
                            a(stringExtra9, byteArrayExtra2, booleanExtra2);
                            return;
                        }
                        if (!bb.f25640a.equals(intent.getAction())) {
                            if ("com.xiaomi.mipush.CLEAR_NOTIFICATION".equals(intent.getAction())) {
                                String stringExtra10 = intent.getStringExtra(ax.f25636z);
                                int intExtra2 = intent.getIntExtra(ax.A, -2);
                                if (TextUtils.isEmpty(stringExtra10)) {
                                    return;
                                }
                                if (intExtra2 >= -1) {
                                    ai.a(this, stringExtra10, intExtra2);
                                    return;
                                } else {
                                    ai.a(this, stringExtra10, intent.getStringExtra(ax.E), intent.getStringExtra(ax.F));
                                    return;
                                }
                            }
                            if ("com.xiaomi.mipush.SET_NOTIFICATION_TYPE".equals(intent.getAction())) {
                                String stringExtra11 = intent.getStringExtra(ax.f25636z);
                                String stringExtra12 = intent.getStringExtra(ax.D);
                                if (intent.hasExtra(ax.B)) {
                                    int intExtra3 = intent.getIntExtra(ax.B, 0);
                                    strB = com.xiaomi.push.ax.b(stringExtra11 + intExtra3);
                                    z3 = false;
                                    i3 = intExtra3;
                                } else {
                                    strB = com.xiaomi.push.ax.b(stringExtra11);
                                }
                                if (!TextUtils.isEmpty(stringExtra11) && TextUtils.equals(stringExtra12, strB)) {
                                    if (z3) {
                                        ai.m711b((Context) this, stringExtra11);
                                        return;
                                    } else {
                                        ai.b(this, stringExtra11, i3);
                                        return;
                                    }
                                }
                                str = "invalid notification for " + stringExtra11;
                                com.xiaomi.channel.commonutils.logger.b.d(str);
                                return;
                            }
                            if ("com.xiaomi.mipush.DISABLE_PUSH".equals(intent.getAction())) {
                                String stringExtra13 = intent.getStringExtra("mipush_app_package");
                                if (!TextUtils.isEmpty(stringExtra13)) {
                                    u.a(this).b(stringExtra13);
                                }
                                if ("com.xiaomi.xmsf".equals(getPackageName())) {
                                    return;
                                }
                                e eVar = this.f957a;
                                if (eVar != null) {
                                    unregisterReceiver(eVar);
                                    this.f957a = null;
                                }
                                this.f961a.m748b();
                                a(new bq(this, 2));
                                at.a().b();
                                at.a().a(this, 0);
                                at.a().m719a();
                                bi.a().m736a();
                                fm.a();
                                return;
                            }
                            if ("com.xiaomi.mipush.DISABLE_PUSH_MESSAGE".equals(intent.getAction()) || "com.xiaomi.mipush.ENABLE_PUSH_MESSAGE".equals(intent.getAction())) {
                                String stringExtra14 = intent.getStringExtra("mipush_app_package");
                                byte[] byteArrayExtra3 = intent.getByteArrayExtra("mipush_payload");
                                String stringExtra15 = intent.getStringExtra("mipush_app_id");
                                String stringExtra16 = intent.getStringExtra("mipush_app_token");
                                if ("com.xiaomi.mipush.DISABLE_PUSH_MESSAGE".equals(intent.getAction())) {
                                    u.a(this).c(stringExtra14);
                                }
                                if ("com.xiaomi.mipush.ENABLE_PUSH_MESSAGE".equals(intent.getAction())) {
                                    u.a(this).e(stringExtra14);
                                    u.a(this).f(stringExtra14);
                                }
                                if (byteArrayExtra3 == null) {
                                    w.a(this, stringExtra14, byteArrayExtra3, ErrorCode.ERROR_INVALID_PAYLOAD, "null payload");
                                    return;
                                }
                                w.b(stringExtra14, byteArrayExtra3);
                                a(new v(this, stringExtra14, stringExtra15, stringExtra16, byteArrayExtra3));
                                if ("com.xiaomi.mipush.ENABLE_PUSH_MESSAGE".equals(intent.getAction()) && this.f957a == null) {
                                    this.f957a = new e();
                                    registerReceiver(this.f957a, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                                    return;
                                }
                                return;
                            }
                            if ("com.xiaomi.mipush.SEND_TINYDATA".equals(intent.getAction())) {
                                String stringExtra17 = intent.getStringExtra("mipush_app_package");
                                byte[] byteArrayExtra4 = intent.getByteArrayExtra("mipush_payload");
                                ib ibVar = new ib();
                                try {
                                    jp.a(ibVar, byteArrayExtra4);
                                    hu.a(this).a(ibVar, stringExtra17);
                                    return;
                                } catch (jv e2) {
                                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                                    return;
                                }
                            }
                            if ("com.xiaomi.push.timer".equalsIgnoreCase(intent.getAction())) {
                                com.xiaomi.channel.commonutils.logger.b.m117a("Service called on timer");
                                fm.a(false);
                                if (!m691e()) {
                                    return;
                                }
                            } else {
                                if (!"com.xiaomi.push.check_alive".equalsIgnoreCase(intent.getAction())) {
                                    if ("com.xiaomi.mipush.thirdparty".equals(intent.getAction())) {
                                        com.xiaomi.channel.commonutils.logger.b.m117a("on thirdpart push :" + intent.getStringExtra("com.xiaomi.mipush.thirdparty_DESC"));
                                        fm.a(this, intent.getIntExtra("com.xiaomi.mipush.thirdparty_LEVEL", 0));
                                        return;
                                    }
                                    if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                                        d();
                                        return;
                                    }
                                    if ("action_cr_config".equals(intent.getAction())) {
                                        boolean booleanExtra3 = intent.getBooleanExtra("action_cr_event_switch", false);
                                        long longExtra = intent.getLongExtra("action_cr_event_frequency", 86400L);
                                        boolean booleanExtra4 = intent.getBooleanExtra("action_cr_perf_switch", false);
                                        long longExtra2 = intent.getLongExtra("action_cr_perf_frequency", 86400L);
                                        boolean booleanExtra5 = intent.getBooleanExtra("action_cr_event_en", true);
                                        long longExtra3 = intent.getLongExtra("action_cr_max_file_size", 1048576L);
                                        Config configBuild = Config.getBuilder().setEventUploadSwitchOpen(booleanExtra3).setEventUploadFrequency(longExtra).setPerfUploadSwitchOpen(booleanExtra4).setPerfUploadFrequency(longExtra2).setAESKey(com.xiaomi.push.be.m227a(getApplicationContext())).setEventEncrypted(booleanExtra5).setMaxFileLength(longExtra3).build(getApplicationContext());
                                        if ("com.xiaomi.xmsf".equals(getPackageName()) || longExtra <= 0 || longExtra2 <= 0 || longExtra3 <= 0) {
                                            return;
                                        }
                                        fk.a(getApplicationContext(), configBuild);
                                        return;
                                    }
                                    if (!"action_help_ping".equals(intent.getAction())) {
                                        if ("action_aw_app_logic".equals(intent.getAction())) {
                                            d(intent);
                                            return;
                                        }
                                        return;
                                    }
                                    boolean booleanExtra6 = intent.getBooleanExtra("extra_help_ping_switch", false);
                                    int intExtra4 = intent.getIntExtra("extra_help_ping_frequency", 0);
                                    if (intExtra4 >= 0 && intExtra4 < 30) {
                                        com.xiaomi.channel.commonutils.logger.b.c("aw_ping: frquency need > 30s.");
                                        intExtra4 = 30;
                                    }
                                    boolean z4 = intExtra4 >= 0 ? booleanExtra6 : false;
                                    com.xiaomi.channel.commonutils.logger.b.m117a("aw_ping: receive a aw_ping message. switch: " + z4 + " frequency: " + intExtra4);
                                    if (!z4 || intExtra4 <= 0 || "com.xiaomi.xmsf".equals(getPackageName())) {
                                        return;
                                    }
                                    a(intent, intExtra4);
                                    return;
                                }
                                com.xiaomi.channel.commonutils.logger.b.m117a("Service called on check alive.");
                                if (!m691e()) {
                                    return;
                                }
                            }
                            b(false);
                            return;
                        }
                        String stringExtra18 = intent.getStringExtra("uninstall_pkg_name");
                        if (stringExtra18 == null || TextUtils.isEmpty(stringExtra18.trim())) {
                            return;
                        }
                        try {
                            getPackageManager().getPackageInfo(stringExtra18, 0);
                            z3 = false;
                        } catch (PackageManager.NameNotFoundException unused) {
                        }
                        if (!"com.xiaomi.channel".equals(stringExtra18) || at.a().m717a("1").isEmpty() || !z3) {
                            SharedPreferences sharedPreferences = getSharedPreferences("pref_registered_pkg_names", 0);
                            String string = sharedPreferences.getString(stringExtra18, null);
                            if (TextUtils.isEmpty(string) || !z3) {
                                return;
                            }
                            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                            editorEdit.remove(stringExtra18);
                            editorEdit.commit();
                            if (ai.m712b((Context) this, stringExtra18)) {
                                ai.m711b((Context) this, stringExtra18);
                            }
                            ai.m707a((Context) this, stringExtra18);
                            if (!m702c() || string == null) {
                                return;
                            }
                            try {
                                af.a(this, af.a(stringExtra18, string));
                                com.xiaomi.channel.commonutils.logger.b.m117a("uninstall " + stringExtra18 + " msg sent");
                                return;
                            } catch (gn e3) {
                                com.xiaomi.channel.commonutils.logger.b.d("Fail to send Message: " + e3.getMessage());
                                a(10, e3);
                                return;
                            }
                        }
                        a("1", 0);
                        str3 = "close the miliao channel as the app is uninstalled.";
                    }
                    com.xiaomi.channel.commonutils.logger.b.m117a(str3);
                    return;
                }
                String stringExtra19 = intent.getStringExtra(ax.f25628r);
                String stringExtra20 = intent.getStringExtra(ax.f25626p);
                if (stringExtra19 == null) {
                    return;
                }
                com.xiaomi.channel.commonutils.logger.b.m117a("request reset connection from chid = " + stringExtra19);
                at.b bVarA3 = at.a().a(stringExtra19, stringExtra20);
                if (bVarA3 == null || !bVarA3.f25598h.equals(intent.getStringExtra(ax.f25632v)) || bVarA3.f1010a != at.c.binded) {
                    return;
                }
                gc gcVarM695a = m695a();
                if (gcVarM695a != null && gcVarM695a.a(System.currentTimeMillis() - C.DEFAULT_SEEK_FORWARD_INCREMENT_MS)) {
                    return;
                } else {
                    cdVar = new o();
                }
                c(cdVar);
                return;
            }
            gt gtVarA2 = a(new gv(intent.getBundleExtra("ext_packet")), intent.getStringExtra(ax.f25636z), intent.getStringExtra(ax.C));
            if (gtVarA2 == null) {
                return;
            } else {
                nVar = new be(this, fv.a(gtVarA2, atVarA.a(gtVarA2.k(), gtVarA2.m()).f25598h));
            }
        }
        c(nVar);
    }

    private void c(i iVar) {
        this.f961a.a(iVar);
    }

    private void c(boolean z2) {
        try {
            if (com.xiaomi.push.v.m771a()) {
                sendBroadcast(z2 ? new Intent("miui.intent.action.NETWORK_CONNECTED") : new Intent("miui.intent.action.NETWORK_BLOCKED"));
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    private void d() {
        NetworkInfo activeNetworkInfo;
        try {
            activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("network changed,");
            sb.append(StrPool.BRACKET_START + "type: " + activeNetworkInfo.getTypeName() + StrPool.BRACKET_START + activeNetworkInfo.getSubtypeName() + "], state: " + activeNetworkInfo.getState() + "/" + activeNetworkInfo.getDetailedState());
            com.xiaomi.channel.commonutils.logger.b.m117a(sb.toString());
            NetworkInfo.State state = activeNetworkInfo.getState();
            if (state == NetworkInfo.State.SUSPENDED || state == NetworkInfo.State.UNKNOWN) {
                return;
            }
        } else {
            com.xiaomi.channel.commonutils.logger.b.m117a("network changed, no active network");
        }
        if (ho.a() != null) {
            ho.a().m487a();
        }
        hh.m484a((Context) this);
        this.f953a.e();
        if (com.xiaomi.push.as.b(this)) {
            if (m702c() && m691e()) {
                b(false);
            }
            if (!m702c() && !m703d()) {
                this.f961a.a(1);
                a(new d());
            }
            dl.a(this).a();
        } else {
            a(new f(2, null));
        }
        e();
    }

    private void d(Intent intent) throws NumberFormatException {
        int i2;
        try {
            fa.a(getApplicationContext()).a(new az());
            String stringExtra = intent.getStringExtra("mipush_app_package");
            byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
            if (byteArrayExtra == null) {
                return;
            }
            je jeVar = new je();
            jp.a(jeVar, byteArrayExtra);
            String strB = jeVar.b();
            Map<String, String> mapM610a = jeVar.m610a();
            if (mapM610a != null) {
                String str = mapM610a.get("extra_help_aw_info");
                String str2 = mapM610a.get("extra_aw_app_online_cmd");
                if (TextUtils.isEmpty(str2)) {
                    return;
                }
                try {
                    i2 = Integer.parseInt(str2);
                } catch (NumberFormatException unused) {
                    i2 = 0;
                }
                int i3 = i2;
                if (TextUtils.isEmpty(stringExtra) || TextUtils.isEmpty(strB) || TextUtils.isEmpty(str)) {
                    return;
                }
                fa.a(getApplicationContext()).a(this, str, i3, stringExtra, strB);
            }
        } catch (jv e2) {
            com.xiaomi.channel.commonutils.logger.b.d("aw_logic: translate fail. " + e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        if (!m698a()) {
            fm.a();
        } else {
            if (fm.m421a()) {
                return;
            }
            fm.a(true);
        }
    }

    /* renamed from: e, reason: collision with other method in class */
    private boolean m691e() {
        if (System.currentTimeMillis() - this.f949a < 30000) {
            return false;
        }
        return com.xiaomi.push.as.c(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        String str;
        gc gcVar = this.f954a;
        if (gcVar == null || !gcVar.m453b()) {
            gc gcVar2 = this.f954a;
            if (gcVar2 == null || !gcVar2.m454c()) {
                this.f955a.b(com.xiaomi.push.as.m201a((Context) this));
                g();
                if (this.f954a == null) {
                    at.a().a(this);
                    c(false);
                    return;
                }
                return;
            }
            str = "try to connect while is connected.";
        } else {
            str = "try to connect while connecting.";
        }
        com.xiaomi.channel.commonutils.logger.b.d(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f, reason: collision with other method in class */
    public boolean m692f() {
        return "com.xiaomi.xmsf".equals(getPackageName()) && Settings.Secure.getInt(getContentResolver(), "EXTREME_POWER_MODE_ENABLE", 0) == 1;
    }

    private void g() {
        try {
            this.f953a.a(this.f956a, new bt(this));
            this.f953a.f();
            this.f954a = this.f953a;
        } catch (gn e2) {
            com.xiaomi.channel.commonutils.logger.b.a("fail to create Slim connection", e2);
            this.f953a.b(3, e2);
        }
    }

    /* renamed from: g, reason: collision with other method in class */
    private boolean m693g() {
        return "com.xiaomi.xmsf".equals(getPackageName()) || !u.a(this).m767b(getPackageName());
    }

    private void h() {
        bindService(new Intent(this, (Class<?>) this.f962a), new bu(this), 1);
    }

    /* renamed from: h, reason: collision with other method in class */
    private boolean m694h() {
        if (TextUtils.equals(getPackageName(), "com.xiaomi.xmsf")) {
            return false;
        }
        return ao.a(this).a(ic.ForegroundServiceSwitch.a(), false);
    }

    private void i() {
        synchronized (this.f964a) {
            this.f964a.clear();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public gc m695a() {
        return this.f954a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public com.xiaomi.push.service.d m696a() {
        return new com.xiaomi.push.service.d();
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m697a() {
        if (System.currentTimeMillis() - this.f949a >= gi.a() && com.xiaomi.push.as.c(this)) {
            b(true);
        }
    }

    public void a(int i2) {
        this.f961a.a(i2);
    }

    public void a(int i2, Exception exc) {
        StringBuilder sb = new StringBuilder();
        sb.append("disconnect ");
        sb.append(hashCode());
        sb.append(", ");
        gc gcVar = this.f954a;
        sb.append(gcVar == null ? null : Integer.valueOf(gcVar.hashCode()));
        com.xiaomi.channel.commonutils.logger.b.m117a(sb.toString());
        gc gcVar2 = this.f954a;
        if (gcVar2 != null) {
            gcVar2.b(i2, exc);
            this.f954a = null;
        }
        a(7);
        a(4);
        at.a().a(this, i2);
    }

    public void a(fv fvVar) throws gn {
        gc gcVar = this.f954a;
        if (gcVar == null) {
            throw new gn("try send msg while connection is null.");
        }
        gcVar.b(fvVar);
    }

    @Override // com.xiaomi.push.gf
    public void a(gc gcVar) {
        ho.a().a(gcVar);
        c(true);
        this.f959a.m727a();
        Iterator<at.b> it = at.a().m716a().iterator();
        while (it.hasNext()) {
            a(new a(it.next()));
        }
    }

    @Override // com.xiaomi.push.gf
    public void a(gc gcVar, int i2, Exception exc) {
        ho.a().a(gcVar, i2, exc);
        a(false);
    }

    @Override // com.xiaomi.push.gf
    public void a(gc gcVar, Exception exc) {
        ho.a().a(gcVar, exc);
        c(false);
        a(false);
    }

    public void a(i iVar) {
        a(iVar, 0L);
    }

    public void a(i iVar, long j2) {
        try {
            this.f961a.a(iVar, j2);
        } catch (IllegalStateException unused) {
        }
    }

    public void a(l lVar) {
        synchronized (this.f964a) {
            this.f964a.add(lVar);
        }
    }

    public void a(at.b bVar) {
        if (bVar != null) {
            long jA = bVar.a();
            com.xiaomi.channel.commonutils.logger.b.m117a("schedule rebind job in " + (jA / 1000));
            a(new a(bVar), jA);
        }
    }

    public void a(String str, String str2, int i2, String str3, String str4) {
        at.b bVarA = at.a().a(str, str2);
        if (bVarA != null) {
            a(new p(bVarA, i2, str4, str3));
        }
        at.a().m721a(str, str2);
    }

    public void a(String str, byte[] bArr, boolean z2) {
        Collection<at.b> collectionM717a = at.a().m717a("5");
        if (collectionM717a.isEmpty()) {
            if (!z2) {
                return;
            }
        } else if (collectionM717a.iterator().next().f1010a == at.c.binded) {
            a(new br(this, 4, str, bArr));
            return;
        } else if (!z2) {
            return;
        }
        w.b(str, bArr);
    }

    public void a(boolean z2) {
        this.f959a.a(z2);
    }

    public void a(byte[] bArr, String str) {
        if (bArr == null) {
            w.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, "null payload");
            com.xiaomi.channel.commonutils.logger.b.m117a("register request without payload");
            return;
        }
        jb jbVar = new jb();
        try {
            jp.a(jbVar, bArr);
            if (jbVar.f750a == hw.Registration) {
                jf jfVar = new jf();
                try {
                    jp.a(jfVar, jbVar.m601a());
                    w.a(jbVar.b(), bArr);
                    a(new v(this, jbVar.b(), jfVar.b(), jfVar.c(), bArr));
                } catch (jv e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                    w.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " data action error.");
                }
            } else {
                w.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " registration action required.");
                com.xiaomi.channel.commonutils.logger.b.m117a("register request with invalid payload");
            }
        } catch (jv e3) {
            com.xiaomi.channel.commonutils.logger.b.a(e3);
            w.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " data container error.");
        }
    }

    public void a(fv[] fvVarArr) throws gn {
        gc gcVar = this.f954a;
        if (gcVar == null) {
            throw new gn("try send msg while connection is null.");
        }
        gcVar.a(fvVarArr);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m698a() {
        return com.xiaomi.push.as.b(this) && at.a().m715a() > 0 && !m701b() && m693g() && !m692f();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m699a(int i2) {
        return this.f961a.m747a(i2);
    }

    public com.xiaomi.push.service.d b() {
        return this.f960a;
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m700b() {
        Iterator it = new ArrayList(this.f964a).iterator();
        while (it.hasNext()) {
            ((l) it.next()).mo495a();
        }
    }

    @Override // com.xiaomi.push.gf
    public void b(gc gcVar) {
        com.xiaomi.channel.commonutils.logger.b.c("begin to connect...");
        ho.a().b(gcVar);
    }

    public void b(i iVar) {
        this.f961a.a(iVar.f25690a, iVar);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m701b() {
        try {
            Class<?> cls = Class.forName("miui.os.Build");
            Field field = cls.getField("IS_CM_CUSTOMIZATION_TEST");
            Field field2 = cls.getField("IS_CU_CUSTOMIZATION_TEST");
            Field field3 = cls.getField("IS_CT_CUSTOMIZATION_TEST");
            if (!field.getBoolean(null) && !field2.getBoolean(null)) {
                if (!field3.getBoolean(null)) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m702c() {
        gc gcVar = this.f954a;
        return gcVar != null && gcVar.m454c();
    }

    /* renamed from: d, reason: collision with other method in class */
    public boolean m703d() {
        gc gcVar = this.f954a;
        return gcVar != null && gcVar.m453b();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.f952a.getBinder();
    }

    @Override // android.app.Service
    public void onCreate() {
        Uri uriFor;
        super.onCreate();
        com.xiaomi.push.v.a((Context) this);
        s sVarA = t.a((Context) this);
        if (sVarA != null) {
            com.xiaomi.push.ab.a(sVarA.f25712a);
        }
        this.f952a = new Messenger(new bv(this));
        ay.a(this);
        bw bwVar = new bw(this, null, R2.color.m3_sys_color_dark_inverse_primary, "xiaomi.com", null);
        this.f955a = bwVar;
        bwVar.a(true);
        this.f953a = new ga(this, this.f955a);
        this.f960a = m696a();
        fm.a(this);
        this.f953a.a(this);
        this.f958a = new as(this);
        this.f959a = new bd(this);
        new com.xiaomi.push.service.e().a();
        ho.m488a().a(this);
        this.f961a = new com.xiaomi.push.service.k("Connection Controller Thread");
        at atVarA = at.a();
        atVarA.b();
        atVarA.a(new bx(this));
        if (m694h()) {
            h();
        }
        hu.a(this).a(new com.xiaomi.push.service.m(this), "UPLOADER_PUSH_CHANNEL");
        a(new hr(this));
        a(new g());
        if (m693g()) {
            this.f957a = new e();
            registerReceiver(this.f957a, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
        if ("com.xiaomi.xmsf".equals(getPackageName()) && (uriFor = Settings.Secure.getUriFor("EXTREME_POWER_MODE_ENABLE")) != null) {
            this.f951a = new by(this, new Handler(Looper.getMainLooper()));
            try {
                getContentResolver().registerContentObserver(uriFor, false, this.f951a);
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.m117a("register observer err:" + th.getMessage());
            }
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("XMPushService created pid = " + f25543b);
    }

    @Override // android.app.Service
    public void onDestroy() {
        e eVar = this.f957a;
        if (eVar != null) {
            unregisterReceiver(eVar);
        }
        if ("com.xiaomi.xmsf".equals(getPackageName()) && this.f951a != null) {
            try {
                getContentResolver().unregisterContentObserver(this.f951a);
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.m117a("unregister observer err:" + th.getMessage());
            }
        }
        this.f961a.m748b();
        a(new bs(this, 2));
        a(new j());
        at.a().b();
        at.a().a(this, 15);
        at.a().m719a();
        this.f953a.b(this);
        bi.a().m736a();
        fm.a();
        i();
        super.onDestroy();
        com.xiaomi.channel.commonutils.logger.b.m117a("Service destroyed");
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int i2) {
        h hVar;
        if (intent == null) {
            com.xiaomi.channel.commonutils.logger.b.d("onStart() with intent NULL");
        } else {
            com.xiaomi.channel.commonutils.logger.b.c(String.format("onStart() with intent.Action = %s, chid = %s", intent.getAction(), intent.getStringExtra(ax.f25628r)));
        }
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if ("com.xiaomi.push.timer".equalsIgnoreCase(intent.getAction()) || "com.xiaomi.push.check_alive".equalsIgnoreCase(intent.getAction())) {
            if (this.f961a.m746a()) {
                com.xiaomi.channel.commonutils.logger.b.d("ERROR, the job controller is blocked.");
                at.a().a(this, 14);
                stopSelf();
                return;
            }
            hVar = new h(intent);
        } else if ("com.xiaomi.push.network_status_changed".equalsIgnoreCase(intent.getAction())) {
            return;
        } else {
            hVar = new h(intent);
        }
        a(hVar);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        onStart(intent, i3);
        return f25542a;
    }
}
