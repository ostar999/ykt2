package com.unity3d.splash.services.ads.api;

import com.unity3d.splash.services.ads.video.VideoPlayerError;
import com.unity3d.splash.services.ads.video.VideoPlayerEvent;
import com.unity3d.splash.services.ads.video.VideoPlayerView;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes6.dex */
public class VideoPlayer {
    private static VideoPlayerView _videoPlayerView;

    @WebViewExposed
    public static void getCurrentPosition(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getVideoPlayerView() != null) {
            webViewCallback.invoke(Integer.valueOf(getVideoPlayerView().getCurrentPosition()));
        } else {
            webViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void getProgressEventInterval(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getVideoPlayerView() != null) {
            webViewCallback.invoke(Integer.valueOf(getVideoPlayerView().getProgressEventInterval()));
        } else {
            webViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
        }
    }

    public static VideoPlayerView getVideoPlayerView() {
        return _videoPlayerView;
    }

    @WebViewExposed
    public static void getVideoViewRectangle(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        VideoPlayerView videoPlayerView = getVideoPlayerView();
        if (videoPlayerView == null) {
            webViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
        } else {
            int[] videoViewRectangle = videoPlayerView.getVideoViewRectangle();
            webViewCallback.invoke(Integer.valueOf(videoViewRectangle[0]), Integer.valueOf(videoViewRectangle[1]), Integer.valueOf(videoViewRectangle[2]), Integer.valueOf(videoViewRectangle[3]));
        }
    }

    @WebViewExposed
    public static void getVolume(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getVideoPlayerView() != null) {
            webViewCallback.invoke(Float.valueOf(getVideoPlayerView().getVolume()));
        } else {
            webViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void pause(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.debug("Pausing current video");
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.VideoPlayer.4
            @Override // java.lang.Runnable
            public final void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                if (VideoPlayer.getVideoPlayerView() != null) {
                    VideoPlayer.getVideoPlayerView().pause();
                }
            }
        });
        if (getVideoPlayerView() != null) {
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void play(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.debug("Starting playback of prepared video");
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.VideoPlayer.3
            @Override // java.lang.Runnable
            public final void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                if (VideoPlayer.getVideoPlayerView() != null) {
                    VideoPlayer.getVideoPlayerView().play();
                }
            }
        });
        if (getVideoPlayerView() != null) {
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void prepare(String str, Double d3, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        prepare(str, d3, 0, webViewCallback);
    }

    @WebViewExposed
    public static void prepare(final String str, final Double d3, final Integer num, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.debug("Preparing video for playback: " + str);
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.VideoPlayer.2
            @Override // java.lang.Runnable
            public final void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                if (VideoPlayer.getVideoPlayerView() != null) {
                    VideoPlayer.getVideoPlayerView().prepare(str, d3.floatValue(), num.intValue());
                }
            }
        });
        if (getVideoPlayerView() != null) {
            webViewCallback.invoke(str);
        } else {
            webViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void seekTo(final Integer num, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.debug("Seeking video to time: " + num);
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.VideoPlayer.6
            @Override // java.lang.Runnable
            public final void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                if (VideoPlayer.getVideoPlayerView() != null) {
                    VideoPlayer.getVideoPlayerView().seekTo(num.intValue());
                }
            }
        });
        if (getVideoPlayerView() != null) {
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setInfoListenerEnabled(boolean z2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getVideoPlayerView() == null) {
            webViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
        } else {
            getVideoPlayerView().setInfoListenerEnabled(z2);
            webViewCallback.invoke(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.INFO, Boolean.valueOf(z2));
        }
    }

    @WebViewExposed
    public static void setProgressEventInterval(final Integer num, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.VideoPlayer.1
            @Override // java.lang.Runnable
            public final void run() {
                if (VideoPlayer.getVideoPlayerView() != null) {
                    VideoPlayer.getVideoPlayerView().setProgressEventInterval(num.intValue());
                }
            }
        });
        if (getVideoPlayerView() != null) {
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
        }
    }

    public static void setVideoPlayerView(VideoPlayerView videoPlayerView) {
        _videoPlayerView = videoPlayerView;
    }

    @WebViewExposed
    public static void setVolume(Double d3, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.debug("Setting video volume: " + d3);
        if (getVideoPlayerView() == null) {
            webViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
        } else {
            getVideoPlayerView().setVolume(Float.valueOf(d3.floatValue()));
            webViewCallback.invoke(d3);
        }
    }

    @WebViewExposed
    public static void stop(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.debug("Stopping current video");
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.VideoPlayer.5
            @Override // java.lang.Runnable
            public final void run() {
                if (VideoPlayer.getVideoPlayerView() != null) {
                    VideoPlayer.getVideoPlayerView().stop();
                }
            }
        });
        if (getVideoPlayerView() != null) {
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
        }
    }
}
