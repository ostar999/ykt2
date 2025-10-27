package com.tencent.liteav;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import com.google.android.exoplayer2.ExoPlayer;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.Monitor;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.capturer.a;
import java.lang.ref.WeakReference;
import java.util.List;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes6.dex */
public class c implements com.tencent.liteav.basic.b.b, com.tencent.liteav.basic.opengl.o, com.tencent.liteav.capturer.b, k {

    /* renamed from: a, reason: collision with root package name */
    WeakReference<com.tencent.liteav.basic.b.b> f19131a;

    /* renamed from: b, reason: collision with root package name */
    private Context f19132b;

    /* renamed from: d, reason: collision with root package name */
    private l f19134d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f19135e;

    /* renamed from: f, reason: collision with root package name */
    private g f19136f;

    /* renamed from: h, reason: collision with root package name */
    private com.tencent.liteav.basic.opengl.n f19138h;

    /* renamed from: k, reason: collision with root package name */
    private long f19141k;

    /* renamed from: g, reason: collision with root package name */
    private int f19137g = 0;

    /* renamed from: i, reason: collision with root package name */
    private boolean f19139i = false;

    /* renamed from: j, reason: collision with root package name */
    private long f19140j = 0;

    /* renamed from: l, reason: collision with root package name */
    private long f19142l = 0;

    /* renamed from: m, reason: collision with root package name */
    private int f19143m = 0;

    /* renamed from: n, reason: collision with root package name */
    private Object f19144n = new Object();

    /* renamed from: o, reason: collision with root package name */
    private HandlerThread f19145o = null;

    /* renamed from: p, reason: collision with root package name */
    private Handler f19146p = null;

    /* renamed from: q, reason: collision with root package name */
    private String f19147q = "";

    /* renamed from: r, reason: collision with root package name */
    private boolean f19148r = true;

    /* renamed from: c, reason: collision with root package name */
    private final com.tencent.liteav.capturer.a f19133c = new com.tencent.liteav.capturer.a();

