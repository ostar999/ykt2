package com.tencent.liteav;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.Monitor;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.screencapture.a;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes6.dex */
public class i implements k, com.tencent.liteav.screencapture.b {

    /* renamed from: a, reason: collision with root package name */
    private final com.tencent.liteav.screencapture.a f19376a;

    /* renamed from: b, reason: collision with root package name */
    private l f19377b;

    /* renamed from: e, reason: collision with root package name */
    private int f19380e;

    /* renamed from: f, reason: collision with root package name */
    private com.tencent.liteav.basic.util.e f19381f;

    /* renamed from: g, reason: collision with root package name */
    private int f19382g;

    /* renamed from: h, reason: collision with root package name */
    private int f19383h;

    /* renamed from: k, reason: collision with root package name */
    private long f19386k;

    /* renamed from: l, reason: collision with root package name */
    private long f19387l;

    /* renamed from: m, reason: collision with root package name */
    private long f19388m;

    /* renamed from: n, reason: collision with root package name */
    private boolean f19389n;

    /* renamed from: o, reason: collision with root package name */
    private com.tencent.liteav.basic.opengl.a f19390o;

    /* renamed from: p, reason: collision with root package name */
    private int f19391p;

    /* renamed from: q, reason: collision with root package name */
    private int f19392q;

    /* renamed from: c, reason: collision with root package name */
    private EGLContext f19378c = null;

    /* renamed from: d, reason: collision with root package name */
    private WeakReference<com.tencent.liteav.basic.b.b> f19379d = null;

    /* renamed from: i, reason: collision with root package name */
    private String f19384i = "";

    /* renamed from: j, reason: collision with root package name */
    private int f19385j = 0;

    /* renamed from: r, reason: collision with root package name */
    private final Queue<Runnable> f19393r = new LinkedList();

    public i(Context context, g gVar, a.InterfaceC0337a interfaceC0337a) {
        this.f19391p = 0;
        this.f19392q = 0;
        com.tencent.liteav.screencapture.a aVar = new com.tencent.liteav.screencapture.a(context, gVar.Y, interfaceC0337a);
        this.f19376a = aVar;
        aVar.a((com.tencent.liteav.screencapture.b) this);
        gVar.a();
        com.tencent.liteav.basic.util.e eVarC = c(gVar.f19327a, gVar.f19328b);
        this.f19381f = eVarC;
        this.f19380e = gVar.f19337k;
        int i2 = gVar.f19327a;
        this.f19382g = i2;
        this.f19383h = gVar.f19328b;
        TXCLog.i("TXCScreenCaptureSource", "capture size: %s, encode size: %dx%d", eVarC, Integer.valueOf(i2), Integer.valueOf(this.f19383h));
        if (TXCBuild.VersionInt() >= 17) {
            try {
                WindowManager windowManager = (WindowManager) context.getSystemService("window");
                if (windowManager != null) {
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
                    this.f19391p = displayMetrics.widthPixels;
                    this.f19392q = displayMetrics.heightPixels;
                    TXCLog.i("TXCScreenCaptureSource", "DeviceScreen: [width:" + this.f19391p + " ][height:" + this.f19392q + StrPool.BRACKET_END);
                }
            } catch (Exception e2) {
                TXCLog.e("TXCScreenCaptureSource", "get screen resolution failed.", e2);
            }
        }
    }

    private com.tencent.liteav.basic.util.e c(int i2, int i3) {
        boolean z2 = i2 > i3;
        com.tencent.liteav.basic.util.e eVar = new com.tencent.liteav.basic.util.e();
        if (i2 > 1280 || i3 > 1280) {
            eVar.f18712a = z2 ? Math.max(i2, i3) : Math.min(i2, i3);
            eVar.f18713b = z2 ? Math.min(i2, i3) : Math.max(i2, i3);
        } else {
            eVar.f18712a = z2 ? 1280 : 720;
            eVar.f18713b = z2 ? 720 : 1280;
        }
        return eVar;
    }

    @Override // com.tencent.liteav.k
    public void a(float f2, float f3) {
    }

    @Override // com.tencent.liteav.k
    public void a(int i2, int i3) {
    }

    @Override // com.tencent.liteav.k
    public void a(com.tencent.liteav.basic.enums.c cVar) {
    }

    public void a(com.tencent.liteav.basic.opengl.a aVar) {
        this.f19390o = aVar;
    }

    @Override // com.tencent.liteav.k
    public void a(com.tencent.liteav.basic.structs.b bVar) {
    }

