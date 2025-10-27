package com.hyphenate.easeui.modules.chat.interfaces;

import android.net.Uri;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.modules.chat.EaseChatInputMenu;
import com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout;

/* loaded from: classes4.dex */
public interface IChatLayout {
    void addMessageAttributes(EMMessage eMMessage);

    void deleteMessage(EMMessage eMMessage);

    EaseChatInputMenu getChatInputMenu();

    EaseChatMessageListLayout getChatMessageListLayout();

    String getInputContent();

    void hideTranslate(EMMessage eMMessage);

    void recallMessage(EMMessage eMMessage);

    void resendMessage(EMMessage eMMessage);

    void sendAtMessage(String str);

    void sendBigExpressionMessage(String str, String str2);

    void sendFileMessage(Uri uri);

    void sendImageMessage(Uri uri);

    void sendImageMessage(Uri uri, boolean z2);

    void sendLocationMessage(double d3, double d4, String str, String str2);

    void sendMessage(EMMessage eMMessage);

    void sendTextMessage(String str);

    void sendTextMessage(String str, boolean z2);

    void sendVideoMessage(Uri uri, int i2);

    void sendVoiceMessage(Uri uri, int i2);

    void sendVoiceMessage(String str, int i2);

    void setOnAddMsgAttrsBeforeSendEvent(OnAddMsgAttrsBeforeSendEvent onAddMsgAttrsBeforeSendEvent);

    void setOnChatLayoutListener(OnChatLayoutListener onChatLayoutListener);

    void setOnChatRecordTouchListener(OnChatRecordTouchListener onChatRecordTouchListener);

    void setOnRecallMessageResultListener(OnRecallMessageResultListener onRecallMessageResultListener);

    void setOnTranslateListener(OnTranslateMessageListener onTranslateMessageListener);

    void translateMessage(EMMessage eMMessage, boolean z2);

    void turnOnTypingMonitor(boolean z2);
}
