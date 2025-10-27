package com.hyphenate.chat.adapter;

import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.adapter.EMAConversation;
import com.hyphenate.chat.adapter.message.EMAMessage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public class EMAChatManager extends EMABase {
    private Set<EMAChatManagerListener> listeners = new HashSet();

    public EMAChatManager() {
    }

    public EMAChatManager(EMAChatManager eMAChatManager) {
        nativeInit(eMAChatManager);
    }

    public void addListener(EMAChatManagerListener eMAChatManagerListener) {
        this.listeners.add(eMAChatManagerListener);
        nativeAddListener(eMAChatManagerListener);
    }

    public void clearListeners() {
        this.listeners.clear();
        nativeClearListeners();
    }

    public EMAConversation conversationWithType(String str, EMAConversation.EMAConversationType eMAConversationType, boolean z2, boolean z3) {
        return nativeConversationWithType(str, eMAConversationType.ordinal(), z2, z3);
    }

    public EMAError deleteConversationFromServer(String str, EMAConversation.EMAConversationType eMAConversationType, boolean z2) {
        return nativeDeleteConversationFromServer(str, eMAConversationType.ordinal(), z2);
    }

    public void downloadMessageAttachments(EMAMessage eMAMessage) {
        nativeDownloadMessageAttachments(eMAMessage);
    }

    public void downloadMessageThumbnail(EMAMessage eMAMessage) {
        nativeDownloadMessageThumbnail(eMAMessage);
    }

    public List<EMAConversation> fetchConversationsFromServer(EMAError eMAError) {
        return nativeFetchConversationsFromServer(eMAError);
    }

    public EMCursorResult<EMAGroupReadAck> fetchGroupReadAcks(String str, String str2, EMAError eMAError, int i2, String str3) {
        return nativeFetchGroupReadAcks(str, str2, eMAError, i2, str3);
    }

    public EMCursorResult<EMAMessage> fetchHistoryMessages(String str, int i2, int i3, String str2, EMAConversation.EMASearchDirection eMASearchDirection, EMAError eMAError) {
        return nativeFetchHistoryMessages(str, i2, i3, str2, eMASearchDirection.ordinal(), eMAError);
    }

    public List<List<String>> fetchSupportLanguages(EMAError eMAError) {
        return nativeFetchSupportLanguages(eMAError);
    }

    public List<EMAConversation> getConversations() {
        return nativeGetConversations();
    }

    public EMAEncryptProviderInterface getEncryptProvider(boolean z2) {
        return nativeGetEncryptProvider(z2);
    }

    public EMAMessage getMessage(String str) {
        return nativeGetMessage(str);
    }

    public List<EMAConversation> loadAllConversationsFromDB() {
        return nativeLoadAllConversationsFromDB();
    }

    public native void nativeAddListener(EMAChatManagerListener eMAChatManagerListener);

    public native void nativeClearListeners();

    public native EMAConversation nativeConversationWithType(String str, int i2, boolean z2, boolean z3);

    public native EMAError nativeDeleteConversationFromServer(String str, int i2, boolean z2);

    public native void nativeDownloadMessageAttachments(EMAMessage eMAMessage);

    public native void nativeDownloadMessageThumbnail(EMAMessage eMAMessage);

    public native List<EMAConversation> nativeFetchConversationsFromServer(EMAError eMAError);

    public native EMCursorResult<EMAGroupReadAck> nativeFetchGroupReadAcks(String str, String str2, EMAError eMAError, int i2, String str3);

    public native EMCursorResult<EMAMessage> nativeFetchHistoryMessages(String str, int i2, int i3, String str2, int i4, EMAError eMAError);

    public native List<List<String>> nativeFetchSupportLanguages(EMAError eMAError);

    public native List<EMAConversation> nativeGetConversations();

    public native EMAEncryptProviderInterface nativeGetEncryptProvider(boolean z2);

    public native EMAMessage nativeGetMessage(String str);

    public native void nativeInit(EMAChatManager eMAChatManager);

    public native List<EMAConversation> nativeLoadAllConversationsFromDB();

    public native void nativeRecallMessage(EMAMessage eMAMessage, EMAError eMAError);

    public native void nativeRemoveConversation(String str, boolean z2, boolean z3);

    public native void nativeRemoveListener(EMAChatManagerListener eMAChatManagerListener);

    public native boolean nativeRemoveMessagesBeforeTimestamp(long j2);

    public native void nativeReportMessage(String str, String str2, String str3, EMAError eMAError);

    public native void nativeResendMessage(EMAMessage eMAMessage);

    public native List<EMAMessage> nativeSearchMessages(int i2, long j2, int i3, String str, int i4);

    public native List<EMAMessage> nativeSearchMessages(String str, long j2, int i2, String str2, int i3);

    public native void nativeSendMessage(EMAMessage eMAMessage);

    public native void nativeSendReadAckForConversation(String str, EMAError eMAError);

    public native void nativeSendReadAckForGroupMessage(EMAMessage eMAMessage, String str);

    public native void nativeSendReadAckForMessage(EMAMessage eMAMessage);

    public native void nativeSetEncryptProvider(EMAEncryptProviderInterface eMAEncryptProviderInterface);

    public native EMAMessage nativeTranslateMessage(EMAMessage eMAMessage, List<String> list, EMAError eMAError);

    public native boolean nativeUpdateParticipant(String str, String str2);

    public native void nativeUploadLog();

    public void recallMessage(EMAMessage eMAMessage, EMAError eMAError) {
        nativeRecallMessage(eMAMessage, eMAError);
    }

    public void removeConversation(String str, boolean z2, boolean z3) {
        nativeRemoveConversation(str, z2, z3);
    }

    public void removeListener(EMAChatManagerListener eMAChatManagerListener) {
        this.listeners.remove(eMAChatManagerListener);
        nativeRemoveListener(eMAChatManagerListener);
    }

    public boolean removeMessagesBeforeTimestamp(long j2) {
        return nativeRemoveMessagesBeforeTimestamp(j2);
    }

    public void reportMessage(String str, String str2, String str3, EMAError eMAError) {
        nativeReportMessage(str, str2, str3, eMAError);
    }

    public void resendMessage(EMAMessage eMAMessage) {
        nativeResendMessage(eMAMessage);
    }

    public List<EMAMessage> searchMessages(int i2, long j2, int i3, String str, EMAConversation.EMASearchDirection eMASearchDirection) {
        return nativeSearchMessages(i2, j2, i3, str, eMASearchDirection.ordinal());
    }

    public List<EMAMessage> searchMessages(String str, long j2, int i2, String str2, EMAConversation.EMASearchDirection eMASearchDirection) {
        return nativeSearchMessages(str, j2, i2, str2, eMASearchDirection.ordinal());
    }

    public void sendMessage(EMAMessage eMAMessage) {
        nativeSendMessage(eMAMessage);
    }

    public void sendReadAckForConversation(String str, EMAError eMAError) {
        nativeSendReadAckForConversation(str, eMAError);
    }

    public void sendReadAckForGroupMessage(EMAMessage eMAMessage, String str) {
        nativeSendReadAckForGroupMessage(eMAMessage, str);
    }

    public void sendReadAckForMessage(EMAMessage eMAMessage) {
        nativeSendReadAckForMessage(eMAMessage);
    }

    public void setEncryptProvider(EMAEncryptProviderInterface eMAEncryptProviderInterface) {
        nativeSetEncryptProvider(eMAEncryptProviderInterface);
    }

    public EMAMessage translateMessage(EMAMessage eMAMessage, List<String> list, EMAError eMAError) {
        return nativeTranslateMessage(eMAMessage, list, eMAError);
    }

    public boolean updateParticipant(String str, String str2) {
        return nativeUpdateParticipant(str, str2);
    }

    public void uploadLog() {
        nativeUploadLog();
    }
}
