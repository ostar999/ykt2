package com.beizi.fusion.sm.a.a;

import android.annotation.SuppressLint;
import android.content.Context;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
class q implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5311a;

    /* renamed from: b, reason: collision with root package name */
    private Class<?> f5312b;

    /* renamed from: c, reason: collision with root package name */
    private Object f5313c;

    @SuppressLint({"PrivateApi"})
    public q(Context context) throws ClassNotFoundException {
        this.f5311a = context;
        try {
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            this.f5312b = cls;
            this.f5313c = cls.newInstance();
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
        }
    }

    private String b() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return (String) this.f5312b.getMethod("getOAID", Context.class).invoke(this.f5313c, this.f5311a);
    }

    @Override // com.beizi.fusion.sm.a.d
    public boolean a() {
        return this.f5313c != null;
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) {
        if (this.f5311a == null || cVar == null) {
            return;
        }
        if (this.f5312b == null || this.f5313c == null) {
            cVar.a(new com.beizi.fusion.sm.a.e("Xiaomi IdProvider not exists"));
            return;
        }
        try {
            String strB = b();
            if (strB == null || strB.length() == 0) {
                throw new com.beizi.fusion.sm.a.e("OAID query failed");
            }
            com.beizi.fusion.sm.a.f.a("OAID query success: " + strB);
            cVar.a(strB);
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            cVar.a(e2);
        }
    }
}
