package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public class EMAMucShareFile extends EMABase {
    public EMAMucShareFile() {
        nativeInit();
    }

    public EMAMucShareFile(EMAMucShareFile eMAMucShareFile) {
        nativeInit(eMAMucShareFile);
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public String getFileId() {
        return nativeGetFileId();
    }

    public String getFileName() {
        return nativeGetFileName();
    }

    public String getFileOwner() {
        return nativeGetFileOwner();
    }

    public long getFileSize() {
        return nativeGetFileSize();
    }

    public long getUpdateTime() {
        return nativeGetUpdateTime();
    }

    public native void nativeFinalize();

    public native String nativeGetFileId();

    public native String nativeGetFileName();

    public native String nativeGetFileOwner();

    public native long nativeGetFileSize();

    public native long nativeGetUpdateTime();

    public native void nativeInit();

    public native void nativeInit(EMAMucShareFile eMAMucShareFile);
}
