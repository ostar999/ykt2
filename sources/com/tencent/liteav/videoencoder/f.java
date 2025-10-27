package com.tencent.liteav.videoencoder;

import android.media.MediaFormat;
import com.tencent.liteav.basic.structs.TXSNALPacket;

/* loaded from: classes6.dex */
public interface f {
    void a(int i2, long j2, long j3);

    void a(MediaFormat mediaFormat);

    void a(TXSNALPacket tXSNALPacket, int i2);

    void l(int i2);

    void m(int i2);
}
