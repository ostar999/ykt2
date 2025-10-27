package com.tencent.liteav.videodecoder;

import com.tencent.liteav.basic.structs.TXSVideoFrame;

/* loaded from: classes6.dex */
public interface h {
    void onDecodeFailed(int i2);

    void onDecodeFrame(TXSVideoFrame tXSVideoFrame, int i2, int i3, long j2, long j3, int i4);

    void onDecoderChange(String str, boolean z2);

    void onVideoSizeChange(int i2, int i3);
}
