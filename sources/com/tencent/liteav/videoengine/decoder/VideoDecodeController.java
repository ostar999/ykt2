package com.tencent.liteav.videoengine.decoder;

import android.os.HandlerThread;
import androidx.annotation.NonNull;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videobase.frame.PixelFrame;
import com.tencent.liteav.videoengine.decoder.a;
import com.tencent.liteav.videoengine.decoder.n;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;

/* loaded from: classes6.dex */
public class VideoDecodeController implements o {
    private static final int INTERVAL_DRAIN_DECODED_FRAME = 5;
    private static final String TAG = "VideoDecodeController";

    @NonNull
    private final a mDecodeSupervisor;
    private VideoDecodeControllerListener mDecoderListener;
    private com.tencent.liteav.basic.util.j mDrainDecodedFrameTimer;
    private com.tencent.liteav.videobase.c.c mEGLCore;
    private JSONArray mMediaCodecDeviceRelatedParams;

    @NonNull
    private final com.tencent.liteav.videobase.f.a mReporter;
    private final a.d mResolutionDecoder;

    @NonNull
    private final m mStatistics;
    private n mVideoDecoder;
    private com.tencent.liteav.basic.util.f mWorkHandler;
    private boolean mIsStarted = false;
    private boolean mIsFirstIFrameReceived = false;
    private final AtomicInteger mDecodingFrameCount = new AtomicInteger(0);

    /* renamed from: com.tencent.liteav.videoengine.decoder.VideoDecodeController$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$tencent$liteav$videoengine$decoder$DecoderSupervisor$Instruction;

        static {
            int[] iArr = new int[a.c.values().length];
            $SwitchMap$com$tencent$liteav$videoengine$decoder$DecoderSupervisor$Instruction = iArr;
            try {
                iArr[a.c.DROP_FRAME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$tencent$liteav$videoengine$decoder$DecoderSupervisor$Instruction[a.c.CONTINUE_DECODE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$tencent$liteav$videoengine$decoder$DecoderSupervisor$Instruction[a.c.SWITCH_TO_HARDWARE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$tencent$liteav$videoengine$decoder$DecoderSupervisor$Instruction[a.c.SWITCH_TO_SOFTWARE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$tencent$liteav$videoengine$decoder$DecoderSupervisor$Instruction[a.c.RESTART_DECODER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$tencent$liteav$videoengine$decoder$DecoderSupervisor$Instruction[a.c.REQUEST_KEY_FRAME.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$tencent$liteav$videoengine$decoder$DecoderSupervisor$Instruction[a.c.REPORT_DECODE_ERROR.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public enum DecodeStrategy {
        AUTO_SWITCH,
        USE_HARDWARE_ONLY,
        USE_SOFTWARE_ONLY
    }

    public interface VideoDecodeControllerListener extends o {
    }

    public VideoDecodeController(@NonNull com.tencent.liteav.videobase.f.a aVar) {
        a.d dVar = VideoDecodeController$$Lambda$1.instance;
        this.mResolutionDecoder = dVar;
        this.mReporter = aVar;
        this.mDecodeSupervisor = new a(dVar, aVar, false, true);
        this.mStatistics = new m(aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decodeInternal(com.tencent.liteav.videobase.e.b bVar) {
        bVar.d();
        switch (AnonymousClass1.$SwitchMap$com$tencent$liteav$videoengine$decoder$DecoderSupervisor$Instruction[this.mDecodeSupervisor.a(bVar).ordinal()]) {
            case 2:
                doDecode(bVar);
                break;
            case 3:
                updateDecoderType(bVar, n.a.HARDWARE);
                doDecode(bVar);
                break;
            case 4:
                updateDecoderType(bVar, n.a.SOFTWARE);
                doDecode(bVar);
                break;
            case 5:
                n.a usingDecoderType = getUsingDecoderType();
                if (usingDecoderType != null) {
                    updateDecoderType(bVar, usingDecoderType);
                    doDecode(bVar);
                    break;
                }
                break;
            case 6:
                VideoDecodeControllerListener videoDecodeControllerListener = this.mDecoderListener;
                if (videoDecodeControllerListener != null) {
                    videoDecodeControllerListener.onRequestKeyFrame();
                    break;
                }
                break;
            case 7:
                VideoDecodeControllerListener videoDecodeControllerListener2 = this.mDecoderListener;
                if (videoDecodeControllerListener2 != null) {
                    videoDecodeControllerListener2.onDecodeFailed(null);
                    break;
                }
                break;
        }
    }

    private void doDecode(com.tencent.liteav.videobase.e.b bVar) {
        if (!this.mIsFirstIFrameReceived && bVar.a()) {
            this.mIsFirstIFrameReceived = true;
            this.mReporter.a(com.tencent.liteav.videobase.f.b.EVT_VIDEO_DECODE_START_DECODE_FIRST_FRAME, "VideoDecode: start decode first frame", null, new Object[0]);
            this.mReporter.a("Remote-VideoDecoder[%s]: Decode first frame [tinyID:%s]", Integer.valueOf(hashCode()), this.mReporter.a());
        }
        this.mStatistics.a(bVar.f19994f);
        if (this.mVideoDecoder != null) {
            this.mDecodingFrameCount.incrementAndGet();
            this.mVideoDecoder.decode(bVar);
        }
    }

    private n.a getUsingDecoderType() {
        n nVar = this.mVideoDecoder;
        if (nVar == null) {
            return null;
        }
        return nVar.getDecoderType();
    }

    public static /* synthetic */ void lambda$initialize$1(VideoDecodeController videoDecodeController) {
        com.tencent.liteav.videobase.c.c cVar = new com.tencent.liteav.videobase.c.c();
        videoDecodeController.mEGLCore = cVar;
        try {
            cVar.a(null, null, 128, 128);
        } catch (com.tencent.liteav.videobase.c.d e2) {
            TXCLog.e(TAG, "create egl core failed.", e2);
            videoDecodeController.mReporter.a(com.tencent.liteav.videobase.f.b.ERR_VIDEO_DECODE_EGL_CORE_CREATE_FAILED, "VideoDecode: create EGLCore failed", "", new Object[0]);
        }
    }

