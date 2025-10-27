package io.agora.rtc.video;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.opengl.EGLContext;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Surface;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.gms.common.Scopes;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import io.agora.rtc.gl.EglBase;
import io.agora.rtc.gl.EglBase10;
import io.agora.rtc.gl.EglBase14;
import io.agora.rtc.internal.Logging;
import io.agora.rtc.utils.ThreadUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import org.wrtca.video.MediaCodecUtils;

@TargetApi(19)
/* loaded from: classes8.dex */
public class MediaCodecVideoEncoder {
    private static final int BASE_FRAME_RATE_FOR_AMLOGIC = 30;
    private static final int BASE_FRAME_RATE_FOR_EXYNOS = 30;
    private static final int BASE_FRAME_RATE_FOR_HIS_HISI = 30;
    private static final int BASE_FRAME_RATE_FOR_HIS_K3 = 30;
    private static final int BASE_FRAME_RATE_FOR_HIS_TOPAZ = 30;
    private static final int BASE_FRAME_RATE_FOR_MTK = 30;
    private static final int COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m = 2141391876;
    private static final int DEQUEUE_TIMEOUT = 0;
    private static final String H264_MIME_TYPE = "video/avc";
    private static final int KBPS_TO_BPS_FACTOR = 900;
    private static final int KBPS_TO_BPS_FACTOR_QCOM = 950;
    private static final int MEDIA_CODEC_RELEASE_TIMEOUT_MS = 3000;
    private static final String TAG = "MediaCodecVideoEncoder";
    private static final int VIDEO_ControlRateConstant = 2;
    private static final int VIDEO_ControlRateVariable = 1;
    private static final String VP8_MIME_TYPE = "video/x-vnd.on2.vp8";
    private static final String VP9_MIME_TYPE = "video/x-vnd.on2.vp9";
    private static int codecErrors;
    private static String codecOmxName;
    private static MediaCodecVideoEncoderErrorCallback errorCallback;
    private static MediaCodecVideoEncoder runningInstance;
    private int colorFormat;
    private int converted_bps;
    private io.agora.rtc.gl.GlRectDrawer drawer;
    private EglBase eglBase;
    private int height;
    private Surface inputSurface;
    private int lastSetFps;
    private MediaCodec mediaCodec;
    private Thread mediaCodecThread;
    private ByteBuffer[] outputBuffers;
    private VideoCodecType type;
    private int width;
    private static Set<String> hwEncoderDisabledTypes = new HashSet();
    private static final String[] supportedVp8HwCodecPrefixes = {MediaCodecUtils.QCOM_PREFIX, MediaCodecUtils.INTEL_PREFIX};
    private static final String[] supportedVp9HwCodecPrefixes = {MediaCodecUtils.QCOM_PREFIX};
    private static final String[] supportedH264HwCodecPrefixes = {MediaCodecUtils.QCOM_PREFIX, MediaCodecUtils.EXYNOS_PREFIX, "OMX.MTK.", "OMX.IMG.TOPAZ.", MediaCodecUtils.HISI_PREFIX, "OMX.k3.", MediaCodecUtils.AMLOGIC_PREFIX, "OMX.rk."};
    private static final String[] H264_HW_EXCEPTION_MODELS = {"SAMSUNG-SGH-I337", "Nexus 7", "Nexus 4", "P6-C00", "HM 2A", "XT105", "XT109", "XT1060"};
    private static final String[] H264_HW_QCOM_EXCEPTION_MODELS = {"mi note lte", "redmi note 4x", "1605-a01", "aosp on hammerhead", "lm-x210", "oppo r9s"};
    private static final String[] MTK_NO_ADJUSTMENT_MODELS = {"vivo y83a", "vivo x21i", "vivo X21i A"};
    private static final int[] supportedColorList = {19, 21, 2141391872, 2141391876};
    private static final int[] supportedSurfaceColorList = {2130708361};
    private final Matrix rotateMatrix = new Matrix();
    private ByteBuffer configData = null;
    private long lastKeyFrameTimeMs = 0;
    private int keyFrameIntervalInMsec = 0;
    private long lastResetForQcomTimeMs = 0;
    private boolean qcomExceptionModel = false;
    private int settingCodecParameterForExynos = -1;
    private ChipProperties chipProperties = null;
    private FileOutputStream fos = null;

    /* renamed from: io.agora.rtc.video.MediaCodecVideoEncoder$1CaughtException, reason: invalid class name */
    public class C1CaughtException {

