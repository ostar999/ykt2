package com.hyphenate.easeui.modules.conversation.interfaces;

import com.hyphenate.easeui.modules.conversation.adapter.EaseConversationListAdapter;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.modules.conversation.presenter.EaseConversationPresenter;
import com.hyphenate.easeui.modules.interfaces.IRecyclerView;

/* loaded from: classes4.dex */
public interface IConversationListLayout extends IRecyclerView {
    void cancelConversationTop(int i2, EaseConversationInfo easeConversationInfo);

    void deleteConversation(int i2, EaseConversationInfo easeConversationInfo);

    EaseConversationInfo getItem(int i2);

    EaseConversationListAdapter getListAdapter();

    void makeConversationTop(int i2, EaseConversationInfo easeConversationInfo);

    void makeConversionRead(int i2, EaseConversationInfo easeConversationInfo);

    void setOnConversationChangeListener(OnConversationChangeListener onConversationChangeListener);

    void setOnConversationLoadListener(OnConversationLoadListener onConversationLoadListener);

    void setPresenter(EaseConversationPresenter easeConversationPresenter);

    void showItemDefaultMenu(boolean z2);
}
