package androidx.camera.core.imagecapture;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.impl.CaptureConfig;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
public interface ImageCaptureControl {
    @MainThread
    void lockFlashMode();

    @NonNull
    @MainThread
    ListenableFuture<Void> submitStillCaptureRequests(@NonNull List<CaptureConfig> list);

    @MainThread
    void unlockFlashMode();
}
