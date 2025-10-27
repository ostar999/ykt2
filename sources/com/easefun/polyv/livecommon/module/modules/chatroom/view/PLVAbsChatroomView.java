package com.easefun.polyv.livecommon.module.modules.chatroom.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVCustomGiftBean;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.PolyvQuestionMessage;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.easefun.polyv.livescenes.model.bulletin.PolyvBulletinVO;
import com.plv.livescenes.chatroom.send.custom.PLVCustomEvent;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.chat.PLVChatEmotionEvent;
import com.plv.socket.event.chat.PLVChatImgEvent;
import com.plv.socket.event.chat.PLVCloseRoomEvent;
import com.plv.socket.event.chat.PLVFocusModeEvent;
import com.plv.socket.event.chat.PLVLikesEvent;
import com.plv.socket.event.chat.PLVRewardEvent;
import com.plv.socket.event.chat.PLVSpeakEvent;
import com.plv.socket.event.chat.PLVTAnswerEvent;
import com.plv.socket.event.commodity.PLVProductControlEvent;
import com.plv.socket.event.commodity.PLVProductMenuSwitchEvent;
import com.plv.socket.event.commodity.PLVProductMoveEvent;
import com.plv.socket.event.commodity.PLVProductRemoveEvent;
import com.plv.socket.event.interact.PLVNewsPushStartEvent;
import com.plv.socket.event.login.PLVLoginEvent;
import com.plv.socket.event.login.PLVLogoutEvent;
import com.plv.socket.user.PLVSocketUserBean;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PLVAbsChatroomView implements IPLVChatroomContract.IChatroomView {
    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public int getQuizEmojiSize() {
        return 0;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public int getSpeakEmojiSize() {
        return 0;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onAnswerEvent(@NonNull PLVTAnswerEvent answerEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onBulletinEvent(@NonNull PolyvBulletinVO bulletinVO) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onCloseRoomEvent(@NonNull PLVCloseRoomEvent closeRoomEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onCloseRoomStatusChanged(boolean isClose) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onCustomGiftEvent(@NonNull PLVCustomEvent.UserBean userBean, @NonNull PLVCustomGiftBean customGiftBean) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onFocusModeEvent(@NonNull PLVFocusModeEvent focusModeEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onHistoryDataList(List<PLVBaseViewData<PLVBaseEvent>> chatMessageDataList, int requestSuccessTime, boolean isNoMoreHistory, int viewIndex) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onHistoryRequestFailed(String errorMsg, Throwable t2, int viewIndex) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onImgEvent(@NonNull PLVChatImgEvent chatImgEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onKickUsersList(List<PLVSocketUserBean> dataList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onLikesEvent(@NonNull PLVLikesEvent likesEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onLoadEmotionMessage(@Nullable PLVChatEmotionEvent emotionEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onLocalImageMessage(@Nullable PolyvSendLocalImgEvent localImgEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onLocalQuestionMessage(@Nullable PolyvQuestionMessage questionMessage) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onLocalSpeakMessage(@Nullable PolyvLocalMessage localMessage) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onLoginError(@Nullable PLVLoginEvent loginEvent, String msg, int errorCode) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onLoginEvent(@NonNull PLVLoginEvent loginEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onLogoutEvent(@NonNull PLVLogoutEvent logoutEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onNewsPushCancelMessage() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onNewsPushStartMessage(@NonNull PLVNewsPushStartEvent newsPushStartEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onProductControlEvent(@NonNull PLVProductControlEvent productControlEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onProductMenuSwitchEvent(@NonNull PLVProductMenuSwitchEvent productMenuSwitchEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onProductMoveEvent(@NonNull PLVProductMoveEvent productMoveEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onProductRemoveEvent(@NonNull PLVProductRemoveEvent productRemoveEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onRemoveBulletinEvent() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onRemoveMessageEvent(@Nullable String id, boolean isRemoveAll) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onRewardEvent(@NonNull PLVRewardEvent rewardEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onSendProhibitedWord(@NonNull String prohibitedMessage, @NonNull String hintMsg, @NonNull String status) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onSpeakEvent(@NonNull PLVSpeakEvent speakEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void onSpeakImgDataList(List<PLVBaseViewData> chatMessageDataList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
    public void setPresenter(@NonNull IPLVChatroomContract.IChatroomPresenter presenter) {
    }
}
