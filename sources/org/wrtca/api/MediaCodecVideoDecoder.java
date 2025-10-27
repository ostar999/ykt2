package org.wrtca.api;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.os.SystemClock;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import org.wrtca.api.SurfaceTextureHelper;
import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.CalledByNativeUnchecked;
import org.wrtca.log.Logging;
import org.wrtca.util.ThreadUtils;
import org.wrtca.video.MediaCodecUtils;

/* loaded from: classes9.dex */
public class MediaCodecVideoDecoder {
    private static final int COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m = 2141391876;
    private static final int COLOR_QCOM_FORMATYVU420PackedSemiPlanar16m4ka = 2141391874;
    private static final int COLOR_QCOM_FORMATYVU420PackedSemiPlanar32m4ka = 2141391873;
    private static final int COLOR_QCOM_FORMATYVU420PackedSemiPlanar64x32Tile2m8ka = 2141391875;
    private static final int DEQUEUE_INPUT_TIMEOUT = 500000;
    private static final String FORMAT_KEY_CROP_BOTTOM = "crop-bottom";
    private static final String FORMAT_KEY_CROP_LEFT = "crop-left";
    private static final String FORMAT_KEY_CROP_RIGHT = "crop-right";
    private static final String FORMAT_KEY_CROP_TOP = "crop-top";
    private static final String FORMAT_KEY_SLICE_HEIGHT = "slice-height";
    private static final String FORMAT_KEY_STRIDE = "stride";
    private static final String H264_MIME_TYPE = "video/avc";
    private static final long MAX_DECODE_TIME_MS = 200;
    private static final int MAX_QUEUED_OUTPUTBUFFERS = 3;
    private static final int MEDIA_CODEC_RELEASE_TIMEOUT_MS = 5000;
    private static final String TAG = "MediaCodecVideoDecoder";
    private static final String VP8_MIME_TYPE = "video/x-vnd.on2.vp8";
    private static final String VP9_MIME_TYPE = "video/x-vnd.on2.vp9";
    private static int codecErrors = 0;
    private static MediaCodecVideoDecoderErrorCallback errorCallback = null;
    private static MediaCodecVideoDecoder runningInstance = null;
    private static final String supportedExynosH264HighProfileHwCodecPrefix = "OMX.Exynos.";
    private static final String supportedMediaTekH264HighProfileHwCodecPrefix = "OMX.MTK.";
    private static final String supportedQcomH264HighProfileHwCodecPrefix = "OMX.qcom.";
    private int colorFormat;
    private int droppedFrames;
    private boolean hasDecodedFirstFrame;
    private int height;
    private ByteBuffer[] inputBuffers;
    private MediaCodec mediaCodec;
    private Thread mediaCodecThread;
    private ByteBuffer[] outputBuffers;
    private int sliceHeight;
    private int stride;
    private TextureListener textureListener;
    private boolean useSurface;
    private int width;
    private static Set<String> hwDecoderDisabledTypes = new HashSet();
    private static final String[] supportedVp9HwCodecPrefixes = {"OMX.qcom.", "OMX.Exynos."};
    private static final List<Integer> supportedColorList = Arrays.asList(19, 21, 2141391872, 2141391873, 2141391874, 2141391875, 2141391876);
    private final Queue<TimeStamps> decodeStartTimeMs = new ArrayDeque();
    private Surface surface = null;
    private final Queue<DecodedOutputBuffer> dequeuedSurfaceOutputBuffers = new ArrayDeque();

    public static class DecodedOutputBuffer {
        private final long decodeTimeMs;
        private final long endDecodeTimeMs;
        private final int index;
        private final long ntpTimeStampMs;
        private final int offset;
        private final long presentationTimeStampMs;
        private final int size;
        private final long timeStampMs;

        public DecodedOutputBuffer(int i2, int i3, int i4, long j2, long j3, long j4, long j5, long j6) {
            this.index = i2;
            this.offset = i3;
            this.size = i4;
            this.presentationTimeStampMs = j2;
            this.timeStampMs = j3;
            this.ntpTimeStampMs = j4;
            this.decodeTimeMs = j5;
            this.endDecodeTimeMs = j6;
        }

