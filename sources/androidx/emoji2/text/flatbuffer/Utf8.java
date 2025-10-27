package androidx.emoji2.text.flatbuffer;

import com.google.common.base.Ascii;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public abstract class Utf8 {
    private static Utf8 DEFAULT;

    public static class DecodeUtil {
        public static void handleFourBytes(byte b3, byte b4, byte b5, byte b6, char[] cArr, int i2) throws IllegalArgumentException {
            if (isNotTrailingByte(b4) || (((b3 << Ascii.FS) + (b4 + 112)) >> 30) != 0 || isNotTrailingByte(b5) || isNotTrailingByte(b6)) {
                throw new IllegalArgumentException("Invalid UTF-8");
            }
            int iTrailingByteValue = ((b3 & 7) << 18) | (trailingByteValue(b4) << 12) | (trailingByteValue(b5) << 6) | trailingByteValue(b6);
            cArr[i2] = highSurrogate(iTrailingByteValue);
            cArr[i2 + 1] = lowSurrogate(iTrailingByteValue);
        }

        public static void handleOneByte(byte b3, char[] cArr, int i2) {
            cArr[i2] = (char) b3;
        }

        public static void handleThreeBytes(byte b3, byte b4, byte b5, char[] cArr, int i2) throws IllegalArgumentException {
            if (isNotTrailingByte(b4) || ((b3 == -32 && b4 < -96) || ((b3 == -19 && b4 >= -96) || isNotTrailingByte(b5)))) {
                throw new IllegalArgumentException("Invalid UTF-8");
            }
            cArr[i2] = (char) (((b3 & 15) << 12) | (trailingByteValue(b4) << 6) | trailingByteValue(b5));
        }

        public static void handleTwoBytes(byte b3, byte b4, char[] cArr, int i2) throws IllegalArgumentException {
            if (b3 < -62) {
                throw new IllegalArgumentException("Invalid UTF-8: Illegal leading byte in 2 bytes utf");
            }
            if (isNotTrailingByte(b4)) {
                throw new IllegalArgumentException("Invalid UTF-8: Illegal trailing byte in 2 bytes utf");
            }
            cArr[i2] = (char) (((b3 & Ascii.US) << 6) | trailingByteValue(b4));
        }

        private static char highSurrogate(int i2) {
            return (char) ((i2 >>> 10) + okio.Utf8.HIGH_SURROGATE_HEADER);
        }

        private static boolean isNotTrailingByte(byte b3) {
            return b3 > -65;
        }

        public static boolean isOneByte(byte b3) {
            return b3 >= 0;
        }

        public static boolean isThreeBytes(byte b3) {
            return b3 < -16;
        }

        public static boolean isTwoBytes(byte b3) {
            return b3 < -32;
        }

        private static char lowSurrogate(int i2) {
            return (char) ((i2 & 1023) + okio.Utf8.LOG_SURROGATE_HEADER);
        }

        private static int trailingByteValue(byte b3) {
            return b3 & okio.Utf8.REPLACEMENT_BYTE;
        }
    }

    public static class UnpairedSurrogateException extends IllegalArgumentException {
        public UnpairedSurrogateException(int i2, int i3) {
            super("Unpaired surrogate at index " + i2 + " of " + i3);
        }
    }

    public static Utf8 getDefault() {
        if (DEFAULT == null) {
            DEFAULT = new Utf8Safe();
        }
        return DEFAULT;
    }

    public static void setDefault(Utf8 utf8) {
        DEFAULT = utf8;
    }

    public abstract String decodeUtf8(ByteBuffer byteBuffer, int i2, int i3);

    public abstract void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer);

    public abstract int encodedLength(CharSequence charSequence);
}
