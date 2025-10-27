package com.tencent.liteav.audio;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.liteav.audio.TXAudioEffectManager;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class TXAudioEffectManagerImpl implements TXAudioEffectManager {
    private static final int EFFECT_PLAYER_ID_TYPE = 2;
    private static final int NEW_BGM_PLAYER_ID_TYPE = 1;
    private static final int OLD_BGM_PLAYER_ID_TYPE = 0;
    private static final String TAG = "AudioCenter:TXAudioEffectManager";
    private WeakReference<TXAudioEffectManagerListener> mAudioEffectManagerListener;
    private final int mIdType;
    private final List<Long> mPlayingMusicIDList;
    private static final HashMap<Long, TXAudioEffectManager.TXMusicPlayObserver> mMusicObserverMap = new HashMap<>();
    private static final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private static final c sCopyrightedMediaProcessor = new c();

    public static class AudioEffectManagerAutoCacheHolder {
        private static final TXAudioEffectManagerImpl INSTANCE = new TXAudioEffectManagerImpl(1);

        private AudioEffectManagerAutoCacheHolder() {
        }
    }

    public static class AudioEffectManagerCacheHolder {
        private static final TXAudioEffectManagerImpl INSTANCE = new TXAudioEffectManagerImpl(2);

        private AudioEffectManagerCacheHolder() {
        }
    }

    public static class AudioEffectManagerHolder {
        private static final TXAudioEffectManagerImpl INSTANCE = new TXAudioEffectManagerImpl(0);

        private AudioEffectManagerHolder() {
        }
    }

    public interface TXAudioEffectManagerListener {
        void onSwitchVoiceEarMonitor(boolean z2);
    }

    static {
        com.tencent.liteav.basic.util.h.d();
        nativeClassInit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long convertIdToInt64(int i2, int i3) {
        return i3 | (i2 << 32);
    }

    public static TXAudioEffectManagerImpl getAutoCacheHolder() {
        return AudioEffectManagerAutoCacheHolder.INSTANCE;
    }

    public static TXAudioEffectManagerImpl getCacheInstance() {
        return AudioEffectManagerCacheHolder.INSTANCE;
    }

    public static TXAudioEffectManagerImpl getInstance() {
        return AudioEffectManagerHolder.INSTANCE;
    }

    private static native void nativeClassInit();

    private native long nativeGetAvailableBGMBytes(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native long nativeGetCurrentPositionInMs(long j2);

    private native long nativeGetDurationMS(long j2);

    private static native long nativeGetDurationMSByPath(String str);

    private native void nativePause(long j2);

    private native void nativeResume(long j2);

    private native void nativeSeekToPosition(long j2, long j3);

    private native void nativeSeekToTime(long j2, int i2);

    private native void nativeSetAllVolume(int i2);

    private native void nativeSetChangerType(long j2, int i2);

    private native void nativeSetMuteDataDurationToPublish(long j2, int i2);

    private native void nativeSetPitch(long j2, float f2);

    private native void nativeSetPlayoutSpeedRate(long j2, float f2);

    private native void nativeSetPlayoutVolume(long j2, int i2);

    private native void nativeSetPublishVolume(long j2, int i2);

    private native void nativeSetReverbType(long j2, int i2);

    private native void nativeSetSpeedRate(long j2, float f2);

    private native void nativeSetVolume(long j2, int i2);

    private native boolean nativeStartPlay(long j2, String str, int i2, boolean z2, boolean z3, boolean z4);

    private native void nativeStartPlayRange(long j2, long j3, long j4);

    private native void nativeStopPlay(long j2);

    public static void onEffectFinish(final long j2, final int i2) {
        mMainHandler.post(new Runnable() { // from class: com.tencent.liteav.audio.TXAudioEffectManagerImpl.3
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TXCLog.i(TXAudioEffectManagerImpl.TAG, "onEffectFinish -> effect id = " + ((int) j2) + ", errCode = " + i2);
                if (TXAudioEffectManagerImpl.mMusicObserverMap.get(Long.valueOf(j2)) != null) {
                    ((TXAudioEffectManager.TXMusicPlayObserver) TXAudioEffectManagerImpl.mMusicObserverMap.get(Long.valueOf(j2))).onComplete((int) j2, i2);
                }
                TXAudioEffectManagerImpl.sCopyrightedMediaProcessor.f(j2, TXAudioEffectManagerImpl.getAutoCacheHolder().nativeGetCurrentPositionInMs(j2));
            }
        });
    }

    public static void onEffectLoop(final long j2, final long j3) {
        mMainHandler.post(new Runnable() { // from class: com.tencent.liteav.audio.TXAudioEffectManagerImpl.6
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TXCLog.i(TXAudioEffectManagerImpl.TAG, "onEffectLoop -> id = " + ((int) j2) + " loopCountRemain = " + j3);
                TXAudioEffectManagerImpl.sCopyrightedMediaProcessor.e(j2, TXAudioEffectManagerImpl.getAutoCacheHolder().nativeGetCurrentPositionInMs(j2));
            }
        });
    }

    public static void onEffectProgress(final long j2, final long j3, final long j4) {
        mMainHandler.post(new Runnable() { // from class: com.tencent.liteav.audio.TXAudioEffectManagerImpl.5
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (TXAudioEffectManagerImpl.mMusicObserverMap.get(Long.valueOf(j2)) != null) {
                    ((TXAudioEffectManager.TXMusicPlayObserver) TXAudioEffectManagerImpl.mMusicObserverMap.get(Long.valueOf(j2))).onPlayProgress((int) j2, j3, j4);
                }
                TXAudioEffectManagerImpl.sCopyrightedMediaProcessor.a(j2, TXAudioEffectManagerImpl.getAutoCacheHolder().nativeGetCurrentPositionInMs(j2));
            }
        });
    }

    public static void onEffectStart(final long j2, final int i2) {
        mMainHandler.post(new Runnable() { // from class: com.tencent.liteav.audio.TXAudioEffectManagerImpl.4
            @Override // java.lang.Runnable
            public void run() {
                TXCLog.i(TXAudioEffectManagerImpl.TAG, "onEffectStart -> effect id = " + ((int) j2) + ", errCode = " + i2);
                if (TXAudioEffectManagerImpl.mMusicObserverMap.get(Long.valueOf(j2)) != null) {
                    ((TXAudioEffectManager.TXMusicPlayObserver) TXAudioEffectManagerImpl.mMusicObserverMap.get(Long.valueOf(j2))).onStart((int) j2, i2);
                }
            }
        });
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void enableVoiceEarMonitor(boolean z2) {
        TXAudioEffectManagerListener tXAudioEffectManagerListener;
        TXCLog.i(TAG, "enableVoiceEarMonitor enable:" + z2);
        WeakReference<TXAudioEffectManagerListener> weakReference = this.mAudioEffectManagerListener;
        if (weakReference != null && (tXAudioEffectManagerListener = weakReference.get()) != null) {
            tXAudioEffectManagerListener.onSwitchVoiceEarMonitor(z2);
        }
        TXCAudioEngine.enableAudioEarMonitoring(z2);
    }

    public long getAvailableBGMBytes(int i2) {
        return nativeGetAvailableBGMBytes(convertIdToInt64(this.mIdType, i2));
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public long getMusicCurrentPosInMS(int i2) {
        return nativeGetCurrentPositionInMs(convertIdToInt64(this.mIdType, i2));
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public long getMusicDurationInMS(String str) {
        String strA = sCopyrightedMediaProcessor.a(str);
        if (TextUtils.isEmpty(strA)) {
            return -1L;
        }
        return nativeGetDurationMSByPath(strA);
    }

    public long getMusicDurationInMSById(int i2) {
        return nativeGetDurationMS(convertIdToInt64(this.mIdType, i2));
    }

    public void interruptAllMusics() {
        TXCLog.i(TAG, "interruptAllMusics");
        Iterator<Long> it = this.mPlayingMusicIDList.iterator();
        while (it.hasNext()) {
            nativePause(it.next().longValue());
        }
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void pausePlayMusic(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "pausePlayMusic id:" + i2);
        long jConvertIdToInt64 = convertIdToInt64(this.mIdType, i2);
        this.mPlayingMusicIDList.remove(Long.valueOf(jConvertIdToInt64));
        sCopyrightedMediaProcessor.b(jConvertIdToInt64, nativeGetCurrentPositionInMs(jConvertIdToInt64));
        nativePause(jConvertIdToInt64);
    }

    public boolean preloadMusic(TXAudioEffectManager.AudioMusicParam audioMusicParam) {
        TXCLog.i("AudioBGMPlayer", "loadMusic");
        long jMax = Math.max(audioMusicParam.startTimeMS, 0L);
        long jMax2 = Math.max(audioMusicParam.endTimeMS, 0L);
        String strA = sCopyrightedMediaProcessor.a(audioMusicParam.path);
        if (TextUtils.isEmpty(strA)) {
            return false;
        }
        long jConvertIdToInt64 = convertIdToInt64(this.mIdType, audioMusicParam.id);
        if (!this.mPlayingMusicIDList.contains(Long.valueOf(jConvertIdToInt64))) {
            this.mPlayingMusicIDList.add(Long.valueOf(jConvertIdToInt64));
        }
        nativeStartPlayRange(jConvertIdToInt64, jMax, jMax2);
        return nativeStartPlay(jConvertIdToInt64, strA, audioMusicParam.loopCount, audioMusicParam.publish, audioMusicParam.isShortFile, true);
    }

    public void recoverAllMusics() {
        TXCLog.i(TAG, "recoverAllMusics");
        Iterator<Long> it = this.mPlayingMusicIDList.iterator();
        while (it.hasNext()) {
            nativeResume(it.next().longValue());
        }
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void resumePlayMusic(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "resumePlayMusic id:" + i2);
        long jConvertIdToInt64 = convertIdToInt64(this.mIdType, i2);
        if (!this.mPlayingMusicIDList.contains(Long.valueOf(jConvertIdToInt64))) {
            this.mPlayingMusicIDList.add(Long.valueOf(jConvertIdToInt64));
        }
        sCopyrightedMediaProcessor.c(jConvertIdToInt64, nativeGetCurrentPositionInMs(jConvertIdToInt64));
        nativeResume(jConvertIdToInt64);
    }

    public void seekMusicToPosInBytes(int i2, long j2) {
        nativeSeekToPosition(convertIdToInt64(this.mIdType, i2), j2);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void seekMusicToPosInMS(int i2, int i3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        long jConvertIdToInt64 = convertIdToInt64(this.mIdType, i2);
        sCopyrightedMediaProcessor.d(jConvertIdToInt64, i3);
        nativeSeekToTime(jConvertIdToInt64, i3);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void setAllMusicVolume(int i2) {
        nativeSetAllVolume(i2);
    }

    public void setAudioEffectManagerListener(TXAudioEffectManagerListener tXAudioEffectManagerListener) {
        this.mAudioEffectManagerListener = new WeakReference<>(tXAudioEffectManagerListener);
    }

    public void setAudioPlayoutVolume(int i2) {
        TXCAudioEngine.getInstance().setMixingPlayoutVolume(i2 / 100.0f);
    }

    public void setMusicChangerType(int i2, int i3) {
        TXCLog.i(TAG, "setMusicChangerType id:" + i2 + " voiceChangerType:" + i3);
        nativeSetChangerType(convertIdToInt64(this.mIdType, i2), i3);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void setMusicObserver(final int i2, final TXAudioEffectManager.TXMusicPlayObserver tXMusicPlayObserver) {
        Runnable runnable = new Runnable() { // from class: com.tencent.liteav.audio.TXAudioEffectManagerImpl.1
            @Override // java.lang.Runnable
            public void run() {
                if (tXMusicPlayObserver == null) {
                    TXAudioEffectManagerImpl.mMusicObserverMap.remove(Long.valueOf(TXAudioEffectManagerImpl.convertIdToInt64(TXAudioEffectManagerImpl.this.mIdType, i2)));
                } else {
                    TXAudioEffectManagerImpl.mMusicObserverMap.put(Long.valueOf(TXAudioEffectManagerImpl.convertIdToInt64(TXAudioEffectManagerImpl.this.mIdType, i2)), tXMusicPlayObserver);
                }
                TXCLog.i(TXAudioEffectManagerImpl.TAG, "setMusicObserver map count: %d", Integer.valueOf(TXAudioEffectManagerImpl.mMusicObserverMap.size()));
            }
        };
        Looper looperMyLooper = Looper.myLooper();
        Handler handler = mMainHandler;
        if (looperMyLooper == handler.getLooper()) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void setMusicPitch(int i2, float f2) {
        nativeSetPitch(convertIdToInt64(this.mIdType, i2), f2);
    }

    public void setMusicPlayoutSpeedRate(int i2, float f2) {
        nativeSetPlayoutSpeedRate(convertIdToInt64(this.mIdType, i2), f2);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void setMusicPlayoutVolume(int i2, int i3) {
        nativeSetPlayoutVolume(convertIdToInt64(this.mIdType, i2), i3);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void setMusicPublishVolume(int i2, int i3) {
        nativeSetPublishVolume(convertIdToInt64(this.mIdType, i2), i3);
    }

    public void setMusicReverbType(int i2, int i3) {
        TXCLog.i(TAG, "setMusicReverbType id:" + i2 + " reverbType:" + i3);
        nativeSetReverbType(convertIdToInt64(this.mIdType, i2), i3);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void setMusicSpeedRate(int i2, float f2) {
        nativeSetSpeedRate(convertIdToInt64(this.mIdType, i2), f2);
    }

    public void setMusicVolume(int i2, int i3) {
        TXCLog.i(TAG, "setMusicVolume " + i3);
        nativeSetVolume(convertIdToInt64(this.mIdType, i2), i3);
    }

    public void setMuteDataDurationToPublish(int i2, int i3) {
        TXCLog.i(TAG, "setMuteDataDurationToPublish id:" + i2 + " millis:" + i3);
        nativeSetMuteDataDurationToPublish(convertIdToInt64(this.mIdType, i2), i3);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void setVoiceCaptureVolume(int i2) {
        TXCAudioEngine.getInstance().setSoftwareCaptureVolume(i2 / 100.0f);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void setVoiceChangerType(TXAudioEffectManager.TXVoiceChangerType tXVoiceChangerType) {
        TXCLog.i(TAG, "setVoiceChangerType voiceChangerType:" + tXVoiceChangerType);
        TXCAudioEngine.getInstance().setVoiceChangerType(tXVoiceChangerType);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void setVoiceEarMonitorVolume(int i2) {
        TXCAudioEngine.setAudioEarMonitoringVolume(i2);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void setVoicePitch(double d3) {
        TXCLog.i(TAG, "setVoicePitch pitch:" + d3);
        TXCAudioEngine.getInstance().setVoicePitch(d3);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void setVoiceReverbType(TXAudioEffectManager.TXVoiceReverbType tXVoiceReverbType) {
        TXCLog.i(TAG, "setVoiceReverbType reverbType:" + tXVoiceReverbType);
        TXCAudioEngine.getInstance().setReverbType(tXVoiceReverbType.getNativeValue());
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public boolean startPlayMusic(TXAudioEffectManager.AudioMusicParam audioMusicParam) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (audioMusicParam == null || TextUtils.isEmpty(audioMusicParam.path)) {
            TXCLog.e(TAG, "startPlayMusic failed. invalid param:" + audioMusicParam);
            return false;
        }
        TXCLog.i(TAG, "startPlayMusic param:" + audioMusicParam);
        long j2 = audioMusicParam.startTimeMS;
        long j3 = audioMusicParam.endTimeMS;
        long j4 = j2 < 0 ? 0L : j2;
        long j5 = j3 >= 0 ? j3 : 0L;
        c cVar = sCopyrightedMediaProcessor;
        String strA = cVar.a(audioMusicParam.path);
        if (TextUtils.isEmpty(strA)) {
            return false;
        }
        long jConvertIdToInt64 = convertIdToInt64(this.mIdType, audioMusicParam.id);
        if (!this.mPlayingMusicIDList.contains(Long.valueOf(jConvertIdToInt64))) {
            this.mPlayingMusicIDList.add(Long.valueOf(jConvertIdToInt64));
        }
        cVar.a(jConvertIdToInt64, audioMusicParam.path);
        nativeStartPlayRange(jConvertIdToInt64, j4, j5);
        return nativeStartPlay(jConvertIdToInt64, strA, audioMusicParam.loopCount, audioMusicParam.publish, audioMusicParam.isShortFile, false);
    }

    public void stopAllMusics() {
        stopAllMusics(true);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager
    public void stopPlayMusic(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "stopPlayMusic id:" + i2);
        long jConvertIdToInt64 = convertIdToInt64(this.mIdType, i2);
        this.mPlayingMusicIDList.remove(Long.valueOf(jConvertIdToInt64));
        sCopyrightedMediaProcessor.f(jConvertIdToInt64, nativeGetCurrentPositionInMs(jConvertIdToInt64));
        nativeStopPlay(jConvertIdToInt64);
    }

    private TXAudioEffectManagerImpl(int i2) {
        this.mPlayingMusicIDList = new ArrayList();
        this.mAudioEffectManagerListener = null;
        this.mIdType = i2;
    }

    public void stopAllMusics(boolean z2) {
        TXCLog.i(TAG, "stopAllMusics, cleanObserver:" + z2);
        Iterator<Long> it = this.mPlayingMusicIDList.iterator();
        while (it.hasNext()) {
            final long jLongValue = it.next().longValue();
            nativeStopPlay(jLongValue);
            if (z2) {
                mMainHandler.post(new Runnable() { // from class: com.tencent.liteav.audio.TXAudioEffectManagerImpl.2
                    @Override // java.lang.Runnable
                    public void run() {
                        TXAudioEffectManagerImpl.mMusicObserverMap.remove(Long.valueOf(jLongValue));
                    }
                });
            }
        }
        this.mPlayingMusicIDList.clear();
    }
}
