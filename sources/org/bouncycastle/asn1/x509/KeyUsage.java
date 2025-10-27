package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.DERBitString;

/* loaded from: classes9.dex */
public class KeyUsage extends DERBitString {
    public static final int cRLSign = 2;
    public static final int dataEncipherment = 16;
    public static final int decipherOnly = 32768;
    public static final int digitalSignature = 128;
    public static final int encipherOnly = 1;
    public static final int keyAgreement = 8;
    public static final int keyCertSign = 4;
    public static final int keyEncipherment = 32;
    public static final int nonRepudiation = 64;

    public KeyUsage(int i2) {
        super(DERBitString.getBytes(i2), DERBitString.getPadBits(i2));
    }

    public KeyUsage(DERBitString dERBitString) {
        super(dERBitString.getBytes(), dERBitString.getPadBits());
    }

    public static DERBitString getInstance(Object obj) {
        return obj instanceof KeyUsage ? (KeyUsage) obj : obj instanceof X509Extension ? new KeyUsage(DERBitString.getInstance(X509Extension.convertValueToObject((X509Extension) obj))) : new KeyUsage(DERBitString.getInstance(obj));
    }

    @Override // org.bouncycastle.asn1.DERBitString
    public String toString() {
        StringBuilder sb;
        int i2;
        if (this.data.length == 1) {
            sb = new StringBuilder();
            sb.append("KeyUsage: 0x");
            i2 = this.data[0] & 255;
        } else {
            sb = new StringBuilder();
            sb.append("KeyUsage: 0x");
            byte[] bArr = this.data;
            i2 = (bArr[0] & 255) | ((bArr[1] & 255) << 8);
        }
        sb.append(Integer.toHexString(i2));
        return sb.toString();
    }
}
