package org.wrtca.record;

import android.media.AudioRecord;

/* loaded from: classes9.dex */
public class AudioRecorder extends Thread {
    private MediaRecorder mMediaRecorder;
    private AudioRecord mAudioRecord = null;
    private int mSampleRate = 44100;

    public AudioRecorder(MediaRecorder mediaRecorder) {
        this.mMediaRecorder = mediaRecorder;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws IllegalStateException {
        int i2 = this.mSampleRate;
        if (i2 != 8000 && i2 != 16000 && i2 != 22050 && i2 != 44100) {
            this.mMediaRecorder.onAudioError(1, "sampleRate not support.");
            return;
        }
        int minBufferSize = AudioRecord.getMinBufferSize(i2, 16, 2);
        if (-2 == minBufferSize) {
            this.mMediaRecorder.onAudioError(2, "parameters are not supported by the hardware.");
            return;
        }
        AudioRecord audioRecord = new AudioRecord(1, this.mSampleRate, 16, 2, minBufferSize);
        this.mAudioRecord = audioRecord;
        try {
            audioRecord.startRecording();
            byte[] bArr = new byte[2048];
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    int i3 = this.mAudioRecord.read(bArr, 0, 2048);
                    if (i3 > 0) {
                        this.mMediaRecorder.receiveAudioData(bArr, i3);
                    }
                } catch (Exception e2) {
                    this.mMediaRecorder.onAudioError(0, e2.getMessage());
                }
            }
            this.mAudioRecord.release();
            this.mAudioRecord = null;
        } catch (IllegalStateException unused) {
            this.mMediaRecorder.onAudioError(0, "startRecording failed.");
        }
    }

    public void setSampleRate(int i2) {
        this.mSampleRate = i2;
    }
}
