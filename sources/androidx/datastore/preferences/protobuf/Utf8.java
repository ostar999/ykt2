package androidx.datastore.preferences.protobuf;

import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
final class Utf8 {
    private static final long ASCII_MASK_LONG = -9187201950435737472L;
    public static final int COMPLETE = 0;
    public static final int MALFORMED = -1;
    static final int MAX_BYTES_PER_CHAR = 3;
    private static final int UNSAFE_COUNT_ASCII_THRESHOLD = 16;
    private static final Processor processor;

    public static class DecodeUtil {
        private DecodeUtil() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleFourBytes(byte b3, byte b4, byte b5, byte b6, char[] cArr, int i2) throws InvalidProtocolBufferException {
            if (isNotTrailingByte(b4) || (((b3 << Ascii.FS) + (b4 + 112)) >> 30) != 0 || isNotTrailingByte(b5) || isNotTrailingByte(b6)) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            int iTrailingByteValue = ((b3 & 7) << 18) | (trailingByteValue(b4) << 12) | (trailingByteValue(b5) << 6) | trailingByteValue(b6);
            cArr[i2] = highSurrogate(iTrailingByteValue);
            cArr[i2 + 1] = lowSurrogate(iTrailingByteValue);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleOneByte(byte b3, char[] cArr, int i2) {
            cArr[i2] = (char) b3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleThreeBytes(byte b3, byte b4, byte b5, char[] cArr, int i2) throws InvalidProtocolBufferException {
            if (isNotTrailingByte(b4) || ((b3 == -32 && b4 < -96) || ((b3 == -19 && b4 >= -96) || isNotTrailingByte(b5)))) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            cArr[i2] = (char) (((b3 & 15) << 12) | (trailingByteValue(b4) << 6) | trailingByteValue(b5));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleTwoBytes(byte b3, byte b4, char[] cArr, int i2) throws InvalidProtocolBufferException {
            if (b3 < -62 || isNotTrailingByte(b4)) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            cArr[i2] = (char) (((b3 & Ascii.US) << 6) | trailingByteValue(b4));
        }

        private static char highSurrogate(int i2) {
            return (char) ((i2 >>> 10) + okio.Utf8.HIGH_SURROGATE_HEADER);
        }

        private static boolean isNotTrailingByte(byte b3) {
            return b3 > -65;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isOneByte(byte b3) {
            return b3 >= 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isThreeBytes(byte b3) {
            return b3 < -16;
        }

        /* JADX INFO: Access modifiers changed from: private */
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

    public static abstract class Processor {
        public final String decodeUtf8(ByteBuffer byteBuffer, int i2, int i3) throws InvalidProtocolBufferException {
            if (byteBuffer.hasArray()) {
                return decodeUtf8(byteBuffer.array(), byteBuffer.arrayOffset() + i2, i3);
            }
            return byteBuffer.isDirect() ? decodeUtf8Direct(byteBuffer, i2, i3) : decodeUtf8Default(byteBuffer, i2, i3);
        }

        public abstract String decodeUtf8(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException;

        public final String decodeUtf8Default(ByteBuffer byteBuffer, int i2, int i3) throws InvalidProtocolBufferException {
            if ((i2 | i3 | ((byteBuffer.limit() - i2) - i3)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i2), Integer.valueOf(i3)));
            }
            int i4 = i2 + i3;
            char[] cArr = new char[i3];
            int i5 = 0;
            while (i2 < i4) {
                byte b3 = byteBuffer.get(i2);
                if (!DecodeUtil.isOneByte(b3)) {
                    break;
                }
                i2++;
                DecodeUtil.handleOneByte(b3, cArr, i5);
                i5++;
            }
            int i6 = i5;
            while (i2 < i4) {
                int i7 = i2 + 1;
                byte b4 = byteBuffer.get(i2);
                if (DecodeUtil.isOneByte(b4)) {
                    int i8 = i6 + 1;
                    DecodeUtil.handleOneByte(b4, cArr, i6);
                    while (i7 < i4) {
                        byte b5 = byteBuffer.get(i7);
                        if (!DecodeUtil.isOneByte(b5)) {
                            break;
                        }
                        i7++;
                        DecodeUtil.handleOneByte(b5, cArr, i8);
                        i8++;
                    }
                    i2 = i7;
                    i6 = i8;
                } else if (DecodeUtil.isTwoBytes(b4)) {
                    if (i7 >= i4) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    DecodeUtil.handleTwoBytes(b4, byteBuffer.get(i7), cArr, i6);
                    i2 = i7 + 1;
                    i6++;
                } else if (DecodeUtil.isThreeBytes(b4)) {
                    if (i7 >= i4 - 1) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i9 = i7 + 1;
                    DecodeUtil.handleThreeBytes(b4, byteBuffer.get(i7), byteBuffer.get(i9), cArr, i6);
                    i2 = i9 + 1;
                    i6++;
                } else {
                    if (i7 >= i4 - 2) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i10 = i7 + 1;
                    byte b6 = byteBuffer.get(i7);
                    int i11 = i10 + 1;
                    DecodeUtil.handleFourBytes(b4, b6, byteBuffer.get(i10), byteBuffer.get(i11), cArr, i6);
                    i2 = i11 + 1;
                    i6 = i6 + 1 + 1;
                }
            }
            return new String(cArr, 0, i6);
        }

        public abstract String decodeUtf8Direct(ByteBuffer byteBuffer, int i2, int i3) throws InvalidProtocolBufferException;

        public abstract int encodeUtf8(CharSequence charSequence, byte[] bArr, int i2, int i3);

        public final void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer) {
            if (byteBuffer.hasArray()) {
                int iArrayOffset = byteBuffer.arrayOffset();
                byteBuffer.position(Utf8.encode(charSequence, byteBuffer.array(), byteBuffer.position() + iArrayOffset, byteBuffer.remaining()) - iArrayOffset);
            } else if (byteBuffer.isDirect()) {
                encodeUtf8Direct(charSequence, byteBuffer);
            } else {
                encodeUtf8Default(charSequence, byteBuffer);
            }
        }

        public final void encodeUtf8Default(CharSequence charSequence, ByteBuffer byteBuffer) {
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

        public abstract void encodeUtf8Direct(CharSequence charSequence, ByteBuffer byteBuffer);

        public final boolean isValidUtf8(byte[] bArr, int i2, int i3) {
            return partialIsValidUtf8(0, bArr, i2, i3) == 0;
        }

        public final int partialIsValidUtf8(int i2, ByteBuffer byteBuffer, int i3, int i4) {
            if (!byteBuffer.hasArray()) {
                return byteBuffer.isDirect() ? partialIsValidUtf8Direct(i2, byteBuffer, i3, i4) : partialIsValidUtf8Default(i2, byteBuffer, i3, i4);
            }
            int iArrayOffset = byteBuffer.arrayOffset();
            return partialIsValidUtf8(i2, byteBuffer.array(), i3 + iArrayOffset, iArrayOffset + i4);
        }

        public abstract int partialIsValidUtf8(int i2, byte[] bArr, int i3, int i4);

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0017, code lost:
        
            if (r8.get(r9) > (-65)) goto L13;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x004c, code lost:
        
            if (r8.get(r9) > (-65)) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x008f, code lost:
        
            if (r8.get(r7) > (-65)) goto L53;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int partialIsValidUtf8Default(int r7, java.nio.ByteBuffer r8, int r9, int r10) {
            /*
                r6 = this;
                if (r7 == 0) goto L92
                if (r9 < r10) goto L5
                return r7
            L5:
                byte r0 = (byte) r7
                r1 = -32
                r2 = -1
                r3 = -65
                if (r0 >= r1) goto L1e
                r7 = -62
                if (r0 < r7) goto L1d
                int r7 = r9 + 1
                byte r9 = r8.get(r9)
                if (r9 <= r3) goto L1a
                goto L1d
            L1a:
                r9 = r7
                goto L92
            L1d:
                return r2
            L1e:
                r4 = -16
                if (r0 >= r4) goto L4f
                int r7 = r7 >> 8
                int r7 = ~r7
                byte r7 = (byte) r7
                if (r7 != 0) goto L38
                int r7 = r9 + 1
                byte r9 = r8.get(r9)
                if (r7 < r10) goto L35
                int r7 = androidx.datastore.preferences.protobuf.Utf8.access$000(r0, r9)
                return r7
            L35:
                r5 = r9
                r9 = r7
                r7 = r5
            L38:
                if (r7 > r3) goto L4e
                r4 = -96
                if (r0 != r1) goto L40
                if (r7 < r4) goto L4e
            L40:
                r1 = -19
                if (r0 != r1) goto L46
                if (r7 >= r4) goto L4e
            L46:
                int r7 = r9 + 1
                byte r9 = r8.get(r9)
                if (r9 <= r3) goto L1a
            L4e:
                return r2
            L4f:
                int r1 = r7 >> 8
                int r1 = ~r1
                byte r1 = (byte) r1
                if (r1 != 0) goto L64
                int r7 = r9 + 1
                byte r1 = r8.get(r9)
                if (r7 < r10) goto L62
                int r7 = androidx.datastore.preferences.protobuf.Utf8.access$000(r0, r1)
                return r7
            L62:
                r9 = 0
                goto L6a
            L64:
                int r7 = r7 >> 16
                byte r7 = (byte) r7
                r5 = r9
                r9 = r7
                r7 = r5
            L6a:
                if (r9 != 0) goto L7c
                int r9 = r7 + 1
                byte r7 = r8.get(r7)
                if (r9 < r10) goto L79
                int r7 = androidx.datastore.preferences.protobuf.Utf8.access$100(r0, r1, r7)
                return r7
            L79:
                r5 = r9
                r9 = r7
                r7 = r5
            L7c:
                if (r1 > r3) goto L91
                int r0 = r0 << 28
                int r1 = r1 + 112
                int r0 = r0 + r1
                int r0 = r0 >> 30
                if (r0 != 0) goto L91
                if (r9 > r3) goto L91
                int r9 = r7 + 1
                byte r7 = r8.get(r7)
                if (r7 <= r3) goto L92
            L91:
                return r2
            L92:
                int r7 = partialIsValidUtf8(r8, r9, r10)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.Utf8.Processor.partialIsValidUtf8Default(int, java.nio.ByteBuffer, int, int):int");
        }

        public abstract int partialIsValidUtf8Direct(int i2, ByteBuffer byteBuffer, int i3, int i4);

        public final boolean isValidUtf8(ByteBuffer byteBuffer, int i2, int i3) {
            return partialIsValidUtf8(0, byteBuffer, i2, i3) == 0;
        }

        private static int partialIsValidUtf8(ByteBuffer byteBuffer, int i2, int i3) {
            int iEstimateConsecutiveAscii = i2 + Utf8.estimateConsecutiveAscii(byteBuffer, i2, i3);
            while (iEstimateConsecutiveAscii < i3) {
                int i4 = iEstimateConsecutiveAscii + 1;
                byte b3 = byteBuffer.get(iEstimateConsecutiveAscii);
                if (b3 < 0) {
                    if (b3 < -32) {
                        if (i4 >= i3) {
                            return b3;
                        }
                        if (b3 < -62 || byteBuffer.get(i4) > -65) {
                            return -1;
                        }
                        i4++;
                    } else {
                        if (b3 >= -16) {
                            if (i4 >= i3 - 2) {
                                return Utf8.incompleteStateFor(byteBuffer, b3, i4, i3 - i4);
                            }
                            int i5 = i4 + 1;
                            byte b4 = byteBuffer.get(i4);
                            if (b4 <= -65 && (((b3 << Ascii.FS) + (b4 + 112)) >> 30) == 0) {
                                int i6 = i5 + 1;
                                if (byteBuffer.get(i5) <= -65) {
                                    i4 = i6 + 1;
                                    if (byteBuffer.get(i6) > -65) {
                                    }
                                }
                            }
                            return -1;
                        }
                        if (i4 >= i3 - 1) {
                            return Utf8.incompleteStateFor(byteBuffer, b3, i4, i3 - i4);
                        }
                        int i7 = i4 + 1;
                        byte b5 = byteBuffer.get(i4);
                        if (b5 > -65 || ((b3 == -32 && b5 < -96) || ((b3 == -19 && b5 >= -96) || byteBuffer.get(i7) > -65))) {
                            return -1;
                        }
                        iEstimateConsecutiveAscii = i7 + 1;
                    }
                }
                iEstimateConsecutiveAscii = i4;
            }
            return 0;
        }
    }

    public static class UnpairedSurrogateException extends IllegalArgumentException {
        public UnpairedSurrogateException(int i2, int i3) {
            super("Unpaired surrogate at index " + i2 + " of " + i3);
        }
    }

    public static final class UnsafeProcessor extends Processor {
        public static boolean isAvailable() {
            return UnsafeUtil.hasUnsafeArrayOperations() && UnsafeUtil.hasUnsafeByteBufferOperations();
        }

        private static int unsafeEstimateConsecutiveAscii(byte[] bArr, long j2, int i2) {
            int i3 = 0;
            if (i2 < 16) {
                return 0;
            }
            while (i3 < i2) {
                long j3 = 1 + j2;
                if (UnsafeUtil.getByte(bArr, j2) < 0) {
                    return i3;
                }
                i3++;
                j2 = j3;
            }
            return i2;
        }

        private static int unsafeIncompleteStateFor(byte[] bArr, int i2, long j2, int i3) {
            if (i3 == 0) {
                return Utf8.incompleteStateFor(i2);
            }
            if (i3 == 1) {
                return Utf8.incompleteStateFor(i2, UnsafeUtil.getByte(bArr, j2));
            }
            if (i3 == 2) {
                return Utf8.incompleteStateFor(i2, UnsafeUtil.getByte(bArr, j2), UnsafeUtil.getByte(bArr, j2 + 1));
            }
            throw new AssertionError();
        }

        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        public String decodeUtf8(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException {
            if ((i2 | i3 | ((bArr.length - i2) - i3)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)));
            }
            int i4 = i2 + i3;
            char[] cArr = new char[i3];
            int i5 = 0;
            while (i2 < i4) {
                byte b3 = UnsafeUtil.getByte(bArr, i2);
                if (!DecodeUtil.isOneByte(b3)) {
                    break;
                }
                i2++;
                DecodeUtil.handleOneByte(b3, cArr, i5);
                i5++;
            }
            int i6 = i5;
            while (i2 < i4) {
                int i7 = i2 + 1;
                byte b4 = UnsafeUtil.getByte(bArr, i2);
                if (DecodeUtil.isOneByte(b4)) {
                    int i8 = i6 + 1;
                    DecodeUtil.handleOneByte(b4, cArr, i6);
                    while (i7 < i4) {
                        byte b5 = UnsafeUtil.getByte(bArr, i7);
                        if (!DecodeUtil.isOneByte(b5)) {
                            break;
                        }
                        i7++;
                        DecodeUtil.handleOneByte(b5, cArr, i8);
                        i8++;
                    }
                    i2 = i7;
                    i6 = i8;
                } else if (DecodeUtil.isTwoBytes(b4)) {
                    if (i7 >= i4) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    DecodeUtil.handleTwoBytes(b4, UnsafeUtil.getByte(bArr, i7), cArr, i6);
                    i2 = i7 + 1;
                    i6++;
                } else if (DecodeUtil.isThreeBytes(b4)) {
                    if (i7 >= i4 - 1) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i9 = i7 + 1;
                    DecodeUtil.handleThreeBytes(b4, UnsafeUtil.getByte(bArr, i7), UnsafeUtil.getByte(bArr, i9), cArr, i6);
                    i2 = i9 + 1;
                    i6++;
                } else {
                    if (i7 >= i4 - 2) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i10 = i7 + 1;
                    byte b6 = UnsafeUtil.getByte(bArr, i7);
                    int i11 = i10 + 1;
                    DecodeUtil.handleFourBytes(b4, b6, UnsafeUtil.getByte(bArr, i10), UnsafeUtil.getByte(bArr, i11), cArr, i6);
                    i2 = i11 + 1;
                    i6 = i6 + 1 + 1;
                }
            }
            return new String(cArr, 0, i6);
        }

        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        public String decodeUtf8Direct(ByteBuffer byteBuffer, int i2, int i3) throws InvalidProtocolBufferException {
            if ((i2 | i3 | ((byteBuffer.limit() - i2) - i3)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i2), Integer.valueOf(i3)));
            }
            long jAddressOffset = UnsafeUtil.addressOffset(byteBuffer) + i2;
            long j2 = i3 + jAddressOffset;
            char[] cArr = new char[i3];
            int i4 = 0;
            while (jAddressOffset < j2) {
                byte b3 = UnsafeUtil.getByte(jAddressOffset);
                if (!DecodeUtil.isOneByte(b3)) {
                    break;
                }
                jAddressOffset++;
                DecodeUtil.handleOneByte(b3, cArr, i4);
                i4++;
            }
            while (true) {
                int i5 = i4;
                while (jAddressOffset < j2) {
                    long j3 = jAddressOffset + 1;
                    byte b4 = UnsafeUtil.getByte(jAddressOffset);
                    if (DecodeUtil.isOneByte(b4)) {
                        int i6 = i5 + 1;
                        DecodeUtil.handleOneByte(b4, cArr, i5);
                        while (j3 < j2) {
                            byte b5 = UnsafeUtil.getByte(j3);
                            if (!DecodeUtil.isOneByte(b5)) {
                                break;
                            }
                            j3++;
                            DecodeUtil.handleOneByte(b5, cArr, i6);
                            i6++;
                        }
                        i5 = i6;
                        jAddressOffset = j3;
                    } else if (DecodeUtil.isTwoBytes(b4)) {
                        if (j3 >= j2) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        jAddressOffset = j3 + 1;
                        DecodeUtil.handleTwoBytes(b4, UnsafeUtil.getByte(j3), cArr, i5);
                        i5++;
                    } else if (DecodeUtil.isThreeBytes(b4)) {
                        if (j3 >= j2 - 1) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        long j4 = j3 + 1;
                        DecodeUtil.handleThreeBytes(b4, UnsafeUtil.getByte(j3), UnsafeUtil.getByte(j4), cArr, i5);
                        i5++;
                        jAddressOffset = j4 + 1;
                    } else {
                        if (j3 >= j2 - 2) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        long j5 = j3 + 1;
                        byte b6 = UnsafeUtil.getByte(j3);
                        long j6 = j5 + 1;
                        byte b7 = UnsafeUtil.getByte(j5);
                        jAddressOffset = j6 + 1;
                        DecodeUtil.handleFourBytes(b4, b6, b7, UnsafeUtil.getByte(j6), cArr, i5);
                        i4 = i5 + 1 + 1;
                    }
                }
                return new String(cArr, 0, i5);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        public int encodeUtf8(CharSequence charSequence, byte[] bArr, int i2, int i3) {
            char c3;
            long j2;
            long j3;
            long j4;
            char c4;
            int i4;
            char cCharAt;
            long j5 = i2;
            long j6 = i3 + j5;
            int length = charSequence.length();
            if (length > i3 || bArr.length - i3 < i2) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(length - 1) + " at index " + (i2 + i3));
            }
            int i5 = 0;
            while (true) {
                c3 = 128;
                j2 = 1;
                if (i5 >= length || (cCharAt = charSequence.charAt(i5)) >= 128) {
                    break;
                }
                UnsafeUtil.putByte(bArr, j5, (byte) cCharAt);
                i5++;
                j5 = 1 + j5;
            }
            if (i5 == length) {
                return (int) j5;
            }
            while (i5 < length) {
                char cCharAt2 = charSequence.charAt(i5);
                if (cCharAt2 < c3 && j5 < j6) {
                    long j7 = j5 + j2;
                    UnsafeUtil.putByte(bArr, j5, (byte) cCharAt2);
                    j4 = j2;
                    j3 = j7;
                    c4 = c3;
                } else if (cCharAt2 < 2048 && j5 <= j6 - 2) {
                    long j8 = j5 + j2;
                    UnsafeUtil.putByte(bArr, j5, (byte) ((cCharAt2 >>> 6) | 960));
                    long j9 = j8 + j2;
                    UnsafeUtil.putByte(bArr, j8, (byte) ((cCharAt2 & '?') | 128));
                    long j10 = j2;
                    c4 = 128;
                    j3 = j9;
                    j4 = j10;
                } else {
                    if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || j5 > j6 - 3) {
                        if (j5 > j6 - 4) {
                            if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i4 = i5 + 1) == length || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i4)))) {
                                throw new UnpairedSurrogateException(i5, length);
                            }
                            throw new ArrayIndexOutOfBoundsException("Failed writing " + cCharAt2 + " at index " + j5);
                        }
                        int i6 = i5 + 1;
                        if (i6 != length) {
                            char cCharAt3 = charSequence.charAt(i6);
                            if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                long j11 = j5 + 1;
                                UnsafeUtil.putByte(bArr, j5, (byte) ((codePoint >>> 18) | 240));
                                long j12 = j11 + 1;
                                c4 = 128;
                                UnsafeUtil.putByte(bArr, j11, (byte) (((codePoint >>> 12) & 63) | 128));
                                long j13 = j12 + 1;
                                UnsafeUtil.putByte(bArr, j12, (byte) (((codePoint >>> 6) & 63) | 128));
                                j4 = 1;
                                j3 = j13 + 1;
                                UnsafeUtil.putByte(bArr, j13, (byte) ((codePoint & 63) | 128));
                                i5 = i6;
                            } else {
                                i5 = i6;
                            }
                        }
                        throw new UnpairedSurrogateException(i5 - 1, length);
                    }
                    long j14 = j5 + j2;
                    UnsafeUtil.putByte(bArr, j5, (byte) ((cCharAt2 >>> '\f') | 480));
                    long j15 = j14 + j2;
                    UnsafeUtil.putByte(bArr, j14, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                    UnsafeUtil.putByte(bArr, j15, (byte) ((cCharAt2 & '?') | 128));
                    j3 = j15 + 1;
                    j4 = 1;
                    c4 = 128;
                }
                i5++;
                c3 = c4;
                long j16 = j4;
                j5 = j3;
                j2 = j16;
            }
            return (int) j5;
        }

        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        public void encodeUtf8Direct(CharSequence charSequence, ByteBuffer byteBuffer) {
            char c3;
            long j2;
            int i2;
            int i3;
            long j3;
            char c4;
            char cCharAt;
            long jAddressOffset = UnsafeUtil.addressOffset(byteBuffer);
            long jPosition = byteBuffer.position() + jAddressOffset;
            long jLimit = byteBuffer.limit() + jAddressOffset;
            int length = charSequence.length();
            if (length > jLimit - jPosition) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(length - 1) + " at index " + byteBuffer.limit());
            }
            int i4 = 0;
            while (true) {
                c3 = 128;
                if (i4 >= length || (cCharAt = charSequence.charAt(i4)) >= 128) {
                    break;
                }
                UnsafeUtil.putByte(jPosition, (byte) cCharAt);
                i4++;
                jPosition++;
            }
            if (i4 == length) {
                byteBuffer.position((int) (jPosition - jAddressOffset));
                return;
            }
            while (i4 < length) {
                char cCharAt2 = charSequence.charAt(i4);
                if (cCharAt2 >= c3 || jPosition >= jLimit) {
                    if (cCharAt2 >= 2048 || jPosition > jLimit - 2) {
                        j2 = jAddressOffset;
                        if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || jPosition > jLimit - 3) {
                            if (jPosition > jLimit - 4) {
                                if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i2 = i4 + 1) == length || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i2)))) {
                                    throw new UnpairedSurrogateException(i4, length);
                                }
                                throw new ArrayIndexOutOfBoundsException("Failed writing " + cCharAt2 + " at index " + jPosition);
                            }
                            i3 = i4 + 1;
                            if (i3 != length) {
                                char cCharAt3 = charSequence.charAt(i3);
                                if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                    int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                    j3 = jLimit;
                                    long j4 = jPosition + 1;
                                    UnsafeUtil.putByte(jPosition, (byte) ((codePoint >>> 18) | 240));
                                    long j5 = j4 + 1;
                                    c4 = 128;
                                    UnsafeUtil.putByte(j4, (byte) (((codePoint >>> 12) & 63) | 128));
                                    long j6 = j5 + 1;
                                    UnsafeUtil.putByte(j5, (byte) (((codePoint >>> 6) & 63) | 128));
                                    UnsafeUtil.putByte(j6, (byte) ((codePoint & 63) | 128));
                                    jPosition = j6 + 1;
                                } else {
                                    i4 = i3;
                                }
                            }
                            throw new UnpairedSurrogateException(i4 - 1, length);
                        }
                        long j7 = jPosition + 1;
                        UnsafeUtil.putByte(jPosition, (byte) ((cCharAt2 >>> '\f') | 480));
                        long j8 = j7 + 1;
                        UnsafeUtil.putByte(j7, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                        UnsafeUtil.putByte(j8, (byte) ((cCharAt2 & '?') | 128));
                        jPosition = j8 + 1;
                    } else {
                        j2 = jAddressOffset;
                        long j9 = jPosition + 1;
                        UnsafeUtil.putByte(jPosition, (byte) ((cCharAt2 >>> 6) | 960));
                        UnsafeUtil.putByte(j9, (byte) ((cCharAt2 & '?') | 128));
                        jPosition = j9 + 1;
                    }
                    j3 = jLimit;
                    i3 = i4;
                    c4 = 128;
                } else {
                    UnsafeUtil.putByte(jPosition, (byte) cCharAt2);
                    j3 = jLimit;
                    i3 = i4;
                    c4 = c3;
                    jPosition++;
                    j2 = jAddressOffset;
                }
                c3 = c4;
                jAddressOffset = j2;
                jLimit = j3;
                i4 = i3 + 1;
            }
            byteBuffer.position((int) (jPosition - jAddressOffset));
        }

        /* JADX WARN: Code restructure failed: missing block: B:35:0x0059, code lost:
        
            if (androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r13, r2) > (-65)) goto L38;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x009e, code lost:
        
            if (androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r13, r2) > (-65)) goto L59;
         */
        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int partialIsValidUtf8(int r12, byte[] r13, int r14, int r15) {
            /*
                Method dump skipped, instructions count: 204
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(int, byte[], int, int):int");
        }

        /* JADX WARN: Code restructure failed: missing block: B:35:0x0063, code lost:
        
            if (androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r2) > (-65)) goto L38;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x00a8, code lost:
        
            if (androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r2) > (-65)) goto L59;
         */
        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int partialIsValidUtf8Direct(int r11, java.nio.ByteBuffer r12, int r13, int r14) {
            /*
                Method dump skipped, instructions count: 217
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8Direct(int, java.nio.ByteBuffer, int, int):int");
        }

        private static int unsafeEstimateConsecutiveAscii(long j2, int i2) {
            if (i2 < 16) {
                return 0;
            }
            int i3 = 8 - (((int) j2) & 7);
            int i4 = i3;
            while (i4 > 0) {
                long j3 = 1 + j2;
                if (UnsafeUtil.getByte(j2) < 0) {
                    return i3 - i4;
                }
                i4--;
                j2 = j3;
            }
            int i5 = i2 - i3;
            while (i5 >= 8 && (UnsafeUtil.getLong(j2) & Utf8.ASCII_MASK_LONG) == 0) {
                j2 += 8;
                i5 -= 8;
            }
            return i2 - i5;
        }

        private static int unsafeIncompleteStateFor(long j2, int i2, int i3) {
            if (i3 == 0) {
                return Utf8.incompleteStateFor(i2);
            }
            if (i3 == 1) {
                return Utf8.incompleteStateFor(i2, UnsafeUtil.getByte(j2));
            }
            if (i3 == 2) {
                return Utf8.incompleteStateFor(i2, UnsafeUtil.getByte(j2), UnsafeUtil.getByte(j2 + 1));
            }
            throw new AssertionError();
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0039, code lost:
        
            return -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0063, code lost:
        
            return -1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static int partialIsValidUtf8(byte[] r8, long r9, int r11) {
            /*
                int r0 = unsafeEstimateConsecutiveAscii(r8, r9, r11)
                int r11 = r11 - r0
                long r0 = (long) r0
                long r9 = r9 + r0
            L7:
                r0 = 0
                r1 = r0
            L9:
                r2 = 1
                if (r11 <= 0) goto L1a
                long r4 = r9 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r8, r9)
                if (r1 < 0) goto L19
                int r11 = r11 + (-1)
                r9 = r4
                goto L9
            L19:
                r9 = r4
            L1a:
                if (r11 != 0) goto L1d
                return r0
            L1d:
                int r11 = r11 + (-1)
                r0 = -32
                r4 = -65
                r5 = -1
                if (r1 >= r0) goto L3a
                if (r11 != 0) goto L29
                return r1
            L29:
                int r11 = r11 + (-1)
                r0 = -62
                if (r1 < r0) goto L39
                long r2 = r2 + r9
                byte r9 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r8, r9)
                if (r9 <= r4) goto L37
                goto L39
            L37:
                r9 = r2
                goto L7
            L39:
                return r5
            L3a:
                r6 = -16
                if (r1 >= r6) goto L64
                r6 = 2
                if (r11 >= r6) goto L46
                int r8 = unsafeIncompleteStateFor(r8, r1, r9, r11)
                return r8
            L46:
                int r11 = r11 + (-2)
                long r6 = r9 + r2
                byte r9 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r8, r9)
                if (r9 > r4) goto L63
                r10 = -96
                if (r1 != r0) goto L56
                if (r9 < r10) goto L63
            L56:
                r0 = -19
                if (r1 != r0) goto L5c
                if (r9 >= r10) goto L63
            L5c:
                long r2 = r2 + r6
                byte r9 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r8, r6)
                if (r9 <= r4) goto L37
            L63:
                return r5
            L64:
                r0 = 3
                if (r11 >= r0) goto L6c
                int r8 = unsafeIncompleteStateFor(r8, r1, r9, r11)
                return r8
            L6c:
                int r11 = r11 + (-3)
                long r6 = r9 + r2
                byte r9 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r8, r9)
                if (r9 > r4) goto L8e
                int r10 = r1 << 28
                int r9 = r9 + 112
                int r10 = r10 + r9
                int r9 = r10 >> 30
                if (r9 != 0) goto L8e
                long r9 = r6 + r2
                byte r0 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r8, r6)
                if (r0 > r4) goto L8e
                long r2 = r2 + r9
                byte r9 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r8, r9)
                if (r9 <= r4) goto L37
            L8e:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(byte[], long, int):int");
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0039, code lost:
        
            return -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0063, code lost:
        
            return -1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static int partialIsValidUtf8(long r8, int r10) {
            /*
                int r0 = unsafeEstimateConsecutiveAscii(r8, r10)
                long r1 = (long) r0
                long r8 = r8 + r1
                int r10 = r10 - r0
            L7:
                r0 = 0
                r1 = r0
            L9:
                r2 = 1
                if (r10 <= 0) goto L1a
                long r4 = r8 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r8)
                if (r1 < 0) goto L19
                int r10 = r10 + (-1)
                r8 = r4
                goto L9
            L19:
                r8 = r4
            L1a:
                if (r10 != 0) goto L1d
                return r0
            L1d:
                int r10 = r10 + (-1)
                r0 = -32
                r4 = -65
                r5 = -1
                if (r1 >= r0) goto L3a
                if (r10 != 0) goto L29
                return r1
            L29:
                int r10 = r10 + (-1)
                r0 = -62
                if (r1 < r0) goto L39
                long r2 = r2 + r8
                byte r8 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r8)
                if (r8 <= r4) goto L37
                goto L39
            L37:
                r8 = r2
                goto L7
            L39:
                return r5
            L3a:
                r6 = -16
                if (r1 >= r6) goto L64
                r6 = 2
                if (r10 >= r6) goto L46
                int r8 = unsafeIncompleteStateFor(r8, r1, r10)
                return r8
            L46:
                int r10 = r10 + (-2)
                long r6 = r8 + r2
                byte r8 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r8)
                if (r8 > r4) goto L63
                r9 = -96
                if (r1 != r0) goto L56
                if (r8 < r9) goto L63
            L56:
                r0 = -19
                if (r1 != r0) goto L5c
                if (r8 >= r9) goto L63
            L5c:
                long r2 = r2 + r6
                byte r8 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r6)
                if (r8 <= r4) goto L37
            L63:
                return r5
            L64:
                r0 = 3
                if (r10 >= r0) goto L6c
                int r8 = unsafeIncompleteStateFor(r8, r1, r10)
                return r8
            L6c:
                int r10 = r10 + (-3)
                long r6 = r8 + r2
                byte r8 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r8)
                if (r8 > r4) goto L8e
                int r9 = r1 << 28
                int r8 = r8 + 112
                int r9 = r9 + r8
                int r8 = r9 >> 30
                if (r8 != 0) goto L8e
                long r8 = r6 + r2
                byte r0 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r6)
                if (r0 > r4) goto L8e
                long r2 = r2 + r8
                byte r8 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r8)
                if (r8 <= r4) goto L37
            L8e:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(long, int):int");
        }
    }

    static {
        processor = (!UnsafeProcessor.isAvailable() || Android.isOnAndroidDevice()) ? new SafeProcessor() : new UnsafeProcessor();
    }

    private Utf8() {
    }

    public static String decodeUtf8(ByteBuffer byteBuffer, int i2, int i3) throws InvalidProtocolBufferException {
        return processor.decodeUtf8(byteBuffer, i2, i3);
    }

    public static int encode(CharSequence charSequence, byte[] bArr, int i2, int i3) {
        return processor.encodeUtf8(charSequence, bArr, i2, i3);
    }

    public static void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer) {
        processor.encodeUtf8(charSequence, byteBuffer);
    }

    public static int encodedLength(CharSequence charSequence) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static int estimateConsecutiveAscii(ByteBuffer byteBuffer, int i2, int i3) {
        int i4 = i3 - 7;
        int i5 = i2;
        while (i5 < i4 && (byteBuffer.getLong(i5) & ASCII_MASK_LONG) == 0) {
            i5 += 8;
        }
        return i5 - i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(int i2) {
        if (i2 > -12) {
            return -1;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(int i2, int i3) {
        if (i2 > -12 || i3 > -65) {
            return -1;
        }
        return i2 ^ (i3 << 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(int i2, int i3, int i4) {
        if (i2 > -12 || i3 > -65 || i4 > -65) {
            return -1;
        }
        return (i2 ^ (i3 << 8)) ^ (i4 << 16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(byte[] bArr, int i2, int i3) {
        byte b3 = bArr[i2 - 1];
        int i4 = i3 - i2;
        if (i4 == 0) {
            return incompleteStateFor(b3);
        }
        if (i4 == 1) {
            return incompleteStateFor(b3, bArr[i2]);
        }
        if (i4 == 2) {
            return incompleteStateFor(b3, bArr[i2], bArr[i2 + 1]);
        }
        throw new AssertionError();
    }

    public static boolean isValidUtf8(byte[] bArr) {
        return processor.isValidUtf8(bArr, 0, bArr.length);
    }

    public static int partialIsValidUtf8(int i2, byte[] bArr, int i3, int i4) {
        return processor.partialIsValidUtf8(i2, bArr, i3, i4);
    }

    public static String decodeUtf8(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException {
        return processor.decodeUtf8(bArr, i2, i3);
    }

    public static boolean isValidUtf8(byte[] bArr, int i2, int i3) {
        return processor.isValidUtf8(bArr, i2, i3);
    }

    public static int partialIsValidUtf8(int i2, ByteBuffer byteBuffer, int i3, int i4) {
        return processor.partialIsValidUtf8(i2, byteBuffer, i3, i4);
    }

    public static boolean isValidUtf8(ByteBuffer byteBuffer) {
        return processor.isValidUtf8(byteBuffer, byteBuffer.position(), byteBuffer.remaining());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(ByteBuffer byteBuffer, int i2, int i3, int i4) {
        if (i4 == 0) {
            return incompleteStateFor(i2);
        }
        if (i4 == 1) {
            return incompleteStateFor(i2, byteBuffer.get(i3));
        }
        if (i4 == 2) {
            return incompleteStateFor(i2, byteBuffer.get(i3), byteBuffer.get(i3 + 1));
        }
        throw new AssertionError();
    }

    public static final class SafeProcessor extends Processor {
        private static int partialIsValidUtf8NonAscii(byte[] bArr, int i2, int i3) {
            while (i2 < i3) {
                int i4 = i2 + 1;
                byte b3 = bArr[i2];
                if (b3 < 0) {
                    if (b3 < -32) {
                        if (i4 >= i3) {
                            return b3;
                        }
                        if (b3 >= -62) {
                            i2 = i4 + 1;
                            if (bArr[i4] > -65) {
                            }
                        }
                        return -1;
                    }
                    if (b3 >= -16) {
                        if (i4 >= i3 - 2) {
                            return Utf8.incompleteStateFor(bArr, i4, i3);
                        }
                        int i5 = i4 + 1;
                        byte b4 = bArr[i4];
                        if (b4 <= -65 && (((b3 << Ascii.FS) + (b4 + 112)) >> 30) == 0) {
                            int i6 = i5 + 1;
                            if (bArr[i5] <= -65) {
                                i4 = i6 + 1;
                                if (bArr[i6] > -65) {
                                }
                            }
                        }
                        return -1;
                    }
                    if (i4 >= i3 - 1) {
                        return Utf8.incompleteStateFor(bArr, i4, i3);
                    }
                    int i7 = i4 + 1;
                    byte b5 = bArr[i4];
                    if (b5 <= -65 && ((b3 != -32 || b5 >= -96) && (b3 != -19 || b5 < -96))) {
                        i2 = i7 + 1;
                        if (bArr[i7] > -65) {
                        }
                    }
                    return -1;
                }
                i2 = i4;
            }
            return 0;
        }

        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        public String decodeUtf8(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException {
            if ((i2 | i3 | ((bArr.length - i2) - i3)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)));
            }
            int i4 = i2 + i3;
            char[] cArr = new char[i3];
            int i5 = 0;
            while (i2 < i4) {
                byte b3 = bArr[i2];
                if (!DecodeUtil.isOneByte(b3)) {
                    break;
                }
                i2++;
                DecodeUtil.handleOneByte(b3, cArr, i5);
                i5++;
            }
            int i6 = i5;
            while (i2 < i4) {
                int i7 = i2 + 1;
                byte b4 = bArr[i2];
                if (DecodeUtil.isOneByte(b4)) {
                    int i8 = i6 + 1;
                    DecodeUtil.handleOneByte(b4, cArr, i6);
                    while (i7 < i4) {
                        byte b5 = bArr[i7];
                        if (!DecodeUtil.isOneByte(b5)) {
                            break;
                        }
                        i7++;
                        DecodeUtil.handleOneByte(b5, cArr, i8);
                        i8++;
                    }
                    i2 = i7;
                    i6 = i8;
                } else if (DecodeUtil.isTwoBytes(b4)) {
                    if (i7 >= i4) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    DecodeUtil.handleTwoBytes(b4, bArr[i7], cArr, i6);
                    i2 = i7 + 1;
                    i6++;
                } else if (DecodeUtil.isThreeBytes(b4)) {
                    if (i7 >= i4 - 1) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i9 = i7 + 1;
                    DecodeUtil.handleThreeBytes(b4, bArr[i7], bArr[i9], cArr, i6);
                    i2 = i9 + 1;
                    i6++;
                } else {
                    if (i7 >= i4 - 2) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i10 = i7 + 1;
                    byte b6 = bArr[i7];
                    int i11 = i10 + 1;
                    DecodeUtil.handleFourBytes(b4, b6, bArr[i10], bArr[i11], cArr, i6);
                    i2 = i11 + 1;
                    i6 = i6 + 1 + 1;
                }
            }
            return new String(cArr, 0, i6);
        }

        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        public String decodeUtf8Direct(ByteBuffer byteBuffer, int i2, int i3) throws InvalidProtocolBufferException {
            return decodeUtf8Default(byteBuffer, i2, i3);
        }

        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        public int encodeUtf8(CharSequence charSequence, byte[] bArr, int i2, int i3) {
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

        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        public void encodeUtf8Direct(CharSequence charSequence, ByteBuffer byteBuffer) {
            encodeUtf8Default(charSequence, byteBuffer);
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0015, code lost:
        
            if (r8[r9] > (-65)) goto L13;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0046, code lost:
        
            if (r8[r9] > (-65)) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x0083, code lost:
        
            if (r8[r7] > (-65)) goto L53;
         */
        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int partialIsValidUtf8(int r7, byte[] r8, int r9, int r10) {
            /*
                r6 = this;
                if (r7 == 0) goto L86
                if (r9 < r10) goto L5
                return r7
            L5:
                byte r0 = (byte) r7
                r1 = -32
                r2 = -1
                r3 = -65
                if (r0 >= r1) goto L1c
                r7 = -62
                if (r0 < r7) goto L1b
                int r7 = r9 + 1
                r9 = r8[r9]
                if (r9 <= r3) goto L18
                goto L1b
            L18:
                r9 = r7
                goto L86
            L1b:
                return r2
            L1c:
                r4 = -16
                if (r0 >= r4) goto L49
                int r7 = r7 >> 8
                int r7 = ~r7
                byte r7 = (byte) r7
                if (r7 != 0) goto L34
                int r7 = r9 + 1
                r9 = r8[r9]
                if (r7 < r10) goto L31
                int r7 = androidx.datastore.preferences.protobuf.Utf8.access$000(r0, r9)
                return r7
            L31:
                r5 = r9
                r9 = r7
                r7 = r5
            L34:
                if (r7 > r3) goto L48
                r4 = -96
                if (r0 != r1) goto L3c
                if (r7 < r4) goto L48
            L3c:
                r1 = -19
                if (r0 != r1) goto L42
                if (r7 >= r4) goto L48
            L42:
                int r7 = r9 + 1
                r9 = r8[r9]
                if (r9 <= r3) goto L18
            L48:
                return r2
            L49:
                int r1 = r7 >> 8
                int r1 = ~r1
                byte r1 = (byte) r1
                if (r1 != 0) goto L5c
                int r7 = r9 + 1
                r1 = r8[r9]
                if (r7 < r10) goto L5a
                int r7 = androidx.datastore.preferences.protobuf.Utf8.access$000(r0, r1)
                return r7
            L5a:
                r9 = 0
                goto L62
            L5c:
                int r7 = r7 >> 16
                byte r7 = (byte) r7
                r5 = r9
                r9 = r7
                r7 = r5
            L62:
                if (r9 != 0) goto L72
                int r9 = r7 + 1
                r7 = r8[r7]
                if (r9 < r10) goto L6f
                int r7 = androidx.datastore.preferences.protobuf.Utf8.access$100(r0, r1, r7)
                return r7
            L6f:
                r5 = r9
                r9 = r7
                r7 = r5
            L72:
                if (r1 > r3) goto L85
                int r0 = r0 << 28
                int r1 = r1 + 112
                int r0 = r0 + r1
                int r0 = r0 >> 30
                if (r0 != 0) goto L85
                if (r9 > r3) goto L85
                int r9 = r7 + 1
                r7 = r8[r7]
                if (r7 <= r3) goto L86
            L85:
                return r2
            L86:
                int r7 = partialIsValidUtf8(r8, r9, r10)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.Utf8.SafeProcessor.partialIsValidUtf8(int, byte[], int, int):int");
        }

        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        public int partialIsValidUtf8Direct(int i2, ByteBuffer byteBuffer, int i3, int i4) {
            return partialIsValidUtf8Default(i2, byteBuffer, i3, i4);
        }

        private static int partialIsValidUtf8(byte[] bArr, int i2, int i3) {
            while (i2 < i3 && bArr[i2] >= 0) {
                i2++;
            }
            if (i2 >= i3) {
                return 0;
            }
            return partialIsValidUtf8NonAscii(bArr, i2, i3);
        }
    }
}
