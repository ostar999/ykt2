package com.tencent.liteav.videobase.e;

/* loaded from: classes6.dex */
public enum d {
    UNKNOWN(0),
    H264_BASELINE(1),
    H264_MAIN(2),
    H264_HIGH(3),
    VP8(4),
    H264_BASELINE_RPS(11),
    H264_MAIN_RPS(12),
    H264_HIGH_RPS(13),
    H265(14);

    private final int mNativeValue;

    d(int i2) {
        this.mNativeValue = i2;
    }
}
