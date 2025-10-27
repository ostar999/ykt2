package com.hyphenate.chat.adapter;

import android.text.TextUtils;
import com.hyphenate.chat.EMChatThreadEvent;
import com.hyphenate.chat.adapter.message.EMAMessage;
import com.hyphenate.util.EMLog;

/* loaded from: classes4.dex */
public class EMAThreadInfo extends EMABase {

    public enum LeaveReason {
        LEFT,
        BE_KICKED,
        DESTROYED
    }

    public EMAThreadInfo() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public long getCreateAt() {
        return nativeGetCreateAt();
    }

    public String getFrom() {
        return nativeGetFrom();
    }

    public EMAMessage getLastMessage() {
        return nativeGetLastMessage();
    }

    public int getMemberCount() {
        return nativeGetMemberCount();
    }

    public int getMessageCount() {
        return nativeGetMessageCount();
    }

    public String getMessageId() {
        return nativeGetMessageId();
    }

    public String getOwner() {
        return nativeGetOwner();
    }

    public String getParentId() {
        return nativeGetParentId();
    }

    public String getThreadId() {
        return nativeGetThreadId();
    }

    public String getThreadName() {
        return nativeGetThreadName();
    }

    public String getTo() {
        return nativeGetTo();
    }

    public EMChatThreadEvent.TYPE getType() {
        String strNativeGetThreadType = nativeGetThreadType();
        if (TextUtils.isEmpty(strNativeGetThreadType)) {
            return null;
        }
        try {
            return EMChatThreadEvent.TYPE.valueOf(strNativeGetThreadType.toUpperCase());
        } catch (IllegalArgumentException e2) {
            EMLog.e("EMThreadEvent", e2.getMessage());
            return EMChatThreadEvent.TYPE.UNKNOWN;
        }
    }

    public long getUpdateAt() {
        return nativeGetUpdateAt();
    }

    public native void nativeFinalize();

    public native long nativeGetCreateAt();

    public native String nativeGetFrom();

    public native EMAMessage nativeGetLastMessage();

    public native int nativeGetMemberCount();

    public native int nativeGetMessageCount();

    public native String nativeGetMessageId();

    public native String nativeGetOwner();

    public native String nativeGetParentId();

    public native String nativeGetThreadId();

    public native String nativeGetThreadName();

    public native String nativeGetThreadType();

    public native String nativeGetTo();

    public native long nativeGetUpdateAt();

    public native void nativeInit();
}
