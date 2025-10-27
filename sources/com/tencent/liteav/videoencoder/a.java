package com.tencent.liteav.videoencoder;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.util.Range;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.android.gms.common.Scopes;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;

/* loaded from: classes6.dex */
class a {

    /* renamed from: a, reason: collision with root package name */
    private static String f20203a = "MediaFormatUtil";

    public static MediaFormat a(String str, int i2, int i3, int i4, int i5, int i6) {
        TXCLog.i(f20203a, "createBaseFormat:  mineType:" + str + "  width:" + i2 + "  height:" + i3 + "  bitrate:" + i4 + "  fps:" + i5 + "  gop:" + i6);
        if (i2 == 0 || i3 == 0 || i4 == 0 || i5 == 0) {
            return null;
        }
        MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat(str, i2, i3);
        mediaFormatCreateVideoFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, i4 * 1024);
        mediaFormatCreateVideoFormat.setInteger("frame-rate", i5);
        mediaFormatCreateVideoFormat.setInteger("color-format", 2130708361);
        mediaFormatCreateVideoFormat.setInteger("i-frame-interval", i6);
        return mediaFormatCreateVideoFormat;
    }

    private static boolean b(String str, MediaCodec mediaCodec, MediaFormat mediaFormat, int i2, int i3) {
        MediaCodecInfo.VideoCapabilities videoCapabilitiesA;
        if (TXCBuild.VersionInt() < 21 || mediaFormat == null || (videoCapabilitiesA = a(str, i2, i3)) == null) {
            return false;
        }
        Range<Integer> supportedWidths = videoCapabilitiesA.getSupportedWidths();
        Range<Integer> supportedHeights = videoCapabilitiesA.getSupportedHeights();
        if (supportedWidths != null && supportedHeights != null) {
            Integer numValueOf = (Integer) supportedWidths.getLower();
            Integer numValueOf2 = (Integer) supportedHeights.getLower();
            MediaCodecInfo.VideoCapabilities videoCapabilitiesA2 = a(mediaCodec, str);
            if (videoCapabilitiesA2 != null) {
                Range<Integer> supportedWidths2 = videoCapabilitiesA2.getSupportedWidths();
                Range<Integer> supportedHeights2 = videoCapabilitiesA2.getSupportedHeights();
                if (supportedWidths2 != null && supportedHeights2 != null) {
                    numValueOf = Integer.valueOf(Math.max(numValueOf.intValue(), ((Integer) supportedWidths2.getLower()).intValue()));
                    numValueOf2 = Integer.valueOf(Math.max(numValueOf2.intValue(), ((Integer) supportedHeights2.getLower()).intValue()));
                }
            }
            if (numValueOf.intValue() >= 0 && numValueOf2.intValue() >= 0) {
                int integer = mediaFormat.getInteger("width");
                int integer2 = mediaFormat.getInteger("height");
                if (numValueOf.intValue() <= integer && numValueOf2.intValue() <= integer2) {
                    return false;
                }
                float f2 = integer;
                float f3 = integer2;
                float fMax = Math.max(numValueOf2.intValue() / (1.0f * f3), numValueOf.intValue() / (f2 * 1.0f));
                int i4 = (int) (f2 * fMax);
                int i5 = (int) (fMax * f3);
                mediaFormat.setInteger("width", i4);
                mediaFormat.setInteger("height", i5);
                TXCLog.i(f20203a, "updateMediaFormatToLowerSize:lowerW:" + numValueOf + " lowerH:" + numValueOf2 + " fixLowW:" + i4 + " fixLowH:" + i5);
                return true;
            }
        }
        return false;
    }

    public static void a(MediaCodec mediaCodec, MediaFormat mediaFormat, String str, int i2, int i3, boolean z2) {
        MediaCodecInfo mediaCodecInfoA;
        MediaCodecInfo.CodecCapabilities capabilitiesForType;
        TXCLog.i(f20203a, "updateFormat: format:" + mediaFormat + "  mineType:" + str + "  bitrateMod:" + i2 + "  targetProfile:" + i3 + "  fullIFrame:" + z2);
        if (mediaFormat == null || (mediaCodecInfoA = a(str)) == null || (capabilitiesForType = mediaCodecInfoA.getCapabilitiesForType(str)) == null) {
            return;
        }
        int i4 = 0;
        int i5 = 0;
        for (MediaCodecInfo.CodecProfileLevel codecProfileLevel : capabilitiesForType.profileLevels) {
            int i6 = codecProfileLevel.profile;
            if (i6 <= i3 && (i6 > i4 || (i6 == i4 && codecProfileLevel.level > i5))) {
                i5 = codecProfileLevel.level;
                i4 = i6;
            }
        }
        mediaFormat.setInteger(Scopes.PROFILE, i4);
        TXCLog.i(f20203a, "createFormat: targetProfile:" + i4 + "  fixLevel:" + i5);
        mediaFormat.setInteger(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, i5);
        MediaCodecInfo.CodecCapabilities codecCapabilitiesCreateFromProfileLevel = MediaCodecInfo.CodecCapabilities.createFromProfileLevel(str, i4, i5);
        a(codecCapabilitiesCreateFromProfileLevel, i2, mediaFormat, z2);
        a(mediaFormat, codecCapabilitiesCreateFromProfileLevel);
        a(str, mediaCodec, mediaFormat, i4, i5);
    }

    private static void a(MediaCodecInfo.CodecCapabilities codecCapabilities, int i2, MediaFormat mediaFormat, boolean z2) {
        MediaCodecInfo.EncoderCapabilities encoderCapabilities;
        if (TXCBuild.VersionInt() < 21 || (encoderCapabilities = codecCapabilities.getEncoderCapabilities()) == null) {
            return;
        }
        if (encoderCapabilities.isBitrateModeSupported(i2)) {
            mediaFormat.setInteger("bitrate-mode", i2);
        } else if (z2) {
            if (encoderCapabilities.isBitrateModeSupported(1)) {
                mediaFormat.setInteger("bitrate-mode", 1);
            } else if (encoderCapabilities.isBitrateModeSupported(2)) {
                mediaFormat.setInteger("bitrate-mode", 2);
            }
        } else if (encoderCapabilities.isBitrateModeSupported(2)) {
            mediaFormat.setInteger("bitrate-mode", 2);
        }
        Range<Integer> complexityRange = encoderCapabilities.getComplexityRange();
        if (complexityRange != null) {
            mediaFormat.setInteger("complexity", ((Integer) complexityRange.getUpper()).intValue());
            TXCLog.i(f20203a, "createFormat:MediaFormat.KEY_COMPLEXITY :" + complexityRange.getUpper());
        }
    }

    private static boolean b(String str, MediaFormat mediaFormat, int i2, int i3) {
        MediaCodecInfo.VideoCapabilities videoCapabilitiesA;
        if (TXCBuild.VersionInt() < 21 || mediaFormat == null || (videoCapabilitiesA = a(str, i2, i3)) == null) {
            return false;
        }
        int widthAlignment = videoCapabilitiesA.getWidthAlignment();
        int heightAlignment = videoCapabilitiesA.getHeightAlignment();
        TXCLog.i(f20203a, "widthAlignment:" + widthAlignment + " heightAlignment:");
        if (widthAlignment >= 2 && heightAlignment >= 2 && widthAlignment % 2 == 0 && heightAlignment % 2 == 0) {
            int integer = mediaFormat.getInteger("width");
            int integer2 = mediaFormat.getInteger("height");
            int i4 = (integer / widthAlignment) * widthAlignment;
            int i5 = (integer2 / heightAlignment) * heightAlignment;
            if (integer == i4 && integer2 == i5) {
                return false;
            }
            mediaFormat.setInteger("width", i4);
            mediaFormat.setInteger("height", i5);
            TXCLog.i(f20203a, "updateMediaFormatWithAlignment: fixSize: src:" + integer + " " + integer2 + " fix:" + i4 + " " + i5 + " widthAlignment：" + widthAlignment + "  heightAlignment：" + heightAlignment);
            return true;
        }
        return false;
    }

    private static void a(MediaFormat mediaFormat, MediaCodecInfo.CodecCapabilities codecCapabilities) {
        MediaCodecInfo.VideoCapabilities videoCapabilities;
        if (codecCapabilities == null || mediaFormat == null || TXCBuild.VersionInt() < 21 || (videoCapabilities = codecCapabilities.getVideoCapabilities()) == null) {
            return;
        }
        Range<Integer> bitrateRange = videoCapabilities.getBitrateRange();
        TXCLog.i(f20203a, "bitrateRange: " + bitrateRange.getLower() + "  " + bitrateRange.getUpper());
        int integer = mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_BITRATE);
        TXCLog.i(f20203a, "bitrateRange: " + bitrateRange.getLower() + "  " + bitrateRange.getUpper() + ", bitrate = " + integer);
        if (integer < ((Integer) bitrateRange.getLower()).intValue()) {
            TXCLog.i(f20203a, "fix bitrate = " + integer + " to lower " + bitrateRange.getLower());
            integer = ((Integer) bitrateRange.getLower()).intValue();
        }
        if (integer > ((Integer) bitrateRange.getUpper()).intValue()) {
            TXCLog.i(f20203a, "fix bitrate = " + integer + " to upper " + bitrateRange.getUpper());
            integer = ((Integer) bitrateRange.getUpper()).intValue();
        }
        mediaFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, integer);
    }

    private static MediaCodecInfo a(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i2 = 0; i2 < codecCount; i2++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
            if (codecInfoAt.isEncoder()) {
                for (String str2 : codecInfoAt.getSupportedTypes()) {
                    if (str2.equalsIgnoreCase(str)) {
                        return codecInfoAt;
                    }
                }
            }
        }
        return null;
    }

    private static boolean a(String str, MediaCodec mediaCodec, MediaFormat mediaFormat, int i2, int i3) {
        return a(str, mediaFormat, i2, i3) || b(str, mediaCodec, mediaFormat, i2, i3) || b(str, mediaFormat, i2, i3);
    }

    private static boolean a(String str, MediaFormat mediaFormat, int i2, int i3) {
        MediaCodecInfo.VideoCapabilities videoCapabilitiesA;
        if (TXCBuild.VersionInt() < 21 || mediaFormat == null || (videoCapabilitiesA = a(str, i2, i3)) == null) {
            return false;
        }
        Range<Integer> supportedWidths = videoCapabilitiesA.getSupportedWidths();
        Integer num = -1;
        if (supportedWidths != null) {
            num = (Integer) supportedWidths.getUpper();
        }
        Range<Integer> supportedHeights = videoCapabilitiesA.getSupportedHeights();
        Integer num2 = supportedHeights != null ? (Integer) supportedHeights.getUpper() : -1;
        if (num.intValue() < 0 || num2.intValue() < 0) {
            return false;
        }
        int integer = mediaFormat.getInteger("width");
        int integer2 = mediaFormat.getInteger("height");
        if ((integer > integer2 && num.intValue() < num2.intValue()) || (integer < integer2 && num.intValue() > num2.intValue())) {
            Integer numValueOf = Integer.valueOf(num.intValue());
            num = num2;
            num2 = numValueOf;
        }
        TXCLog.i(f20203a, "updateToCodecSupportSize: srcWidth:" + integer + " srcHeight:" + integer2);
        if (num.intValue() >= integer && num2.intValue() >= integer2) {
            return false;
        }
        float f2 = integer;
        float f3 = integer2;
        float fMin = Math.min(num2.intValue() / (1.0f * f3), num.intValue() / (f2 * 1.0f));
        int i4 = (int) (f2 * fMin);
        int i5 = (int) (fMin * f3);
        mediaFormat.setInteger("width", i4);
        mediaFormat.setInteger("height", i5);
        TXCLog.i(f20203a, "updateMediaFormatToUpperSize:upperW:" + num + " upperH:" + num2 + " fixUpperW:" + i4 + " fixUpperH:" + i5);
        return true;
    }

    private static MediaCodecInfo.VideoCapabilities a(String str, int i2, int i3) {
        MediaCodecInfo.CodecCapabilities codecCapabilitiesCreateFromProfileLevel;
        if (TXCBuild.VersionInt() >= 21 && (codecCapabilitiesCreateFromProfileLevel = MediaCodecInfo.CodecCapabilities.createFromProfileLevel(str, i2, i3)) != null) {
            return codecCapabilitiesCreateFromProfileLevel.getVideoCapabilities();
        }
        return null;
    }

    private static MediaCodecInfo.VideoCapabilities a(MediaCodec mediaCodec, String str) {
        MediaCodecInfo.CodecCapabilities capabilitiesForType;
        if (mediaCodec == null || TXCBuild.VersionInt() < 21 || (capabilitiesForType = mediaCodec.getCodecInfo().getCapabilitiesForType(str)) == null) {
            return null;
        }
        return capabilitiesForType.getVideoCapabilities();
    }
}
