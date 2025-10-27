package com.easefun.polyv.livecommon.module.modules.chatroom.contract;

import android.util.Pair;
import androidx.annotation.AnyThread;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.annotation.WorkerThread;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVCustomGiftBean;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.data.PLVChatroomData;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.PolyvQuestionMessage;
import com.easefun.polyv.livescenes.chatroom.send.custom.PolyvBaseCustomEvent;
import com.easefun.polyv.livescenes.chatroom.send.custom.PolyvCustomEvent;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.easefun.polyv.livescenes.model.bulletin.PolyvBulletinVO;
import com.plv.livescenes.chatroom.IPLVChatroomManager;
import com.plv.livescenes.chatroom.send.custom.PLVCustomEvent;
import com.plv.livescenes.model.interact.PLVCardPushVO;
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
import io.reactivex.Observable;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPLVChatroomContract {

    public interface IChatroomPresenter {
        void destroy();

        Observable<PLVCardPushVO> getCardPushInfo(String cardId);

        void getChatEmotionImages();

        int getChatHistoryTime();

        @NonNull
        PLVChatroomData getData();

        int[] getSpeakEmojiSizes();

        int getViewIndex(IChatroomView v2);

        void init();

        boolean isCloseRoom();

        void onJoinDiscuss(String groupId);

        void onLeaveDiscuss();

        void registerView(@NonNull IChatroomView v2);

        void requestChatHistory(int viewIndex);

        void requestKickUsers();

        Pair<Boolean, Integer> sendChatEmotionImage(PLVChatEmotionEvent emotionEvent);

        void sendChatImage(PolyvSendLocalImgEvent localImgEvent);

        Pair<Boolean, Integer> sendChatMessage(PolyvLocalMessage textMessage);

        PolyvCustomEvent<PLVCustomGiftBean> sendCustomGiftMessage(PLVCustomGiftBean customGiftBean, String tip);

        <DataBean> void sendCustomMsg(PolyvBaseCustomEvent<DataBean> baseCustomEvent);

        void sendLikeMessage();

        int sendQuestionMessage(PolyvQuestionMessage questionMessage);

        Pair<Boolean, Integer> sendQuoteMessage(PolyvLocalMessage textMessage, String quoteId);

        void setGetChatHistoryCount(int count);

        void setHistoryContainRewardEvent(boolean historyContainRewardEvent);

        void toggleRoom(final boolean isClose, final IPLVChatroomManager.RequestApiListener<String> listener);

        void unregisterView(IChatroomView v2);
    }

    public interface IChatroomView {
        @AnyThread
        int getQuizEmojiSize();

        @AnyThread
        int getSpeakEmojiSize();

        @WorkerThread
        void onAnswerEvent(@NonNull PLVTAnswerEvent answerEvent);

        @WorkerThread
        void onBulletinEvent(@NonNull PolyvBulletinVO bulletinVO);

        @WorkerThread
        void onCloseRoomEvent(@NonNull PLVCloseRoomEvent closeRoomEvent);

        @MainThread
        void onCloseRoomStatusChanged(boolean isClose);

        @WorkerThread
        void onCustomGiftEvent(@NonNull PLVCustomEvent.UserBean userBean, @NonNull PLVCustomGiftBean customGiftBean);

        @WorkerThread
        void onFocusModeEvent(@NonNull PLVFocusModeEvent focusModeEvent);

        @MainThread
        void onHistoryDataList(@Size(min = 0) List<PLVBaseViewData<PLVBaseEvent>> chatMessageDataList, int requestSuccessTime, boolean isNoMoreHistory, int viewIndex);

        @MainThread
        void onHistoryRequestFailed(String errorMsg, Throwable t2, int viewIndex);

        @WorkerThread
        void onImgEvent(@NonNull PLVChatImgEvent chatImgEvent);

        void onKickUsersList(List<PLVSocketUserBean> dataList);

        @WorkerThread
        void onLikesEvent(@NonNull PLVLikesEvent likesEvent);

        void onLoadEmotionMessage(@Nullable PLVChatEmotionEvent emotionEvent);

        void onLocalImageMessage(@Nullable PolyvSendLocalImgEvent localImgEvent);

        void onLocalQuestionMessage(@Nullable PolyvQuestionMessage questionMessage);

        void onLocalSpeakMessage(@Nullable PolyvLocalMessage localMessage);

        @WorkerThread
        void onLoginError(@Nullable PLVLoginEvent loginEvent, String msg, int errorCode);

        @WorkerThread
        void onLoginEvent(@NonNull PLVLoginEvent loginEvent);

        @WorkerThread
        void onLogoutEvent(@NonNull PLVLogoutEvent logoutEvent);

        void onNewsPushCancelMessage();

        void onNewsPushStartMessage(@NonNull PLVNewsPushStartEvent newsPushStartEvent);

        @WorkerThread
        void onProductControlEvent(@NonNull PLVProductControlEvent productControlEvent);

        @WorkerThread
        void onProductMenuSwitchEvent(@NonNull PLVProductMenuSwitchEvent productMenuSwitchEvent);

        @WorkerThread
        void onProductMoveEvent(@NonNull PLVProductMoveEvent productMoveEvent);

        @WorkerThread
        void onProductRemoveEvent(@NonNull PLVProductRemoveEvent productRemoveEvent);

        @WorkerThread
        void onRemoveBulletinEvent();

        @WorkerThread
        void onRemoveMessageEvent(@Nullable String id, boolean isRemoveAll);

        @WorkerThread
        void onRewardEvent(@NonNull PLVRewardEvent rewardEvent);

        @MainThread
        void onSendProhibitedWord(@NonNull String prohibitedMessage, @NonNull String hintMsg, @NonNull String status);

        @WorkerThread
        void onSpeakEvent(@NonNull PLVSpeakEvent speakEvent);

        @WorkerThread
        void onSpeakImgDataList(@Size(min = 1) List<PLVBaseViewData> chatMessageDataList);

        void setPresenter(@NonNull IChatroomPresenter presenter);
    }
}
