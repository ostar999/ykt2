package com.xiaomi.mipush.sdk;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class g implements AbstractPushManager {

    /* renamed from: a, reason: collision with root package name */
    private static volatile g f24566a;

    /* renamed from: a, reason: collision with other field name */
    private Context f157a;

    /* renamed from: a, reason: collision with other field name */
    private PushConfiguration f158a;

    /* renamed from: a, reason: collision with other field name */
    private Map<f, AbstractPushManager> f159a = new HashMap();

    private g(Context context) {
        this.f157a = context.getApplicationContext();
    }

    public static g a(Context context) {
        if (f24566a == null) {
            synchronized (g.class) {
                if (f24566a == null) {
                    f24566a = new g(context);
                }
            }
        }
        return f24566a;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0120  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a() {
        /*
            Method dump skipped, instructions count: 444
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.g.a():void");
    }

    public AbstractPushManager a(f fVar) {
        return this.f159a.get(fVar);
    }

    public void a(PushConfiguration pushConfiguration) {
        this.f158a = pushConfiguration;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m171a(f fVar) {
        this.f159a.remove(fVar);
    }

    public void a(f fVar, AbstractPushManager abstractPushManager) {
        if (abstractPushManager != null) {
            if (this.f159a.containsKey(fVar)) {
                this.f159a.remove(fVar);
            }
            this.f159a.put(fVar, abstractPushManager);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m172a(f fVar) {
        return this.f159a.containsKey(fVar);
    }

    public boolean b(f fVar) {
        PushConfiguration pushConfiguration;
        int i2 = h.f24567a[fVar.ordinal()];
        if (i2 == 1) {
            PushConfiguration pushConfiguration2 = this.f158a;
            if (pushConfiguration2 != null) {
                return pushConfiguration2.getOpenHmsPush();
            }
        } else if (i2 == 2) {
            PushConfiguration pushConfiguration3 = this.f158a;
            if (pushConfiguration3 != null) {
                return pushConfiguration3.getOpenFCMPush();
            }
        } else if (i2 == 3 && (pushConfiguration = this.f158a) != null) {
            return pushConfiguration.getOpenCOSPush();
        }
        return false;
    }

    @Override // com.xiaomi.mipush.sdk.AbstractPushManager
    public void register() {
        com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : assemble push register");
        if (this.f159a.size() <= 0) {
            a();
        }
        if (this.f159a.size() > 0) {
            for (AbstractPushManager abstractPushManager : this.f159a.values()) {
                if (abstractPushManager != null) {
                    abstractPushManager.register();
                }
            }
            i.m173a(this.f157a);
        }
    }

    @Override // com.xiaomi.mipush.sdk.AbstractPushManager
    public void unregister() {
        com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : assemble push unregister");
        for (AbstractPushManager abstractPushManager : this.f159a.values()) {
            if (abstractPushManager != null) {
                abstractPushManager.unregister();
            }
        }
        this.f159a.clear();
    }
}
