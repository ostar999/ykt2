package com.easefun.polyv.livecommon.module.utils.media;

/* loaded from: classes3.dex */
public interface PLVCameraListener {
    public static final int CAMERA_DISABLED = 3;
    public static final int CAMERA_NOT_SUPPORT = 1;
    public static final int CAMERA_OPEN_FAILED = 4;
    public static final int NO_CAMERA = 2;

    void onCameraChange();

    void onOpenFail(Throwable t2, int error);

    void onOpenSuccess();
}
