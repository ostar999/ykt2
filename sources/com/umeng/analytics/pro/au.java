package com.umeng.analytics.pro;

/* loaded from: classes6.dex */
public class au extends bb {

    /* renamed from: a, reason: collision with root package name */
    public static final int f22531a = 0;

    /* renamed from: b, reason: collision with root package name */
    public static final int f22532b = 1;

    /* renamed from: c, reason: collision with root package name */
    public static final int f22533c = 2;

    /* renamed from: d, reason: collision with root package name */
    public static final int f22534d = 3;

    /* renamed from: e, reason: collision with root package name */
    public static final int f22535e = 4;

    /* renamed from: f, reason: collision with root package name */
    public static final int f22536f = 5;

    /* renamed from: g, reason: collision with root package name */
    public static final int f22537g = 6;

    /* renamed from: h, reason: collision with root package name */
    public static final int f22538h = 7;

    /* renamed from: j, reason: collision with root package name */
    private static final bz f22539j = new bz("TApplicationException");

    /* renamed from: k, reason: collision with root package name */
    private static final bp f22540k = new bp("message", (byte) 11, 1);

    /* renamed from: l, reason: collision with root package name */
    private static final bp f22541l = new bp("type", (byte) 8, 2);

    /* renamed from: m, reason: collision with root package name */
    private static final long f22542m = 1;

    /* renamed from: i, reason: collision with root package name */
    protected int f22543i;

    public au() {
        this.f22543i = 0;
    }

    public int a() {
        return this.f22543i;
    }

    public void b(bu buVar) throws bb {
        buVar.a(f22539j);
        if (getMessage() != null) {
            buVar.a(f22540k);
            buVar.a(getMessage());
            buVar.c();
        }
        buVar.a(f22541l);
        buVar.a(this.f22543i);
        buVar.c();
        buVar.d();
        buVar.b();
    }

    public static au a(bu buVar) throws bb {
        buVar.j();
        String strZ = null;
        int iW = 0;
        while (true) {
            bp bpVarL = buVar.l();
            byte b3 = bpVarL.f22625b;
            if (b3 == 0) {
                buVar.k();
                return new au(iW, strZ);
            }
            short s2 = bpVarL.f22626c;
            if (s2 != 1) {
                if (s2 != 2) {
                    bx.a(buVar, b3);
                } else if (b3 == 8) {
                    iW = buVar.w();
                } else {
                    bx.a(buVar, b3);
                }
            } else if (b3 == 11) {
                strZ = buVar.z();
            } else {
                bx.a(buVar, b3);
            }
            buVar.m();
        }
    }

    public au(int i2) {
        this.f22543i = i2;
    }

    public au(int i2, String str) {
        super(str);
        this.f22543i = i2;
    }

    public au(String str) {
        super(str);
        this.f22543i = 0;
    }
}
