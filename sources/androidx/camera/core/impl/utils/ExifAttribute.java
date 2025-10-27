package androidx.camera.core.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.compress.archivers.tar.TarConstants;

@RequiresApi(21)
/* loaded from: classes.dex */
final class ExifAttribute {
    public static final long BYTES_OFFSET_UNKNOWN = -1;
    static final int IFD_FORMAT_BYTE = 1;
    static final int IFD_FORMAT_DOUBLE = 12;
    static final int IFD_FORMAT_SBYTE = 6;
    static final int IFD_FORMAT_SINGLE = 11;
    static final int IFD_FORMAT_SLONG = 9;
    static final int IFD_FORMAT_SRATIONAL = 10;
    static final int IFD_FORMAT_SSHORT = 8;
    static final int IFD_FORMAT_STRING = 2;
    static final int IFD_FORMAT_ULONG = 4;
    static final int IFD_FORMAT_UNDEFINED = 7;
    static final int IFD_FORMAT_URATIONAL = 5;
    static final int IFD_FORMAT_USHORT = 3;
    private static final String TAG = "ExifAttribute";
    public final byte[] bytes;
    public final long bytesOffset;
    public final int format;
    public final int numberOfComponents;
    static final Charset ASCII = StandardCharsets.US_ASCII;
    static final String[] IFD_FORMAT_NAMES = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE", "IFD"};
    static final int[] IFD_FORMAT_BYTES_PER_FORMAT = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
    static final byte[] EXIF_ASCII_PREFIX = {65, TarConstants.LF_GNUTYPE_SPARSE, 67, 73, 73, 0, 0, 0};

    public ExifAttribute(int i2, int i3, byte[] bArr) {
        this(i2, i3, -1L, bArr);
    }

    @NonNull
    public static ExifAttribute createByte(@NonNull String str) {
        if (str.length() == 1 && str.charAt(0) >= '0' && str.charAt(0) <= '1') {
            return new ExifAttribute(1, 1, new byte[]{(byte) (str.charAt(0) - '0')});
        }
        byte[] bytes = str.getBytes(ASCII);
        return new ExifAttribute(1, bytes.length, bytes);
    }

