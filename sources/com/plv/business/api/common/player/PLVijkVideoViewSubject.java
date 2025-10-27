package com.plv.business.api.common.player;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.SurfaceHolder;
import com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView;
import com.easefun.polyv.mediasdk.example.widget.media.IMediaController;
import com.easefun.polyv.mediasdk.example.widget.media.IRenderView;
import com.easefun.polyv.mediasdk.gifmaker.GifMaker;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import com.easefun.polyv.mediasdk.player.misc.ITrackInfo;
import java.util.Map;

/* loaded from: classes4.dex */
public class PLVijkVideoViewSubject implements IIjkVideoView {
    private IIjkVideoView ijkVideoView;

    public PLVijkVideoViewSubject(IIjkVideoView iIjkVideoView) {
        this.ijkVideoView = iIjkVideoView;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canPause() {
        return this.ijkVideoView.canPause();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekBackward() {
        return this.ijkVideoView.canSeekBackward();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekForward() {
        return this.ijkVideoView.canSeekForward();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void cancelClip() {
        this.ijkVideoView.cancelClip();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void clearOptionParameters() {
        this.ijkVideoView.clearOptionParameters();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public IMediaPlayer createPlayer(int i2) {
        return this.ijkVideoView.createPlayer(i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void deselectTrack(int i2) {
        this.ijkVideoView.deselectTrack(i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void enterBackground() {
        this.ijkVideoView.enterBackground();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getAudioSessionId() {
        return 0;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getBufferPercentage() {
        return this.ijkVideoView.getBufferPercentage();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getCurrentAspectRatio() {
        return this.ijkVideoView.getCurrentAspectRatio();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getCurrentPosition() {
        return this.ijkVideoView.getCurrentPosition();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getCurrentState() {
        return this.ijkVideoView.getCurrentState();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getDuration() {
        return this.ijkVideoView.getDuration();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public IjkMediaPlayer getIjkMediaPlayer() {
        return this.ijkVideoView.getIjkMediaPlayer();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public IMediaPlayer getMediaPlayer() {
        return this.ijkVideoView.getMediaPlayer();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public IRenderView getRenderView() {
        return this.ijkVideoView.getRenderView();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getSelectedTrack(int i2) {
        return this.ijkVideoView.getSelectedTrack(i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public float getSpeed() {
        return this.ijkVideoView.getSpeed();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStateErrorCode() {
        return this.ijkVideoView.getStateErrorCode();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStateIdleCode() {
        return this.ijkVideoView.getStateIdleCode();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStatePauseCode() {
        return this.ijkVideoView.getStatePauseCode();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStatePlaybackCompletedCode() {
        return this.ijkVideoView.getStatePlaybackCompletedCode();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStatePlayingCode() {
        return this.ijkVideoView.getStatePlayingCode();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStatePreparedCode() {
        return this.ijkVideoView.getStatePreparedCode();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStatePreparingCode() {
        return this.ijkVideoView.getStatePreparingCode();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public SurfaceHolder getSurfaceHolder() {
        return this.ijkVideoView.getSurfaceHolder();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getTargetState() {
        return this.ijkVideoView.getTargetState();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public ITrackInfo[] getTrackInfo() {
        return this.ijkVideoView.getTrackInfo();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getVideoHeight() {
        return this.ijkVideoView.getVideoHeight();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getVideoWidth() {
        return this.ijkVideoView.getVideoWidth();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public boolean isInPlaybackStateForwarding() {
        return this.ijkVideoView.isInPlaybackStateForwarding();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean isPlaying() {
        return this.ijkVideoView.isPlaying();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void onErrorState() {
        this.ijkVideoView.onErrorState();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void pause() {
        this.ijkVideoView.pause();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void release(boolean z2) {
        this.ijkVideoView.release(z2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void releaseWithoutStop() {
        this.ijkVideoView.releaseWithoutStop();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void removeRenderView() {
        this.ijkVideoView.removeRenderView();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void resetLoadCost() {
        this.ijkVideoView.resetLoadCost();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void resetVideoURI() {
        this.ijkVideoView.resetVideoURI();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void resume() {
        this.ijkVideoView.resume();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public Bitmap screenshot() {
        return this.ijkVideoView.screenshot();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void seekTo(int i2) {
        this.ijkVideoView.seekTo(i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void selectTrack(int i2) {
        this.ijkVideoView.selectTrack(i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setCurrentAspectRatio(int i2) {
        this.ijkVideoView.setCurrentAspectRatio(i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setIjkLogLevel(int i2) {
        this.ijkVideoView.setIjkLogLevel(i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setLogTag(String str) {
        this.ijkVideoView.setLogTag(str);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setMediaController(IMediaController iMediaController) {
        this.ijkVideoView.setMediaController(iMediaController);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setMinFrames(int i2) {
        this.ijkVideoView.setMinFrames(i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setMirror(boolean z2) {
        this.ijkVideoView.setMirror(z2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnBufferingListener(IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener) {
        this.ijkVideoView.setOnBufferingListener(onBufferingUpdateListener);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener onCompletionListener) {
        this.ijkVideoView.setOnCompletionListener(onCompletionListener);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnErrorListener(IMediaPlayer.OnErrorListener onErrorListener) {
        this.ijkVideoView.setOnErrorListener(onErrorListener);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnIjkMediaPlayerCreateListener(IIjkVideoView.OnIjkMediaPlayerCreateListener onIjkMediaPlayerCreateListener) {
        this.ijkVideoView.setOnIjkMediaPlayerCreateListener(onIjkMediaPlayerCreateListener);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnInfoListener(IMediaPlayer.OnInfoListener onInfoListener) {
        this.ijkVideoView.setOnInfoListener(onInfoListener);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnPreparedListener(IMediaPlayer.OnPreparedListener onPreparedListener) {
        this.ijkVideoView.setOnPreparedListener(onPreparedListener);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnSEIRefreshListener(IMediaPlayer.OnSEIRefreshListener onSEIRefreshListener) {
        this.ijkVideoView.setOnSEIRefreshListener(onSEIRefreshListener);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        this.ijkVideoView.setOnSeekCompleteListener(onSeekCompleteListener);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnVideoSizeChangedListener(IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.ijkVideoView.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOptionParameters(Object[][] objArr) {
        this.ijkVideoView.setOptionParameters(objArr);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setRender(int i2) {
        this.ijkVideoView.setRender(i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setRenderView(IRenderView iRenderView) {
        this.ijkVideoView.setRenderView(iRenderView);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setSpeed(float f2) {
        this.ijkVideoView.setSpeed(f2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setTargetState(int i2) {
        this.ijkVideoView.setTargetState(i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setVideoContentPrefixURLString(String str, byte[] bArr, Map<String, String> map) {
        this.ijkVideoView.setVideoContentPrefixURLString(str, bArr, map);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setVideoPath(String str) {
        this.ijkVideoView.setVideoPath(str);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setVideoURI(Uri uri) {
        this.ijkVideoView.setVideoURI(uri);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void start() {
        this.ijkVideoView.start();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public boolean startClip(int i2) {
        return this.ijkVideoView.startClip(i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void stopBackgroundPlay() {
        this.ijkVideoView.stopBackgroundPlay();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void stopClip(GifMaker.OnGifListener onGifListener) {
        this.ijkVideoView.stopClip(onGifListener);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void stopPlayback() {
        this.ijkVideoView.stopPlayback();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void suspend() {
        this.ijkVideoView.suspend();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int toggleAspectRatio() {
        return this.ijkVideoView.toggleAspectRatio();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int togglePlayer() {
        return this.ijkVideoView.togglePlayer();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int toggleRender() {
        return this.ijkVideoView.toggleRender();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setVideoURI(Uri uri, Map<String, String> map) {
        this.ijkVideoView.setVideoURI(uri, map);
    }
}
