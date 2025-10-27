package org.wrtca.record.model;

/* loaded from: classes9.dex */
public interface CameraParamObserver {
    int getDirection();

    int getFps();

    int getHeight();

    int getWidth();

    boolean isFrontCamera();

    void reportCameraClosed();

    void reportCameraOpenParam(int i2, int i3, int i4, int i5);
}
