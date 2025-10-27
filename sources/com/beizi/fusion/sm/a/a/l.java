package com.beizi.fusion.sm.a.a;

import android.app.Application;
import android.content.Context;

/* loaded from: classes2.dex */
public final class l {

    /* renamed from: a, reason: collision with root package name */
    private static com.beizi.fusion.sm.a.d f5301a;

    public static com.beizi.fusion.sm.a.d a(Context context) {
        if (context != null && !(context instanceof Application)) {
            context = context.getApplicationContext();
        }
        com.beizi.fusion.sm.a.d dVar = f5301a;
        if (dVar != null) {
            return dVar;
        }
        com.beizi.fusion.sm.a.d dVarB = b(context);
        f5301a = dVarB;
        if (dVarB == null || !dVarB.a()) {
            com.beizi.fusion.sm.a.d dVarC = c(context);
            f5301a = dVarC;
            return dVarC;
        }
        com.beizi.fusion.sm.a.f.a("Manufacturer interface has been found: " + f5301a.getClass().getName());
        return f5301a;
    }

    private static com.beizi.fusion.sm.a.d b(Context context) {
        if (com.beizi.fusion.sm.a.g.k() || com.beizi.fusion.sm.a.g.n()) {
            return new h(context);
        }
        if (com.beizi.fusion.sm.a.g.j()) {
            return new i(context);
        }
        if (com.beizi.fusion.sm.a.g.l()) {
            return new k(context);
        }
        if (com.beizi.fusion.sm.a.g.e() || com.beizi.fusion.sm.a.g.f() || com.beizi.fusion.sm.a.g.g()) {
            return new q(context);
        }
        if (com.beizi.fusion.sm.a.g.i()) {
            return new o(context);
        }
        if (com.beizi.fusion.sm.a.g.d()) {
            return new p(context);
        }
        if (com.beizi.fusion.sm.a.g.m()) {
            return new a(context);
        }
        if (com.beizi.fusion.sm.a.g.a() || com.beizi.fusion.sm.a.g.b()) {
            return new g(context);
        }
        if (com.beizi.fusion.sm.a.g.c() || com.beizi.fusion.sm.a.g.h()) {
            return new n(context);
        }
        if (com.beizi.fusion.sm.a.g.a(context)) {
            return new b(context);
        }
        if (com.beizi.fusion.sm.a.g.p()) {
            return new c(context);
        }
        if (com.beizi.fusion.sm.a.g.o()) {
            return new e(context);
        }
        return null;
    }

    private static com.beizi.fusion.sm.a.d c(Context context) {
        j jVar = new j(context);
        if (jVar.a()) {
            com.beizi.fusion.sm.a.f.a("Mobile Security Alliance has been found: " + j.class.getName());
            return jVar;
        }
        f fVar = new f(context);
        if (fVar.a()) {
            com.beizi.fusion.sm.a.f.a("Google Play Service has been found: " + f.class.getName());
            return fVar;
        }
        d dVar = new d();
        com.beizi.fusion.sm.a.f.a("OAID/AAID was not supported: " + d.class.getName());
        return dVar;
    }
}
