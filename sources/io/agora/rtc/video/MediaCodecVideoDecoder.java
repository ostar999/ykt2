package io.agora.rtc.video;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import io.agora.rtc.internal.Logging;
import io.agora.rtc.utils.ThreadUtils;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.wrtca.video.MediaCodecUtils;

/* loaded from: classes8.dex */
public class MediaCodecVideoDecoder {
    private static final int COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m = 2141391876;
    private static final int DEQUEUE_INPUT_TIMEOUT = 100000;
    private static final String H264_MIME_TYPE = "video/avc";
    private static final long MAX_DECODE_TIME_MS = 2000;
    private static final int MAX_QUEUED_OUTPUTBUFFERS = 3;
    private static final int MEDIA_CODEC_RELEASE_TIMEOUT_MS = 5000;
    private static final String TAG = "MediaCodecVideoDecoder";
    private static final String VP8_MIME_TYPE = "video/x-vnd.on2.vp8";
    private static final String VP9_MIME_TYPE = "video/x-vnd.on2.vp9";
    private static int codecErrors;
    private static MediaCodecVideoDecoderErrorCallback errorCallback;
    private static MediaCodecVideoDecoder runningInstance;
    private int colorFormat;
    private int cropHeight;
    private int cropWidth;
    private int droppedFrames;
    private boolean hasDecodedFirstFrame;
    private int height;
    private ByteBuffer[] inputBuffers;
    private MediaCodec mediaCodec;
    private Thread mediaCodecThread;
    private ByteBuffer[] outputBuffers;
    private int sliceHeight;
    private int stride;
    private boolean useSurface;
    private int width;
    private static Set<String> hwDecoderDisabledTypes = new HashSet();
    private static final String[] supportedVp8HwCodecPrefixes = {MediaCodecUtils.QCOM_PREFIX, MediaCodecUtils.NVIDIA_PREFIX, MediaCodecUtils.EXYNOS_PREFIX, MediaCodecUtils.INTEL_PREFIX};
    private static final String[] supportedVp9HwCodecPrefixes = {MediaCodecUtils.QCOM_PREFIX, MediaCodecUtils.EXYNOS_PREFIX};
    private static final String[] supportedH264HwCodecPrefixes = {MediaCodecUtils.QCOM_PREFIX, MediaCodecUtils.EXYNOS_PREFIX, "OMX.rk.", "OMX.sprd.", MediaCodecUtils.AMLOGIC_PREFIX, "OMX.IMG.TOPAZ.", "OMX.IMG.MSVDX.", MediaCodecUtils.HISI_PREFIX, "OMX.k3.", "OMX.allwinner.", "OMX.MTK.", MediaCodecUtils.NVIDIA_PREFIX, MediaCodecUtils.INTEL_PREFIX, "OMX.MS."};
    private static final List<Integer> supportedColorList = Arrays.asList(19, 21, 2141391872, 2141391876);
    private final Queue<TimeStamps> decodeStartTimeMs = new LinkedList();
    private Surface surface = null;
    private final Queue<DecodedOutputBuffer> dequeuedSurfaceOutputBuffers = new LinkedList();

    public static class DecodedOutputBuffer {
        private final long decodeTimeMs;
        private final long endDecodeTimeMs;
        private final int index;
        private final long ntpTimeStampMs;
        private final int offset;
        private final long presentationTimeStampMs;
        private final int size;
        private final long timeStampMs;

        public DecodedOutputBuffer(int index, int offset, int size, long presentationTimeStampMs, long timeStampMs, long ntpTimeStampMs, long decodeTime, long endDecodeTime) {
            this.index = index;
            this.offset = offset;
            this.size = size;
            this.presentationTimeStampMs = presentationTimeStampMs;
            this.timeStampMs = timeStampMs;
            this.ntpTimeStampMs = ntpTimeStampMs;
            this.decodeTimeMs = decodeTime;
            this.endDecodeTimeMs = endDecodeTime;
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

        public DecodedTextureBuffer(int textureID, float[] transformMatrix, long presentationTimeStampMs, long timeStampMs, long ntpTimeStampMs, long decodeTimeMs, long frameDelay) {
            this.textureID = textureID;
            this.transformMatrix = transformMatrix;
            this.presentationTimeStampMs = presentationTimeStampMs;
            this.timeStampMs = timeStampMs;
            this.ntpTimeStampMs = ntpTimeStampMs;
            this.decodeTimeMs = decodeTimeMs;
            this.frameDelayMs = frameDelay;
        }
    }

