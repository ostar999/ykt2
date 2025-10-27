package com.tencent.liteav.videoencoder;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.j;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.basic.util.h;
import com.tencent.liteav.beauty.b.o;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public class TXCSWVideoEncoder extends e {
    private static final boolean DEBUG = false;
    private static final String TAG = "TXCSWVideoEncoder";
    private j mRawFrameFilter;
    private j mResizeFilter;
    private long mNativeEncoder = 0;
    private int mBitrate = 0;
    private long mPTS = 0;
    private int mPushIdx = 0;
    private int mRendIdx = 0;
    private int mPopIdx = 0;

    static {
        h.d();
        nativeClassInit();
    }

    public static long getAndIncreaseGopIndex() {
        return nativeGetAndIncreaseGopIndex();
    }

    public static long getAndIncreateSeq() {
        return nativeGetAndIncreaseSeq();
    }

    public static boolean isRPSSupported() {
        return nativeIsRPSSupported();
    }

    private static native void nativeClassInit();

    private native void nativeEnableNearestRPS(long j2, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeEncode(long j2, int i2, int i3, int i4, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeEncodeSync(long j2, int i2, int i3, int i4, long j3);

    private static native long nativeGetAndIncreaseGopIndex();

    private static native long nativeGetAndIncreaseSeq();

    private native long nativeGetRealFPS(long j2);

    private native long nativeInit(WeakReference<TXCSWVideoEncoder> weakReference);

    private static native boolean nativeIsRPSSupported();

    private native void nativeRelease(long j2);

    private native void nativeRestartIDR(long j2);

    private native void nativeSetBitrate(long j2, int i2);

    private native void nativeSetBitrateFromQos(long j2, int i2, int i3);

    private native void nativeSetEncodeIdrFpsFromQos(long j2, int i2);

    private native void nativeSetFPS(long j2, int i2);

    private native void nativeSetID(long j2, String str);

    private native void nativeSetRPSRefBitmap(long j2, int i2, int i3, long j3);

    private native void nativeSignalEOSAndFlush(long j2);

    private native int nativeStart(long j2, TXSVideoEncoderParam tXSVideoEncoderParam);

    private native void nativeStop(long j2);

    private native long nativegetRealBitrate(long j2);

    private static void onEncodeFinishedFromNative(WeakReference<TXCSWVideoEncoder> weakReference, int i2, long j2, long j3) {
        TXCSWVideoEncoder tXCSWVideoEncoder = weakReference.get();
        if (tXCSWVideoEncoder != null) {
            tXCSWVideoEncoder.onEncodeFinished(i2, j2, j3);
        }
    }

    private static void postEventFromNative(WeakReference<TXCSWVideoEncoder> weakReference, byte[] bArr, int i2, long j2, long j3, long j4, long j5, long j6, long j7, int i3) {
        TXCSWVideoEncoder tXCSWVideoEncoder = weakReference.get();
        if (tXCSWVideoEncoder != null) {
            TXSNALPacket tXSNALPacket = new TXSNALPacket();
            tXSNALPacket.nalData = bArr;
            tXSNALPacket.nalType = i2;
            tXSNALPacket.gopIndex = j2;
            tXSNALPacket.gopFrameIndex = j3;
            tXSNALPacket.frameIndex = j4;
            tXSNALPacket.refFremeIndex = j5;
            tXSNALPacket.pts = j6;
            tXSNALPacket.dts = j7;
            tXCSWVideoEncoder.callDelegate(tXSNALPacket, i3);
        }
    }

    private long pushVideoFrameInternal(int i2, int i3, int i4, long j2, final boolean z2) {
        j jVar = this.mResizeFilter;
        j jVar2 = this.mRawFrameFilter;
        if (this.mGLContextExternal == null) {
            return 0L;
        }
        this.mInputWidth = i3;
        this.mInputHeight = i4;
        if (jVar == null) {
            jVar = new j();
            this.mResizeFilter = jVar;
            jVar.a();
            jVar.a(true);
        }
        jVar.a(this.mOutputWidth, this.mOutputHeight);
        GLES20.glViewport(0, 0, this.mOutputWidth, this.mOutputHeight);
        int i5 = (720 - this.mRotation) % 360;
        jVar.a(i3, i4, i5, null, ((i5 == 90 || i5 == 270) ? this.mOutputHeight : this.mOutputWidth) / ((i5 == 90 || i5 == 270) ? this.mOutputWidth : this.mOutputHeight), this.mEnableXMirror, false);
        jVar.b(i2);
        final int iL = jVar.l();
        int[] iArr = new int[1];
        this.mPTS = j2;
        if (jVar2 == null) {
            String str = TAG;
            TXCLog.i(str, "pushVideoFrameInternal->create mRawFrameFilter");
            o oVar = new o(1);
            this.mRawFrameFilter = oVar;
            oVar.a(true);
            if (!oVar.a()) {
                TXCLog.i(str, "pushVideoFrameInternal->destroy mRawFrameFilter, init failed!");
                this.mRawFrameFilter = null;
                return 10000004L;
            }
            oVar.a(this.mOutputWidth, this.mOutputHeight);
            oVar.a(new j.a() { // from class: com.tencent.liteav.videoencoder.TXCSWVideoEncoder.1
                @Override // com.tencent.liteav.basic.opengl.j.a
                public void a(int i6) {
                    synchronized (TXCSWVideoEncoder.this) {
                        TXCSWVideoEncoder tXCSWVideoEncoder = TXCSWVideoEncoder.this;
                        f fVar = tXCSWVideoEncoder.mListener;
                        if (fVar != null) {
                            fVar.m(tXCSWVideoEncoder.mStreamType);
                        }
                        if (z2) {
                            TXCSWVideoEncoder tXCSWVideoEncoder2 = TXCSWVideoEncoder.this;
                            long j3 = tXCSWVideoEncoder2.mNativeEncoder;
                            int i7 = iL;
                            TXCSWVideoEncoder tXCSWVideoEncoder3 = TXCSWVideoEncoder.this;
                            tXCSWVideoEncoder2.nativeEncodeSync(j3, i7, tXCSWVideoEncoder3.mOutputWidth, tXCSWVideoEncoder3.mOutputHeight, tXCSWVideoEncoder3.mPTS);
                        } else {
                            TXCSWVideoEncoder tXCSWVideoEncoder4 = TXCSWVideoEncoder.this;
                            long j4 = tXCSWVideoEncoder4.mNativeEncoder;
                            int i8 = iL;
                            TXCSWVideoEncoder tXCSWVideoEncoder5 = TXCSWVideoEncoder.this;
                            tXCSWVideoEncoder4.nativeEncode(j4, i8, tXCSWVideoEncoder5.mOutputWidth, tXCSWVideoEncoder5.mOutputHeight, tXCSWVideoEncoder5.mPTS);
                        }
                    }
                }
            });
            jVar2 = oVar;
        }
        GLES20.glViewport(0, 0, this.mOutputWidth, this.mOutputHeight);
        jVar2.b(iL);
        int i6 = iArr[0];
        if (i6 == 0) {
            return 0L;
        }
        callDelegate(i6);
        return 0L;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void enableNearestRPS(int i2) {
        synchronized (this) {
            nativeEnableNearestRPS(this.mNativeEncoder, i2);
        }
    }

    @Override // com.tencent.liteav.videoencoder.e
    public long getRealBitrate() {
        long jNativegetRealBitrate;
        synchronized (this) {
            jNativegetRealBitrate = nativegetRealBitrate(this.mNativeEncoder);
        }
        return jNativegetRealBitrate;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public double getRealFPS() {
        double dNativeGetRealFPS;
        synchronized (this) {
            dNativeGetRealFPS = nativeGetRealFPS(this.mNativeEncoder);
        }
        return dNativeGetRealFPS;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public long pushVideoFrame(int i2, int i3, int i4, long j2) {
        return pushVideoFrameInternal(i2, i3, i4, j2, false);
    }

    @Override // com.tencent.liteav.videoencoder.e
    public long pushVideoFrameAsync(int i2, int i3, int i4, long j2) {
        return pushVideoFrameInternal(i2, i3, i4, j2, true);
    }

    @Override // com.tencent.liteav.videoencoder.e
    public long pushVideoFrameSync(int i2, int i3, int i4, long j2) {
        return pushVideoFrameInternal(i2, i3, i4, j2, true);
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void restartIDR() {
        synchronized (this) {
            nativeRestartIDR(this.mNativeEncoder);
        }
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void setBitrate(int i2) {
        this.mBitrate = i2;
        synchronized (this) {
            nativeSetBitrate(this.mNativeEncoder, i2);
        }
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void setBitrateFromQos(int i2, int i3) {
        this.mBitrate = i2;
        synchronized (this) {
            nativeSetBitrateFromQos(this.mNativeEncoder, i2, i3);
        }
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void setEncodeIdrFpsFromQos(int i2) {
        synchronized (this) {
            nativeSetEncodeIdrFpsFromQos(this.mNativeEncoder, i2);
        }
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void setFPS(int i2) {
        synchronized (this) {
            nativeSetFPS(this.mNativeEncoder, i2);
        }
    }

    @Override // com.tencent.liteav.basic.module.a
    public void setID(String str) {
        super.setID(str);
        synchronized (this) {
            nativeSetID(this.mNativeEncoder, str);
        }
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void setRPSRefBitmap(int i2, int i3, long j2) {
        synchronized (this) {
            nativeSetRPSRefBitmap(this.mNativeEncoder, i2, i3, j2);
        }
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void signalEOSAndFlush() {
        synchronized (this) {
            nativeSignalEOSAndFlush(this.mNativeEncoder);
        }
    }

    @Override // com.tencent.liteav.videoencoder.e
    public int start(TXSVideoEncoderParam tXSVideoEncoderParam) {
        super.start(tXSVideoEncoderParam);
        int i2 = tXSVideoEncoderParam.width;
        int i3 = ((i2 + 7) / 8) * 8;
        int i4 = tXSVideoEncoderParam.height;
        int i5 = ((i4 + 1) / 2) * 2;
        if (i3 != i2 || i5 != i4) {
            String str = TAG;
            TXCLog.w(str, "Encode Resolution not supportted, transforming...");
            TXCLog.w(str, tXSVideoEncoderParam.width + "x" + tXSVideoEncoderParam.height + "-> " + i3 + "x" + i5);
        }
        tXSVideoEncoderParam.width = i3;
        tXSVideoEncoderParam.height = i5;
        this.mOutputWidth = i3;
        this.mOutputHeight = i5;
        this.mInputWidth = i3;
        this.mInputHeight = i5;
        this.mRawFrameFilter = null;
        this.mResizeFilter = null;
        synchronized (this) {
            long jNativeInit = nativeInit(new WeakReference<>(this));
            this.mNativeEncoder = jNativeInit;
            nativeSetBitrate(jNativeInit, this.mBitrate);
            nativeSetID(this.mNativeEncoder, getID());
            nativeStart(this.mNativeEncoder, tXSVideoEncoderParam);
        }
        return 0;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void stop() {
        long j2;
        TXCLog.i(TAG, "stop->enter with mRawFrameFilter:" + this.mRawFrameFilter);
        this.mGLContextExternal = null;
        synchronized (this) {
            j2 = this.mNativeEncoder;
            this.mNativeEncoder = 0L;
        }
        nativeStop(j2);
        nativeRelease(j2);
        j jVar = this.mRawFrameFilter;
        if (jVar != null) {
            jVar.d();
            this.mRawFrameFilter = null;
        }
        j jVar2 = this.mResizeFilter;
        if (jVar2 != null) {
            jVar2.d();
            this.mResizeFilter = null;
        }
        super.stop();
    }
}
