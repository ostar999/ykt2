package com.easefun.polyv.mediasdk.player;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.easefun.polyv.mediasdk.player.misc.AndroidTrackInfo;
import com.easefun.polyv.mediasdk.player.misc.IMediaDataSource;
import com.easefun.polyv.mediasdk.player.misc.ITrackInfo;
import com.easefun.polyv.mediasdk.player.pragma.DebugLog;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;

/* loaded from: classes3.dex */
public class AndroidMediaPlayer extends AbstractMediaPlayer {
    private static MediaInfo sMediaInfo;
    private String mDataSource;
    private final Object mInitLock;
    private final AndroidMediaPlayerListenerHolder mInternalListenerAdapter;
    private final MediaPlayer mInternalMediaPlayer;
    private boolean mIsReleased;
    private MediaDataSource mMediaDataSource;

    public class AndroidMediaPlayerListenerHolder implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnTimedTextListener {
        public final WeakReference<AndroidMediaPlayer> mWeakMediaPlayer;

        public AndroidMediaPlayerListenerHolder(AndroidMediaPlayer androidMediaPlayer) {
            this.mWeakMediaPlayer = new WeakReference<>(androidMediaPlayer);
        }

        @Override // android.media.MediaPlayer.OnBufferingUpdateListener
        public void onBufferingUpdate(MediaPlayer mediaPlayer, int i2) {
            if (this.mWeakMediaPlayer.get() == null) {
                return;
            }
            AndroidMediaPlayer.this.notifyOnBufferingUpdate(i2);
        }

        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(MediaPlayer mediaPlayer) {
            if (this.mWeakMediaPlayer.get() == null) {
                return;
            }
            AndroidMediaPlayer.this.notifyOnCompletion();
        }

        @Override // android.media.MediaPlayer.OnErrorListener
        public boolean onError(MediaPlayer mediaPlayer, int i2, int i3) {
            return this.mWeakMediaPlayer.get() != null && AndroidMediaPlayer.this.notifyOnError(i2, i3);
        }

        @Override // android.media.MediaPlayer.OnInfoListener
        public boolean onInfo(MediaPlayer mediaPlayer, int i2, int i3) {
            return this.mWeakMediaPlayer.get() != null && AndroidMediaPlayer.this.notifyOnInfo(i2, i3);
        }

        @Override // android.media.MediaPlayer.OnPreparedListener
        public void onPrepared(MediaPlayer mediaPlayer) {
            if (this.mWeakMediaPlayer.get() == null) {
                return;
            }
            AndroidMediaPlayer.this.notifyOnPrepared();
        }

        @Override // android.media.MediaPlayer.OnSeekCompleteListener
        public void onSeekComplete(MediaPlayer mediaPlayer) {
            if (this.mWeakMediaPlayer.get() == null) {
                return;
            }
            AndroidMediaPlayer.this.notifyOnSeekComplete();
        }

        @Override // android.media.MediaPlayer.OnTimedTextListener
        public void onTimedText(MediaPlayer mediaPlayer, TimedText timedText) {
            if (this.mWeakMediaPlayer.get() == null) {
                return;
            }
            AndroidMediaPlayer.this.notifyOnTimedText(timedText != null ? new IjkTimedText(timedText.getBounds(), timedText.getText()) : null);
        }