        /* renamed from: e, reason: collision with root package name */
        Exception f27147e;

        public C1CaughtException() {
        }
    }

    public enum BitrateAdjustmentType {
        NO_ADJUSTMENT,
        FRAMERATE_ADJUSTMENT,
        ACTUAL_FRAMERATE_ADJUSTMENT,
        DYNAMIC_ADJUSTMENT
    }

    public static class ChipProperties {
        public final int baseFrameRate;
        public final BitrateAdjustmentType bitrateAdjustmentType;
        public final String chipName;
        public final int highProfileMinSdkVersion;
        public final int initFrameRate;
        public final boolean isNeedResetWhenDownBps;

        public ChipProperties(String chipName, BitrateAdjustmentType bitrateAdjustmentType, boolean isNeedResetWhenDownBps, int baseFrameRate, int initFrameRate, int highProfileMinSdkVersion) {
            this.chipName = chipName;
            this.bitrateAdjustmentType = bitrateAdjustmentType;
            this.isNeedResetWhenDownBps = isNeedResetWhenDownBps;
            this.baseFrameRate = baseFrameRate;
            this.initFrameRate = initFrameRate;
            this.highProfileMinSdkVersion = highProfileMinSdkVersion;
        }
    }

    public static class EncoderProperties {
        public final String codecName;
        public final int colorFormat;
        public final boolean supportedList;

        public EncoderProperties(String codecName, int colorFormat, boolean supportedList) {
            this.codecName = codecName;
            this.colorFormat = colorFormat;
            this.supportedList = supportedList;
        }
    }

    public interface MediaCodecVideoEncoderErrorCallback {
        void onMediaCodecVideoEncoderCriticalError(int codecErrors);
    }

    public static class OutputBufferInfo {
        public final ByteBuffer buffer;
        public final int index;
        public final boolean isKeyFrame;
        public final long presentationTimestampUs;
        public final int size;

        public OutputBufferInfo(int index, ByteBuffer buffer, boolean isKeyFrame, long presentationTimestampUs, int size) {
            this.index = index;
            this.buffer = buffer;
            this.isKeyFrame = isKeyFrame;
            this.presentationTimestampUs = presentationTimestampUs;
            this.size = size;
        }
    }

    public enum VideoCodecType {
        VIDEO_CODEC_VP8,
        VIDEO_CODEC_VP9,
        VIDEO_CODEC_H264
    }

    private static boolean checkMinSDKVersion(String chipName, boolean isTexture) {
        if (isTexture || chipName.startsWith(MediaCodecUtils.QCOM_PREFIX) || chipName.startsWith("OMX.MTK.") || chipName.startsWith(MediaCodecUtils.EXYNOS_PREFIX) || chipName.startsWith("OMX.IMG.TOPAZ.")) {
            return true;
        }
        chipName.startsWith("OMX.k3.");
        return true;
    }

    private void checkOnMediaCodecThread() {
    }

    private int convertBitRate(int Kbps, int fps) {
        ChipProperties chipProperties = this.chipProperties;
        return chipProperties.bitrateAdjustmentType == BitrateAdjustmentType.FRAMERATE_ADJUSTMENT ? chipProperties.chipName.startsWith("OMX.rk.") ? ((Kbps * 1000) * this.chipProperties.baseFrameRate) / fps : ((Kbps * 900) * this.chipProperties.baseFrameRate) / fps : chipProperties.chipName.startsWith(MediaCodecUtils.QCOM_PREFIX) ? Kbps * 950 : Kbps * 900;
    }

    public static MediaCodec createByCodecName(String codecName) {
        try {
            return MediaCodec.createByCodecName(codecName);
        } catch (Exception unused) {
            return null;
        }
    }

