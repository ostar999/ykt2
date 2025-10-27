package org.wrtca.api;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import org.wrtca.api.EglBase;
import org.wrtca.log.Logging;
import org.wrtca.video.HardwareVideoDecoder;
import org.wrtca.video.MediaCodecUtils;
import org.wrtca.video.SoftwareVideoDecoderFactory;
import org.wrtca.video.VideoCodecType;

/* loaded from: classes9.dex */
public class HardwareVideoDecoderFactory implements VideoDecoderFactory {
    private static final String TAG = "HardwareVideoDecoderFactory";
    private final boolean fallbackToSoftware;
    private final EglBase.Context sharedContext;

    /* renamed from: org.wrtca.api.HardwareVideoDecoderFactory$1, reason: invalid class name */
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

    @Deprecated
    public HardwareVideoDecoderFactory() {
        this(null);
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
            if (codecInfoAt != null && !codecInfoAt.isEncoder() && isSupportedCodec(codecInfoAt, videoCodecType)) {
                return codecInfoAt;
            }
            i2++;
        }
    }

    private boolean isHardwareSupported(MediaCodecInfo mediaCodecInfo, VideoCodecType videoCodecType) {
        String name = mediaCodecInfo.getName();
        int i2 = AnonymousClass1.$SwitchMap$org$wrtca$video$VideoCodecType[videoCodecType.ordinal()];
        if (i2 == 1) {
            return name.startsWith(MediaCodecUtils.QCOM_PREFIX) || name.startsWith(MediaCodecUtils.INTEL_PREFIX) || name.startsWith(MediaCodecUtils.EXYNOS_PREFIX) || name.startsWith(MediaCodecUtils.NVIDIA_PREFIX);
        }
        if (i2 == 2) {
            return name.startsWith(MediaCodecUtils.QCOM_PREFIX) || name.startsWith(MediaCodecUtils.EXYNOS_PREFIX);
        }
        if (i2 != 3) {
            return false;
        }
        return name.startsWith(MediaCodecUtils.QCOM_PREFIX) || name.startsWith(MediaCodecUtils.INTEL_PREFIX) || name.startsWith(MediaCodecUtils.EXYNOS_PREFIX) || name.startsWith(MediaCodecUtils.AMLOGIC_PREFIX);
    }

    private boolean isSupportedCodec(MediaCodecInfo mediaCodecInfo, VideoCodecType videoCodecType) {
        if (MediaCodecUtils.codecSupportsType(mediaCodecInfo, videoCodecType) && MediaCodecUtils.selectColorFormat(MediaCodecUtils.DECODER_COLOR_FORMATS, mediaCodecInfo.getCapabilitiesForType(videoCodecType.mimeType())) != null) {
            return isHardwareSupported(mediaCodecInfo, videoCodecType);
        }
        return false;
    }

    @Override // org.wrtca.api.VideoDecoderFactory
    public VideoDecoder createDecoder(String str) {
        VideoCodecType videoCodecTypeValueOf = VideoCodecType.valueOf(str);
        MediaCodecInfo mediaCodecInfoFindCodecForType = findCodecForType(videoCodecTypeValueOf);
        if (mediaCodecInfoFindCodecForType != null) {
            return new HardwareVideoDecoder(mediaCodecInfoFindCodecForType.getName(), videoCodecTypeValueOf, MediaCodecUtils.selectColorFormat(MediaCodecUtils.DECODER_COLOR_FORMATS, mediaCodecInfoFindCodecForType.getCapabilitiesForType(videoCodecTypeValueOf.mimeType())).intValue(), this.sharedContext);
        }
        if (this.fallbackToSoftware) {
            return new SoftwareVideoDecoderFactory().createDecoder(str);
        }
        return null;
    }

    public HardwareVideoDecoderFactory(EglBase.Context context) {
        this(context, true);
    }

    public HardwareVideoDecoderFactory(EglBase.Context context, boolean z2) {
        this.sharedContext = context;
        this.fallbackToSoftware = z2;
    }
}
