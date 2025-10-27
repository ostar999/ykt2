package org.wrtca.api;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.wrtca.api.VideoFrame;
import org.wrtca.gl.EglBase14;
import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.CalledByNativeUnchecked;
import org.wrtca.jni.JNINamespace;
import org.wrtca.log.Logging;
import org.wrtca.util.ThreadUtils;
import org.wrtca.video.MediaCodecUtils;

@JNINamespace("webrtc::jni")
@TargetApi(19)
/* loaded from: classes9.dex */
public class MediaCodecVideoEncoder {
    private static final int BITRATE_ADJUSTMENT_FPS = 30;
    private static final double BITRATE_CORRECTION_MAX_SCALE = 4.0d;
    private static final double BITRATE_CORRECTION_SEC = 3.0d;
    private static final int BITRATE_CORRECTION_STEPS = 20;
    private static final int COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m = 2141391876;
    private static final int DEQUEUE_TIMEOUT = 0;
    private static final String[] H264_HW_EXCEPTION_MODELS;
    private static final String H264_MIME_TYPE = "video/avc";
    private static final int MAXIMUM_INITIAL_FPS = 30;
    private static final int MEDIA_CODEC_RELEASE_TIMEOUT_MS = 5000;
    private static final long QCOM_VP8_KEY_FRAME_INTERVAL_ANDROID_L_MS = 15000;
    private static final long QCOM_VP8_KEY_FRAME_INTERVAL_ANDROID_M_MS = 20000;
    private static final long QCOM_VP8_KEY_FRAME_INTERVAL_ANDROID_N_MS = 15000;
    private static final String TAG = "MediaCodecVideoEncoder";
    private static final int VIDEO_AVCLevel3 = 256;
    private static final int VIDEO_AVCProfileHigh = 8;
    private static final int VIDEO_ControlRateConstant = 2;
    private static final String VP8_MIME_TYPE = "video/x-vnd.on2.vp8";
    private static final String VP9_MIME_TYPE = "video/x-vnd.on2.vp9";
    private static final MediaCodecProperties amlogicH264HwProperties;
    private static int codecErrors;
    private static MediaCodecVideoEncoderErrorCallback errorCallback;
    private static final MediaCodecProperties exynosH264HighProfileHwProperties;
    private static final MediaCodecProperties exynosH264HwProperties;
    private static final MediaCodecProperties exynosVp8HwProperties;
    private static final MediaCodecProperties exynosVp9HwProperties;
    private static final MediaCodecProperties googleH264HwProperties;
    private static final MediaCodecProperties[] h264HighProfileHwList;
    private static final MediaCodecProperties hisiH264HwProperties;
    private static Set<String> hwEncoderDisabledTypes = new HashSet();
    private static final MediaCodecProperties intelVp8HwProperties;
    private static final MediaCodecProperties qcomH264HwProperties;
    private static final MediaCodecProperties qcomVp8HwProperties;
    private static final MediaCodecProperties qcomVp9HwProperties;
    private static MediaCodecVideoEncoder runningInstance;
    private static final int[] supportedColorList;
    private static final int[] supportedSurfaceColorList;
    private static final MediaCodecProperties[] vp9HwList;
    private double bitrateAccumulator;
    private double bitrateAccumulatorMax;
    private int bitrateAdjustmentScaleExp;
    private double bitrateObservationTimeMs;
    private int colorFormat;
    private GlRectDrawer drawer;
    private EglBase14 eglBase;
    private long forcedKeyFrameMs;
    private int height;
    private Surface inputSurface;
    private long lastKeyFrameMs;
    private MediaCodec mediaCodec;
    private Thread mediaCodecThread;
    private ByteBuffer[] outputBuffers;
    private int profile;
    private int targetBitrateBps;
    private int targetFps;
    private VideoCodecType type;
    private int width;
    private BitrateAdjustmentType bitrateAdjustmentType = BitrateAdjustmentType.NO_ADJUSTMENT;
    private ByteBuffer configData = null;

    /* renamed from: org.wrtca.api.MediaCodecVideoEncoder$1CaughtException, reason: invalid class name */
    public class C1CaughtException {

        /* renamed from: e, reason: collision with root package name */
        public Exception f28095e;

        public C1CaughtException() {
        }
    }

