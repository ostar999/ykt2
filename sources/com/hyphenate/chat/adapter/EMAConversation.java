package com.hyphenate.chat.adapter;

import com.hyphenate.chat.adapter.message.EMAMessage;
import java.util.List;

/* loaded from: classes4.dex */
public class EMAConversation extends EMABase {
    public static final int EMAConversationType_CHAT = 0;
    public static final int EMAConversationType_CHATROOM = 2;
    public static final int EMAConversationType_DISCUSSIONGROUP = 3;
    public static final int EMAConversationType_GROUPCHAT = 1;
    public static final int EMAConversationType_HELPDESK = 4;

    public enum EMAConversationType {
        CHAT,
        GROUPCHAT,
        CHATROOM,
        DISCUSSIONGROUP,
        HELPDESK
    }

    public enum EMASearchDirection {
        UP,
        DOWN
    }

    public EMAConversation() {
        nativeInit();
    }

    public EMAConversation(EMAConversation eMAConversation) {
        nativeInit(eMAConversation);
    }

    public EMAConversationType _getType() {
        int iNativeConversationType = nativeConversationType();
        return iNativeConversationType == 0 ? EMAConversationType.CHAT : iNativeConversationType == 1 ? EMAConversationType.GROUPCHAT : iNativeConversationType == 2 ? EMAConversationType.CHATROOM : iNativeConversationType == 3 ? EMAConversationType.DISCUSSIONGROUP : iNativeConversationType == 4 ? EMAConversationType.HELPDESK : EMAConversationType.CHAT;
    }

    public boolean _removeMessage(String str) {
        return nativeRemoveMessage(str);
    }

    public boolean _setExtField(String str) {
        return nativeSetExtField(str);
    }

    public boolean appendMessage(EMAMessage eMAMessage) {
        return nativeInsertMessage(eMAMessage);
    }

    public boolean clearAllMessages() {
        return nativeClearAllMessages();
    }

    public String conversationId() {
        return nativeConversationId();
    }

    public String extField() {
        return nativeExtField();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public boolean insertMessage(EMAMessage eMAMessage) {
        return nativeInsertMessage(eMAMessage);
    }

    public boolean isChatThread() {
        return nativeIsThread();
    }

    public EMAMessage latestMessage() {
        return nativeLatestMessage();
    }

    public EMAMessage latestMessageFromOthers() {
        return nativeLatestMessageFromOthers();
    }

    public EMAMessage loadMessage(String str) {
        return nativeLoadMessage(str);
    }

    public List<EMAMessage> loadMoreMessages(String str, int i2, EMASearchDirection eMASearchDirection) {
        return nativeLoadMoreMessages(str, i2, eMASearchDirection.ordinal());
    }

    public boolean markAllMessagesAsRead(boolean z2) {
        return nativeMarkAllMessagesAsRead(z2);
    }

    public boolean markMessageAsRead(String str, boolean z2) {
        return nativeMarkMessageAsRead(str, z2);
    }

    public int messagesCount() {
        return nativeMessagesCount();
    }

    public native boolean nativeAppendMessage(EMAMessage eMAMessage);

    public native boolean nativeClearAllMessages();

    public native void nativeClearCachedMessages();

    public native String nativeConversationId();

    public native int nativeConversationType();

    public native String nativeExtField();

    public native void nativeFinalize();

    public native void nativeInit();

    public native void nativeInit(EMAConversation eMAConversation);

    public native boolean nativeInsertMessage(EMAMessage eMAMessage);

    public native boolean nativeIsThread();

    public native EMAMessage nativeLatestMessage();

    public native EMAMessage nativeLatestMessageFromOthers();

    public native EMAMessage nativeLoadMessage(String str);

    public native List<EMAMessage> nativeLoadMoreMessages(String str, int i2, int i3);

    public native boolean nativeMarkAllMessagesAsRead(boolean z2);

    public native boolean nativeMarkMessageAsRead(String str, boolean z2);

    public native int nativeMessagesCount();

    public native boolean nativeRemoveMessage(EMAMessage eMAMessage);

    public native boolean nativeRemoveMessage(String str);

    public native List<EMAMessage> nativeSearchCustomMessages(String str, long j2, int i2, String str2, int i3);

    public native List<EMAMessage> nativeSearchMessages(int i2, long j2, int i3, String str, int i4);

    public native List<EMAMessage> nativeSearchMessages(long j2, int i2, int i3);

    public native List<EMAMessage> nativeSearchMessages(long j2, long j3, int i2);

    public native List<EMAMessage> nativeSearchMessages(String str, long j2, int i2, String str2, int i3);

    public native boolean nativeSetExtField(String str);

    public native void nativeSetIsThread(boolean z2);

    public native int nativeUnreadMessagesCount();

    public native boolean nativeUpdateMessage(EMAMessage eMAMessage);

    public boolean removeMessage(EMAMessage eMAMessage) {
        return nativeRemoveMessage(eMAMessage);
    }

    public List<EMAMessage> searchCustomMessages(String str, long j2, int i2, String str2, EMASearchDirection eMASearchDirection) {
        return nativeSearchCustomMessages(str, j2, i2, str2, eMASearchDirection.ordinal());
    }

    public List<EMAMessage> searchMessages(int i2, long j2, int i3, String str, EMASearchDirection eMASearchDirection) {
        return nativeSearchMessages(i2, j2, i3, str, eMASearchDirection.ordinal());
    }

    public List<EMAMessage> searchMessages(long j2, int i2, EMASearchDirection eMASearchDirection) {
        return nativeSearchMessages(j2, i2, eMASearchDirection.ordinal());
    }

    public List<EMAMessage> searchMessages(long j2, long j3, int i2) {
        return nativeSearchMessages(j2, j3, i2);
    }

    public List<EMAMessage> searchMessages(String str, long j2, int i2, String str2, EMASearchDirection eMASearchDirection) {
        return nativeSearchMessages(str, j2, i2, str2, eMASearchDirection.ordinal());
    }

    public void setIsThread(boolean z2) {
        nativeSetIsThread(z2);
    }

    public int unreadMessagesCount() {
        return nativeUnreadMessagesCount();
    }

    public boolean updateMessage(EMAMessage eMAMessage) {
        return nativeUpdateMessage(eMAMessage);
    }
}
