package androidx.camera.camera2.internal;

import androidx.camera.core.SafeCloseImageReaderProxy;

/* loaded from: classes.dex */
public final /* synthetic */ class o2 implements Runnable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ SafeCloseImageReaderProxy f1395c;

    public /* synthetic */ o2(SafeCloseImageReaderProxy safeCloseImageReaderProxy) {
        this.f1395c = safeCloseImageReaderProxy;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f1395c.safeClose();
    }
}
