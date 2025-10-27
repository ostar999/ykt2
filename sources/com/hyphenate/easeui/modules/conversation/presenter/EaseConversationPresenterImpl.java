package com.hyphenate.easeui.modules.conversation.presenter;

import android.text.TextUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EaseConversationPresenterImpl extends EaseConversationPresenter {
    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadData$0() {
        if (isDestroy()) {
            return;
        }
        this.mView.loadConversationListNoData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadData$1(List list) {
        this.mView.loadConversationListSuccess(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sortData$2() {
        if (isDestroy()) {
            return;
        }
        this.mView.loadConversationListNoData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sortData$3(List list) {
        if (isDestroy()) {
            return;
        }
        this.mView.sortConversationListSuccess(list);
    }

    private void sortByTimestamp(List<EaseConversationInfo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Collections.sort(list, new Comparator<EaseConversationInfo>() { // from class: com.hyphenate.easeui.modules.conversation.presenter.EaseConversationPresenterImpl.1
            @Override // java.util.Comparator
            public int compare(EaseConversationInfo easeConversationInfo, EaseConversationInfo easeConversationInfo2) {
                if (easeConversationInfo2.getTimestamp() > easeConversationInfo.getTimestamp()) {
                    return 1;
                }
                return easeConversationInfo2.getTimestamp() == easeConversationInfo.getTimestamp() ? 0 : -1;
            }
        });
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.EaseConversationPresenter
    public void cancelConversationTop(int i2, EaseConversationInfo easeConversationInfo) {
        if (easeConversationInfo.getInfo() instanceof EMConversation) {
            ((EMConversation) easeConversationInfo.getInfo()).setExtField("");
            easeConversationInfo.setTop(false);
            easeConversationInfo.setTimestamp(((EMConversation) easeConversationInfo.getInfo()).getLastMessage().getMsgTime());
        }
        if (isDestroy()) {
            return;
        }
        this.mView.refreshList();
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.EaseConversationPresenter
    public void deleteConversation(int i2, EaseConversationInfo easeConversationInfo) {
        if (easeConversationInfo.getInfo() instanceof EMConversation) {
            boolean zDeleteConversation = EMClient.getInstance().chatManager().deleteConversation(((EMConversation) easeConversationInfo.getInfo()).conversationId(), !TextUtils.equals(((EMConversation) easeConversationInfo.getInfo()).conversationId(), EaseConstant.DEFAULT_SYSTEM_MESSAGE_ID));
            if (isDestroy()) {
                return;
            }
            if (!zDeleteConversation) {
                this.mView.deleteItemFail(i2, "");
            } else {
                this.mView.deleteItem(i2);
                EMClient.getInstance().translationManager().removeResultsByConversationId(((EMConversation) easeConversationInfo.getInfo()).conversationId());
            }
        }
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.EaseConversationPresenter
    public void loadData() {
        Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager().getAllConversations();
        if (allConversations.isEmpty()) {
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.conversation.presenter.a
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8735c.lambda$loadData$0();
                }
            });
            return;
        }
        final ArrayList arrayList = new ArrayList();
        synchronized (this) {
            for (EMConversation eMConversation : allConversations.values()) {
                if (eMConversation.getAllMessages().size() != 0 && (this.showSystemMessage || !TextUtils.equals(eMConversation.conversationId(), EaseConstant.DEFAULT_SYSTEM_MESSAGE_ID))) {
                    EaseConversationInfo easeConversationInfo = new EaseConversationInfo();
                    easeConversationInfo.setInfo(eMConversation);
                    String extField = eMConversation.getExtField();
                    long msgTime = eMConversation.getLastMessage().getMsgTime();
                    if (TextUtils.isEmpty(extField) || !EaseCommonUtils.isTimestamp(extField)) {
                        easeConversationInfo.setTimestamp(msgTime);
                    } else {
                        easeConversationInfo.setTop(true);
                        long j2 = Long.parseLong(extField);
                        if (j2 > msgTime) {
                            easeConversationInfo.setTimestamp(j2);
                        } else {
                            easeConversationInfo.setTimestamp(msgTime);
                        }
                    }
                    arrayList.add(easeConversationInfo);
                }
            }
        }
        if (isActive()) {
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.conversation.presenter.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8736c.lambda$loadData$1(arrayList);
                }
            });
        }
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.EaseConversationPresenter
    public void makeConversationTop(int i2, EaseConversationInfo easeConversationInfo) {
        if (easeConversationInfo.getInfo() instanceof EMConversation) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            ((EMConversation) easeConversationInfo.getInfo()).setExtField(jCurrentTimeMillis + "");
            easeConversationInfo.setTop(true);
            easeConversationInfo.setTimestamp(jCurrentTimeMillis);
        }
        if (isDestroy()) {
            return;
        }
        this.mView.refreshList();
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.EaseConversationPresenter
    public void makeConversionRead(int i2, EaseConversationInfo easeConversationInfo) {
        if (easeConversationInfo.getInfo() instanceof EMConversation) {
            ((EMConversation) easeConversationInfo.getInfo()).markAllMessagesAsRead();
        }
        if (isDestroy()) {
            return;
        }
        this.mView.refreshList(i2);
    }

    @Override // com.hyphenate.easeui.modules.conversation.presenter.EaseConversationPresenter
    public void sortData(List<EaseConversationInfo> list) {
        if (list == null || list.isEmpty()) {
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.conversation.presenter.c
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8738c.lambda$sortData$2();
                }
            });
            return;
        }
        final ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        synchronized (this) {
            for (EaseConversationInfo easeConversationInfo : list) {
                if (easeConversationInfo.isTop()) {
                    arrayList2.add(easeConversationInfo);
                } else {
                    arrayList.add(easeConversationInfo);
                }
            }
            sortByTimestamp(arrayList2);
            sortByTimestamp(arrayList);
            arrayList.addAll(0, arrayList2);
        }
        runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.conversation.presenter.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f8739c.lambda$sortData$3(arrayList);
            }
        });
    }
}
