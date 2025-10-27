package com.beizi.fusion.e.a;

import android.content.Context;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class k {

    /* renamed from: a, reason: collision with root package name */
    private Context f5036a;

    /* renamed from: b, reason: collision with root package name */
    private Class f5037b;

    /* renamed from: c, reason: collision with root package name */
    private Object f5038c;

    /* renamed from: d, reason: collision with root package name */
    private Method f5039d;

    /* renamed from: e, reason: collision with root package name */
    private Method f5040e;

    /* renamed from: f, reason: collision with root package name */
    private Method f5041f;

    /* renamed from: g, reason: collision with root package name */
    private Method f5042g;

    public k(Context context) throws ClassNotFoundException {
        this.f5036a = context;
        try {
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            this.f5037b = cls;
            this.f5038c = cls.newInstance();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.f5039d = this.f5037b.getMethod("getDefaultUDID", Context.class);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            this.f5040e = this.f5037b.getMethod("getOAID", Context.class);
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        try {
            this.f5041f = this.f5037b.getMethod("getVAID", Context.class);
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        try {
            this.f5042g = this.f5037b.getMethod("getAAID", Context.class);
        } catch (Exception e6) {
            e6.printStackTrace();
        }
    }

    private String a(Context context, Method method) {
        Object obj = this.f5038c;
        if (obj != null && method != null) {
            try {
                return (String) method.invoke(obj, context);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public String a() {
        return a(this.f5036a, this.f5040e);
    }
}
