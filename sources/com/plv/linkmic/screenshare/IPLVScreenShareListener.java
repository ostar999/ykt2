package com.plv.linkmic.screenshare;

/* loaded from: classes4.dex */
public interface IPLVScreenShareListener {
    public static final int PLV_SCREEN_SHARE_ERR = 1060505;
    public static final int PLV_SCREEN_SHARE_NO_SUPPORT = 1060501;
    public static final int PLV_SCREEN_SHARE_OK = 1060500;
    public static final int PLV_SCREEN_SHARE_USER_REFUSE = 1060501;

    void onScreenShare(boolean z2);

    void onScreenShareError(int i2);
}
