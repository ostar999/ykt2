package com.luck.lib.camerax.listener;

/* loaded from: classes4.dex */
public interface CaptureListener {
    void changeTime(long j2);

    void recordEnd(long j2);

    void recordError();

    void recordShort(long j2);

    void recordStart();

    void recordZoom(float f2);

    void takePictures();
}
