package com.tencent.liteav.audio;

import android.content.Context;
import com.tencent.liteav.audio.impl.Record.TXCAudioSysRecord;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes6.dex */
public class TXCAudioUGCRecorder implements com.tencent.liteav.audio.impl.Record.c {
    private static final TXCAudioUGCRecorder INSTANCE;
    private static final String TAG = "AudioCenter:TXCAudioUGCRecorder";
    protected Context mContext;
    private WeakReference<g> mWeakRecordListener;
    private final int AAC_SAMPLE_NUM = 1024;
    protected AtomicInteger mSampleRate = new AtomicInteger(48000);
    protected int mChannels = 1;
    protected int mBits = 16;
    protected int mAACFrameLength = ((1 * 1024) * 16) / 8;
    protected int mReverbType = 0;
    protected int mVoiceChangerType = 0;
    protected int mAECType = 0;
    protected boolean mIsEarphoneOn = false;
    private long mLastPTS = 0;
    private float mVolume = 1.0f;
    private com.tencent.liteav.audio.impl.Record.a mBGMRecorder = null;
    private boolean mEnableBGMRecord = false;
    private int mShouldClearAACDataCnt = 0;
    private boolean mCurBGMRecordFlag = false;
    private AtomicReference<Float> mSpeedRate = new AtomicReference<>(Float.valueOf(1.0f));
    private boolean mIsRunning = false;
    private boolean mIsPause = false;
    private boolean mIsMute = false;
    private final List<byte[]> mEncodedAudioList = new ArrayList();

    static {
        com.tencent.liteav.basic.util.h.d();
        INSTANCE = new TXCAudioUGCRecorder();
    }

    private TXCAudioUGCRecorder() {
        TXCAudioSysRecord.getInstance();
        nativeClassInit();
    }

    public static TXCAudioUGCRecorder getInstance() {
        return INSTANCE;
    }

    private native void nativeClassInit();

    private native void nativeEnableMixMode(boolean z2);

    private native void nativeSetChangerType(int i2);

    private native void nativeSetReverbType(int i2);

    private native void nativeSetSpeedRate(float f2);

    private native void nativeSetVolume(float f2);

    private native void nativeStartAudioRecord(int i2, int i3, int i4);

    private native void nativeStopAudioRecord();

    private synchronized void updateAudioEffector() {
        boolean z2 = true;
        if (!this.mEnableBGMRecord && this.mAECType != 1) {
            z2 = false;
        }
        if (!z2) {
            nativeSetReverbType(this.mReverbType);
            nativeSetChangerType(this.mVoiceChangerType);
            if (this.mIsMute) {
                nativeSetVolume(0.0f);
            } else {
                nativeSetVolume(this.mVolume);
            }
        }
        if (z2) {
            nativeSetVolume(0.0f);
        }
        nativeEnableMixMode(z2);
        nativeSetSpeedRate(this.mSpeedRate.get().floatValue());
    }

    public synchronized void clearCache() {
        TXCLog.i(TAG, "clearCache");
        synchronized (this.mEncodedAudioList) {
            this.mEncodedAudioList.clear();
        }
    }

    public void enableBGMRecord(boolean z2) {
        TXCLog.i(TAG, "enableBGMRecord: " + z2);
        if (this.mEnableBGMRecord != z2 && !z2) {
            this.mShouldClearAACDataCnt = 2;
        }
        this.mEnableBGMRecord = z2;
        updateAudioEffector();
    }

    public int getAECType() {
        return this.mAECType;
    }

    public int getChannels() {
        return this.mChannels;
    }

    public synchronized boolean getIsMute() {
        return this.mIsMute;
    }

