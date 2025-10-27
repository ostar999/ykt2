package com.tencent.liteav.basic.opengl;

import android.opengl.EGLContext;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;

/* loaded from: classes6.dex */
public class i extends com.tencent.liteav.basic.util.f {

    /* renamed from: a, reason: collision with root package name */
    public int f18580a;

    /* renamed from: b, reason: collision with root package name */
    public int f18581b;

    /* renamed from: c, reason: collision with root package name */
    public Surface f18582c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f18583d;

    /* renamed from: e, reason: collision with root package name */
    public c f18584e;

    /* renamed from: f, reason: collision with root package name */
    public EGLContext f18585f;

    /* renamed from: g, reason: collision with root package name */
    public b f18586g;

    /* renamed from: h, reason: collision with root package name */
    public javax.microedition.khronos.egl.EGLContext f18587h;

    /* renamed from: i, reason: collision with root package name */
    private a f18588i;

    public interface a {
        void c();

        void d();

        void e();
    }

    public i(Looper looper) {
        super(looper);
        this.f18580a = 720;
        this.f18581b = 1280;
        this.f18582c = null;
        this.f18588i = null;
        this.f18583d = false;
        this.f18584e = null;
        this.f18585f = null;
        this.f18586g = null;
        this.f18587h = null;
    }

    public static void a(final Handler handler, final HandlerThread handlerThread) {
        if (handler == null || handlerThread == null) {
            return;
        }
        Message message = new Message();
        message.what = 101;
        message.obj = new Runnable() { // from class: com.tencent.liteav.basic.opengl.i.1
            @Override // java.lang.Runnable
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.basic.opengl.i.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Handler handler2 = handler;
                        if (handler2 != null) {
                            handler2.removeCallbacksAndMessages(null);
                        }
                        if (handlerThread != null) {
                            if (TXCBuild.VersionInt() >= 18) {
                                handlerThread.quitSafely();
                            } else {
                                handlerThread.quit();
                            }
                        }
                    }
                });
            }
        };
        handler.sendMessage(message);
    }

    private boolean f() {
        TXCLog.i("TXGLThreadHandler", String.format("init egl size[%d/%d]", Integer.valueOf(this.f18580a), Integer.valueOf(this.f18581b)));
        if (this.f18583d) {
            this.f18584e = c.a(null, this.f18585f, this.f18582c, this.f18580a, this.f18581b);
        } else {
            this.f18586g = b.a(null, this.f18587h, this.f18582c, this.f18580a, this.f18581b);
        }
        if (this.f18586g == null && this.f18584e == null) {
            return false;
        }
        TXCLog.w("TXGLThreadHandler", "surface-render: create egl context " + this.f18582c);
        a aVar = this.f18588i;
        if (aVar != null) {
            aVar.c();
        }
        return true;
    }

    private void g() {
        TXCLog.w("TXGLThreadHandler", "surface-render: destroy egl context " + this.f18582c);
        this.f18583d = false;
        a aVar = this.f18588i;
        if (aVar != null) {
            aVar.e();
        }
        b bVar = this.f18586g;
        if (bVar != null) {
            bVar.c();
            this.f18586g = null;
        }
        c cVar = this.f18584e;
        if (cVar != null) {
            cVar.d();
            this.f18584e = null;
        }
        this.f18582c = null;
    }

    public Surface b() {
        return this.f18582c;
    }

    public void c() {
        b bVar = this.f18586g;
        if (bVar != null) {
            bVar.a();
        }
        c cVar = this.f18584e;
        if (cVar != null) {
            cVar.e();
        }
    }

    public void d() {
        b bVar = this.f18586g;
        if (bVar != null) {
            bVar.b();
        }
        c cVar = this.f18584e;
        if (cVar != null) {
            cVar.b();
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (message == null) {
            return;
        }
        switch (message.what) {
            case 100:
                a(message);
                break;
            case 101:
                b(message);
                break;
            case 102:
                c(message);
                break;
        }
        Object obj = message.obj;
        if (obj != null) {
            ((Runnable) obj).run();
        }
    }

    private void b(Message message) {
        g();
    }

    private void c(Message message) {
        try {
            a aVar = this.f18588i;
            if (aVar != null) {
                aVar.d();
            }
        } catch (Exception e2) {
            TXCLog.e("TXGLThreadHandler", "onMsgRend Exception " + e2.getMessage());
        }
    }

    public void a(a aVar) {
        this.f18588i = aVar;
    }

    public javax.microedition.khronos.egl.EGLContext a() {
        b bVar = this.f18586g;
        if (bVar != null) {
            return bVar.d();
        }
        return null;
    }

    private void a(Message message) {
        try {
            f();
        } catch (Exception unused) {
            TXCLog.e("TXGLThreadHandler", "surface-render: init egl context exception " + this.f18582c);
            this.f18582c = null;
        }
    }
}
