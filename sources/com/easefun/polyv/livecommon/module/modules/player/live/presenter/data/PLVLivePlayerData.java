package com.easefun.polyv.livecommon.module.modules.player.live.presenter.data;

import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.easefun.polyv.livecommon.module.modules.player.PLVPlayerState;

/* loaded from: classes3.dex */
public class PLVLivePlayerData {
    private MutableLiveData<Integer> linesPos = new MutableLiveData<>();
    private MutableLiveData<PLVPlayerState> playerState = new MutableLiveData<>();
    private MutableLiveData<Boolean> pptShowState = new MutableLiveData<>();
    private MutableLiveData<Pair<Boolean, Boolean>> linkMicOpen = new MutableLiveData<>();
    private MutableLiveData<PLVPlayInfoVO> playInfoVO = new MutableLiveData<>();
    private MutableLiveData<Long> seiData = new MutableLiveData<>();
    private MutableLiveData<Boolean> castOpen = new MutableLiveData<>();

    public LiveData<Boolean> getCastInitState() {
        return this.castOpen;
    }

    public LiveData<Integer> getLinesPos() {
        return this.linesPos;
    }

    public LiveData<Pair<Boolean, Boolean>> getLinkMicState() {
        return this.linkMicOpen;
    }

    public LiveData<Boolean> getPPTShowState() {
        return this.pptShowState;
    }

    public LiveData<PLVPlayInfoVO> getPlayInfoVO() {
        return this.playInfoVO;
    }

    public LiveData<PLVPlayerState> getPlayerState() {
        return this.playerState;
    }

    public LiveData<Long> getSeiData() {
        return this.seiData;
    }

    public void postCastInitData(boolean initResult) {
        this.castOpen.postValue(Boolean.valueOf(initResult));
    }

    public void postLinesChange(int linesPos) {
        this.linesPos.postValue(Integer.valueOf(linesPos));
    }

    public void postLinkMicOpen(boolean isLinkMicOpen, boolean isAudio) {
        this.linkMicOpen.postValue(new Pair<>(Boolean.valueOf(isLinkMicOpen), Boolean.valueOf(isAudio)));
    }

    public void postLiveEnd() {
        this.playerState.postValue(PLVPlayerState.LIVE_END);
    }

    public void postLiveStop() {
        this.playerState.postValue(PLVPlayerState.LIVE_STOP);
    }

    public void postNoLive() {
        this.playerState.postValue(PLVPlayerState.NO_LIVE);
    }

    public void postPPTShowState(boolean visible) {
        this.pptShowState.postValue(Boolean.valueOf(visible));
    }

    public void postPlayInfoVO(PLVPlayInfoVO playInfo) {
        this.playInfoVO.postValue(playInfo);
    }

    public void postPrepared() {
        this.playerState.postValue(PLVPlayerState.PREPARED);
    }

    public void postSeiData(long data) {
        this.seiData.postValue(Long.valueOf(data));
    }
}
