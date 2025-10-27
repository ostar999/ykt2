package com.easefun.polyv.mediasdk.example.widget.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.widget.MediaController;
import com.easefun.polyv.mediasdk.gifmaker.GifMaker;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import com.easefun.polyv.mediasdk.player.misc.ITrackInfo;
import java.util.Map;

/* loaded from: classes3.dex */
public interface IIjkVideoView extends MediaController.MediaPlayerControl {

    public interface OnIjkMediaPlayerCreateListener {
        void onIjkPlayerCreate(IMediaPlayer iMediaPlayer);
    }

    void cancelClip();

    void clearOptionParameters();

    IMediaPlayer createPlayer(int i2);

    void deselectTrack(int i2);

    void enterBackground();

    int getCurrentAspectRatio();

    int getCurrentState();

    IjkMediaPlayer getIjkMediaPlayer();

    IMediaPlayer getMediaPlayer();

    IRenderView getRenderView();

    int getSelectedTrack(int i2);

    float getSpeed();

    int getStateErrorCode();

    int getStateIdleCode();

    int getStatePauseCode();

    int getStatePlaybackCompletedCode();

    int getStatePlayingCode();

    int getStatePreparedCode();

    int getStatePreparingCode();

    SurfaceHolder getSurfaceHolder();

    int getTargetState();

    ITrackInfo[] getTrackInfo();

    int getVideoHeight();

    int getVideoWidth();

    boolean isInPlaybackStateForwarding();

    void onErrorState();

    void release(boolean z2);

    void releaseWithoutStop();

    void removeRenderView();

    void resetLoadCost();

    void resetVideoURI();

    void resume();

    Bitmap screenshot();

    void selectTrack(int i2);

    void setCurrentAspectRatio(int i2);

    void setIjkLogLevel(int i2);

    void setLogTag(String str);

    void setMediaController(IMediaController iMediaController);

    void setMinFrames(int i2);

    void setMirror(boolean z2);

    void setOnBufferingListener(IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener);

    void setOnCompletionListener(IMediaPlayer.OnCompletionListener onCompletionListener);

    void setOnErrorListener(IMediaPlayer.OnErrorListener onErrorListener);

    void setOnIjkMediaPlayerCreateListener(OnIjkMediaPlayerCreateListener onIjkMediaPlayerCreateListener);

    void setOnInfoListener(IMediaPlayer.OnInfoListener onInfoListener);

    void setOnPreparedListener(IMediaPlayer.OnPreparedListener onPreparedListener);

    void setOnSEIRefreshListener(IMediaPlayer.OnSEIRefreshListener onSEIRefreshListener);

    void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener onSeekCompleteListener);

    void setOnVideoSizeChangedListener(IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener);

    void setOptionParameters(Object[][] objArr);

    void setRender(int i2);

    void setRenderView(IRenderView iRenderView);

    void setSpeed(float f2);

    void setTargetState(int i2);

    void setVideoContentPrefixURLString(String str, byte[] bArr, Map<String, String> map);

    void setVideoPath(String str);

    void setVideoURI(Uri uri);

    void setVideoURI(Uri uri, Map<String, String> map);

    boolean startClip(int i2);

    void stopBackgroundPlay();

    void stopClip(GifMaker.OnGifListener onGifListener);

    void stopPlayback();

    void suspend();

    int toggleAspectRatio();

    int togglePlayer();

    int toggleRender();
}
