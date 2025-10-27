package com.tencent.liteav.beauty;

import android.graphics.Bitmap;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;

/* loaded from: classes6.dex */
public class b implements TXBeautyManager {

    /* renamed from: a, reason: collision with root package name */
    private com.tencent.liteav.basic.license.e f18792a;

    /* renamed from: b, reason: collision with root package name */
    private d f18793b;

    /* renamed from: c, reason: collision with root package name */
    private int f18794c;

    /* renamed from: e, reason: collision with root package name */
    private boolean f18796e = true;

    /* renamed from: d, reason: collision with root package name */
    private a f18795d = new a();

    public class a {
        public Bitmap A;
        public float B;
        public String C;

        /* renamed from: a, reason: collision with root package name */
        public int f18797a;

        /* renamed from: b, reason: collision with root package name */
        public int f18798b;

        /* renamed from: c, reason: collision with root package name */
        public int f18799c;

        /* renamed from: d, reason: collision with root package name */
        public int f18800d;

        /* renamed from: e, reason: collision with root package name */
        public int f18801e;

        /* renamed from: f, reason: collision with root package name */
        public int f18802f;

        /* renamed from: g, reason: collision with root package name */
        public int f18803g;

        /* renamed from: h, reason: collision with root package name */
        public int f18804h;

        /* renamed from: i, reason: collision with root package name */
        public int f18805i;

        /* renamed from: j, reason: collision with root package name */
        public int f18806j;

        /* renamed from: k, reason: collision with root package name */
        public int f18807k;

        /* renamed from: l, reason: collision with root package name */
        public int f18808l;

        /* renamed from: m, reason: collision with root package name */
        public int f18809m;

        /* renamed from: n, reason: collision with root package name */
        public int f18810n;

        /* renamed from: o, reason: collision with root package name */
        public int f18811o;

        /* renamed from: p, reason: collision with root package name */
        public int f18812p;

        /* renamed from: q, reason: collision with root package name */
        public int f18813q;

        /* renamed from: r, reason: collision with root package name */
        public int f18814r;

        /* renamed from: s, reason: collision with root package name */
        public int f18815s;

        /* renamed from: t, reason: collision with root package name */
        public int f18816t;

        /* renamed from: u, reason: collision with root package name */
        public int f18817u;

        /* renamed from: v, reason: collision with root package name */
        public int f18818v;

        /* renamed from: w, reason: collision with root package name */
        public int f18819w;

        /* renamed from: x, reason: collision with root package name */
        public int f18820x;

        /* renamed from: y, reason: collision with root package name */
        public String f18821y;

        /* renamed from: z, reason: collision with root package name */
        public boolean f18822z;

        public a() {
        }
    }

    public b(com.tencent.liteav.basic.license.e eVar) {
        this.f18792a = eVar;
        enableSharpnessEnhancement(true);
        setFilterStrength(0.5f);
    }

    private void a() {
        TXCLog.d("TXBeautyManager", "applyBeautyParams");
        this.f18793b.b(this.f18794c);
        b(this.f18796e);
        if (this.f18792a.a()) {
            this.f18793b.g(this.f18795d.f18801e);
            this.f18793b.h(this.f18795d.f18802f);
            this.f18793b.i(this.f18795d.f18803g);
            this.f18793b.j(this.f18795d.f18804h);
            this.f18793b.l(this.f18795d.f18805i);
            this.f18793b.k(this.f18795d.f18806j);
            this.f18793b.m(this.f18795d.f18807k);
            this.f18793b.n(this.f18795d.f18808l);
            this.f18793b.o(this.f18795d.f18809m);
            this.f18793b.p(this.f18795d.f18810n);
            this.f18793b.q(this.f18795d.f18811o);
            this.f18793b.r(this.f18795d.f18812p);
            this.f18793b.s(this.f18795d.f18813q);
            this.f18793b.t(this.f18795d.f18814r);
            this.f18793b.u(this.f18795d.f18815s);
            this.f18793b.v(this.f18795d.f18816t);
            this.f18793b.w(this.f18795d.f18817u);
            this.f18793b.x(this.f18795d.f18818v);
            this.f18793b.y(this.f18795d.f18819w);
            this.f18793b.z(this.f18795d.f18820x);
            this.f18793b.a(this.f18795d.C, true);
        }
        this.f18793b.a(this.f18795d.A);
        this.f18793b.a(this.f18795d.B);
        this.f18793b.a(this.f18795d.f18821y);
        this.f18793b.c(this.f18795d.f18822z);
    }

