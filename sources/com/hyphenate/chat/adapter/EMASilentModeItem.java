package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public class EMASilentModeItem extends EMABase {
    public EMASilentModeItem() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public String getConversationId() {
        return nativeGetConversationId();
    }

    public int getConversationType() {
        return nativeGetConversationType();
    }

    public long getExpireTimestamp() {
        return nativeGetExpireTimestamp();
    }

    public int getRemindType() {
        return nativeGetRemindType();
    }

    public EMASilentModeTime getSilentModeEndTime() {
        return nativeGetSilentModeEndTime();
    }

    public EMASilentModeTime getSilentModeStartTime() {
        return nativeGetSilentModeStartTime();
    }

    public native void nativeFinalize();

    public native String nativeGetConversationId();

    public native int nativeGetConversationType();

    public native long nativeGetExpireTimestamp();

    public native int nativeGetRemindType();

    public native EMASilentModeTime nativeGetSilentModeEndTime();

    public native EMASilentModeTime nativeGetSilentModeStartTime();

    public native void nativeInit();
}
