package androidx.camera.core.impl;

import android.util.Size;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.common.util.concurrent.ListenableFuture;

@RequiresApi(21)
/* loaded from: classes.dex */
public interface CaptureProcessor {
    void close();

    @NonNull
    ListenableFuture<Void> getCloseFuture();

    void onOutputSurface(@NonNull Surface surface, int i2);

    void onResolutionUpdate(@NonNull Size size);

    void process(@NonNull ImageProxyBundle imageProxyBundle);
}
