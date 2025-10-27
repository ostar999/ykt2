package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import android.util.Base64;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/* loaded from: classes3.dex */
final class RtspMediaTrack {
    private static final String AAC_CODECS_PREFIX = "mp4a.40.";
    private static final String GENERIC_CONTROL_ATTR = "*";
    private static final String H264_CODECS_PREFIX = "avc1.";
    private static final String PARAMETER_PROFILE_LEVEL_ID = "profile-level-id";
    private static final String PARAMETER_SPROP_PARAMS = "sprop-parameter-sets";
    public final RtpPayloadFormat payloadFormat;
    public final Uri uri;

    public RtspMediaTrack(MediaDescription mediaDescription, Uri uri) {
        Assertions.checkArgument(mediaDescription.attributes.containsKey(SessionDescription.ATTR_CONTROL));
        this.payloadFormat = generatePayloadFormat(mediaDescription);
        this.uri = extractTrackUri(uri, (String) Util.castNonNull(mediaDescription.attributes.get(SessionDescription.ATTR_CONTROL)));
    }

    private static Uri extractTrackUri(Uri uri, String str) {
        Uri uri2 = Uri.parse(str);
        return uri2.isAbsolute() ? uri2 : str.equals("*") ? uri : uri.buildUpon().appendEncodedPath(str).build();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0071  */
    @androidx.annotation.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat generatePayloadFormat(com.google.android.exoplayer2.source.rtsp.MediaDescription r10) {
        /*
            com.google.android.exoplayer2.Format$Builder r0 = new com.google.android.exoplayer2.Format$Builder
            r0.<init>()
            int r1 = r10.bitrate
            if (r1 <= 0) goto Lc
            r0.setAverageBitrate(r1)
        Lc:
            com.google.android.exoplayer2.source.rtsp.MediaDescription$RtpMapAttribute r1 = r10.rtpMapAttribute
            int r2 = r1.payloadType
            java.lang.String r1 = r1.mediaEncoding
            java.lang.String r1 = com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat.getMimeTypeFromRtpMediaType(r1)
            r0.setSampleMimeType(r1)
            com.google.android.exoplayer2.source.rtsp.MediaDescription$RtpMapAttribute r3 = r10.rtpMapAttribute
            int r3 = r3.clockRate
            java.lang.String r4 = "audio"
            java.lang.String r5 = r10.mediaType
            boolean r4 = r4.equals(r5)
            r5 = -1
            if (r4 == 0) goto L38
            com.google.android.exoplayer2.source.rtsp.MediaDescription$RtpMapAttribute r4 = r10.rtpMapAttribute
            int r4 = r4.encodingParameters
            int r4 = inferChannelCount(r4, r1)
            com.google.android.exoplayer2.Format$Builder r6 = r0.setSampleRate(r3)
            r6.setChannelCount(r4)
            goto L39
        L38:
            r4 = r5
        L39:
            com.google.common.collect.ImmutableMap r10 = r10.getFmtpParametersAsMap()
            int r6 = r1.hashCode()
            r7 = -53558318(0xfffffffffccec3d2, float:-8.588679E36)
            r8 = 0
            r9 = 1
            if (r6 == r7) goto L67
            r7 = 187078296(0xb269698, float:3.208373E-32)
            if (r6 == r7) goto L5d
            r7 = 1331836730(0x4f62373a, float:3.7952701E9)
            if (r6 == r7) goto L53
            goto L71
        L53:
            java.lang.String r6 = "video/avc"
            boolean r1 = r1.equals(r6)
            if (r1 == 0) goto L71
            r1 = r9
            goto L72
        L5d:
            java.lang.String r6 = "audio/ac3"
            boolean r1 = r1.equals(r6)
            if (r1 == 0) goto L71
            r1 = 2
            goto L72
        L67:
            java.lang.String r6 = "audio/mp4a-latm"
            boolean r1 = r1.equals(r6)
            if (r1 == 0) goto L71
            r1 = r8
            goto L72
        L71:
            r1 = r5
        L72:
            if (r1 == 0) goto L83
            if (r1 == r9) goto L77
            goto L96
        L77:
            boolean r1 = r10.isEmpty()
            r1 = r1 ^ r9
            com.google.android.exoplayer2.util.Assertions.checkArgument(r1)
            processH264FmtpAttribute(r0, r10)
            goto L96
        L83:
            if (r4 == r5) goto L87
            r1 = r9
            goto L88
        L87:
            r1 = r8
        L88:
            com.google.android.exoplayer2.util.Assertions.checkArgument(r1)
            boolean r1 = r10.isEmpty()
            r1 = r1 ^ r9
            com.google.android.exoplayer2.util.Assertions.checkArgument(r1)
            processAacFmtpAttribute(r0, r10, r4, r3)
        L96:
            if (r3 <= 0) goto L9a
            r1 = r9
            goto L9b
        L9a:
            r1 = r8
        L9b:
            com.google.android.exoplayer2.util.Assertions.checkArgument(r1)
            r1 = 96
            if (r2 < r1) goto La3
            r8 = r9
        La3:
            com.google.android.exoplayer2.util.Assertions.checkArgument(r8)
            com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat r1 = new com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat
            com.google.android.exoplayer2.Format r0 = r0.build()
            r1.<init>(r0, r2, r3, r10)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.rtsp.RtspMediaTrack.generatePayloadFormat(com.google.android.exoplayer2.source.rtsp.MediaDescription):com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat");
    }

    private static byte[] getH264InitializationDataFromParameterSet(String str) {
        byte[] bArrDecode = Base64.decode(str, 0);
        int length = bArrDecode.length;
        byte[] bArr = NalUnitUtil.NAL_START_CODE;
        byte[] bArr2 = new byte[length + bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        System.arraycopy(bArrDecode, 0, bArr2, bArr.length, bArrDecode.length);
        return bArr2;
    }

    private static int inferChannelCount(int i2, String str) {
        return i2 != -1 ? i2 : str.equals(MimeTypes.AUDIO_AC3) ? 6 : 1;
    }

    private static void processAacFmtpAttribute(Format.Builder builder, ImmutableMap<String, String> immutableMap, int i2, int i3) {
        Assertions.checkArgument(immutableMap.containsKey("profile-level-id"));
        String strValueOf = String.valueOf((String) Assertions.checkNotNull(immutableMap.get("profile-level-id")));
        builder.setCodecs(strValueOf.length() != 0 ? AAC_CODECS_PREFIX.concat(strValueOf) : new String(AAC_CODECS_PREFIX));
        builder.setInitializationData(ImmutableList.of(AacUtil.buildAacLcAudioSpecificConfig(i3, i2)));
    }

    private static void processH264FmtpAttribute(Format.Builder builder, ImmutableMap<String, String> immutableMap) {
        Assertions.checkArgument(immutableMap.containsKey(PARAMETER_SPROP_PARAMS));
        String[] strArrSplit = Util.split((String) Assertions.checkNotNull(immutableMap.get(PARAMETER_SPROP_PARAMS)), ",");
        Assertions.checkArgument(strArrSplit.length == 2);
        ImmutableList immutableListOf = ImmutableList.of(getH264InitializationDataFromParameterSet(strArrSplit[0]), getH264InitializationDataFromParameterSet(strArrSplit[1]));
        builder.setInitializationData(immutableListOf);
        byte[] bArr = immutableListOf.get(0);
        NalUnitUtil.SpsData spsNalUnit = NalUnitUtil.parseSpsNalUnit(bArr, NalUnitUtil.NAL_START_CODE.length, bArr.length);
        builder.setPixelWidthHeightRatio(spsNalUnit.pixelWidthHeightRatio);
        builder.setHeight(spsNalUnit.height);
        builder.setWidth(spsNalUnit.width);
        String str = immutableMap.get("profile-level-id");
        if (str != null) {
            builder.setCodecs(str.length() != 0 ? H264_CODECS_PREFIX.concat(str) : new String(H264_CODECS_PREFIX));
        } else {
            builder.setCodecs(CodecSpecificDataUtil.buildAvcCodecString(spsNalUnit.profileIdc, spsNalUnit.constraintsFlagsAndReservedZero2Bits, spsNalUnit.levelIdc));
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || RtspMediaTrack.class != obj.getClass()) {
            return false;
        }
        RtspMediaTrack rtspMediaTrack = (RtspMediaTrack) obj;
        return this.payloadFormat.equals(rtspMediaTrack.payloadFormat) && this.uri.equals(rtspMediaTrack.uri);
    }

    public int hashCode() {
        return ((217 + this.payloadFormat.hashCode()) * 31) + this.uri.hashCode();
    }
}
