package com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/* loaded from: classes3.dex */
public class PLVMultiRoleLinkMicData {
    private MutableLiveData<Boolean> enableAudio = new MutableLiveData<>();
    private MutableLiveData<Boolean> enableVideo = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFrontCamera = new MutableLiveData<>();
    private MutableLiveData<Integer> limitLinkNumber = new MutableLiveData<>();

    public LiveData<Boolean> getEnableAudio() {
        return this.enableAudio;
    }

    public LiveData<Boolean> getEnableVideo() {
        return this.enableVideo;
    }

    public LiveData<Boolean> getIsFrontCamera() {
        return this.isFrontCamera;
    }

    public LiveData<Integer> getLimitLinkNumber() {
        return this.limitLinkNumber;
    }

    public void postEnableAudio(boolean isEnableAudio) {
        this.enableAudio.postValue(Boolean.valueOf(isEnableAudio));
    }

    public void postEnableVideo(boolean isEnableVideo) {
        this.enableVideo.postValue(Boolean.valueOf(isEnableVideo));
    }

    public void postIsFrontCamera(boolean frontCamera) {
        this.isFrontCamera.postValue(Boolean.valueOf(frontCamera));
    }

    public void postLimitLinkNumber(int maxLinkNumber) {
        this.limitLinkNumber.postValue(Integer.valueOf(maxLinkNumber));
    }
}