    public enum BitrateAdjustmentType {
        NO_ADJUSTMENT,
        FRAMERATE_ADJUSTMENT,
        DYNAMIC_ADJUSTMENT
    }

    public static class EncoderProperties {
        public final BitrateAdjustmentType bitrateAdjustmentType;
        public final String codecName;
        public final int colorFormat;

        public EncoderProperties(String str, int i2, BitrateAdjustmentType bitrateAdjustmentType) {
            this.codecName = str;
            this.colorFormat = i2;
            this.bitrateAdjustmentType = bitrateAdjustmentType;
        }
    }

    public enum H264Profile {
        CONSTRAINED_BASELINE(0),
        BASELINE(1),
        MAIN(2),
        CONSTRAINED_HIGH(3),
        HIGH(4);

        private final int value;

        H264Profile(int i2) {
            this.value = i2;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static class MediaCodecProperties {
        public final BitrateAdjustmentType bitrateAdjustmentType;
        public final String codecPrefix;
        public final int minSdk;

        public MediaCodecProperties(String str, int i2, BitrateAdjustmentType bitrateAdjustmentType) {
            this.codecPrefix = str;
            this.minSdk = i2;
            this.bitrateAdjustmentType = bitrateAdjustmentType;
        }
    }

    public interface MediaCodecVideoEncoderErrorCallback {
        void onMediaCodecVideoEncoderCriticalError(int i2);
    }

    public static class OutputBufferInfo {
        public final ByteBuffer buffer;
        public final int index;
        public final boolean isKeyFrame;
        public final long presentationTimestampUs;

        public OutputBufferInfo(int i2, ByteBuffer byteBuffer, boolean z2, long j2) {
            this.index = i2;
            this.buffer = byteBuffer;
            this.isKeyFrame = z2;
            this.presentationTimestampUs = j2;
        }

        @CalledByNative("OutputBufferInfo")
        public ByteBuffer getBuffer() {
            return this.buffer;
        }

        @CalledByNative("OutputBufferInfo")
        public int getIndex() {
            return this.index;
        }

        @CalledByNative("OutputBufferInfo")
        public long getPresentationTimestampUs() {
            return this.presentationTimestampUs;
        }

        @CalledByNative("OutputBufferInfo")
        public boolean isKeyFrame() {
            return this.isKeyFrame;
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

    static {
        BitrateAdjustmentType bitrateAdjustmentType = BitrateAdjustmentType.NO_ADJUSTMENT;
        qcomVp8HwProperties = new MediaCodecProperties(MediaCodecUtils.QCOM_PREFIX, 19, bitrateAdjustmentType);
        exynosVp8HwProperties = new MediaCodecProperties(MediaCodecUtils.EXYNOS_PREFIX, 23, BitrateAdjustmentType.DYNAMIC_ADJUSTMENT);
        intelVp8HwProperties = new MediaCodecProperties(MediaCodecUtils.INTEL_PREFIX, 21, bitrateAdjustmentType);
        MediaCodecProperties mediaCodecProperties = new MediaCodecProperties(MediaCodecUtils.QCOM_PREFIX, 24, bitrateAdjustmentType);
        qcomVp9HwProperties = mediaCodecProperties;
        BitrateAdjustmentType bitrateAdjustmentType2 = BitrateAdjustmentType.FRAMERATE_ADJUSTMENT;
        MediaCodecProperties mediaCodecProperties2 = new MediaCodecProperties(MediaCodecUtils.EXYNOS_PREFIX, 24, bitrateAdjustmentType2);
        exynosVp9HwProperties = mediaCodecProperties2;
        vp9HwList = new MediaCodecProperties[]{mediaCodecProperties, mediaCodecProperties2};
        qcomH264HwProperties = new MediaCodecProperties(MediaCodecUtils.QCOM_PREFIX, 19, bitrateAdjustmentType);
        exynosH264HwProperties = new MediaCodecProperties(MediaCodecUtils.EXYNOS_PREFIX, 21, bitrateAdjustmentType2);
        hisiH264HwProperties = new MediaCodecProperties(MediaCodecUtils.HISI_PREFIX, 21, bitrateAdjustmentType2);
        googleH264HwProperties = new MediaCodecProperties(MediaCodecUtils.GOOGLE_PREFIX, 21, bitrateAdjustmentType2);
        amlogicH264HwProperties = new MediaCodecProperties(MediaCodecUtils.AMLOGIC_PREFIX, 19, bitrateAdjustmentType2);
        MediaCodecProperties mediaCodecProperties3 = new MediaCodecProperties(MediaCodecUtils.EXYNOS_PREFIX, 23, bitrateAdjustmentType2);
        exynosH264HighProfileHwProperties = mediaCodecProperties3;
        h264HighProfileHwList = new MediaCodecProperties[]{mediaCodecProperties3};
        H264_HW_EXCEPTION_MODELS = new String[]{"SAMSUNG-SGH-I337", "Nexus 7", "Nexus 4"};
        supportedColorList = new int[]{19, 21, 2141391872, 2141391876};
        supportedSurfaceColorList = new int[]{2130708361};
    }

    @CalledByNative
    public MediaCodecVideoEncoder() {
    }

    private void checkOnMediaCodecThread() {
        if (this.mediaCodecThread.getId() == Thread.currentThread().getId()) {
            return;
        }
        throw new RuntimeException("MediaCodecVideoEncoder previously operated on " + this.mediaCodecThread + " but is now called on " + Thread.currentThread());
    }

    public static MediaCodec createByCodecName(String str) {
        try {
            return MediaCodec.createByCodecName(str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void disableH264HwCodec() {
        Logging.w(TAG, "H.264 encoding is disabled by application.");
        hwEncoderDisabledTypes.add("video/avc");
    }

    public static void disableVp8HwCodec() {
        Logging.w(TAG, "VP8 encoding is disabled by application.");
        hwEncoderDisabledTypes.add("video/x-vnd.on2.vp8");
    }

    public static void disableVp9HwCodec() {
        Logging.w(TAG, "VP9 encoding is disabled by application.");
        hwEncoderDisabledTypes.add("video/x-vnd.on2.vp9");
    }

    private static EncoderProperties findHwEncoder(String str, MediaCodecProperties[] mediaCodecPropertiesArr, int[] iArr) {
        MediaCodecInfo codecInfoAt;
        String name;
        boolean z2;
        c.h.a(TAG, "findHwEncoder supportedHwCodecProperties: " + mediaCodecPropertiesArr);
        if (str.equals("video/avc")) {
            List listAsList = Arrays.asList(H264_HW_EXCEPTION_MODELS);
            String str2 = Build.MODEL;
            if (listAsList.contains(str2)) {
                Logging.w(TAG, "Model: " + str2 + " has black listed H.264 encoder.");
                return null;
            }
        }
        for (int i2 = 0; i2 < MediaCodecList.getCodecCount(); i2++) {
            try {
                codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
            } catch (IllegalArgumentException e2) {
                Logging.e(TAG, "Cannot retrieve encoder codec info", e2);
                codecInfoAt = null;
            }
            if (codecInfoAt != null && codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                int length = supportedTypes.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        name = null;
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
                    Logging.d(TAG, "Found candidate encoder " + name);
                    BitrateAdjustmentType bitrateAdjustmentType = BitrateAdjustmentType.NO_ADJUSTMENT;
                    int length2 = mediaCodecPropertiesArr.length;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= length2) {
                            z2 = false;
                            break;
                        }
                        MediaCodecProperties mediaCodecProperties = mediaCodecPropertiesArr[i4];
                        if (name.startsWith(mediaCodecProperties.codecPrefix)) {
                            int i5 = Build.VERSION.SDK_INT;
                            if (i5 < mediaCodecProperties.minSdk) {
                                Logging.w(TAG, "Codec " + name + " is disabled due to SDK version " + i5);
                            } else {
                                BitrateAdjustmentType bitrateAdjustmentType2 = mediaCodecProperties.bitrateAdjustmentType;
                                if (bitrateAdjustmentType2 != BitrateAdjustmentType.NO_ADJUSTMENT) {
                                    Logging.w(TAG, "Codec " + name + " requires bitrate adjustment: " + bitrateAdjustmentType2);
                                    bitrateAdjustmentType = bitrateAdjustmentType2;
                                }
                                z2 = true;
                            }
                        }
                        i4++;
                    }
                    if (z2) {
                        try {
                            MediaCodecInfo.CodecCapabilities capabilitiesForType = codecInfoAt.getCapabilitiesForType(str);
                            for (int i6 : capabilitiesForType.colorFormats) {
                                Logging.v(TAG, "   Color: 0x" + Integer.toHexString(i6));
                            }
                            for (int i7 : iArr) {
                                for (int i8 : capabilitiesForType.colorFormats) {
                                    if (i8 == i7) {
                                        Logging.d(TAG, "Found target encoder for mime " + str + " : " + name + ". Color: 0x" + Integer.toHexString(i8) + ". Bitrate adjustment: " + bitrateAdjustmentType);
                                        return new EncoderProperties(name, i8, bitrateAdjustmentType);
                                    }
                                }
                            }
                        } catch (IllegalArgumentException e3) {
                            Logging.e(TAG, "Cannot retrieve encoder capabilities", e3);
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        Logging.d(TAG, "No HW encoder found for mime " + str);
        return null;
    }

    private double getBitrateScale(int i2) {
        return Math.pow(BITRATE_CORRECTION_MAX_SCALE, i2 / 20.0d);
    }

    private static final MediaCodecProperties[] h264HwList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(qcomH264HwProperties);
        arrayList.add(exynosH264HwProperties);
        arrayList.add(hisiH264HwProperties);
        arrayList.add(googleH264HwProperties);
        arrayList.add(amlogicH264HwProperties);
        return (MediaCodecProperties[]) arrayList.toArray(new MediaCodecProperties[arrayList.size()]);
    }

    public static boolean isH264HighProfileHwSupported() {
        return (hwEncoderDisabledTypes.contains("video/avc") || findHwEncoder("video/avc", h264HighProfileHwList, supportedColorList) == null) ? false : true;
    }

    @CalledByNative
    public static boolean isH264HwSupported() {
        return (hwEncoderDisabledTypes.contains("video/avc") || findHwEncoder("video/avc", h264HwList(), supportedColorList) == null) ? false : true;
    }

    public static boolean isH264HwSupportedUsingTextures() {
        return (hwEncoderDisabledTypes.contains("video/avc") || findHwEncoder("video/avc", h264HwList(), supportedSurfaceColorList) == null) ? false : true;
    }

    @CalledByNative
    public static boolean isTextureBuffer(VideoFrame.Buffer buffer) {
        return buffer instanceof VideoFrame.TextureBuffer;
    }

    @CalledByNative
    public static boolean isVp8HwSupported() {
        return (hwEncoderDisabledTypes.contains("video/x-vnd.on2.vp8") || findHwEncoder("video/x-vnd.on2.vp8", vp8HwList(), supportedColorList) == null) ? false : true;
    }

    public static boolean isVp8HwSupportedUsingTextures() {
        return (hwEncoderDisabledTypes.contains("video/x-vnd.on2.vp8") || findHwEncoder("video/x-vnd.on2.vp8", vp8HwList(), supportedSurfaceColorList) == null) ? false : true;
    }

    @CalledByNative
    public static boolean isVp9HwSupported() {
        return (hwEncoderDisabledTypes.contains("video/x-vnd.on2.vp9") || findHwEncoder("video/x-vnd.on2.vp9", vp9HwList, supportedColorList) == null) ? false : true;
    }

    public static boolean isVp9HwSupportedUsingTextures() {
        return (hwEncoderDisabledTypes.contains("video/x-vnd.on2.vp9") || findHwEncoder("video/x-vnd.on2.vp9", vp9HwList, supportedSurfaceColorList) == null) ? false : true;
    }

    private static native void nativeFillInputBuffer(long j2, int i2, ByteBuffer byteBuffer, int i3, ByteBuffer byteBuffer2, int i4, ByteBuffer byteBuffer3, int i5);

    public static void printStackTrace() {
        Thread thread;
        MediaCodecVideoEncoder mediaCodecVideoEncoder = runningInstance;
        if (mediaCodecVideoEncoder == null || (thread = mediaCodecVideoEncoder.mediaCodecThread) == null) {
            return;
        }
        StackTraceElement[] stackTrace = thread.getStackTrace();
        if (stackTrace.length > 0) {
            Logging.d(TAG, "MediaCodecVideoEncoder stacks trace:");
            for (StackTraceElement stackTraceElement : stackTrace) {
                Logging.d(TAG, stackTraceElement.toString());
            }
        }
    }

    private void reportEncodedFrame(int i2) {
        int i3 = this.targetFps;
        if (i3 == 0 || this.bitrateAdjustmentType != BitrateAdjustmentType.DYNAMIC_ADJUSTMENT) {
            return;
        }
        double d3 = i3;
        double d4 = this.bitrateAccumulator + (i2 - (this.targetBitrateBps / (8.0d * d3)));
        this.bitrateAccumulator = d4;
        this.bitrateObservationTimeMs += 1000.0d / d3;
        double d5 = this.bitrateAccumulatorMax * BITRATE_CORRECTION_SEC;
        double dMin = Math.min(d4, d5);
        this.bitrateAccumulator = dMin;
        this.bitrateAccumulator = Math.max(dMin, -d5);
        if (this.bitrateObservationTimeMs > 3000.0d) {
            Logging.d(TAG, "Acc: " + ((int) this.bitrateAccumulator) + ". Max: " + ((int) this.bitrateAccumulatorMax) + ". ExpScale: " + this.bitrateAdjustmentScaleExp);
            double d6 = this.bitrateAccumulator;
            double d7 = this.bitrateAccumulatorMax;
            boolean z2 = true;
            if (d6 > d7) {
                this.bitrateAdjustmentScaleExp -= (int) ((d6 / d7) + 0.5d);
                this.bitrateAccumulator = d7;
            } else {
                double d8 = -d7;
                if (d6 < d8) {
                    this.bitrateAdjustmentScaleExp += (int) (((-d6) / d7) + 0.5d);
                    this.bitrateAccumulator = d8;
                } else {
                    z2 = false;
                }
            }
            if (z2) {
                int iMin = Math.min(this.bitrateAdjustmentScaleExp, 20);
                this.bitrateAdjustmentScaleExp = iMin;
                this.bitrateAdjustmentScaleExp = Math.max(iMin, -20);
                Logging.d(TAG, "Adjusting bitrate scale to " + this.bitrateAdjustmentScaleExp + ". Value: " + getBitrateScale(this.bitrateAdjustmentScaleExp));
                setRates(this.targetBitrateBps / 1000, this.targetFps);
            }
            this.bitrateObservationTimeMs = 0.0d;
        }
    }

    public static void setErrorCallback(MediaCodecVideoEncoderErrorCallback mediaCodecVideoEncoderErrorCallback) {
        Logging.d(TAG, "Set error callback");
        errorCallback = mediaCodecVideoEncoderErrorCallback;
    }

    @CalledByNativeUnchecked
    private boolean setRates(int i2, int i3) {
        checkOnMediaCodecThread();
        int bitrateScale = i2 * 1000;
        BitrateAdjustmentType bitrateAdjustmentType = this.bitrateAdjustmentType;
        BitrateAdjustmentType bitrateAdjustmentType2 = BitrateAdjustmentType.DYNAMIC_ADJUSTMENT;
        if (bitrateAdjustmentType == bitrateAdjustmentType2) {
            double d3 = bitrateScale;
            this.bitrateAccumulatorMax = d3 / 8.0d;
            int i4 = this.targetBitrateBps;
            if (i4 > 0 && bitrateScale < i4) {
                this.bitrateAccumulator = (this.bitrateAccumulator * d3) / i4;
            }
        }
        this.targetBitrateBps = bitrateScale;
        this.targetFps = i3;
        if (bitrateAdjustmentType == BitrateAdjustmentType.FRAMERATE_ADJUSTMENT && i3 > 0) {
            bitrateScale = (bitrateScale * 30) / i3;
            Logging.v(TAG, "setRates: " + i2 + " -> " + (bitrateScale / 1000) + " kbps. Fps: " + this.targetFps);
        } else if (bitrateAdjustmentType == bitrateAdjustmentType2) {
            Logging.v(TAG, "setRates: " + i2 + " kbps. Fps: " + this.targetFps + ". ExpScale: " + this.bitrateAdjustmentScaleExp);
            int i5 = this.bitrateAdjustmentScaleExp;
            if (i5 != 0) {
                bitrateScale = (int) (bitrateScale * getBitrateScale(i5));
            }
        } else {
            Logging.v(TAG, "setRates: " + i2 + " kbps. Fps: " + this.targetFps);
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("video-bitrate", bitrateScale);
            this.mediaCodec.setParameters(bundle);
            return true;
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "setRates failed", e2);
            return false;
        }
    }

    public static EncoderProperties vp8HwEncoderProperties() {
        if (hwEncoderDisabledTypes.contains("video/x-vnd.on2.vp8")) {
            return null;
        }
        return findHwEncoder("video/x-vnd.on2.vp8", vp8HwList(), supportedColorList);
    }

    private static MediaCodecProperties[] vp8HwList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(qcomVp8HwProperties);
        arrayList.add(exynosVp8HwProperties);
        if (PeerConnectionFactory.fieldTrialsFindFullName("WebRTC-IntelVP8").equals(PeerConnectionFactory.TRIAL_ENABLED)) {
            arrayList.add(intelVp8HwProperties);
        }
        return (MediaCodecProperties[]) arrayList.toArray(new MediaCodecProperties[arrayList.size()]);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void checkKeyFrameRequired(boolean r7, long r8) {
        /*
            r6 = this;
            r0 = 500(0x1f4, double:2.47E-321)
            long r8 = r8 + r0
            r0 = 1000(0x3e8, double:4.94E-321)
            long r8 = r8 / r0
            long r0 = r6.lastKeyFrameMs
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L10
            r6.lastKeyFrameMs = r8
        L10:
            r0 = 0
            if (r7 != 0) goto L22
            long r4 = r6.forcedKeyFrameMs
            int r1 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r1 <= 0) goto L22
            long r1 = r6.lastKeyFrameMs
            long r1 = r1 + r4
            int r1 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r1 <= 0) goto L22
            r1 = 1
            goto L23
        L22:
            r1 = r0
        L23:
            if (r7 != 0) goto L27
            if (r1 == 0) goto L47
        L27:
            java.lang.String r1 = "MediaCodecVideoEncoder"
            if (r7 == 0) goto L31
            java.lang.String r7 = "Sync frame request"
            org.wrtca.log.Logging.d(r1, r7)
            goto L36
        L31:
            java.lang.String r7 = "Sync frame forced"
            org.wrtca.log.Logging.d(r1, r7)
        L36:
            android.os.Bundle r7 = new android.os.Bundle
            r7.<init>()
            java.lang.String r1 = "request-sync"
            r7.putInt(r1, r0)
            android.media.MediaCodec r0 = r6.mediaCodec
            r0.setParameters(r7)
            r6.lastKeyFrameMs = r8
        L47:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.wrtca.api.MediaCodecVideoEncoder.checkKeyFrameRequired(boolean, long):void");
    }

    @CalledByNativeUnchecked
    public int dequeueInputBuffer() {
        checkOnMediaCodecThread();
        try {
            return this.mediaCodec.dequeueInputBuffer(0L);
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "dequeueIntputBuffer failed", e2);
            return -2;
        }
    }

    @CalledByNativeUnchecked
    public OutputBufferInfo dequeueOutputBuffer() {
        checkOnMediaCodecThread();
        try {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int iDequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, 0L);
            boolean z2 = true;
            if (iDequeueOutputBuffer >= 0) {
                if ((bufferInfo.flags & 2) != 0) {
                    Logging.d(TAG, "Config frame generated. Offset: " + bufferInfo.offset + ". Size: " + bufferInfo.size);
                    this.configData = ByteBuffer.allocateDirect(bufferInfo.size);
                    this.outputBuffers[iDequeueOutputBuffer].position(bufferInfo.offset);
                    this.outputBuffers[iDequeueOutputBuffer].limit(bufferInfo.offset + bufferInfo.size);
                    this.configData.put(this.outputBuffers[iDequeueOutputBuffer]);
                    String str = "";
                    int i2 = 0;
                    while (true) {
                        int i3 = bufferInfo.size;
                        if (i3 >= 8) {
                            i3 = 8;
                        }
                        if (i2 >= i3) {
                            break;
                        }
                        str = str + Integer.toHexString(this.configData.get(i2) & 255) + " ";
                        i2++;
                    }
                    Logging.d(TAG, str);
                    this.mediaCodec.releaseOutputBuffer(iDequeueOutputBuffer, false);
                    iDequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, 0L);
                }
            }
            int i4 = iDequeueOutputBuffer;
            if (i4 < 0) {
                if (i4 == -3) {
                    this.outputBuffers = this.mediaCodec.getOutputBuffers();
                    return dequeueOutputBuffer();
                }
                if (i4 == -2) {
                    return dequeueOutputBuffer();
                }
                if (i4 == -1) {
                    return null;
                }
                throw new RuntimeException("dequeueOutputBuffer: " + i4);
            }
            ByteBuffer byteBufferDuplicate = this.outputBuffers[i4].duplicate();
            byteBufferDuplicate.position(bufferInfo.offset);
            byteBufferDuplicate.limit(bufferInfo.offset + bufferInfo.size);
            reportEncodedFrame(bufferInfo.size);
            if ((bufferInfo.flags & 1) == 0) {
                z2 = false;
            }
            if (z2) {
                Logging.d(TAG, "Sync frame generated");
            }
            if (!z2 || this.type != VideoCodecType.VIDEO_CODEC_H264) {
                return new OutputBufferInfo(i4, byteBufferDuplicate.slice(), z2, bufferInfo.presentationTimeUs);
            }
            Logging.d(TAG, "Appending config frame of size " + this.configData.capacity() + " to output buffer with offset " + bufferInfo.offset + ", size " + bufferInfo.size);
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(this.configData.capacity() + bufferInfo.size);
            this.configData.rewind();
            byteBufferAllocateDirect.put(this.configData);
            byteBufferAllocateDirect.put(byteBufferDuplicate);
            byteBufferAllocateDirect.position(0);
            return new OutputBufferInfo(i4, byteBufferAllocateDirect, z2, bufferInfo.presentationTimeUs);
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "dequeueOutputBuffer failed", e2);
            return new OutputBufferInfo(-1, null, false, -1L);
        }
    }

    @CalledByNativeUnchecked
    public boolean encodeBuffer(boolean z2, int i2, int i3, long j2) throws MediaCodec.CryptoException {
        checkOnMediaCodecThread();
        try {
            checkKeyFrameRequired(z2, j2);
            this.mediaCodec.queueInputBuffer(i2, 0, i3, j2, 0);
            return true;
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "encodeBuffer failed", e2);
            return false;
        }
    }

    @CalledByNativeUnchecked
    public boolean encodeFrame(long j2, boolean z2, VideoFrame videoFrame, int i2, long j3) throws MediaCodec.CryptoException {
        checkOnMediaCodecThread();
        try {
            checkKeyFrameRequired(z2, j3);
            VideoFrame.Buffer buffer = videoFrame.getBuffer();
            if (buffer instanceof VideoFrame.TextureBuffer) {
                VideoFrame.TextureBuffer textureBuffer = (VideoFrame.TextureBuffer) buffer;
                this.eglBase.makeCurrent();
                GLES20.glClear(16384);
                GlRectDrawer glRectDrawer = this.drawer;
                Matrix matrix = new Matrix();
                int i3 = this.width;
                int i4 = this.height;
                VideoFrameDrawer.drawTexture(glRectDrawer, textureBuffer, matrix, i3, i4, 0, 0, i3, i4);
                this.eglBase.swapBuffers(TimeUnit.MICROSECONDS.toNanos(j3));
            } else {
                VideoFrame.I420Buffer i420 = buffer.toI420();
                int i5 = (this.height + 1) / 2;
                ByteBuffer dataY = i420.getDataY();
                ByteBuffer dataU = i420.getDataU();
                ByteBuffer dataV = i420.getDataV();
                int strideY = i420.getStrideY();
                int strideU = i420.getStrideU();
                int strideV = i420.getStrideV();
                if (dataY.capacity() < this.height * strideY) {
                    throw new RuntimeException("Y-plane buffer size too small.");
                }
                if (dataU.capacity() < strideU * i5) {
                    throw new RuntimeException("U-plane buffer size too small.");
                }
                if (dataV.capacity() < i5 * strideV) {
                    throw new RuntimeException("V-plane buffer size too small.");
                }
                nativeFillInputBuffer(j2, i2, dataY, strideY, dataU, strideU, dataV, strideV);
                i420.release();
                this.mediaCodec.queueInputBuffer(i2, 0, ((this.width * this.height) * 3) / 2, j3, 0);
            }
            return true;
        } catch (RuntimeException e2) {
            Logging.e(TAG, "encodeFrame failed", e2);
            return false;
        }
    }

    @CalledByNativeUnchecked
    public boolean encodeTexture(boolean z2, int i2, float[] fArr, long j2) {
        checkOnMediaCodecThread();
        try {
            checkKeyFrameRequired(z2, j2);
            this.eglBase.makeCurrent();
            GLES20.glClear(16384);
            GlRectDrawer glRectDrawer = this.drawer;
            int i3 = this.width;
            int i4 = this.height;
            glRectDrawer.drawOes(i2, fArr, i3, i4, 0, 0, i3, i4);
            this.eglBase.swapBuffers(TimeUnit.MICROSECONDS.toNanos(j2));
            return true;
        } catch (RuntimeException e2) {
            Logging.e(TAG, "encodeTexture failed", e2);
            return false;
        }
    }

    @CalledByNative
    public int getColorFormat() {
        return this.colorFormat;
    }

    @CalledByNativeUnchecked
    public ByteBuffer[] getInputBuffers() {
        ByteBuffer[] inputBuffers = this.mediaCodec.getInputBuffers();
        Logging.d(TAG, "Input buffers: " + inputBuffers.length);
        return inputBuffers;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x021b  */
    @org.wrtca.jni.CalledByNativeUnchecked
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean initEncode(org.wrtca.api.MediaCodecVideoEncoder.VideoCodecType r20, int r21, int r22, int r23, int r24, int r25, org.wrtca.gl.EglBase14.Context r26) {
        /*
            Method dump skipped, instructions count: 570
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.wrtca.api.MediaCodecVideoEncoder.initEncode(org.wrtca.api.MediaCodecVideoEncoder$VideoCodecType, int, int, int, int, int, org.wrtca.gl.EglBase14$Context):boolean");
    }

    @CalledByNativeUnchecked
    public void release() {
        c.h.a(TAG, "Java releaseEncoder");
        checkOnMediaCodecThread();
        final C1CaughtException c1CaughtException = new C1CaughtException();
        boolean z2 = false;
        if (this.mediaCodec != null) {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            new Thread(new Runnable() { // from class: org.wrtca.api.MediaCodecVideoEncoder.1
                @Override // java.lang.Runnable
                public void run() {
                    Logging.d(MediaCodecVideoEncoder.TAG, "Java releaseEncoder on release thread");
                    try {
                        MediaCodecVideoEncoder.this.mediaCodec.stop();
                    } catch (Exception e2) {
                        Logging.e(MediaCodecVideoEncoder.TAG, "Media encoder stop failed", e2);
                    }
                    try {
                        MediaCodecVideoEncoder.this.mediaCodec.release();
                    } catch (Exception e3) {
                        Logging.e(MediaCodecVideoEncoder.TAG, "Media encoder release failed", e3);
                        c1CaughtException.f28095e = e3;
                    }
                    Logging.d(MediaCodecVideoEncoder.TAG, "Java releaseEncoder on release thread done");
                    countDownLatch.countDown();
                }
            }).start();
            if (!ThreadUtils.awaitUninterruptibly(countDownLatch, 5000L)) {
                Logging.e(TAG, "Media encoder release timeout");
                z2 = true;
            }
            this.mediaCodec = null;
        }
        this.mediaCodecThread = null;
        GlRectDrawer glRectDrawer = this.drawer;
        if (glRectDrawer != null) {
            glRectDrawer.release();
            this.drawer = null;
        }
        EglBase14 eglBase14 = this.eglBase;
        if (eglBase14 != null) {
            eglBase14.release();
            this.eglBase = null;
        }
        Surface surface = this.inputSurface;
        if (surface != null) {
            surface.release();
            this.inputSurface = null;
        }
        runningInstance = null;
        if (!z2) {
            if (c1CaughtException.f28095e == null) {
                Logging.d(TAG, "Java releaseEncoder done");
                return;
            } else {
                RuntimeException runtimeException = new RuntimeException(c1CaughtException.f28095e);
                runtimeException.setStackTrace(ThreadUtils.concatStackTraces(c1CaughtException.f28095e.getStackTrace(), runtimeException.getStackTrace()));
                throw runtimeException;
            }
        }
        codecErrors++;
        if (errorCallback != null) {
            Logging.e(TAG, "Invoke codec error callback. Errors: " + codecErrors);
            errorCallback.onMediaCodecVideoEncoderCriticalError(codecErrors);
        }
        throw new RuntimeException("Media encoder release timeout.");
    }

    @CalledByNativeUnchecked
    public boolean releaseOutputBuffer(int i2) {
        checkOnMediaCodecThread();
        try {
            this.mediaCodec.releaseOutputBuffer(i2, false);
            return true;
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "releaseOutputBuffer failed", e2);
            return false;
        }
    }
}
