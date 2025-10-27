package com.google.common.hash;

import com.google.common.primitives.Longs;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

/* loaded from: classes4.dex */
final class LittleEndianByteArray {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final LittleEndianBytes byteArray;

    public enum JavaLittleEndianBytes implements LittleEndianBytes {
        INSTANCE { // from class: com.google.common.hash.LittleEndianByteArray.JavaLittleEndianBytes.1
            @Override // com.google.common.hash.LittleEndianByteArray.LittleEndianBytes
            public long getLongLittleEndian(byte[] bArr, int i2) {
                return Longs.fromBytes(bArr[i2 + 7], bArr[i2 + 6], bArr[i2 + 5], bArr[i2 + 4], bArr[i2 + 3], bArr[i2 + 2], bArr[i2 + 1], bArr[i2]);
            }

            @Override // com.google.common.hash.LittleEndianByteArray.LittleEndianBytes
            public void putLongLittleEndian(byte[] bArr, int i2, long j2) {
                long j3 = 255;
                for (int i3 = 0; i3 < 8; i3++) {
                    bArr[i2 + i3] = (byte) ((j2 & j3) >> (i3 * 8));
                    j3 <<= 8;
                }
            }
        }
    }

    public interface LittleEndianBytes {
        long getLongLittleEndian(byte[] bArr, int i2);

        void putLongLittleEndian(byte[] bArr, int i2, long j2);
    }

    public enum UnsafeByteArray implements LittleEndianBytes {
        UNSAFE_LITTLE_ENDIAN { // from class: com.google.common.hash.LittleEndianByteArray.UnsafeByteArray.1
            @Override // com.google.common.hash.LittleEndianByteArray.LittleEndianBytes
            public long getLongLittleEndian(byte[] bArr, int i2) {
                return UnsafeByteArray.theUnsafe.getLong(bArr, i2 + UnsafeByteArray.BYTE_ARRAY_BASE_OFFSET);
            }

            @Override // com.google.common.hash.LittleEndianByteArray.LittleEndianBytes
            public void putLongLittleEndian(byte[] bArr, int i2, long j2) {
                UnsafeByteArray.theUnsafe.putLong(bArr, i2 + UnsafeByteArray.BYTE_ARRAY_BASE_OFFSET, j2);
            }
        },
        UNSAFE_BIG_ENDIAN { // from class: com.google.common.hash.LittleEndianByteArray.UnsafeByteArray.2
            @Override // com.google.common.hash.LittleEndianByteArray.LittleEndianBytes
            public long getLongLittleEndian(byte[] bArr, int i2) {
                return Long.reverseBytes(UnsafeByteArray.theUnsafe.getLong(bArr, i2 + UnsafeByteArray.BYTE_ARRAY_BASE_OFFSET));
            }

            @Override // com.google.common.hash.LittleEndianByteArray.LittleEndianBytes
            public void putLongLittleEndian(byte[] bArr, int i2, long j2) {
                UnsafeByteArray.theUnsafe.putLong(bArr, i2 + UnsafeByteArray.BYTE_ARRAY_BASE_OFFSET, Long.reverseBytes(j2));
            }
        };

        private static final int BYTE_ARRAY_BASE_OFFSET;
        private static final Unsafe theUnsafe;

        static {
            Unsafe unsafe = getUnsafe();
            theUnsafe = unsafe;
            BYTE_ARRAY_BASE_OFFSET = unsafe.arrayBaseOffset(byte[].class);
            if (unsafe.arrayIndexScale(byte[].class) != 1) {
                throw new AssertionError();
            }
        }

        private static Unsafe getUnsafe() {
            try {
                try {
                    return Unsafe.getUnsafe();
                } catch (SecurityException unused) {
                    return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.common.hash.LittleEndianByteArray.UnsafeByteArray.3
                        @Override // java.security.PrivilegedExceptionAction
                        public Unsafe run() throws Exception {
                            for (Field field : Unsafe.class.getDeclaredFields()) {
                                field.setAccessible(true);
                                Object obj = field.get(null);
                                if (Unsafe.class.isInstance(obj)) {
                                    return (Unsafe) Unsafe.class.cast(obj);
                                }
                            }
                            throw new NoSuchFieldError("the Unsafe");
                        }
                    });
                }
            } catch (PrivilegedActionException e2) {
                throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
            }
        }
    }

    static {
        LittleEndianBytes littleEndianBytes = JavaLittleEndianBytes.INSTANCE;
        try {
            if ("amd64".equals(System.getProperty("os.arch"))) {
                littleEndianBytes = ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN) ? UnsafeByteArray.UNSAFE_LITTLE_ENDIAN : UnsafeByteArray.UNSAFE_BIG_ENDIAN;
            }
        } catch (Throwable unused) {
        }
        byteArray = littleEndianBytes;
    }

    private LittleEndianByteArray() {
    }

    public static int load32(byte[] bArr, int i2) {
        return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
    }

    public static long load64(byte[] bArr, int i2) {
        return byteArray.getLongLittleEndian(bArr, i2);
    }

    public static long load64Safely(byte[] bArr, int i2, int i3) {
        long j2 = 0;
        for (int i4 = 0; i4 < Math.min(i3, 8); i4++) {
            j2 |= (bArr[i2 + i4] & 255) << (i4 * 8);
        }
        return j2;
    }

    public static void store64(byte[] bArr, int i2, long j2) {
        byteArray.putLongLittleEndian(bArr, i2, j2);
    }

    public static boolean usingUnsafe() {
        return byteArray instanceof UnsafeByteArray;
    }
}
