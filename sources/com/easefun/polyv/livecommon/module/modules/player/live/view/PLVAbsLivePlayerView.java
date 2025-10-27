package com.easefun.polyv.livecommon.module.modules.player.live.view;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.easefun.polyv.businesssdk.api.auxiliary.PolyvAuxiliaryVideoview;
import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayError;
import com.easefun.polyv.livecommon.module.modules.marquee.IPLVMarqueeView;
import com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract;
import com.easefun.polyv.livecommon.module.modules.player.live.presenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livescenes.video.PolyvLiveVideoView;

/* loaded from: classes3.dex */
public abstract class PLVAbsLivePlayerView implements IPLVLivePlayerContract.ILivePlayerView {
    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public View getBufferingIndicator() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public PolyvLiveVideoView getLiveVideoView() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public PLVPlayerLogoView getLogo() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public IPLVMarqueeView getMarqueeView() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public View getNoStreamIndicator() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public View getPlayErrorIndicator() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public PolyvAuxiliaryVideoview getSubVideoView() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public boolean onLightChanged(int changeValue, boolean isEnd) {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onLinesChanged(int linesPos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onLiveEnd() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onLiveStop() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onLoadSlow(int loadedTime, boolean isBufferEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onLowLatencyNetworkQuality(int networkQuality) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public boolean onNetworkRecover() {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onNoLiveAtPresent() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onOnlyAudio(boolean isOnlyAudio) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onPlayError(PolyvPlayError error, String tips) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onPrepared(int mediaPlayMode) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onRestartPlay() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onServerDanmuOpen(boolean isServerDanmuOpen) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onShowPPTView(int visible) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onSubVideoViewClick(boolean mainPlayerIsPlaying) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onSubVideoViewCountDown(boolean isOpenAdHead, int totalTime, int remainTime, int adStage) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onSubVideoViewLoadImage(String imageUrl, ImageView imageView) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onSubVideoViewPlay(boolean isFirst) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void onSubVideoViewVisiblityChanged(boolean isOpenAdHead, boolean isShow) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public boolean onVolumeChanged(int changeValue, boolean isEnd) {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void setPresenter(@NonNull IPLVLivePlayerContract.ILivePlayerPresenter presenter) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
    public void updatePlayInfo(PLVPlayInfoVO playInfoVO) {
    }
}
