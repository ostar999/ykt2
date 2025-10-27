package com.unity3d.player;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;

/* loaded from: classes6.dex */
public class Camera2Wrapper implements e {

    /* renamed from: a, reason: collision with root package name */
    private Context f23882a;

    /* renamed from: b, reason: collision with root package name */
    private c f23883b = null;

    /* renamed from: c, reason: collision with root package name */
    private final int f23884c = 100;

    public Camera2Wrapper(Context context) {
        this.f23882a = context;
        initCamera2Jni();
    }

    private static int a(float f2) {
        return (int) Math.min(Math.max((f2 * 2000.0f) - 1000.0f, -900.0f), 900.0f);
    }

    private final native void deinitCamera2Jni();

    private final native void initCamera2Jni();

    private final native void nativeFrameReady(Object obj, Object obj2, Object obj3, int i2, int i3, int i4);

    private final native void nativeSurfaceTextureReady(Object obj);

    public final void a() throws InterruptedException {
        deinitCamera2Jni();
        closeCamera2();
    }

    @Override // com.unity3d.player.e
    public final void a(Object obj) {
        nativeSurfaceTextureReady(obj);
    }

    @Override // com.unity3d.player.e
    public final void a(Object obj, Object obj2, Object obj3, int i2, int i3, int i4) {
        nativeFrameReady(obj, obj2, obj3, i2, i3, i4);
    }

    public void closeCamera2() throws InterruptedException {
        c cVar = this.f23883b;
        if (cVar != null) {
            cVar.b();
        }
        this.f23883b = null;
    }

    public int getCamera2Count() {
        return c.a(this.f23882a);
    }

    public int getCamera2FocalLengthEquivalent(int i2) {
        return c.d(this.f23882a, i2);
    }

    public int[] getCamera2Resolutions(int i2) {
        return c.e(this.f23882a, i2);
    }

    public int getCamera2SensorOrientation(int i2) {
        return c.a(this.f23882a, i2);
    }

    public Object getCameraFocusArea(float f2, float f3) {
        int iA = a(f2);
        int iA2 = a(1.0f - f3);
        return new Camera.Area(new Rect(iA - 100, iA2 - 100, iA + 100, iA2 + 100), 1000);
    }

    public Rect getFrameSizeCamera2() {
        c cVar = this.f23883b;
        return cVar != null ? cVar.a() : new Rect();
    }

    public boolean initializeCamera2(int i2, int i3, int i4, int i5, int i6) {
        if (this.f23883b != null || UnityPlayer.currentActivity == null) {
            return false;
        }
        c cVar = new c(this);
        this.f23883b = cVar;
        return cVar.a(this.f23882a, i2, i3, i4, i5, i6);
    }

    public boolean isCamera2AutoFocusPointSupported(int i2) {
        return c.c(this.f23882a, i2);
    }

    public boolean isCamera2FrontFacing(int i2) {
        return c.b(this.f23882a, i2);
    }

    public void pauseCamera2() {
        c cVar = this.f23883b;
        if (cVar != null) {
            cVar.d();
        }
    }

    public boolean setAutoFocusPoint(float f2, float f3) {
        c cVar = this.f23883b;
        if (cVar != null) {
            return cVar.a(f2, f3);
        }
        return false;
    }

    public void startCamera2() throws CameraAccessException {
        c cVar = this.f23883b;
        if (cVar != null) {
            cVar.c();
        }
    }

    public void stopCamera2() {
        c cVar = this.f23883b;
        if (cVar != null) {
            cVar.e();
        }
    }
}
