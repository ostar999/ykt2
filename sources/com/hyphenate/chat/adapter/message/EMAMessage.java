package com.hyphenate.chat.adapter.message;

import com.hyphenate.chat.adapter.EMABase;
import com.hyphenate.chat.adapter.EMACallback;
import com.hyphenate.chat.adapter.EMAMessageReaction;
import com.hyphenate.chat.adapter.EMAThreadInfo;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public class EMAMessage extends EMABase {
    public static final int EMAChatType_CHATROOM = 2;
    public static final int EMAChatType_GROUP = 1;
    public static final int EMAChatType_SINGLE = 0;
    public static final int EMAMessageStatus_DELIVERING = 1;
    public static final int EMAMessageStatus_FAIL = 3;
    public static final int EMAMessageStatus_NEW = 0;
    public static final int EMAMessageStatus_SUCCESS = 2;

    public enum EMAChatType {
        SINGLE,
        GROUP,
        CHATROOM
    }

    public enum EMADirection {
        SEND,
        RECEIVE
    }

    public enum EMAMessageStatus {
        NEW,
        DELIVERING,
        SUCCESS,
        FAIL
    }

    public EMAMessage() {
        nativeInit();
    }

    public EMAMessage(EMAMessage eMAMessage) {
        nativeInit(eMAMessage);
    }

    public static EMAMessage createReceiveMessage(String str, String str2, EMAMessageBody eMAMessageBody, int i2) {
        return nativeCreateReceiveMessage(str, str2, eMAMessageBody, i2);
    }

    public static EMAMessage createSendMessage(String str, String str2, EMAMessageBody eMAMessageBody, int i2) {
        return nativeCreateSendMessage(str, str2, eMAMessageBody, i2);
    }

    public static native EMAMessage nativeCreateReceiveMessage(String str, String str2, EMAMessageBody eMAMessageBody, int i2);

    public static native EMAMessage nativeCreateSendMessage(String str, String str2, EMAMessageBody eMAMessageBody, int i2);

    public EMAMessageStatus _status() {
        int iNativeStatus = nativeStatus();
        return iNativeStatus != 0 ? iNativeStatus != 1 ? iNativeStatus != 2 ? iNativeStatus != 3 ? EMAMessageStatus.FAIL : EMAMessageStatus.FAIL : EMAMessageStatus.SUCCESS : EMAMessageStatus.DELIVERING : EMAMessageStatus.NEW;
    }

    public void addBody(EMAMessageBody eMAMessageBody) {
        nativeAddBody(eMAMessageBody);
    }

    public List<EMAMessageBody> bodies() {
        return nativeBodies();
    }

    public EMAChatType chatType() {
        int iNativeChatType = nativeChatType();
        EMAChatType eMAChatType = EMAChatType.SINGLE;
        if (iNativeChatType == eMAChatType.ordinal()) {
            return eMAChatType;
        }
        EMAChatType eMAChatType2 = EMAChatType.GROUP;
        return iNativeChatType == eMAChatType2.ordinal() ? eMAChatType2 : EMAChatType.CHATROOM;
    }

    public void clearBodies() {
        nativeClearBodies();
    }

    public String conversationId() {
        return nativeConversationId();
    }

    public EMADirection direction() {
        int iNativeDirection = nativeDirection();
        EMADirection eMADirection = EMADirection.SEND;
        return iNativeDirection == eMADirection.ordinal() ? eMADirection : EMADirection.RECEIVE;
    }

    public Map ext() {
        return nativeExt();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public String from() {
        return nativeFrom();
    }

    public boolean getBooleanAttribute(String str, boolean z2, AtomicBoolean atomicBoolean) {
        return nativeGetBooleanAttribute(str, z2, atomicBoolean);
    }

    public EMAThreadInfo getChatThread() {
        return nativeGetChatThread();
    }

    public boolean getDoubleAttribute(String str, double d3, AtomicReference atomicReference) {
        return nativeGetDoubleAttribute(str, d3, atomicReference);
    }

    public boolean getFloatAttribute(String str, float f2, AtomicReference atomicReference) {
        return nativeGetFloatAttribute(str, f2, atomicReference);
    }

    public boolean getIntAttribute(String str, int i2, AtomicInteger atomicInteger) {
        return nativeGetIntAttribute(str, i2, atomicInteger);
    }

    public boolean getJsonAttribute(String str, String str2, StringBuilder sb) {
        return nativeGetJsonAttribute(str, str2, sb);
    }

    public long getLocalTime() {
        return nativeGetLocalTime();
    }

    public boolean getLongAttribute(String str, long j2, AtomicLong atomicLong) {
        return nativeGetLongAttribute(str, j2, atomicLong);
    }

    public String getRecaller() {
        return nativeGetRecaller();
    }

    public boolean getStringAttribute(String str, String str2, StringBuilder sb) {
        return nativeGetStringAttribute(str, str2, sb);
    }

    public int groupAckCount() {
        return nativeGroupAckCount();
    }

    public boolean isAcked() {
        return nativeIsAcked();
    }

    public boolean isChatThreadMessage() {
        return nativeIsThread();
    }

    public boolean isDeliverAcked() {
        return nativeIsDeliverAcked();
    }

    public boolean isListened() {
        return nativeIsListened();
    }

    public boolean isNeedGroupAck() {
        return nativeIsNeedGroupAck();
    }

    public boolean isOnlineState() {
        return nativeIsOnlineState();
    }

    public boolean isRead() {
        return nativeIsRead();
    }

    public String msgId() {
        return nativeMsgId();
    }

    public native void nativeAddBody(EMAMessageBody eMAMessageBody);

    public native List<EMAMessageBody> nativeBodies();

    public native int nativeChatType();

    public native void nativeClearBodies();

    public native String nativeConversationId();

    public native int nativeDirection();

    public native Map<String, Object> nativeExt();

    public native void nativeFinalize();

    public native String nativeFrom();

    public native boolean nativeGetBooleanAttribute(String str, boolean z2, AtomicBoolean atomicBoolean);

    public native EMAThreadInfo nativeGetChatThread();

    public native boolean nativeGetDoubleAttribute(String str, double d3, AtomicReference atomicReference);

    public native boolean nativeGetFloatAttribute(String str, float f2, AtomicReference atomicReference);

    public native boolean nativeGetIntAttribute(String str, int i2, AtomicInteger atomicInteger);

    public native boolean nativeGetJsonAttribute(String str, String str2, StringBuilder sb);

    public native long nativeGetLocalTime();

    public native boolean nativeGetLongAttribute(String str, long j2, AtomicLong atomicLong);

    public native String nativeGetRecaller();

    public native boolean nativeGetStringAttribute(String str, String str2, StringBuilder sb);

    public native int nativeGroupAckCount();

    public native void nativeInit();

    public native void nativeInit(EMAMessage eMAMessage);

    public native boolean nativeIsAcked();

    public native boolean nativeIsDeliverAcked();

    public native boolean nativeIsListened();

    public native boolean nativeIsNeedGroupAck();

    public native boolean nativeIsOnlineState();

    public native boolean nativeIsRead();

    public native boolean nativeIsThread();

    public native String nativeMsgId();

    public native int nativeProgress();

    public native List<EMAMessageReaction> nativeReactionList();

    public native void nativeSetAttribute(String str, double d3);

    public native void nativeSetAttribute(String str, float f2);

    public native void nativeSetAttribute(String str, int i2);

    public native void nativeSetAttribute(String str, long j2);

    public native void nativeSetAttribute(String str, String str2);

    public native void nativeSetAttribute(String str, boolean z2);

    public native void nativeSetCallback(EMACallback eMACallback);

    public native void nativeSetChatType(int i2);

    public native void nativeSetConversationId(String str);

    public native void nativeSetDirection(int i2);

    public native void nativeSetFrom(String str);

    public native void nativeSetGroupAckCount(int i2);

    public native void nativeSetIsAcked(boolean z2);

    public native void nativeSetIsDeliverAcked(boolean z2);

    public native void nativeSetIsNeedGroupAck(boolean z2);

    public native void nativeSetIsRead(boolean z2);

    public native void nativeSetIsThread(boolean z2);

    public native void nativeSetJsonAttribute(String str, String str2);

    public native void nativeSetListened(boolean z2);

    public native void nativeSetLocalTime(long j2);

    public native void nativeSetMsgId(String str);

    public native void nativeSetPriority(int i2);

    public native void nativeSetProgress(int i2);

    public native void nativeSetStatus(int i2);

    public native void nativeSetTimeStamp(long j2);

    public native void nativeSetTo(String str);

    public native int nativeStatus();

    public native long nativeTimeStamp();

    public native String nativeTo();

    public int progress() {
        return nativeProgress();
    }

    public List<EMAMessageReaction> reactionList() {
        return nativeReactionList();
    }

    public void setAttribute(String str, double d3) {
        nativeSetAttribute(str, d3);
    }

    public void setAttribute(String str, float f2) {
        nativeSetAttribute(str, f2);
    }

    public void setAttribute(String str, int i2) {
        nativeSetAttribute(str, i2);
    }

    public void setAttribute(String str, long j2) {
        nativeSetAttribute(str, j2);
    }

    public void setAttribute(String str, String str2) {
        nativeSetAttribute(str, str2);
    }

    public void setAttribute(String str, boolean z2) {
        nativeSetAttribute(str, z2);
    }

    public void setCallback(EMACallback eMACallback) {
        nativeSetCallback(eMACallback);
    }

    public void setChatType(EMAChatType eMAChatType) {
        nativeSetChatType(eMAChatType.ordinal());
    }

    public void setConversationId(String str) {
        nativeSetConversationId(str);
    }

    public void setDirection(int i2) {
        nativeSetDirection(i2);
    }

    public void setFrom(String str) {
        nativeSetFrom(str);
    }

    public void setGroupAckCount(int i2) {
        nativeSetGroupAckCount(i2);
    }

    public void setIsAcked(boolean z2) {
        nativeSetIsAcked(z2);
    }

    public void setIsChatThreadMessage(boolean z2) {
        nativeSetIsThread(z2);
    }

    public void setIsDeliverAcked(boolean z2) {
        nativeSetIsDeliverAcked(z2);
    }

    public void setIsNeedGroupAck(boolean z2) {
        nativeSetIsNeedGroupAck(z2);
    }

    public void setIsRead(boolean z2) {
        nativeSetIsRead(z2);
    }

    public void setJsonAttribute(String str, String str2) {
        nativeSetJsonAttribute(str, str2);
    }

    public void setListened(boolean z2) {
        nativeSetListened(z2);
    }

    public void setLocalTime(long j2) {
        nativeSetLocalTime(j2);
    }

    public void setMsgId(String str) {
        nativeSetMsgId(str);
    }

    public void setPriority(int i2) {
        nativeSetPriority(i2);
    }

    public void setProgress(int i2) {
        nativeSetProgress(i2);
    }

    public void setStatus(int i2) {
        nativeSetStatus(i2);
    }

    public void setTimeStamp(long j2) {
        nativeSetTimeStamp(j2);
    }

    public void setTo(String str) {
        nativeSetTo(str);
    }

    public long timeStamp() {
        return nativeTimeStamp();
    }

    public String to() {
        return nativeTo();
    }
}
