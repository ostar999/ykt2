package androidx.emoji2.text.flatbuffer;

import androidx.emoji2.text.flatbuffer.Utf8;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class Utf8Safe extends Utf8 {

    public static class UnpairedSurrogateException extends IllegalArgumentException {
        public UnpairedSurrogateException(int i2, int i3) {
            super("Unpaired surrogate at index " + i2 + " of " + i3);
        }
    }

    private static int computeEncodedLength(CharSequence charSequence) {
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int iEncodedLengthGeneral = length;
        while (true) {
            if (i2 < length) {
                char cCharAt = charSequence.charAt(i2);
                if (cCharAt >= 2048) {
                    iEncodedLengthGeneral += encodedLengthGeneral(charSequence, i2);
                    break;
                }
                iEncodedLengthGeneral += (127 - cCharAt) >>> 31;
                i2++;
            } else {
                break;
            }
        }
        if (iEncodedLengthGeneral >= length) {
            return iEncodedLengthGeneral;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (iEncodedLengthGeneral + IjkMediaMeta.AV_CH_WIDE_RIGHT));
    }

    public static String decodeUtf8Array(byte[] bArr, int i2, int i3) throws IllegalArgumentException {
        if ((i2 | i3 | ((bArr.length - i2) - i3)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        int i4 = i2 + i3;
        char[] cArr = new char[i3];
        int i5 = 0;
        while (i2 < i4) {
            byte b3 = bArr[i2];
            if (!Utf8.DecodeUtil.isOneByte(b3)) {
                break;
            }
            i2++;
            Utf8.DecodeUtil.handleOneByte(b3, cArr, i5);
            i5++;
        }
        int i6 = i5;
        while (i2 < i4) {
            int i7 = i2 + 1;
            byte b4 = bArr[i2];
            if (Utf8.DecodeUtil.isOneByte(b4)) {
                int i8 = i6 + 1;
                Utf8.DecodeUtil.handleOneByte(b4, cArr, i6);
                while (i7 < i4) {
                    byte b5 = bArr[i7];
                    if (!Utf8.DecodeUtil.isOneByte(b5)) {
                        break;
                    }
                    i7++;
                    Utf8.DecodeUtil.handleOneByte(b5, cArr, i8);
                    i8++;
                }
                i2 = i7;
                i6 = i8;
            } else if (Utf8.DecodeUtil.isTwoBytes(b4)) {
                if (i7 >= i4) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                Utf8.DecodeUtil.handleTwoBytes(b4, bArr[i7], cArr, i6);
                i2 = i7 + 1;
                i6++;
            } else if (Utf8.DecodeUtil.isThreeBytes(b4)) {
                if (i7 >= i4 - 1) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                int i9 = i7 + 1;
                Utf8.DecodeUtil.handleThreeBytes(b4, bArr[i7], bArr[i9], cArr, i6);
                i2 = i9 + 1;
                i6++;
            } else {
                if (i7 >= i4 - 2) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                int i10 = i7 + 1;
                byte b6 = bArr[i7];
                int i11 = i10 + 1;
                Utf8.DecodeUtil.handleFourBytes(b4, b6, bArr[i10], bArr[i11], cArr, i6);
                i2 = i11 + 1;
                i6 = i6 + 1 + 1;
            }
        }
        return new String(cArr, 0, i6);
    }

    public static String decodeUtf8Buffer(ByteBuffer byteBuffer, int i2, int i3) throws IllegalArgumentException {
        if ((i2 | i3 | ((byteBuffer.limit() - i2) - i3)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        int i4 = i2 + i3;
        char[] cArr = new char[i3];
        int i5 = 0;
        while (i2 < i4) {
            byte b3 = byteBuffer.get(i2);
            if (!Utf8.DecodeUtil.isOneByte(b3)) {
                break;
            }
            i2++;
            Utf8.DecodeUtil.handleOneByte(b3, cArr, i5);
            i5++;
        }
        int i6 = i5;
        while (i2 < i4) {
            int i7 = i2 + 1;
            byte b4 = byteBuffer.get(i2);
            if (Utf8.DecodeUtil.isOneByte(b4)) {
                int i8 = i6 + 1;
                Utf8.DecodeUtil.handleOneByte(b4, cArr, i6);
                while (i7 < i4) {
                    byte b5 = byteBuffer.get(i7);
                    if (!Utf8.DecodeUtil.isOneByte(b5)) {
                        break;
                    }
                    i7++;
                    Utf8.DecodeUtil.handleOneByte(b5, cArr, i8);
                    i8++;
                }
                i2 = i7;
                i6 = i8;
            } else if (Utf8.DecodeUtil.isTwoBytes(b4)) {
                if (i7 >= i4) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                Utf8.DecodeUtil.handleTwoBytes(b4, byteBuffer.get(i7), cArr, i6);
                i2 = i7 + 1;
                i6++;
            } else if (Utf8.DecodeUtil.isThreeBytes(b4)) {
                if (i7 >= i4 - 1) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                int i9 = i7 + 1;
                Utf8.DecodeUtil.handleThreeBytes(b4, byteBuffer.get(i7), byteBuffer.get(i9), cArr, i6);
                i2 = i9 + 1;
                i6++;
            } else {
                if (i7 >= i4 - 2) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                int i10 = i7 + 1;
                byte b6 = byteBuffer.get(i7);
                int i11 = i10 + 1;
                Utf8.DecodeUtil.handleFourBytes(b4, b6, byteBuffer.get(i10), byteBuffer.get(i11), cArr, i6);
                i2 = i11 + 1;
                i6 = i6 + 1 + 1;
            }
        }
        return new String(cArr, 0, i6);
    }

    private static int encodeUtf8Array(CharSequence charSequence, byte[] bArr, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        char cCharAt;
        int length = charSequence.length();
        int i7 = i3 + i2;
        int i8 = 0;
        while (i8 < length && (i6 = i8 + i2) < i7 && (cCharAt = charSequence.charAt(i8)) < 128) {
            bArr[i6] = (byte) cCharAt;
            i8++;
        }
        if (i8 == length) {
            return i2 + length;
        }
        int i9 = i2 + i8;
        while (i8 < length) {
            char cCharAt2 = charSequence.charAt(i8);
            if (cCharAt2 >= 128 || i9 >= i7) {
                if (cCharAt2 < 2048 && i9 <= i7 - 2) {
                    int i10 = i9 + 1;
                    bArr[i9] = (byte) ((cCharAt2 >>> 6) | 960);
                    i9 = i10 + 1;
                    bArr[i10] = (byte) ((cCharAt2 & '?') | 128);
                } else {
                    if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || i9 > i7 - 3) {
                        if (i9 > i7 - 4) {
                            if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i5 = i8 + 1) == charSequence.length() || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i5)))) {
                                throw new UnpairedSurrogateException(i8, length);
                            }
                            throw new ArrayIndexOutOfBoundsException("Failed writing " + cCharAt2 + " at index " + i9);
                        }
                        int i11 = i8 + 1;
                        if (i11 != charSequence.length()) {
                            char cCharAt3 = charSequence.charAt(i11);
                            if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                int i12 = i9 + 1;
                                bArr[i9] = (byte) ((codePoint >>> 18) | 240);
                                int i13 = i12 + 1;
                                bArr[i12] = (byte) (((codePoint >>> 12) & 63) | 128);
                                int i14 = i13 + 1;
                                bArr[i13] = (byte) (((codePoint >>> 6) & 63) | 128);
                                i9 = i14 + 1;
                                bArr[i14] = (byte) ((codePoint & 63) | 128);
                                i8 = i11;
                            } else {
                                i8 = i11;
                            }
                        }
                        throw new UnpairedSurrogateException(i8 - 1, length);
                    }
                    int i15 = i9 + 1;
                    bArr[i9] = (byte) ((cCharAt2 >>> '\f') | 480);
                    int i16 = i15 + 1;
                    bArr[i15] = (byte) (((cCharAt2 >>> 6) & 63) | 128);
                    i4 = i16 + 1;
                    bArr[i16] = (byte) ((cCharAt2 & '?') | 128);
                }
                i8++;
            } else {
                i4 = i9 + 1;
                bArr[i9] = (byte) cCharAt2;
            }
            i9 = i4;
            i8++;
        }
        return i9;
    }

    private static void encodeUtf8Buffer(CharSequence charSequence, ByteBuffer byteBuffer) {
        int length = charSequence.length();
        int iPosition = byteBuffer.position();
        int i2 = 0;
        while (i2 < length) {
            try {
                char cCharAt = charSequence.charAt(i2);
                if (cCharAt >= 128) {
                    break;
                }
                byteBuffer.put(iPosition + i2, (byte) cCharAt);
                i2++;
            } catch (IndexOutOfBoundsException unused) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i2) + " at index " + (byteBuffer.position() + Math.max(i2, (iPosition - byteBuffer.position()) + 1)));
            }
        }
        if (i2 == length) {
            byteBuffer.position(iPosition + i2);
            return;
        }
        iPosition += i2;
        while (i2 < length) {
            char cCharAt2 = charSequence.charAt(i2);
            if (cCharAt2 < 128) {
                byteBuffer.put(iPosition, (byte) cCharAt2);
            } else if (cCharAt2 < 2048) {
                int i3 = iPosition + 1;
                try {
                    byteBuffer.put(iPosition, (byte) ((cCharAt2 >>> 6) | 192));
                    byteBuffer.put(i3, (byte) ((cCharAt2 & '?') | 128));
                    iPosition = i3;
                } catch (IndexOutOfBoundsException unused2) {
                    iPosition = i3;
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i2) + " at index " + (byteBuffer.position() + Math.max(i2, (iPosition - byteBuffer.position()) + 1)));
                }
            } else {
                if (cCharAt2 >= 55296 && 57343 >= cCharAt2) {
                    int i4 = i2 + 1;
                    if (i4 != length) {
                        try {
                            char cCharAt3 = charSequence.charAt(i4);
                            if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                int i5 = iPosition + 1;
                                try {
                                    byteBuffer.put(iPosition, (byte) ((codePoint >>> 18) | 240));
                                    int i6 = i5 + 1;
                                    byteBuffer.put(i5, (byte) (((codePoint >>> 12) & 63) | 128));
                                    int i7 = i6 + 1;
                                    byteBuffer.put(i6, (byte) (((codePoint >>> 6) & 63) | 128));
                                    byteBuffer.put(i7, (byte) ((codePoint & 63) | 128));
                                    iPosition = i7;
                                    i2 = i4;
                                } catch (IndexOutOfBoundsException unused3) {
                                    iPosition = i5;
                                    i2 = i4;
                                    throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i2) + " at index " + (byteBuffer.position() + Math.max(i2, (iPosition - byteBuffer.position()) + 1)));
                                }
                            } else {
                                i2 = i4;
                            }
                        } catch (IndexOutOfBoundsException unused4) {
                        }
                    }
                    throw new UnpairedSurrogateException(i2, length);
                }
                int i8 = iPosition + 1;
                byteBuffer.put(iPosition, (byte) ((cCharAt2 >>> '\f') | 224));
                iPosition = i8 + 1;
                byteBuffer.put(i8, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                byteBuffer.put(iPosition, (byte) ((cCharAt2 & '?') | 128));
            }
            i2++;
            iPosition++;
        }
        byteBuffer.position(iPosition);
    }

    private static int encodedLengthGeneral(CharSequence charSequence, int i2) {
        int length = charSequence.length();
        int i3 = 0;
        while (i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            if (cCharAt < 2048) {
                i3 += (127 - cCharAt) >>> 31;
            } else {
                i3 += 2;
                if (55296 <= cCharAt && cCharAt <= 57343) {
                    if (Character.codePointAt(charSequence, i2) < 65536) {
                        throw new UnpairedSurrogateException(i2, length);
                    }
                    i2++;
                }
            }
            i2++;
        }
        return i3;
    }

    @Override // androidx.emoji2.text.flatbuffer.Utf8
    public String decodeUtf8(ByteBuffer byteBuffer, int i2, int i3) throws IllegalArgumentException {
        return byteBuffer.hasArray() ? decodeUtf8Array(byteBuffer.array(), byteBuffer.arrayOffset() + i2, i3) : decodeUtf8Buffer(byteBuffer, i2, i3);
    }

    @Override // androidx.emoji2.text.flatbuffer.Utf8
    public void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (!byteBuffer.hasArray()) {
            encodeUtf8Buffer(charSequence, byteBuffer);
        } else {
            int iArrayOffset = byteBuffer.arrayOffset();
            byteBuffer.position(encodeUtf8Array(charSequence, byteBuffer.array(), byteBuffer.position() + iArrayOffset, byteBuffer.remaining()) - iArrayOffset);
        }
    }

    @Override // androidx.emoji2.text.flatbuffer.Utf8
    public int encodedLength(CharSequence charSequence) {
        return computeEncodedLength(charSequence);
    }
}
