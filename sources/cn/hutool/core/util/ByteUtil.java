package cn.hutool.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

/* loaded from: classes.dex */
public class ByteUtil {
    public static final ByteOrder CPU_ENDIAN;
    public static final ByteOrder DEFAULT_ORDER;

    static {
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        DEFAULT_ORDER = byteOrder;
        if (!"little".equals(System.getProperty("sun.cpu.endian"))) {
            byteOrder = ByteOrder.BIG_ENDIAN;
        }
        CPU_ENDIAN = byteOrder;
    }

    public static int byteToUnsignedInt(byte b3) {
        return b3 & 255;
    }

    public static double bytesToDouble(byte[] bArr) {
        return bytesToDouble(bArr, DEFAULT_ORDER);
    }

    public static float bytesToFloat(byte[] bArr) {
        return bytesToFloat(bArr, DEFAULT_ORDER);
    }

    public static int bytesToInt(byte[] bArr) {
        return bytesToInt(bArr, DEFAULT_ORDER);
    }

    public static long bytesToLong(byte[] bArr) {
        return bytesToLong(bArr, DEFAULT_ORDER);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.util.concurrent.atomic.LongAdder] */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.util.concurrent.atomic.AtomicLong] */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.util.concurrent.atomic.AtomicInteger] */
    public static <T extends Number> T bytesToNumber(byte[] bArr, Class<T> cls, ByteOrder byteOrder) throws IllegalArgumentException {
        DoubleAdder atomicLong;
        if (Byte.class == cls) {
            return Byte.valueOf(bArr[0]);
        }
        if (Short.class == cls) {
            return Short.valueOf(bytesToShort(bArr, byteOrder));
        }
        if (Integer.class == cls) {
            return Integer.valueOf(bytesToInt(bArr, byteOrder));
        }
        if (AtomicInteger.class == cls) {
            atomicLong = new AtomicInteger(bytesToInt(bArr, byteOrder));
        } else {
            if (Long.class == cls) {
                return Long.valueOf(bytesToLong(bArr, byteOrder));
            }
            if (AtomicLong.class == cls) {
                atomicLong = new AtomicLong(bytesToLong(bArr, byteOrder));
            } else if (LongAdder.class == cls) {
                ?? longAdder = new LongAdder();
                longAdder.add(bytesToLong(bArr, byteOrder));
                atomicLong = longAdder;
            } else {
                if (Float.class == cls) {
                    return Float.valueOf(bytesToFloat(bArr, byteOrder));
                }
                if (Double.class == cls) {
                    return Double.valueOf(bytesToDouble(bArr, byteOrder));
                }
                if (DoubleAdder.class != cls) {
                    if (BigDecimal.class == cls) {
                        return NumberUtil.toBigDecimal(Double.valueOf(bytesToDouble(bArr, byteOrder)));
                    }
                    if (BigInteger.class == cls) {
                        return BigInteger.valueOf(bytesToLong(bArr, byteOrder));
                    }
                    if (Number.class == cls) {
                        return Double.valueOf(bytesToDouble(bArr, byteOrder));
                    }
                    throw new IllegalArgumentException("Unsupported Number type: " + cls.getName());
                }
                DoubleAdder doubleAdder = new DoubleAdder();
                doubleAdder.add(bytesToDouble(bArr, byteOrder));
                atomicLong = doubleAdder;
            }
        }
        return atomicLong;
    }

    public static short bytesToShort(byte[] bArr) {
        return bytesToShort(bArr, DEFAULT_ORDER);
    }

    public static byte[] doubleToBytes(double d3) {
        return doubleToBytes(d3, DEFAULT_ORDER);
    }

    public static byte[] floatToBytes(float f2) {
        return floatToBytes(f2, DEFAULT_ORDER);
    }

    public static byte intToByte(int i2) {
        return (byte) i2;
    }

    public static byte[] intToBytes(int i2) {
        return intToBytes(i2, DEFAULT_ORDER);
    }

    public static byte[] longToBytes(long j2) {
        return longToBytes(j2, DEFAULT_ORDER);
    }

    public static byte[] numberToBytes(Number number) {
        return numberToBytes(number, DEFAULT_ORDER);
    }

    public static byte[] shortToBytes(short s2) {
        return shortToBytes(s2, DEFAULT_ORDER);
    }

    public static double bytesToDouble(byte[] bArr, ByteOrder byteOrder) {
        return Double.longBitsToDouble(bytesToLong(bArr, byteOrder));
    }

    public static float bytesToFloat(byte[] bArr, ByteOrder byteOrder) {
        return Float.intBitsToFloat(bytesToInt(bArr, byteOrder));
    }

    public static int bytesToInt(byte[] bArr, ByteOrder byteOrder) {
        return bytesToInt(bArr, 0, byteOrder);
    }

    public static long bytesToLong(byte[] bArr, ByteOrder byteOrder) {
        return bytesToLong(bArr, 0, byteOrder);
    }

    public static short bytesToShort(byte[] bArr, ByteOrder byteOrder) {
        return bytesToShort(bArr, 0, byteOrder);
    }

    public static byte[] doubleToBytes(double d3, ByteOrder byteOrder) {
        return longToBytes(Double.doubleToLongBits(d3), byteOrder);
    }

    public static byte[] floatToBytes(float f2, ByteOrder byteOrder) {
        return intToBytes(Float.floatToIntBits(f2), byteOrder);
    }

    public static byte[] intToBytes(int i2, ByteOrder byteOrder) {
        return ByteOrder.LITTLE_ENDIAN == byteOrder ? new byte[]{(byte) (i2 & 255), (byte) ((i2 >> 8) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 24) & 255)} : new byte[]{(byte) ((i2 >> 24) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
    }

    public static byte[] longToBytes(long j2, ByteOrder byteOrder) {
        byte[] bArr = new byte[8];
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            for (int i2 = 0; i2 < 8; i2++) {
                bArr[i2] = (byte) (j2 & 255);
                j2 >>= 8;
            }
        } else {
            for (int i3 = 7; i3 >= 0; i3--) {
                bArr[i3] = (byte) (j2 & 255);
                j2 >>= 8;
            }
        }
        return bArr;
    }

    public static byte[] numberToBytes(Number number, ByteOrder byteOrder) {
        return number instanceof Byte ? new byte[]{number.byteValue()} : number instanceof Double ? doubleToBytes(((Double) number).doubleValue(), byteOrder) : number instanceof Long ? longToBytes(((Long) number).longValue(), byteOrder) : number instanceof Integer ? intToBytes(((Integer) number).intValue(), byteOrder) : number instanceof Short ? shortToBytes(((Short) number).shortValue(), byteOrder) : number instanceof Float ? floatToBytes(((Float) number).floatValue(), byteOrder) : doubleToBytes(number.doubleValue(), byteOrder);
    }

    public static byte[] shortToBytes(short s2, ByteOrder byteOrder) {
        byte[] bArr = new byte[2];
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            bArr[0] = (byte) (s2 & 255);
            bArr[1] = (byte) ((s2 >> 8) & 255);
        } else {
            bArr[1] = (byte) (s2 & 255);
            bArr[0] = (byte) ((s2 >> 8) & 255);
        }
        return bArr;
    }

    public static int bytesToInt(byte[] bArr, int i2, ByteOrder byteOrder) {
        int i3;
        byte b3;
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            i3 = (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
            b3 = bArr[i2 + 3];
        } else {
            i3 = (bArr[i2 + 3] & 255) | ((bArr[i2 + 2] & 255) << 8) | ((bArr[i2 + 1] & 255) << 16);
            b3 = bArr[i2];
        }
        return ((b3 & 255) << 24) | i3;
    }

    public static long bytesToLong(byte[] bArr, int i2, ByteOrder byteOrder) {
        long j2 = 0;
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            for (int i3 = 7; i3 >= 0; i3--) {
                j2 = (j2 << 8) | (bArr[i3 + i2] & 255);
            }
        } else {
            for (int i4 = 0; i4 < 8; i4++) {
                j2 = (j2 << 8) | (bArr[i4 + i2] & 255);
            }
        }
        return j2;
    }

    public static short bytesToShort(byte[] bArr, int i2, ByteOrder byteOrder) {
        int i3;
        byte b3;
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            i3 = bArr[i2] & 255;
            b3 = bArr[i2 + 1];
        } else {
            i3 = bArr[i2 + 1] & 255;
            b3 = bArr[i2];
        }
        return (short) (((b3 & 255) << 8) | i3);
    }
}
