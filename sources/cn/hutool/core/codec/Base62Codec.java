package cn.hutool.core.codec;

import cn.hutool.core.util.PrimitiveArrayUtil;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes.dex */
public class Base62Codec implements Encoder<byte[], byte[]>, Decoder<byte[], byte[]>, Serializable {
    public static Base62Codec INSTANCE = new Base62Codec();
    private static final int STANDARD_BASE = 256;
    private static final int TARGET_BASE = 62;
    private static final long serialVersionUID = 1;

    public static class Base62Decoder implements Decoder<byte[], byte[]> {
        public static Base62Decoder GMP_DECODER = new Base62Decoder(Base62Encoder.GMP);
        public static Base62Decoder INVERTED_DECODER = new Base62Decoder(Base62Encoder.INVERTED);
        private final byte[] lookupTable = new byte[123];

        public Base62Decoder(byte[] bArr) {
            for (int i2 = 0; i2 < bArr.length; i2++) {
                this.lookupTable[bArr[i2]] = (byte) i2;
            }
        }

        @Override // cn.hutool.core.codec.Decoder
        public byte[] decode(byte[] bArr) {
            return Base62Codec.convert(Base62Codec.translate(bArr, this.lookupTable), 62, 256);
        }
    }

    public static class Base62Encoder implements Encoder<byte[], byte[]> {
        private static final byte[] GMP;
        public static Base62Encoder GMP_ENCODER;
        private static final byte[] INVERTED;
        public static Base62Encoder INVERTED_ENCODER;
        private final byte[] alphabet;

        static {
            byte[] bArr = {TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122};
            GMP = bArr;
            byte[] bArr2 = {TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90};
            INVERTED = bArr2;
            GMP_ENCODER = new Base62Encoder(bArr);
            INVERTED_ENCODER = new Base62Encoder(bArr2);
        }

        public Base62Encoder(byte[] bArr) {
            this.alphabet = bArr;
        }

        @Override // cn.hutool.core.codec.Encoder
        public byte[] encode(byte[] bArr) {
            return Base62Codec.translate(Base62Codec.convert(bArr, 256, 62), this.alphabet);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] convert(byte[] bArr, int i2, int i3) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(estimateOutputLength(bArr.length, i2, i3));
        byte[] byteArray = bArr;
        while (true) {
            if (byteArray.length <= 0) {
                break;
            }
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream(byteArray.length);
            int i4 = 0;
            for (byte b3 : byteArray) {
                int i5 = (b3 & 255) + (i4 * i2);
                i4 = i5 % i3;
                int i6 = (i5 - i4) / i3;
                if (byteArrayOutputStream2.size() > 0 || i6 > 0) {
                    byteArrayOutputStream2.write(i6);
                }
            }
            byteArrayOutputStream.write(i4);
            byteArray = byteArrayOutputStream2.toByteArray();
        }
        for (int i7 = 0; i7 < bArr.length - 1 && bArr[i7] == 0; i7++) {
            byteArrayOutputStream.write(0);
        }
        return PrimitiveArrayUtil.reverse(byteArrayOutputStream.toByteArray());
    }

    private static int estimateOutputLength(int i2, int i3, int i4) {
        return (int) Math.ceil((Math.log(i3) / Math.log(i4)) * i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] translate(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr3[i2] = bArr2[bArr[i2]];
        }
        return bArr3;
    }

    @Override // cn.hutool.core.codec.Decoder
    public byte[] decode(byte[] bArr) {
        return decode(bArr, false);
    }

    @Override // cn.hutool.core.codec.Encoder
    public byte[] encode(byte[] bArr) {
        return encode(bArr, false);
    }

    public byte[] decode(byte[] bArr, boolean z2) {
        return (z2 ? Base62Decoder.INVERTED_DECODER : Base62Decoder.GMP_DECODER).decode(bArr);
    }

    public byte[] encode(byte[] bArr, boolean z2) {
        return (z2 ? Base62Encoder.INVERTED_ENCODER : Base62Encoder.GMP_ENCODER).encode(bArr);
    }
}
