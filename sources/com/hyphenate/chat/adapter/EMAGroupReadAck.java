package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public class EMAGroupReadAck extends EMABase {
    public EMAGroupReadAck() {
        nativeInit();
    }

    public EMAGroupReadAck(EMAGroupReadAck eMAGroupReadAck) {
        nativeInit(eMAGroupReadAck);
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public String getAckId() {
        return nativeGetAckId();
    }

    public String getContent() {
        return nativeGetContent();
    }

    public int getCount() {
        return nativeGetCount();
    }

    public String getFrom() {
        return nativeGetFrom();
    }

    public String getMsgId() {
        return nativeGetMsgId();
    }

    public long getTimestamp() {
        return nativeGetTimestamp();
    }

    public native void nativeFinalize();

    public native String nativeGetAckId();

    public native String nativeGetContent();

    public native int nativeGetCount();

    public native String nativeGetFrom();

    public native String nativeGetMsgId();

    public native long nativeGetTimestamp();

    public native void nativeInit();

    public native void nativeInit(EMAGroupReadAck eMAGroupReadAck);
}
