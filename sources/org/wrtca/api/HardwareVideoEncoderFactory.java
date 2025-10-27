package org.wrtca.api;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.wrtca.api.EglBase;
import org.wrtca.gl.EglBase14;
import org.wrtca.jni.JNINamespace;
import org.wrtca.log.Logging;
import org.wrtca.video.BaseBitrateAdjuster;
import org.wrtca.video.BitrateAdjuster;
import org.wrtca.video.DynamicBitrateAdjuster;
import org.wrtca.video.FramerateBitrateAdjuster;
import org.wrtca.video.HardwareVideoEncoder;
import org.wrtca.video.MediaCodecUtils;
import org.wrtca.video.SoftwareVideoEncoderFactory;
import org.wrtca.video.VideoCodecType;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class HardwareVideoEncoderFactory implements VideoEncoderFactory {
    private static final List<String> H264_HW_EXCEPTION_MODELS = Arrays.asList("SAMSUNG-SGH-I337", "Nexus 7", "Nexus 4");
    private static final int QCOM_VP8_KEY_FRAME_INTERVAL_ANDROID_L_MS = 15000;
    private static final int QCOM_VP8_KEY_FRAME_INTERVAL_ANDROID_M_MS = 20000;
    private static final int QCOM_VP8_KEY_FRAME_INTERVAL_ANDROID_N_MS = 15000;
    private static final String TAG = "HardwareVideoEncoderFactory";
    private final boolean enableH264HighProfile;
    private final boolean enableIntelVp8Encoder;
    private final boolean fallbackToSoftware;
    private final EglBase14.Context sharedContext;

    /* renamed from: org.wrtca.api.HardwareVideoEncoderFactory$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$org$wrtca$video$VideoCodecType;

        static {
            int[] iArr = new int[VideoCodecType.values().length];
            $SwitchMap$org$wrtca$video$VideoCodecType = iArr;
            try {
                iArr[VideoCodecType.VP8.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$wrtca$video$VideoCodecType[VideoCodecType.VP9.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$wrtca$video$VideoCodecType[VideoCodecType.H264.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public HardwareVideoEncoderFactory(EglBase.Context context, boolean z2, boolean z3) {
        this(context, z2, z3, true);
    }

    private BitrateAdjuster createBitrateAdjuster(VideoCodecType videoCodecType, String str) {
        return (str.startsWith(MediaCodecUtils.EXYNOS_PREFIX) || str.startsWith(MediaCodecUtils.GOOGLE_PREFIX) || str.startsWith(MediaCodecUtils.AMLOGIC_PREFIX)) ? videoCodecType == VideoCodecType.VP8 ? new DynamicBitrateAdjuster() : new FramerateBitrateAdjuster() : new BaseBitrateAdjuster();
    }

    private MediaCodecInfo findCodecForType(VideoCodecType videoCodecType) {
        int i2 = 0;
        while (true) {
            MediaCodecInfo codecInfoAt = null;
            if (i2 >= MediaCodecList.getCodecCount()) {
                return null;
            }
            try {
                codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
            } catch (IllegalArgumentException e2) {
                Logging.e(TAG, "Cannot retrieve encoder codec info", e2);
            }
            if (codecInfoAt != null && codecInfoAt.isEncoder() && isSupportedCodec(codecInfoAt, videoCodecType)) {
                return codecInfoAt;
            }
            i2++;
        }
    }

    private Map<String, String> getCodecProperties(VideoCodecType videoCodecType, boolean z2) {
        int i2 = AnonymousClass1.$SwitchMap$org$wrtca$video$VideoCodecType[videoCodecType.ordinal()];
        if (i2 == 1 || i2 == 2) {
            return new HashMap();
        }
        if (i2 != 3) {
            throw new IllegalArgumentException("Unsupported codec: " + videoCodecType);
        }
        HashMap map = new HashMap();
        map.put(VideoCodecInfo.H264_FMTP_LEVEL_ASYMMETRY_ALLOWED, "1");
        map.put(VideoCodecInfo.H264_FMTP_PACKETIZATION_MODE, "1");
        map.put(VideoCodecInfo.H264_FMTP_PROFILE_LEVEL_ID, z2 ? VideoCodecInfo.H264_CONSTRAINED_HIGH_3_1 : VideoCodecInfo.H264_CONSTRAINED_BASELINE_3_1);
        return map;
    }

    private int getForcedKeyFrameIntervalMs(VideoCodecType videoCodecType, String str) {
        if (videoCodecType != VideoCodecType.VP8 || !str.startsWith(MediaCodecUtils.QCOM_PREFIX)) {
            return 0;
        }
        int i2 = Build.VERSION.SDK_INT;
        if (i2 == 23) {
            return 20000;
        }
        return i2 > 23 ? 15000 : 0;
    }

    private int getKeyFrameIntervalSec(VideoCodecType videoCodecType) {
        int i2 = AnonymousClass1.$SwitchMap$org$wrtca$video$VideoCodecType[videoCodecType.ordinal()];
        if (i2 == 1 || i2 == 2) {
            return 100;
        }
        if (i2 == 3) {
            return 20;
        }
        throw new IllegalArgumentException("Unsupported VideoCodecType " + videoCodecType);
    }

    private boolean isH264HighProfileSupported(MediaCodecInfo mediaCodecInfo) {
        return this.enableH264HighProfile && (mediaCodecInfo.getName().startsWith(MediaCodecUtils.EXYNOS_PREFIX) || mediaCodecInfo.getName().startsWith(MediaCodecUtils.GOOGLE_PREFIX) || mediaCodecInfo.getName().startsWith(MediaCodecUtils.QCOM_PREFIX) || mediaCodecInfo.getName().startsWith(MediaCodecUtils.AMLOGIC_PREFIX));
    }

    private boolean isHardwareSupportedInCurrentSdk(MediaCodecInfo mediaCodecInfo, VideoCodecType videoCodecType) {
        int i2 = AnonymousClass1.$SwitchMap$org$wrtca$video$VideoCodecType[videoCodecType.ordinal()];
        if (i2 == 1) {
            return isHardwareSupportedInCurrentSdkVp8(mediaCodecInfo);
        }
        if (i2 == 2) {
            return isHardwareSupportedInCurrentSdkVp9(mediaCodecInfo);
        }
        if (i2 != 3) {
            return false;
        }
        return isHardwareSupportedInCurrentSdkH264(mediaCodecInfo);
    }

    private boolean isHardwareSupportedInCurrentSdkH264(MediaCodecInfo mediaCodecInfo) {
        c.h.a(TAG, "isHardwareSupportedInCurrentSdkH264 info: " + mediaCodecInfo);
        if (H264_HW_EXCEPTION_MODELS.contains(Build.MODEL)) {
            return false;
        }
        String name = mediaCodecInfo.getName();
        return name.startsWith(MediaCodecUtils.QCOM_PREFIX) || name.startsWith(MediaCodecUtils.AMLOGIC_PREFIX) || name.startsWith(MediaCodecUtils.EXYNOS_PREFIX) || name.startsWith(MediaCodecUtils.GOOGLE_PREFIX) || name.startsWith(MediaCodecUtils.HISI_PREFIX);
    }

    private boolean isHardwareSupportedInCurrentSdkVp8(MediaCodecInfo mediaCodecInfo) {
        String name = mediaCodecInfo.getName();
        return name.startsWith(MediaCodecUtils.QCOM_PREFIX) || name.startsWith(MediaCodecUtils.EXYNOS_PREFIX) || (name.startsWith(MediaCodecUtils.INTEL_PREFIX) && this.enableIntelVp8Encoder);
    }

    private boolean isHardwareSupportedInCurrentSdkVp9(MediaCodecInfo mediaCodecInfo) {
        String name = mediaCodecInfo.getName();
        return (name.startsWith(MediaCodecUtils.QCOM_PREFIX) || name.startsWith(MediaCodecUtils.EXYNOS_PREFIX)) && Build.VERSION.SDK_INT >= 24;
    }

    private boolean isSupportedCodec(MediaCodecInfo mediaCodecInfo, VideoCodecType videoCodecType) {
        if (MediaCodecUtils.codecSupportsType(mediaCodecInfo, videoCodecType) && MediaCodecUtils.selectColorFormat(MediaCodecUtils.ENCODER_COLOR_FORMATS, mediaCodecInfo.getCapabilitiesForType(videoCodecType.mimeType())) != null) {
            return isHardwareSupportedInCurrentSdk(mediaCodecInfo, videoCodecType);
        }
        return false;
    }

    private static native boolean nativeIsSameH264Profile(Map<String, String> map, Map<String, String> map2);

    @Override // org.wrtca.api.VideoEncoderFactory
    public VideoEncoder createEncoder(VideoCodecInfo videoCodecInfo) {
        c.h.a(TAG, "createEncoder in HardwareVideoEncoderFactory");
        VideoCodecType videoCodecTypeValueOf = VideoCodecType.valueOf(videoCodecInfo.name);
        MediaCodecInfo mediaCodecInfoFindCodecForType = findCodecForType(videoCodecTypeValueOf);
        if (mediaCodecInfoFindCodecForType == null) {
            if (this.fallbackToSoftware) {
                return new SoftwareVideoEncoderFactory().createEncoder(videoCodecInfo);
            }
            return null;
        }
        String name = mediaCodecInfoFindCodecForType.getName();
        String strMimeType = videoCodecTypeValueOf.mimeType();
        Integer numSelectColorFormat = MediaCodecUtils.selectColorFormat(MediaCodecUtils.TEXTURE_COLOR_FORMATS, mediaCodecInfoFindCodecForType.getCapabilitiesForType(strMimeType));
        Integer numSelectColorFormat2 = MediaCodecUtils.selectColorFormat(MediaCodecUtils.ENCODER_COLOR_FORMATS, mediaCodecInfoFindCodecForType.getCapabilitiesForType(strMimeType));
        if (videoCodecTypeValueOf == VideoCodecType.H264) {
            boolean z2 = nativeIsSameH264Profile(videoCodecInfo.params, getCodecProperties(videoCodecTypeValueOf, true)) && isH264HighProfileSupported(mediaCodecInfoFindCodecForType);
            boolean zNativeIsSameH264Profile = nativeIsSameH264Profile(videoCodecInfo.params, getCodecProperties(videoCodecTypeValueOf, false));
            if (!z2 && !zNativeIsSameH264Profile) {
                return null;
            }
        }
        c.h.a(TAG, "create hw encoder success with " + name);
        return new HardwareVideoEncoder(name, videoCodecTypeValueOf, numSelectColorFormat, numSelectColorFormat2, videoCodecInfo.params, getKeyFrameIntervalSec(videoCodecTypeValueOf), getForcedKeyFrameIntervalMs(videoCodecTypeValueOf, name), createBitrateAdjuster(videoCodecTypeValueOf, name), this.sharedContext);
    }

    @Override // org.wrtca.api.VideoEncoderFactory
    public VideoCodecInfo[] getSupportedCodecs() {
        ArrayList arrayList = new ArrayList();
        VideoCodecType[] videoCodecTypeArr = {VideoCodecType.VP8, VideoCodecType.VP9, VideoCodecType.H264};
        for (int i2 = 0; i2 < 3; i2++) {
            VideoCodecType videoCodecType = videoCodecTypeArr[i2];
            MediaCodecInfo mediaCodecInfoFindCodecForType = findCodecForType(videoCodecType);
            if (mediaCodecInfoFindCodecForType != null) {
                String strName = videoCodecType.name();
                if (videoCodecType == VideoCodecType.H264 && isH264HighProfileSupported(mediaCodecInfoFindCodecForType)) {
                    arrayList.add(new VideoCodecInfo(strName, getCodecProperties(videoCodecType, true)));
                }
                arrayList.add(new VideoCodecInfo(strName, getCodecProperties(videoCodecType, false)));
            }
        }
        if (this.fallbackToSoftware) {
            for (VideoCodecInfo videoCodecInfo : SoftwareVideoEncoderFactory.supportedCodecs()) {
                if (!arrayList.contains(videoCodecInfo)) {
                    arrayList.add(videoCodecInfo);
                }
            }
        }
        return (VideoCodecInfo[]) arrayList.toArray(new VideoCodecInfo[arrayList.size()]);
    }

    public HardwareVideoEncoderFactory(EglBase.Context context, boolean z2, boolean z3, boolean z4) {
        if (context instanceof EglBase14.Context) {
            this.sharedContext = (EglBase14.Context) context;
        } else {
            Logging.w(TAG, "No shared EglBase.Context.  Encoders will not use texture mode.");
            this.sharedContext = null;
        }
        this.enableIntelVp8Encoder = z2;
        this.enableH264HighProfile = z3;
        this.fallbackToSoftware = z4;
    }

    @Deprecated
    public HardwareVideoEncoderFactory(boolean z2, boolean z3) {
        this(null, z2, z3);
    }
}
