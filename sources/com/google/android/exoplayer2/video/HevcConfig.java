package com.google.android.exoplayer2.video;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class HevcConfig {
    private static final int SPS_NAL_UNIT_TYPE = 33;

    @Nullable
    public final String codecs;
    public final int height;
    public final List<byte[]> initializationData;
    public final int nalUnitLengthFieldLength;
    public final float pixelWidthHeightRatio;
    public final int width;

    private HevcConfig(List<byte[]> list, int i2, int i3, int i4, float f2, @Nullable String str) {
        this.initializationData = list;
        this.nalUnitLengthFieldLength = i2;
        this.width = i3;
        this.height = i4;
        this.pixelWidthHeightRatio = f2;
        this.codecs = str;
    }

    public static HevcConfig parse(ParsableByteArray parsableByteArray) throws ParserException {
        int i2;
        int i3;
        try {
            parsableByteArray.skipBytes(21);
            int unsignedByte = parsableByteArray.readUnsignedByte() & 3;
            int unsignedByte2 = parsableByteArray.readUnsignedByte();
            int position = parsableByteArray.getPosition();
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < unsignedByte2; i6++) {
                parsableByteArray.skipBytes(1);
                int unsignedShort = parsableByteArray.readUnsignedShort();
                for (int i7 = 0; i7 < unsignedShort; i7++) {
                    int unsignedShort2 = parsableByteArray.readUnsignedShort();
                    i5 += unsignedShort2 + 4;
                    parsableByteArray.skipBytes(unsignedShort2);
                }
            }
            parsableByteArray.setPosition(position);
            byte[] bArr = new byte[i5];
            int i8 = -1;
            int i9 = -1;
            float f2 = 1.0f;
            String strBuildHevcCodecString = null;
            int i10 = 0;
            int i11 = 0;
            while (i10 < unsignedByte2) {
                int unsignedByte3 = parsableByteArray.readUnsignedByte() & 127;
                int unsignedShort3 = parsableByteArray.readUnsignedShort();
                int i12 = i4;
                while (i12 < unsignedShort3) {
                    int unsignedShort4 = parsableByteArray.readUnsignedShort();
                    byte[] bArr2 = NalUnitUtil.NAL_START_CODE;
                    int i13 = unsignedByte2;
                    System.arraycopy(bArr2, i4, bArr, i11, bArr2.length);
                    int length = i11 + bArr2.length;
                    System.arraycopy(parsableByteArray.getData(), parsableByteArray.getPosition(), bArr, length, unsignedShort4);
                    if (unsignedByte3 == 33 && i12 == 0) {
                        NalUnitUtil.H265SpsData h265SpsNalUnit = NalUnitUtil.parseH265SpsNalUnit(bArr, length, length + unsignedShort4);
                        int i14 = h265SpsNalUnit.width;
                        i9 = h265SpsNalUnit.height;
                        f2 = h265SpsNalUnit.pixelWidthHeightRatio;
                        i2 = unsignedByte3;
                        i3 = unsignedShort3;
                        i8 = i14;
                        strBuildHevcCodecString = CodecSpecificDataUtil.buildHevcCodecString(h265SpsNalUnit.generalProfileSpace, h265SpsNalUnit.generalTierFlag, h265SpsNalUnit.generalProfileIdc, h265SpsNalUnit.generalProfileCompatibilityFlags, h265SpsNalUnit.constraintBytes, h265SpsNalUnit.generalLevelIdc);
                    } else {
                        i2 = unsignedByte3;
                        i3 = unsignedShort3;
                    }
                    i11 = length + unsignedShort4;
                    parsableByteArray.skipBytes(unsignedShort4);
                    i12++;
                    unsignedByte2 = i13;
                    unsignedByte3 = i2;
                    unsignedShort3 = i3;
                    i4 = 0;
                }
                i10++;
                i4 = 0;
            }
            return new HevcConfig(i5 == 0 ? Collections.emptyList() : Collections.singletonList(bArr), unsignedByte + 1, i8, i9, f2, strBuildHevcCodecString);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw ParserException.createForMalformedContainer("Error parsing HEVC config", e2);
        }
    }
}