    public g getListener() {
        WeakReference<g> weakReference = this.mWeakRecordListener;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public int getSampleRate() {
        return this.mSampleRate.get();
    }

    public boolean isPaused() {
        return this.mIsPause;
    }

    public boolean isRecording() {
        return this.mIsRunning;
    }

    @Override // com.tencent.liteav.audio.impl.Record.c
    public void onAudioRecordError(int i2, String str) {
        TXCLog.e(TAG, "sys audio record error: " + i2 + ", " + str);
        g listener = getListener();
        if (listener != null) {
            listener.onRecordError(i2, str);
        }
    }

    @Override // com.tencent.liteav.audio.impl.Record.c
    public void onAudioRecordPCM(byte[] bArr, int i2, long j2) {
        byte[] bArr2;
        long j3 = this.mLastPTS;
        if (j3 >= j2) {
            j2 = 2 + j3;
        }
        do {
            synchronized (this.mEncodedAudioList) {
                if (this.mEncodedAudioList.isEmpty() || this.mIsPause) {
                    bArr2 = null;
                } else {
                    bArr2 = this.mEncodedAudioList.get(0);
                    this.mEncodedAudioList.remove(0);
                    int i3 = this.mShouldClearAACDataCnt;
                    if (i3 > 0) {
                        this.mShouldClearAACDataCnt = i3 - 1;
                        bArr2 = null;
                    }
                }
                if (bArr2 != null) {
                    this.mLastPTS = j2;
                    g listener = getListener();
                    if (listener != null) {
                        listener.onRecordEncData(bArr2, j2, this.mSampleRate.get(), this.mChannels, this.mBits);
                    } else {
                        TXCLog.e(TAG, "onAudioRecordPCM listener is null");
                    }
                    int i4 = this.mSampleRate.get();
                    if (i4 > 0) {
                        j2 += (long) ((this.mSpeedRate.get().floatValue() * 1024000.0f) / i4);
                    }
                }
            }
        } while (bArr2 != null);
    }

    @Override // com.tencent.liteav.audio.impl.Record.c
    public void onAudioRecordStart() {
        TXCLog.i(TAG, "sys audio record start");
    }

    @Override // com.tencent.liteav.audio.impl.Record.c
    public void onAudioRecordStop() {
        TXCLog.i(TAG, "sys audio record stop");
    }

    public void onEncodedData(byte[] bArr) {
        synchronized (this.mEncodedAudioList) {
            this.mEncodedAudioList.add(bArr);
        }
    }

    public void pause() {
        TXCLog.i(TAG, "pause");
        TXCAudioEngine.getInstance().pauseLocalAudio();
        synchronized (this.mEncodedAudioList) {
            this.mIsPause = true;
        }
    }

    public void resume() {
        TXCLog.i(TAG, "resume");
        TXCAudioEngine.getInstance().resumeLocalAudio();
        synchronized (this.mEncodedAudioList) {
            this.mIsPause = false;
        }
        nativeEnableMixMode(this.mEnableBGMRecord);
        if (this.mIsMute || this.mEnableBGMRecord) {
            nativeSetVolume(0.0f);
        } else {
            nativeSetVolume(this.mVolume);
        }
    }

    public void setAECType(int i2, Context context) {
        TXCLog.i(TAG, "setAECType: " + i2);
        this.mAECType = i2;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
    }

    public synchronized void setChangerType(int i2) {
        TXCLog.i(TAG, "setChangerType: " + i2);
        this.mVoiceChangerType = i2;
        nativeSetChangerType(i2);
    }

    public void setChannels(int i2) {
        TXCLog.i(TAG, "setChannels: " + i2);
        this.mChannels = i2;
    }

    public synchronized void setListener(g gVar) {
        if (gVar == null) {
            this.mWeakRecordListener = null;
        } else {
            this.mWeakRecordListener = new WeakReference<>(gVar);
        }
    }

    public void setMute(boolean z2) {
        TXCLog.i(TAG, "setMute: " + z2);
        this.mIsMute = z2;
        if (z2) {
            nativeSetVolume(0.0f);
        } else {
            nativeSetVolume(this.mVolume);
        }
    }

    public synchronized void setReverbType(int i2) {
        TXCLog.i(TAG, "setReverbType: " + i2);
        this.mReverbType = i2;
        nativeSetReverbType(i2);
    }

    public void setSampleRate(int i2) {
        TXCLog.i(TAG, "setSampleRate: " + i2);
        this.mSampleRate.set(i2);
    }

    public synchronized void setSpeedRate(float f2) {
        TXCLog.i(TAG, "setSpeedRate: " + f2);
        this.mSpeedRate.set(Float.valueOf(f2));
        nativeSetSpeedRate(this.mSpeedRate.get().floatValue());
    }

    public synchronized void setVolume(float f2) {
        TXCLog.i(TAG, "setVolume: " + f2);
        this.mVolume = f2;
        if (this.mIsMute) {
            nativeSetVolume(0.0f);
        } else {
            nativeSetVolume(f2);
        }
    }

    public int startRecord(Context context) {
        TXCLog.i(TAG, "startRecord");
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
        updateAudioEffector();
        TXCAudioSysRecord.getInstance().setAudioRecordListener(this);
        nativeStartAudioRecord(this.mSampleRate.get(), this.mChannels, this.mBits);
        this.mIsRunning = true;
        this.mLastPTS = 0L;
        return 0;
    }

    public int stopRecord() throws InterruptedException {
        TXCLog.i(TAG, "stopRecord");
        TXCAudioSysRecord.getInstance().setAudioRecordListener(null);
        com.tencent.liteav.audio.impl.Record.a aVar = this.mBGMRecorder;
        if (aVar != null) {
            aVar.a();
            this.mBGMRecorder = null;
        }
        nativeStopAudioRecord();
        nativeEnableMixMode(false);
        nativeSetVolume(1.0f);
        synchronized (this.mEncodedAudioList) {
            this.mEncodedAudioList.clear();
        }
        this.mIsRunning = false;
        this.mLastPTS = 0L;
        this.mIsPause = false;
        this.mIsMute = false;
        this.mShouldClearAACDataCnt = 0;
        return 0;
    }
}
