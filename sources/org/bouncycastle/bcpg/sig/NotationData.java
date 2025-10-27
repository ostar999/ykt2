package org.bouncycastle.bcpg.sig;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.bcpg.SignatureSubpacket;
import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
public class NotationData extends SignatureSubpacket {
    public static final int HEADER_FLAG_LENGTH = 4;
    public static final int HEADER_NAME_LENGTH = 2;
    public static final int HEADER_VALUE_LENGTH = 2;

    public NotationData(boolean z2, boolean z3, String str, String str2) {
        super(20, z2, createData(z3, str, str2));
    }

    public NotationData(boolean z2, byte[] bArr) {
        super(20, z2, bArr);
    }

    private static byte[] createData(boolean z2, String str, String str2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(z2 ? 128 : 0);
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(0);
        byte[] uTF8ByteArray = Strings.toUTF8ByteArray(str);
        int iMin = Math.min(uTF8ByteArray.length, 255);
        byte[] uTF8ByteArray2 = Strings.toUTF8ByteArray(str2);
        int iMin2 = Math.min(uTF8ByteArray2.length, 255);
        byteArrayOutputStream.write((iMin >>> 8) & 255);
        byteArrayOutputStream.write((iMin >>> 0) & 255);
        byteArrayOutputStream.write((iMin2 >>> 8) & 255);
        byteArrayOutputStream.write(255 & (iMin2 >>> 0));
        byteArrayOutputStream.write(uTF8ByteArray, 0, iMin);
        byteArrayOutputStream.write(uTF8ByteArray2, 0, iMin2);
        return byteArrayOutputStream.toByteArray();
    }

    public String getNotationName() {
        byte[] bArr = this.data;
        int i2 = (bArr[4] << 8) + (bArr[5] << 0);
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, 8, bArr2, 0, i2);
        return Strings.fromUTF8ByteArray(bArr2);
    }

    public String getNotationValue() {
        return Strings.fromUTF8ByteArray(getNotationValueBytes());
    }

    public byte[] getNotationValueBytes() {
        byte[] bArr = this.data;
        int i2 = (bArr[4] << 8) + (bArr[5] << 0);
        int i3 = (bArr[6] << 8) + (bArr[7] << 0);
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2 + 8, bArr2, 0, i3);
        return bArr2;
    }

    public boolean isHumanReadable() {
        return this.data[0] == -128;
    }
}
