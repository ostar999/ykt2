package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes9.dex */
class Simple8BitZipEncoding implements ZipEncoding {
    private final char[] highChars;
    private final List<Simple8BitChar> reverseMapping;

    public static final class Simple8BitChar implements Comparable<Simple8BitChar> {
        public final byte code;
        public final char unicode;

        public Simple8BitChar(byte b3, char c3) {
            this.code = b3;
            this.unicode = c3;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Simple8BitChar)) {
                return false;
            }
            Simple8BitChar simple8BitChar = (Simple8BitChar) obj;
            return this.unicode == simple8BitChar.unicode && this.code == simple8BitChar.code;
        }

        public int hashCode() {
            return this.unicode;
        }

        public String toString() {
            return "0x" + Integer.toHexString(65535 & this.unicode) + "->0x" + Integer.toHexString(this.code & 255);
        }

        @Override // java.lang.Comparable
        public int compareTo(Simple8BitChar simple8BitChar) {
            return this.unicode - simple8BitChar.unicode;
        }
    }

    public Simple8BitZipEncoding(char[] cArr) {
        char[] cArr2 = (char[]) cArr.clone();
        this.highChars = cArr2;
        ArrayList arrayList = new ArrayList(cArr2.length);
        byte b3 = 127;
        for (char c3 : cArr2) {
            b3 = (byte) (b3 + 1);
            arrayList.add(new Simple8BitChar(b3, c3));
        }
        Collections.sort(arrayList);
        this.reverseMapping = Collections.unmodifiableList(arrayList);
    }

    private Simple8BitChar encodeHighChar(char c3) {
        int size = this.reverseMapping.size();
        int i2 = 0;
        while (size > i2) {
            int i3 = ((size - i2) / 2) + i2;
            Simple8BitChar simple8BitChar = this.reverseMapping.get(i3);
            char c4 = simple8BitChar.unicode;
            if (c4 == c3) {
                return simple8BitChar;
            }
            if (c4 < c3) {
                i2 = i3 + 1;
            } else {
                size = i3;
            }
        }
        if (i2 >= this.reverseMapping.size()) {
            return null;
        }
        Simple8BitChar simple8BitChar2 = this.reverseMapping.get(i2);
        if (simple8BitChar2.unicode != c3) {
            return null;
        }
        return simple8BitChar2;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipEncoding
    public boolean canEncode(String str) {
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (!canEncodeChar(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public boolean canEncodeChar(char c3) {
        return (c3 >= 0 && c3 < 128) || encodeHighChar(c3) != null;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipEncoding
    public String decode(byte[] bArr) throws IOException {
        char[] cArr = new char[bArr.length];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            cArr[i2] = decodeByte(bArr[i2]);
        }
        return new String(cArr);
    }

    public char decodeByte(byte b3) {
        return b3 >= 0 ? (char) b3 : this.highChars[b3 + 128];
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipEncoding
    public ByteBuffer encode(String str) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(str.length() + 6 + ((str.length() + 1) / 2));
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (byteBufferAllocate.remaining() < 6) {
                byteBufferAllocate = ZipEncodingHelper.growBuffer(byteBufferAllocate, byteBufferAllocate.position() + 6);
            }
            if (!pushEncodedChar(byteBufferAllocate, cCharAt)) {
                ZipEncodingHelper.appendSurrogate(byteBufferAllocate, cCharAt);
            }
        }
        byteBufferAllocate.limit(byteBufferAllocate.position());
        byteBufferAllocate.rewind();
        return byteBufferAllocate;
    }

    public boolean pushEncodedChar(ByteBuffer byteBuffer, char c3) {
        if (c3 >= 0 && c3 < 128) {
            byteBuffer.put((byte) c3);
            return true;
        }
        Simple8BitChar simple8BitCharEncodeHighChar = encodeHighChar(c3);
        if (simple8BitCharEncodeHighChar == null) {
            return false;
        }
        byteBuffer.put(simple8BitCharEncodeHighChar.code);
        return true;
    }
}
