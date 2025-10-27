package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import java.util.Collections;
import java.util.LinkedHashSet;

/* loaded from: classes.dex */
public final /* synthetic */ class i {
    @NonNull
    public static CameraControl a(CameraInternal cameraInternal) {
        return cameraInternal.getCameraControlInternal();
    }

    @NonNull
    public static CameraInfo b(CameraInternal cameraInternal) {
        return cameraInternal.getCameraInfoInternal();
    }

    @NonNull
    public static LinkedHashSet c(CameraInternal cameraInternal) {
        return new LinkedHashSet(Collections.singleton(cameraInternal));
    }

    @NonNull
    public static CameraConfig d(CameraInternal cameraInternal) {
        return CameraConfigs.emptyConfig();
    }

    public static void e(CameraInternal cameraInternal, boolean z2) {
    }

    public static void f(CameraInternal cameraInternal, @Nullable CameraConfig cameraConfig) {
    }
}
