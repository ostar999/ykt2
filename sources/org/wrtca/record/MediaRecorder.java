package org.wrtca.record;

import core.interfaces.AudioResample;
import java.io.FileOutputStream;
import org.wrtca.record.model.MediaObject;

/* loaded from: classes9.dex */
public interface MediaRecorder {
    void onAudioError(int i2, String str);

    void receiveAudioData(byte[] bArr, int i2);

    void startAudioResample(AudioResample audioResample, FileOutputStream fileOutputStream);

    MediaObject.MediaPart startRecord(int i2, String str, long j2);

    void stopAudioResample();

    void stopRecord();
}
