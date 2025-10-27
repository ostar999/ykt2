package com.easefun.polyv.livecommon.module.modules.player.playback.contract;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.businesssdk.api.auxiliary.PolyvAuxiliaryVideoview;
import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayError;
import com.easefun.polyv.businesssdk.api.common.ppt.IPolyvPPTView;
import com.easefun.polyv.livecommon.module.modules.marquee.IPLVMarqueeView;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlaybackPlayerData;
import com.easefun.polyv.livecommon.module.modules.watermark.IPLVWatermarkView;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livescenes.playback.video.PolyvPlaybackVideoView;

/* loaded from: classes3.dex */
public interface IPLVPlaybackPlayerContract {

    public interface IPlaybackPlayerPresenter {
        void bindPPTView(IPolyvPPTView pptView);

        void destroy();

        @NonNull
        PLVPlaybackPlayerData getData();

        int getDuration();

        String getSessionId();

        float getSpeed();

        String getSubVideoViewHerf();

        int getVideoCurrentPosition();

        @Nullable
        String getVideoName();

        int getVolume();

        void init();

        boolean isInPlaybackState();

        boolean isPlaying();

        boolean isSubVideoViewShow();

        void pause();

        void registerView(@NonNull IPlaybackPlayerView v2);

        void resume();

        void seekTo(int duration);

        void seekTo(int progress, int max);

        void setAllowOpenAdHead(boolean isAllowOpenAdHead);

        void setPlayerVid(String vid);

        void setPlayerVidAndPlay(String vid);

        void setPlayerVolume(int volume);

        void setSpeed(float speed);

        void setVolume(int volume);

        void startPlay();

        void stop();

        void unregisterView();
    }

    public interface IPlaybackPlayerView {
        View getBufferingIndicator();

        PLVPlayerLogoView getLogo();

        IPLVMarqueeView getMarqueeView();

        View getNoStreamIndicator();

        View getPlayErrorIndicator();

        PolyvPlaybackVideoView getPlaybackVideoView();

        View getRetryLayout();

        PolyvAuxiliaryVideoview getSubVideoView();

        IPLVWatermarkView getWatermarkView();

        void onAutoContinuePlaySeeked(int seekTo);

        void onBufferEnd();

        void onBufferStart();

        void onCompletion();

        void onDoubleClick();

        boolean onLightChanged(int changeValue, boolean isEnd);

        void onLoadSlow(int loadedTime, boolean isBufferEvent);

        void onPlayError(PolyvPlayError error, String tips);

        void onPrepared();

        boolean onProgressChanged(int seekTime, int totalTime, boolean isEnd, boolean isRightSwipe);

        void onServerDanmuOpen(boolean isServerDanmuOpen);

        void onShowPPTView(int visible);

        void onSubVideoViewCountDown(boolean isOpenAdHead, int totalTime, int remainTime, int adStage);

        void onSubVideoViewPlay(boolean isFirst);

        void onSubVideoViewVisiblityChanged(boolean isOpenAdHead, boolean isShow);

        void onVideoPause();

        void onVideoPlay(boolean isFirst);

        boolean onVolumeChanged(int changeValue, boolean isEnd);

        void setPresenter(@NonNull IPlaybackPlayerPresenter presenter);

        void updatePlayInfo(PLVPlayInfoVO playInfoVO);
    }
}
