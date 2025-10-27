package androidx.camera.core;

import androidx.annotation.RequiresApi;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiresApi(21)
/* loaded from: classes.dex */
final class SingleCloseImageProxy extends ForwardingImageProxy {
    private final AtomicBoolean mClosed;

    public SingleCloseImageProxy(ImageProxy imageProxy) {
        super(imageProxy);
        this.mClosed = new AtomicBoolean(false);
    }

    @Override // androidx.camera.core.ForwardingImageProxy, androidx.camera.core.ImageProxy, java.lang.AutoCloseable
    public void close() {
        if (this.mClosed.getAndSet(true)) {
            return;
        }
        super.close();
    }
}