    private boolean createEncoder(int codec, int width, int height, int Kbps, int fps, int init_fps, int keyinterval, int profile, boolean useSurface) throws RuntimeException {
        EncoderProperties encoderPropertiesFindHwEncoder;
        String str;
        Logging.i(TAG, "Java initEncode: " + VideoCodecType.values()[codec] + " : " + width + " x " + height + ". @ " + Kbps + " Kbps. Fps: " + fps + " key interval: " + keyinterval + "s. Encode from texture : " + useSurface);
        this.width = width;
        this.height = height;
        if (this.mediaCodecThread != null) {
            throw new RuntimeException("Forgot to release()?");
        }
        if (fps < 1) {
            fps = 1;
        }
        if (keyinterval < 1) {
            keyinterval = 1;
        }
        this.lastSetFps = fps;
        this.keyFrameIntervalInMsec = keyinterval * 1000;
        this.lastKeyFrameTimeMs = 0L;
        this.lastResetForQcomTimeMs = SystemClock.elapsedRealtime();
        VideoCodecType videoCodecType = VideoCodecType.values()[codec];
        if (videoCodecType == VideoCodecType.VIDEO_CODEC_VP8) {
            str = "video/x-vnd.on2.vp8";
            encoderPropertiesFindHwEncoder = findHwEncoder("video/x-vnd.on2.vp8", supportedVp8HwCodecPrefixes, useSurface ? supportedSurfaceColorList : supportedColorList);
        } else if (videoCodecType == VideoCodecType.VIDEO_CODEC_VP9) {
            str = "video/x-vnd.on2.vp9";
            encoderPropertiesFindHwEncoder = findHwEncoder("video/x-vnd.on2.vp9", supportedH264HwCodecPrefixes, useSurface ? supportedSurfaceColorList : supportedColorList);
        } else if (videoCodecType == VideoCodecType.VIDEO_CODEC_H264) {
            str = "video/avc";
            encoderPropertiesFindHwEncoder = findHwEncoder("video/avc", supportedH264HwCodecPrefixes, useSurface ? supportedSurfaceColorList : supportedColorList);
        } else {
            encoderPropertiesFindHwEncoder = null;
            str = null;
        }
        if (encoderPropertiesFindHwEncoder == null) {
            throw new RuntimeException("Can not find HW encoder for " + videoCodecType);
        }
        runningInstance = this;
        this.colorFormat = encoderPropertiesFindHwEncoder.colorFormat;
        this.chipProperties = getChipProperties(encoderPropertiesFindHwEncoder.codecName, fps);
        Logging.i(TAG, "Color format: " + this.colorFormat);
        this.converted_bps = convertBitRate(Kbps, fps);
        this.mediaCodecThread = Thread.currentThread();
        MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat(str, width, height);
        if (Build.VERSION.SDK_INT >= this.chipProperties.highProfileMinSdkVersion && profile == 100) {
            Logging.i(TAG, "Set high profile and level");
            mediaFormatCreateVideoFormat.setInteger(Scopes.PROFILE, 8);
            mediaFormatCreateVideoFormat.setInteger(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, 512);
        }
        mediaFormatCreateVideoFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, this.converted_bps);
        if (encoderPropertiesFindHwEncoder.codecName.startsWith("OMX.rk.")) {
            mediaFormatCreateVideoFormat.setInteger("bitrate-mode", 2);
        } else if (!this.qcomExceptionModel) {
            mediaFormatCreateVideoFormat.setInteger("bitrate-mode", 1);
        }
        mediaFormatCreateVideoFormat.setInteger("color-format", encoderPropertiesFindHwEncoder.colorFormat);
        ChipProperties chipProperties = this.chipProperties;
        if (chipProperties.bitrateAdjustmentType == BitrateAdjustmentType.NO_ADJUSTMENT) {
            mediaFormatCreateVideoFormat.setInteger("frame-rate", init_fps);
        } else {
            mediaFormatCreateVideoFormat.setInteger("frame-rate", chipProperties.initFrameRate);
        }
        if (this.chipProperties.bitrateAdjustmentType == BitrateAdjustmentType.ACTUAL_FRAMERATE_ADJUSTMENT) {
            mediaFormatCreateVideoFormat.setInteger("i-frame-interval", keyinterval);
        } else {
            mediaFormatCreateVideoFormat.setInteger("i-frame-interval", keyinterval + 1);
        }
        Logging.d(TAG, "  Format: " + mediaFormatCreateVideoFormat);
        MediaCodec mediaCodecCreateByCodecName = createByCodecName(encoderPropertiesFindHwEncoder.codecName);
        this.mediaCodec = mediaCodecCreateByCodecName;
        this.type = videoCodecType;
        if (mediaCodecCreateByCodecName == null) {
            throw new RuntimeException("Can not create media encoder");
        }
        mediaCodecCreateByCodecName.configure(mediaFormatCreateVideoFormat, (Surface) null, (MediaCrypto) null, 1);
        return true;
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

