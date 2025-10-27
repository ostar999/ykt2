package com.plv.business.api.common.player.listener;

import androidx.annotation.IntRange;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.business.api.common.player.PLVPlayError;
import com.plv.business.api.common.player.PLVPlayErrorReason;
import com.plv.business.model.video.PLVLiveMarqueeVO;
import com.plv.business.model.video.PLVWatermarkVO;

/* loaded from: classes4.dex */
public interface IPLVVideoViewListenerEvent {

    public interface OnAdvertisementCountDownListener {
        @MainThread
        void onCountDown(int i2);

        @MainThread
        void onEnd();
    }

    public interface OnAuxiliaryPlayEndListener {
        @MainThread
        void onEnd(boolean z2);
    }

    public interface OnBufferingUpdateListener {
        @MainThread
        void onBufferingUpdate(int i2);
    }

    public interface OnCompletionListener {
        @MainThread
        void onCompletion();
    }

    public interface OnCoverImageOutListener {
        @MainThread
        void onOut(@NonNull String str, @Nullable String str2);
    }

    public interface OnDanmuServerOpenListener {
        void onDanmuServerOpenListener(boolean z2);
    }

    public interface OnErrorListener {
        @MainThread
        void onError(int i2, int i3);

        @MainThread
        void onError(PLVPlayError pLVPlayError);
    }

    public interface OnGestureClickListener {
        @MainThread
        void callback(boolean z2, boolean z3);
    }

    public interface OnGestureDoubleClickListener {
        @MainThread
        void callback();
    }

    public interface OnGestureLeftDownListener {
        @MainThread
        void callback(boolean z2, boolean z3);
    }

    public interface OnGestureLeftUpListener {
        @MainThread
        void callback(boolean z2, boolean z3);
    }

    public interface OnGestureRightDownListener {
        @MainThread
        void callback(boolean z2, boolean z3);
    }

    public interface OnGestureRightUpListener {
        @MainThread
        void callback(boolean z2, boolean z3);
    }

    public interface OnGestureSwipeLeftListener {
        @MainThread
        void callback(boolean z2, boolean z3, int i2);
    }

    public interface OnGestureSwipeRightListener {
        @MainThread
        void callback(boolean z2, boolean z3, int i2);
    }

    public interface OnGetLogoListener {
        void onLogo(String str, @IntRange(from = 0, to = 100) int i2, @IntRange(from = 0, to = 4) int i3, String str2);
    }

    public interface OnGetMarqueeVoListener {
        void onGetMarqueeVo(PLVLiveMarqueeVO pLVLiveMarqueeVO);
    }

    public interface OnGetWatermarkVoListener {
        void onGetWatermarkVO(PLVWatermarkVO pLVWatermarkVO);
    }

    public interface OnInfoListener {
        @MainThread
        void onInfo(int i2, int i3);
    }

    public interface OnNetworkStateListener {
        boolean onNetworkError();

        boolean onNetworkRecover();
    }

    public interface OnPPTShowListener {
        void showPPTView(int i2);
    }

    public interface OnPreparedListener {
        @MainThread
        void onPrepared();

        @MainThread
        void onPreparing();
    }

    public interface OnRemindCallbackListener {
        @MainThread
        void callback();
    }

    public interface OnSEIRefreshListener {
        void onSEIRefresh(int i2, byte[] bArr);
    }

    public interface OnSeekCompleteListener {
        @MainThread
        void onSeekComplete();
    }

    public interface OnVideoLoadSlowListener {
        void onLoadSlow(int i2, boolean z2);
    }

    public interface OnVideoPauseListener {
        @MainThread
        void onPause();
    }

    public interface OnVideoPlayErrorListener {
        @MainThread
        void onVideoPlayError(@NonNull PLVPlayErrorReason pLVPlayErrorReason);
    }

    public interface OnVideoPlayListener {
        @MainThread
        void onPlay(boolean z2);
    }

    public interface OnVideoSizeChangedListener {
        @MainThread
        void onVideoSizeChanged(int i2, int i3, int i4, int i5);
    }

    public interface OnVideoViewRestart {
        @MainThread
        void restartLoad(boolean z2);
    }
}
