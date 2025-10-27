package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import java.util.HashMap;

/* loaded from: classes6.dex */
public final class fa {

    /* renamed from: a, reason: collision with root package name */
    private static volatile fa f24842a;

    /* renamed from: a, reason: collision with other field name */
    private int f411a;

    /* renamed from: a, reason: collision with other field name */
    private Context f412a;

    /* renamed from: a, reason: collision with other field name */
    private fe f413a;

    /* renamed from: a, reason: collision with other field name */
    private String f414a;

    /* renamed from: a, reason: collision with other field name */
    private HashMap<fc, fd> f415a;

    /* renamed from: b, reason: collision with root package name */
    private String f24843b;

    private fa(Context context) {
        HashMap<fc, fd> map = new HashMap<>();
        this.f415a = map;
        this.f412a = context;
        map.put(fc.SERVICE_ACTION, new fg());
        this.f415a.put(fc.SERVICE_COMPONENT, new fh());
        this.f415a.put(fc.ACTIVITY, new ey());
        this.f415a.put(fc.PROVIDER, new ff());
    }

    public static fa a(Context context) {
        if (f24842a == null) {
            synchronized (fa.class) {
                if (f24842a == null) {
                    f24842a = new fa(context);
                }
            }
        }
        return f24842a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(fc fcVar, Context context, ez ezVar) {
        this.f415a.get(fcVar).a(context, ezVar);
    }

    public int a() {
        return this.f411a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public fe m415a() {
        return this.f413a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m416a() {
        return this.f414a;
    }

    public void a(int i2) {
        this.f411a = i2;
    }

    public void a(Context context, String str, int i2, String str2, String str3) {
        if (context != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            a(i2);
            ai.a(this.f412a).a(new fb(this, str, context, str2, str3));
        } else {
            ev.a(context, "" + str, 1008, "A receive a incorrect message");
        }
    }

    public void a(fc fcVar, Context context, Intent intent, String str) {
        if (fcVar != null) {
            this.f415a.get(fcVar).a(context, intent, str);
        } else {
            ev.a(context, "null", 1008, "A receive a incorrect message with empty type");
        }
    }

    public void a(fe feVar) {
        this.f413a = feVar;
    }

    public void a(String str) {
        this.f414a = str;
    }

    public void a(String str, String str2, int i2, fe feVar) {
        a(str);
        b(str2);
        a(i2);
        a(feVar);
    }

    public String b() {
        return this.f24843b;
    }

    public void b(String str) {
        this.f24843b = str;
    }
}
