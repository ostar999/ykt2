package org.wrtca.customize;

import java.nio.ByteBuffer;

/* loaded from: classes9.dex */
public interface RtcNativeOperation {
    ByteBuffer createNativeByteBuffer(int i2);

    void releaseNativeByteBuffer(ByteBuffer byteBuffer);
}