    private void b(boolean z2) {
        if (z2) {
            this.f18793b.c(this.f18795d.f18797a);
            this.f18793b.d(this.f18795d.f18798b);
            this.f18793b.e(this.f18795d.f18799c);
            this.f18793b.f(this.f18795d.f18800d);
            return;
        }
        this.f18793b.c(0);
        this.f18793b.d(0);
        this.f18793b.e(0);
        this.f18793b.f(0);
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public void enableSharpnessEnhancement(boolean z2) {
        TXCLog.d("TXBeautyManager", "enableSharpnessEnhancement enable: %b", Boolean.valueOf(z2));
        a aVar = this.f18795d;
        int i2 = z2 ? 4 : 0;
        aVar.f18800d = i2;
        d dVar = this.f18793b;
        if (dVar == null || !this.f18796e) {
            return;
        }
        dVar.f(i2);
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public void setBeautyLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setBeautyLevel beautyLevel:" + f2);
        int i2 = (int) f2;
        this.f18795d.f18797a = i2;
        d dVar = this.f18793b;
        if (dVar == null || !this.f18796e) {
            return;
        }
        dVar.c(i2);
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public void setBeautyStyle(int i2) {
        TXCLog.d("TXBeautyManager", "setBeautyStyle beautyStyle:" + i2);
        this.f18794c = i2;
        d dVar = this.f18793b;
        if (dVar != null) {
            dVar.b(i2);
        }
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setChinLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setChinLevel chinLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setChinLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18805i = a(f2, 15);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.l(this.f18795d.f18805i);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setEyeAngleLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setEyeAngleLevel eyeAngleLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setEyeAngleLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18815s = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.u(this.f18795d.f18815s);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setEyeDistanceLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setEyeDistanceLevel eyeDistanceLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setEyeDistanceLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18814r = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.t(this.f18795d.f18814r);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setEyeLightenLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setEyeLightenLevel eyeLightenLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setEyeLightenLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18808l = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.n(this.f18795d.f18808l);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setEyeScaleLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setEyeScaleLevel eyeScaleLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setEyeScaleLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18801e = a(f2, 15);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.g(this.f18795d.f18801e);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setFaceBeautyLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setFaceBeautyLevel faceBeautyLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setFaceBeautyLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18820x = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.z(this.f18795d.f18820x);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setFaceNarrowLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setFaceNarrowLevel faceNarrowLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setFaceNarrowLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18803g = a(f2, 15);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.i(this.f18795d.f18803g);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setFaceShortLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setFaceShortLevel faceShortLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setFaceShortLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18806j = a(f2, 15);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.k(this.f18795d.f18806j);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setFaceSlimLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setFaceSlimLevel faceSlimLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setFaceSlimLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18802f = a(f2, 15);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.h(this.f18795d.f18802f);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setFaceVLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setFaceVLevel faceVLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setFaceVLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18804h = a(f2, 15);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.j(this.f18795d.f18804h);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public void setFilter(Bitmap bitmap) {
        TXCLog.d("TXBeautyManager", "setFilter image:" + bitmap);
        this.f18795d.A = bitmap;
        d dVar = this.f18793b;
        if (dVar != null) {
            dVar.a(bitmap);
        }
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public void setFilterStrength(float f2) {
        TXCLog.d("TXBeautyManager", "setFilterStrength strength:" + f2);
        this.f18795d.B = f2;
        d dVar = this.f18793b;
        if (dVar != null) {
            dVar.a(f2);
        }
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setForeheadLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setForeheadLevel foreheadLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setForeheadLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18813q = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.s(this.f18795d.f18813q);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setGreenScreenFile(String str) {
        TXCLog.d("TXBeautyManager", "setGreenScreenFile path:" + str);
        if (TXCBuild.VersionInt() < 18 || !this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setGreenScreenFile failed! license feature not support, android sdk version: " + TXCBuild.VersionInt());
            return -5;
        }
        this.f18795d.C = str;
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.a(str, true);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setLipsThicknessLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setLipsThicknessLevel lipsThicknessLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setLipsThicknessLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18819w = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.y(this.f18795d.f18819w);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public void setMotionMute(boolean z2) {
        TXCLog.d("TXBeautyManager", "setMotionMute motionMute:" + z2);
        this.f18795d.f18822z = z2;
        d dVar = this.f18793b;
        if (dVar != null) {
            dVar.c(z2);
        }
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public void setMotionTmpl(String str) {
        TXCLog.d("TXBeautyManager", "setMotionTmpl tmplPath:" + str);
        this.f18795d.f18821y = str;
        d dVar = this.f18793b;
        if (dVar != null) {
            dVar.a(str);
        }
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setMouthShapeLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setMouthShapeLevel mouthShapeLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setMouthShapeLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18816t = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.v(this.f18795d.f18816t);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setNosePositionLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setNosePositionLevel nosePositionLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setNosePositionLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18818v = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.x(this.f18795d.f18818v);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setNoseSlimLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setNoseSlimLevel noseSlimLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setNoseSlimLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18807k = a(f2, 15);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.m(this.f18795d.f18807k);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setNoseWingLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setNoseWingLevel noseWingLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setNoseWingLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18817u = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.w(this.f18795d.f18817u);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setPounchRemoveLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setPounchRemoveLevel pounchRemoveLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setPounchRemoveLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18811o = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.q(this.f18795d.f18811o);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public void setPreprocessor(d dVar) {
        this.f18793b = dVar;
        if (dVar != null) {
            a();
        }
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public void setRuddyLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setRuddyLevel ruddyLevel:" + f2);
        int i2 = (int) f2;
        this.f18795d.f18799c = i2;
        d dVar = this.f18793b;
        if (dVar == null || !this.f18796e) {
            return;
        }
        dVar.e(i2);
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setSmileLinesRemoveLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setSmileLinesRemoveLevel smileLinesRemoveLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setSmileLinesRemoveLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18812p = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.r(this.f18795d.f18812p);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setToothWhitenLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setToothWhitenLevel toothWhitenLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setToothWhitenLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18809m = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.o(this.f18795d.f18809m);
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public void setWhitenessLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setWhitenessLevel whitenessLevel:" + f2);
        int i2 = (int) f2;
        this.f18795d.f18798b = i2;
        d dVar = this.f18793b;
        if (dVar == null || !this.f18796e) {
            return;
        }
        dVar.d(i2);
    }

    @Override // com.tencent.liteav.beauty.TXBeautyManager
    public int setWrinkleRemoveLevel(float f2) {
        TXCLog.d("TXBeautyManager", "setWrinkleRemoveLevel wrinkleRemoveLevel:" + f2);
        if (!this.f18792a.a()) {
            TXCLog.e("TXBeautyManager", "setWrinkleRemoveLevel failed! license feature not support");
            return -5;
        }
        this.f18795d.f18810n = a(f2, 10);
        d dVar = this.f18793b;
        if (dVar == null) {
            return 0;
        }
        dVar.p(this.f18795d.f18810n);
        return 0;
    }

    public void a(boolean z2) {
        this.f18796e = z2;
        b(z2);
    }

    private int a(float f2, int i2) {
        return Math.round(f2 * i2);
    }
}