    @Override // com.tencent.liteav.k
    public boolean a(int i2) {
        return false;
    }

    @Override // com.tencent.liteav.k
    public void b() {
        this.f19376a.a(true);
    }

    @Override // com.tencent.liteav.k
    public void b(int i2) {
    }

    @Override // com.tencent.liteav.k
    public void c(int i2) {
    }

    @Override // com.tencent.liteav.k
    public void c(boolean z2) {
    }

    @Override // com.tencent.liteav.k
    public void d(int i2) {
    }

    @Override // com.tencent.liteav.k
    public boolean d() {
        return true;
    }

    @Override // com.tencent.liteav.k
    public boolean d(boolean z2) {
        return false;
    }

    @Override // com.tencent.liteav.k
    public int e() {
        return 0;
    }

    @Override // com.tencent.liteav.k
    public void e(int i2) {
    }

    @Override // com.tencent.liteav.k
    public void e(boolean z2) {
    }

    @Override // com.tencent.liteav.k
    public EGLContext f() {
        return this.f19378c;
    }

    @Override // com.tencent.liteav.k
    public int g() {
        return this.f19380e;
    }

    @Override // com.tencent.liteav.k
    public boolean h() {
        return false;
    }

    @Override // com.tencent.liteav.k
    public boolean i() {
        return false;
    }

    @Override // com.tencent.liteav.k
    public boolean j() {
        return false;
    }

    @Override // com.tencent.liteav.k
    public boolean k() {
        return false;
    }

    @Override // com.tencent.liteav.k
    public boolean l() {
        return false;
    }

    @Override // com.tencent.liteav.k
    public void a() {
        Monitor.a(2, String.format("VideoCapture[%d]: start screen", Integer.valueOf(hashCode())), "", 0);
        this.f19386k = 0L;
        this.f19387l = 0L;
        this.f19388m = 0L;
        this.f19389n = true;
        com.tencent.liteav.screencapture.a aVar = this.f19376a;
        com.tencent.liteav.basic.util.e eVar = this.f19381f;
        aVar.a(eVar.f18712a, eVar.f18713b, this.f19380e);
    }

    @Override // com.tencent.liteav.k
    public void b(boolean z2) {
        com.tencent.liteav.basic.util.e eVarC = c(this.f19382g, this.f19383h);
        if (eVarC.equals(this.f19381f)) {
            return;
        }
        this.f19381f = eVarC;
        this.f19376a.a(eVarC.f18712a, eVarC.f18713b);
        TXCLog.i("TXCScreenCaptureSource", "capture size: %s, encode size: %dx%d", this.f19381f, Integer.valueOf(this.f19382g), Integer.valueOf(this.f19383h));
    }

    @Override // com.tencent.liteav.k
    public void f(int i2) {
        this.f19380e = i2;
        this.f19376a.a(i2);
    }

    @Override // com.tencent.liteav.k
    public void g(int i2) {
        this.f19385j = i2;
    }

    private void f(boolean z2) {
        if (z2) {
            int i2 = this.f19382g;
            int i3 = this.f19383h;
            if (i2 > i3) {
                b(i3, i2);
                return;
            }
            return;
        }
        int i4 = this.f19382g;
        int i5 = this.f19383h;
        if (i4 < i5) {
            b(i5, i4);
        }
    }

    @Override // com.tencent.liteav.k
    public void c() {
        this.f19376a.a(false);
    }

    @Override // com.tencent.liteav.k
    public void b(int i2, int i3) {
        TXCLog.i("TXCScreenCaptureSource", "setVideoEncSize %dx%d", Integer.valueOf(i2), Integer.valueOf(i3));
        this.f19382g = i2;
        this.f19383h = i3;
    }

    @Override // com.tencent.liteav.k
    public void a(boolean z2) {
        Monitor.a(2, String.format("VideoCapture[%d]: stop screen", Integer.valueOf(hashCode())), "", 0);
        this.f19376a.a((Object) null);
    }

    @Override // com.tencent.liteav.k
    public void a(String str) {
        this.f19384i = str;
    }

    @Override // com.tencent.liteav.k
    public void a(l lVar) {
        this.f19377b = lVar;
    }

    @Override // com.tencent.liteav.k
    public void a(Runnable runnable) {
        com.tencent.liteav.screencapture.a aVar = this.f19376a;
        if (aVar != null) {
            aVar.a(runnable);
        }
    }

