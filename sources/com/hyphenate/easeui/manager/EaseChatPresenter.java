package com.hyphenate.easeui.manager;

import android.content.Context;
import android.util.Log;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupReadAck;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.model.EaseNotifier;
import d1.e;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseChatPresenter implements EMMessageListener {
    private static final String TAG = "EaseChatPresenter";
    public Context context;

    public EaseChatPresenter() {
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    public void attachApp(Context context) {
        this.context = context;
    }

    public EaseNotifier getNotifier() {
        return EaseIM.getInstance().getNotifier();
    }

    @Override // com.hyphenate.EMMessageListener
    public void onCmdMessageReceived(List<EMMessage> list) {
    }

    @Override // com.hyphenate.EMMessageListener
    public void onGroupMessageRead(List<EMGroupReadAck> list) {
        Iterator<EMGroupReadAck> it = list.iterator();
        while (it.hasNext()) {
            EaseDingMessageHelper.get().handleGroupReadAck(it.next());
        }
    }

    @Override // com.hyphenate.EMMessageListener
    public void onMessageChanged(EMMessage eMMessage, Object obj) {
    }

    @Override // com.hyphenate.EMMessageListener
    public void onMessageDelivered(List<EMMessage> list) {
    }

    @Override // com.hyphenate.EMMessageListener
    public void onMessageRead(List<EMMessage> list) {
    }

    @Override // com.hyphenate.EMMessageListener
    public void onMessageRecalled(List<EMMessage> list) {
    }

    @Override // com.hyphenate.EMMessageListener
    public void onMessageReceived(List<EMMessage> list) {
        Log.e(TAG, "EaseChatPresenter onMessageReceived messages.size = " + list.size());
        EaseAtMessageHelper.get().parseMessages(list);
    }

    @Override // com.hyphenate.EMMessageListener
    public /* synthetic */ void onReactionChanged(List list) {
        e.g(this, list);
    }

    @Override // com.hyphenate.EMMessageListener
    public /* synthetic */ void onReadAckForGroupMessageUpdated() {
        e.h(this);
    }
}