    public static /* synthetic */ com.tencent.liteav.basic.util.e lambda$new$0(boolean z2, byte[] bArr) {
        int[] iArrNativeDecodeResolution = nativeDecodeResolution(z2, bArr);
        return (iArrNativeDecodeResolution == null || iArrNativeDecodeResolution.length != 2) ? new com.tencent.liteav.basic.util.e(0, 0) : new com.tencent.liteav.basic.util.e(iArrNativeDecodeResolution[0], iArrNativeDecodeResolution[1]);
    }

    public static /* synthetic */ void lambda$onDecodeFailed$12(VideoDecodeController videoDecodeController) {
        TXCLog.i(TAG, "on decode failed, type: %s", videoDecodeController.getUsingDecoderType());
        videoDecodeController.mDecodeSupervisor.a();
    }

    public static /* synthetic */ void lambda$onDecodeFrame$10(VideoDecodeController videoDecodeController, PixelFrame pixelFrame, long j2) {
        videoDecodeController.mDecodeSupervisor.a(pixelFrame.getTimestamp());
        videoDecodeController.mDecodingFrameCount.decrementAndGet();
        videoDecodeController.mStatistics.b();
        if (videoDecodeController.mEGLCore == null || !videoDecodeController.makeCurrentInternal()) {
            pixelFrame.release();
            return;
        }
        VideoDecodeControllerListener videoDecodeControllerListener = videoDecodeController.mDecoderListener;
        if (videoDecodeControllerListener != null) {
            videoDecodeControllerListener.onDecodeFrame(pixelFrame, j2);
        }
        pixelFrame.release();
    }

    public static /* synthetic */ void lambda$onDecodeSEI$11(VideoDecodeController videoDecodeController, ByteBuffer byteBuffer) {
        VideoDecodeControllerListener videoDecodeControllerListener = videoDecodeController.mDecoderListener;
        if (videoDecodeControllerListener != null) {
            videoDecodeControllerListener.onDecodeSEI(byteBuffer);
        }
    }

    public static /* synthetic */ void lambda$onRequestKeyFrame$13(VideoDecodeController videoDecodeController) {
        VideoDecodeControllerListener videoDecodeControllerListener = videoDecodeController.mDecoderListener;
        if (videoDecodeControllerListener != null) {
            videoDecodeControllerListener.onRequestKeyFrame();
        }
    }

    public static /* synthetic */ void lambda$restartDecoder$6(VideoDecodeController videoDecodeController) {
        videoDecodeController.stopDecoderInternal();
        TXCLog.i(TAG, "restart decoder");
    }

