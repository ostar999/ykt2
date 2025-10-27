package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.xiaomi.push.di;
import com.xiaomi.push.hw;
import com.xiaomi.push.hx;
import com.xiaomi.push.ib;
import com.xiaomi.push.ic;
import com.xiaomi.push.in;
import com.xiaomi.push.iq;
import com.xiaomi.push.jb;
import com.xiaomi.push.je;
import com.xiaomi.push.jf;
import com.xiaomi.push.jl;
import com.xiaomi.push.jp;
import com.xiaomi.push.jq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class az {

    /* renamed from: a, reason: collision with root package name */
    private static az f24537a = null;

    /* renamed from: a, reason: collision with other field name */
    private static final ArrayList<a> f134a = new ArrayList<>();

    /* renamed from: b, reason: collision with root package name */
    private static boolean f24538b = false;

    /* renamed from: a, reason: collision with other field name */
    private Context f135a;

    /* renamed from: a, reason: collision with other field name */
    private Handler f137a;

    /* renamed from: a, reason: collision with other field name */
    private Messenger f138a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f142a;

    /* renamed from: a, reason: collision with other field name */
    private List<Message> f141a = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    private boolean f24539c = false;

    /* renamed from: a, reason: collision with other field name */
    private Intent f136a = null;

    /* renamed from: a, reason: collision with other field name */
    private Integer f139a = null;

    /* renamed from: a, reason: collision with other field name */
    private String f140a = null;

    public static class a<T extends jq<T, ?>> {

        /* renamed from: a, reason: collision with root package name */
        hw f24540a;

        /* renamed from: a, reason: collision with other field name */
        T f143a;

        /* renamed from: a, reason: collision with other field name */
        boolean f144a;
    }

    private az(Context context) {
        this.f142a = false;
        this.f137a = null;
        this.f135a = context.getApplicationContext();
        this.f142a = m142c();
        f24538b = m143d();
        this.f137a = new ba(this, Looper.getMainLooper());
        Intent intentB = b();
        if (intentB != null) {
            b(intentB);
        }
    }

    private synchronized int a() {
        return this.f135a.getSharedPreferences("mipush_extra", 0).getInt(Constants.EXTRA_KEY_BOOT_SERVICE_MODE, -1);
    }

    /* renamed from: a, reason: collision with other method in class */
    private Intent m137a() {
        return (!m147a() || "com.xiaomi.xmsf".equals(this.f135a.getPackageName())) ? e() : d();
    }

    private Message a(Intent intent) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 17;
        messageObtain.obj = intent;
        return messageObtain;
    }

    public static synchronized az a(Context context) {
        if (f24537a == null) {
            f24537a = new az(context);
        }
        return f24537a;
    }

    /* renamed from: a, reason: collision with other method in class */
    private String m140a() {
        try {
            return this.f135a.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4).versionCode >= 106 ? "com.xiaomi.push.service.XMPushService" : "com.xiaomi.xmsf.push.service.XMPushService";
        } catch (Exception unused) {
            return "com.xiaomi.xmsf.push.service.XMPushService";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00c9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(java.lang.String r12, com.xiaomi.mipush.sdk.be r13, boolean r14, java.util.HashMap<java.lang.String, java.lang.String> r15) {
        /*
            Method dump skipped, instructions count: 331
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.az.a(java.lang.String, com.xiaomi.mipush.sdk.be, boolean, java.util.HashMap):void");
    }

    private Intent b() {
        if (!"com.xiaomi.xmsf".equals(this.f135a.getPackageName())) {
            return c();
        }
        com.xiaomi.channel.commonutils.logger.b.c("pushChannel xmsf create own channel");
        return e();
    }

    private void b(Intent intent) {
        try {
            if (com.xiaomi.push.n.m679a() || Build.VERSION.SDK_INT < 26) {
                this.f135a.startService(intent);
            } else {
                d(intent);
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    private Intent c() {
        if (m147a()) {
            com.xiaomi.channel.commonutils.logger.b.c("pushChannel app start miui china channel");
            return d();
        }
        com.xiaomi.channel.commonutils.logger.b.c("pushChannel app start  own channel");
        return e();
    }

    private synchronized void c(int i2) {
        this.f135a.getSharedPreferences("mipush_extra", 0).edit().putInt(Constants.EXTRA_KEY_BOOT_SERVICE_MODE, i2).commit();
    }

    private void c(Intent intent) {
        com.xiaomi.push.service.ao aoVarA = com.xiaomi.push.service.ao.a(this.f135a);
        int iA = ic.ServiceBootMode.a();
        hx hxVar = hx.START;
        int iA2 = aoVarA.a(iA, hxVar.a());
        int iA3 = a();
        hx hxVar2 = hx.BIND;
        boolean z2 = iA2 == hxVar2.a() && f24538b;
        int iA4 = z2 ? hxVar2.a() : hxVar.a();
        if (iA4 != iA3) {
            m148a(iA4);
        }
        if (z2) {
            d(intent);
        } else {
            b(intent);
        }
    }

    /* renamed from: c, reason: collision with other method in class */
    private boolean m142c() {
        try {
            PackageInfo packageInfo = this.f135a.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode >= 105;
        } catch (Throwable unused) {
            return false;
        }
    }

    private Intent d() {
        Intent intent = new Intent();
        String packageName = this.f135a.getPackageName();
        intent.setPackage("com.xiaomi.xmsf");
        intent.setClassName("com.xiaomi.xmsf", m140a());
        intent.putExtra("mipush_app_package", packageName);
        f();
        return intent;
    }

    private synchronized void d(Intent intent) {
        if (this.f24539c) {
            Message messageA = a(intent);
            if (this.f141a.size() >= 50) {
                this.f141a.remove(0);
            }
            this.f141a.add(messageA);
            return;
        }
        if (this.f138a == null) {
            this.f135a.bindService(intent, new bc(this), 1);
            this.f24539c = true;
            this.f141a.clear();
            this.f141a.add(a(intent));
        } else {
            try {
                this.f138a.send(a(intent));
            } catch (RemoteException unused) {
                this.f138a = null;
                this.f24539c = false;
            }
        }
    }

    /* renamed from: d, reason: collision with other method in class */
    private boolean m143d() {
        if (m147a()) {
            try {
                return this.f135a.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4).versionCode >= 108;
            } catch (Exception unused) {
            }
        }
        return true;
    }

    private Intent e() {
        Intent intent = new Intent();
        String packageName = this.f135a.getPackageName();
        g();
        intent.setComponent(new ComponentName(this.f135a, "com.xiaomi.push.service.XMPushService"));
        intent.putExtra("mipush_app_package", packageName);
        return intent;
    }

    /* renamed from: e, reason: collision with other method in class */
    private boolean m144e() {
        String packageName = this.f135a.getPackageName();
        return packageName.contains("miui") || packageName.contains("xiaomi") || (this.f135a.getApplicationInfo().flags & 1) != 0;
    }

    private void f() {
        try {
            this.f135a.getPackageManager().setComponentEnabledSetting(new ComponentName(this.f135a, "com.xiaomi.push.service.XMPushService"), 2, 1);
        } catch (Throwable unused) {
        }
    }

    private void g() {
        try {
            this.f135a.getPackageManager().setComponentEnabledSetting(new ComponentName(this.f135a, "com.xiaomi.push.service.XMPushService"), 1, 1);
        } catch (Throwable unused) {
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m145a() {
        b(m137a());
    }

    public void a(int i2) {
        Intent intentM137a = m137a();
        intentM137a.setAction("com.xiaomi.mipush.CLEAR_NOTIFICATION");
        intentM137a.putExtra(com.xiaomi.push.service.ax.f25636z, this.f135a.getPackageName());
        intentM137a.putExtra(com.xiaomi.push.service.ax.A, i2);
        c(intentM137a);
    }

    public void a(int i2, String str) {
        Intent intentM137a = m137a();
        intentM137a.setAction("com.xiaomi.mipush.thirdparty");
        intentM137a.putExtra("com.xiaomi.mipush.thirdparty_LEVEL", i2);
        intentM137a.putExtra("com.xiaomi.mipush.thirdparty_DESC", str);
        b(intentM137a);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m146a(Intent intent) {
        intent.fillIn(m137a(), 24);
        c(intent);
    }

    public final void a(ib ibVar) {
        Intent intentM137a = m137a();
        byte[] bArrA = jp.a(ibVar);
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("send TinyData failed, because tinyDataBytes is null.");
            return;
        }
        intentM137a.setAction("com.xiaomi.mipush.SEND_TINYDATA");
        intentM137a.putExtra("mipush_payload", bArrA);
        b(intentM137a);
    }

    public final void a(jf jfVar, boolean z2) {
        this.f136a = null;
        d.m156a(this.f135a).f149a = jfVar.a();
        Intent intentM137a = m137a();
        byte[] bArrA = jp.a(as.a(this.f135a, jfVar, hw.Registration));
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("register fail, because msgBytes is null.");
            return;
        }
        intentM137a.setAction("com.xiaomi.mipush.REGISTER_APP");
        intentM137a.putExtra("mipush_app_id", d.m156a(this.f135a).m157a());
        intentM137a.putExtra("mipush_payload", bArrA);
        intentM137a.putExtra("mipush_session", this.f140a);
        intentM137a.putExtra("mipush_env_chanage", z2);
        intentM137a.putExtra("mipush_env_type", d.m156a(this.f135a).a());
        if (com.xiaomi.push.as.b(this.f135a) && m150b()) {
            c(intentM137a);
        } else {
            this.f136a = intentM137a;
        }
    }

    public final void a(jl jlVar) {
        byte[] bArrA = jp.a(as.a(this.f135a, jlVar, hw.UnRegistration));
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("unregister fail, because msgBytes is null.");
            return;
        }
        Intent intentM137a = m137a();
        intentM137a.setAction("com.xiaomi.mipush.UNREGISTER_APP");
        intentM137a.putExtra("mipush_app_id", d.m156a(this.f135a).m157a());
        intentM137a.putExtra("mipush_payload", bArrA);
        c(intentM137a);
    }

    public final <T extends jq<T, ?>> void a(T t2, hw hwVar, iq iqVar) {
        a((az) t2, hwVar, !hwVar.equals(hw.Registration), iqVar);
    }

    public <T extends jq<T, ?>> void a(T t2, hw hwVar, boolean z2) {
        a aVar = new a();
        aVar.f143a = t2;
        aVar.f24540a = hwVar;
        aVar.f144a = z2;
        ArrayList<a> arrayList = f134a;
        synchronized (arrayList) {
            arrayList.add(aVar);
            if (arrayList.size() > 10) {
                arrayList.remove(0);
            }
        }
    }

    public final <T extends jq<T, ?>> void a(T t2, hw hwVar, boolean z2, iq iqVar) {
        a(t2, hwVar, z2, true, iqVar, true);
    }

    public final <T extends jq<T, ?>> void a(T t2, hw hwVar, boolean z2, iq iqVar, boolean z3) {
        a(t2, hwVar, z2, true, iqVar, z3);
    }

    public final <T extends jq<T, ?>> void a(T t2, hw hwVar, boolean z2, boolean z3, iq iqVar, boolean z4) {
        a(t2, hwVar, z2, z3, iqVar, z4, this.f135a.getPackageName(), d.m156a(this.f135a).m157a());
    }

    public final <T extends jq<T, ?>> void a(T t2, hw hwVar, boolean z2, boolean z3, iq iqVar, boolean z4, String str, String str2) {
        if (!d.m156a(this.f135a).m165c()) {
            if (z3) {
                a((az) t2, hwVar, z2);
                return;
            } else {
                com.xiaomi.channel.commonutils.logger.b.m117a("drop the message before initialization.");
                return;
            }
        }
        jb jbVarA = as.a(this.f135a, t2, hwVar, z2, str, str2);
        if (iqVar != null) {
            jbVarA.a(iqVar);
        }
        byte[] bArrA = jp.a(jbVarA);
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("send message fail, because msgBytes is null.");
            return;
        }
        di.a(this.f135a.getPackageName(), this.f135a, t2, hwVar, bArrA.length);
        Intent intentM137a = m137a();
        intentM137a.setAction("com.xiaomi.mipush.SEND_MESSAGE");
        intentM137a.putExtra("mipush_payload", bArrA);
        intentM137a.putExtra("com.xiaomi.mipush.MESSAGE_CACHE", z4);
        c(intentM137a);
    }

    public final void a(String str, be beVar, f fVar) {
        ap.a(this.f135a).a(beVar, "syncing");
        a(str, beVar, false, i.a(this.f135a, fVar));
    }

    public void a(String str, String str2) {
        Intent intentM137a = m137a();
        intentM137a.setAction("com.xiaomi.mipush.CLEAR_NOTIFICATION");
        intentM137a.putExtra(com.xiaomi.push.service.ax.f25636z, this.f135a.getPackageName());
        intentM137a.putExtra(com.xiaomi.push.service.ax.E, str);
        intentM137a.putExtra(com.xiaomi.push.service.ax.F, str2);
        c(intentM137a);
    }

    public final void a(boolean z2) {
        a(z2, (String) null);
    }

    public final void a(boolean z2, String str) {
        be beVar;
        ap apVarA;
        be beVar2;
        if (z2) {
            ap apVarA2 = ap.a(this.f135a);
            beVar = be.DISABLE_PUSH;
            apVarA2.a(beVar, "syncing");
            apVarA = ap.a(this.f135a);
            beVar2 = be.ENABLE_PUSH;
        } else {
            ap apVarA3 = ap.a(this.f135a);
            beVar = be.ENABLE_PUSH;
            apVarA3.a(beVar, "syncing");
            apVarA = ap.a(this.f135a);
            beVar2 = be.DISABLE_PUSH;
        }
        apVarA.a(beVar2, "");
        a(str, beVar, true, (HashMap<String, String>) null);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m147a() {
        return this.f142a && 1 == d.m156a(this.f135a).a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m148a(int i2) {
        if (!d.m156a(this.f135a).m163b()) {
            return false;
        }
        c(i2);
        je jeVar = new je();
        jeVar.a(com.xiaomi.push.service.ar.a());
        jeVar.b(d.m156a(this.f135a).m157a());
        jeVar.d(this.f135a.getPackageName());
        jeVar.c(in.ClientABTest.f622a);
        HashMap map = new HashMap();
        jeVar.f769a = map;
        map.put("boot_mode", i2 + "");
        a(this.f135a).a((az) jeVar, hw.Notification, false, (iq) null);
        return true;
    }

    /* renamed from: b, reason: collision with other method in class */
    public final void m149b() {
        Intent intentM137a = m137a();
        intentM137a.setAction("com.xiaomi.mipush.DISABLE_PUSH");
        c(intentM137a);
    }

    public void b(int i2) {
        Intent intentM137a = m137a();
        intentM137a.setAction("com.xiaomi.mipush.SET_NOTIFICATION_TYPE");
        intentM137a.putExtra(com.xiaomi.push.service.ax.f25636z, this.f135a.getPackageName());
        intentM137a.putExtra(com.xiaomi.push.service.ax.B, i2);
        intentM137a.putExtra(com.xiaomi.push.service.ax.D, com.xiaomi.push.ax.b(this.f135a.getPackageName() + i2));
        c(intentM137a);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m150b() {
        if (!m147a() || !m144e()) {
            return true;
        }
        if (this.f139a == null) {
            Integer numValueOf = Integer.valueOf(com.xiaomi.push.service.ba.a(this.f135a).a());
            this.f139a = numValueOf;
            if (numValueOf.intValue() == 0) {
                this.f135a.getContentResolver().registerContentObserver(com.xiaomi.push.service.ba.a(this.f135a).m724a(), false, new bb(this, new Handler(Looper.getMainLooper())));
            }
        }
        return this.f139a.intValue() != 0;
    }

    /* renamed from: c, reason: collision with other method in class */
    public void m151c() {
        Intent intent = this.f136a;
        if (intent != null) {
            c(intent);
            this.f136a = null;
        }
    }

    /* renamed from: d, reason: collision with other method in class */
    public void m152d() {
        ArrayList<a> arrayList = f134a;
        synchronized (arrayList) {
            Iterator<a> it = arrayList.iterator();
            while (it.hasNext()) {
                a next = it.next();
                a(next.f143a, next.f24540a, next.f144a, false, null, true);
            }
            f134a.clear();
        }
    }

    /* renamed from: e, reason: collision with other method in class */
    public void m153e() {
        Intent intentM137a = m137a();
        intentM137a.setAction("com.xiaomi.mipush.SET_NOTIFICATION_TYPE");
        intentM137a.putExtra(com.xiaomi.push.service.ax.f25636z, this.f135a.getPackageName());
        intentM137a.putExtra(com.xiaomi.push.service.ax.D, com.xiaomi.push.ax.b(this.f135a.getPackageName()));
        c(intentM137a);
    }
}
