package com.huawei.hms.framework.network.grs.g;

import android.content.Context;
import android.net.Uri;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.weibo.ssosdk.WeiboSsoSdk;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    protected d f7617a;

    /* renamed from: b, reason: collision with root package name */
    private final String f7618b;

    /* renamed from: c, reason: collision with root package name */
    private final c f7619c;

    /* renamed from: d, reason: collision with root package name */
    private final int f7620d;

    /* renamed from: e, reason: collision with root package name */
    private final Context f7621e;

    /* renamed from: f, reason: collision with root package name */
    private final String f7622f;

    /* renamed from: g, reason: collision with root package name */
    private final GrsBaseInfo f7623g;

    /* renamed from: h, reason: collision with root package name */
    private final com.huawei.hms.framework.network.grs.e.c f7624h;

    /* renamed from: com.huawei.hms.framework.network.grs.g.a$a, reason: collision with other inner class name */
    public enum EnumC0181a {
        GRSPOST,
        GRSGET,
        GRSDEFAULT
    }

    public a(String str, int i2, c cVar, Context context, String str2, GrsBaseInfo grsBaseInfo, com.huawei.hms.framework.network.grs.e.c cVar2) {
        this.f7618b = str;
        this.f7619c = cVar;
        this.f7620d = i2;
        this.f7621e = context;
        this.f7622f = str2;
        this.f7623g = grsBaseInfo;
        this.f7624h = cVar2;
    }

    private String a(String str) {
        return Uri.parse(str).getPath();
    }

    private EnumC0181a h() {
        if (this.f7618b.isEmpty()) {
            return EnumC0181a.GRSDEFAULT;
        }
        String strA = a(this.f7618b);
        return strA.contains("1.0") ? EnumC0181a.GRSGET : strA.contains(WeiboSsoSdk.SDK_VERSION_CODE) ? EnumC0181a.GRSPOST : EnumC0181a.GRSDEFAULT;
    }

    public Context a() {
        return this.f7621e;
    }

    public c b() {
        return this.f7619c;
    }

    public String c() {
        return this.f7618b;
    }

    public int d() {
        return this.f7620d;
    }

    public String e() {
        return this.f7622f;
    }

    public com.huawei.hms.framework.network.grs.e.c f() {
        return this.f7624h;
    }

    public Callable<d> g() {
        if (EnumC0181a.GRSDEFAULT.equals(h())) {
            return null;
        }
        return EnumC0181a.GRSGET.equals(h()) ? new f(this.f7618b, this.f7620d, this.f7619c, this.f7621e, this.f7622f, this.f7623g) : new g(this.f7618b, this.f7620d, this.f7619c, this.f7621e, this.f7622f, this.f7623g, this.f7624h);
    }
}
