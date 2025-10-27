package com.unity3d.splash.services.ads.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.VideoView;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes6.dex */
public class VideoPlayerView extends VideoView {
    private boolean _infoListenerEnabled;
    private MediaPlayer _mediaPlayer;
    private Timer _prepareTimer;
    private int _progressEventInterval;
    private Timer _videoTimer;
    private String _videoUrl;
    private Float _volume;

    public VideoPlayerView(Context context) {
        super(context);
        this._progressEventInterval = 500;
        this._mediaPlayer = null;
        this._volume = null;
        this._infoListenerEnabled = true;
    }

    private void startPrepareTimer(long j2) {
        Timer timer = new Timer();
        this._prepareTimer = timer;
        timer.schedule(new TimerTask() { // from class: com.unity3d.splash.services.ads.video.VideoPlayerView.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                if (VideoPlayerView.this.isPlaying()) {
                    return;
                }
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PREPARE_TIMEOUT, VideoPlayerView.this._videoUrl);
                DeviceLog.error("Video player prepare timeout: " + VideoPlayerView.this._videoUrl);
            }
        }, j2);
    }

    private void startVideoProgressTimer() {
        Timer timer = new Timer();
        this._videoTimer = timer;
        TimerTask timerTask = new TimerTask() { // from class: com.unity3d.splash.services.ads.video.VideoPlayerView.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                boolean zIsPlaying;
                try {
                    zIsPlaying = VideoPlayerView.this.isPlaying();
                } catch (IllegalStateException e2) {
                    e = e2;
                    zIsPlaying = false;
                }
                try {
                    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PROGRESS, Integer.valueOf(VideoPlayerView.this.getCurrentPosition()));
                } catch (IllegalStateException e3) {
                    e = e3;
                    DeviceLog.exception("Exception while sending current position to webapp", e);
                    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.ILLEGAL_STATE, VideoPlayerEvent.PROGRESS, VideoPlayerView.this._videoUrl, Boolean.valueOf(zIsPlaying));
                }
            }
        };
        int i2 = this._progressEventInterval;
        timer.scheduleAtFixedRate(timerTask, i2, i2);
    }

    public int getProgressEventInterval() {
        return this._progressEventInterval;
    }

    public int[] getVideoViewRectangle() {
        int[] iArr = new int[2];
        getLocationInWindow(iArr);
        return new int[]{iArr[0], iArr[1], getMeasuredWidth(), getMeasuredHeight()};
    }

    public float getVolume() {
        return this._volume.floatValue();
    }

    @Override // android.widget.VideoView, android.widget.MediaController.MediaPlayerControl
    public void pause() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            super.pause();
            stopVideoProgressTimer();
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PAUSE, this._videoUrl);
        } catch (Exception e2) {
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PAUSE_ERROR, this._videoUrl);
            DeviceLog.exception("Error pausing video", e2);
        }
    }

    public void play() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.entered();
        setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.unity3d.splash.services.ads.video.VideoPlayerView.5
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mediaPlayer != null) {
                    VideoPlayerView.this._mediaPlayer = mediaPlayer;
                }
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.COMPLETED, VideoPlayerView.this._videoUrl);
                VideoPlayerView.this.stopVideoProgressTimer();
            }
        });
        start();
        stopVideoProgressTimer();
        startVideoProgressTimer();
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PLAY, this._videoUrl);
    }

    public boolean prepare(String str, final float f2, int i2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.entered();
        this._videoUrl = str;
        setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.unity3d.splash.services.ads.video.VideoPlayerView.3
            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(MediaPlayer mediaPlayer) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                VideoPlayerView.this.stopPrepareTimer();
                if (mediaPlayer != null) {
                    VideoPlayerView.this._mediaPlayer = mediaPlayer;
                }
                VideoPlayerView.this.setVolume(Float.valueOf(f2));
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PREPARED, VideoPlayerView.this._videoUrl, Integer.valueOf(mediaPlayer.getDuration()), Integer.valueOf(mediaPlayer.getVideoWidth()), Integer.valueOf(mediaPlayer.getVideoHeight()));
            }
        });
        setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.unity3d.splash.services.ads.video.VideoPlayerView.4
            @Override // android.media.MediaPlayer.OnErrorListener
            public boolean onError(MediaPlayer mediaPlayer, int i3, int i4) {
                VideoPlayerView.this.stopPrepareTimer();
                if (mediaPlayer != null) {
                    VideoPlayerView.this._mediaPlayer = mediaPlayer;
                }
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.GENERIC_ERROR, VideoPlayerView.this._videoUrl, Integer.valueOf(i3), Integer.valueOf(i4));
                VideoPlayerView.this.stopVideoProgressTimer();
                return true;
            }
        });
        setInfoListenerEnabled(this._infoListenerEnabled);
        if (i2 > 0) {
            startPrepareTimer(i2);
        }
        try {
            setVideoPath(this._videoUrl);
            return true;
        } catch (Exception e2) {
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PREPARE_ERROR, this._videoUrl);
            DeviceLog.exception("Error preparing video: " + this._videoUrl, e2);
            return false;
        }
    }

    @Override // android.widget.VideoView, android.widget.MediaController.MediaPlayerControl
    public void seekTo(int i2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            super.seekTo(i2);
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.SEEKTO, this._videoUrl);
        } catch (Exception e2) {
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.SEEKTO_ERROR, this._videoUrl);
            DeviceLog.exception("Error seeking video", e2);
        }
    }

    public void setInfoListenerEnabled(boolean z2) {
        this._infoListenerEnabled = z2;
        if (z2) {
            setOnInfoListener(new MediaPlayer.OnInfoListener() { // from class: com.unity3d.splash.services.ads.video.VideoPlayerView.6
                @Override // android.media.MediaPlayer.OnInfoListener
                public boolean onInfo(MediaPlayer mediaPlayer, int i2, int i3) {
                    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.INFO, VideoPlayerView.this._videoUrl, Integer.valueOf(i2), Integer.valueOf(i3));
                    return true;
                }
            });
        } else {
            setOnInfoListener(null);
        }
    }

    public void setProgressEventInterval(int i2) {
        this._progressEventInterval = i2;
        if (this._videoTimer != null) {
            stopVideoProgressTimer();
            startVideoProgressTimer();
        }
    }

    public void setVolume(Float f2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            this._mediaPlayer.setVolume(f2.floatValue(), f2.floatValue());
            this._volume = f2;
        } catch (Exception e2) {
            DeviceLog.exception("MediaPlayer generic error", e2);
        }
    }

    public void stop() {
        stopPlayback();
        stopVideoProgressTimer();
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.STOP, this._videoUrl);
    }

    public void stopPrepareTimer() {
        Timer timer = this._prepareTimer;
        if (timer != null) {
            timer.cancel();
            this._prepareTimer.purge();
            this._prepareTimer = null;
        }
    }

    public void stopVideoProgressTimer() {
        Timer timer = this._videoTimer;
        if (timer != null) {
            timer.cancel();
            this._videoTimer.purge();
            this._videoTimer = null;
        }
    }
}
