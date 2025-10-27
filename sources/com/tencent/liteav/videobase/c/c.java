package com.tencent.liteav.videobase.c;

import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private e<?> f19982a;

    public void a(Object obj, Surface surface, int i2, int i3) throws d {
        if (obj == null) {
            this.f19982a = b.a(null, null, surface, i2, i3);
        } else if (obj instanceof EGLContext) {
            this.f19982a = a.a(null, (EGLContext) obj, surface, i2, i3);
        } else {
            if (!(obj instanceof android.opengl.EGLContext)) {
                throw new d(0, "sharedContext isn't EGLContext");
            }
            this.f19982a = b.a(null, (android.opengl.EGLContext) obj, surface, i2, i3);
        }
        TXCLog.i("EGLCore", "EGLCore created in thread %d, sharedContext: %s, Surface: %s, width: %d, height: %d", Long.valueOf(Thread.currentThread().getId()), obj, surface, Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public void b() {
        this.f19982a.d();
    }

    public Object c() {
        return this.f19982a.f();
    }

    public void d() throws d {
        this.f19982a.c();
        this.f19982a = null;
    }

    public void a() throws d {
        this.f19982a.a();
    }
}
