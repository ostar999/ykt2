package com.easefun.polyv.livecommon.module.modules.chatroom.presenter.data;

import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.easefun.polyv.livescenes.model.bulletin.PolyvBulletinVO;
import com.plv.livescenes.model.PLVChatFunctionSwitchVO;
import com.plv.livescenes.model.PLVEmotionImageVO;
import com.plv.socket.event.chat.PLVRewardEvent;
import com.plv.socket.event.login.PLVLoginEvent;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVChatroomData {
    private MutableLiveData<PolyvBulletinVO> bulletinVO = new MutableLiveData<>();
    private MutableLiveData<List<PLVChatFunctionSwitchVO.DataBean>> functionSwitchData = new MutableLiveData<>();
    private MutableLiveData<Long> likesCountData = new MutableLiveData<>();
    private MutableLiveData<Long> viewerCountData = new MutableLiveData<>();
    private MutableLiveData<Integer> onlineCountData = new MutableLiveData<>();
    private MutableLiveData<Integer> kickCountData = new MutableLiveData<>();
    private MutableLiveData<Pair<CharSequence, Boolean>> speakMessageData = new MutableLiveData<>();
    private MutableLiveData<PLVLoginEvent> loginEventData = new MutableLiveData<>();
    private MutableLiveData<PLVRewardEvent> rewardEventData = new MutableLiveData<>();
    private MutableLiveData<List<PLVEmotionImageVO.EmotionImage>> emotionImagesData = new MutableLiveData<>();

    public LiveData<PolyvBulletinVO> getBulletinVO() {
        return this.bulletinVO;
    }

    public LiveData<List<PLVEmotionImageVO.EmotionImage>> getEmotionImages() {
        return this.emotionImagesData;
    }

    public LiveData<List<PLVChatFunctionSwitchVO.DataBean>> getFunctionSwitchData() {
        return this.functionSwitchData;
    }

    public LiveData<Integer> getKickCountData() {
        return this.kickCountData;
    }

    public LiveData<Long> getLikesCountData() {
        return this.likesCountData;
    }

    public LiveData<PLVLoginEvent> getLoginEventData() {
        return this.loginEventData;
    }

    public LiveData<Integer> getOnlineCountData() {
        return this.onlineCountData;
    }

    public LiveData<PLVRewardEvent> getRewardEvent() {
        return this.rewardEventData;
    }

    public LiveData<Pair<CharSequence, Boolean>> getSpeakMessageData() {
        return this.speakMessageData;
    }

    public LiveData<Long> getViewerCountData() {
        return this.viewerCountData;
    }

    public void postBulletinVO(PolyvBulletinVO bulletin) {
        this.bulletinVO.postValue(bulletin);
    }

    public void postEmotionImages(List<PLVEmotionImageVO.EmotionImage> emotionImages) {
        this.emotionImagesData.postValue(emotionImages);
    }

    public void postFunctionSwitchData(List<PLVChatFunctionSwitchVO.DataBean> data) {
        this.functionSwitchData.postValue(data);
    }

    public void postKickCountData(int kickCount) {
        this.kickCountData.postValue(Integer.valueOf(kickCount));
    }

    public void postLikesCountData(long likesCount) {
        this.likesCountData.postValue(Long.valueOf(likesCount));
    }

    public void postLoginEventData(PLVLoginEvent loginEvent) {
        this.loginEventData.postValue(loginEvent);
    }

    public void postOnlineCountData(int onlineCount) {
        this.onlineCountData.postValue(Integer.valueOf(onlineCount));
    }

    public void postRewardEvent(PLVRewardEvent rewardEvent) {
        this.rewardEventData.postValue(rewardEvent);
    }

    public void postSpeakMessageData(CharSequence message, boolean isSpecialType) {
        this.speakMessageData.postValue(new Pair<>(message, Boolean.valueOf(isSpecialType)));
    }

    public void postViewerCountData(long viewerCount) {
        this.viewerCountData.postValue(Long.valueOf(viewerCount));
    }
}