    public static /* synthetic */ void lambda$setDecodeStrategy$5(VideoDecodeController videoDecodeController, DecodeStrategy decodeStrategy) {
        videoDecodeController.mDecodeSupervisor.a(decodeStrategy);
    }

    public static /* synthetic */ void lambda$setHWDecoderMaxCache$8(VideoDecodeController videoDecodeController, int i2, int i3) {
        videoDecodeController.mDecodeSupervisor.a(i2, i3);
    }

    public static /* synthetic */ void lambda$setMediaCodecDeviceRelatedParams$7(VideoDecodeController videoDecodeController, JSONArray jSONArray) {
        videoDecodeController.mMediaCodecDeviceRelatedParams = jSONArray;
        TXCLog.i(TAG, "set MediaCodec device related params to %s", jSONArray);
    }

    public static /* synthetic */ void lambda$start$2(VideoDecodeController videoDecodeController, VideoDecodeControllerListener videoDecodeControllerListener) {
        if (videoDecodeController.mIsStarted) {
            return;
        }
        videoDecodeController.mIsStarted = true;
        videoDecodeController.mDecoderListener = videoDecodeControllerListener;
        videoDecodeController.mDecodingFrameCount.set(0);
        videoDecodeController.mDecodeSupervisor.b();
        videoDecodeController.mStatistics.a();
        com.tencent.liteav.basic.util.f fVar = videoDecodeController.mWorkHandler;
        if (fVar != null && videoDecodeController.mDrainDecodedFrameTimer == null) {
            com.tencent.liteav.basic.util.j jVar = new com.tencent.liteav.basic.util.j(fVar.getLooper(), VideoDecodeController$$Lambda$15.lambdaFactory$(videoDecodeController));
            videoDecodeController.mDrainDecodedFrameTimer = jVar;
            jVar.a(0, 5);
        }
    }

    public static /* synthetic */ void lambda$stop$4(VideoDecodeController videoDecodeController) {
        videoDecodeController.mIsStarted = false;
        videoDecodeController.mDecoderListener = null;
        com.tencent.liteav.basic.util.j jVar = videoDecodeController.mDrainDecodedFrameTimer;
        if (jVar != null) {
            jVar.a();
            videoDecodeController.mDrainDecodedFrameTimer = null;
        }
        videoDecodeController.stopDecoderInternal();
        com.tencent.liteav.videobase.f.a aVar = videoDecodeController.mReporter;
        aVar.a("Remote-VideoDecoder[%s]: Stop [tinyID:%s]", videoDecodeController, aVar.a());
    }

    public static /* synthetic */ void lambda$uninitialize$9(VideoDecodeController videoDecodeController) {
        com.tencent.liteav.videobase.c.c cVar = videoDecodeController.mEGLCore;
        if (cVar == null) {
            return;
        }
        try {
            cVar.b();
            videoDecodeController.mEGLCore.d();
        } catch (com.tencent.liteav.videobase.c.d e2) {
            TXCLog.e(TAG, "destroy egl core failed.", e2);
            videoDecodeController.mReporter.a(com.tencent.liteav.videobase.f.b.ERR_VIDEO_DECODE_EGL_CORE_DESTROY_FAILED, "VideoDecode: destroy EGLCore failed", "", new Object[0]);
        }
        videoDecodeController.mEGLCore = null;
    }

    private boolean makeCurrentInternal() {
        try {
            this.mEGLCore.a();
            return true;
        } catch (com.tencent.liteav.videobase.c.d e2) {
            TXCLog.e(TAG, "make current failed.", e2);
            return false;
        }
    }

