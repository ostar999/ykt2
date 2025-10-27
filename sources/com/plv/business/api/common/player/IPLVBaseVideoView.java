package com.plv.business.api.common.player;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.GestureDetector;
import android.view.View;
import androidx.annotation.NonNull;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import com.plv.business.api.common.mediacontrol.IPLVMediaController;

/* loaded from: classes4.dex */
public interface IPLVBaseVideoView<T extends IPLVMediaController> {
    public static final int MEDIA_INFO_AUDIO_RENDERING_START = 10002;
    public static final int MEDIA_INFO_BAD_INTERLEAVING = 800;
    public static final int MEDIA_INFO_BUFFERING_END = 702;
    public static final int MEDIA_INFO_BUFFERING_START = 701;
    public static final int MEDIA_INFO_METADATA_UPDATE = 802;
    public static final int MEDIA_INFO_NETWORK_BANDWIDTH = 703;
    public static final int MEDIA_INFO_NOT_SEEKABLE = 801;
    public static final int MEDIA_INFO_STARTED_AS_NEXT = 2;
    public static final int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;
    public static final int MEDIA_INFO_TIMED_TEXT_ERROR = 900;
    public static final int MEDIA_INFO_UNKNOWN = 1;
    public static final int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;
    public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3;
    public static final int MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001;
    public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;

    boolean canStart();

    void closeSound();

    void destroy();

    void enterBackground();

    int getAspectRatio();

    int getBrightness(Activity activity);

    String getCurrentPlayPath();

    GestureDetector getGestureDetector();

    IjkMediaPlayer getIjkMediaPlayer();

    T getMediaController();

    IMediaPlayer getMediaPlayer();

    boolean getNeedGestureDetector();

    int getPlayerVolume();

    float getSpeed();

    long getTcpSpeed();

    int getVolume();

    boolean isBufferState();

    boolean isCompletedState();

    boolean isInPlaybackState();

    boolean isInPlaybackStateEx();

    boolean isLivePlayMode();

    boolean isOpenSound();

    boolean isPlayState();

    boolean isPlayState(boolean z2);

    boolean isPreparedState();

    boolean isPreparingState();

    boolean isRealPlaying();

    boolean isTargetCompletedState();

    boolean isVodPlayMode();

    void keepPlayerVolume(boolean z2);

    void openKeepScreenOn(boolean z2);

    void openSound();

    void pause(boolean z2);

    void release(boolean z2);

    void removeRenderView();

    Bitmap screenshot();

    boolean setAspectRatio(int i2);

    void setBrightness(Activity activity, int i2);

    void setDecodeMode(int i2);

    void setMediaController(T t2);

    void setMirror(boolean z2);

    void setNeedGestureDetector(boolean z2);

    void setNoStreamIndicator(@NonNull View view);

    void setPlayerBufferingIndicator(@NonNull View view);

    void setPlayerVolume(int i2);

    void setSpeed(float f2);

    void setStopStreamIndicator(@NonNull View view);

    void setVolume(int i2);

    void stopPlay();
}
