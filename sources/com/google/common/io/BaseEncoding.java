package com.google.common.io;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.RoundingMode;
import java.util.Arrays;
import net.lingala.zip4j.crypto.PBKDF2.BinTools;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
public abstract class BaseEncoding {
    private static final BaseEncoding BASE64 = new Base64Encoding("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", '=');
    private static final BaseEncoding BASE64_URL = new Base64Encoding("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", '=');
    private static final BaseEncoding BASE32 = new StandardBaseEncoding("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", '=');
    private static final BaseEncoding BASE32_HEX = new StandardBaseEncoding("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", '=');
    private static final BaseEncoding BASE16 = new Base16Encoding("base16()", BinTools.hex);

    public static final class Alphabet {
        final int bitsPerChar;
        final int bytesPerChunk;
        private final char[] chars;
        final int charsPerChunk;
        private final byte[] decodabet;
        final int mask;
        private final String name;
        private final boolean[] validPadding;

        public Alphabet(String str, char[] cArr) {
            this.name = (String) Preconditions.checkNotNull(str);
            this.chars = (char[]) Preconditions.checkNotNull(cArr);
            try {
                int iLog2 = IntMath.log2(cArr.length, RoundingMode.UNNECESSARY);
                this.bitsPerChar = iLog2;
                int iMin = Math.min(8, Integer.lowestOneBit(iLog2));
                try {
                    this.charsPerChunk = 8 / iMin;
                    this.bytesPerChunk = iLog2 / iMin;
                    this.mask = cArr.length - 1;
                    byte[] bArr = new byte[128];
                    Arrays.fill(bArr, (byte) -1);
                    for (int i2 = 0; i2 < cArr.length; i2++) {
                        char c3 = cArr[i2];
                        Preconditions.checkArgument(c3 < 128, "Non-ASCII character: %s", c3);
                        Preconditions.checkArgument(bArr[c3] == -1, "Duplicate character: %s", c3);
                        bArr[c3] = (byte) i2;
                    }
                    this.decodabet = bArr;
                    boolean[] zArr = new boolean[this.charsPerChunk];
                    for (int i3 = 0; i3 < this.bytesPerChunk; i3++) {
                        zArr[IntMath.divide(i3 * 8, this.bitsPerChar, RoundingMode.CEILING)] = true;
                    }
                    this.validPadding = zArr;
                } catch (ArithmeticException e2) {
                    throw new IllegalArgumentException("Illegal alphabet " + new String(cArr), e2);
                }
            } catch (ArithmeticException e3) {
                throw new IllegalArgumentException("Illegal alphabet length " + cArr.length, e3);
            }
        }

        private boolean hasLowerCase() {
            for (char c3 : this.chars) {
                if (Ascii.isLowerCase(c3)) {
                    return true;
                }
            }
            return false;
        }

        private boolean hasUpperCase() {
            for (char c3 : this.chars) {
                if (Ascii.isUpperCase(c3)) {
                    return true;
                }
            }
            return false;
        }

        public boolean canDecode(char c3) {
            return c3 <= 127 && this.decodabet[c3] != -1;
        }

        public int decode(char c3) throws DecodingException {
            if (c3 > 127) {
                throw new DecodingException("Unrecognized character: 0x" + Integer.toHexString(c3));
            }
            byte b3 = this.decodabet[c3];
            if (b3 != -1) {
                return b3;
            }
            if (c3 <= ' ' || c3 == 127) {
                throw new DecodingException("Unrecognized character: 0x" + Integer.toHexString(c3));
            }
            throw new DecodingException("Unrecognized character: " + c3);
        }

