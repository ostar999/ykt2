package com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.easefun.polyv.livecommon.module.modules.player.PLVPlayerState;

/* loaded from: classes3.dex */
public class PLVPlaybackPlayerData {
    private MutableLiveData<PLVPlayerState> playerState = new MutableLiveData<>();
    private MutableLiveData<Boolean> pptShowState = new MutableLiveData<>();
    private MutableLiveData<PLVPlayInfoVO> playInfoVO = new MutableLiveData<>();
    private MutableLiveData<Integer> seekCompleteVO = new MutableLiveData<>();

    public LiveData<Boolean> getPPTShowState() {
        return this.pptShowState;
    }

    public LiveData<PLVPlayInfoVO> getPlayInfoVO() {
        return this.playInfoVO;
    }

    public LiveData<PLVPlayerState> getPlayerState() {
        return this.playerState;
    }

    public LiveData<Integer> getSeekCompleteVO() {
        return this.seekCompleteVO;
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

    public void postSeekComplete(int time) {
        this.seekCompleteVO.postValue(Integer.valueOf(time));
    }
}