    public static class DecoderProperties {
        public final String codecName;
        public final int colorFormat;

        public DecoderProperties(String codecName, int colorFormat) {
            this.codecName = codecName;
            this.colorFormat = colorFormat;
        }
    }

    public interface MediaCodecVideoDecoderErrorCallback {
        void onMediaCodecVideoDecoderCriticalError(int codecErrors);
    }

    public class SurfaceTextureHelper {
        public SurfaceTextureHelper() {
        }
    }

    public static class TimeStamps {
        private final long decodeStartTimeMs;
        private final long ntpTimeStampMs;
        private final long timeStampMs;

        public TimeStamps(long decodeStartTimeMs, long timeStampMs, long ntpTimeStampMs) {
            this.decodeStartTimeMs = decodeStartTimeMs;
            this.timeStampMs = timeStampMs;
            this.ntpTimeStampMs = ntpTimeStampMs;
        }
    }

    public enum VideoCodecType {
        VIDEO_CODEC_VP8,
        VIDEO_CODEC_VP9,
        VIDEO_CODEC_H264
    }

    private void checkOnMediaCodecThread() throws IllegalStateException {
    }

    private int dequeueInputBuffer() throws IllegalStateException {
        checkOnMediaCodecThread();
        try {
            return this.mediaCodec.dequeueInputBuffer(100000L);
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "dequeueIntputBuffer failed", e2);
            return -2;
        }
    }

