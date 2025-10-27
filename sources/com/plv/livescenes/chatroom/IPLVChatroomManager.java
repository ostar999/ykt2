package com.plv.livescenes.chatroom;

import androidx.annotation.MainThread;
import com.plv.livescenes.chatroom.send.custom.PLVBaseCustomEvent;
import com.plv.livescenes.chatroom.send.custom.PLVSendCustomMsgListener;
import com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener;
import com.plv.livescenes.chatroom.send.img.PLVSendLocalImgEvent;
import com.plv.livescenes.model.PLVChatFunctionSwitchVO;
import com.plv.socket.event.chat.PLVChatEmotionEvent;
import io.reactivex.functions.Consumer;
import io.socket.client.Ack;

/* loaded from: classes4.dex */
public interface IPLVChatroomManager {

    public interface RequestApiListener<D> {
        void onFailed(Throwable th);

        void onSuccess(D d3);
    }

    public interface RoomStatusListener {
        @MainThread
        void onStatus(boolean z2);
    }

    void addOnRoomStatusListener(RoomStatusListener roomStatusListener);

    void addSendChatImageListener(PLVSendChatImageListener pLVSendChatImageListener);

    void destroy();

    PLVChatFunctionSwitchVO getChatFunctionSwitchVO();

    int getOnlineCount();

    void init();

    boolean isCloseRoom();

    int kick(String str);

    void removeOnRoomStatusListener(RoomStatusListener roomStatusListener);

    void removeSendChatImageListener(PLVSendChatImageListener pLVSendChatImageListener);

    int removeShield(String str);

    void requestFunctionSwitch(Consumer<PLVChatFunctionSwitchVO> consumer);

    void sendChatImage(PLVSendLocalImgEvent pLVSendLocalImgEvent, String str);

    int sendChatMessage(PLVLocalMessage pLVLocalMessage, String str);

    int sendChatMessage(PLVLocalMessage pLVLocalMessage, String str, boolean z2, Ack ack);

    <DataBean> void sendCustomMsg(PLVBaseCustomEvent<DataBean> pLVBaseCustomEvent);

    int sendEmotionImage(PLVChatEmotionEvent pLVChatEmotionEvent, Ack ack);

    void sendInteractiveSocketMessage(String str, Object obj, int i2, String str2);

    void sendLikes(int i2, String str);

    void sendLikes(String str);

    int sendLookAtMeMessage();

    int sendQuestionMessage(PLVQuestionMessage pLVQuestionMessage);

    int sendQuoteMessage(PLVLocalMessage pLVLocalMessage, String str, String str2);

    int sendQuoteMessage(PLVLocalMessage pLVLocalMessage, String str, boolean z2, Ack ack, String str2);

    void setOnlineCount(int i2);

    void setOnlineCountListener(IPLVOnlineCountListener iPLVOnlineCountListener);

    void setProhibitedWordListener(IPLVProhibitedWordListener iPLVProhibitedWordListener);

    void setSendCustomMsgListener(PLVSendCustomMsgListener pLVSendCustomMsgListener);

    void setSocketCallbackListener(PLVSocketCallbackListener pLVSocketCallbackListener);

    int shield(String str);

    void toggleRoom(boolean z2, RequestApiListener<String> requestApiListener);

    int unKick(String str);
}
