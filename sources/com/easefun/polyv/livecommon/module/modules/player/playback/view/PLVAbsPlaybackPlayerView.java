package com.easefun.polyv.livecommon.module.modules.player.playback.view;

import android.view.View;
import androidx.annotation.NonNull;
import com.easefun.polyv.businesssdk.api.auxiliary.PolyvAuxiliaryVideoview;
import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayError;
import com.easefun.polyv.livecommon.module.modules.marquee.IPLVMarqueeView;
import com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livescenes.playback.video.PolyvPlaybackVideoView;

/* loaded from: classes3.dex */
public abstract class PLVAbsPlaybackPlayerView implements IPLVPlaybackPlayerContract.IPlaybackPlayerView {
    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public View getBufferingIndicator() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public PLVPlayerLogoView getLogo() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public IPLVMarqueeView getMarqueeView() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public View getNoStreamIndicator() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public View getPlayErrorIndicator() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public PolyvPlaybackVideoView getPlaybackVideoView() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public View getRetryLayout() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public PolyvAuxiliaryVideoview getSubVideoView() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onAutoContinuePlaySeeked(int seekTo) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onBufferEnd() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onBufferStart() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onCompletion() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onDoubleClick() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public boolean onLightChanged(int changeValue, boolean isEnd) {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onLoadSlow(int loadedTime, boolean isBufferEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onPlayError(PolyvPlayError error, String tips) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onPrepared() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public boolean onProgressChanged(int seekTime, int totalTime, boolean isEnd, boolean isRightSwipe) {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onServerDanmuOpen(boolean isServerDanmuOpen) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onShowPPTView(int visible) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onSubVideoViewCountDown(boolean isOpenAdHead, int totalTime, int remainTime, int adStage) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onSubVideoViewPlay(boolean isFirst) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onSubVideoViewVisiblityChanged(boolean isOpenAdHead, boolean isShow) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onVideoPause() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void onVideoPlay(boolean isFirst) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public boolean onVolumeChanged(int changeValue, boolean isEnd) {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void setPresenter(@NonNull IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter presenter) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
    public void updatePlayInfo(PLVPlayInfoVO playInfoVO) {
    }
}
