package com.luck.lib.camerax;

import com.luck.lib.camerax.listener.OnSimpleXPermissionDeniedListener;
import com.luck.lib.camerax.listener.OnSimpleXPermissionDescriptionListener;

/* loaded from: classes4.dex */
public final class CustomCameraConfig {
    public static final int BUTTON_STATE_BOTH = 0;
    public static final int BUTTON_STATE_ONLY_CAPTURE = 1;
    public static final int BUTTON_STATE_ONLY_RECORDER = 2;
    public static final int DEFAULT_MAX_RECORD_VIDEO = 60500;
    public static final int DEFAULT_MIN_RECORD_VIDEO = 1500;
    public static final String SP_NAME = "PictureSpUtils";
    public static OnSimpleXPermissionDeniedListener deniedListener;
    public static OnSimpleXPermissionDescriptionListener explainListener;
    public static CameraImageEngine imageEngine;

    public static void destroy() {
        imageEngine = null;
        explainListener = null;
        deniedListener = null;
    }
}
