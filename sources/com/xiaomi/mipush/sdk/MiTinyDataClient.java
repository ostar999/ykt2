package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.xiaomi.push.hw;
import com.xiaomi.push.ib;
import com.xiaomi.push.iq;
import com.xiaomi.push.je;
import com.xiaomi.push.service.bl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class MiTinyDataClient {
    public static final String PENDING_REASON_APPID = "com.xiaomi.xmpushsdk.tinydataPending.appId";
    public static final String PENDING_REASON_CHANNEL = "com.xiaomi.xmpushsdk.tinydataPending.channel";
    public static final String PENDING_REASON_INIT = "com.xiaomi.xmpushsdk.tinydataPending.init";

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static a f24509a;

        /* renamed from: a, reason: collision with other field name */
        private Context f109a;

        /* renamed from: a, reason: collision with other field name */
        private Boolean f111a;

        /* renamed from: a, reason: collision with other field name */
        private String f112a;

        /* renamed from: a, reason: collision with other field name */
        private C0405a f110a = new C0405a();

        /* renamed from: a, reason: collision with other field name */
        private final ArrayList<ib> f113a = new ArrayList<>();

        /* renamed from: com.xiaomi.mipush.sdk.MiTinyDataClient$a$a, reason: collision with other inner class name */
        public class C0405a {

            /* renamed from: a, reason: collision with other field name */
            private ScheduledFuture<?> f116a;

            /* renamed from: a, reason: collision with other field name */
            private ScheduledThreadPoolExecutor f117a = new ScheduledThreadPoolExecutor(1);

            /* renamed from: a, reason: collision with other field name */
            public final ArrayList<ib> f115a = new ArrayList<>();

            /* renamed from: a, reason: collision with other field name */
            private final Runnable f114a = new an(this);

            public C0405a() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void a() {
                if (this.f116a == null) {
                    this.f116a = this.f117a.scheduleAtFixedRate(this.f114a, 1000L, 1000L, TimeUnit.MILLISECONDS);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void b() {
                ib ibVarRemove = this.f115a.remove(0);
                for (je jeVar : bl.a(Arrays.asList(ibVarRemove), a.this.f109a.getPackageName(), d.m156a(a.this.f109a).m157a(), 30720)) {
                    com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient Send item by PushServiceClient.sendMessage(XmActionNotification)." + ibVarRemove.d());
                    az.a(a.this.f109a).a((az) jeVar, hw.Notification, true, (iq) null);
                }
            }

            public void a(ib ibVar) {
                this.f117a.execute(new am(this, ibVar));
            }
        }

        public static a a() {
            if (f24509a == null) {
                synchronized (a.class) {
                    if (f24509a == null) {
                        f24509a = new a();
                    }
                }
            }
            return f24509a;
        }

        private void a(ib ibVar) {
            synchronized (this.f113a) {
                if (!this.f113a.contains(ibVar)) {
                    this.f113a.add(ibVar);
                    if (this.f113a.size() > 100) {
                        this.f113a.remove(0);
                    }
                }
            }
        }

        private boolean a(Context context) throws PackageManager.NameNotFoundException {
            if (!az.a(context).m147a()) {
                return true;
            }
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4);
                if (packageInfo == null) {
                    return false;
                }
                return packageInfo.versionCode >= 108;
            } catch (Exception unused) {
                return false;
            }
        }

        private boolean b(Context context) {
            return d.m156a(context).m157a() == null && !a(this.f109a);
        }

        private boolean b(ib ibVar) {
            if (bl.a(ibVar, false)) {
                return false;
            }
            if (!this.f111a.booleanValue()) {
                this.f110a.a(ibVar);
                return true;
            }
            com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient Send item by PushServiceClient.sendTinyData(ClientUploadDataItem)." + ibVar.d());
            az.a(this.f109a).a(ibVar);
            return true;
        }

        /* renamed from: a, reason: collision with other method in class */
        public void m128a(Context context) {
            if (context == null) {
                com.xiaomi.channel.commonutils.logger.b.m117a("context is null, MiTinyDataClientImp.init() failed.");
                return;
            }
            this.f109a = context;
            this.f111a = Boolean.valueOf(a(context));
            b(MiTinyDataClient.PENDING_REASON_INIT);
        }

        public synchronized void a(String str) {
            if (TextUtils.isEmpty(str)) {
                com.xiaomi.channel.commonutils.logger.b.m117a("channel is null, MiTinyDataClientImp.setChannel(String) failed.");
            } else {
                this.f112a = str;
                b(MiTinyDataClient.PENDING_REASON_CHANNEL);
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m129a() {
            return this.f109a != null;
        }

        /* renamed from: a, reason: collision with other method in class */
        public synchronized boolean m130a(ib ibVar) {
            String str;
            if (ibVar == null) {
                return false;
            }
            if (bl.a(ibVar, true)) {
                return false;
            }
            boolean z2 = TextUtils.isEmpty(ibVar.m502a()) && TextUtils.isEmpty(this.f112a);
            boolean z3 = !m129a();
            Context context = this.f109a;
            boolean z4 = context == null || b(context);
            if (!z3 && !z2 && !z4) {
                com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient Send item immediately." + ibVar.d());
                if (TextUtils.isEmpty(ibVar.d())) {
                    ibVar.f(com.xiaomi.push.service.ar.a());
                }
                if (TextUtils.isEmpty(ibVar.m502a())) {
                    ibVar.a(this.f112a);
                }
                if (TextUtils.isEmpty(ibVar.c())) {
                    ibVar.e(this.f109a.getPackageName());
                }
                if (ibVar.a() <= 0) {
                    ibVar.b(System.currentTimeMillis());
                }
                return b(ibVar);
            }
            if (z2) {
                str = "MiTinyDataClient Pending " + ibVar.b() + " reason is " + MiTinyDataClient.PENDING_REASON_CHANNEL;
            } else {
                if (!z3) {
                    if (z4) {
                        str = "MiTinyDataClient Pending " + ibVar.b() + " reason is " + MiTinyDataClient.PENDING_REASON_APPID;
                    }
                    a(ibVar);
                    return true;
                }
                str = "MiTinyDataClient Pending " + ibVar.b() + " reason is " + MiTinyDataClient.PENDING_REASON_INIT;
            }
            com.xiaomi.channel.commonutils.logger.b.c(str);
            a(ibVar);
            return true;
        }

        public void b(String str) {
            com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient.processPendingList(" + str + ")");
            ArrayList arrayList = new ArrayList();
            synchronized (this.f113a) {
                arrayList.addAll(this.f113a);
                this.f113a.clear();
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                m130a((ib) it.next());
            }
        }
    }

    public static void init(Context context, String str) {
        if (context == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("context is null, MiTinyDataClient.init(Context, String) failed.");
            return;
        }
        a.a().m128a(context);
        if (TextUtils.isEmpty(str)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("channel is null or empty, MiTinyDataClient.init(Context, String) failed.");
        } else {
            a.a().a(str);
        }
    }

    public static boolean upload(Context context, ib ibVar) {
        com.xiaomi.channel.commonutils.logger.b.c("MiTinyDataClient.upload " + ibVar.d());
        if (!a.a().m129a()) {
            a.a().m128a(context);
        }
        return a.a().m130a(ibVar);
    }

    public static boolean upload(Context context, String str, String str2, long j2, String str3) {
        ib ibVar = new ib();
        ibVar.d(str);
        ibVar.c(str2);
        ibVar.a(j2);
        ibVar.b(str3);
        ibVar.a(true);
        ibVar.a("push_sdk_channel");
        return upload(context, ibVar);
    }

    public static boolean upload(String str, String str2, long j2, String str3) {
        ib ibVar = new ib();
        ibVar.d(str);
        ibVar.c(str2);
        ibVar.a(j2);
        ibVar.b(str3);
        return a.a().m130a(ibVar);
    }
}