        @CalledByNative("DecodedOutputBuffer")
        public long getDecodeTimeMs() {
            return this.decodeTimeMs;
        }

        @CalledByNative("DecodedOutputBuffer")
        public int getIndex() {
            return this.index;
        }

        @CalledByNative("DecodedOutputBuffer")
        public long getNtpTimestampMs() {
            return this.ntpTimeStampMs;
        }

        @CalledByNative("DecodedOutputBuffer")
        public int getOffset() {
            return this.offset;
        }

        @CalledByNative("DecodedOutputBuffer")
        public long getPresentationTimestampMs() {
            return this.presentationTimeStampMs;
        }

        @CalledByNative("DecodedOutputBuffer")
        public int getSize() {
            return this.size;
        }

        @CalledByNative("DecodedOutputBuffer")
        public long getTimestampMs() {
            return this.timeStampMs;
        }
    }

    public static class DecodedTextureBuffer {
        private final long decodeTimeMs;
        private final long frameDelayMs;
        private final long ntpTimeStampMs;
        private final long presentationTimeStampMs;
        private final int textureID;
        private final long timeStampMs;
        private final float[] transformMatrix;

        public DecodedTextureBuffer(int i2, float[] fArr, long j2, long j3, long j4, long j5, long j6) {
            this.textureID = i2;
            this.transformMatrix = fArr;
            this.presentationTimeStampMs = j2;
            this.timeStampMs = j3;
            this.ntpTimeStampMs = j4;
            this.decodeTimeMs = j5;
            this.frameDelayMs = j6;
        }

        @CalledByNative("DecodedTextureBuffer")
        public long getDecodeTimeMs() {
            return this.decodeTimeMs;
        }

        @CalledByNative("DecodedTextureBuffer")
        public long getFrameDelayMs() {
            return this.frameDelayMs;
        }

        @CalledByNative("DecodedTextureBuffer")
        public long getNtpTimestampMs() {
            return this.ntpTimeStampMs;
        }

        @CalledByNative("DecodedTextureBuffer")
        public long getPresentationTimestampMs() {
            return this.presentationTimeStampMs;
        }

        @CalledByNative("DecodedTextureBuffer")
        public int getTextureId() {
            return this.textureID;
        }

        @CalledByNative("DecodedTextureBuffer")
        public long getTimeStampMs() {
            return this.timeStampMs;
        }

        @CalledByNative("DecodedTextureBuffer")
        public float[] getTransformMatrix() {
            return this.transformMatrix;
        }
    }

    public static class DecoderProperties {
        public final String codecName;
        public final int colorFormat;

        public DecoderProperties(String str, int i2) {
            this.codecName = str;
            this.colorFormat = i2;
        }
    }

    public interface MediaCodecVideoDecoderErrorCallback {
        void onMediaCodecVideoDecoderCriticalError(int i2);
    }

    public static class TextureListener implements SurfaceTextureHelper.OnTextureFrameAvailableListener {
        private DecodedOutputBuffer bufferToRender;
        private final Object newFrameLock = new Object();
        private DecodedTextureBuffer renderedBuffer;
        private final SurfaceTextureHelper surfaceTextureHelper;

        public TextureListener(SurfaceTextureHelper surfaceTextureHelper) {
            this.surfaceTextureHelper = surfaceTextureHelper;
            surfaceTextureHelper.startListening(this);
        }

        public void addBufferToRender(DecodedOutputBuffer decodedOutputBuffer) {
            if (this.bufferToRender == null) {
                this.bufferToRender = decodedOutputBuffer;
            } else {
                Logging.e(MediaCodecVideoDecoder.TAG, "Unexpected addBufferToRender() called while waiting for a texture.");
                throw new IllegalStateException("Waiting for a texture.");
            }
        }