    @NonNull
    public static ExifAttribute createDouble(@NonNull double[] dArr, @NonNull ByteOrder byteOrder) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[IFD_FORMAT_BYTES_PER_FORMAT[12] * dArr.length]);
        byteBufferWrap.order(byteOrder);
        for (double d3 : dArr) {
            byteBufferWrap.putDouble(d3);
        }
        return new ExifAttribute(12, dArr.length, byteBufferWrap.array());
    }

    @NonNull
    public static ExifAttribute createSLong(@NonNull int[] iArr, @NonNull ByteOrder byteOrder) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[IFD_FORMAT_BYTES_PER_FORMAT[9] * iArr.length]);
        byteBufferWrap.order(byteOrder);
        for (int i2 : iArr) {
            byteBufferWrap.putInt(i2);
        }
        return new ExifAttribute(9, iArr.length, byteBufferWrap.array());
    }

    @NonNull
    public static ExifAttribute createSRational(@NonNull LongRational[] longRationalArr, @NonNull ByteOrder byteOrder) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[IFD_FORMAT_BYTES_PER_FORMAT[10] * longRationalArr.length]);
        byteBufferWrap.order(byteOrder);
        for (LongRational longRational : longRationalArr) {
            byteBufferWrap.putInt((int) longRational.getNumerator());
            byteBufferWrap.putInt((int) longRational.getDenominator());
        }
        return new ExifAttribute(10, longRationalArr.length, byteBufferWrap.array());
    }

    @NonNull
    public static ExifAttribute createString(@NonNull String str) {
        byte[] bytes = (str + (char) 0).getBytes(ASCII);
        return new ExifAttribute(2, bytes.length, bytes);
    }

    @NonNull
    public static ExifAttribute createULong(@NonNull long[] jArr, @NonNull ByteOrder byteOrder) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[IFD_FORMAT_BYTES_PER_FORMAT[4] * jArr.length]);
        byteBufferWrap.order(byteOrder);
        for (long j2 : jArr) {
            byteBufferWrap.putInt((int) j2);
        }
        return new ExifAttribute(4, jArr.length, byteBufferWrap.array());
    }

    @NonNull
    public static ExifAttribute createURational(@NonNull LongRational[] longRationalArr, @NonNull ByteOrder byteOrder) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[IFD_FORMAT_BYTES_PER_FORMAT[5] * longRationalArr.length]);
        byteBufferWrap.order(byteOrder);
        for (LongRational longRational : longRationalArr) {
            byteBufferWrap.putInt((int) longRational.getNumerator());
            byteBufferWrap.putInt((int) longRational.getDenominator());
        }
        return new ExifAttribute(5, longRationalArr.length, byteBufferWrap.array());
    }

    @NonNull
    public static ExifAttribute createUShort(@NonNull int[] iArr, @NonNull ByteOrder byteOrder) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[IFD_FORMAT_BYTES_PER_FORMAT[3] * iArr.length]);
        byteBufferWrap.order(byteOrder);
        for (int i2 : iArr) {
            byteBufferWrap.putShort((short) i2);
        }
        return new ExifAttribute(3, iArr.length, byteBufferWrap.array());
    }

    public double getDoubleValue(@NonNull ByteOrder byteOrder) throws Throwable {
        Object value = getValue(byteOrder);
        if (value == null) {
            throw new NumberFormatException("NULL can't be converted to a double value");
        }
        if (value instanceof String) {
            return Double.parseDouble((String) value);
        }
        if (value instanceof long[]) {
            if (((long[]) value).length == 1) {
                return r5[0];
            }
            throw new NumberFormatException("There are more than one component");
        }
        if (value instanceof int[]) {
            if (((int[]) value).length == 1) {
                return r5[0];
            }
            throw new NumberFormatException("There are more than one component");
        }
        if (value instanceof double[]) {
            double[] dArr = (double[]) value;
            if (dArr.length == 1) {
                return dArr[0];
            }
            throw new NumberFormatException("There are more than one component");
        }
        if (!(value instanceof LongRational[])) {
            throw new NumberFormatException("Couldn't find a double value");
        }
        LongRational[] longRationalArr = (LongRational[]) value;
        if (longRationalArr.length == 1) {
            return longRationalArr[0].toDouble();
        }
        throw new NumberFormatException("There are more than one component");
    }

    public int getIntValue(@NonNull ByteOrder byteOrder) throws Throwable {
        Object value = getValue(byteOrder);
        if (value == null) {
            throw new NumberFormatException("NULL can't be converted to a integer value");
        }
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        if (value instanceof long[]) {
            long[] jArr = (long[]) value;
            if (jArr.length == 1) {
                return (int) jArr[0];
            }
            throw new NumberFormatException("There are more than one component");
        }
        if (!(value instanceof int[])) {
            throw new NumberFormatException("Couldn't find a integer value");
        }
        int[] iArr = (int[]) value;
        if (iArr.length == 1) {
            return iArr[0];
        }
        throw new NumberFormatException("There are more than one component");
    }

    @Nullable
    public String getStringValue(@NonNull ByteOrder byteOrder) throws Throwable {
        Object value = getValue(byteOrder);
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        if (value instanceof long[]) {
            long[] jArr = (long[]) value;
            while (i2 < jArr.length) {
                sb.append(jArr[i2]);
                i2++;
                if (i2 != jArr.length) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        if (value instanceof int[]) {
            int[] iArr = (int[]) value;
            while (i2 < iArr.length) {
                sb.append(iArr[i2]);
                i2++;
                if (i2 != iArr.length) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        if (value instanceof double[]) {
            double[] dArr = (double[]) value;
            while (i2 < dArr.length) {
                sb.append(dArr[i2]);
                i2++;
                if (i2 != dArr.length) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        if (!(value instanceof LongRational[])) {
            return null;
        }
        LongRational[] longRationalArr = (LongRational[]) value;
        while (i2 < longRationalArr.length) {
            sb.append(longRationalArr[i2].getNumerator());
            sb.append('/');
            sb.append(longRationalArr[i2].getDenominator());
            i2++;
            if (i2 != longRationalArr.length) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0198: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]) (LINE:409), block:B:151:0x0198 */
    /* JADX WARN: Removed duplicated region for block: B:182:0x019b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object getValue(java.nio.ByteOrder r11) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 448
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.utils.ExifAttribute.getValue(java.nio.ByteOrder):java.lang.Object");
    }

    public int size() {
        return IFD_FORMAT_BYTES_PER_FORMAT[this.format] * this.numberOfComponents;
    }

    public String toString() {
        return "(" + IFD_FORMAT_NAMES[this.format] + ", data length:" + this.bytes.length + ")";
    }

    public ExifAttribute(int i2, int i3, long j2, byte[] bArr) {
        this.format = i2;
        this.numberOfComponents = i3;
        this.bytesOffset = j2;
        this.bytes = bArr;
    }

    @NonNull
    public static ExifAttribute createDouble(double d3, @NonNull ByteOrder byteOrder) {
        return createDouble(new double[]{d3}, byteOrder);
    }

    @NonNull
    public static ExifAttribute createSLong(int i2, @NonNull ByteOrder byteOrder) {
        return createSLong(new int[]{i2}, byteOrder);
    }

    @NonNull
    public static ExifAttribute createULong(long j2, @NonNull ByteOrder byteOrder) {
        return createULong(new long[]{j2}, byteOrder);
    }

    @NonNull
    public static ExifAttribute createUShort(int i2, @NonNull ByteOrder byteOrder) {
        return createUShort(new int[]{i2}, byteOrder);
    }

    @NonNull
    public static ExifAttribute createSRational(@NonNull LongRational longRational, @NonNull ByteOrder byteOrder) {
        return createSRational(new LongRational[]{longRational}, byteOrder);
    }

    @NonNull
    public static ExifAttribute createURational(@NonNull LongRational longRational, @NonNull ByteOrder byteOrder) {
        return createURational(new LongRational[]{longRational}, byteOrder);
    }
}
