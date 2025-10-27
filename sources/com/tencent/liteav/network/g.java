package com.tencent.liteav.network;

import com.tencent.liteav.basic.structs.TXSNALPacket;

/* loaded from: classes6.dex */
public interface g {
    void onPullAudio(com.tencent.liteav.basic.structs.a aVar);

    void onPullNAL(TXSNALPacket tXSNALPacket);
}
