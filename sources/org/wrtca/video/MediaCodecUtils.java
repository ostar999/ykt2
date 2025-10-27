package org.wrtca.video;

import android.annotation.TargetApi;
import android.media.MediaCodecInfo;

@TargetApi(18)
/* loaded from: classes9.dex */
public class MediaCodecUtils {
    public static final String AMLOGIC_PREFIX = "OMX.amlogic.";
    public static final String EXYNOS_PREFIX = "OMX.Exynos.";
    public static final String GOOGLE_PREFIX = "OMX.google.";
    public static final String HISI_PREFIX = "OMX.hisi.";
    public static final String INTEL_PREFIX = "OMX.Intel.";
    public static final String NVIDIA_PREFIX = "OMX.Nvidia.";
    public static final String QCOM_PREFIX = "OMX.qcom.";
    private static final String TAG = "MediaCodecUtils";
    public static final int COLOR_QCOM_FORMATYVU420PackedSemiPlanar32m4ka = 2141391873;
    public static final int COLOR_QCOM_FORMATYVU420PackedSemiPlanar16m4ka = 2141391874;
    public static final int COLOR_QCOM_FORMATYVU420PackedSemiPlanar64x32Tile2m8ka = 2141391875;
    public static final int COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m = 2141391876;
    public static final int[] DECODER_COLOR_FORMATS = {19, 21, 2141391872, COLOR_QCOM_FORMATYVU420PackedSemiPlanar32m4ka, COLOR_QCOM_FORMATYVU420PackedSemiPlanar16m4ka, COLOR_QCOM_FORMATYVU420PackedSemiPlanar64x32Tile2m8ka, COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m};
    public static final int[] ENCODER_COLOR_FORMATS = {19, 21, 2141391872, COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m};
    public static final int[] TEXTURE_COLOR_FORMATS = {2130708361};

    private MediaCodecUtils() {
    }

    public static boolean codecSupportsType(MediaCodecInfo mediaCodecInfo, VideoCodecType videoCodecType) {
        for (String str : mediaCodecInfo.getSupportedTypes()) {
            if (videoCodecType.mimeType().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static Integer selectColorFormat(int[] iArr, MediaCodecInfo.CodecCapabilities codecCapabilities) {
        for (int i2 : iArr) {
            for (int i3 : codecCapabilities.colorFormats) {
                if (i3 == i2) {
                    return Integer.valueOf(i3);
                }
            }
        }
        return null;
    }
}