    private static int distance(float x02, float y02, float x12, float y12) {
        return (int) Math.round(Math.hypot(x12 - x02, y12 - y02));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x008b  */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v6 */
    /* JADX WARN: Type inference failed for: r12v2, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [int] */
    /* JADX WARN: Type inference failed for: r13v10, types: [int] */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4, types: [int] */
    /* JADX WARN: Type inference failed for: r13v9, types: [int] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v2, types: [int] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r8v7, types: [java.lang.StringBuilder] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static io.agora.rtc.video.MediaCodecVideoEncoder.EncoderProperties do_findHwEncoder(java.lang.String r18, java.lang.String[] r19, int[] r20) {
        /*
            Method dump skipped, instructions count: 497
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.agora.rtc.video.MediaCodecVideoEncoder.do_findHwEncoder(java.lang.String, java.lang.String[], int[]):io.agora.rtc.video.MediaCodecVideoEncoder$EncoderProperties");
    }

    private static EncoderProperties findHwEncoder(String mime, String[] supportedHwCodecPrefixes, int[] colorList) {
        try {
            return do_findHwEncoder(mime, supportedHwCodecPrefixes, colorList);
        } catch (Exception unused) {
            return null;
        }
    }

    private ChipProperties getChipProperties(String chipName, int fps) {
        if (chipName.startsWith(MediaCodecUtils.QCOM_PREFIX)) {
            List listAsList = Arrays.asList(H264_HW_QCOM_EXCEPTION_MODELS);
            String str = Build.MODEL;
            if (!listAsList.contains(str.toLowerCase())) {
                this.qcomExceptionModel = false;
                return new ChipProperties(chipName, BitrateAdjustmentType.NO_ADJUSTMENT, false, fps, fps, 21);
            }
            Logging.w(TAG, "Qcom Exception Model: " + str);
            this.qcomExceptionModel = true;
            return new ChipProperties(chipName, BitrateAdjustmentType.NO_ADJUSTMENT, true, fps, fps, 21);
        }
        if (chipName.startsWith("OMX.MTK.")) {
            String str2 = Build.HARDWARE;
            Logging.i(TAG, "MTK hardware: " + str2);
            return (str2.equalsIgnoreCase("mt6763") || str2.equalsIgnoreCase("mt6763t")) ? new ChipProperties(chipName, BitrateAdjustmentType.NO_ADJUSTMENT, false, fps, fps, 21) : Arrays.asList(MTK_NO_ADJUSTMENT_MODELS).contains(Build.MODEL) ? new ChipProperties(chipName, BitrateAdjustmentType.NO_ADJUSTMENT, false, fps, fps, 21) : str2.equalsIgnoreCase("mt6735") ? new ChipProperties(chipName, BitrateAdjustmentType.ACTUAL_FRAMERATE_ADJUSTMENT, false, fps, fps, Integer.MAX_VALUE) : new ChipProperties(chipName, BitrateAdjustmentType.ACTUAL_FRAMERATE_ADJUSTMENT, false, fps, fps, 21);
        }
        if (chipName.startsWith(MediaCodecUtils.EXYNOS_PREFIX)) {
            return Build.MODEL.equalsIgnoreCase("MX4 Pro") ? new ChipProperties(chipName, BitrateAdjustmentType.ACTUAL_FRAMERATE_ADJUSTMENT, false, fps, fps, Integer.MAX_VALUE) : (this.settingCodecParameterForExynos <= 0 || Build.VERSION.SDK_INT <= 28) ? new ChipProperties(chipName, BitrateAdjustmentType.FRAMERATE_ADJUSTMENT, false, 30, 30, 21) : new ChipProperties(chipName, BitrateAdjustmentType.ACTUAL_FRAMERATE_ADJUSTMENT, false, fps, fps, 21);
        }
        if (chipName.startsWith("OMX.IMG.TOPAZ.")) {
            return Build.HARDWARE.equalsIgnoreCase("hi6250") ? new ChipProperties(chipName, BitrateAdjustmentType.ACTUAL_FRAMERATE_ADJUSTMENT, false, fps, fps, Integer.MAX_VALUE) : new ChipProperties(chipName, BitrateAdjustmentType.FRAMERATE_ADJUSTMENT, false, 30, 30, Integer.MAX_VALUE);
        }
        if (chipName.startsWith(MediaCodecUtils.HISI_PREFIX)) {
            return new ChipProperties(chipName, BitrateAdjustmentType.ACTUAL_FRAMERATE_ADJUSTMENT, false, fps, fps, Integer.MAX_VALUE);
        }
        if (chipName.startsWith("OMX.k3.")) {
            return new ChipProperties(chipName, BitrateAdjustmentType.FRAMERATE_ADJUSTMENT, false, 30, 30, 21);
        }
        if (chipName.startsWith(MediaCodecUtils.AMLOGIC_PREFIX)) {
            Logging.i(TAG, "getChipProperties for amlogic");
            return new ChipProperties(chipName, BitrateAdjustmentType.FRAMERATE_ADJUSTMENT, false, 30, 30, Integer.MAX_VALUE);
        }
        if (chipName.startsWith("OMX.rk.")) {
            return new ChipProperties(chipName, BitrateAdjustmentType.FRAMERATE_ADJUSTMENT, false, 30, 30, Integer.MAX_VALUE);
        }
        Logging.i(TAG, "getChipProperties from unsupported chip list");
        return new ChipProperties(chipName, BitrateAdjustmentType.NO_ADJUSTMENT, false, fps, fps, 23);
    }

    public static boolean isH264HwSupported() {
        try {
            if (hwEncoderDisabledTypes.contains("video/avc")) {
                return false;
            }
            return findHwEncoder("video/avc", supportedH264HwCodecPrefixes, supportedColorList) != null;
        } catch (Exception unused) {
            Logging.e(TAG, "isH264HwSupported failed!");
            return false;
        }
    }

    public static boolean isH264HwSupportedUsingTextures() {
        try {
            if (hwEncoderDisabledTypes.contains("video/avc")) {
                return false;
            }
            return findHwEncoder("video/avc", supportedH264HwCodecPrefixes, supportedSurfaceColorList) != null;
        } catch (Exception unused) {
            Logging.e(TAG, "isH264HwSupportedUsingTextures failed!");
            return false;
        }
    }

    public static boolean isQcomHWEncoder() {
        String str = codecOmxName;
        if (str == null || str.startsWith(MediaCodecUtils.QCOM_PREFIX)) {
            Logging.i(TAG, "Qualcomm HW encoder true");
            return true;
        }
        Logging.i(TAG, "Qualcomm HW encoder false");
        return false;
    }

    public static boolean isVp8HwSupported() {
        return (hwEncoderDisabledTypes.contains("video/x-vnd.on2.vp8") || findHwEncoder("video/x-vnd.on2.vp8", supportedVp8HwCodecPrefixes, supportedColorList) == null) ? false : true;
    }

    public static boolean isVp8HwSupportedUsingTextures() {
        return (hwEncoderDisabledTypes.contains("video/x-vnd.on2.vp8") || findHwEncoder("video/x-vnd.on2.vp8", supportedVp8HwCodecPrefixes, supportedSurfaceColorList) == null) ? false : true;
    }

    public static boolean isVp9HwSupported() {
        return (hwEncoderDisabledTypes.contains("video/x-vnd.on2.vp9") || findHwEncoder("video/x-vnd.on2.vp9", supportedVp9HwCodecPrefixes, supportedColorList) == null) ? false : true;
    }

    public static boolean isVp9HwSupportedUsingTextures() {
        return (hwEncoderDisabledTypes.contains("video/x-vnd.on2.vp9") || findHwEncoder("video/x-vnd.on2.vp9", supportedVp9HwCodecPrefixes, supportedSurfaceColorList) == null) ? false : true;
    }

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

    public static void setErrorCallback(MediaCodecVideoEncoderErrorCallback errorCallback2) {
        Logging.d(TAG, "Set error callback");
        errorCallback = errorCallback2;
    }

    private int setRates(int Kbps, int fps) {
        int i2;
        checkOnMediaCodecThread();
        Logging.d(TAG, "Bwe setRates: " + Kbps + " Kbps");
        boolean z2 = fps > 0 && fps != this.lastSetFps;
        if (fps <= 0) {
            fps = this.lastSetFps;
        }
        this.lastSetFps = fps;
        int iConvertBitRate = convertBitRate(Kbps, fps);
        if (z2) {
            try {
                if (this.chipProperties.bitrateAdjustmentType == BitrateAdjustmentType.ACTUAL_FRAMERATE_ADJUSTMENT) {
                    this.converted_bps = iConvertBitRate;
                    return 0;
                }
            } catch (IllegalStateException e2) {
                Logging.e(TAG, "setRates failed", e2);
                return 0;
            }
        }
        if (iConvertBitRate > this.converted_bps) {
            this.converted_bps = iConvertBitRate;
            Bundle bundle = new Bundle();
            bundle.putInt("video-bitrate", this.converted_bps);
            this.mediaCodec.setParameters(bundle);
            Logging.i(TAG, "setRates up to : " + this.converted_bps + " bps(converted) " + this.lastSetFps + " fps");
            return 1;
        }
        if (this.chipProperties.chipName.startsWith(MediaCodecUtils.QCOM_PREFIX)) {
            i2 = 25000;
            if (!this.qcomExceptionModel && this.converted_bps <= 200000) {
                i2 = 15000;
            }
        } else {
            i2 = 0;
        }
        if (iConvertBitRate < this.converted_bps - i2) {
            this.converted_bps = iConvertBitRate;
            if (this.chipProperties.isNeedResetWhenDownBps) {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                if (jElapsedRealtime - this.lastResetForQcomTimeMs < ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
                    return 2;
                }
                this.lastResetForQcomTimeMs = jElapsedRealtime;
                return 0;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putInt("video-bitrate", this.converted_bps);
            this.mediaCodec.setParameters(bundle2);
            Logging.i(TAG, "setRates down to : " + this.converted_bps + " bps(converted) " + this.lastSetFps + " fps");
        }
        return 1;
    }

    public int dequeueInputBuffer() {
        checkOnMediaCodecThread();
        try {
            return this.mediaCodec.dequeueInputBuffer(0L);
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "dequeueIntputBuffer failed", e2);
            return -2;
        }
    }

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
                    this.mediaCodec.releaseOutputBuffer(iDequeueOutputBuffer, false);
                    iDequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, 0L);
                }
            }
            int i2 = iDequeueOutputBuffer;
            if (i2 < 0) {
                if (i2 == -3) {
                    this.outputBuffers = this.mediaCodec.getOutputBuffers();
                    return dequeueOutputBuffer();
                }
                if (i2 == -2) {
                    return dequeueOutputBuffer();
                }
                if (i2 == -1) {
                    return null;
                }
                throw new RuntimeException("dequeueOutputBuffer: " + i2);
            }
            ByteBuffer byteBufferDuplicate = this.outputBuffers[i2].duplicate();
            byteBufferDuplicate.position(bufferInfo.offset);
            byteBufferDuplicate.limit(bufferInfo.offset + bufferInfo.size);
            if ((bufferInfo.flags & 1) == 0) {
                z2 = false;
            }
            if (z2) {
                Logging.d(TAG, "Sync frame generated");
            }
            if (!z2 || this.type != VideoCodecType.VIDEO_CODEC_H264) {
                return new OutputBufferInfo(i2, byteBufferDuplicate.slice(), z2, bufferInfo.presentationTimeUs, bufferInfo.size);
            }
            Logging.d(TAG, "Appending config frame of size " + this.configData.capacity() + " to output buffer with offset " + bufferInfo.offset + ", size " + bufferInfo.size);
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(this.configData.capacity() + bufferInfo.size);
            this.configData.rewind();
            byteBufferAllocateDirect.put(this.configData);
            byteBufferAllocateDirect.put(byteBufferDuplicate);
            byteBufferAllocateDirect.position(0);
            return new OutputBufferInfo(i2, byteBufferAllocateDirect, z2, bufferInfo.presentationTimeUs, bufferInfo.size + this.configData.capacity());
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "dequeueOutputBuffer failed", e2);
            return new OutputBufferInfo(-1, null, false, -1L, 0);
        }
    }

    public void dumpIntoFile(OutputBufferInfo buf) throws IOException {
        if (this.fos == null) {
            try {
                this.fos = new FileOutputStream(String.format("/sdcard/java_dump_video_%d_%d.h264", Integer.valueOf(this.width), Integer.valueOf(this.height)), true);
            } catch (Exception unused) {
                Logging.i(TAG, "dumpIntoFile: failed to open java_dump_video.h264");
                return;
            }
        }
        if (buf == null || buf.index < 0) {
            return;
        }
        Logging.i(TAG, "Dump nal: " + buf.buffer);
        try {
            byte[] bArr = new byte[buf.buffer.remaining()];
            buf.buffer.get(bArr);
            this.fos.write(bArr, 0, buf.size);
        } catch (Exception e2) {
            Logging.e(TAG, "Run: 4.1 Exception ", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f A[Catch: IllegalStateException -> 0x002b, TryCatch #0 {IllegalStateException -> 0x002b, blocks: (B:7:0x0017, B:9:0x001f, B:17:0x0045, B:15:0x002f, B:16:0x0034), top: B:22:0x0017 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean encodeBuffer(boolean r15, int r16, int r17, long r18) throws android.media.MediaCodec.CryptoException {
        /*
            r14 = this;
            r1 = r14
            r14.checkOnMediaCodecThread()
            long r2 = android.os.SystemClock.elapsedRealtime()
            long r4 = r1.lastKeyFrameTimeMs
            r6 = 0
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 != 0) goto L12
            r1.lastKeyFrameTimeMs = r2
        L12:
            r4 = 0
            java.lang.String r5 = "MediaCodecVideoEncoder"
            if (r15 != 0) goto L2d
            io.agora.rtc.video.MediaCodecVideoEncoder$ChipProperties r0 = r1.chipProperties     // Catch: java.lang.IllegalStateException -> L2b
            io.agora.rtc.video.MediaCodecVideoEncoder$BitrateAdjustmentType r0 = r0.bitrateAdjustmentType     // Catch: java.lang.IllegalStateException -> L2b
            io.agora.rtc.video.MediaCodecVideoEncoder$BitrateAdjustmentType r6 = io.agora.rtc.video.MediaCodecVideoEncoder.BitrateAdjustmentType.ACTUAL_FRAMERATE_ADJUSTMENT     // Catch: java.lang.IllegalStateException -> L2b
            if (r0 == r6) goto L45
            long r6 = r1.lastKeyFrameTimeMs     // Catch: java.lang.IllegalStateException -> L2b
            long r6 = r2 - r6
            int r0 = r1.keyFrameIntervalInMsec     // Catch: java.lang.IllegalStateException -> L2b
            long r8 = (long) r0     // Catch: java.lang.IllegalStateException -> L2b
            int r0 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r0 < 0) goto L45
            goto L2d
        L2b:
            r0 = move-exception
            goto L54
        L2d:
            if (r15 == 0) goto L34
            java.lang.String r0 = "Sync frame request"
            io.agora.rtc.internal.Logging.i(r5, r0)     // Catch: java.lang.IllegalStateException -> L2b
        L34:
            android.os.Bundle r0 = new android.os.Bundle     // Catch: java.lang.IllegalStateException -> L2b
            r0.<init>()     // Catch: java.lang.IllegalStateException -> L2b
            java.lang.String r6 = "request-sync"
            r0.putInt(r6, r4)     // Catch: java.lang.IllegalStateException -> L2b
            android.media.MediaCodec r6 = r1.mediaCodec     // Catch: java.lang.IllegalStateException -> L2b
            r6.setParameters(r0)     // Catch: java.lang.IllegalStateException -> L2b
            r1.lastKeyFrameTimeMs = r2     // Catch: java.lang.IllegalStateException -> L2b
        L45:
            android.media.MediaCodec r7 = r1.mediaCodec     // Catch: java.lang.IllegalStateException -> L2b
            r9 = 0
            r13 = 0
            r8 = r16
            r10 = r17
            r11 = r18
            r7.queueInputBuffer(r8, r9, r10, r11, r13)     // Catch: java.lang.IllegalStateException -> L2b
            r0 = 1
            return r0
        L54:
            java.lang.String r2 = "encodeBuffer failed"
            io.agora.rtc.internal.Logging.e(r5, r2, r0)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.agora.rtc.video.MediaCodecVideoEncoder.encodeBuffer(boolean, int, int, long):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0035 A[Catch: RuntimeException -> 0x0030, TryCatch #0 {RuntimeException -> 0x0030, blocks: (B:7:0x001c, B:9:0x0024, B:17:0x004b, B:24:0x0093, B:26:0x00bf, B:28:0x00e2, B:27:0x00d1, B:15:0x0035, B:16:0x003a), top: B:33:0x001c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean encodeTexture(boolean r19, int r20, int r21, float[] r22, int r23, int r24, int r25, int r26, int r27, long r28) {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.agora.rtc.video.MediaCodecVideoEncoder.encodeTexture(boolean, int, int, float[], int, int, int, int, int, long):boolean");
    }

    public ByteBuffer[] getInputBuffers() {
        ByteBuffer[] inputBuffers = this.mediaCodec.getInputBuffers();
        Logging.d(TAG, "Input buffers: " + inputBuffers.length);
        return inputBuffers;
    }

    public boolean initEncode(int codec, int width, int height, int Kbps, int fps, int init_fps, int keyinterval, int profile, EGLContext sharedContext) {
        try {
            if (!createEncoder(codec, width, height, Kbps, fps, init_fps, keyinterval, profile, sharedContext != null)) {
                Logging.e(TAG, "failed to create hardware encoder!!");
                return false;
            }
            if (sharedContext != null) {
                this.eglBase = new EglBase14(new EglBase14.Context(sharedContext), EglBase.CONFIG_RECORDABLE);
                Surface surfaceCreateInputSurface = this.mediaCodec.createInputSurface();
                this.inputSurface = surfaceCreateInputSurface;
                this.eglBase.createSurface(surfaceCreateInputSurface);
                this.drawer = new io.agora.rtc.gl.GlRectDrawer();
            }
            this.mediaCodec.start();
            this.outputBuffers = this.mediaCodec.getOutputBuffers();
            Logging.d(TAG, "Output buffers: " + this.outputBuffers.length);
            return true;
        } catch (Exception e2) {
            Logging.e(TAG, "failed to create hardware encoder,", e2);
            release();
            return false;
        }
    }

    public void release() {
        Logging.i(TAG, "Java releaseEncoder");
        checkOnMediaCodecThread();
        final C1CaughtException c1CaughtException = new C1CaughtException();
        boolean z2 = false;
        if (this.mediaCodec != null) {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            new Thread(new Runnable() { // from class: io.agora.rtc.video.MediaCodecVideoEncoder.1
                @Override // java.lang.Runnable
                public void run() {
                    Logging.i(MediaCodecVideoEncoder.TAG, "Java releaseEncoder on release thread");
                    try {
                        MediaCodecVideoEncoder.this.mediaCodec.stop();
                    } catch (Exception e2) {
                        Logging.e(MediaCodecVideoEncoder.TAG, "Media encoder stop failed", e2);
                    }
                    try {
                        MediaCodecVideoEncoder.this.mediaCodec.release();
                    } catch (Exception e3) {
                        Logging.e(MediaCodecVideoEncoder.TAG, "Media encoder release failed", e3);
                        c1CaughtException.f27147e = e3;
                    }
                    Logging.i(MediaCodecVideoEncoder.TAG, "Java releaseEncoder on release thread done");
                    countDownLatch.countDown();
                }
            }).start();
            if (!ThreadUtils.awaitUninterruptibly(countDownLatch, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)) {
                Logging.e(TAG, "Media encoder release timeout");
                z2 = true;
            }
            this.mediaCodec = null;
        }
        this.mediaCodecThread = null;
        io.agora.rtc.gl.GlRectDrawer glRectDrawer = this.drawer;
        if (glRectDrawer != null) {
            glRectDrawer.release();
            this.drawer = null;
        }
        EglBase eglBase = this.eglBase;
        if (eglBase != null) {
            eglBase.release();
            this.eglBase = null;
        }
        Surface surface = this.inputSurface;
        if (surface != null) {
            surface.release();
            this.inputSurface = null;
        }
        runningInstance = null;
        if (!z2) {
            if (c1CaughtException.f27147e == null) {
                Logging.i(TAG, "Java releaseEncoder done");
                return;
            } else {
                RuntimeException runtimeException = new RuntimeException(c1CaughtException.f27147e);
                runtimeException.setStackTrace(ThreadUtils.concatStackTraces(c1CaughtException.f27147e.getStackTrace(), runtimeException.getStackTrace()));
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

    public boolean releaseOutputBuffer(int index) {
        checkOnMediaCodecThread();
        try {
            this.mediaCodec.releaseOutputBuffer(index, false);
            return true;
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "releaseOutputBuffer failed", e2);
            return false;
        }
    }

    public boolean initEncode(int codec, int width, int height, int Kbps, int fps, int init_fps, int keyinterval, int profile, javax.microedition.khronos.egl.EGLContext sharedContext) {
        try {
            if (createEncoder(codec, width, height, Kbps, fps, init_fps, keyinterval, profile, sharedContext != null)) {
                if (sharedContext != null) {
                    this.eglBase = new EglBase10(new EglBase10.Context(sharedContext), EglBase.CONFIG_RECORDABLE);
                    Surface surfaceCreateInputSurface = this.mediaCodec.createInputSurface();
                    this.inputSurface = surfaceCreateInputSurface;
                    this.eglBase.createSurface(surfaceCreateInputSurface);
                    this.drawer = new io.agora.rtc.gl.GlRectDrawer();
                }
                this.mediaCodec.start();
                this.outputBuffers = this.mediaCodec.getOutputBuffers();
                Logging.d(TAG, "Output buffers: " + this.outputBuffers.length);
                return true;
            }
            Logging.e(TAG, "failed to create hardware encoder!!");
            return false;
        } catch (Exception e2) {
            Logging.e(TAG, "failed to create hardware encoder,", e2);
            release();
            return false;
        }
    }
}
