package com.tencent.liteav.beauty;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.ExoPlayer;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.TXCOpenGlUtils;
import com.tencent.liteav.basic.util.TXCBuild;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class d extends com.tencent.liteav.basic.module.a implements com.tencent.liteav.beauty.e {

    /* renamed from: a, reason: collision with root package name */
    protected Context f19076a;

    /* renamed from: b, reason: collision with root package name */
    protected boolean f19077b;

    /* renamed from: h, reason: collision with root package name */
    protected com.tencent.liteav.beauty.c f19083h;

    /* renamed from: k, reason: collision with root package name */
    com.tencent.liteav.beauty.f f19086k;

    /* renamed from: p, reason: collision with root package name */
    private Object f19091p;

    /* renamed from: c, reason: collision with root package name */
    protected boolean f19078c = false;

    /* renamed from: d, reason: collision with root package name */
    protected int f19079d = 0;

    /* renamed from: e, reason: collision with root package name */
    protected int f19080e = 0;

    /* renamed from: f, reason: collision with root package name */
    protected int f19081f = 1;

    /* renamed from: g, reason: collision with root package name */
    protected com.tencent.liteav.basic.opengl.a f19082g = null;

    /* renamed from: i, reason: collision with root package name */
    protected b f19084i = new b();

    /* renamed from: j, reason: collision with root package name */
    protected c f19085j = null;

    /* renamed from: l, reason: collision with root package name */
    private EnumC0332d f19087l = EnumC0332d.MODE_THRESHOLD;

    /* renamed from: m, reason: collision with root package name */
    private long f19088m = 0;

    /* renamed from: n, reason: collision with root package name */
    private long f19089n = 0;

    /* renamed from: o, reason: collision with root package name */
    private long f19090o = 0;

    /* renamed from: q, reason: collision with root package name */
    private a f19092q = new a(this);

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        int f19095a;

        /* renamed from: b, reason: collision with root package name */
        int f19096b;

        /* renamed from: c, reason: collision with root package name */
        int f19097c;

        /* renamed from: d, reason: collision with root package name */
        int f19098d;

        /* renamed from: e, reason: collision with root package name */
        int f19099e;

        /* renamed from: f, reason: collision with root package name */
        int f19100f;

        /* renamed from: g, reason: collision with root package name */
        int f19101g;

        /* renamed from: h, reason: collision with root package name */
        int f19102h;

        /* renamed from: i, reason: collision with root package name */
        boolean f19103i;

        /* renamed from: j, reason: collision with root package name */
        boolean f19104j;

        /* renamed from: k, reason: collision with root package name */
        public int f19105k = 5;

        /* renamed from: l, reason: collision with root package name */
        public int f19106l = 0;

        /* renamed from: m, reason: collision with root package name */
        com.tencent.liteav.basic.opengl.a f19107m = null;
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        public boolean f19108a;

        /* renamed from: b, reason: collision with root package name */
        public int f19109b;

        /* renamed from: c, reason: collision with root package name */
        public int f19110c;

        /* renamed from: d, reason: collision with root package name */
        public int f19111d;

        /* renamed from: e, reason: collision with root package name */
        public boolean f19112e;

        /* renamed from: f, reason: collision with root package name */
        public int f19113f;

        /* renamed from: g, reason: collision with root package name */
        public int f19114g;

        /* renamed from: h, reason: collision with root package name */
        public int f19115h;

        /* renamed from: i, reason: collision with root package name */
        public int f19116i;

        /* renamed from: j, reason: collision with root package name */
        public com.tencent.liteav.basic.opengl.a f19117j;

        private c() {
            this.f19112e = false;
            this.f19115h = 5;
            this.f19116i = 0;
            this.f19117j = null;
        }
    }

    /* renamed from: com.tencent.liteav.beauty.d$d, reason: collision with other inner class name */
    public enum EnumC0332d {
        MODE_SAME_AS_OUTPUT,
        MODE_SAME_AS_INPUT,
        MODE_THRESHOLD
    }

    public static class e {

        /* renamed from: a, reason: collision with root package name */
        public g f19122a = g.TXE_FILL_MODE_SCALL_ASPECT_FILL;

        /* renamed from: b, reason: collision with root package name */
        public boolean f19123b = false;
    }

    public static class f {

        /* renamed from: a, reason: collision with root package name */
        public Bitmap f19124a;

        /* renamed from: b, reason: collision with root package name */
        public float f19125b;

        /* renamed from: c, reason: collision with root package name */
        public float f19126c;

        /* renamed from: d, reason: collision with root package name */
        public float f19127d;
    }

    public enum g {
        TXE_FILL_MODE_SCALL_TO_FILL,
        TXE_FILL_MODE_SCALL_ASPECT_FILL
    }

    public d(Context context, boolean z2) {
        this.f19077b = true;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        TXCLog.i("TXCVideoPreprocessor", "TXCVideoPreprocessor version: VideoPreprocessor-v1.1");
        ConfigurationInfo deviceConfigurationInfo = activityManager.getDeviceConfigurationInfo();
        if (deviceConfigurationInfo != null) {
            TXCLog.i("TXCVideoPreprocessor", "opengl es version " + deviceConfigurationInfo.reqGlEsVersion);
            TXCLog.i("TXCVideoPreprocessor", "set GLContext " + z2);
            if (deviceConfigurationInfo.reqGlEsVersion > 131072) {
                TXCLog.i("TXCVideoPreprocessor", "This devices is OpenGlUtils.OPENGL_ES_3");
                TXCOpenGlUtils.a(3);
            } else {
                TXCLog.i("TXCVideoPreprocessor", "This devices is OpenGlUtils.OPENGL_ES_2");
                TXCOpenGlUtils.a(2);
            }
        } else {
            TXCLog.e("TXCVideoPreprocessor", "getDeviceConfigurationInfo opengl Info failed!");
        }
        this.f19076a = context;
        this.f19077b = z2;
        this.f19083h = new com.tencent.liteav.beauty.c(this.f19076a, this.f19077b);
        com.tencent.liteav.beauty.a.a().a(context);
    }

    private int A(int i2) {
        if (i2 == 1) {
            return 90;
        }
        if (i2 == 2) {
            return 180;
        }
        if (i2 != 3) {
            return i2;
        }
        return 270;
    }

    private void c() {
        if (this.f19088m != 0) {
            setStatusValue(3002, Long.valueOf(System.currentTimeMillis() - this.f19088m));
        }
        this.f19089n++;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis > ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS + this.f19090o) {
            setStatusValue(3003, Double.valueOf((this.f19089n * 1000.0d) / (jCurrentTimeMillis - r4)));
            this.f19089n = 0L;
            this.f19090o = jCurrentTimeMillis;
        }
    }

    @Override // com.tencent.liteav.beauty.e
    public int a(int i2, int i3, int i4) {
        if (this.f19086k == null) {
            return 0;
        }
        com.tencent.liteav.basic.structs.b bVar = new com.tencent.liteav.basic.structs.b();
        bVar.f18656e = i3;
        bVar.f18657f = i4;
        bVar.f18661j = 0;
        c cVar = this.f19085j;
        bVar.f18660i = cVar != null ? cVar.f19112e : false;
        bVar.f18652a = i2;
        return this.f19086k.a(bVar);
    }

    public synchronized void b(boolean z2) {
        this.f19078c = z2;
    }

    public synchronized void d(int i2) {
        try {
            if (i2 > 9) {
                TXCLog.e("TXCVideoPreprocessor", "Brightness value too large! set max value 9");
                i2 = 9;
            } else if (i2 < 0) {
                TXCLog.e("TXCVideoPreprocessor", "Brightness < 0; set 0");
                i2 = 0;
            }
            com.tencent.liteav.beauty.c cVar = this.f19083h;
            if (cVar != null) {
                cVar.e(i2);
            }
            this.f19092q.a("whiteLevel", i2);
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void e(int i2) {
        try {
            if (i2 > 9) {
                TXCLog.e("TXCVideoPreprocessor", "Ruddy value too large! set max value 9");
                i2 = 9;
            } else if (i2 < 0) {
                TXCLog.e("TXCVideoPreprocessor", "Ruddy < 0; set 0");
                i2 = 0;
            }
            com.tencent.liteav.beauty.c cVar = this.f19083h;
            if (cVar != null) {
                cVar.g(i2);
            }
            this.f19092q.a("ruddyLevel", i2);
        } catch (Throwable th) {
            throw th;
        }
    }

    public void f(int i2) {
        if (i2 > 9) {
            TXCLog.e("TXCVideoPreprocessor", "Brightness value too large! set max value 9");
            i2 = 9;
        } else if (i2 < 0) {
            TXCLog.e("TXCVideoPreprocessor", "Brightness < 0; set 0");
            i2 = 0;
        }
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.f(i2);
        }
    }

    public synchronized void g(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.h(i2);
        }
        this.f19092q.a("eyeBigScale", i2);
    }

    public synchronized void h(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.j(i2);
        }
        this.f19092q.a("faceSlimLevel", i2);
    }

    public synchronized void i(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.k(i2);
        }
        this.f19092q.a("faceNarrowLevel", i2);
    }

    public void j(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.i(i2);
        }
        this.f19092q.a("faceVLevel", i2);
    }

    public void k(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.l(i2);
        }
        this.f19092q.a("faceShortLevel", i2);
    }

    public void l(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.m(i2);
        }
        this.f19092q.a("chinLevel", i2);
    }

    public void m(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.n(i2);
        }
        this.f19092q.a("noseSlimLevel", i2);
    }

    public void n(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.o(i2);
        }
        this.f19092q.a("eyeLightenLevel", i2);
    }

    public void o(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.p(i2);
        }
        this.f19092q.a("toothWhitenLevel", i2);
    }

    public void p(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.q(i2);
        }
        this.f19092q.a("wrinkleRemoveLevel", i2);
    }

    public void q(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.r(i2);
        }
        this.f19092q.a("pounchRemoveLevel", i2);
    }

    public void r(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.s(i2);
        }
        this.f19092q.a("smileLinesRemoveLevel", i2);
    }

    public void s(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.t(i2);
        }
        this.f19092q.a("foreheadLevel", i2);
    }

    @Override // com.tencent.liteav.basic.module.a
    public void setID(String str) {
        super.setID(str);
        setStatusValue(3001, this.f19092q.a());
    }

    public void t(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.u(i2);
        }
        this.f19092q.a("eyeDistanceLevel", i2);
    }

    public void u(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.v(i2);
        }
        this.f19092q.a("eyeAngleLevel", i2);
    }

    public void v(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.w(i2);
        }
        this.f19092q.a("mouthShapeLevel", i2);
    }

    public void w(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.x(i2);
        }
        this.f19092q.a("noseWingLevel", i2);
    }

    public void x(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.y(i2);
        }
        this.f19092q.a("nosePositionLevel", i2);
    }

    public void y(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.z(i2);
        }
        this.f19092q.a("lipsThicknessLevel", i2);
    }

    public void z(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.A(i2);
        }
        this.f19092q.a("faceBeautyLevel", i2);
    }

    private com.tencent.liteav.basic.util.e b(int i2, int i3, int i4, int i5, int i6) {
        if (i4 == 90 || i4 == 270) {
            i6 = i5;
            i5 = i6;
        }
        int iMin = Math.min(i5, i6);
        int iMin2 = Math.min(i2, i3);
        int[] iArr = {720, R2.attr.color_hot_circle_one_end, 1280};
        for (int i7 = 0; i7 < 3; i7++) {
            int i8 = iArr[i7];
            if (iMin <= i8 && iMin2 >= i8) {
                float f2 = (i8 * 1.0f) / iMin;
                return new com.tencent.liteav.basic.util.e((int) (i5 * f2), (int) (f2 * i6));
            }
        }
        return new com.tencent.liteav.basic.util.e(i2, i3);
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        WeakReference<d> f19093a;

        /* renamed from: b, reason: collision with root package name */
        private HashMap<String, String> f19094b = new HashMap<>();

        public a(d dVar) {
            this.f19093a = new WeakReference<>(dVar);
        }

        public void a(String str, int i2) {
            String id;
            this.f19094b.put(str, String.valueOf(i2));
            d dVar = this.f19093a.get();
            if (dVar == null || (id = dVar.getID()) == null || id.length() <= 0) {
                return;
            }
            dVar.setStatusValue(3001, a());
        }

        public String a() {
            String str = "";
            for (String str2 : this.f19094b.keySet()) {
                str = str + str2 + ":" + this.f19094b.get(str2) + " ";
            }
            return StrPool.DELIM_START + str + "}";
        }
    }

    @Override // com.tencent.liteav.beauty.e
    public void a(int i2, int i3, int i4, long j2) {
        c();
        if (this.f19086k != null) {
            com.tencent.liteav.basic.structs.b bVar = new com.tencent.liteav.basic.structs.b();
            bVar.f18656e = i3;
            bVar.f18657f = i4;
            bVar.f18661j = 0;
            c cVar = this.f19085j;
            bVar.f18660i = cVar != null ? cVar.f19112e : false;
            bVar.f18652a = i2;
            this.f19086k.a(bVar, j2);
        }
    }

    public synchronized void b() {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.a();
        }
        this.f19085j = null;
    }

    public synchronized void c(int i2) {
        try {
            if (i2 > 9) {
                TXCLog.e("TXCVideoPreprocessor", "Beauty value too large! set max value 9");
                i2 = 9;
            } else if (i2 < 0) {
                TXCLog.e("TXCVideoPreprocessor", "Beauty < 0; set 0");
                i2 = 0;
            }
            com.tencent.liteav.beauty.c cVar = this.f19083h;
            if (cVar != null) {
                cVar.c(i2);
            }
            this.f19092q.a("beautyLevel", i2);
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void b(int i2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.d(i2);
        }
        this.f19092q.a("beautyStyle", i2);
    }

    public synchronized void c(boolean z2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.b(z2);
        }
    }

    @Override // com.tencent.liteav.beauty.e
    public void a(byte[] bArr, int i2, int i3, int i4, long j2) {
        com.tencent.liteav.beauty.f fVar = this.f19086k;
        if (fVar != null) {
            fVar.a(bArr, i2, i3, i4, j2);
        }
    }

    public void a(float[] fArr) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.a(fArr);
        }
    }

    public void a(boolean z2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.a(z2);
        }
    }

    public synchronized int a(byte[] bArr, int i2, int i3, int i4, int i5, int i6) {
        a(i2, i3, A(i4), i5, i6);
        this.f19083h.b(this.f19084i);
        return this.f19083h.a(bArr, i5);
    }

    public synchronized int a(int i2, int i3, int i4, int i5, int i6, int i7, long j2) {
        a(i3, i4, A(i5), i6, i7);
        this.f19083h.b(this.f19084i);
        return this.f19083h.a(i2, i6, j2);
    }

    public synchronized int a(com.tencent.liteav.basic.structs.b bVar, int i2, int i3, long j2) {
        this.f19088m = System.currentTimeMillis();
        a(bVar.f18663l);
        a(bVar.f18658g, bVar.f18659h);
        b(bVar.f18660i);
        a(bVar.f18654c);
        a(bVar.f18655d);
        byte[] bArr = bVar.f18664m;
        if (bArr != null && bVar.f18652a == -1) {
            return a(bArr, bVar.f18656e, bVar.f18657f, bVar.f18661j, i2, i3);
        }
        return a(bVar.f18652a, bVar.f18656e, bVar.f18657f, bVar.f18661j, i2, i3, j2);
    }

    public synchronized void a(EnumC0332d enumC0332d) {
        this.f19087l = enumC0332d;
        TXCLog.i("TXCVideoPreprocessor", "set Process SDK performance " + enumC0332d);
    }

    public synchronized void a(com.tencent.liteav.basic.opengl.a aVar) {
        this.f19082g = aVar;
    }

    public synchronized void a(int i2, int i3) {
        this.f19079d = i2;
        this.f19080e = i3;
    }

    public synchronized void a(Bitmap bitmap, float f2, float f3, float f4) {
        if (f2 >= 0.0f && f3 >= 0.0f && f4 >= 0.0d) {
            com.tencent.liteav.beauty.c cVar = this.f19083h;
            if (cVar != null) {
                cVar.a(bitmap, f2, f3, f4);
            }
            return;
        }
        TXCLog.e("TXCVideoPreprocessor", "WaterMark param is Error!");
    }

    public synchronized void a(Object obj) {
        this.f19091p = obj;
    }

    public synchronized Object a() {
        return this.f19091p;
    }

    public synchronized void a(com.tencent.liteav.beauty.f fVar) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar == null) {
            TXCLog.e("TXCVideoPreprocessor", "setListener mDrawer is null!");
            return;
        }
        this.f19086k = fVar;
        if (fVar == null) {
            cVar.a((com.tencent.liteav.beauty.e) null);
        } else {
            cVar.a(this);
        }
    }

    public synchronized void a(com.tencent.liteav.basic.b.b bVar) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar == null) {
            TXCLog.e("TXCVideoPreprocessor", "setListener mDrawer is null!");
        } else {
            cVar.a(bVar);
        }
    }

    private boolean a(int i2, int i3, int i4, int i5, int i6) {
        int i7;
        int i8;
        com.tencent.liteav.basic.opengl.a aVar;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        com.tencent.liteav.basic.opengl.a aVar2;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        com.tencent.liteav.basic.opengl.a aVar3;
        com.tencent.liteav.basic.opengl.a aVar4;
        com.tencent.liteav.basic.opengl.a aVar5;
        com.tencent.liteav.basic.opengl.a aVar6;
        if (this.f19085j == null) {
            this.f19085j = new c();
            this.f19089n = 0L;
            this.f19090o = System.currentTimeMillis();
        }
        c cVar = this.f19085j;
        if (i2 == cVar.f19109b && i3 == cVar.f19110c && i4 == cVar.f19111d && (((i13 = this.f19079d) <= 0 || i13 == cVar.f19113f) && (((i14 = this.f19080e) <= 0 || i14 == cVar.f19114g) && (((aVar2 = this.f19082g) == null || (((i16 = aVar2.f18494c) <= 0 || ((aVar6 = cVar.f19117j) != null && i16 == aVar6.f18494c)) && (((i17 = aVar2.f18495d) <= 0 || ((aVar5 = cVar.f19117j) != null && i17 == aVar5.f18495d)) && (((i18 = aVar2.f18492a) < 0 || ((aVar4 = cVar.f19117j) != null && i18 == aVar4.f18492a)) && ((i19 = aVar2.f18493b) < 0 || ((aVar3 = cVar.f19117j) != null && i19 == aVar3.f18493b)))))) && this.f19078c == cVar.f19112e && (i15 = cVar.f19115h) == i5)))) {
            if (i5 != i15 || i6 != cVar.f19116i) {
                cVar.f19115h = i5;
                b bVar = this.f19084i;
                bVar.f19105k = i5;
                cVar.f19116i = i6;
                bVar.f19106l = i6;
                this.f19083h.b(i6);
            }
        } else {
            TXCLog.i("TXCVideoPreprocessor", "Init sdk");
            c cVar2 = this.f19085j;
            cVar2.f19109b = i2;
            cVar2.f19110c = i3;
            com.tencent.liteav.basic.opengl.a aVar7 = this.f19082g;
            if (aVar7 == null || (i9 = aVar7.f18492a) < 0 || (i10 = aVar7.f18493b) < 0 || (i11 = aVar7.f18494c) <= 0 || (i12 = aVar7.f18495d) <= 0) {
                i7 = i2;
                i8 = i3;
                aVar = null;
            } else {
                aVar = new com.tencent.liteav.basic.opengl.a(i9, i10, i11, i12);
                com.tencent.liteav.basic.opengl.a aVar8 = this.f19082g;
                int i20 = aVar8.f18492a;
                int i21 = i2 - i20;
                i7 = aVar8.f18494c;
                if (i21 <= i7) {
                    i7 = i2 - i20;
                }
                int i22 = aVar8.f18493b;
                int i23 = i3 - i22;
                i8 = aVar8.f18495d;
                if (i23 <= i8) {
                    i8 = i3 - i22;
                }
                aVar.f18494c = i7;
                aVar.f18495d = i8;
                TXCLog.i("TXCVideoPreprocessor", "set crop rect [%d,%d,%d,%d]", Integer.valueOf(aVar.f18492a), Integer.valueOf(aVar.f18493b), Integer.valueOf(aVar.f18494c), Integer.valueOf(aVar.f18495d));
            }
            c cVar3 = this.f19085j;
            cVar3.f19117j = this.f19082g;
            cVar3.f19111d = i4;
            cVar3.f19108a = this.f19077b;
            cVar3.f19115h = i5;
            cVar3.f19116i = i6;
            int i24 = this.f19079d;
            cVar3.f19113f = i24;
            int i25 = this.f19080e;
            cVar3.f19114g = i25;
            if (i24 <= 0 || i25 <= 0) {
                if (90 != i4 && 270 != i4) {
                    cVar3.f19113f = i7;
                    cVar3.f19114g = i8;
                } else {
                    cVar3.f19113f = i8;
                    cVar3.f19114g = i7;
                }
            }
            EnumC0332d enumC0332d = this.f19087l;
            if (enumC0332d == EnumC0332d.MODE_SAME_AS_OUTPUT) {
                if (90 != i4 && 270 != i4) {
                    i7 = cVar3.f19113f;
                    i8 = cVar3.f19114g;
                } else {
                    i7 = cVar3.f19114g;
                    i8 = cVar3.f19113f;
                }
            } else if (enumC0332d != EnumC0332d.MODE_SAME_AS_INPUT) {
                com.tencent.liteav.basic.util.e eVarB = b(i7, i8, i4, cVar3.f19113f, cVar3.f19114g);
                i7 = ((eVarB.f18712a + 7) / 8) * 8;
                i8 = ((eVarB.f18713b + 7) / 8) * 8;
            }
            TXCLog.i("TXCVideoPreprocessor", "input size:%d*%d process size:%d*%d output size:%d*%d", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i7), Integer.valueOf(i8), Integer.valueOf(this.f19085j.f19113f), Integer.valueOf(this.f19085j.f19114g));
            c cVar4 = this.f19085j;
            cVar4.f19112e = this.f19078c;
            if (!a(cVar4, i7, i8, aVar)) {
                TXCLog.e("TXCVideoPreprocessor", "init failed!");
                return false;
            }
        }
        return true;
    }

    private boolean a(c cVar, int i2, int i3, com.tencent.liteav.basic.opengl.a aVar) {
        b bVar = this.f19084i;
        bVar.f19098d = cVar.f19109b;
        bVar.f19099e = cVar.f19110c;
        bVar.f19107m = aVar;
        bVar.f19101g = i2;
        bVar.f19100f = i3;
        bVar.f19102h = (cVar.f19111d + 360) % 360;
        bVar.f19096b = cVar.f19113f;
        bVar.f19097c = cVar.f19114g;
        bVar.f19095a = 0;
        bVar.f19104j = cVar.f19108a;
        bVar.f19103i = cVar.f19112e;
        bVar.f19105k = cVar.f19115h;
        bVar.f19106l = cVar.f19116i;
        if (this.f19083h == null) {
            com.tencent.liteav.beauty.c cVar2 = new com.tencent.liteav.beauty.c(this.f19076a, cVar.f19108a);
            this.f19083h = cVar2;
            cVar2.a(this.f19081f);
        }
        return this.f19083h.a(this.f19084i);
    }

    public void a(int i2) {
        if (i2 != this.f19081f) {
            this.f19081f = i2;
            com.tencent.liteav.beauty.c cVar = this.f19083h;
            if (cVar != null) {
                cVar.a(i2);
            }
        }
    }

    public synchronized void a(String str) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.a(str);
        }
    }

    @TargetApi(18)
    public boolean a(String str, boolean z2) {
        if (TXCBuild.VersionInt() < 18) {
            return false;
        }
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar == null) {
            return true;
        }
        cVar.a(str, z2);
        return true;
    }

    public synchronized void a(float f2) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.a(f2);
        }
    }

    public synchronized void a(Bitmap bitmap) {
        com.tencent.liteav.beauty.c cVar = this.f19083h;
        if (cVar != null) {
            cVar.a(bitmap);
        }
    }
}
