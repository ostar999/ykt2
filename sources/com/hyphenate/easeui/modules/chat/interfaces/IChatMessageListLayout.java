package com.hyphenate.easeui.modules.chat.interfaces;

import com.hyphenate.easeui.adapter.EaseMessageAdapter;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout;
import com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenter;
import com.hyphenate.easeui.modules.interfaces.IRecyclerView;

/* loaded from: classes4.dex */
public interface IChatMessageListLayout extends IRecyclerView {
    EaseMessageAdapter getMessageAdapter();

    void setMessageListItemClickListener(MessageListItemClickListener messageListItemClickListener);

    void setOnChatErrorListener(EaseChatMessageListLayout.OnChatErrorListener onChatErrorListener);

    void setOnMessageTouchListener(EaseChatMessageListLayout.OnMessageTouchListener onMessageTouchListener);

    void setPresenter(EaseChatMessagePresenter easeChatMessagePresenter);
}
