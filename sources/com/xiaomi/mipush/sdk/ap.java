package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ap {

    /* renamed from: a, reason: collision with root package name */
    private static volatile ap f24526a;

    /* renamed from: a, reason: collision with other field name */
    private Context f125a;

    /* renamed from: a, reason: collision with other field name */
    private List<ad> f126a = new ArrayList();

    private ap(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.f125a = applicationContext;
        if (applicationContext == null) {
            this.f125a = context;
        }
    }

    public static ap a(Context context) {
        if (f24526a == null) {
            synchronized (ap.class) {
                if (f24526a == null) {
                    f24526a = new ap(context);
                }
            }
        }
        return f24526a;
    }

    public int a(String str) {
        synchronized (this.f126a) {
            ad adVar = new ad();
            adVar.f121a = str;
            if (this.f126a.contains(adVar)) {
                for (ad adVar2 : this.f126a) {
                    if (adVar2.equals(adVar)) {
                        return adVar2.f24517a;
                    }
                }
            }
            return 0;
        }
    }

    public synchronized String a(be beVar) {
        return this.f125a.getSharedPreferences("mipush_extra", 0).getString(beVar.name(), "");
    }

    public synchronized void a(be beVar, String str) {
        SharedPreferences sharedPreferences = this.f125a.getSharedPreferences("mipush_extra", 0);
        sharedPreferences.edit().putString(beVar.name(), str).commit();
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m133a(String str) {
        synchronized (this.f126a) {
            ad adVar = new ad();
            adVar.f24517a = 0;
            adVar.f121a = str;
            if (this.f126a.contains(adVar)) {
                this.f126a.remove(adVar);
            }
            this.f126a.add(adVar);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m134a(String str) {
        synchronized (this.f126a) {
            ad adVar = new ad();
            adVar.f121a = str;
            return this.f126a.contains(adVar);
        }
    }

    public void b(String str) {
        synchronized (this.f126a) {
            ad adVar = new ad();
            adVar.f121a = str;
            if (this.f126a.contains(adVar)) {
                Iterator<ad> it = this.f126a.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ad next = it.next();
                    if (adVar.equals(next)) {
                        adVar = next;
                        break;
                    }
                }
            }
            adVar.f24517a++;
            this.f126a.remove(adVar);
            this.f126a.add(adVar);
        }
    }

    public void c(String str) {
        synchronized (this.f126a) {
            ad adVar = new ad();
            adVar.f121a = str;
            if (this.f126a.contains(adVar)) {
                this.f126a.remove(adVar);
            }
        }
    }
}
