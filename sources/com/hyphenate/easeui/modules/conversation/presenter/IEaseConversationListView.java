package com.hyphenate.easeui.modules.conversation.presenter;

import com.hyphenate.easeui.modules.ILoadDataView;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface IEaseConversationListView extends ILoadDataView {
    void deleteItem(int i2);

    void deleteItemFail(int i2, String str);

    void loadConversationListFail(String str);

    void loadConversationListNoData();

    void loadConversationListSuccess(List<EaseConversationInfo> list);

    void refreshList();

    void refreshList(int i2);

    void sortConversationListSuccess(List<EaseConversationInfo> list);
}
