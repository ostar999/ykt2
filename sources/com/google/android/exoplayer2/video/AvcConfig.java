package com.google.android.exoplayer2.video;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class AvcConfig {

    @Nullable
    public final String codecs;
    public final int height;
    public final List<byte[]> initializationData;
    public final int nalUnitLengthFieldLength;
    public final float pixelWidthHeightRatio;
    public final int width;

    private AvcConfig(List<byte[]> list, int i2, int i3, int i4, float f2, @Nullable String str) {
        this.initializationData = list;
        this.nalUnitLengthFieldLength = i2;
        this.width = i3;
        this.height = i4;
        this.pixelWidthHeightRatio = f2;
        this.codecs = str;
    }

    private static byte[] buildNalUnitForChild(ParsableByteArray parsableByteArray) {
        int unsignedShort = parsableByteArray.readUnsignedShort();
        int position = parsableByteArray.getPosition();
        parsableByteArray.skipBytes(unsignedShort);
        return CodecSpecificDataUtil.buildNalUnit(parsableByteArray.getData(), position, unsignedShort);
    }

    public static AvcConfig parse(ParsableByteArray parsableByteArray) throws ParserException {
        int i2;
        int i3;
        float f2;
        String strBuildAvcCodecString;
        try {
            parsableByteArray.skipBytes(4);
            int unsignedByte = (parsableByteArray.readUnsignedByte() & 3) + 1;
            if (unsignedByte == 3) {
                throw new IllegalStateException();
            }
            ArrayList arrayList = new ArrayList();
            int unsignedByte2 = parsableByteArray.readUnsignedByte() & 31;
            for (int i4 = 0; i4 < unsignedByte2; i4++) {
                arrayList.add(buildNalUnitForChild(parsableByteArray));
            }
            int unsignedByte3 = parsableByteArray.readUnsignedByte();
            for (int i5 = 0; i5 < unsignedByte3; i5++) {
                arrayList.add(buildNalUnitForChild(parsableByteArray));
            }
            if (unsignedByte2 > 0) {
                NalUnitUtil.SpsData spsNalUnit = NalUnitUtil.parseSpsNalUnit((byte[]) arrayList.get(0), unsignedByte, ((byte[]) arrayList.get(0)).length);
                int i6 = spsNalUnit.width;
                int i7 = spsNalUnit.height;
                float f3 = spsNalUnit.pixelWidthHeightRatio;
                strBuildAvcCodecString = CodecSpecificDataUtil.buildAvcCodecString(spsNalUnit.profileIdc, spsNalUnit.constraintsFlagsAndReservedZero2Bits, spsNalUnit.levelIdc);
                i2 = i6;
                i3 = i7;
                f2 = f3;
            } else {
                i2 = -1;
                i3 = -1;
                f2 = 1.0f;
                strBuildAvcCodecString = null;
            }
            return new AvcConfig(arrayList, unsignedByte, i2, i3, f2, strBuildAvcCodecString);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw ParserException.createForMalformedContainer("Error parsing AVC config", e2);
        }
    }
}
