package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes9.dex */
public class DERBMPString extends ASN1Object implements DERString {
    String string;

    public DERBMPString(String str) {
        this.string = str;
    }

    public DERBMPString(byte[] bArr) {
        int length = bArr.length / 2;
        char[] cArr = new char[length];
        for (int i2 = 0; i2 != length; i2++) {
            int i3 = i2 * 2;
            cArr[i2] = (char) ((bArr[i3 + 1] & 255) | (bArr[i3] << 8));
        }
        this.string = new String(cArr);
    }

    public static DERBMPString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERBMPString)) {
            return (DERBMPString) obj;
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static DERBMPString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        DERObject object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERBMPString)) ? getInstance(object) : new DERBMPString(ASN1OctetString.getInstance(object).getOctets());
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public boolean asn1Equals(DERObject dERObject) {
        if (dERObject instanceof DERBMPString) {
            return getString().equals(((DERBMPString) dERObject).getString());
        }
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        char[] charArray = this.string.toCharArray();
        byte[] bArr = new byte[charArray.length * 2];
        for (int i2 = 0; i2 != charArray.length; i2++) {
            int i3 = i2 * 2;
            char c3 = charArray[i2];
            bArr[i3] = (byte) (c3 >> '\b');
            bArr[i3 + 1] = (byte) c3;
        }
        dEROutputStream.writeEncoded(30, bArr);
    }

    @Override // org.bouncycastle.asn1.ASN1String
    public String getString() {
        return this.string;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject, org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        return getString().hashCode();
    }

    public String toString() {
        return this.string;
    }
}
