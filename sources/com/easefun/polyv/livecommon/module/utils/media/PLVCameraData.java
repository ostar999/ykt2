package com.easefun.polyv.livecommon.module.utils.media;

/* loaded from: classes3.dex */
public class PLVCameraData {
    public static final int FACING_BACK = 2;
    public static final int FACING_FRONT = 1;
    public int cameraFacing;
    public int cameraHeight;
    public int cameraID;
    public int cameraWidth;
    public boolean hasLight;
    public int orientation;
    public boolean supportTouchFocus;
    public boolean touchFocusMode;

    public PLVCameraData(int id, int facing, int width, int height) {
        this.cameraID = id;
        this.cameraFacing = facing;
        this.cameraWidth = width;
        this.cameraHeight = height;
    }

    public PLVCameraData(int id, int facing) {
        this.cameraID = id;
        this.cameraFacing = facing;
    }
}
