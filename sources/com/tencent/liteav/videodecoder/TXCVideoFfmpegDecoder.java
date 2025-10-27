package com.tencent.liteav.videodecoder;

import android.view.Surface;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import org.json.JSONArray;

/* loaded from: classes6.dex */
public class TXCVideoFfmpegDecoder implements b {
    private boolean mFirstDec;
    private h mListener;
    private long mNativeDecoder;
    private long mNativeNotify;
    private ByteBuffer mPps;
    private byte[] mRawData;
    private ByteBuffer mSps;
    private int mVideoHeight;
    private int mVideoWidth;

    static {
        com.tencent.liteav.basic.util.h.d();
        nativeClassInit();
    }

    private static native void nativeClassInit();

    private native boolean nativeDecode(byte[] bArr, long j2, long j3, long j4);

    private native void nativeInit(WeakReference<TXCVideoFfmpegDecoder> weakReference, boolean z2);

    private native void nativeLoadRawData(byte[] bArr, long j2, int i2);

    private native void nativeRelease();

    private static void postEventFromNative(WeakReference<TXCVideoFfmpegDecoder> weakReference, long j2, int i2, int i3, long j3, long j4, int i4) {
    }

    @Override // com.tencent.liteav.videodecoder.b
    public int GetDecodeCost() {
        return 0;
    }

    @Override // com.tencent.liteav.videodecoder.b
    public int config(Surface surface) {
        return 0;
    }

    public void config(JSONArray jSONArray) {
    }

    @Override // com.tencent.liteav.videodecoder.b
    public void decode(TXSNALPacket tXSNALPacket) {
        h hVar;
        h hVar2;
        if (this.mFirstDec) {
            ByteBuffer byteBuffer = this.mSps;
            if (byteBuffer != null && this.mPps != null) {
                byte[] bArrArray = byteBuffer.array();
                byte[] bArrArray2 = this.mPps.array();
                byte[] bArr = new byte[bArrArray.length + bArrArray2.length];
                System.arraycopy(bArrArray, 0, bArr, 0, bArrArray.length);
                System.arraycopy(bArrArray2, 0, bArr, bArrArray.length, bArrArray2.length);
                if (!nativeDecode(bArr, tXSNALPacket.pts - 1, tXSNALPacket.dts - 1, tXSNALPacket.rotation) && (hVar2 = this.mListener) != null) {
                    hVar2.onDecodeFailed(-2);
                }
            }
            this.mFirstDec = false;
        }
        if (nativeDecode(tXSNALPacket.nalData, tXSNALPacket.pts, tXSNALPacket.dts, tXSNALPacket.rotation) || (hVar = this.mListener) == null) {
            return;
        }
        hVar.onDecodeFailed(-2);
    }

    @Override // com.tencent.liteav.videodecoder.b
    public void enableLimitDecCache(boolean z2) {
    }

    public boolean isH265() {
        return false;
    }

    public void loadNativeData(byte[] bArr, long j2, int i2) {
        nativeLoadRawData(bArr, j2, i2);
    }

    @Override // com.tencent.liteav.videodecoder.b
    public void setListener(h hVar) {
        this.mListener = hVar;
    }

    @Override // com.tencent.liteav.videodecoder.b
    public void setNotifyListener(WeakReference<com.tencent.liteav.basic.b.b> weakReference) {
    }

    @Override // com.tencent.liteav.videodecoder.b
    public int start(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z2, boolean z3) {
        this.mSps = byteBuffer;
        this.mPps = byteBuffer2;
        this.mFirstDec = true;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        nativeInit(new WeakReference<>(this), z2);
        return 0;
    }

    @Override // com.tencent.liteav.videodecoder.b
    public void stop() {
        nativeRelease();
    }
}