        public char encode(int i2) {
            return this.chars[i2];
        }

        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof Alphabet) {
                return Arrays.equals(this.chars, ((Alphabet) obj).chars);
            }
            return false;
        }

        public int hashCode() {
            return Arrays.hashCode(this.chars);
        }

        public boolean isValidPaddingStartPosition(int i2) {
            return this.validPadding[i2 % this.charsPerChunk];
        }

        public Alphabet lowerCase() {
            if (!hasUpperCase()) {
                return this;
            }
            Preconditions.checkState(!hasLowerCase(), "Cannot call lowerCase() on a mixed-case alphabet");
            char[] cArr = new char[this.chars.length];
            int i2 = 0;
            while (true) {
                char[] cArr2 = this.chars;
                if (i2 >= cArr2.length) {
                    return new Alphabet(this.name + ".lowerCase()", cArr);
                }
                cArr[i2] = Ascii.toLowerCase(cArr2[i2]);
                i2++;
            }
        }

        public boolean matches(char c3) {
            byte[] bArr = this.decodabet;
            return c3 < bArr.length && bArr[c3] != -1;
        }

        public String toString() {
            return this.name;
        }

        public Alphabet upperCase() {
            if (!hasLowerCase()) {
                return this;
            }
            Preconditions.checkState(!hasUpperCase(), "Cannot call upperCase() on a mixed-case alphabet");
            char[] cArr = new char[this.chars.length];
            int i2 = 0;
            while (true) {
                char[] cArr2 = this.chars;
                if (i2 >= cArr2.length) {
                    return new Alphabet(this.name + ".upperCase()", cArr);
                }
                cArr[i2] = Ascii.toUpperCase(cArr2[i2]);
                i2++;
            }
        }
    }

    public static final class Base16Encoding extends StandardBaseEncoding {
        final char[] encoding;

        public Base16Encoding(String str, String str2) {
            this(new Alphabet(str, str2.toCharArray()));
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding, com.google.common.io.BaseEncoding
        public int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(bArr);
            if (charSequence.length() % 2 == 1) {
                throw new DecodingException("Invalid input length " + charSequence.length());
            }
            int i2 = 0;
            int i3 = 0;
            while (i2 < charSequence.length()) {
                bArr[i3] = (byte) ((this.alphabet.decode(charSequence.charAt(i2)) << 4) | this.alphabet.decode(charSequence.charAt(i2 + 1)));
                i2 += 2;
                i3++;
            }
            return i3;
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding, com.google.common.io.BaseEncoding
        public void encodeTo(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
            for (int i4 = 0; i4 < i3; i4++) {
                int i5 = bArr[i2 + i4] & 255;
                appendable.append(this.encoding[i5]);
                appendable.append(this.encoding[i5 | 256]);
            }
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding
        public BaseEncoding newInstance(Alphabet alphabet, @NullableDecl Character ch) {
            return new Base16Encoding(alphabet);
        }

        private Base16Encoding(Alphabet alphabet) {
            super(alphabet, null);
            this.encoding = new char[512];
            Preconditions.checkArgument(alphabet.chars.length == 16);
            for (int i2 = 0; i2 < 256; i2++) {
                this.encoding[i2] = alphabet.encode(i2 >>> 4);
                this.encoding[i2 | 256] = alphabet.encode(i2 & 15);
            }
        }
    }

    public static final class Base64Encoding extends StandardBaseEncoding {
        public Base64Encoding(String str, String str2, @NullableDecl Character ch) {
            this(new Alphabet(str, str2.toCharArray()), ch);
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding, com.google.common.io.BaseEncoding
        public int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(bArr);
            CharSequence charSequenceTrimTrailingPadding = trimTrailingPadding(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequenceTrimTrailingPadding.length())) {
                throw new DecodingException("Invalid input length " + charSequenceTrimTrailingPadding.length());
            }
            int i2 = 0;
            int i3 = 0;
            while (i2 < charSequenceTrimTrailingPadding.length()) {
                int i4 = i2 + 1;
                int i5 = i4 + 1;
                int iDecode = (this.alphabet.decode(charSequenceTrimTrailingPadding.charAt(i2)) << 18) | (this.alphabet.decode(charSequenceTrimTrailingPadding.charAt(i4)) << 12);
                int i6 = i3 + 1;
                bArr[i3] = (byte) (iDecode >>> 16);
                if (i5 < charSequenceTrimTrailingPadding.length()) {
                    int i7 = i5 + 1;
                    int iDecode2 = iDecode | (this.alphabet.decode(charSequenceTrimTrailingPadding.charAt(i5)) << 6);
                    i3 = i6 + 1;
                    bArr[i6] = (byte) ((iDecode2 >>> 8) & 255);
                    if (i7 < charSequenceTrimTrailingPadding.length()) {
                        i5 = i7 + 1;
                        i6 = i3 + 1;
                        bArr[i3] = (byte) ((iDecode2 | this.alphabet.decode(charSequenceTrimTrailingPadding.charAt(i7))) & 255);
                    } else {
                        i2 = i7;
                    }
                }
                i3 = i6;
                i2 = i5;
            }
            return i3;
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding, com.google.common.io.BaseEncoding
        public void encodeTo(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
            Preconditions.checkNotNull(appendable);
            int i4 = i2 + i3;
            Preconditions.checkPositionIndexes(i2, i4, bArr.length);
            while (i3 >= 3) {
                int i5 = i2 + 1;
                int i6 = i5 + 1;
                int i7 = ((bArr[i2] & 255) << 16) | ((bArr[i5] & 255) << 8) | (bArr[i6] & 255);
                appendable.append(this.alphabet.encode(i7 >>> 18));
                appendable.append(this.alphabet.encode((i7 >>> 12) & 63));
                appendable.append(this.alphabet.encode((i7 >>> 6) & 63));
                appendable.append(this.alphabet.encode(i7 & 63));
                i3 -= 3;
                i2 = i6 + 1;
            }
            if (i2 < i4) {
                encodeChunkTo(appendable, bArr, i2, i4 - i2);
            }
        }

        @Override // com.google.common.io.BaseEncoding.StandardBaseEncoding
        public BaseEncoding newInstance(Alphabet alphabet, @NullableDecl Character ch) {
            return new Base64Encoding(alphabet, ch);
        }

        private Base64Encoding(Alphabet alphabet, @NullableDecl Character ch) {
            super(alphabet, ch);
            Preconditions.checkArgument(alphabet.chars.length == 64);
        }
    }

    public static final class DecodingException extends IOException {
        public DecodingException(String str) {
            super(str);
        }

        public DecodingException(Throwable th) {
            super(th);
        }
    }

    public static final class SeparatedBaseEncoding extends BaseEncoding {
        private final int afterEveryChars;
        private final BaseEncoding delegate;
        private final String separator;

        public SeparatedBaseEncoding(BaseEncoding baseEncoding, String str, int i2) {
            this.delegate = (BaseEncoding) Preconditions.checkNotNull(baseEncoding);
            this.separator = (String) Preconditions.checkNotNull(str);
            this.afterEveryChars = i2;
            Preconditions.checkArgument(i2 > 0, "Cannot add a separator after every %s chars", i2);
        }

        @Override // com.google.common.io.BaseEncoding
        public boolean canDecode(CharSequence charSequence) {
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < charSequence.length(); i2++) {
                char cCharAt = charSequence.charAt(i2);
                if (this.separator.indexOf(cCharAt) < 0) {
                    sb.append(cCharAt);
                }
            }
            return this.delegate.canDecode(sb);
        }

        @Override // com.google.common.io.BaseEncoding
        public int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException {
            StringBuilder sb = new StringBuilder(charSequence.length());
            for (int i2 = 0; i2 < charSequence.length(); i2++) {
                char cCharAt = charSequence.charAt(i2);
                if (this.separator.indexOf(cCharAt) < 0) {
                    sb.append(cCharAt);
                }
            }
            return this.delegate.decodeTo(bArr, sb);
        }

        @Override // com.google.common.io.BaseEncoding
        @GwtIncompatible
        public InputStream decodingStream(Reader reader) {
            return this.delegate.decodingStream(BaseEncoding.ignoringReader(reader, this.separator));
        }

        @Override // com.google.common.io.BaseEncoding
        public void encodeTo(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
            this.delegate.encodeTo(BaseEncoding.separatingAppendable(appendable, this.separator, this.afterEveryChars), bArr, i2, i3);
        }

        @Override // com.google.common.io.BaseEncoding
        @GwtIncompatible
        public OutputStream encodingStream(Writer writer) {
            return this.delegate.encodingStream(BaseEncoding.separatingWriter(writer, this.separator, this.afterEveryChars));
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding lowerCase() {
            return this.delegate.lowerCase().withSeparator(this.separator, this.afterEveryChars);
        }

        @Override // com.google.common.io.BaseEncoding
        public int maxDecodedSize(int i2) {
            return this.delegate.maxDecodedSize(i2);
        }

        @Override // com.google.common.io.BaseEncoding
        public int maxEncodedSize(int i2) {
            int iMaxEncodedSize = this.delegate.maxEncodedSize(i2);
            return iMaxEncodedSize + (this.separator.length() * IntMath.divide(Math.max(0, iMaxEncodedSize - 1), this.afterEveryChars, RoundingMode.FLOOR));
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding omitPadding() {
            return this.delegate.omitPadding().withSeparator(this.separator, this.afterEveryChars);
        }

        public String toString() {
            return this.delegate + ".withSeparator(\"" + this.separator + "\", " + this.afterEveryChars + ")";
        }

        @Override // com.google.common.io.BaseEncoding
        public CharSequence trimTrailingPadding(CharSequence charSequence) {
            return this.delegate.trimTrailingPadding(charSequence);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding upperCase() {
            return this.delegate.upperCase().withSeparator(this.separator, this.afterEveryChars);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withPadChar(char c3) {
            return this.delegate.withPadChar(c3).withSeparator(this.separator, this.afterEveryChars);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withSeparator(String str, int i2) {
            throw new UnsupportedOperationException("Already have a separator");
        }
    }

    public static class StandardBaseEncoding extends BaseEncoding {
        final Alphabet alphabet;

        @MonotonicNonNullDecl
        private transient BaseEncoding lowerCase;

        @NullableDecl
        final Character paddingChar;

        @MonotonicNonNullDecl
        private transient BaseEncoding upperCase;

        public StandardBaseEncoding(String str, String str2, @NullableDecl Character ch) {
            this(new Alphabet(str, str2.toCharArray()), ch);
        }

        @Override // com.google.common.io.BaseEncoding
        public boolean canDecode(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            CharSequence charSequenceTrimTrailingPadding = trimTrailingPadding(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequenceTrimTrailingPadding.length())) {
                return false;
            }
            for (int i2 = 0; i2 < charSequenceTrimTrailingPadding.length(); i2++) {
                if (!this.alphabet.canDecode(charSequenceTrimTrailingPadding.charAt(i2))) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.io.BaseEncoding
        public int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Alphabet alphabet;
            Preconditions.checkNotNull(bArr);
            CharSequence charSequenceTrimTrailingPadding = trimTrailingPadding(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequenceTrimTrailingPadding.length())) {
                throw new DecodingException("Invalid input length " + charSequenceTrimTrailingPadding.length());
            }
            int i2 = 0;
            int i3 = 0;
            while (i2 < charSequenceTrimTrailingPadding.length()) {
                long jDecode = 0;
                int i4 = 0;
                int i5 = 0;
                while (true) {
                    alphabet = this.alphabet;
                    if (i4 >= alphabet.charsPerChunk) {
                        break;
                    }
                    jDecode <<= alphabet.bitsPerChar;
                    if (i2 + i4 < charSequenceTrimTrailingPadding.length()) {
                        jDecode |= this.alphabet.decode(charSequenceTrimTrailingPadding.charAt(i5 + i2));
                        i5++;
                    }
                    i4++;
                }
                int i6 = alphabet.bytesPerChunk;
                int i7 = (i6 * 8) - (i5 * alphabet.bitsPerChar);
                int i8 = (i6 - 1) * 8;
                while (i8 >= i7) {
                    bArr[i3] = (byte) ((jDecode >>> i8) & 255);
                    i8 -= 8;
                    i3++;
                }
                i2 += this.alphabet.charsPerChunk;
            }
            return i3;
        }

        @Override // com.google.common.io.BaseEncoding
        @GwtIncompatible
        public InputStream decodingStream(final Reader reader) {
            Preconditions.checkNotNull(reader);
            return new InputStream() { // from class: com.google.common.io.BaseEncoding.StandardBaseEncoding.2
                int bitBuffer = 0;
                int bitBufferLength = 0;
                int readChars = 0;
                boolean hitPadding = false;

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    reader.close();
                }

                /* JADX WARN: Code restructure failed: missing block: B:24:0x0074, code lost:
                
                    throw new com.google.common.io.BaseEncoding.DecodingException("Padding cannot start at index " + r4.readChars);
                 */
                @Override // java.io.InputStream
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public int read() throws java.io.IOException {
                    /*
                        r4 = this;
                    L0:
                        java.io.Reader r0 = r2
                        int r0 = r0.read()
                        r1 = -1
                        if (r0 != r1) goto L34
                        boolean r0 = r4.hitPadding
                        if (r0 != 0) goto L33
                        com.google.common.io.BaseEncoding$StandardBaseEncoding r0 = com.google.common.io.BaseEncoding.StandardBaseEncoding.this
                        com.google.common.io.BaseEncoding$Alphabet r0 = r0.alphabet
                        int r2 = r4.readChars
                        boolean r0 = r0.isValidPaddingStartPosition(r2)
                        if (r0 == 0) goto L1a
                        goto L33
                    L1a:
                        com.google.common.io.BaseEncoding$DecodingException r0 = new com.google.common.io.BaseEncoding$DecodingException
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        java.lang.String r2 = "Invalid input length "
                        r1.append(r2)
                        int r2 = r4.readChars
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        r0.<init>(r1)
                        throw r0
                    L33:
                        return r1
                    L34:
                        int r1 = r4.readChars
                        r2 = 1
                        int r1 = r1 + r2
                        r4.readChars = r1
                        char r0 = (char) r0
                        com.google.common.io.BaseEncoding$StandardBaseEncoding r1 = com.google.common.io.BaseEncoding.StandardBaseEncoding.this
                        java.lang.Character r1 = r1.paddingChar
                        if (r1 == 0) goto L78
                        char r1 = r1.charValue()
                        if (r1 != r0) goto L78
                        boolean r0 = r4.hitPadding
                        if (r0 != 0) goto L75
                        int r0 = r4.readChars
                        if (r0 == r2) goto L5c
                        com.google.common.io.BaseEncoding$StandardBaseEncoding r1 = com.google.common.io.BaseEncoding.StandardBaseEncoding.this
                        com.google.common.io.BaseEncoding$Alphabet r1 = r1.alphabet
                        int r0 = r0 + (-1)
                        boolean r0 = r1.isValidPaddingStartPosition(r0)
                        if (r0 == 0) goto L5c
                        goto L75
                    L5c:
                        com.google.common.io.BaseEncoding$DecodingException r0 = new com.google.common.io.BaseEncoding$DecodingException
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        java.lang.String r2 = "Padding cannot start at index "
                        r1.append(r2)
                        int r2 = r4.readChars
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        r0.<init>(r1)
                        throw r0
                    L75:
                        r4.hitPadding = r2
                        goto L0
                    L78:
                        boolean r1 = r4.hitPadding
                        if (r1 != 0) goto La4
                        int r1 = r4.bitBuffer
                        com.google.common.io.BaseEncoding$StandardBaseEncoding r2 = com.google.common.io.BaseEncoding.StandardBaseEncoding.this
                        com.google.common.io.BaseEncoding$Alphabet r2 = r2.alphabet
                        int r3 = r2.bitsPerChar
                        int r1 = r1 << r3
                        r4.bitBuffer = r1
                        int r0 = r2.decode(r0)
                        r0 = r0 | r1
                        r4.bitBuffer = r0
                        int r1 = r4.bitBufferLength
                        com.google.common.io.BaseEncoding$StandardBaseEncoding r2 = com.google.common.io.BaseEncoding.StandardBaseEncoding.this
                        com.google.common.io.BaseEncoding$Alphabet r2 = r2.alphabet
                        int r2 = r2.bitsPerChar
                        int r1 = r1 + r2
                        r4.bitBufferLength = r1
                        r2 = 8
                        if (r1 < r2) goto L0
                        int r1 = r1 - r2
                        r4.bitBufferLength = r1
                        int r0 = r0 >> r1
                        r0 = r0 & 255(0xff, float:3.57E-43)
                        return r0
                    La4:
                        com.google.common.io.BaseEncoding$DecodingException r1 = new com.google.common.io.BaseEncoding$DecodingException
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        r2.<init>()
                        java.lang.String r3 = "Expected padding character but found '"
                        r2.append(r3)
                        r2.append(r0)
                        java.lang.String r0 = "' at index "
                        r2.append(r0)
                        int r0 = r4.readChars
                        r2.append(r0)
                        java.lang.String r0 = r2.toString()
                        r1.<init>(r0)
                        throw r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.common.io.BaseEncoding.StandardBaseEncoding.AnonymousClass2.read():int");
                }
            };
        }

        public void encodeChunkTo(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
            int i4 = 0;
            Preconditions.checkArgument(i3 <= this.alphabet.bytesPerChunk);
            long j2 = 0;
            for (int i5 = 0; i5 < i3; i5++) {
                j2 = (j2 | (bArr[i2 + i5] & 255)) << 8;
            }
            int i6 = ((i3 + 1) * 8) - this.alphabet.bitsPerChar;
            while (i4 < i3 * 8) {
                Alphabet alphabet = this.alphabet;
                appendable.append(alphabet.encode(((int) (j2 >>> (i6 - i4))) & alphabet.mask));
                i4 += this.alphabet.bitsPerChar;
            }
            if (this.paddingChar != null) {
                while (i4 < this.alphabet.bytesPerChunk * 8) {
                    appendable.append(this.paddingChar.charValue());
                    i4 += this.alphabet.bitsPerChar;
                }
            }
        }

        @Override // com.google.common.io.BaseEncoding
        public void encodeTo(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
            int i4 = 0;
            while (i4 < i3) {
                encodeChunkTo(appendable, bArr, i2 + i4, Math.min(this.alphabet.bytesPerChunk, i3 - i4));
                i4 += this.alphabet.bytesPerChunk;
            }
        }

        @Override // com.google.common.io.BaseEncoding
        @GwtIncompatible
        public OutputStream encodingStream(final Writer writer) {
            Preconditions.checkNotNull(writer);
            return new OutputStream() { // from class: com.google.common.io.BaseEncoding.StandardBaseEncoding.1
                int bitBuffer = 0;
                int bitBufferLength = 0;
                int writtenChars = 0;

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    int i2 = this.bitBufferLength;
                    if (i2 > 0) {
                        int i3 = this.bitBuffer;
                        Alphabet alphabet = StandardBaseEncoding.this.alphabet;
                        writer.write(alphabet.encode((i3 << (alphabet.bitsPerChar - i2)) & alphabet.mask));
                        this.writtenChars++;
                        if (StandardBaseEncoding.this.paddingChar != null) {
                            while (true) {
                                int i4 = this.writtenChars;
                                StandardBaseEncoding standardBaseEncoding = StandardBaseEncoding.this;
                                if (i4 % standardBaseEncoding.alphabet.charsPerChunk == 0) {
                                    break;
                                }
                                writer.write(standardBaseEncoding.paddingChar.charValue());
                                this.writtenChars++;
                            }
                        }
                    }
                    writer.close();
                }

                @Override // java.io.OutputStream, java.io.Flushable
                public void flush() throws IOException {
                    writer.flush();
                }

                @Override // java.io.OutputStream
                public void write(int i2) throws IOException {
                    this.bitBuffer = (i2 & 255) | (this.bitBuffer << 8);
                    this.bitBufferLength += 8;
                    while (true) {
                        int i3 = this.bitBufferLength;
                        Alphabet alphabet = StandardBaseEncoding.this.alphabet;
                        int i4 = alphabet.bitsPerChar;
                        if (i3 < i4) {
                            return;
                        }
                        writer.write(alphabet.encode((this.bitBuffer >> (i3 - i4)) & alphabet.mask));
                        this.writtenChars++;
                        this.bitBufferLength -= StandardBaseEncoding.this.alphabet.bitsPerChar;
                    }
                }
            };
        }

        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof StandardBaseEncoding)) {
                return false;
            }
            StandardBaseEncoding standardBaseEncoding = (StandardBaseEncoding) obj;
            return this.alphabet.equals(standardBaseEncoding.alphabet) && Objects.equal(this.paddingChar, standardBaseEncoding.paddingChar);
        }

        public int hashCode() {
            return this.alphabet.hashCode() ^ Objects.hashCode(this.paddingChar);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding lowerCase() {
            BaseEncoding baseEncodingNewInstance = this.lowerCase;
            if (baseEncodingNewInstance == null) {
                Alphabet alphabetLowerCase = this.alphabet.lowerCase();
                baseEncodingNewInstance = alphabetLowerCase == this.alphabet ? this : newInstance(alphabetLowerCase, this.paddingChar);
                this.lowerCase = baseEncodingNewInstance;
            }
            return baseEncodingNewInstance;
        }

        @Override // com.google.common.io.BaseEncoding
        public int maxDecodedSize(int i2) {
            return (int) (((this.alphabet.bitsPerChar * i2) + 7) / 8);
        }

        @Override // com.google.common.io.BaseEncoding
        public int maxEncodedSize(int i2) {
            Alphabet alphabet = this.alphabet;
            return alphabet.charsPerChunk * IntMath.divide(i2, alphabet.bytesPerChunk, RoundingMode.CEILING);
        }

        public BaseEncoding newInstance(Alphabet alphabet, @NullableDecl Character ch) {
            return new StandardBaseEncoding(alphabet, ch);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding omitPadding() {
            return this.paddingChar == null ? this : newInstance(this.alphabet, null);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("BaseEncoding.");
            sb.append(this.alphabet.toString());
            if (8 % this.alphabet.bitsPerChar != 0) {
                if (this.paddingChar == null) {
                    sb.append(".omitPadding()");
                } else {
                    sb.append(".withPadChar('");
                    sb.append(this.paddingChar);
                    sb.append("')");
                }
            }
            return sb.toString();
        }

        @Override // com.google.common.io.BaseEncoding
        public CharSequence trimTrailingPadding(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            Character ch = this.paddingChar;
            if (ch == null) {
                return charSequence;
            }
            char cCharValue = ch.charValue();
            int length = charSequence.length() - 1;
            while (length >= 0 && charSequence.charAt(length) == cCharValue) {
                length--;
            }
            return charSequence.subSequence(0, length + 1);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding upperCase() {
            BaseEncoding baseEncodingNewInstance = this.upperCase;
            if (baseEncodingNewInstance == null) {
                Alphabet alphabetUpperCase = this.alphabet.upperCase();
                baseEncodingNewInstance = alphabetUpperCase == this.alphabet ? this : newInstance(alphabetUpperCase, this.paddingChar);
                this.upperCase = baseEncodingNewInstance;
            }
            return baseEncodingNewInstance;
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withPadChar(char c3) {
            Character ch;
            return (8 % this.alphabet.bitsPerChar == 0 || ((ch = this.paddingChar) != null && ch.charValue() == c3)) ? this : newInstance(this.alphabet, Character.valueOf(c3));
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withSeparator(String str, int i2) {
            for (int i3 = 0; i3 < str.length(); i3++) {
                Preconditions.checkArgument(!this.alphabet.matches(str.charAt(i3)), "Separator (%s) cannot contain alphabet characters", str);
            }
            Character ch = this.paddingChar;
            if (ch != null) {
                Preconditions.checkArgument(str.indexOf(ch.charValue()) < 0, "Separator (%s) cannot contain padding character", str);
            }
            return new SeparatedBaseEncoding(this, str, i2);
        }

        public StandardBaseEncoding(Alphabet alphabet, @NullableDecl Character ch) {
            this.alphabet = (Alphabet) Preconditions.checkNotNull(alphabet);
            Preconditions.checkArgument(ch == null || !alphabet.matches(ch.charValue()), "Padding character %s was already in alphabet", ch);
            this.paddingChar = ch;
        }
    }

    public static BaseEncoding base16() {
        return BASE16;
    }

    public static BaseEncoding base32() {
        return BASE32;
    }

    public static BaseEncoding base32Hex() {
        return BASE32_HEX;
    }

    public static BaseEncoding base64() {
        return BASE64;
    }

    public static BaseEncoding base64Url() {
        return BASE64_URL;
    }

    private static byte[] extract(byte[] bArr, int i2) {
        if (i2 == bArr.length) {
            return bArr;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, 0, bArr2, 0, i2);
        return bArr2;
    }

    @GwtIncompatible
    public static Reader ignoringReader(final Reader reader, final String str) {
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(str);
        return new Reader() { // from class: com.google.common.io.BaseEncoding.3
            @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                reader.close();
            }

            @Override // java.io.Reader
            public int read() throws IOException {
                int i2;
                do {
                    i2 = reader.read();
                    if (i2 == -1) {
                        break;
                    }
                } while (str.indexOf((char) i2) >= 0);
                return i2;
            }

            @Override // java.io.Reader
            public int read(char[] cArr, int i2, int i3) throws IOException {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static Appendable separatingAppendable(Appendable appendable, String str, int i2) {
        Preconditions.checkNotNull(appendable);
        Preconditions.checkNotNull(str);
        Preconditions.checkArgument(i2 > 0);
        return new Appendable(i2, appendable, str) { // from class: com.google.common.io.BaseEncoding.4
            int charsUntilSeparator;
            final /* synthetic */ int val$afterEveryChars;
            final /* synthetic */ Appendable val$delegate;
            final /* synthetic */ String val$separator;

            {
                this.val$afterEveryChars = i2;
                this.val$delegate = appendable;
                this.val$separator = str;
                this.charsUntilSeparator = i2;
            }

            @Override // java.lang.Appendable
            public Appendable append(char c3) throws IOException {
                if (this.charsUntilSeparator == 0) {
                    this.val$delegate.append(this.val$separator);
                    this.charsUntilSeparator = this.val$afterEveryChars;
                }
                this.val$delegate.append(c3);
                this.charsUntilSeparator--;
                return this;
            }

            @Override // java.lang.Appendable
            public Appendable append(@NullableDecl CharSequence charSequence, int i3, int i4) throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override // java.lang.Appendable
            public Appendable append(@NullableDecl CharSequence charSequence) throws IOException {
                throw new UnsupportedOperationException();
            }
        };
    }

    @GwtIncompatible
    public static Writer separatingWriter(final Writer writer, String str, int i2) {
        final Appendable appendableSeparatingAppendable = separatingAppendable(writer, str, i2);
        return new Writer() { // from class: com.google.common.io.BaseEncoding.5
            @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                writer.close();
            }

            @Override // java.io.Writer, java.io.Flushable
            public void flush() throws IOException {
                writer.flush();
            }

            @Override // java.io.Writer
            public void write(int i3) throws IOException {
                appendableSeparatingAppendable.append((char) i3);
            }

            @Override // java.io.Writer
            public void write(char[] cArr, int i3, int i4) throws IOException {
                throw new UnsupportedOperationException();
            }
        };
    }

    public abstract boolean canDecode(CharSequence charSequence);

    public final byte[] decode(CharSequence charSequence) {
        try {
            return decodeChecked(charSequence);
        } catch (DecodingException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public final byte[] decodeChecked(CharSequence charSequence) throws DecodingException {
        CharSequence charSequenceTrimTrailingPadding = trimTrailingPadding(charSequence);
        byte[] bArr = new byte[maxDecodedSize(charSequenceTrimTrailingPadding.length())];
        return extract(bArr, decodeTo(bArr, charSequenceTrimTrailingPadding));
    }

    public abstract int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException;

    @GwtIncompatible
    public final ByteSource decodingSource(final CharSource charSource) {
        Preconditions.checkNotNull(charSource);
        return new ByteSource() { // from class: com.google.common.io.BaseEncoding.2
            @Override // com.google.common.io.ByteSource
            public InputStream openStream() throws IOException {
                return BaseEncoding.this.decodingStream(charSource.openStream());
            }
        };
    }

    @GwtIncompatible
    public abstract InputStream decodingStream(Reader reader);

    public String encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    public abstract void encodeTo(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException;

    @GwtIncompatible
    public final ByteSink encodingSink(final CharSink charSink) {
        Preconditions.checkNotNull(charSink);
        return new ByteSink() { // from class: com.google.common.io.BaseEncoding.1
            @Override // com.google.common.io.ByteSink
            public OutputStream openStream() throws IOException {
                return BaseEncoding.this.encodingStream(charSink.openStream());
            }
        };
    }

    @GwtIncompatible
    public abstract OutputStream encodingStream(Writer writer);

    public abstract BaseEncoding lowerCase();

    public abstract int maxDecodedSize(int i2);

    public abstract int maxEncodedSize(int i2);

    public abstract BaseEncoding omitPadding();

    public CharSequence trimTrailingPadding(CharSequence charSequence) {
        return (CharSequence) Preconditions.checkNotNull(charSequence);
    }

    public abstract BaseEncoding upperCase();

    public abstract BaseEncoding withPadChar(char c3);

    public abstract BaseEncoding withSeparator(String str, int i2);

    public final String encode(byte[] bArr, int i2, int i3) {
        Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
        StringBuilder sb = new StringBuilder(maxEncodedSize(i3));
        try {
            encodeTo(sb, bArr, i2, i3);
            return sb.toString();
        } catch (IOException e2) {
            throw new AssertionError(e2);
        }
    }
}