    private static native int[] nativeDecodeResolution(boolean z2, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public void onDrainDecodedFrameTimeOut() {
        n nVar;
        if (this.mDecodingFrameCount.get() <= 0 || (nVar = this.mVideoDecoder) == null) {
            return;
        }
        nVar.decode(null);
    }

    private void runOnWorkThread(Runnable runnable) {
        com.tencent.liteav.basic.util.f fVar = this.mWorkHandler;
        if (fVar == null || !fVar.getLooper().getThread().isAlive()) {
            TXCLog.w(TAG, "runnable [%s] is failed to post", runnable.getClass().getName());
        } else {
            fVar.post(runnable);
        }
    }

    private void stopDecoderInternal() {
        n nVar = this.mVideoDecoder;
        if (nVar != null) {
            nVar.stop();
            this.mVideoDecoder = null;
        }
    }

    private void updateDecoderType(com.tencent.liteav.videobase.e.b bVar, n.a aVar) {
        stopDecoderInternal();
        com.tencent.liteav.basic.util.f fVar = this.mWorkHandler;
        if (fVar == null) {
            return;
        }
        com.tencent.liteav.basic.util.e eVarDecodeResolutionFromSps = this.mResolutionDecoder.decodeResolutionFromSps(bVar.c(), bVar.f19989a);
        if (aVar == n.a.SOFTWARE) {
            this.mVideoDecoder = new SoftwareVideoDecoder(this.mReporter);
        } else {
            this.mVideoDecoder = new h(fVar.getLooper(), eVarDecodeResolutionFromSps, bVar.c(), this.mMediaCodecDeviceRelatedParams, this.mReporter);
        }
        this.mVideoDecoder.start(this.mEGLCore.c(), this);
        this.mDecodingFrameCount.set(0);
        TXCLog.i(TAG, "update decoder type to %s, video res: %s", aVar, eVarDecodeResolutionFromSps);
        this.mStatistics.a(this.mVideoDecoder.getDecoderType(), bVar.c());
    }

    public void decode(com.tencent.liteav.videobase.e.b bVar) {
        runOnWorkThread(VideoDecodeController$$Lambda$4.lambdaFactory$(this, bVar));
    }

    public int getDecodingFrameCount() {
        return this.mDecodingFrameCount.get();
    }

    public void initialize() {
        TXCLog.i(TAG, "initialize");
        synchronized (this) {
            if (this.mWorkHandler != null) {
                TXCLog.w(TAG, "video decode controller is initialized");
                return;
            }
            HandlerThread handlerThread = new HandlerThread("video-decoder-controller");
            handlerThread.start();
            this.mWorkHandler = new com.tencent.liteav.basic.util.f(handlerThread.getLooper());
            runOnWorkThread(VideoDecodeController$$Lambda$2.lambdaFactory$(this));
        }
    }

    @Override // com.tencent.liteav.videoengine.decoder.o
    public void onDecodeFailed(com.tencent.liteav.videobase.f.b bVar) {
        runOnWorkThread(VideoDecodeController$$Lambda$13.lambdaFactory$(this));
    }

    @Override // com.tencent.liteav.videoengine.decoder.o
    public void onDecodeFrame(PixelFrame pixelFrame, long j2) {
        pixelFrame.retain();
        runOnWorkThread(VideoDecodeController$$Lambda$11.lambdaFactory$(this, pixelFrame, j2));
    }

    @Override // com.tencent.liteav.videoengine.decoder.o
    public void onDecodeSEI(ByteBuffer byteBuffer) {
        runOnWorkThread(VideoDecodeController$$Lambda$12.lambdaFactory$(this, byteBuffer));
    }

    @Override // com.tencent.liteav.videoengine.decoder.o
    public void onRequestKeyFrame() {
        runOnWorkThread(VideoDecodeController$$Lambda$14.lambdaFactory$(this));
    }

    public void restartDecoder() {
        runOnWorkThread(VideoDecodeController$$Lambda$7.lambdaFactory$(this));
    }

    public void setDecodeStrategy(DecodeStrategy decodeStrategy) {
        runOnWorkThread(VideoDecodeController$$Lambda$6.lambdaFactory$(this, decodeStrategy));
    }

    public void setHWDecoderMaxCache(int i2, int i3) {
        runOnWorkThread(VideoDecodeController$$Lambda$9.lambdaFactory$(this, i2, i3));
    }

    public void setMediaCodecDeviceRelatedParams(JSONArray jSONArray) {
        runOnWorkThread(VideoDecodeController$$Lambda$8.lambdaFactory$(this, jSONArray));
    }

    public void start(VideoDecodeControllerListener videoDecodeControllerListener) {
        runOnWorkThread(VideoDecodeController$$Lambda$3.lambdaFactory$(this, videoDecodeControllerListener));
    }

    public void stop() {
        runOnWorkThread(VideoDecodeController$$Lambda$5.lambdaFactory$(this));
    }

    public void uninitialize() {
        TXCLog.i(TAG, "uninitialize");
        runOnWorkThread(VideoDecodeController$$Lambda$10.lambdaFactory$(this));
        synchronized (this) {
            com.tencent.liteav.basic.util.f fVar = this.mWorkHandler;
            if (fVar != null) {
                fVar.e();
                this.mWorkHandler = null;
            }
        }
    }
}
