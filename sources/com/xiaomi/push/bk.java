package com.xiaomi.push;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.HandlerThread;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class bk extends ContextWrapper {

    /* renamed from: a, reason: collision with root package name */
    private static bk f24640a;

    /* renamed from: a, reason: collision with other field name */
    private int f214a;

    /* renamed from: a, reason: collision with other field name */
    private Handler f215a;

    /* renamed from: a, reason: collision with other field name */
    private HandlerThread f216a;

    /* renamed from: a, reason: collision with other field name */
    private bt f217a;

    /* renamed from: a, reason: collision with other field name */
    List f218a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f219a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f24641b;

    private bk(Context context) {
        super(context);
        this.f24641b = false;
        this.f218a = new ArrayList();
        this.f214a = 0;
        this.f217a = new cw(this);
        this.f219a = false;
        HandlerThread handlerThread = new HandlerThread("metoknlp_app");
        this.f216a = handlerThread;
        handlerThread.start();
        this.f215a = new cs(this, this.f216a.getLooper());
        bp.a(context);
        this.f215a.sendEmptyMessageDelayed(101, 1000L);
    }

    public static bk a() {
        bk bkVar = f24640a;
        if (bkVar == null) {
            return null;
        }
        return bkVar;
    }

    public static bk a(Context context) {
        if (f24640a == null) {
            f24640a = new bk(context);
        }
        return f24640a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        if (!this.f219a) {
            this.f219a = true;
        }
        by.a().a(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        this.f215a.sendEmptyMessageDelayed(102, com.heytap.mcssdk.constant.a.f7153q);
    }

    /* renamed from: a, reason: collision with other method in class */
    public int m235a() {
        return this.f214a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public Handler m236a() {
        return this.f215a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m237a() {
        by.a().m252a();
    }

    public void a(ca caVar, int i2) {
        Iterator it = this.f218a.iterator();
        while (it.hasNext()) {
            if (((ca) it.next()) == caVar) {
                return;
            }
        }
        this.f214a = i2;
        this.f218a.add(caVar);
    }

    public void a(String str) {
        for (ca caVar : this.f218a) {
            if (caVar != null) {
                caVar.a(str);
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m238a() {
        return this.f219a;
    }

    public void b() {
        bl.a(f24640a);
        bv.a(f24640a);
        bv.a().a(this.f217a);
    }
}
