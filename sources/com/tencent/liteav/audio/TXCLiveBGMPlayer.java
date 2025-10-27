package com.tencent.liteav.audio;

import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.audio.TXAudioEffectManager;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes6.dex */
public class TXCLiveBGMPlayer implements TXAudioEffectManager.TXMusicPlayObserver {
    private static final int PLAY_ERR_OPEN = -1;
    private static final int PLAY_SUCCESS = 0;
    private static final String TAG = "AudioCenter:TXCLiveBGMPlayer";
    private int mBGMId;
    private final Handler mHandler;
    private boolean mIsPause;
    private boolean mIsRunning;
    private WeakReference<h> mWeakListener;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static TXCLiveBGMPlayer f18162a = new TXCLiveBGMPlayer();

        public static TXCLiveBGMPlayer a() {
            return f18162a;
        }
    }

    static {
        com.tencent.liteav.basic.util.h.d();
    }

    public static TXCLiveBGMPlayer getInstance() {
        return a.a();
    }

    private void onPlayEnd(final int i2) {
        final h hVar;
        synchronized (this) {
            WeakReference<h> weakReference = this.mWeakListener;
            hVar = weakReference != null ? weakReference.get() : null;
        }
        this.mHandler.post(new Runnable() { // from class: com.tencent.liteav.audio.TXCLiveBGMPlayer.2
            @Override // java.lang.Runnable
            public void run() {
                h hVar2 = hVar;
                if (hVar2 != null) {
                    hVar2.onPlayEnd(i2);
                }
            }
        });
    }

    private void onPlayProgress(final long j2, final long j3) {
        final h hVar;
        synchronized (this) {
            WeakReference<h> weakReference = this.mWeakListener;
            hVar = weakReference != null ? weakReference.get() : null;
        }
        this.mHandler.post(new Runnable() { // from class: com.tencent.liteav.audio.TXCLiveBGMPlayer.3
            @Override // java.lang.Runnable
            public void run() {
                h hVar2 = hVar;
                if (hVar2 != null) {
                    hVar2.onPlayProgress(j2, j3);
                }
            }
        });
    }

    private void onPlayStart(int i2) {
        final h hVar;
        synchronized (this) {
            WeakReference<h> weakReference = this.mWeakListener;
            hVar = weakReference != null ? weakReference.get() : null;
        }
        this.mHandler.post(new Runnable() { // from class: com.tencent.liteav.audio.TXCLiveBGMPlayer.1
            @Override // java.lang.Runnable
            public void run() {
                h hVar2 = hVar;
                if (hVar2 != null) {
                    hVar2.onPlayStart();
                }
            }
        });
    }

    public int getBGMDuration(String str) {
        return (int) TXAudioEffectManagerImpl.getInstance().getMusicDurationInMS(str);
    }

    public long getBGMGetCurrentProgressInMs(String str) {
        if (str == null) {
            return TXAudioEffectManagerImpl.getInstance().getMusicCurrentPosInMS(this.mBGMId);
        }
        return 0L;
    }

    public boolean isPlaying() {
        return this.mIsRunning;
    }

    public boolean isRunning() {
        return this.mIsRunning && !this.mIsPause;
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager.TXMusicPlayObserver
    public void onComplete(int i2, int i3) {
        onPlayEnd(i3);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager.TXMusicPlayObserver
    public void onStart(int i2, int i3) {
        onPlayStart(i3);
    }

    public boolean pause() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "pause");
        this.mIsPause = true;
        TXAudioEffectManagerImpl.getInstance().pausePlayMusic(this.mBGMId);
        return true;
    }

    public boolean resume() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "resume");
        this.mIsPause = false;
        TXAudioEffectManagerImpl.getInstance().resumePlayMusic(this.mBGMId);
        return true;
    }

    public void setBGMPosition(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXAudioEffectManagerImpl.getInstance().seekMusicToPosInMS(this.mBGMId, i2);
    }

    public synchronized void setOnPlayListener(h hVar) {
        if (hVar == null) {
            this.mWeakListener = null;
            this.mWeakListener = new WeakReference<>(hVar);
        } else {
            this.mWeakListener = new WeakReference<>(hVar);
        }
    }

    public void setPitch(float f2) {
        TXAudioEffectManagerImpl.getInstance().setMusicPitch(this.mBGMId, f2);
    }

    public boolean setPlayoutVolume(float f2) {
        TXCLog.i(TAG, "setPlayoutVolume:" + f2);
        TXAudioEffectManagerImpl.getInstance().setMusicPlayoutVolume(this.mBGMId, (int) (f2 * 100.0f));
        return true;
    }

    public boolean setPublishVolume(float f2) {
        TXAudioEffectManagerImpl.getInstance().setMusicPublishVolume(this.mBGMId, (int) (f2 * 100.0f));
        return true;
    }

    public boolean setVolume(float f2) {
        TXCLog.i(TAG, "setVolume");
        TXAudioEffectManagerImpl.getInstance().setMusicVolume(this.mBGMId, (int) (f2 * 100.0f));
        return true;
    }

    public boolean startPlay(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (str == null || str.isEmpty()) {
            TXCLog.e(TAG, "start live bgm failed! invalid params!");
            return false;
        }
        this.mIsRunning = true;
        TXAudioEffectManager.AudioMusicParam audioMusicParam = new TXAudioEffectManager.AudioMusicParam(this.mBGMId, str);
        audioMusicParam.publish = true;
        audioMusicParam.loopCount = 0;
        boolean zStartPlayMusic = TXAudioEffectManagerImpl.getInstance().startPlayMusic(audioMusicParam);
        TXAudioEffectManagerImpl.getInstance().setMusicObserver(this.mBGMId, this);
        if (!zStartPlayMusic) {
            onPlayEnd(-1);
            return false;
        }
        TXCLog.i(TAG, "start bgm play : filePath = " + str);
        return true;
    }

    public void stopAll() {
        TXAudioEffectManagerImpl.getInstance().stopAllMusics();
        TXAudioEffectManagerImpl.getAutoCacheHolder().stopAllMusics();
        TXAudioEffectManagerImpl.getCacheInstance().stopAllMusics();
    }

    public boolean stopPlay() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mIsRunning = false;
        long jCurrentTimeMillis = System.currentTimeMillis();
        TXAudioEffectManagerImpl.getInstance().setMusicObserver(this.mBGMId, null);
        TXAudioEffectManagerImpl.getInstance().stopPlayMusic(this.mBGMId);
        this.mIsPause = false;
        TXCLog.i(TAG, "stopBGMPlay cost(MS): " + (System.currentTimeMillis() - jCurrentTimeMillis));
        return true;
    }

    private TXCLiveBGMPlayer() {
        this.mIsRunning = false;
        this.mIsPause = false;
        this.mWeakListener = null;
        this.mBGMId = Integer.MAX_VALUE;
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager.TXMusicPlayObserver
    public void onPlayProgress(int i2, long j2, long j3) {
        onPlayProgress(j2, j3);
    }
}
