package androidx.camera.core.impl;

import android.hardware.camera2.CaptureResult;
import androidx.annotation.NonNull;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.utils.ExifData;

/* loaded from: classes.dex */
public final /* synthetic */ class a {
    @NonNull
    public static CaptureResult a(CameraCaptureResult cameraCaptureResult) {
        return CameraCaptureResult.EmptyCameraCaptureResult.create().getCaptureResult();
    }

    public static void b(CameraCaptureResult cameraCaptureResult, @NonNull ExifData.Builder builder) {
        builder.setFlashState(cameraCaptureResult.getFlashState());
    }
}
