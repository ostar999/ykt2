package com.easefun.polyv.livecommon.module.utils.media;

import android.media.AudioRecord;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/* loaded from: classes3.dex */
public class PLVAudioRecordVolumeHelper {
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(8000, 1, 2);
    private static final int SAMPLE_RATE_IN_HZ = 8000;
    private boolean isGetVoiceRun;
    private OnAudioRecordListener listener;
    private AudioRecord mAudioRecord;
    private Thread thread;
    private final Object mLock = new Object();
    private Handler handler = new Handler(Looper.getMainLooper());

    public interface OnAudioRecordListener {
        void onStartFail(Throwable t2);

        void onStartSuccess();

        void onVolume(int volumeValue);
    }

    private void getNoiseLevel() {
        if (this.isGetVoiceRun) {
            return;
        }
        this.isGetVoiceRun = true;
        this.mAudioRecord = new AudioRecord(1, 8000, 1, 2, BUFFER_SIZE);
        Thread thread = new Thread(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.media.PLVAudioRecordVolumeHelper.1
            @Override // java.lang.Runnable
            public void run() throws IllegalStateException {
                final int i2;
                try {
                    PLVAudioRecordVolumeHelper.this.mAudioRecord.startRecording();
                    if (PLVAudioRecordVolumeHelper.this.listener != null) {
                        PLVAudioRecordVolumeHelper.this.handler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.media.PLVAudioRecordVolumeHelper.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                if (PLVAudioRecordVolumeHelper.this.listener != null) {
                                    PLVAudioRecordVolumeHelper.this.listener.onStartSuccess();
                                }
                            }
                        });
                    }
                    int i3 = PLVAudioRecordVolumeHelper.BUFFER_SIZE;
                    short[] sArr = new short[i3];
                    while (PLVAudioRecordVolumeHelper.this.isGetVoiceRun) {
                        int i4 = PLVAudioRecordVolumeHelper.this.mAudioRecord.read(sArr, 0, PLVAudioRecordVolumeHelper.BUFFER_SIZE);
                        long j2 = 0;
                        for (int i5 = 0; i5 < i3; i5++) {
                            short s2 = sArr[i5];
                            j2 += s2 * s2;
                        }
                        double dLog10 = Math.log10(j2 / i4) * 10.0d;
                        if (dLog10 >= 90.3d) {
                            i2 = 100;
                        } else {
                            double d3 = 45.15f;
                            i2 = dLog10 <= d3 ? 0 : (int) (((dLog10 - d3) / d3) * 100.0d);
                        }
                        if (PLVAudioRecordVolumeHelper.this.listener != null) {
                            PLVAudioRecordVolumeHelper.this.handler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.media.PLVAudioRecordVolumeHelper.1.3
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (PLVAudioRecordVolumeHelper.this.listener != null) {
                                        PLVAudioRecordVolumeHelper.this.listener.onVolume(i2);
                                    }
                                }
                            });
                        }
                        Log.d("audioRecord", dLog10 + "dB*" + i2);
                        synchronized (PLVAudioRecordVolumeHelper.this.mLock) {
                            try {
                                PLVAudioRecordVolumeHelper.this.mLock.wait(500L);
                            } catch (InterruptedException unused) {
                            }
                        }
                    }
                    try {
                        PLVAudioRecordVolumeHelper.this.mAudioRecord.stop();
                        PLVAudioRecordVolumeHelper.this.mAudioRecord.release();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    PLVAudioRecordVolumeHelper.this.mAudioRecord = null;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    PLVAudioRecordVolumeHelper.this.isGetVoiceRun = false;
                    if (PLVAudioRecordVolumeHelper.this.listener != null) {
                        PLVAudioRecordVolumeHelper.this.handler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.media.PLVAudioRecordVolumeHelper.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (PLVAudioRecordVolumeHelper.this.listener != null) {
                                    PLVAudioRecordVolumeHelper.this.listener.onStartFail(e3);
                                }
                            }
                        });
                    }
                }
            }
        });
        this.thread = thread;
        thread.start();
    }

    public void setOnGetVolumeListener(OnAudioRecordListener listener) {
        this.listener = listener;
    }

    public void start() {
        getNoiseLevel();
    }

    public void stop() {
        this.isGetVoiceRun = false;
        Thread thread = this.thread;
        if (thread != null) {
            thread.interrupt();
        }
    }
}
