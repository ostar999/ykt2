package com.hyphenate.easeui.modules.conversation.presenter;

import com.hyphenate.easeui.modules.EaseBasePresenter;
import com.hyphenate.easeui.modules.ILoadDataView;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class EaseConversationPresenter extends EaseBasePresenter {
    public IEaseConversationListView mView;
    public boolean showSystemMessage;

    @Override // com.hyphenate.easeui.modules.EaseBasePresenter
    public void attachView(ILoadDataView iLoadDataView) {
        this.mView = (IEaseConversationListView) iLoadDataView;
    }

    public abstract void cancelConversationTop(int i2, EaseConversationInfo easeConversationInfo);

    public abstract void deleteConversation(int i2, EaseConversationInfo easeConversationInfo);

    @Override // com.hyphenate.easeui.modules.EaseBasePresenter
    public void detachView() {
        this.mView = null;
    }

    public abstract void loadData();

    public abstract void makeConversationTop(int i2, EaseConversationInfo easeConversationInfo);

    public abstract void makeConversionRead(int i2, EaseConversationInfo easeConversationInfo);

    @Override // com.hyphenate.easeui.modules.EaseBasePresenter
    public void onDestroy() {
        super.onDestroy();
        detachView();
    }

    public void setShowSystemMessage(boolean z2) {
        this.showSystemMessage = z2;
    }

    public abstract void sortData(List<EaseConversationInfo> list);
}
