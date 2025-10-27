package com.psychiatrygarden.interfaceclass;

import android.app.ActionBar;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.VideoView;
import com.github.lzyzsd.jsbridge.BridgeUtil;
import com.psychiatrygarden.widget.VideoEnabledWebView;

/* loaded from: classes4.dex */
public class VideoEnabledWebChromeClient extends WebChromeClient implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private View activityNonVideoView;
    private ViewGroup activityVideoView;
    private boolean isVideoFullscreen;
    private View loadingView;
    private ToggledFullscreenCallback toggledFullscreenCallback;
    private WebChromeClient.CustomViewCallback videoViewCallback;
    private FrameLayout videoViewContainer;
    private VideoEnabledWebView webView;

    public interface ToggledFullscreenCallback {
        void toggledFullscreen(boolean fullscreen);
    }

    public VideoEnabledWebChromeClient() {
    }

    @Override // android.webkit.WebChromeClient
    public View getVideoLoadingProgressView() {
        View view = this.loadingView;
        if (view == null) {
            return super.getVideoLoadingProgressView();
        }
        view.setVisibility(0);
        return this.loadingView;
    }

    public boolean isVideoFullscreen() {
        return this.isVideoFullscreen;
    }

    public boolean onBackPressed() {
        if (!this.isVideoFullscreen) {
            return false;
        }
        onHideCustomView();
        return true;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mp) {
        onHideCustomView();
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override // android.webkit.WebChromeClient
    public void onHideCustomView() {
        if (this.isVideoFullscreen) {
            this.activityVideoView.setVisibility(8);
            this.activityVideoView.removeView(this.videoViewContainer);
            this.activityNonVideoView.setVisibility(0);
            WebChromeClient.CustomViewCallback customViewCallback = this.videoViewCallback;
            if (customViewCallback != null) {
                customViewCallback.onCustomViewHidden();
            }
            this.isVideoFullscreen = false;
            this.videoViewContainer = null;
            this.videoViewCallback = null;
            ToggledFullscreenCallback toggledFullscreenCallback = this.toggledFullscreenCallback;
            if (toggledFullscreenCallback != null) {
                toggledFullscreenCallback.toggledFullscreen(false);
            }
        }
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mp) {
        View view = this.loadingView;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        if (view instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) view;
            View focusedChild = frameLayout.getFocusedChild();
            this.isVideoFullscreen = true;
            this.videoViewContainer = frameLayout;
            this.videoViewCallback = callback;
            this.activityNonVideoView.setVisibility(8);
            this.activityVideoView.addView(this.videoViewContainer, new ActionBar.LayoutParams(-1, -1));
            this.activityVideoView.setVisibility(0);
            if (focusedChild instanceof VideoView) {
                VideoView videoView = (VideoView) focusedChild;
                videoView.setOnPreparedListener(this);
                videoView.setOnCompletionListener(this);
                videoView.setOnErrorListener(this);
            } else {
                VideoEnabledWebView videoEnabledWebView = this.webView;
                if (videoEnabledWebView != null && videoEnabledWebView.getSettings().getJavaScriptEnabled()) {
                    this.webView.loadUrl((((((((BridgeUtil.JAVASCRIPT_STR + "_ytrp_html5_video = document.getElementsByTagName('video')[0];") + "if (_ytrp_html5_video !== undefined) {") + "function _ytrp_html5_video_ended() {") + "_ytrp_html5_video.removeEventListener('ended', _ytrp_html5_video_ended);") + "_VideoEnabledWebView.notifyVideoEnd();") + "}") + "_ytrp_html5_video.addEventListener('ended', _ytrp_html5_video_ended);") + "}");
                }
            }
            ToggledFullscreenCallback toggledFullscreenCallback = this.toggledFullscreenCallback;
            if (toggledFullscreenCallback != null) {
                toggledFullscreenCallback.toggledFullscreen(true);
            }
        }
    }

    public void setOnToggledFullscreen(ToggledFullscreenCallback callback) {
        this.toggledFullscreenCallback = callback;
    }

    public VideoEnabledWebChromeClient(View activityNonVideoView, ViewGroup activityVideoView) {
        this.activityNonVideoView = activityNonVideoView;
        this.activityVideoView = activityVideoView;
        this.loadingView = null;
        this.webView = null;
        this.isVideoFullscreen = false;
    }

    public VideoEnabledWebChromeClient(View activityNonVideoView, ViewGroup activityVideoView, View loadingView) {
        this.activityNonVideoView = activityNonVideoView;
        this.activityVideoView = activityVideoView;
        this.loadingView = loadingView;
        this.webView = null;
        this.isVideoFullscreen = false;
    }

    public VideoEnabledWebChromeClient(View activityNonVideoView, ViewGroup activityVideoView, View loadingView, VideoEnabledWebView webView) {
        this.activityNonVideoView = activityNonVideoView;
        this.activityVideoView = activityVideoView;
        this.loadingView = loadingView;
        this.webView = webView;
        this.isVideoFullscreen = false;
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, int requestedOrientation, WebChromeClient.CustomViewCallback callback) {
        onShowCustomView(view, callback);
    }
}
