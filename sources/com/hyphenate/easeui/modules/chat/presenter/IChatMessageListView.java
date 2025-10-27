package com.hyphenate.easeui.modules.chat.presenter;

import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.modules.ILoadDataView;
import java.util.List;

/* loaded from: classes4.dex */
public interface IChatMessageListView extends ILoadDataView {
    EMConversation getCurrentConversation();

    void joinChatRoomFail(int i2, String str);

    void joinChatRoomSuccess(EMChatRoom eMChatRoom);

    void loadLocalMsgSuccess(List<EMMessage> list);

    void loadMoreLocalHistoryMsgSuccess(List<EMMessage> list, EMConversation.EMSearchDirection eMSearchDirection);

    void loadMoreLocalMsgSuccess(List<EMMessage> list);

    void loadMoreServerMsgSuccess(List<EMMessage> list);

    void loadMsgFail(int i2, String str);

    void loadNoLocalMsg();

    void loadNoMoreLocalHistoryMsg();

    void loadNoMoreLocalMsg();

    void loadServerMsgSuccess(List<EMMessage> list);

    void refreshCurrentConSuccess(List<EMMessage> list, boolean z2);
}
