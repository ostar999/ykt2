package org.wrtca.api;

import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public interface AudioSink {
    @CalledByNative
    void onData(byte[] bArr, int i2, int i3, int i4, int i5);

    @CalledByNative
    void onVolLevel(int i2);
}
