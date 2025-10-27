package org.apache.commons.compress.archivers.zip;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.text.Typography;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.compress.utils.Charsets;

/* loaded from: classes9.dex */
public abstract class ZipEncodingHelper {
    private static final byte[] HEX_DIGITS;
    static final String UTF8 = "UTF8";
    static final ZipEncoding UTF8_ZIP_ENCODING;
    private static final Map<String, SimpleEncodingHolder> simpleEncodings;

    public static class SimpleEncodingHolder {
        private Simple8BitZipEncoding encoding;
        private final char[] highChars;

        public SimpleEncodingHolder(char[] cArr) {
            this.highChars = cArr;
        }

        public synchronized Simple8BitZipEncoding getEncoding() {
            if (this.encoding == null) {
                this.encoding = new Simple8BitZipEncoding(this.highChars);
            }
            return this.encoding;
        }
    }

    static {
        HashMap map = new HashMap();
        SimpleEncodingHolder simpleEncodingHolder = new SimpleEncodingHolder(new char[]{199, 252, 233, 226, 228, 224, 229, 231, 234, 235, 232, 239, 238, 236, 196, 197, 201, 230, 198, 244, 246, 242, 251, 249, 255, 214, 220, Typography.cent, Typography.pound, 165, 8359, 402, 225, 237, 243, 250, 241, 209, 170, 186, 191, 8976, 172, Typography.half, 188, 161, 171, 187, 9617, 9618, 9619, 9474, 9508, 9569, 9570, 9558, 9557, 9571, 9553, 9559, 9565, 9564, 9563, 9488, 9492, 9524, 9516, 9500, 9472, 9532, 9566, 9567, 9562, 9556, 9577, 9574, 9568, 9552, 9580, 9575, 9576, 9572, 9573, 9561, 9560, 9554, 9555, 9579, 9578, 9496, 9484, 9608, 9604, 9612, 9616, 9600, 945, 223, 915, 960, 931, 963, 181, 964, 934, 920, 937, 948, 8734, 966, 949, 8745, 8801, Typography.plusMinus, Typography.greaterOrEqual, Typography.lessOrEqual, 8992, 8993, 247, Typography.almostEqual, Typography.degree, 8729, Typography.middleDot, 8730, 8319, 178, 9632, Typography.nbsp});
        map.put("CP437", simpleEncodingHolder);
        map.put("Cp437", simpleEncodingHolder);
        map.put("cp437", simpleEncodingHolder);
        map.put("IBM437", simpleEncodingHolder);
        map.put("ibm437", simpleEncodingHolder);
        SimpleEncodingHolder simpleEncodingHolder2 = new SimpleEncodingHolder(new char[]{199, 252, 233, 226, 228, 224, 229, 231, 234, 235, 232, 239, 238, 236, 196, 197, 201, 230, 198, 244, 246, 242, 251, 249, 255, 214, 220, 248, Typography.pound, 216, Typography.times, 402, 225, 237, 243, 250, 241, 209, 170, 186, 191, Typography.registered, 172, Typography.half, 188, 161, 171, 187, 9617, 9618, 9619, 9474, 9508, 193, 194, 192, Typography.copyright, 9571, 9553, 9559, 9565, Typography.cent, 165, 9488, 9492, 9524, 9516, 9500, 9472, 9532, 227, 195, 9562, 9556, 9577, 9574, 9568, 9552, 9580, 164, 240, 208, 202, 203, 200, 305, 205, 206, 207, 9496, 9484, 9608, 9604, 166, 204, 9600, 211, 223, 212, 210, 245, 213, 181, 254, 222, 218, 219, 217, 253, 221, 175, 180, 173, Typography.plusMinus, 8215, 190, Typography.paragraph, Typography.section, 247, 184, Typography.degree, 168, Typography.middleDot, 185, 179, 178, 9632, Typography.nbsp});
        map.put("CP850", simpleEncodingHolder2);
        map.put(InternalZipConstants.CHARSET_CP850, simpleEncodingHolder2);
        map.put("cp850", simpleEncodingHolder2);
        map.put("IBM850", simpleEncodingHolder2);
        map.put("ibm850", simpleEncodingHolder2);
        simpleEncodings = Collections.unmodifiableMap(map);
        HEX_DIGITS = new byte[]{TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 65, 66, 67, 68, 69, 70};
        UTF8_ZIP_ENCODING = new FallbackZipEncoding("UTF8");
    }

    public static void appendSurrogate(ByteBuffer byteBuffer, char c3) {
        byteBuffer.put((byte) 37);
        byteBuffer.put((byte) 85);
        byte[] bArr = HEX_DIGITS;
        byteBuffer.put(bArr[(c3 >> '\f') & 15]);
        byteBuffer.put(bArr[(c3 >> '\b') & 15]);
        byteBuffer.put(bArr[(c3 >> 4) & 15]);
        byteBuffer.put(bArr[c3 & 15]);
    }

    public static ZipEncoding getZipEncoding(String str) {
        if (isUTF8(str)) {
            return UTF8_ZIP_ENCODING;
        }
        if (str == null) {
            return new FallbackZipEncoding();
        }
        SimpleEncodingHolder simpleEncodingHolder = simpleEncodings.get(str);
        if (simpleEncodingHolder != null) {
            return simpleEncodingHolder.getEncoding();
        }
        try {
            return new NioZipEncoding(Charset.forName(str));
        } catch (UnsupportedCharsetException unused) {
            return new FallbackZipEncoding(str);
        }
    }

    public static ByteBuffer growBuffer(ByteBuffer byteBuffer, int i2) {
        byteBuffer.limit(byteBuffer.position());
        byteBuffer.rewind();
        int iCapacity = byteBuffer.capacity() * 2;
        if (iCapacity >= i2) {
            i2 = iCapacity;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i2);
        byteBufferAllocate.put(byteBuffer);
        return byteBufferAllocate;
    }

    public static boolean isUTF8(String str) {
        if (str == null) {
            str = Charset.defaultCharset().name();
        }
        Charset charset = Charsets.UTF_8;
        if (charset.name().equalsIgnoreCase(str)) {
            return true;
        }
        Iterator<String> it = charset.aliases().iterator();
        while (it.hasNext()) {
            if (it.next().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
}
