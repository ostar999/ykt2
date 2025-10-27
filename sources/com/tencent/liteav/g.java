package com.tencent.liteav;

import android.graphics.Bitmap;
import com.yikaobang.yixue.R2;
import org.json.JSONArray;

/* loaded from: classes6.dex */
public class g implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    public int f19327a = 0;

    /* renamed from: b, reason: collision with root package name */
    public int f19328b = 0;

    /* renamed from: c, reason: collision with root package name */
    public int f19329c = 0;

    /* renamed from: d, reason: collision with root package name */
    public int f19330d = 0;

    /* renamed from: e, reason: collision with root package name */
    public int f19331e = 1200;

    /* renamed from: f, reason: collision with root package name */
    public int f19332f = 1500;

    /* renamed from: g, reason: collision with root package name */
    public int f19333g = 800;

    /* renamed from: h, reason: collision with root package name */
    public int f19334h = 5;

    /* renamed from: i, reason: collision with root package name */
    public boolean f19335i = true;

    /* renamed from: j, reason: collision with root package name */
    public boolean f19336j = false;

    /* renamed from: k, reason: collision with root package name */
    public int f19337k = 20;

    /* renamed from: l, reason: collision with root package name */
    public int f19338l = 1;

    /* renamed from: m, reason: collision with root package name */
    public int f19339m = 2;

    /* renamed from: n, reason: collision with root package name */
    public com.tencent.liteav.basic.enums.c f19340n = com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_540_960;

    /* renamed from: o, reason: collision with root package name */
    public int f19341o = 1;

    /* renamed from: p, reason: collision with root package name */
    public boolean f19342p = true;

    /* renamed from: q, reason: collision with root package name */
    public int f19343q = 3;

    /* renamed from: r, reason: collision with root package name */
    public int f19344r = 0;

    /* renamed from: s, reason: collision with root package name */
    public boolean f19345s = false;

    /* renamed from: t, reason: collision with root package name */
    public int f19346t = 3;

    /* renamed from: u, reason: collision with root package name */
    public int f19347u = 3;

    /* renamed from: v, reason: collision with root package name */
    public int f19348v = 48000;

    /* renamed from: w, reason: collision with root package name */
    public int f19349w = 1;

    /* renamed from: x, reason: collision with root package name */
    public boolean f19350x = true;

    /* renamed from: y, reason: collision with root package name */
    public boolean f19351y = false;

    /* renamed from: z, reason: collision with root package name */
    public boolean f19352z = false;
    public int A = 0;
    public int B = 10;
    public boolean C = false;
    public Bitmap D = null;
    public int E = 300;
    public int F = 10;
    public int G = 1;
    public Bitmap H = null;
    public int I = 0;
    public int J = 0;
    public float K = 0.0f;
    public float L = 0.0f;
    public float M = -1.0f;
    public boolean N = true;
    public boolean O = false;
    public boolean P = false;
    public boolean Q = true;
    public int R = 1;
    public boolean S = false;
    public boolean T = false;
    public int U = 0;
    public boolean V = false;
    public boolean W = true;
    public boolean X = false;
    public boolean Y = false;
    public boolean Z = false;
    public int aa = 0;
    public JSONArray ab = null;

    /* renamed from: com.tencent.liteav.g$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f19353a;

        static {
            int[] iArr = new int[com.tencent.liteav.basic.enums.c.values().length];
            f19353a = iArr;
            try {
                iArr[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_360_640.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_540_960.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_720_1280.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_320_480.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_180_320.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_270_480.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_240_320.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_360_480.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_480_640.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_480_480.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_270_270.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_160_160.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_640_360.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_960_540.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_1280_720.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_640_480.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_480_360.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_320_240.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_480_270.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_320_180.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_120_120.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_1080_1920.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                f19353a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_1920_1080.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
        }
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public int f19354a = 0;

        /* renamed from: b, reason: collision with root package name */
        public int f19355b = 0;
    }

    public static a a(com.tencent.liteav.basic.enums.c cVar) {
        a aVar = new a();
        switch (AnonymousClass1.f19353a[cVar.ordinal()]) {
            case 1:
                aVar.f19354a = R2.attr.arcStrokeCap;
                aVar.f19355b = 640;
                return aVar;
            case 2:
                aVar.f19354a = R2.attr.bl_checked_gradient_type;
                aVar.f19355b = 960;
                return aVar;
            case 3:
                aVar.f19354a = 720;
                aVar.f19355b = 1280;
                return aVar;
            case 4:
                aVar.f19354a = 320;
                aVar.f19355b = 480;
                return aVar;
            case 5:
                aVar.f19354a = 192;
                aVar.f19355b = 320;
                return aVar;
            case 6:
                aVar.f19354a = R2.attr.adScopeTextColor;
                aVar.f19355b = 480;
                return aVar;
            case 7:
                aVar.f19354a = 240;
                aVar.f19355b = 320;
                return aVar;
            case 8:
                aVar.f19354a = R2.attr.arcStrokeCap;
                aVar.f19355b = 480;
                return aVar;
            case 9:
                aVar.f19354a = 480;
                aVar.f19355b = 640;
                return aVar;
            case 10:
                aVar.f19354a = 480;
                aVar.f19355b = 480;
                return aVar;
            case 11:
                aVar.f19354a = R2.attr.adScopeTextColor;
                aVar.f19355b = R2.attr.adScopeTextColor;
                return aVar;
            case 12:
                aVar.f19354a = 160;
                aVar.f19355b = 160;
                return aVar;
            case 13:
                aVar.f19354a = 640;
                aVar.f19355b = R2.attr.arcStrokeCap;
                return aVar;
            case 14:
                aVar.f19354a = 960;
                aVar.f19355b = R2.attr.bl_checked_gradient_type;
                return aVar;
            case 15:
                aVar.f19354a = 1280;
                aVar.f19355b = 720;
                return aVar;
            case 16:
                aVar.f19354a = 640;
                aVar.f19355b = 480;
                return aVar;
            case 17:
                aVar.f19354a = 480;
                aVar.f19355b = R2.attr.arcStrokeCap;
                return aVar;
            case 18:
                aVar.f19354a = 320;
                aVar.f19355b = 240;
                return aVar;
            case 19:
                aVar.f19354a = 480;
                aVar.f19355b = R2.attr.adScopeTextColor;
                return aVar;
            case 20:
                aVar.f19354a = 320;
                aVar.f19355b = 192;
                return aVar;
            case 21:
                aVar.f19354a = 128;
                aVar.f19355b = 128;
                return aVar;
            case 22:
                aVar.f19354a = R2.attr.columnOrderPreserved;
                aVar.f19355b = R2.attr.iconTint;
                return aVar;
            case 23:
                aVar.f19354a = R2.attr.iconTint;
                aVar.f19355b = R2.attr.columnOrderPreserved;
                return aVar;
            default:
                aVar.f19354a = R2.attr.arcStrokeCap;
                aVar.f19355b = 640;
                return aVar;
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return (g) super.clone();
    }

    public boolean a() {
        com.tencent.liteav.basic.enums.c cVar = this.f19340n;
        if (cVar != com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_INVALID) {
            a aVarA = a(cVar);
            this.f19327a = aVarA.f19354a;
            this.f19328b = aVarA.f19355b;
        }
        return this.f19327a > this.f19328b;
    }
}