        @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i2, int i3) {
            if (this.mWeakMediaPlayer.get() == null) {
                return;
            }
            AndroidMediaPlayer.this.notifyOnVideoSizeChanged(i2, i3, 1, 1);
        }
    }

    @TargetApi(23)
    public static class MediaDataSourceProxy extends MediaDataSource {
        private final IMediaDataSource mMediaDataSource;

        public MediaDataSourceProxy(IMediaDataSource iMediaDataSource) {
            this.mMediaDataSource = iMediaDataSource;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.mMediaDataSource.close();
        }

        @Override // android.media.MediaDataSource
        public long getSize() throws IOException {
            return this.mMediaDataSource.getSize();
        }

        @Override // android.media.MediaDataSource
        public int readAt(long j2, byte[] bArr, int i2, int i3) throws IOException {
            return this.mMediaDataSource.readAt(j2, bArr, i2, i3);
        }
    }

    public AndroidMediaPlayer() {
        MediaPlayer mediaPlayer;
        Object obj = new Object();
        this.mInitLock = obj;
        synchronized (obj) {
            mediaPlayer = new MediaPlayer();
            this.mInternalMediaPlayer = mediaPlayer;
        }
        mediaPlayer.setAudioStreamType(3);
        this.mInternalListenerAdapter = new AndroidMediaPlayerListenerHolder(this);
        attachInternalListeners();
    }

    private void attachInternalListeners() {
        this.mInternalMediaPlayer.setOnPreparedListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnBufferingUpdateListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnCompletionListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnSeekCompleteListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnVideoSizeChangedListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnErrorListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnInfoListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnTimedTextListener(this.mInternalListenerAdapter);
    }

    private void releaseMediaDataSource() throws IOException {
        MediaDataSource mediaDataSource = this.mMediaDataSource;
        if (mediaDataSource != null) {
            try {
                mediaDataSource.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            this.mMediaDataSource = null;
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getAudioSessionId() {
        return this.mInternalMediaPlayer.getAudioSessionId();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public long getCurrentPosition() {
        try {
            return this.mInternalMediaPlayer.getCurrentPosition();
        } catch (IllegalStateException e2) {
            DebugLog.printStackTrace(e2);
            return 0L;
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public String getDataSource() {
        return this.mDataSource;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public long getDuration() {
        try {
            return this.mInternalMediaPlayer.getDuration();
        } catch (IllegalStateException e2) {
            DebugLog.printStackTrace(e2);
            return 0L;
        }
    }

    public MediaPlayer getInternalMediaPlayer() {
        return this.mInternalMediaPlayer;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public MediaInfo getMediaInfo() {
        if (sMediaInfo == null) {
            MediaInfo mediaInfo = new MediaInfo();
            mediaInfo.mVideoDecoder = "android";
            mediaInfo.mVideoDecoderImpl = "HW";
            mediaInfo.mAudioDecoder = "android";
            mediaInfo.mAudioDecoderImpl = "HW";
            sMediaInfo = mediaInfo;
        }
        return sMediaInfo;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public ITrackInfo[] getTrackInfo() {
        return AndroidTrackInfo.fromMediaPlayer(this.mInternalMediaPlayer);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getVideoHeight() {
        return this.mInternalMediaPlayer.getVideoHeight();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getVideoSarDen() {
        return 1;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getVideoSarNum() {
        return 1;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getVideoWidth() {
        return this.mInternalMediaPlayer.getVideoWidth();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public boolean isLooping() {
        return this.mInternalMediaPlayer.isLooping();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public boolean isPlayable() {
        return true;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public boolean isPlaying() {
        try {
            return this.mInternalMediaPlayer.isPlaying();
        } catch (IllegalStateException e2) {
            DebugLog.printStackTrace(e2);
            return false;
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void pause() throws IllegalStateException {
        this.mInternalMediaPlayer.pause();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void prepareAsync() throws IllegalStateException {
        this.mInternalMediaPlayer.prepareAsync();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void release() throws IOException {
        this.mIsReleased = true;
        this.mInternalMediaPlayer.release();
        releaseMediaDataSource();
        resetListeners();
        attachInternalListeners();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void reset() throws IOException {
        try {
            this.mInternalMediaPlayer.reset();
        } catch (IllegalStateException e2) {
            DebugLog.printStackTrace(e2);
        }
        releaseMediaDataSource();
        resetListeners();
        attachInternalListeners();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void seekTo(long j2) throws IllegalStateException {
        this.mInternalMediaPlayer.seekTo((int) j2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setAudioStreamType(int i2) {
        this.mInternalMediaPlayer.setAudioStreamType(i2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(Context context, Uri uri) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.mInternalMediaPlayer.setDataSource(context, uri);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(String str, byte[] bArr, Map<String, String> map) {
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDisplay(SurfaceHolder surfaceHolder) {
        synchronized (this.mInitLock) {
            if (!this.mIsReleased) {
                this.mInternalMediaPlayer.setDisplay(surfaceHolder);
            }
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setKeepInBackground(boolean z2) {
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setLogEnabled(boolean z2) {
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setLooping(boolean z2) {
        this.mInternalMediaPlayer.setLooping(z2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setScreenOnWhilePlaying(boolean z2) {
        this.mInternalMediaPlayer.setScreenOnWhilePlaying(z2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    @TargetApi(14)
    public void setSurface(Surface surface) {
        this.mInternalMediaPlayer.setSurface(surface);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setVolume(float f2, float f3) {
        this.mInternalMediaPlayer.setVolume(f2, f3);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setWakeMode(Context context, int i2) {
        this.mInternalMediaPlayer.setWakeMode(context, i2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void start() throws IllegalStateException {
        this.mInternalMediaPlayer.start();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void stop() throws IllegalStateException {
        this.mInternalMediaPlayer.stop();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    @TargetApi(14)
    public void setDataSource(Context context, Uri uri, Map<String, String> map) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.mInternalMediaPlayer.setDataSource(context, uri, map);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(FileDescriptor fileDescriptor) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mInternalMediaPlayer.setDataSource(fileDescriptor);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(String str) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.mDataSource = str;
        Uri uri = Uri.parse(str);
        String scheme = uri.getScheme();
        if (!TextUtils.isEmpty(scheme) && scheme.equalsIgnoreCase("file")) {
            this.mInternalMediaPlayer.setDataSource(uri.getPath());
        } else {
            this.mInternalMediaPlayer.setDataSource(str);
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.AbstractMediaPlayer, com.easefun.polyv.mediasdk.player.IMediaPlayer
    @TargetApi(23)
    public void setDataSource(IMediaDataSource iMediaDataSource) throws IllegalStateException, IOException, IllegalArgumentException {
        releaseMediaDataSource();
        MediaDataSourceProxy mediaDataSourceProxy = new MediaDataSourceProxy(iMediaDataSource);
        this.mMediaDataSource = mediaDataSourceProxy;
        this.mInternalMediaPlayer.setDataSource(mediaDataSourceProxy);
    }
}
