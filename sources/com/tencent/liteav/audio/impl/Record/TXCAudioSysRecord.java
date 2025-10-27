package com.tencent.liteav.audio.impl.Record;

import android.content.Context;
import android.media.AudioRecord;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes6.dex */
public class TXCAudioSysRecord implements Runnable {
    private static final String TAG = "AudioCenter:" + TXCAudioSysRecord.class.getSimpleName();
    private static TXCAudioSysRecord instance = null;
    private Context mContext;
    private AudioRecord mMic;
    private WeakReference<c> mWeakRefListener;
    private int mSampleRate = 48000;
    private int mChannels = 1;
    private int mBits = 16;
    private int mAECType = 0;
    private byte[] mRecordBuffer = null;
    private Thread mRecordThread = null;
    private boolean mIsRunning = false;
    private boolean mIsCapFirstFrame = false;
    private boolean mSendMuteData = false;
    private AtomicBoolean mPause = new AtomicBoolean(false);
    private Object threadMutex = new Object();

    private TXCAudioSysRecord() {
        nativeClassInit();
    }

    public static TXCAudioSysRecord getInstance() {
        if (instance == null) {
            synchronized (TXCAudioSysRecord.class) {
                if (instance == null) {
                    instance = new TXCAudioSysRecord();
                }
            }
        }
        return instance;
    }

