package com.easefun.polyv.livecommon.module.modules.player.live.contract;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.businesssdk.api.auxiliary.PolyvAuxiliaryVideoview;
import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayError;
import com.easefun.polyv.businesssdk.model.video.PolyvDefinitionVO;
import com.easefun.polyv.businesssdk.model.video.PolyvLiveChannelVO;
import com.easefun.polyv.livecommon.module.modules.marquee.IPLVMarqueeView;
import com.easefun.polyv.livecommon.module.modules.player.live.presenter.data.PLVLivePlayerData;
import com.easefun.polyv.livecommon.module.modules.player.live.presenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.modules.watermark.IPLVWatermarkView;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livescenes.video.PolyvLiveVideoView;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPLVLivePlayerContract {

    public interface ILivePlayerPresenter {
        void changeBitRate(int bitRate);

        void changeLines(int linesPos);

        void changeMediaPlayMode(int mediaPlayMode);

        void destroy();

        int getBitratePos();

        @Nullable
        List<PolyvDefinitionVO> getBitrateVO();

        @Nullable
        PolyvLiveChannelVO getChannelVO();

        @NonNull
        PLVLivePlayerData getData();

        int getLinesCount();

        int getLinesPos();

        int getMediaPlayMode();

        String getSubVideoViewHerf();

        int getVolume();

        void init();

        boolean isInPlaybackState();

        boolean isPlaying();

        boolean isSubVideoViewShow();

        void pause();

        void registerView(@NonNull ILivePlayerView v2);

        void restartPlay();

        void resume();

        @Nullable
        Bitmap screenshot();

        void setAllowOpenAdHead(boolean isAllowOpenAdHead);

        void setNeedGestureDetector(boolean need);

        void setPlayerVolume(int volume);

        void setVolume(int volume);

        void startPlay();

        void startPlay(boolean lowLatency);

        void stop();

        void unregisterView();
    }

    public interface ILivePlayerView {
        View getBufferingIndicator();

        PolyvLiveVideoView getLiveVideoView();

        PLVPlayerLogoView getLogo();

        IPLVMarqueeView getMarqueeView();

        View getNoStreamIndicator();

        View getPlayErrorIndicator();

        PolyvAuxiliaryVideoview getSubVideoView();

        IPLVWatermarkView getWatermarkView();

        boolean onLightChanged(int changeValue, boolean isEnd);

        void onLinesChanged(int linesPos);

        void onLiveEnd();

        void onLiveStop();

        void onLoadSlow(int loadedTime, boolean isBufferEvent);

        void onLowLatencyNetworkQuality(int networkQuality);

        boolean onNetworkRecover();

        void onNoLiveAtPresent();

        void onOnlyAudio(boolean isOnlyAudio);

        void onPlayError(PolyvPlayError error, String tips);

        void onPrepared(int mediaPlayMode);

        void onRestartPlay();

        void onServerDanmuOpen(boolean isServerDanmuOpen);

        void onShowPPTView(int visible);

        void onSubVideoViewClick(boolean mainPlayerIsPlaying);

        void onSubVideoViewCountDown(boolean isOpenAdHead, int totalTime, int remainTime, int adStage);

        void onSubVideoViewLoadImage(String imageUrl, ImageView imageView);

        void onSubVideoViewPlay(boolean isFirst);

        void onSubVideoViewVisiblityChanged(boolean isOpenAdHead, boolean isShow);

        boolean onVolumeChanged(int changeValue, boolean isEnd);

        void setPresenter(@NonNull ILivePlayerPresenter presenter);

        void updatePlayInfo(PLVPlayInfoVO playInfoVO);
    }
}
