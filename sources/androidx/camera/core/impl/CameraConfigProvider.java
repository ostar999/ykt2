package androidx.camera.core.impl;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.CameraInfo;

@RequiresApi(21)
/* loaded from: classes.dex */
public interface CameraConfigProvider {
    public static final CameraConfigProvider EMPTY = new CameraConfigProvider() { // from class: androidx.camera.core.impl.c
        @Override // androidx.camera.core.impl.CameraConfigProvider
        public final CameraConfig getConfig(CameraInfo cameraInfo, Context context) {
            return d.a(cameraInfo, context);
        }
    };

    @Nullable
    CameraConfig getConfig(@NonNull CameraInfo cameraInfo, @NonNull Context context);
}
