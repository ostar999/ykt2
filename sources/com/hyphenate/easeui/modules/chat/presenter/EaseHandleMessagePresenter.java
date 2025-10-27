package com.hyphenate.easeui.modules.chat.presenter;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.modules.EaseBasePresenter;
import com.hyphenate.easeui.modules.ILoadDataView;
import com.hyphenate.easeui.utils.EaseCommonUtils;

/* loaded from: classes4.dex */
public abstract class EaseHandleMessagePresenter extends EaseBasePresenter {
    protected int chatType;
    protected EMConversation conversation;
    protected IHandleMessageView mView;
    protected String toChatUsername;

    public abstract void addMessageAttributes(EMMessage eMMessage);

    @Override // com.hyphenate.easeui.modules.EaseBasePresenter
    public void attachView(ILoadDataView iLoadDataView) {
        this.mView = (IHandleMessageView) iLoadDataView;
    }

    public abstract void deleteMessage(EMMessage eMMessage);

    @Override // com.hyphenate.easeui.modules.EaseBasePresenter
    public void detachView() {
        this.mView = null;
    }

    public abstract void hideTranslate(EMMessage eMMessage);

    public boolean isGroupChat() {
        return this.chatType == 2;
    }

    @Override // com.hyphenate.easeui.modules.EaseBasePresenter
    public void onDestroy() {
        super.onDestroy();
        detachView();
    }

    public abstract void recallMessage(EMMessage eMMessage);

    public abstract void resendMessage(EMMessage eMMessage);

    public abstract void sendAtMessage(String str);

    public abstract void sendBigExpressionMessage(String str, String str2);

    public abstract void sendCmdMessage(String str);

    public abstract void sendFileMessage(Uri uri);

    public abstract void sendImageMessage(Uri uri);

    public abstract void sendImageMessage(Uri uri, boolean z2);

    public abstract void sendLocationMessage(double d3, double d4, String str, String str2);

    public abstract void sendMessage(EMMessage eMMessage);

    public abstract void sendTextMessage(String str);

    public abstract void sendTextMessage(String str, boolean z2);

    public abstract void sendVideoMessage(Uri uri, int i2);

    public abstract void sendVoiceMessage(Uri uri, int i2);

    public void setupWithToUser(int i2, @NonNull String str) {
        this.chatType = i2;
        this.toChatUsername = str;
        this.conversation = EMClient.getInstance().chatManager().getConversation(str, EaseCommonUtils.getConversationType(i2), true);
    }

    public abstract void translateMessage(EMMessage eMMessage, String str, boolean z2);
}