    /* renamed from: com.tencent.liteav.c$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f19152a;

        static {
            int[] iArr = new int[com.tencent.liteav.basic.enums.c.values().length];
            f19152a = iArr;
            try {
                iArr[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_INVALID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f19152a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_360_640.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f19152a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_540_960.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f19152a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_1080_1920.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f19152a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_320_480.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f19152a[com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_720_1280.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public c(Context context, g gVar, com.tencent.liteav.basic.opengl.n nVar, boolean z2) {
        this.f19138h = null;
        try {
            this.f19136f = (g) gVar.clone();
        } catch (CloneNotSupportedException e2) {
            this.f19136f = new g();
            e2.printStackTrace();
        }
        this.f19132b = context;
        this.f19138h = nVar;
        nVar.setSurfaceTextureListener(this);
        g gVar2 = this.f19136f;
        gVar2.Z = z2;
        this.f19133c.b(gVar2.X || (gVar2.f19329c > 0 && gVar2.f19330d > 0));
        TXCLog.i("TXCCameraCaptureSource", "camera capture with nv21: %b", Boolean.valueOf(this.f19136f.Z));
    }

    private a.EnumC0333a n() {
        g gVar = this.f19136f;
        if (gVar.W) {
            return a.EnumC0333a.RESOLUTION_HIGHEST;
        }
        int i2 = AnonymousClass3.f19152a[gVar.f19340n.ordinal()];
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? a.EnumC0333a.RESOLUTION_720_1280 : a.EnumC0333a.RESOLUTION_320_480 : a.EnumC0333a.RESOLUTION_1080_1920 : a.EnumC0333a.RESOLUTION_540_960 : a.EnumC0333a.RESOLUTION_360_640 : a.EnumC0333a.RESOLUTION_INVALID;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean o() {
        try {
            Context context = this.f19132b;
            if (context != null) {
                List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
                if (runningAppProcesses == null) {
                    TXCLog.w("CameraCapture", "List of RunningAppProcessInfo is null");
                    return false;
                }
                for (int i2 = 0; i2 < runningAppProcesses.size(); i2++) {
                    ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcesses.get(i2);
                    if (runningAppProcessInfo == null) {
                        TXCLog.w("CameraCapture", "ActivityManager.RunningAppProcessInfo is null");
                    } else if (runningAppProcessInfo.processName.equals(this.f19132b.getPackageName()) && runningAppProcessInfo.importance == 100) {
                        return true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    @Override // com.tencent.liteav.k
    public EGLContext f() {
        return this.f19138h.getGLContext();
    }

    @Override // com.tencent.liteav.k
    public int g() {
        return this.f19136f.f19337k;
    }

    @Override // com.tencent.liteav.k
    public boolean h() {
        com.tencent.liteav.capturer.a aVar = this.f19133c;
        if (aVar != null) {
            return aVar.b();
        }
        return false;
    }

    @Override // com.tencent.liteav.k
    public boolean i() {
        com.tencent.liteav.capturer.a aVar = this.f19133c;
        if (aVar != null) {
            return aVar.c();
        }
        return false;
    }

    @Override // com.tencent.liteav.k
    public boolean j() {
        com.tencent.liteav.capturer.a aVar = this.f19133c;
        if (aVar != null) {
            return aVar.d();
        }
        return false;
    }

    @Override // com.tencent.liteav.k
    public boolean k() {
        com.tencent.liteav.capturer.a aVar = this.f19133c;
        if (aVar != null) {
            return aVar.e();
        }
        return false;
    }

    @Override // com.tencent.liteav.k
    public boolean l() {
        com.tencent.liteav.capturer.a aVar = this.f19133c;
        if (aVar != null) {
            return aVar.i();
        }
        return false;
    }

    @Override // com.tencent.liteav.capturer.b
    public void m() {
        if (this.f19133c.l() != null) {
            this.f19133c.g();
        }
        synchronized (this.f19144n) {
            if (this.f19145o == null) {
                HandlerThread handlerThread = new HandlerThread("cameraMonitorThread");
                this.f19145o = handlerThread;
                handlerThread.start();
                this.f19146p = new Handler(this.f19145o.getLooper());
                TXCLog.w("CameraCapture", "start camera monitor ");
            }
            Handler handler = this.f19146p;
            if (handler != null) {
                handler.postDelayed(new Runnable() { // from class: com.tencent.liteav.c.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            if (!c.this.d() || !c.this.o() || c.this.f19133c.l() != null) {
                                if (c.this.f19146p != null) {
                                    c.this.f19146p.postDelayed(this, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                                    return;
                                }
                                return;
                            }
                            TXCLog.w("CameraCapture", "camera monitor restart capture");
                            c.this.f19133c.g();
                            c.this.f19138h.a(false);
                            c.this.f19133c.a(c.this.f19136f.f19337k);
                            if (c.this.f19136f.f19329c <= 0 || c.this.f19136f.f19330d <= 0) {
                                c.this.f19133c.a(c.this.f19136f.Z, c.this.f19136f.f19327a, c.this.f19136f.f19328b);
                            } else {
                                c.this.f19133c.a(c.this.f19136f.Z, c.this.f19136f.f19329c, c.this.f19136f.f19330d);
                            }
                            c.this.f19133c.a(c.this.f19138h.getSurfaceTexture());
                            c.this.f19133c.d(c.this.f19136f.f19342p);
                        } catch (Exception unused) {
                            TXCLog.w("CameraCapture", "camera monitor exception ");
                        }
                    }
                }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            }
        }
    }

    @Override // com.tencent.liteav.basic.b.b
    public void onNotifyEvent(int i2, Bundle bundle) {
        com.tencent.liteav.basic.util.h.a(this.f19131a, i2, bundle);
    }

    @Override // com.tencent.liteav.k
    public void a() {
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(hashCode());
        objArr[1] = Integer.valueOf(this.f19138h.getSurfaceTexture() == null ? 0 : this.f19138h.getSurfaceTexture().hashCode());
        Monitor.a(2, String.format("VideoCapture[%d]: start camera. sufaceTexture:%d", objArr), "", 0);
        this.f19138h.a(this.f19136f.f19337k, !r1.Z);
        c(this.f19138h.getSurfaceTexture());
    }

    @Override // com.tencent.liteav.k
    public void b() {
        TXCLog.i("CameraCapture", "startCapture->enter with getSurfaceTexture:" + this.f19138h.getSurfaceTexture());
        c(this.f19138h.getSurfaceTexture());
    }

    @Override // com.tencent.liteav.k
    public void c() {
        TXCLog.i("CameraCapture", "stopCapture->enter with null");
        this.f19133c.a((com.tencent.liteav.capturer.b) null);
        this.f19133c.g();
        this.f19135e = false;
    }

    @Override // com.tencent.liteav.k
    public boolean d() {
        return this.f19135e;
    }

    @Override // com.tencent.liteav.k
    public int e() {
        return this.f19133c.f();
    }

    @Override // com.tencent.liteav.k
    public void f(int i2) {
        this.f19136f.f19337k = i2;
        com.tencent.liteav.capturer.a aVar = this.f19133c;
        if (aVar != null) {
            aVar.a(i2);
        }
        com.tencent.liteav.basic.opengl.n nVar = this.f19138h;
        if (nVar != null) {
            nVar.setFPS(i2);
        }
    }

    @Override // com.tencent.liteav.k
    public void g(int i2) {
        this.f19143m = i2;
    }

    @Override // com.tencent.liteav.k
    public void d(int i2) {
        com.tencent.liteav.basic.opengl.n nVar = this.f19138h;
        if (nVar != null) {
            nVar.setRendMirror(i2);
        }
    }

    @Override // com.tencent.liteav.k
    public void e(int i2) {
        this.f19136f.f19341o = i2;
        this.f19133c.c(i2);
        this.f19148r = true;
        TXCLog.i("CameraCapture", String.format("vsize setCaptureOrientation w*h:%d*%d orientation:%d", Integer.valueOf(this.f19136f.f19327a), Integer.valueOf(this.f19136f.f19328b), Integer.valueOf(this.f19136f.f19341o)));
    }

    @Override // com.tencent.liteav.k
    public void b(boolean z2) {
        com.tencent.liteav.capturer.a aVar;
        int i2;
        if (!this.f19135e || (aVar = this.f19133c) == null) {
            return;
        }
        g gVar = this.f19136f;
        gVar.f19342p = z2 ? !gVar.f19342p : gVar.f19342p;
        aVar.g();
        this.f19138h.a(false);
        this.f19138h.setFPS(this.f19136f.f19337k);
        this.f19133c.a(this.f19136f.f19337k);
        this.f19133c.c(this.f19136f.f19341o);
        this.f19133c.a(n());
        g gVar2 = this.f19136f;
        int i3 = gVar2.f19329c;
        if (i3 > 0 && (i2 = gVar2.f19330d) > 0) {
            this.f19133c.a(gVar2.Z, i3, i2);
        } else {
            this.f19133c.a(gVar2.Z, gVar2.f19327a, gVar2.f19328b);
        }
        this.f19133c.a(this);
        this.f19133c.a(this.f19138h.getSurfaceTexture());
        if (this.f19133c.d(this.f19136f.f19342p) == 0) {
            this.f19135e = true;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(hashCode());
            objArr[1] = this.f19136f.f19342p ? "front" : "back";
            Monitor.a(2, String.format("VideoCapture[%d]: start %s camera successfully", objArr), String.format("w*h:%d*%d orientation:%d", Integer.valueOf(this.f19136f.f19327a), Integer.valueOf(this.f19136f.f19328b), Integer.valueOf(this.f19136f.f19341o)), 0);
            a(1003, "Enabled camera successfully");
        } else {
            this.f19135e = false;
            a(-1301, "Failed to open the camera, please confirm whether the camera permission is turned on");
            Object[] objArr2 = new Object[2];
            objArr2[0] = Integer.valueOf(hashCode());
            objArr2[1] = this.f19136f.f19342p ? "front" : "back";
            Monitor.a(2, String.format("VideoCapture[%d]: start %s camera failed", objArr2), String.format("w*h:%d*%d orientation:%d", Integer.valueOf(this.f19136f.f19327a), Integer.valueOf(this.f19136f.f19328b), Integer.valueOf(this.f19136f.f19341o)), 0);
        }
        this.f19139i = false;
    }

    @Override // com.tencent.liteav.k
    public boolean d(boolean z2) {
        return this.f19133c.a(z2);
    }

    @Override // com.tencent.liteav.k
    public void c(int i2) {
        com.tencent.liteav.basic.opengl.n nVar = this.f19138h;
        if (nVar != null) {
            nVar.setRendMode(i2);
        }
    }

    @Override // com.tencent.liteav.k
    public void e(boolean z2) {
        g gVar = this.f19136f;
        gVar.X = z2;
        this.f19133c.b(z2 || (gVar.f19329c > 0 && gVar.f19330d > 0));
        this.f19148r = true;
    }

    @Override // com.tencent.liteav.k
    public void c(final boolean z2) {
        a(new Runnable() { // from class: com.tencent.liteav.c.1
            @Override // java.lang.Runnable
            public void run() {
                c.this.f19136f.V = z2;
            }
        });
    }

    private void c(SurfaceTexture surfaceTexture) {
        com.tencent.liteav.capturer.a aVar;
        int i2;
        if (surfaceTexture == null || this.f19135e || (aVar = this.f19133c) == null) {
            return;
        }
        aVar.a(this);
        this.f19133c.a(surfaceTexture);
        this.f19133c.a(this.f19136f.f19337k);
        this.f19133c.c(this.f19136f.f19341o);
        this.f19133c.c(this.f19136f.N);
        this.f19133c.a(n());
        g gVar = this.f19136f;
        int i3 = gVar.f19329c;
        if (i3 > 0 && (i2 = gVar.f19330d) > 0) {
            this.f19133c.a(gVar.Z, i3, i2);
        } else {
            this.f19133c.a(gVar.Z, gVar.f19327a, gVar.f19328b);
        }
        if (this.f19133c.d(this.f19136f.f19342p) == 0) {
            this.f19135e = true;
            this.f19141k = System.currentTimeMillis();
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(hashCode());
            objArr[1] = this.f19136f.f19342p ? "front" : "back";
            Monitor.a(2, String.format("VideoCapture[%d]: start %s camera successfully", objArr), String.format("w*h:%d*%d orientation:%d", Integer.valueOf(this.f19136f.f19327a), Integer.valueOf(this.f19136f.f19328b), Integer.valueOf(this.f19136f.f19341o)), 0);
            a(1003, "Enabled camera successfully");
            this.f19139i = false;
            return;
        }
        this.f19135e = false;
        a(-1301, "Failed to open camera, please confirm whether the camera permission is turned on");
        Object[] objArr2 = new Object[2];
        objArr2[0] = Integer.valueOf(hashCode());
        objArr2[1] = this.f19136f.f19342p ? "front" : "back";
        Monitor.a(2, String.format("VideoCapture[%d]: start %s camera failed", objArr2), String.format("w*h:%d*%d orientation:%d", Integer.valueOf(this.f19136f.f19327a), Integer.valueOf(this.f19136f.f19328b), Integer.valueOf(this.f19136f.f19341o)), 0);
    }

    @Override // com.tencent.liteav.k
    public void a(boolean z2) {
        Monitor.a(2, String.format("VideoCapture[%d]: stop camera", Integer.valueOf(hashCode())), "", 0);
        c();
        this.f19138h.a();
        synchronized (this.f19144n) {
            Handler handler = this.f19146p;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            if (this.f19145o != null) {
                TXCLog.w("CameraCapture", "stop camera monitor ");
                this.f19145o.quit();
                this.f19145o = null;
                this.f19146p = null;
            }
        }
    }

    @Override // com.tencent.liteav.k
    public void a(String str) {
        this.f19147q = str;
    }

    @Override // com.tencent.liteav.k
    public boolean a(int i2) {
        return this.f19133c.b(i2);
    }

    @Override // com.tencent.liteav.k
    public void a(int i2, int i3) {
        this.f19133c.a(i2, i3);
    }

    @Override // com.tencent.liteav.k
    public void a(l lVar) {
        this.f19134d = lVar;
    }

    @Override // com.tencent.liteav.k
    public void a(com.tencent.liteav.basic.structs.b bVar) {
        com.tencent.liteav.basic.opengl.n nVar = this.f19138h;
        if (nVar != null) {
            nVar.a(bVar.f18652a, bVar.f18660i, this.f19137g, bVar.f18656e, bVar.f18657f, this.f19133c.i());
        }
    }

    @Override // com.tencent.liteav.k
    public void a(Runnable runnable) {
        this.f19138h.a(runnable);
    }

    @Override // com.tencent.liteav.k
    public void a(com.tencent.liteav.basic.b.b bVar) {
        this.f19131a = new WeakReference<>(bVar);
    }

    @Override // com.tencent.liteav.k
    public void a(com.tencent.liteav.basic.enums.c cVar) {
        this.f19136f.f19340n = cVar;
        this.f19148r = true;
    }

    @Override // com.tencent.liteav.k
    public void a(float f2, float f3) {
        com.tencent.liteav.capturer.a aVar = this.f19133c;
        if (aVar == null || !this.f19136f.N) {
            return;
        }
        aVar.a(f2, f3);
    }

    private void a(int i2, String str) {
        com.tencent.liteav.basic.util.h.a(this.f19131a, i2, str);
    }

    @Override // com.tencent.liteav.basic.opengl.o
    public void a(SurfaceTexture surfaceTexture) {
        TXCLog.i("CameraCapture", "onSurfaceTextureAvailable->enter with mListener:" + this.f19134d);
        c(surfaceTexture);
        l lVar = this.f19134d;
        if (lVar != null) {
            lVar.a(surfaceTexture);
        }
    }

    @Override // com.tencent.liteav.k
    public void b(int i2) {
        this.f19137g = i2;
    }

    @Override // com.tencent.liteav.k
    public void b(int i2, int i3) {
        g gVar = this.f19136f;
        gVar.f19327a = i2;
        gVar.f19328b = i3;
        this.f19148r = true;
        TXCLog.i("CameraCapture", String.format("vsize setVideoEncSize w*h:%d*%d orientation:%d", Integer.valueOf(i2), Integer.valueOf(this.f19136f.f19328b), Integer.valueOf(this.f19136f.f19341o)));
    }

    @Override // com.tencent.liteav.basic.opengl.o
    public int a(int i2, float[] fArr) {
        a(i2, null, fArr, 4);
        return 0;
    }

    @Override // com.tencent.liteav.basic.opengl.o
    public void a(byte[] bArr, float[] fArr) {
        a(-1, bArr, fArr, 3);
    }

    @Override // com.tencent.liteav.basic.opengl.o
    public void b(SurfaceTexture surfaceTexture) {
        c();
        TXCLog.i("CameraCapture", "onSurfaceTextureDestroy->enter with mListener:" + this.f19134d);
        l lVar = this.f19134d;
        if (lVar != null) {
            lVar.r();
        }
    }

    private void a(int i2, byte[] bArr, float[] fArr, int i3) {
        if (this.f19135e) {
            if (!this.f19139i) {
                Monitor.a(2, String.format("VideoCapture[%d]: capture first frame", Integer.valueOf(hashCode())), "", 0);
                com.tencent.liteav.basic.util.h.a(this.f19131a, 1007, "First frame capture completed");
                this.f19139i = true;
                this.f19148r = true;
                TXCLog.i("CameraCapture", "trtc_render: render first frame");
            }
            com.tencent.liteav.basic.structs.b bVar = new com.tencent.liteav.basic.structs.b();
            bVar.f18656e = this.f19133c.j();
            bVar.f18657f = this.f19133c.k();
            g gVar = this.f19136f;
            bVar.f18658g = gVar.f19327a;
            bVar.f18659h = gVar.f19328b;
            bVar.f18661j = this.f19133c.h();
            bVar.f18660i = this.f19133c.i() ? !this.f19136f.V : this.f19136f.V;
            bVar.f18652a = i2;
            bVar.f18654c = fArr;
            g gVar2 = this.f19136f;
            bVar.f18655d = gVar2.Z;
            bVar.f18664m = bArr;
            bVar.f18653b = i3;
            int i4 = bVar.f18661j;
            if (i4 != 0 && i4 != 180) {
                bVar.f18658g = gVar2.f19327a;
                bVar.f18659h = gVar2.f19328b;
            } else {
                bVar.f18658g = gVar2.f19328b;
                bVar.f18659h = gVar2.f19327a;
            }
            bVar.f18663l = com.tencent.liteav.basic.util.h.a(bVar.f18656e, bVar.f18657f, gVar2.f19328b, gVar2.f19327a);
            l lVar = this.f19134d;
            if (lVar != null) {
                lVar.b(bVar);
            }
            if (this.f19148r) {
                this.f19148r = false;
                TXCLog.i("CameraCapture", String.format("vsize onCaptureFrame w*h:%d*%d angle:%d", Integer.valueOf(bVar.f18658g), Integer.valueOf(bVar.f18659h), Integer.valueOf(bVar.f18661j)));
            }
            this.f19140j++;
            long jCurrentTimeMillis = System.currentTimeMillis() - this.f19141k;
            if (jCurrentTimeMillis >= 1000) {
                TXCStatus.a(this.f19147q, 1001, this.f19143m, Double.valueOf(((this.f19140j - this.f19142l) * 1000.0d) / jCurrentTimeMillis));
                this.f19142l = this.f19140j;
                this.f19141k += jCurrentTimeMillis;
            }
        }
    }

    @Override // com.tencent.liteav.capturer.b
    public void a(byte[] bArr) {
        com.tencent.liteav.basic.opengl.n nVar = this.f19138h;
        if (nVar != null) {
            nVar.a(bArr);
        }
    }
}
