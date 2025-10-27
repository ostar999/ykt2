package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public interface SurfaceProcessor {
    void onInputSurface(@NonNull SurfaceRequest surfaceRequest);

    void onOutputSurface(@NonNull SurfaceOutput surfaceOutput);
}