    private void init() throws IllegalStateException {
        int i2;
        AudioRecord audioRecord;
        int i3 = this.mSampleRate;
        int i4 = this.mChannels;
        int i5 = this.mBits;
        int i6 = this.mAECType;
        String str = TAG;
        TXCLog.i(str, String.format("audio record sampleRate = %d, channels = %d, bits = %d, aectype = %d", Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)));
        int i7 = i4 == 1 ? 16 : 12;
        int i8 = i5 == 8 ? 3 : 2;
        int minBufferSize = AudioRecord.getMinBufferSize(i3, i7, i8);
        try {
            TXCLog.i(str, "audio record type: system normal");
            i2 = minBufferSize;
        } catch (IllegalArgumentException e2) {
            e = e2;
            i2 = minBufferSize;
        }
        try {
            this.mMic = new AudioRecord(1, i3, i7, i8, minBufferSize * 2);
        } catch (IllegalArgumentException e3) {
            e = e3;
            TXCLog.e(TAG, "create AudioRecord failed.", e);
            audioRecord = this.mMic;
            if (audioRecord != null) {
            }
            TXCLog.e(TAG, "audio record: initialize the mic failed.");
            uninit();
            onRecordError(-1, "microphone permission denied!");
            return;
        }
        audioRecord = this.mMic;
        if (audioRecord != null || audioRecord.getState() != 1) {
            TXCLog.e(TAG, "audio record: initialize the mic failed.");
            uninit();
            onRecordError(-1, "microphone permission denied!");
            return;
        }
        int i9 = ((i4 * 1024) * i5) / 8;
        if (i9 > i2) {
            this.mRecordBuffer = new byte[i2];
        } else {
            this.mRecordBuffer = new byte[i9];
        }
        TXCLog.i(TAG, String.format("audio record: mic open rate=%dHZ, channels=%d, bits=%d, buffer=%d/%d, state=%d", Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i2), Integer.valueOf(this.mRecordBuffer.length), Integer.valueOf(this.mMic.getState())));
        AudioRecord audioRecord2 = this.mMic;
        if (audioRecord2 != null) {
            try {
                audioRecord2.startRecording();
            } catch (Exception e4) {
                TXCLog.e(TAG, "mic startRecording failed.", e4);
                onRecordError(-1, "start recording failed!");
            }
        }
    }

    private native void nativeClassInit();

    private native void nativeSendSysRecordAudioData(byte[] bArr, int i2, int i3, int i4, int i5);

    private void onRecordError(int i2, String str) {
        c cVar;
        synchronized (this) {
            WeakReference<c> weakReference = this.mWeakRefListener;
            cVar = weakReference != null ? weakReference.get() : null;
        }
        if (cVar != null) {
            cVar.onAudioRecordError(i2, str);
        } else {
            TXCLog.e(TAG, "onRecordError:no callback");
        }
    }

    private void onRecordPcmData(byte[] bArr, int i2, long j2) {
        WeakReference<c> weakReference = this.mWeakRefListener;
        c cVar = weakReference != null ? weakReference.get() : null;
        if (cVar != null) {
            cVar.onAudioRecordPCM(bArr, i2, j2);
        } else {
            TXCLog.e(TAG, "onRecordPcmData:no callback");
        }
    }

    private void onRecordStart() {
        c cVar;
        synchronized (this) {
            WeakReference<c> weakReference = this.mWeakRefListener;
            cVar = weakReference != null ? weakReference.get() : null;
        }
        if (cVar != null) {
            cVar.onAudioRecordStart();
        } else {
            TXCLog.e(TAG, "onRecordStart:no callback");
        }
    }

    private void onRecordStop() {
        c cVar;
        synchronized (this) {
            WeakReference<c> weakReference = this.mWeakRefListener;
            cVar = weakReference != null ? weakReference.get() : null;
        }
        if (cVar != null) {
            cVar.onAudioRecordStop();
        } else {
            TXCLog.e(TAG, "onRecordStop:no callback");
        }
    }

    private void uninit() throws IllegalStateException {
        if (this.mMic != null) {
            TXCLog.i(TAG, "stop mic");
            try {
                this.mMic.setRecordPositionUpdateListener(null);
                this.mMic.stop();
                this.mMic.release();
            } catch (Exception e2) {
                TXCLog.e(TAG, "stop AudioRecord failed.", e2);
            }
        }
        this.mMic = null;
        this.mRecordBuffer = null;
        this.mIsCapFirstFrame = false;
    }

    public synchronized boolean isRecording() {
        return this.mIsRunning;
    }

    public void pause(boolean z2) {
        TXCLog.i(TAG, "system audio record pause");
        this.mPause.set(true);
        this.mSendMuteData = z2;
    }

    public void resume() {
        TXCLog.i(TAG, "system audio record resume");
        this.mPause.set(false);
    }

    @Override // java.lang.Runnable
    public void run() throws IllegalStateException {
        int i2;
        if (!this.mIsRunning) {
            TXCLog.w(TAG, "audio record: abandom start audio sys record thread!");
            return;
        }
        onRecordStart();
        TXCLog.i(TAG, "start capture audio data ...,mIsRunning:" + this.mIsRunning + " Thread.interrupted:" + Thread.interrupted() + " mMic:" + this.mMic);
        init();
        loop0: while (true) {
            i2 = 0;
            int i3 = 0;
            while (this.mIsRunning && !Thread.interrupted() && this.mMic != null && i2 <= 5) {
                System.currentTimeMillis();
                AudioRecord audioRecord = this.mMic;
                byte[] bArr = this.mRecordBuffer;
                int i4 = audioRecord.read(bArr, i3, bArr.length - i3);
                if (i4 == this.mRecordBuffer.length - i3) {
                    boolean z2 = true;
                    if (!this.mIsCapFirstFrame) {
                        onRecordError(-6, "First frame captured#");
                        this.mIsCapFirstFrame = true;
                    }
                    if (this.mSendMuteData) {
                        Arrays.fill(this.mRecordBuffer, (byte) 0);
                    }
                    if (this.mPause.get() && !this.mSendMuteData) {
                        z2 = false;
                    }
                    if (z2) {
                        byte[] bArr2 = this.mRecordBuffer;
                        onRecordPcmData(bArr2, bArr2.length, TXCTimeUtil.getTimeTick());
                        byte[] bArr3 = this.mRecordBuffer;
                        nativeSendSysRecordAudioData(bArr3, bArr3.length, this.mSampleRate, this.mChannels, this.mBits);
                    }
                } else if (i4 <= 0) {
                    TXCLog.e(TAG, "read pcm error, len =" + i4);
                    i2++;
                } else {
                    i3 += i4;
                }
            }
            break loop0;
        }
        TXCLog.d(TAG, "stop capture audio data ...,mIsRunning:" + this.mIsRunning + " mMic:" + this.mMic + " nFailedCount:" + i2);
        uninit();
        if (i2 > 5) {
            onRecordError(-1, "read data failed!");
        } else {
            onRecordStop();
        }
    }

    public synchronized void setAudioRecordListener(c cVar) {
        if (cVar == null) {
            this.mWeakRefListener = null;
        } else {
            this.mWeakRefListener = new WeakReference<>(cVar);
        }
    }

    public void start(int i2, int i3, int i4) {
        String str = TAG;
        TXCLog.i(str, "start");
        synchronized (this.threadMutex) {
            stop();
            this.mSampleRate = i2;
            this.mChannels = i3;
            this.mBits = i4;
            this.mIsRunning = true;
            Thread thread = new Thread(this, "AudioSysRecord Thread");
            this.mRecordThread = thread;
            thread.start();
        }
        TXCLog.i(str, "start ok");
    }

    public void stop() {
        String str;
        TXCLog.i(TAG, "stop");
        synchronized (this.threadMutex) {
            this.mIsRunning = false;
            long jCurrentTimeMillis = System.currentTimeMillis();
            Thread thread = this.mRecordThread;
            if (thread == null || !thread.isAlive() || Thread.currentThread().getId() == this.mRecordThread.getId()) {
                str = TAG;
                TXCLog.i(str, "stop ok,stop record cost time(MS): " + (System.currentTimeMillis() - jCurrentTimeMillis));
                this.mRecordThread = null;
            } else {
                try {
                    this.mRecordThread.join();
                } catch (Exception e2) {
                    TXCLog.e(TAG, "record stop Exception: " + e2.getMessage());
                }
                str = TAG;
                TXCLog.i(str, "stop ok,stop record cost time(MS): " + (System.currentTimeMillis() - jCurrentTimeMillis));
                this.mRecordThread = null;
            }
        }
        TXCLog.i(str, "stop ok");
    }
}
