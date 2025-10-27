package com.meizu.cloud.pushsdk.platform.a;

import android.content.Context;
import com.meizu.cloud.pushsdk.b.a.c;
import com.meizu.cloud.pushsdk.platform.b.d;
import com.meizu.cloud.pushsdk.platform.b.e;
import com.meizu.cloud.pushsdk.platform.b.f;
import com.meizu.cloud.pushsdk.platform.b.g;
import java.io.File;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static b f9520a;

    /* renamed from: b, reason: collision with root package name */
    private ScheduledExecutorService f9521b;

    /* renamed from: c, reason: collision with root package name */
    private Context f9522c;

    /* renamed from: d, reason: collision with root package name */
    private a f9523d;

    /* renamed from: e, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.platform.b.b f9524e;

    /* renamed from: f, reason: collision with root package name */
    private g f9525f;

    /* renamed from: g, reason: collision with root package name */
    private f f9526g;

    /* renamed from: h, reason: collision with root package name */
    private e f9527h;

    /* renamed from: i, reason: collision with root package name */
    private d f9528i;

    /* renamed from: j, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.platform.b.a f9529j;

    /* renamed from: k, reason: collision with root package name */
    private boolean f9530k;

    public b(Context context, boolean z2) {
        this(context, z2, true);
    }

    public b(Context context, boolean z2, boolean z3) {
        this.f9530k = true;
        Context applicationContext = context.getApplicationContext();
        this.f9522c = applicationContext;
        this.f9523d = new a(applicationContext);
        if (z2) {
            this.f9521b = (ScheduledExecutorService) com.meizu.cloud.pushsdk.c.b.a.b.a();
        }
        this.f9530k = z3;
        this.f9524e = new com.meizu.cloud.pushsdk.platform.b.b(this.f9522c, this.f9523d, this.f9521b, z3);
        this.f9525f = new g(this.f9522c, this.f9523d, this.f9521b, z3);
        this.f9526g = new f(this.f9522c, this.f9523d, this.f9521b, z3);
        this.f9527h = new e(this.f9522c, this.f9523d, this.f9521b, z3);
        this.f9528i = new d(this.f9522c, this.f9523d, this.f9521b, z3);
        this.f9529j = new com.meizu.cloud.pushsdk.platform.b.a(this.f9522c, this.f9521b, z3);
    }

    public static b a(Context context) {
        if (f9520a == null) {
            synchronized (b.class) {
                if (f9520a == null) {
                    f9520a = new b(context, true);
                }
            }
        }
        return f9520a;
    }

    public c<String> a(String str, String str2, String str3, File file) {
        return this.f9523d.a(str, str2, str3, file);
    }

    public void a(boolean z2) {
        this.f9524e.a(z2);
        this.f9525f.a(z2);
        this.f9526g.a(z2);
        this.f9528i.a(z2);
        this.f9527h.a(z2);
    }

    public boolean a(String str) {
        com.meizu.cloud.pushsdk.platform.b.a aVar = new com.meizu.cloud.pushsdk.platform.b.a(this.f9522c, this.f9521b, this.f9530k);
        aVar.a(0);
        aVar.d(str);
        return aVar.m();
    }

    public boolean a(String str, String str2) {
        com.meizu.cloud.pushsdk.platform.b.a aVar = new com.meizu.cloud.pushsdk.platform.b.a(this.f9522c, this.f9521b, this.f9530k);
        aVar.a(2);
        aVar.a(str2);
        aVar.d(str);
        return aVar.m();
    }

    public boolean a(String str, String str2, String str3) {
        this.f9524e.b(str);
        this.f9524e.c(str2);
        this.f9524e.d(str3);
        return this.f9524e.m();
    }

    public boolean a(String str, String str2, String str3, String str4) {
        this.f9526g.b(str);
        this.f9526g.c(str2);
        this.f9526g.d(str3);
        this.f9526g.a(str4);
        this.f9526g.a(2);
        return this.f9526g.m();
    }

    public boolean a(String str, String str2, String str3, String str4, int i2, boolean z2) {
        this.f9526g.b(str);
        this.f9526g.c(str2);
        this.f9526g.d(str3);
        this.f9526g.a(str4);
        this.f9526g.a(i2);
        this.f9526g.b(z2);
        return this.f9526g.m();
    }

    public boolean a(String str, String str2, String str3, String str4, String str5) {
        this.f9527h.b(str);
        this.f9527h.c(str2);
        this.f9527h.d(str3);
        this.f9527h.e(str4);
        this.f9527h.a(0);
        this.f9527h.a(str5);
        return this.f9527h.m();
    }

    public boolean a(String str, String str2, String str3, String str4, boolean z2) {
        this.f9526g.b(str);
        this.f9526g.c(str2);
        this.f9526g.d(str3);
        this.f9526g.a(str4);
        this.f9526g.a(3);
        this.f9526g.b(z2);
        return this.f9526g.m();
    }

    public boolean a(String str, int... iArr) {
        com.meizu.cloud.pushsdk.platform.b.a aVar = new com.meizu.cloud.pushsdk.platform.b.a(this.f9522c, this.f9521b, this.f9530k);
        aVar.a(iArr);
        aVar.d(str);
        aVar.a(1);
        return aVar.m();
    }

    public boolean b(String str, String str2, String str3) {
        this.f9525f.b(str);
        this.f9525f.c(str2);
        this.f9525f.d(str3);
        return this.f9525f.m();
    }

    public boolean b(String str, String str2, String str3, String str4) {
        this.f9527h.b(str);
        this.f9527h.c(str2);
        this.f9527h.d(str3);
        this.f9527h.e(str4);
        this.f9527h.a(2);
        return this.f9527h.m();
    }

    public boolean b(String str, String str2, String str3, String str4, String str5) {
        this.f9527h.b(str);
        this.f9527h.c(str2);
        this.f9527h.d(str3);
        this.f9527h.e(str4);
        this.f9527h.a(1);
        this.f9527h.a(str5);
        return this.f9527h.m();
    }

    public boolean c(String str, String str2, String str3, String str4) {
        this.f9527h.b(str);
        this.f9527h.c(str2);
        this.f9527h.d(str3);
        this.f9527h.e(str4);
        this.f9527h.a(3);
        return this.f9527h.m();
    }

    public boolean c(String str, String str2, String str3, String str4, String str5) {
        this.f9528i.b(str);
        this.f9528i.c(str2);
        this.f9528i.d(str3);
        this.f9528i.e(str4);
        this.f9528i.a(0);
        this.f9528i.a(str5);
        return this.f9528i.m();
    }

    public boolean d(String str, String str2, String str3, String str4) {
        this.f9528i.b(str);
        this.f9528i.c(str2);
        this.f9528i.d(str3);
        this.f9528i.e(str4);
        this.f9528i.a(2);
        return this.f9528i.m();
    }

    public boolean d(String str, String str2, String str3, String str4, String str5) {
        this.f9528i.b(str);
        this.f9528i.c(str2);
        this.f9528i.d(str3);
        this.f9528i.e(str4);
        this.f9528i.a(1);
        this.f9528i.a(str5);
        return this.f9528i.m();
    }
}