        public DecodedTextureBuffer dequeueTextureBuffer(int i2) {
            DecodedTextureBuffer decodedTextureBuffer;
            synchronized (this.newFrameLock) {
                if (this.renderedBuffer == null && i2 > 0 && isWaitingForTexture()) {
                    try {
                        this.newFrameLock.wait(i2);
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                    decodedTextureBuffer = this.renderedBuffer;
                    this.renderedBuffer = null;
                } else {
                    decodedTextureBuffer = this.renderedBuffer;
                    this.renderedBuffer = null;
                }
            }
            return decodedTextureBuffer;
        }

        public boolean isWaitingForTexture() {
            boolean z2;
            synchronized (this.newFrameLock) {
                z2 = this.bufferToRender != null;
            }
            return z2;
        }

        @Override // org.wrtca.api.SurfaceTextureHelper.OnTextureFrameAvailableListener
        public void onTextureFrameAvailable(int i2, float[] fArr, long j2) {
            synchronized (this.newFrameLock) {
                if (this.renderedBuffer != null) {
                    Logging.e(MediaCodecVideoDecoder.TAG, "Unexpected onTextureFrameAvailable() called while already holding a texture.");
                    throw new IllegalStateException("Already holding a texture.");
                }
                this.renderedBuffer = new DecodedTextureBuffer(i2, fArr, this.bufferToRender.presentationTimeStampMs, this.bufferToRender.timeStampMs, this.bufferToRender.ntpTimeStampMs, this.bufferToRender.decodeTimeMs, SystemClock.elapsedRealtime() - this.bufferToRender.endDecodeTimeMs);
                this.bufferToRender = null;
                this.newFrameLock.notifyAll();
            }
        }

        public void release() {
            this.surfaceTextureHelper.stopListening();
            synchronized (this.newFrameLock) {
                if (this.renderedBuffer != null) {
                    this.surfaceTextureHelper.returnTextureFrame();
                    this.renderedBuffer = null;
                }
            }
        }
    }

    public static class TimeStamps {
        private final long decodeStartTimeMs;
        private final long ntpTimeStampMs;
        private final long timeStampMs;

        public TimeStamps(long j2, long j3, long j4) {
            this.decodeStartTimeMs = j2;
            this.timeStampMs = j3;
            this.ntpTimeStampMs = j4;
        }
    }

    public enum VideoCodecType {
        VIDEO_CODEC_VP8,
        VIDEO_CODEC_VP9,
        VIDEO_CODEC_H264;

        @CalledByNative("VideoCodecType")
        public static VideoCodecType fromNativeIndex(int i2) {
            return values()[i2];
        }
    }

    @CalledByNative
    public MediaCodecVideoDecoder() {
    }

    private void MaybeRenderDecodedTextureBuffer() {
        if (this.dequeuedSurfaceOutputBuffers.isEmpty() || this.textureListener.isWaitingForTexture()) {
            return;
        }
        DecodedOutputBuffer decodedOutputBufferRemove = this.dequeuedSurfaceOutputBuffers.remove();
        this.textureListener.addBufferToRender(decodedOutputBufferRemove);
        this.mediaCodec.releaseOutputBuffer(decodedOutputBufferRemove.index, true);
    }

    private void checkOnMediaCodecThread() throws IllegalStateException {
        if (this.mediaCodecThread.getId() == Thread.currentThread().getId()) {
            return;
        }
        throw new IllegalStateException("MediaCodecVideoDecoder previously operated on " + this.mediaCodecThread + " but is now called on " + Thread.currentThread());
    }

