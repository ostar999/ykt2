package com.tencent.liteav.videoengine.decoder;

import androidx.annotation.NonNull;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videobase.a.a;
import com.tencent.liteav.videobase.frame.PixelFrame;
import com.tencent.liteav.videoengine.decoder.n;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class SoftwareVideoDecoder implements n {
    private static final String TAG = "FFMPEGVideoDecoder";
    private o mListener;
    private long mNativeHandle = 0;

    @NonNull
    private final com.tencent.liteav.videobase.f.a mReporter;

    static {
        nativeClassInit();
    }

    public SoftwareVideoDecoder(@NonNull com.tencent.liteav.videobase.f.a aVar) {
        this.mReporter = aVar;
    }

    private static PixelFrame createPixelFrameCallFromNative(ByteBuffer byteBuffer, int i2, int i3, int i4, int i5, long j2) throws DecodeFailException {
        a.c cVar;
        if (i2 == 0) {
            cVar = a.c.I420;
        } else if (i2 == 1) {
            cVar = a.c.NV21;
        } else if (i2 != 3) {
            if (i2 != 4) {
                throw new DecodeFailException("unknown format " + i2);
            }
            cVar = a.c.I420;
        } else {
            cVar = a.c.NV12;
        }
        PixelFrame pixelFrame = new PixelFrame();
        pixelFrame.setPixelBufferType(a.b.BYTE_BUFFER);
        pixelFrame.setPixelFormatType(cVar);
        pixelFrame.setBuffer(byteBuffer);
        pixelFrame.setWidth(i3);
        pixelFrame.setHeight(i4);
        pixelFrame.setRotation(com.tencent.liteav.videobase.utils.d.a(i5));
        pixelFrame.setTimestamp(j2);
        return pixelFrame;
    }

    private void handleDecoderError(com.tencent.liteav.videobase.f.b bVar, String str, String str2, Object... objArr) {
        this.mReporter.b(bVar, str, str2, objArr);
        o oVar = this.mListener;
        if (oVar != null) {
            oVar.onDecodeFailed(bVar);
        }
    }

    private static native void nativeClassInit();

    private native long nativeCreate();

    private native PixelFrame nativeDecode(long j2, byte[] bArr, int i2, int i3, long j3) throws DecodeFailException;

    private native void nativeDestroy(long j2);

    private native boolean nativeStart(long j2);

    private native void nativeStop(long j2);

    @Override // com.tencent.liteav.videoengine.decoder.n
    public void decode(com.tencent.liteav.videobase.e.b bVar) {
        o oVar;
        if (bVar == null) {
            return;
        }
        try {
            PixelFrame pixelFrameNativeDecode = nativeDecode(this.mNativeHandle, bVar.f19989a, bVar.f19990b.a(), bVar.f19992d, bVar.f19994f);
            if (pixelFrameNativeDecode == null || (oVar = this.mListener) == null) {
                return;
            }
            oVar.onDecodeFrame(pixelFrameNativeDecode, pixelFrameNativeDecode.getTimestamp());
        } catch (DecodeFailException e2) {
            handleDecoderError(com.tencent.liteav.videobase.f.b.EVT_VIDEO_DECODE_RESTART_WHEN_DECODE_ERROR, "VideoDecode: decode error, restart decoder", e2.getMessage(), new Object[0]);
            TXCLog.e(TAG, "decode failed.", e2);
        }
    }

    @Override // com.tencent.liteav.videoengine.decoder.n
    public n.a getDecoderType() {
        return n.a.SOFTWARE;
    }

    @Override // com.tencent.liteav.videoengine.decoder.n
    public void start(Object obj, o oVar) {
        if (this.mNativeHandle != 0) {
            TXCLog.w(TAG, "decoder is already started!");
            return;
        }
        this.mListener = oVar;
        long jNativeCreate = nativeCreate();
        this.mNativeHandle = jNativeCreate;
        if (jNativeCreate == 0) {
            handleDecoderError(com.tencent.liteav.videobase.f.b.ERR_VIDEO_DECODE_START_FAILED_OUT_OF_MEMORY, "VideoDecode: out of memory, start decoder failed", "create software decoder failed.", new Object[0]);
            TXCLog.e(TAG, "create native instance failed.");
        } else if (nativeStart(jNativeCreate)) {
            this.mReporter.a(com.tencent.liteav.videobase.f.b.EVT_VIDEO_DECODE_START_SUCCESS, "VideoDecode: start decoder success", "", new Object[0]);
            TXCLog.i(TAG, "decoder start success.");
        } else {
            handleDecoderError(com.tencent.liteav.videobase.f.b.ERR_VIDEO_DECODE_START_FAILED, "VideoDecode: start decoder failed", "", new Object[0]);
            TXCLog.e(TAG, "start software decoder failed.");
        }
    }

    @Override // com.tencent.liteav.videoengine.decoder.n
    public void stop() {
        long j2 = this.mNativeHandle;
        if (j2 == 0) {
            TXCLog.w(TAG, "decoder has already stopped");
            return;
        }
        nativeStop(j2);
        nativeDestroy(this.mNativeHandle);
        this.mNativeHandle = 0L;
    }
}
