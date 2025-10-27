package com.tencent.liteav.trtc.wrapper;

import com.tencent.trtc.TRTCCloudListener;

/* loaded from: classes6.dex */
public class TRTCLogListenerJNI extends TRTCCloudListener.TRTCLogListener {
    private long mLogCallback;

    private native void nativeOnLog(long j2, String str, int i2, String str2);

    @Override // com.tencent.trtc.TRTCCloudListener.TRTCLogListener
    public void onLog(String str, int i2, String str2) {
        synchronized (this) {
            nativeOnLog(this.mLogCallback, str, i2, str2);
        }
    }

    public void setCallback(long j2) {
        synchronized (this) {
            this.mLogCallback = j2;
        }
    }
}
