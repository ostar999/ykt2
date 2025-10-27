package com.tencent.liteav.audio.impl.Play;

import android.media.AudioTrack;
import android.os.Process;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class TXCAudioPlayout {
    private static final String TAG = "TXCAudioPlayout";
    private AudioTrack mAudioTrack;
    private byte[] mPlayBuffer;

    private static AudioTrack createStartedAudioTrack(int i2, int i3, int i4, int i5) throws IllegalStateException {
        AudioTrack audioTrack;
        try {
            audioTrack = new AudioTrack(i5, i2, i3, 2, i4, 1);
        } catch (Exception unused) {
            audioTrack = null;
        }
        try {
            if (audioTrack.getState() != 1) {
                throw new RuntimeException("AudioTrack is not initialized.");
            }
            audioTrack.play();
            TXCLog.i(TAG, "create AudioTrack success. samplerate: %d, channelConfig: %d, bufferSize: %d, streamType: %s", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), streamTypeToText(i5));
            return audioTrack;
        } catch (Exception unused2) {
            TXCLog.w(TAG, "create AudioTrack failed. samplerate: %d, channelConfig: %d, bufferSize: %d, streamType: %s", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), streamTypeToText(i5));
            tearDownAudioTrack(audioTrack);
            return null;
        }
    }

    private static String streamTypeToText(int i2) {
        if (i2 == 0) {
            return "STREAM_VOICE_CALL";
        }
        if (i2 == 3) {
            return "STREAM_MUSIC";
        }
        return "UNSUPPORT " + i2;
    }

    private static void tearDownAudioTrack(AudioTrack audioTrack) throws IllegalStateException {
        if (audioTrack == null) {
            return;
        }
        try {
            if (audioTrack.getPlayState() == 3) {
                audioTrack.stop();
                audioTrack.flush();
            }
            audioTrack.release();
        } catch (Exception e2) {
            TXCLog.e(TAG, "stop AudioTrack failed.", e2);
        }
    }

    public boolean startPlayout(int i2, int i3, int i4, int i5) throws SecurityException, IllegalArgumentException {
        int[] iArr = {i5, 0, 3, 1};
        int i6 = i3 == 1 ? 4 : 12;
        int minBufferSize = AudioTrack.getMinBufferSize(i2, i6, 2);
        for (int i7 = 0; i7 < 4 && this.mAudioTrack == null; i7++) {
            int i8 = iArr[i7];
            for (int i9 = 1; i9 <= 2 && this.mAudioTrack == null; i9++) {
                if (minBufferSize * i9 >= i4 * 4 || i9 >= 2) {
                    this.mAudioTrack = createStartedAudioTrack(i2, i6, i4, i8);
                }
            }
        }
        Thread.currentThread().setName("tx_audio_playout");
        Process.setThreadPriority(-19);
        return this.mAudioTrack != null;
    }

    public void stopPlayout() throws IllegalStateException {
        tearDownAudioTrack(this.mAudioTrack);
        this.mAudioTrack = null;
    }

    public int write(ByteBuffer byteBuffer, int i2, int i3) {
        int iWrite;
        if (this.mAudioTrack == null) {
            return -1;
        }
        byteBuffer.position(i2);
        if (TXCBuild.VersionInt() >= 21) {
            iWrite = this.mAudioTrack.write(byteBuffer, i3, 0);
        } else {
            byte[] bArr = this.mPlayBuffer;
            if (bArr == null || bArr.length < i3) {
                this.mPlayBuffer = new byte[i3];
            }
            byteBuffer.get(this.mPlayBuffer, 0, i3);
            iWrite = this.mAudioTrack.write(this.mPlayBuffer, 0, i3);
        }
        if (iWrite > 0) {
            return iWrite;
        }
        TXCLog.e(TAG, "write audio data to AudioTrack failed. " + iWrite);
        return -1;
    }
}
