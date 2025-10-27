package com.xiaomi.push;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class cn {

    /* renamed from: a, reason: collision with root package name */
    private static cn f24689a;

    /* renamed from: a, reason: collision with other field name */
    private static String f267a;

    /* renamed from: a, reason: collision with other field name */
    private Context f269a;

    /* renamed from: a, reason: collision with other field name */
    private cg f272a;

    /* renamed from: a, reason: collision with other field name */
    private ch f273a;

    /* renamed from: a, reason: collision with other field name */
    private ck f274a;

    /* renamed from: a, reason: collision with other field name */
    private Object f275a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private int f268a = 0;

    /* renamed from: a, reason: collision with other field name */
    private cd f271a = new cd();

    /* renamed from: a, reason: collision with other field name */
    private Handler f270a = new cb(this);

    public static cn a() {
        if (f24689a == null) {
            f24689a = new cn();
        }
        return f24689a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(HashMap map) throws Throwable {
        if (map == null) {
            return;
        }
        String strB = cj.b(this.f269a);
        cd cdVar = this.f271a;
        if (cdVar != null && strB != null) {
            cdVar.g(strB);
            if (map.containsKey(strB)) {
                this.f271a.h((String) map.get(strB));
            }
        }
        Object objM287a = cj.m287a(this.f269a);
        if (objM287a != null && map.containsKey(objM287a)) {
            map.remove(objM287a);
        }
        if (this.f271a == null || map.size() <= 0) {
            return;
        }
        this.f271a.a(new ArrayList(map.values()));
        b();
    }

    private void d() {
        cm.a(this.f269a, this.f270a, 0);
    }

    private void e() throws Throwable {
        cd cdVar;
        String strC = cj.c(this.f269a);
        String strA = cj.a(this.f269a, 2);
        String strA2 = cj.a(this.f269a, 1);
        if (strC == null || strA == null || strA2 == null || (cdVar = this.f271a) == null) {
            return;
        }
        cdVar.a(Build.MODEL).b(bp.b()).c(strC).f(strA).e(strA2).a(this.f273a.a()).b(this.f273a.b());
    }

    private void f() {
        b();
    }

    private void g() {
        cd cdVar;
        if (this.f268a != 4 || (cdVar = this.f271a) == null) {
            return;
        }
        ((bk) this.f269a).a(cdVar.a().a().toString());
    }

    private void h() {
        this.f274a = new ck(this, null);
        String strM241a = bl.a().m241a();
        f267a = strM241a;
        StringBuffer stringBuffer = new StringBuffer(strM241a);
        stringBuffer.append("/api/v2/realip");
        this.f274a.execute(stringBuffer.toString());
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m295a() {
        cg cgVar = this.f272a;
        if (cgVar != null) {
            cgVar.m286c();
            this.f272a.d();
            this.f272a = null;
        }
        this.f273a = null;
    }

    public void a(Context context) {
        this.f269a = context;
        this.f273a = new ch(this, null);
        cg cgVar = new cg(context);
        this.f272a = cgVar;
        cgVar.m285b();
        this.f272a.a(this.f273a);
    }

    public void b() throws Throwable {
        if (bl.a().m245b()) {
            int i2 = this.f268a;
            if (i2 == 0) {
                this.f268a = 1;
                e();
                if (this.f271a != null) {
                    d();
                    return;
                }
                return;
            }
            if (i2 == 1) {
                this.f268a = 2;
                f();
            } else if (i2 == 2) {
                this.f268a = 3;
                h();
            } else {
                if (i2 != 3) {
                    return;
                }
                this.f268a = 4;
                this.f274a.cancel(true);
                this.f274a = null;
                g();
            }
        }
    }

    public void c() {
        cg cgVar = this.f272a;
        if (cgVar != null) {
            cgVar.m284a();
        }
    }
}
