package com.tencent.liteav.basic.opengl;

import android.graphics.SurfaceTexture;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes6.dex */
public interface n {
    void a();

    void a(int i2, boolean z2);

    void a(int i2, boolean z2, int i3, int i4, int i5, boolean z3);

    void a(Runnable runnable);

    void a(boolean z2);

    void a(byte[] bArr);

    EGLContext getGLContext();

    SurfaceTexture getSurfaceTexture();

    void setFPS(int i2);

    void setRendMirror(int i2);

    void setRendMode(int i2);

    void setSurfaceTextureListener(o oVar);
}