    private DecodedOutputBuffer dequeueOutputBuffer(int dequeueTimeoutMs) throws IllegalStateException {
        long j2;
        checkOnMediaCodecThread();
        if (this.decodeStartTimeMs.isEmpty()) {
            return null;
        }
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        while (true) {
            int iDequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, TimeUnit.MILLISECONDS.toMicros(dequeueTimeoutMs));
            if (iDequeueOutputBuffer == -3) {
                this.outputBuffers = this.mediaCodec.getOutputBuffers();
                Logging.i(TAG, "Decoder output buffers changed: " + this.outputBuffers.length);
                if (this.hasDecodedFirstFrame) {
                    throw new RuntimeException("Unexpected output buffer change event.");
                }
            } else {
                if (iDequeueOutputBuffer != -2) {
                    if (iDequeueOutputBuffer == -1) {
                        return null;
                    }
                    this.hasDecodedFirstFrame = true;
                    TimeStamps timeStampsRemove = this.decodeStartTimeMs.remove();
                    long jElapsedRealtime = SystemClock.elapsedRealtime() - timeStampsRemove.decodeStartTimeMs;
                    if (jElapsedRealtime > 2000) {
                        Logging.w(TAG, "Very high decode time: " + jElapsedRealtime + "ms.");
                        j2 = 2000L;
                    } else {
                        j2 = jElapsedRealtime;
                    }
                    return new DecodedOutputBuffer(iDequeueOutputBuffer, bufferInfo.offset, bufferInfo.size, TimeUnit.MICROSECONDS.toMillis(bufferInfo.presentationTimeUs), timeStampsRemove.timeStampMs, timeStampsRemove.ntpTimeStampMs, j2, SystemClock.elapsedRealtime());
                }
                MediaFormat outputFormat = this.mediaCodec.getOutputFormat();
                Logging.i(TAG, "Decoder format changed: " + outputFormat.toString());
                int integer = outputFormat.getInteger("width");
                int integer2 = outputFormat.getInteger("height");
                if (this.hasDecodedFirstFrame && (integer != this.width || integer2 != this.height)) {
                    Logging.w(TAG, "Decoder format changed. Configured " + this.width + "*" + this.height + ". New " + integer + "*" + integer2);
                }
                this.width = outputFormat.getInteger("width");
                this.height = outputFormat.getInteger("height");
                if (outputFormat.containsKey("stride")) {
                    this.stride = outputFormat.getInteger("stride");
                }
                if (outputFormat.containsKey("slice-height")) {
                    this.sliceHeight = outputFormat.getInteger("slice-height");
                }
                if (outputFormat.containsKey("crop-left") && outputFormat.containsKey("crop-right")) {
                    this.cropWidth = (outputFormat.getInteger("crop-right") - outputFormat.getInteger("crop-left")) + 1;
                } else {
                    this.cropWidth = this.width;
                }
                if (outputFormat.containsKey("crop-bottom") && outputFormat.containsKey("crop-top")) {
                    this.cropHeight = (outputFormat.getInteger("crop-bottom") - outputFormat.getInteger("crop-top")) + 1;
                } else {
                    this.cropHeight = this.height;
                }
                Logging.i(TAG, "Frame stride and slice height: " + this.stride + " x " + this.sliceHeight);
                Logging.i(TAG, "Crop width and height: " + this.cropWidth + " x " + this.cropHeight);
                this.stride = Math.max(this.width, this.stride);
                this.sliceHeight = Math.max(this.height, this.sliceHeight);
            }
        }
    }

    public static void disableH264HwCodec() {
        Logging.w(TAG, "H.264 decoding is disabled by application.");
        hwDecoderDisabledTypes.add("video/avc");
    }

    public static void disableVp8HwCodec() {
        Log.w(TAG, "VP8 decoding is disabled by application.");
        hwDecoderDisabledTypes.add("video/x-vnd.on2.vp8");
    }

    public static void disableVp9HwCodec() {
        Logging.w(TAG, "VP9 decoding is disabled by application.");
        hwDecoderDisabledTypes.add("video/x-vnd.on2.vp9");
    }

    private static DecoderProperties findDecoder(String mime, String[] supportedCodecPrefixes) {
        MediaCodecInfo codecInfoAt;
        boolean z2;
        Logging.i(TAG, "Trying to find HW decoder for mime " + mime);
        int i2 = 0;
        while (true) {
            String name = null;
            if (i2 >= MediaCodecList.getCodecCount()) {
                Logging.d(TAG, "No HW decoder found for mime " + mime);
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
                    if (supportedTypes[i3].equals(mime)) {
                        name = codecInfoAt.getName();
                        break;
                    }
                    i3++;
                }
                if (name == null) {
                    continue;
                } else {
                    Logging.i(TAG, "Found candidate decoder " + name);
                    int length2 = supportedCodecPrefixes.length;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= length2) {
                            z2 = false;
                            break;
                        }
                        if (name.startsWith(supportedCodecPrefixes[i4])) {
                            z2 = true;
                            break;
                        }
                        i4++;
                    }
                    if (z2) {
                        MediaCodecInfo.CodecCapabilities capabilitiesForType = codecInfoAt.getCapabilitiesForType(mime);
                        for (int i5 : capabilitiesForType.colorFormats) {
                            Logging.d(TAG, "   Color: 0x" + Integer.toHexString(i5));
                        }
                        if (name.startsWith("OMX.rk.")) {
                            return new DecoderProperties(name, 21);
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
                    } else {
                        continue;
                    }
                }
            }
            i2++;
        }
    }

    private boolean initDecode(int codec, int width, int height, SurfaceTextureHelper surfaceTextureHelper) {
        String[] strArr;
        String str;
        if (this.mediaCodecThread != null) {
            throw new RuntimeException("initDecode: Forgot to release()?");
        }
        this.useSurface = surfaceTextureHelper != null;
        VideoCodecType videoCodecType = VideoCodecType.values()[codec];
        if (videoCodecType == VideoCodecType.VIDEO_CODEC_VP8) {
            strArr = supportedVp8HwCodecPrefixes;
            str = "video/x-vnd.on2.vp8";
        } else if (videoCodecType == VideoCodecType.VIDEO_CODEC_VP9) {
            strArr = supportedVp9HwCodecPrefixes;
            str = "video/x-vnd.on2.vp9";
        } else {
            if (videoCodecType != VideoCodecType.VIDEO_CODEC_H264) {
                throw new RuntimeException("initDecode: Non-supported codec " + videoCodecType);
            }
            strArr = supportedH264HwCodecPrefixes;
            str = "video/avc";
        }
        DecoderProperties decoderPropertiesFindDecoder = findDecoder(str, strArr);
        if (decoderPropertiesFindDecoder == null) {
            throw new RuntimeException("Cannot find HW decoder for " + videoCodecType);
        }
        Logging.d(TAG, "Java initDecode: " + videoCodecType + " : " + width + " x " + height + ". Color: 0x" + Integer.toHexString(decoderPropertiesFindDecoder.colorFormat) + ". Use Surface: " + this.useSurface);
        runningInstance = this;
        this.mediaCodecThread = Thread.currentThread();
        try {
            this.width = width;
            this.height = height;
            this.stride = width;
            this.sliceHeight = height;
            this.cropWidth = width;
            this.cropHeight = height;
            MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat(str, width, height);
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
            Logging.i(TAG, "Input buffers: " + this.inputBuffers.length + ". Output buffers: " + this.outputBuffers.length);
            return true;
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "initDecode failed", e2);
            return false;
        }
    }

    public static boolean isH264HwSupported() {
        return (hwDecoderDisabledTypes.contains("video/avc") || findDecoder("video/avc", supportedH264HwCodecPrefixes) == null) ? false : true;
    }

    public static boolean isVp8HwSupported() {
        return (hwDecoderDisabledTypes.contains("video/x-vnd.on2.vp8") || findDecoder("video/x-vnd.on2.vp8", supportedVp8HwCodecPrefixes) == null) ? false : true;
    }

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

    private boolean queueInputBuffer(int inputBufferIndex, int size, long presentationTimeStamUs, long timeStampMs, long ntpTimeStamp) throws IllegalStateException, MediaCodec.CryptoException {
        checkOnMediaCodecThread();
        try {
            this.inputBuffers[inputBufferIndex].position(0);
            this.inputBuffers[inputBufferIndex].limit(size);
            this.decodeStartTimeMs.add(new TimeStamps(SystemClock.elapsedRealtime(), timeStampMs, ntpTimeStamp));
            this.mediaCodec.queueInputBuffer(inputBufferIndex, 0, size, presentationTimeStamUs, 0);
            return true;
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "decode failed", e2);
            return false;
        }
    }

    private void release() throws IllegalStateException {
        Logging.i(TAG, "Java releaseDecoder. Total number of dropped frames: " + this.droppedFrames);
        checkOnMediaCodecThread();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() { // from class: io.agora.rtc.video.MediaCodecVideoDecoder.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Logging.i(MediaCodecVideoDecoder.TAG, "Java releaseDecoder on release thread");
                    MediaCodecVideoDecoder.this.mediaCodec.stop();
                    MediaCodecVideoDecoder.this.mediaCodec.release();
                    Logging.i(MediaCodecVideoDecoder.TAG, "Java releaseDecoder on release thread done");
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
        Logging.d(TAG, "Java releaseDecoder done");
    }

    private void reset(int width, int height) {
        if (this.mediaCodecThread == null || this.mediaCodec == null) {
            throw new RuntimeException("Incorrect reset call for non-initialized decoder.");
        }
        Logging.i(TAG, "Java reset: " + width + " x " + height);
        this.mediaCodec.flush();
        this.width = width;
        this.height = height;
        this.decodeStartTimeMs.clear();
        this.dequeuedSurfaceOutputBuffers.clear();
        this.hasDecodedFirstFrame = false;
        this.droppedFrames = 0;
    }

    private void returnDecodedOutputBuffer(int index) throws IllegalStateException {
        checkOnMediaCodecThread();
        if (this.useSurface) {
            throw new IllegalStateException("returnDecodedOutputBuffer() called for surface decoding.");
        }
        this.mediaCodec.releaseOutputBuffer(index, false);
    }

    public static void setErrorCallback(MediaCodecVideoDecoderErrorCallback errorCallback2) {
        errorCallback = errorCallback2;
    }
}
