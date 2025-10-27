package com.easefun.polyv.livecommon.module.modules.streamer.presenter.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.plv.linkmic.PLVLinkMicConstant;

/* loaded from: classes3.dex */
public class PLVStreamerData {
    private MutableLiveData<Boolean> streamerStatus = new MutableLiveData<>();
    private MutableLiveData<Integer> networkQuality = new MutableLiveData<>();
    private MutableLiveData<Integer> streamerTime = new MutableLiveData<>();
    private MutableLiveData<Boolean> showNetBroken = new MutableLiveData<>();
    private MutableLiveData<String> userRequestData = new MutableLiveData<>();
    private MutableLiveData<Boolean> enableAudio = new MutableLiveData<>();
    private MutableLiveData<Boolean> enableVideo = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFrontCamera = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFrontMirrorMode = new MutableLiveData<>();
    private MutableLiveData<Integer> curBitrate = new MutableLiveData<>();
    private MutableLiveData<PLVLinkMicConstant.PushResolutionRatio> pushResolutionRatioLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> curLinkMicCount = new MutableLiveData<>();
    private MutableLiveData<Boolean> enableShareScreen = new MutableLiveData<>();

    public LiveData<Integer> getCurBitrate() {
        return this.curBitrate;
    }

    public LiveData<Boolean> getEnableAudio() {
        return this.enableAudio;
    }

    public LiveData<Boolean> getEnableVideo() {
        return this.enableVideo;
    }

    public LiveData<Boolean> getIsFrontCamera() {
        return this.isFrontCamera;
    }

    public LiveData<Boolean> getIsFrontMirrorMode() {
        return this.isFrontMirrorMode;
    }

    public LiveData<Boolean> getIsStartShareScreen() {
        return this.enableShareScreen;
    }

    public LiveData<Integer> getLinkMicCount() {
        return this.curLinkMicCount;
    }

    public LiveData<Integer> getNetworkQuality() {
        return this.networkQuality;
    }

    public LiveData<PLVLinkMicConstant.PushResolutionRatio> getPushResolutionRatio() {
        return this.pushResolutionRatioLiveData;
    }

    public LiveData<Boolean> getShowNetBroken() {
        return this.showNetBroken;
    }

    public LiveData<Boolean> getStreamerStatus() {
        return this.streamerStatus;
    }

    public LiveData<Integer> getStreamerTime() {
        return this.streamerTime;
    }

    public LiveData<String> getUserRequestData() {
        return this.userRequestData;
    }

    public void postCurBitrate(int bitrate) {
        this.curBitrate.postValue(Integer.valueOf(bitrate));
    }

    public void postEnableAudio(boolean isEnableAudio) {
        this.enableAudio.postValue(Boolean.valueOf(isEnableAudio));
    }

    public void postEnableShareScreen(boolean isStartShareScreen) {
        this.enableShareScreen.postValue(Boolean.valueOf(isStartShareScreen));
    }

    public void postEnableVideo(boolean isEnableVideo) {
        this.enableVideo.postValue(Boolean.valueOf(isEnableVideo));
    }

    public void postIsFrontCamera(boolean frontCamera) {
        this.isFrontCamera.postValue(Boolean.valueOf(frontCamera));
    }

    public void postIsFrontMirrorMode(boolean isMirror) {
        this.isFrontMirrorMode.postValue(Boolean.valueOf(isMirror));
    }

    public void postLinkMicCount(int count) {
        this.curLinkMicCount.postValue(Integer.valueOf(count));
    }

    public void postNetworkQuality(int quality) {
        this.networkQuality.postValue(Integer.valueOf(quality));
    }

    public void postPushResolutionRatio(PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio) {
        this.pushResolutionRatioLiveData.postValue(pushResolutionRatio);
    }

    public void postShowNetBroken() {
        this.showNetBroken.postValue(Boolean.TRUE);
    }

    public void postStreamerStatus(boolean isStarted) {
        this.streamerStatus.postValue(Boolean.valueOf(isStarted));
    }

    public void postStreamerTime(int duration) {
        this.streamerTime.postValue(Integer.valueOf(duration));
    }

    public void postUserRequestData(String uid) {
        this.userRequestData.postValue(uid);
    }
}
