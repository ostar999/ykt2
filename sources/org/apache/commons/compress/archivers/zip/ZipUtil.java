package org.apache.commons.compress.archivers.zip;

import androidx.core.view.InputDeviceCompat;
import cn.hutool.core.text.StrPool;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.CRC32;
import org.apache.commons.compress.archivers.zip.UnsupportedZipFeatureException;

/* loaded from: classes9.dex */
public abstract class ZipUtil {
    private static final byte[] DOS_TIME_MIN = ZipLong.getBytes(8448);

    public static long adjustToLong(int i2) {
        return i2 < 0 ? i2 + IjkMediaMeta.AV_CH_WIDE_RIGHT : i2;
    }

    public static long bigToLong(BigInteger bigInteger) {
        if (bigInteger.bitLength() <= 63) {
            return bigInteger.longValue();
        }
        throw new NumberFormatException("The BigInteger cannot fit inside a 64 bit java long: [" + bigInteger + StrPool.BRACKET_END);
    }

    public static boolean canHandleEntryData(ZipArchiveEntry zipArchiveEntry) {
        return supportsEncryptionOf(zipArchiveEntry) && supportsMethodOf(zipArchiveEntry);
    }

    public static void checkRequestedFeatures(ZipArchiveEntry zipArchiveEntry) throws UnsupportedZipFeatureException {
        if (!supportsEncryptionOf(zipArchiveEntry)) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.ENCRYPTION, zipArchiveEntry);
        }
        if (supportsMethodOf(zipArchiveEntry)) {
            return;
        }
        ZipMethod methodByCode = ZipMethod.getMethodByCode(zipArchiveEntry.getMethod());
        if (methodByCode != null) {
            throw new UnsupportedZipFeatureException(methodByCode, zipArchiveEntry);
        }
        throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.METHOD, zipArchiveEntry);
    }

    public static byte[] copy(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    public static long dosToJavaTime(long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, ((int) ((j2 >> 25) & 127)) + R2.attr.icon_pressed_bottom);
        calendar.set(2, ((int) ((j2 >> 21) & 15)) - 1);
        calendar.set(5, ((int) (j2 >> 16)) & 31);
        calendar.set(11, ((int) (j2 >> 11)) & 31);
        calendar.set(12, ((int) (j2 >> 5)) & 63);
        calendar.set(13, ((int) (j2 << 1)) & 62);
        calendar.set(14, 0);
        return calendar.getTime().getTime();
    }

    public static Date fromDosTime(ZipLong zipLong) {
        return new Date(dosToJavaTime(zipLong.getValue()));
    }

    private static String getUnicodeStringIfOriginalMatches(AbstractUnicodeExtraField abstractUnicodeExtraField, byte[] bArr) {
        if (abstractUnicodeExtraField != null) {
            CRC32 crc32 = new CRC32();
            crc32.update(bArr);
            if (crc32.getValue() == abstractUnicodeExtraField.getNameCRC32()) {
                try {
                    return ZipEncodingHelper.UTF8_ZIP_ENCODING.decode(abstractUnicodeExtraField.getUnicodeName());
                } catch (IOException unused) {
                }
            }
        }
        return null;
    }

    public static BigInteger longToBig(long j2) {
        if (j2 >= -2147483648L) {
            if (j2 < 0 && j2 >= -2147483648L) {
                j2 = adjustToLong((int) j2);
            }
            return BigInteger.valueOf(j2);
        }
        throw new IllegalArgumentException("Negative longs < -2^31 not permitted: [" + j2 + StrPool.BRACKET_END);
    }

    public static byte[] reverse(byte[] bArr) {
        int length = bArr.length - 1;
        for (int i2 = 0; i2 < bArr.length / 2; i2++) {
            byte b3 = bArr[i2];
            int i3 = length - i2;
            bArr[i2] = bArr[i3];
            bArr[i3] = b3;
        }
        return bArr;
    }

    public static void setNameAndCommentFromExtraFields(ZipArchiveEntry zipArchiveEntry, byte[] bArr, byte[] bArr2) {
        String unicodeStringIfOriginalMatches;
        UnicodePathExtraField unicodePathExtraField = (UnicodePathExtraField) zipArchiveEntry.getExtraField(UnicodePathExtraField.UPATH_ID);
        String name = zipArchiveEntry.getName();
        String unicodeStringIfOriginalMatches2 = getUnicodeStringIfOriginalMatches(unicodePathExtraField, bArr);
        if (unicodeStringIfOriginalMatches2 != null && !name.equals(unicodeStringIfOriginalMatches2)) {
            zipArchiveEntry.setName(unicodeStringIfOriginalMatches2);
        }
        if (bArr2 == null || bArr2.length <= 0 || (unicodeStringIfOriginalMatches = getUnicodeStringIfOriginalMatches((UnicodeCommentExtraField) zipArchiveEntry.getExtraField(UnicodeCommentExtraField.UCOM_ID), bArr2)) == null) {
            return;
        }
        zipArchiveEntry.setComment(unicodeStringIfOriginalMatches);
    }

    public static int signedByteToUnsignedInt(byte b3) {
        return b3 >= 0 ? b3 : b3 + 256;
    }

    private static boolean supportsEncryptionOf(ZipArchiveEntry zipArchiveEntry) {
        return !zipArchiveEntry.getGeneralPurposeBit().usesEncryption();
    }

    private static boolean supportsMethodOf(ZipArchiveEntry zipArchiveEntry) {
        return zipArchiveEntry.getMethod() == 0 || zipArchiveEntry.getMethod() == ZipMethod.UNSHRINKING.getCode() || zipArchiveEntry.getMethod() == ZipMethod.IMPLODING.getCode() || zipArchiveEntry.getMethod() == 8 || zipArchiveEntry.getMethod() == ZipMethod.BZIP2.getCode();
    }

    public static ZipLong toDosTime(Date date) {
        return new ZipLong(toDosTime(date.getTime()));
    }

    public static byte unsignedIntToSignedByte(int i2) {
        if (i2 <= 255 && i2 >= 0) {
            return i2 < 128 ? (byte) i2 : (byte) (i2 + InputDeviceCompat.SOURCE_ANY);
        }
        throw new IllegalArgumentException("Can only convert non-negative integers between [0,255] to byte: [" + i2 + StrPool.BRACKET_END);
    }

    public static byte[] toDosTime(long j2) {
        byte[] bArr = new byte[4];
        toDosTime(j2, bArr, 0);
        return bArr;
    }

    public static void copy(byte[] bArr, byte[] bArr2, int i2) {
        if (bArr != null) {
            System.arraycopy(bArr, 0, bArr2, i2, bArr.length);
        }
    }

    public static void toDosTime(long j2, byte[] bArr, int i2) {
        toDosTime(Calendar.getInstance(), j2, bArr, i2);
    }

    public static void toDosTime(Calendar calendar, long j2, byte[] bArr, int i2) {
        calendar.setTimeInMillis(j2);
        if (calendar.get(1) < 1980) {
            byte[] bArr2 = DOS_TIME_MIN;
            System.arraycopy(bArr2, 0, bArr, i2, bArr2.length);
        } else {
            ZipLong.putLong((calendar.get(13) >> 1) | ((r5 - R2.attr.icon_pressed_bottom) << 25) | ((calendar.get(2) + 1) << 21) | (calendar.get(5) << 16) | (calendar.get(11) << 11) | (calendar.get(12) << 5), bArr, i2);
        }
    }
}
