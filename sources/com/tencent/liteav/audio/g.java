package com.tencent.liteav.audio;

/* loaded from: classes6.dex */
public interface g {
    void onRecordEncData(byte[] bArr, long j2, int i2, int i3, int i4);

    void onRecordError(int i2, String str);

    void onRecordPcmData(byte[] bArr, long j2, int i2, int i3, int i4);

    void onRecordRawPcmData(byte[] bArr, long j2, int i2, int i3, int i4, boolean z2);
}