    @Override // com.tencent.liteav.k
    public void a(com.tencent.liteav.basic.b.b bVar) {
        this.f19379d = new WeakReference<>(bVar);
        com.tencent.liteav.screencapture.a aVar = this.f19376a;
        if (aVar != null) {
            aVar.a(bVar);
        }
    }

    private boolean a(Queue<Runnable> queue) {
        synchronized (queue) {
            if (queue.isEmpty()) {
                return false;
            }
            Runnable runnablePoll = queue.poll();
            if (runnablePoll == null) {
                return false;
            }
            runnablePoll.run();
            return true;
        }
    }

    @Override // com.tencent.liteav.screencapture.b
    public void a(int i2, EGLContext eGLContext, int i3, int i4, int i5, long j2) {
        this.f19378c = eGLContext;
        while (a(this.f19393r)) {
        }
        if (i2 != 0) {
            TXCLog.e("TXCScreenCaptureSource", "onScreenCaptureFrame failed");
            return;
        }
        if (this.f19389n) {
            this.f19389n = false;
            Monitor.a(2, String.format("VideoCapture[%d]: capture first frame", Integer.valueOf(hashCode())), "", 0);
            com.tencent.liteav.basic.util.h.a(this.f19379d, 1007, "First frame capture completed");
            TXCLog.i("TXCScreenCaptureSource", "on Got first frame");
        }
        this.f19386k++;
        long jCurrentTimeMillis = System.currentTimeMillis() - this.f19387l;
        if (jCurrentTimeMillis >= TimeUnit.SECONDS.toMillis(1L)) {
            this.f19388m = this.f19386k;
            this.f19387l = System.currentTimeMillis();
            TXCStatus.a(this.f19384i, 1001, this.f19385j, Double.valueOf(((r0 - this.f19388m) * 1000.0d) / jCurrentTimeMillis));
        }
        if (this.f19377b != null) {
            f(i4 < i5);
            int i6 = this.f19391p;
            int i7 = this.f19392q;
            if (i4 > i5) {
                i7 = i6;
                i6 = i7;
            }
            com.tencent.liteav.basic.structs.b bVar = new com.tencent.liteav.basic.structs.b();
            bVar.f18656e = i4;
            bVar.f18657f = i5;
            bVar.f18652a = i3;
            bVar.f18653b = 0;
            bVar.f18661j = 0;
            com.tencent.liteav.basic.opengl.a aVar = this.f19390o;
            if (aVar != null && i6 > 0 && i7 > 0 && aVar.f18492a < i6 && aVar.f18493b < i7) {
                com.tencent.liteav.basic.opengl.a aVar2 = new com.tencent.liteav.basic.opengl.a();
                bVar.f18663l = aVar2;
                float f2 = i6;
                float f3 = i4;
                int i8 = (int) ((aVar.f18492a / f2) * f3);
                aVar2.f18492a = i8;
                float f4 = i7;
                float f5 = i5;
                int i9 = (int) ((aVar.f18493b / f4) * f5);
                aVar2.f18493b = i9;
                int i10 = (int) (((((aVar.f18494c / f2) * f3) + 15.0f) / 16.0f) * 16.0f);
                aVar2.f18494c = i10;
                int i11 = (int) (((((aVar.f18495d / f4) * f5) + 15.0f) / 16.0f) * 16.0f);
                aVar2.f18495d = i11;
                if (i4 - i8 <= i10) {
                    i10 = i4 - i8;
                }
                if (i5 - i9 <= i11) {
                    i11 = i5 - i9;
                }
                int i12 = this.f19383h;
                int i13 = i10 * i12;
                int i14 = this.f19382g;
                if (i13 >= i11 * i14) {
                    i12 = (i11 * i14) / i10;
                } else {
                    i14 = (i10 * i12) / i11;
                }
                bVar.f18658g = ((i14 + 15) / 16) * 16;
                bVar.f18659h = ((i12 + 15) / 16) * 16;
            } else {
                int i15 = this.f19382g;
                bVar.f18658g = i15;
                int i16 = this.f19383h;
                bVar.f18659h = i16;
                bVar.f18663l = com.tencent.liteav.basic.util.h.a(i4, i5, i15, i16);
            }
            this.f19377b.b(bVar);
        }
    }

    @Override // com.tencent.liteav.screencapture.b
    public void a(Object obj) {
        while (a(this.f19393r)) {
        }
        l lVar = this.f19377b;
        if (lVar != null) {
            lVar.r();
        }
    }
}