    @CalledByNativeUnchecked
    private int dequeueInputBuffer() throws IllegalStateException {
        checkOnMediaCodecThread();
        try {
            return this.mediaCodec.dequeueInputBuffer(500000L);
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "dequeueIntputBuffer failed", e2);
            return -2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0130, code lost:
    
        throw new java.lang.RuntimeException("Unexpected size change. Configured " + r22.width + "*" + r22.height + ". New " + r8 + "*" + r7);
     */
    @org.wrtca.jni.CalledByNativeUnchecked
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.wrtca.api.MediaCodecVideoDecoder.DecodedOutputBuffer dequeueOutputBuffer(int r23) throws java.lang.IllegalStateException {
        /*
            Method dump skipped, instructions count: 520
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.wrtca.api.MediaCodecVideoDecoder.dequeueOutputBuffer(int):org.wrtca.api.MediaCodecVideoDecoder$DecodedOutputBuffer");
    }

    @CalledByNativeUnchecked
    private DecodedTextureBuffer dequeueTextureBuffer(int i2) throws IllegalStateException {
        checkOnMediaCodecThread();
        if (!this.useSurface) {
            throw new IllegalStateException("dequeueTexture() called for byte buffer decoding.");
        }
        DecodedOutputBuffer decodedOutputBufferDequeueOutputBuffer = dequeueOutputBuffer(i2);
        if (decodedOutputBufferDequeueOutputBuffer != null) {
            this.dequeuedSurfaceOutputBuffers.add(decodedOutputBufferDequeueOutputBuffer);
        }
        MaybeRenderDecodedTextureBuffer();
        DecodedTextureBuffer decodedTextureBufferDequeueTextureBuffer = this.textureListener.dequeueTextureBuffer(i2);
        if (decodedTextureBufferDequeueTextureBuffer != null) {
            MaybeRenderDecodedTextureBuffer();
            return decodedTextureBufferDequeueTextureBuffer;
        }
        if (this.dequeuedSurfaceOutputBuffers.size() < Math.min(3, this.outputBuffers.length) && (i2 <= 0 || this.dequeuedSurfaceOutputBuffers.isEmpty())) {
            return null;
        }
        this.droppedFrames++;
        DecodedOutputBuffer decodedOutputBufferRemove = this.dequeuedSurfaceOutputBuffers.remove();
        if (i2 > 0) {
            Logging.w(TAG, "Draining decoder. Dropping frame with TS: " + decodedOutputBufferRemove.presentationTimeStampMs + ". Total number of dropped frames: " + this.droppedFrames);
        } else {
            Logging.w(TAG, "Too many output buffers " + this.dequeuedSurfaceOutputBuffers.size() + ". Dropping frame with TS: " + decodedOutputBufferRemove.presentationTimeStampMs + ". Total number of dropped frames: " + this.droppedFrames);
        }
        this.mediaCodec.releaseOutputBuffer(decodedOutputBufferRemove.index, false);
        return new DecodedTextureBuffer(0, null, decodedOutputBufferRemove.presentationTimeStampMs, decodedOutputBufferRemove.timeStampMs, decodedOutputBufferRemove.ntpTimeStampMs, decodedOutputBufferRemove.decodeTimeMs, SystemClock.elapsedRealtime() - decodedOutputBufferRemove.endDecodeTimeMs);
    }

    public static void disableH264HwCodec() {
        Logging.w(TAG, "H.264 decoding is disabled by application.");
        hwDecoderDisabledTypes.add("video/avc");
    }

    public static void disableVp8HwCodec() {
        Logging.w(TAG, "VP8 decoding is disabled by application.");
        hwDecoderDisabledTypes.add("video/x-vnd.on2.vp8");
    }

    public static void disableVp9HwCodec() {
        Logging.w(TAG, "VP9 decoding is disabled by application.");
        hwDecoderDisabledTypes.add("video/x-vnd.on2.vp9");
    }

    private static DecoderProperties findDecoder(String str, String[] strArr) {
        MediaCodecInfo codecInfoAt;
        boolean z2;
        Logging.d(TAG, "Trying to find HW decoder for mime " + str);
        int i2 = 0;
        while (true) {
            String name = null;
            if (i2 >= MediaCodecList.getCodecCount()) {
                Logging.d(TAG, "No HW decoder found for mime " + str);
                return null;
            }
            try {
                codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
            } catch (IllegalArgumentException e2) {
                Logging.e(TAG, "Cannot retrieve decoder codec info", e2);
                codecInfoAt = null;
            }
            if (codecInfoAt != null && !codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                int length = supportedTypes.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        break;
                    }
                    if (supportedTypes[i3].equals(str)) {
                        name = codecInfoAt.getName();
                        break;
                    }
                    i3++;
                }
                if (name == null) {
                    continue;
                } else {
                    Logging.d(TAG, "Found candidate decoder " + name);
                    int length2 = strArr.length;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= length2) {
                            z2 = false;
                            break;
                        }
                        if (name.startsWith(strArr[i4])) {
                            z2 = true;
                            break;
                        }
                        i4++;
                    }
                    if (z2) {
                        try {
                            MediaCodecInfo.CodecCapabilities capabilitiesForType = codecInfoAt.getCapabilitiesForType(str);
                            c.h.a(TAG, "capabilities " + capabilitiesForType + "");
                            for (int i5 : capabilitiesForType.colorFormats) {
                                Logging.v(TAG, "   Color: 0x" + Integer.toHexString(i5));
                            }
                            Iterator<Integer> it = supportedColorList.iterator();
                            while (it.hasNext()) {
                                int iIntValue = it.next().intValue();
                                for (int i6 : capabilitiesForType.colorFormats) {
                                    if (i6 == iIntValue) {
                                        Logging.d(TAG, "Found target decoder " + name + ". Color: 0x" + Integer.toHexString(i6));
                                        return new DecoderProperties(name, i6);
                                    }
                                }
                            }
                        } catch (IllegalArgumentException e3) {
                            Logging.e(TAG, "Cannot retrieve decoder capabilities", e3);
                        }
                    } else {
                        continue;
                    }
                }
            }
            i2++;
        }
    }

    @CalledByNativeUnchecked
    private boolean initDecode(VideoCodecType videoCodecType, int i2, int i3, SurfaceTextureHelper surfaceTextureHelper) {
        String[] strArrSupportedH264HwCodecPrefixes;
        String str;
        if (this.mediaCodecThread != null) {
            throw new RuntimeException("initDecode: Forgot to release()?");
        }
        this.useSurface = surfaceTextureHelper != null;
        if (videoCodecType == VideoCodecType.VIDEO_CODEC_VP8) {
            strArrSupportedH264HwCodecPrefixes = supportedVp8HwCodecPrefixes();
            str = "video/x-vnd.on2.vp8";
        } else if (videoCodecType == VideoCodecType.VIDEO_CODEC_VP9) {
            strArrSupportedH264HwCodecPrefixes = supportedVp9HwCodecPrefixes;
            str = "video/x-vnd.on2.vp9";
        } else {
            if (videoCodecType != VideoCodecType.VIDEO_CODEC_H264) {
                throw new RuntimeException("initDecode: Non-supported codec " + videoCodecType);
            }
            strArrSupportedH264HwCodecPrefixes = supportedH264HwCodecPrefixes();
            str = "video/avc";
        }
        DecoderProperties decoderPropertiesFindDecoder = findDecoder(str, strArrSupportedH264HwCodecPrefixes);
        if (decoderPropertiesFindDecoder == null) {
            throw new RuntimeException("Cannot find HW decoder for " + videoCodecType);
        }
        Logging.d(TAG, "Java initDecode: " + videoCodecType + " : " + i2 + " x " + i3 + ". Color: 0x" + Integer.toHexString(decoderPropertiesFindDecoder.colorFormat) + ". Use Surface: " + this.useSurface);
        runningInstance = this;
        this.mediaCodecThread = Thread.currentThread();
        try {
            this.width = i2;
            this.height = i3;
            this.stride = i2;
            this.sliceHeight = i3;
            if (this.useSurface) {
                this.textureListener = new TextureListener(surfaceTextureHelper);
                this.surface = new Surface(surfaceTextureHelper.getSurfaceTexture());
            }
            MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat(str, i2, i3);
            if (!this.useSurface) {
                mediaFormatCreateVideoFormat.setInteger("color-format", decoderPropertiesFindDecoder.colorFormat);
            }
            Logging.d(TAG, "  Format: " + mediaFormatCreateVideoFormat);
            MediaCodec mediaCodecCreateByCodecName = MediaCodecVideoEncoder.createByCodecName(decoderPropertiesFindDecoder.codecName);
            this.mediaCodec = mediaCodecCreateByCodecName;
            if (mediaCodecCreateByCodecName == null) {
                Logging.e(TAG, "Can not create media decoder");
                return false;
            }
            mediaCodecCreateByCodecName.configure(mediaFormatCreateVideoFormat, this.surface, (MediaCrypto) null, 0);
            this.mediaCodec.start();
            this.colorFormat = decoderPropertiesFindDecoder.colorFormat;
            this.outputBuffers = this.mediaCodec.getOutputBuffers();
            this.inputBuffers = this.mediaCodec.getInputBuffers();
            this.decodeStartTimeMs.clear();
            this.hasDecodedFirstFrame = false;
            this.dequeuedSurfaceOutputBuffers.clear();
            this.droppedFrames = 0;
            Logging.d(TAG, "Input buffers: " + this.inputBuffers.length + ". Output buffers: " + this.outputBuffers.length);
            return true;
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "initDecode failed", e2);
            return false;
        }
    }

    @CalledByNative
    public static boolean isH264HighProfileHwSupported() {
        if (hwDecoderDisabledTypes.contains("video/avc")) {
            return false;
        }
        return (findDecoder("video/avc", new String[]{"OMX.qcom."}) == null && findDecoder("video/avc", new String[]{"OMX.Exynos."}) == null) ? false : true;
    }

    @CalledByNativeUnchecked
    public static boolean isH264HwSupported() {
        return (hwDecoderDisabledTypes.contains("video/avc") || findDecoder("video/avc", supportedH264HwCodecPrefixes()) == null) ? false : true;
    }

    @CalledByNativeUnchecked
    public static boolean isVp8HwSupported() {
        return (hwDecoderDisabledTypes.contains("video/x-vnd.on2.vp8") || findDecoder("video/x-vnd.on2.vp8", supportedVp8HwCodecPrefixes()) == null) ? false : true;
    }

    @CalledByNativeUnchecked
    public static boolean isVp9HwSupported() {
        return (hwDecoderDisabledTypes.contains("video/x-vnd.on2.vp9") || findDecoder("video/x-vnd.on2.vp9", supportedVp9HwCodecPrefixes) == null) ? false : true;
    }

    public static void printStackTrace() {
        Thread thread;
        MediaCodecVideoDecoder mediaCodecVideoDecoder = runningInstance;
        if (mediaCodecVideoDecoder == null || (thread = mediaCodecVideoDecoder.mediaCodecThread) == null) {
            return;
        }
        StackTraceElement[] stackTrace = thread.getStackTrace();
        if (stackTrace.length > 0) {
            Logging.d(TAG, "MediaCodecVideoDecoder stacks trace:");
            for (StackTraceElement stackTraceElement : stackTrace) {
                Logging.d(TAG, stackTraceElement.toString());
            }
        }
    }

    @CalledByNativeUnchecked
    private boolean queueInputBuffer(int i2, int i3, long j2, long j3, long j4) throws IllegalStateException, MediaCodec.CryptoException {
        checkOnMediaCodecThread();
        try {
            this.inputBuffers[i2].position(0);
            this.inputBuffers[i2].limit(i3);
            this.decodeStartTimeMs.add(new TimeStamps(SystemClock.elapsedRealtime(), j3, j4));
            this.mediaCodec.queueInputBuffer(i2, 0, i3, j2, 0);
            return true;
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "decode failed", e2);
            return false;
        }
    }

    @CalledByNativeUnchecked
    private void release() throws IllegalStateException {
        Logging.d(TAG, "Java releaseDecoder. Total number of dropped frames: " + this.droppedFrames);
        checkOnMediaCodecThread();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() { // from class: org.wrtca.api.MediaCodecVideoDecoder.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Logging.d(MediaCodecVideoDecoder.TAG, "Java releaseDecoder on release thread");
                    MediaCodecVideoDecoder.this.mediaCodec.stop();
                    MediaCodecVideoDecoder.this.mediaCodec.release();
                    Logging.d(MediaCodecVideoDecoder.TAG, "Java releaseDecoder on release thread done");
                } catch (Exception e2) {
                    Logging.e(MediaCodecVideoDecoder.TAG, "Media decoder release failed", e2);
                }
                countDownLatch.countDown();
            }
        }).start();
        if (!ThreadUtils.awaitUninterruptibly(countDownLatch, 5000L)) {
            Logging.e(TAG, "Media decoder release timeout");
            codecErrors++;
            if (errorCallback != null) {
                Logging.e(TAG, "Invoke codec error callback. Errors: " + codecErrors);
                errorCallback.onMediaCodecVideoDecoderCriticalError(codecErrors);
            }
        }
        this.mediaCodec = null;
        this.mediaCodecThread = null;
        runningInstance = null;
        if (this.useSurface) {
            this.surface.release();
            this.surface = null;
            this.textureListener.release();
        }
        Logging.d(TAG, "Java releaseDecoder done");
    }

    @CalledByNativeUnchecked
    private void reset(int i2, int i3) {
        if (this.mediaCodecThread == null || this.mediaCodec == null) {
            throw new RuntimeException("Incorrect reset call for non-initialized decoder.");
        }
        Logging.d(TAG, "Java reset: " + i2 + " x " + i3);
        this.mediaCodec.flush();
        this.width = i2;
        this.height = i3;
        this.decodeStartTimeMs.clear();
        this.dequeuedSurfaceOutputBuffers.clear();
        this.hasDecodedFirstFrame = false;
        this.droppedFrames = 0;
    }

    @CalledByNativeUnchecked
    private void returnDecodedOutputBuffer(int i2) throws IllegalStateException {
        checkOnMediaCodecThread();
        if (this.useSurface) {
            throw new IllegalStateException("returnDecodedOutputBuffer() called for surface decoding.");
        }
        this.mediaCodec.releaseOutputBuffer(i2, false);
    }

    public static void setErrorCallback(MediaCodecVideoDecoderErrorCallback mediaCodecVideoDecoderErrorCallback) {
        Logging.d(TAG, "Set error callback");
        errorCallback = mediaCodecVideoDecoderErrorCallback;
    }

    private static final String[] supportedH264HwCodecPrefixes() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("OMX.qcom.");
        arrayList.add(MediaCodecUtils.INTEL_PREFIX);
        arrayList.add("OMX.Exynos.");
        arrayList.add(MediaCodecUtils.GOOGLE_PREFIX);
        arrayList.add(MediaCodecUtils.HISI_PREFIX);
        arrayList.add(MediaCodecUtils.AMLOGIC_PREFIX);
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private static final String[] supportedVp8HwCodecPrefixes() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("OMX.qcom.");
        arrayList.add(MediaCodecUtils.NVIDIA_PREFIX);
        arrayList.add("OMX.Exynos.");
        arrayList.add(MediaCodecUtils.INTEL_PREFIX);
        if (PeerConnectionFactory.fieldTrialsFindFullName("WebRTC-MediaTekVP8").equals(PeerConnectionFactory.TRIAL_ENABLED) && Build.VERSION.SDK_INT >= 24) {
            arrayList.add(supportedMediaTekH264HighProfileHwCodecPrefix);
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    @CalledByNative
    public int getColorFormat() {
        return this.colorFormat;
    }

    @CalledByNative
    public int getHeight() {
        return this.height;
    }

    @CalledByNative
    public ByteBuffer[] getInputBuffers() {
        return this.inputBuffers;
    }

    @CalledByNative
    public ByteBuffer[] getOutputBuffers() {
        return this.outputBuffers;
    }

    @CalledByNative
    public int getSliceHeight() {
        return this.sliceHeight;
    }

    @CalledByNative
    public int getStride() {
        return this.stride;
    }

    @CalledByNative
    public int getWidth() {
        return this.width;
    }
}
