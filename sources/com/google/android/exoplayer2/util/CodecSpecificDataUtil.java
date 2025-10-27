package com.google.android.exoplayer2.util;

import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class CodecSpecificDataUtil {
    private static final byte[] NAL_START_CODE = {0, 0, 0, 1};
    private static final String[] HEVC_GENERAL_PROFILE_SPACE_STRINGS = {"", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C"};

    private CodecSpecificDataUtil() {
    }

    public static String buildAvcCodecString(int i2, int i3, int i4) {
        return String.format("avc1.%02X%02X%02X", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
    }

    public static List<byte[]> buildCea708InitializationData(boolean z2) {
        return Collections.singletonList(z2 ? new byte[]{1} : new byte[]{0});
    }

    public static String buildHevcCodecString(int i2, boolean z2, int i3, int i4, int[] iArr, int i5) {
        Object[] objArr = new Object[5];
        objArr[0] = HEVC_GENERAL_PROFILE_SPACE_STRINGS[i2];
        objArr[1] = Integer.valueOf(i3);
        objArr[2] = Integer.valueOf(i4);
        objArr[3] = Character.valueOf(z2 ? 'H' : 'L');
        objArr[4] = Integer.valueOf(i5);
        StringBuilder sb = new StringBuilder(Util.formatInvariant("hvc1.%s%d.%X.%c%d", objArr));
        int length = iArr.length;
        while (length > 0 && iArr[length - 1] == 0) {
            length--;
        }
        for (int i6 = 0; i6 < length; i6++) {
            sb.append(String.format(".%02X", Integer.valueOf(iArr[i6])));
        }
        return sb.toString();
    }

    public static byte[] buildNalUnit(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = NAL_START_CODE;
        byte[] bArr3 = new byte[bArr2.length + i3];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(bArr, i2, bArr3, bArr2.length, i3);
        return bArr3;
    }

    private static int findNalStartCode(byte[] bArr, int i2) {
        int length = bArr.length - NAL_START_CODE.length;
        while (i2 <= length) {
            if (isNalStartCode(bArr, i2)) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    private static boolean isNalStartCode(byte[] bArr, int i2) {
        if (bArr.length - i2 <= NAL_START_CODE.length) {
            return false;
        }
        int i3 = 0;
        while (true) {
            byte[] bArr2 = NAL_START_CODE;
            if (i3 >= bArr2.length) {
                return true;
            }
            if (bArr[i2 + i3] != bArr2[i3]) {
                return false;
            }
            i3++;
        }
    }

    public static Pair<Integer, Integer> parseAlacAudioSpecificConfig(byte[] bArr) {
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr);
        parsableByteArray.setPosition(9);
        int unsignedByte = parsableByteArray.readUnsignedByte();
        parsableByteArray.setPosition(20);
        return Pair.create(Integer.valueOf(parsableByteArray.readUnsignedIntToInt()), Integer.valueOf(unsignedByte));
    }

    public static boolean parseCea708InitializationData(List<byte[]> list) {
        return list.size() == 1 && list.get(0).length == 1 && list.get(0)[0] == 1;
    }

    @Nullable
    public static byte[][] splitNalUnits(byte[] bArr) {
        if (!isNalStartCode(bArr, 0)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int iFindNalStartCode = 0;
        do {
            arrayList.add(Integer.valueOf(iFindNalStartCode));
            iFindNalStartCode = findNalStartCode(bArr, iFindNalStartCode + NAL_START_CODE.length);
        } while (iFindNalStartCode != -1);
        byte[][] bArr2 = new byte[arrayList.size()][];
        int i2 = 0;
        while (i2 < arrayList.size()) {
            int iIntValue = ((Integer) arrayList.get(i2)).intValue();
            int iIntValue2 = (i2 < arrayList.size() + (-1) ? ((Integer) arrayList.get(i2 + 1)).intValue() : bArr.length) - iIntValue;
            byte[] bArr3 = new byte[iIntValue2];
            System.arraycopy(bArr, iIntValue, bArr3, 0, iIntValue2);
            bArr2[i2] = bArr3;
            i2++;
        }
        return bArr2;
    }
}
