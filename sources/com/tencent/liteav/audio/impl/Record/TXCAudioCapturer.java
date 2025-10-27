package com.tencent.liteav.audio.impl.Record;

import android.media.AudioRecord;
import android.os.Process;
import com.tencent.liteav.basic.log.TXCLog;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class TXCAudioCapturer {
    private static final String TAG = "TXCAudioCapturer";
    private AudioRecord mAudioRecord;

    private static String audioSourceToText(int i2) {
        if (i2 == 1) {
            return "MIC";
        }
        if (i2 == 7) {
            return "VOICE_COMMUNICATION";
        }
        return "UNSUPPORT " + i2;
    }

    private static AudioRecord createStartedAudioRecord(int i2, int i3, int i4, int i5) throws IllegalStateException {
        AudioRecord audioRecord;
        try {
            audioRecord = new AudioRecord(i2, i3, i4, 2, i5);
            try {
                if (audioRecord.getState() != 1) {
                    throw new RuntimeException("AudioRecord is not initialized.");
                }
                audioRecord.startRecording();
                return audioRecord;
            } catch (Exception unused) {
                TXCLog.w(TAG, "create AudioRecord failed. source: %s, samplerate: %d, channelConfig: %d, bufferSize: %d", audioSourceToText(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
                tearDownAudioRecord(audioRecord);
                return null;
            }
        } catch (Exception unused2) {
            audioRecord = null;
        }
    }

    private static void tearDownAudioRecord(AudioRecord audioRecord) throws IllegalStateException {
        if (audioRecord == null) {
            return;
        }
        try {
            if (audioRecord.getRecordingState() == 3) {
                audioRecord.stop();
            }
            audioRecord.release();
        } catch (Exception e2) {
            TXCLog.e(TAG, "stop AudioRecord failed.", e2);
        }
    }

    public int read(ByteBuffer byteBuffer, int i2) {
        if (this.mAudioRecord == null) {
            return -1;
        }
        byteBuffer.position(0);
        int i3 = this.mAudioRecord.read(byteBuffer, i2);
        if (i3 > 0) {
            return i3;
        }
        TXCLog.e(TAG, "read failed, %d", Integer.valueOf(i3));
        return -1;
    }

    public boolean startRecord(int i2, int i3, int i4, int i5) throws SecurityException, IllegalArgumentException {
        int[] iArr = {i2, 1, 5, 0};
        int i6 = i4 == 1 ? 16 : 12;
        int minBufferSize = AudioRecord.getMinBufferSize(i3, i6, 2);
        for (int i7 = 0; i7 < 4 && this.mAudioRecord == null; i7++) {
            int i8 = iArr[i7];
            for (int i9 = 1; i9 <= 2 && this.mAudioRecord == null; i9++) {
                int i10 = minBufferSize * i9;
                if (i10 >= i5 * 4 || i9 >= 2) {
                    this.mAudioRecord = createStartedAudioRecord(i8, i3, i6, i10);
                }
            }
        }
        Thread.currentThread().setName("tx_audio_capturer");
        Process.setThreadPriority(-19);
        return this.mAudioRecord != null;
    }

    public void stopRecord() throws IllegalStateException {
        tearDownAudioRecord(this.mAudioRecord);
        this.mAudioRecord = null;
    }
}
