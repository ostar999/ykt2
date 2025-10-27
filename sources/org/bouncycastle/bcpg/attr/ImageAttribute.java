package org.bouncycastle.bcpg.attr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.bcpg.UserAttributeSubpacket;

/* loaded from: classes9.dex */
public class ImageAttribute extends UserAttributeSubpacket {
    public static final int JPEG = 1;
    private static final byte[] ZEROES = new byte[12];
    private int encoding;
    private int hdrLength;
    private byte[] imageData;
    private int version;

    public ImageAttribute(int i2, byte[] bArr) {
        this(toByteArray(i2, bArr));
    }

    public ImageAttribute(byte[] bArr) {
        super(1, bArr);
        int i2 = ((bArr[1] & 255) << 8) | (bArr[0] & 255);
        this.hdrLength = i2;
        this.version = bArr[2] & 255;
        this.encoding = bArr[3] & 255;
        byte[] bArr2 = new byte[bArr.length - i2];
        this.imageData = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, bArr2.length);
    }

    private static byte[] toByteArray(int i2, byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(16);
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write(1);
            byteArrayOutputStream.write(i2);
            byteArrayOutputStream.write(ZEROES);
            byteArrayOutputStream.write(bArr);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            throw new RuntimeException("unable to encode to byte array!");
        }
    }

    public int getEncoding() {
        return this.encoding;
    }

    public byte[] getImageData() {
        return this.imageData;
    }

    public int version() {
        return this.version;
    }
}
