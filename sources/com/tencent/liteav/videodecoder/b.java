package com.tencent.liteav.videodecoder;

import android.view.Surface;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public interface b {
    int GetDecodeCost();

    int config(Surface surface);

    void decode(TXSNALPacket tXSNALPacket);

    void enableLimitDecCache(boolean z2);

    void setListener(h hVar);

    void setNotifyListener(WeakReference<com.tencent.liteav.basic.b.b> weakReference);

    int start(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z2, boolean z3);

    void stop();
}
