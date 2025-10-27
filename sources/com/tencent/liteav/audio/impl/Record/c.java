package com.tencent.liteav.audio.impl.Record;

/* loaded from: classes6.dex */
public interface c {
    void onAudioRecordError(int i2, String str);

    void onAudioRecordPCM(byte[] bArr, int i2, long j2);

    void onAudioRecordStart();

    void onAudioRecordStop();
}
