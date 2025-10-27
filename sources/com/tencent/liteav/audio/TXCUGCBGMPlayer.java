package com.tencent.liteav.audio;

import com.tencent.liteav.audio.TXAudioEffectManager;
import com.tencent.liteav.audio.impl.Play.TXCMultAudioTrackPlayer;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes6.dex */
public class TXCUGCBGMPlayer implements TXAudioEffectManager.TXMusicPlayObserver {
    private static final int PLAY_ERR_OPEN = -1;
    private static final int PLAY_SUCCESS = 0;
    private static final String TAG = "AudioCenter:TXCUGCBGMPlayer";
    private int mBGMId;
    private long mEndTimeMS;
    private boolean mIsRunning;
    private long mSeekBytes;
    private float mSpeedRate;
    private long mStartTimeMS;
    private float mVolume;
    private WeakReference<h> mWeakListener;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static TXCUGCBGMPlayer f18164a = new TXCUGCBGMPlayer();

        public static TXCUGCBGMPlayer a() {
            return f18164a;
        }
    }

    static {
        com.tencent.liteav.basic.util.h.d();
    }

    public static TXCUGCBGMPlayer getInstance() {
        return a.a();
    }

    private void onPlayEnd(int i2) {
        h hVar;
        synchronized (this) {
            WeakReference<h> weakReference = this.mWeakListener;
            hVar = weakReference != null ? weakReference.get() : null;
        }
        if (hVar != null) {
            hVar.onPlayEnd(i2);
        }
    }

    private void onPlayProgress(long j2, long j3) {
        h hVar;
        synchronized (this) {
            WeakReference<h> weakReference = this.mWeakListener;
            hVar = weakReference != null ? weakReference.get() : null;
        }
        if (hVar != null) {
            hVar.onPlayProgress(j2, j3);
        }
    }

    private void onPlayStart() {
        h hVar;
        synchronized (this) {
            WeakReference<h> weakReference = this.mWeakListener;
            hVar = weakReference != null ? weakReference.get() : null;
        }
        if (hVar != null) {
            hVar.onPlayStart();
        }
    }

    public long getDurationMS(String str) {
        return TXAudioEffectManagerImpl.getCacheInstance().getMusicDurationInMS(str);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager.TXMusicPlayObserver
    public void onComplete(int i2, int i3) {
        onPlayEnd(i3);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager.TXMusicPlayObserver
    public void onStart(int i2, int i3) {
        onPlayStart();
    }

    public void pause() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "pause");
        TXAudioEffectManagerImpl.getCacheInstance().pausePlayMusic(this.mBGMId);
    }

    public void playFromTime(long j2, long j3) {
        TXCLog.i(TAG, "startPlayRange:" + j2 + ", " + j3);
        this.mStartTimeMS = j2;
        this.mEndTimeMS = j3;
    }

    public void resume() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "resume");
        TXAudioEffectManagerImpl.getCacheInstance().resumePlayMusic(this.mBGMId);
    }

    public void seekBytes(long j2) {
        if (j2 < 0) {
            TXCLog.e(TAG, "seek bytes can not be negative. change to 0");
            j2 = 0;
        }
        long availableBGMBytes = TXAudioEffectManagerImpl.getCacheInstance().getAvailableBGMBytes(this.mBGMId);
        if (availableBGMBytes > 0) {
            j2 %= availableBGMBytes;
        }
        this.mSeekBytes = j2;
        TXCLog.i(TAG, "mSeekBytes:" + this.mSeekBytes);
        TXAudioEffectManagerImpl.getCacheInstance().seekMusicToPosInBytes(this.mBGMId, j2);
    }

    public void setChangerType(int i2) {
        TXCLog.i(TAG, "changerType:" + i2);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicChangerType(this.mBGMId, i2);
    }

    public synchronized void setOnPlayListener(h hVar) {
        if (hVar == null) {
            this.mWeakListener = null;
            this.mWeakListener = new WeakReference<>(hVar);
        } else {
            this.mWeakListener = new WeakReference<>(hVar);
        }
    }

    public void setReverbType(int i2) {
        TXCLog.i(TAG, "int reverbType:" + i2);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicReverbType(this.mBGMId, i2);
    }

    public void setSpeedRate(float f2) {
        TXCLog.i(TAG, "setSpeedRate:" + f2);
        this.mSpeedRate = f2;
        TXAudioEffectManagerImpl.getCacheInstance().setMusicPlayoutSpeedRate(this.mBGMId, f2);
    }

    public void setVolume(float f2) {
        TXCLog.i(TAG, "setVolume:" + f2);
        this.mVolume = f2;
        TXAudioEffectManagerImpl.getCacheInstance().setMusicVolume(this.mBGMId, (int) (f2 * 100.0f));
    }

    public void startPlay(String str, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "startPlay:" + str + "record:" + z2);
        if (str == null || str.isEmpty()) {
            return;
        }
        if (this.mIsRunning) {
            TXCLog.w(TAG, "BGM is playing, restarting...");
            stopPlay();
        }
        this.mSeekBytes = 0L;
        this.mIsRunning = true;
        TXAudioEffectManager.AudioMusicParam audioMusicParam = new TXAudioEffectManager.AudioMusicParam(this.mBGMId, str);
        audioMusicParam.publish = z2;
        audioMusicParam.loopCount = 0;
        audioMusicParam.startTimeMS = this.mStartTimeMS;
        audioMusicParam.endTimeMS = this.mEndTimeMS;
        audioMusicParam.isShortFile = true;
        TXCLog.i(TAG, "start bgm play : filePath = " + str + " publish:" + z2 + " startTimeMS:" + this.mStartTimeMS + " endTimeMS:" + this.mEndTimeMS + " isShortFile:" + audioMusicParam.isShortFile + "mVolume:" + this.mVolume);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicVolume(this.mBGMId, (int) (this.mVolume * 100.0f));
        TXAudioEffectManagerImpl.getCacheInstance().setMusicPlayoutSpeedRate(this.mBGMId, this.mSpeedRate);
        if (z2) {
            TXAudioEffectManagerImpl.getCacheInstance().setMuteDataDurationToPublish(this.mBGMId, 200);
        }
        boolean zStartPlayMusic = TXAudioEffectManagerImpl.getCacheInstance().startPlayMusic(audioMusicParam);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicObserver(this.mBGMId, this);
        if (zStartPlayMusic) {
            onPlayStart();
        } else {
            onPlayEnd(-1);
        }
    }

    public void stopPlay() {
        TXCLog.i(TAG, "stopPlay");
        this.mIsRunning = false;
        long jCurrentTimeMillis = System.currentTimeMillis();
        synchronized (this) {
            TXAudioEffectManagerImpl.getCacheInstance().setMusicObserver(this.mBGMId, null);
            TXAudioEffectManagerImpl.getCacheInstance().stopPlayMusic(this.mBGMId);
        }
        TXCLog.i(TAG, "stopBGMPlay cost(MS): " + (System.currentTimeMillis() - jCurrentTimeMillis));
    }

    private TXCUGCBGMPlayer() {
        this.mWeakListener = null;
        this.mIsRunning = false;
        this.mVolume = 1.0f;
        this.mSpeedRate = 1.0f;
        this.mStartTimeMS = 0L;
        this.mEndTimeMS = 0L;
        this.mSeekBytes = 0L;
        this.mBGMId = Integer.MIN_VALUE;
        TXCMultAudioTrackPlayer.getInstance();
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager.TXMusicPlayObserver
    public void onPlayProgress(int i2, long j2, long j3) {
        onPlayProgress(j2, j3);
    }
}
