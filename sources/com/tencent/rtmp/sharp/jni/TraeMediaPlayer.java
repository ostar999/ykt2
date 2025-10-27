package com.tencent.rtmp.sharp.jni;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes6.dex */
public class TraeMediaPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    public static final int TRAE_MEDIAPLAER_DATASOURCE_FILEPATH = 2;
    public static final int TRAE_MEDIAPLAER_DATASOURCE_RSID = 0;
    public static final int TRAE_MEDIAPLAER_DATASOURCE_URI = 1;
    public static final int TRAE_MEDIAPLAER_STOP = 100;
    private Context _context;
    private OnCompletionListener mCallback;
    private MediaPlayer mMediaPlay = null;
    private int _streamType = 0;
    private boolean _hasCall = false;
    private boolean _loop = false;
    private int _durationMS = -1;
    int _loopCount = 0;
    boolean _ringMode = false;
    private Timer _watchTimer = null;
    private TimerTask _watchTimertask = null;
    private int _prevVolume = -1;

    public interface OnCompletionListener {
        void onCompletion();
    }

    public TraeMediaPlayer(Context context, OnCompletionListener onCompletionListener) {
        this._context = context;
        this.mCallback = onCompletionListener;
    }

    private void volumeDo() {
        if (this.mMediaPlay == null || !this._ringMode || this._streamType == 2) {
            return;
        }
        try {
            AudioManager audioManager = (AudioManager) this._context.getSystemService("audio");
            int streamVolume = audioManager.getStreamVolume(this._streamType);
            int streamMaxVolume = audioManager.getStreamMaxVolume(this._streamType);
            int streamVolume2 = audioManager.getStreamVolume(2);
            int streamMaxVolume2 = audioManager.getStreamMaxVolume(2);
            int i2 = (int) (((streamVolume2 * 1.0d) / streamMaxVolume2) * streamMaxVolume);
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "TraeMediaPlay volumeDo currV:" + streamVolume + " maxV:" + streamMaxVolume + " currRV:" + streamVolume2 + " maxRV:" + streamMaxVolume2 + " setV:" + i2);
            }
            int i3 = i2 + 1;
            if (i3 < streamMaxVolume) {
                streamMaxVolume = i3;
            }
            audioManager.setStreamVolume(this._streamType, streamMaxVolume, 0);
            this._prevVolume = streamVolume;
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "set stream volume failed." + e2.getMessage());
            }
        }
    }

    private void volumeUndo() {
        if (this.mMediaPlay == null || !this._ringMode || this._streamType == 2 || this._prevVolume == -1) {
            return;
        }
        try {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "TraeMediaPlay volumeUndo _prevVolume:" + this._prevVolume);
            }
            ((AudioManager) this._context.getSystemService("audio")).setStreamVolume(this._streamType, this._prevVolume, 0);
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "set stream volume failed." + e2.getMessage());
            }
        }
    }

    public int getDuration() {
        return this._durationMS;
    }

    public int getStreamType() {
        return this._streamType;
    }

    public boolean hasCall() {
        return this._hasCall;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) throws IllegalStateException {
        AudioDeviceInterface.LogTraceEntry(" cb:" + this.mCallback + " loopCount:" + this._loopCount + " _loop:" + this._loop);
        if (this._loop) {
            if (QLog.isColorLevel()) {
                QLog.d("TRAE", 2, "loop play,continue...");
                return;
            }
            return;
        }
        try {
            if (this._loopCount <= 0) {
                volumeUndo();
                if (this.mMediaPlay.isPlaying()) {
                    this.mMediaPlay.stop();
                }
                this.mMediaPlay.reset();
                this.mMediaPlay.release();
                this.mMediaPlay = null;
                OnCompletionListener onCompletionListener = this.mCallback;
                if (onCompletionListener != null) {
                    onCompletionListener.onCompletion();
                }
            } else {
                this.mMediaPlay.start();
                this._loopCount--;
            }
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "stop play failed." + e2.getMessage());
            }
        }
        AudioDeviceInterface.LogTraceExit();
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i2, int i3) {
        AudioDeviceInterface.LogTraceEntry(" cb:" + this.mCallback + " arg1:" + i2 + " arg2:" + i3);
        try {
            this.mMediaPlay.release();
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "release MediaPlayer failed." + e2.getMessage());
            }
        }
        this.mMediaPlay = null;
        OnCompletionListener onCompletionListener = this.mCallback;
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion();
        }
        AudioDeviceInterface.LogTraceExit();
        return false;
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0377  */
    /* JADX WARN: Type inference failed for: r4v10 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean playRing(int r26, int r27, android.net.Uri r28, java.lang.String r29, boolean r30, int r31, boolean r32, boolean r33, int r34) {
        /*
            Method dump skipped, instructions count: 932
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.rtmp.sharp.jni.TraeMediaPlayer.playRing(int, int, android.net.Uri, java.lang.String, boolean, int, boolean, boolean, int):boolean");
    }

    public void stopRing() {
        if (QLog.isColorLevel()) {
            QLog.d("TRAE", 2, "TraeMediaPlay stopRing ");
        }
        MediaPlayer mediaPlayer = this.mMediaPlay;
        if (mediaPlayer == null) {
            return;
        }
        if (mediaPlayer.isPlaying()) {
            this.mMediaPlay.stop();
        }
        this.mMediaPlay.reset();
        try {
            Timer timer = this._watchTimer;
            if (timer != null) {
                timer.cancel();
                this._watchTimer = null;
                this._watchTimertask = null;
            }
            this.mMediaPlay.release();
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "release MediaPlayer failed." + e2.getMessage());
            }
        }
        this.mMediaPlay = null;
        this._durationMS = -1;
    }
}
