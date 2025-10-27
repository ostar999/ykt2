package com.tencent.live2.leb;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videobase.e.b;
import com.tencent.liteav.videobase.e.c;
import com.tencent.liteav.videobase.e.d;

/* loaded from: classes6.dex */
public class TXLEBPlayerJNI {
    public static final int NET_STATE_CONNECTED = 1;
    public static final int NET_STATE_CONNECTING = 0;
    public static final int NET_STATE_CONNECT_FAILED = 3;
    public static final int NET_STATE_DISCONNECTED = 2;
    private static final String TAG = "TXLEBPlayerJNI";
    private Callback mCallback;
    private boolean mEnableEncryption;
    private boolean mEnableFlexFec;
    private boolean mEnableH265;
    private String mUserId;
    private boolean mEnableReceiveVideo = true;
    private boolean mEnableReceiveAudio = true;
    private boolean mEnableAAC = true;
    private int mSEIPayLoadType = 0;
    private boolean mEnableReceiveSEI = false;
    private long mNativeHandle = 0;

    public interface Callback {
        void onEncodedVideo(b bVar);

        void onError(int i2);

        void onNetState(int i2);

        void onReceiveSEIMessage(byte[] bArr);

        int onRequestVideoDecodeCacheNum();

        void onSetHWDecoderMaxCache(int i2, int i3);
    }

    public static final class Configuration {
        boolean enableEncryption;
        boolean enableFlexFec;
        boolean enableH265;
        boolean enableReceiveSeiMessage;
        int seiPayloadType;
        String url;
        boolean enableReceiveVideo = true;
        boolean enableReceiveAudio = true;
        boolean enableAAC = true;
    }

    private void apiError(String str) {
        TXCLog.e(TAG, "v2_api_leb_player_jni(" + this + ") " + str);
    }

    private void apiLog(String str) {
        TXCLog.i(TAG, "v2_api_leb_player_jni(" + this + ") " + str);
    }

    private void apiWarn(String str) {
        TXCLog.w(TAG, "v2_api_leb_player_jni(" + this + ") " + str);
    }

    private native void nativeEnableReceiveSeiMessage(long j2, boolean z2, int i2);

    private native void nativeNotifyRenderFramePTS(long j2, long j3);

    private native void nativeRequestUpdateStatistics(long j2);

    private native long nativeStart(String str, Configuration configuration);

    private native void nativeStop(long j2);

    private void onError(int i2) {
        apiError("onError: " + i2);
        Callback callback = this.mCallback;
        if (callback == null) {
            return;
        }
        callback.onError(i2);
    }

    private void onNetState(int i2) {
        apiLog("onNetState: " + i2);
        Callback callback = this.mCallback;
        if (callback == null) {
            return;
        }
        callback.onNetState(i2);
    }

    private void onReceiveSEIMessage(byte[] bArr) {
        Callback callback = this.mCallback;
        if (callback == null) {
            return;
        }
        callback.onReceiveSEIMessage(bArr);
    }

    private void onReceiveVideoFrame(byte[] bArr, long j2, long j3, boolean z2) {
        Callback callback = this.mCallback;
        if (callback == null) {
            return;
        }
        b bVar = new b();
        bVar.f19989a = bArr;
        bVar.f19993e = j3;
        bVar.f19994f = j2;
        if (z2) {
            bVar.f19991c = d.H265;
        } else {
            bVar.f19991c = d.H264_BASELINE;
        }
        bVar.f19990b = c.UNKNOWN;
        callback.onEncodedVideo(bVar);
    }

    private int onRequestVideoDecodeCacheNum() {
        Callback callback = this.mCallback;
        if (callback == null) {
            return 0;
        }
        return callback.onRequestVideoDecodeCacheNum();
    }

    private void onSetHWDecoderMaxCache(int i2, int i3) {
        Callback callback = this.mCallback;
        if (callback == null) {
            return;
        }
        callback.onSetHWDecoderMaxCache(i2, i3);
    }

    public void enableAAC(boolean z2) {
        this.mEnableAAC = z2;
    }

    public void enableEncryption(boolean z2) {
        this.mEnableEncryption = z2;
    }

    public void enableFlexFec(boolean z2) {
        this.mEnableFlexFec = z2;
    }

    public void enableH265(boolean z2) {
        this.mEnableH265 = z2;
    }

    public void enableReceiveAudio(boolean z2) {
        this.mEnableReceiveAudio = z2;
    }

    public void enableReceiveSeiMessage(boolean z2, int i2) {
        this.mSEIPayLoadType = i2;
        this.mEnableReceiveSEI = z2;
        long j2 = this.mNativeHandle;
        if (j2 != 0) {
            nativeEnableReceiveSeiMessage(j2, z2, i2);
        }
    }

    public void enableReceiveVideo(boolean z2) {
        this.mEnableReceiveVideo = z2;
    }

    public void notifyRenderFramePTS(long j2) {
        nativeNotifyRenderFramePTS(this.mNativeHandle, j2);
    }

    public void requestUpdateStatistics() {
        nativeRequestUpdateStatistics(this.mNativeHandle);
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setUserId(String str) {
        this.mUserId = str;
    }

    public void start(String str) {
        apiLog("start: " + str);
        if (this.mNativeHandle != 0) {
            return;
        }
        Configuration configuration = new Configuration();
        configuration.url = str;
        configuration.enableReceiveVideo = this.mEnableReceiveVideo;
        configuration.enableReceiveAudio = this.mEnableReceiveAudio;
        configuration.enableEncryption = this.mEnableEncryption;
        configuration.enableAAC = this.mEnableAAC;
        configuration.enableH265 = this.mEnableH265;
        configuration.enableFlexFec = this.mEnableFlexFec;
        configuration.enableReceiveSeiMessage = this.mEnableReceiveSEI;
        configuration.seiPayloadType = this.mSEIPayLoadType;
        this.mNativeHandle = nativeStart(this.mUserId, configuration);
    }

    public void stop() {
        apiLog("stop");
        nativeStop(this.mNativeHandle);
        this.mNativeHandle = 0L;
    }
}
