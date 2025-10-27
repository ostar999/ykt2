package com.hyphenate.easeui.modules.chat.presenter;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.modules.EaseBasePresenter;
import com.hyphenate.easeui.modules.ILoadDataView;

/* loaded from: classes4.dex */
public abstract class EaseChatMessagePresenter extends EaseBasePresenter {
    public EMConversation conversation;
    public IChatMessageListView mView;

    @Override // com.hyphenate.easeui.modules.EaseBasePresenter
    public void attachView(ILoadDataView iLoadDataView) {
        this.mView = (IChatMessageListView) iLoadDataView;
    }

    @Override // com.hyphenate.easeui.modules.EaseBasePresenter
    public void detachView() {
        this.mView = null;
    }

    public abstract void joinChatRoom(String str);

    public abstract void loadLocalMessages(int i2);

    public abstract void loadMoreLocalHistoryMessages(String str, int i2, EMConversation.EMSearchDirection eMSearchDirection);

    public abstract void loadMoreLocalMessages(String str, int i2);

    public abstract void loadMoreServerMessages(String str, int i2);

    public abstract void loadServerMessages(int i2);

    @Override // com.hyphenate.easeui.modules.EaseBasePresenter
    public void onDestroy() {
        super.onDestroy();
        detachView();
    }

    public abstract void refreshCurrentConversation();

    public abstract void refreshToLatest();

    public void setupWithConversation(EMConversation eMConversation) {
        this.conversation = eMConversation;
    }
}
